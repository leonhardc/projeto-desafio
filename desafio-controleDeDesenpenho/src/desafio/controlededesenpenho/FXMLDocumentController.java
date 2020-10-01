/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desafio.controlededesenpenho;

import conexaodb.ConexaoDB;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author Leonardo
 */
public class FXMLDocumentController implements Initializable{
    
@FXML
    private Tab tabDados;

    @FXML
    private TableView<Dados> tabelaDados;

    @FXML
    private TableColumn<Dados, String> colJogo;

    @FXML
    private TableColumn<Dados, String> colPlacar;

    @FXML
    private TableColumn<Dados, String> colPMin;

    @FXML
    private TableColumn<Dados, String> colPMax;

    @FXML
    private TableColumn<Dados, String> colQuebraMin;

    @FXML
    private TableColumn<Dados, String> colQuebraMax;

    @FXML
    private Button botaoAdicionar;

    @FXML
    private Button botaoExcluir;

    @FXML
    private Tab tabDesempenho;
    
    
    private ObservableList<Dados>data;    
    private ConexaoDB dc;
    
    int ultimoJogo;
    int placar;
    int mintemp,ultmintemp ;
    int maxtemp, ultmaxtemp ;
    int quebramin, ultquebramin;
    int quebramax, ultquebramax;
    
   
    void atualizaTabela() throws SQLException { // Metodo usado para atualizar os dados da tabela
        
        Connection conn = ConexaoDB.getConexaoMySQL();
        
        data = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("select * from registros");
        
        while(rs.next()){            
            data.add(new Dados(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
        }
        
        // setando valores na tabela
        colJogo.setCellValueFactory(new PropertyValueFactory<>("jogo"));
        colPlacar.setCellValueFactory(new PropertyValueFactory<>("placar"));
        colPMin.setCellValueFactory(new PropertyValueFactory<>("mintemp"));
        colPMax.setCellValueFactory(new PropertyValueFactory<>("maxtemp"));
        colQuebraMin.setCellValueFactory(new PropertyValueFactory<>("quebramin"));
        colQuebraMax.setCellValueFactory(new PropertyValueFactory<>("quebramax"));
        
        tabelaDados.setItems(null);
        tabelaDados.setItems(data);
        
        ConexaoDB.FecharConexao();
    }   
    
    @FXML
    private void botaoAdiciona() throws SQLException{
 
       try{
            placar = Integer.parseInt(JOptionPane.showInputDialog(null, "Qual o placar do Jogo?"));
            
            Connection conn = ConexaoDB.getConexaoMySQL();
       
            String sqlBuscaUltima = "select * from registros where Jogo = (select max(Jogo) from registros)";
            String sqlAdd = "insert into registros (Jogo, Placar, MinTemp, MaxTemp, QuebraRecMin, QuebraRecMax) values (?,?,?,?,?,?)";

            /** Passos do processo daqui para frente
             1. realiza busca da ultima tupla
             2. compara valores para saber se algo a mais será modificado
             3. reinsere valores no banco*/

            // Primeiro passo: Realizar busca da ultima tupla

             ResultSet res = conn.createStatement().executeQuery("select * from registros where Jogo = (select max(Jogo) from registros)");
             while(res.next()){
             ultimoJogo = res.getInt(1);
             ultmintemp = res.getInt(3);
             ultmaxtemp = res.getInt(4);
             ultquebramin = res.getInt(5);
             ultquebramax = res.getInt(6);
             }


             // Segundo Passo:verificar se temos uma pontuação menor que a menor ou maior que a maior

             if (ultimoJogo == 0){
                 mintemp = maxtemp = placar;
                 quebramin = quebramax = 0;
             }
             else{
                if (placar < ultmintemp){ // atualizando os valores de maximo e minimo da temporada
                                       // nao houve quebra de recorde minimo
                    mintemp = placar;
                    quebramin = ultquebramin;

                 }
                else{ // ouve quebra de recorde minimo
                    mintemp = ultmintemp;
                    if (placar >= ultmintemp && placar < ultmaxtemp){
                        quebramin = ultquebramin + 1;
                    }
                    else {
                        quebramin = ultquebramin;
                    }            
                 }

                if (placar > ultmaxtemp){ // houve quebra de recorde maximo
                    maxtemp = placar;
                    quebramax = ultquebramax + 1;
                }
                else{  // nao houve quebra de recorde maximo
                   maxtemp = ultmaxtemp;
                   quebramax = ultquebramax;
                }
             
             }

             // Terceiro Passo: reinsere valores no banco
             PreparedStatement statement = conn.prepareStatement(sqlAdd);
             statement.setInt(1, ultimoJogo+1);
             statement.setInt(2, placar);
             statement.setInt(3, mintemp);
             statement.setInt(4, maxtemp);
             statement.setInt(5, quebramin);
             statement.setInt(6, quebramax);

             statement.execute();

             ConexaoDB.FecharConexao(); 
             
             atualizaTabela();
       }
       catch(HeadlessException | NumberFormatException e){
         
           JOptionPane.showMessageDialog(null,"Provavelmente você inseriu algum caractere errado!");
       }
       
     
        
    }
    
    @FXML
    private void botaoExclui() throws SQLException{      
        
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o ultimo jogo?","Mensagem", JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            //Usuário clicou em Sim. Executar o código correspondente.
            Connection conn = ConexaoDB.getConexaoMySQL();
        
            // seleciona do banco o ultimo registro
            ResultSet res = conn.createStatement().executeQuery("select * from registros where Jogo = (select max(Jogo) from registros)");
            while(res.next()){
                ultimoJogo = res.getInt(1);
            }

            // faz a exclusão do ultimo jogo
            PreparedStatement statement = conn.prepareStatement("delete from registros where Jogo = ?");
            statement.setInt(1, ultimoJogo);
            statement.executeUpdate();
            ultimoJogo = ultimoJogo - 1; // atualiza a variavel global
            ConexaoDB.FecharConexao();
            atualizaTabela();
        } else if (resposta == JOptionPane.NO_OPTION) {
            //Usuário clicou em não. Executar o código correspondente.
        }
        
        
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dc = new ConexaoDB();
    try {
        atualizaTabela();
    } catch (SQLException ex) {
        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
    }
    } 
}

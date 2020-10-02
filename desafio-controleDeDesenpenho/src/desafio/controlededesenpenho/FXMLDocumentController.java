/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desafio.controlededesenpenho;

import dados.Dados;
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
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import javafx.stage.Stage;

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
    
    @FXML
    private LineChart <String,Number> graficoLinha;

    @FXML
    private CategoryAxis linhaDeJogos;

    @FXML
    private NumberAxis linhaDePontuacao;
    
    @FXML
    private Label labelInfo;

    @FXML
    private Label labelMedia;
    
    @FXML
    private Label labelRecMin;
    
    @FXML
    private Label labelRecMax;
    
    @FXML
    private Label labelInfoPontMin;

    @FXML
    private Label labelInfoPontMax;
    
    int ultimoJogo;
    int placar;
    int mintemp,ultmintemp ;
    int maxtemp, ultmaxtemp ;
    int quebramin, ultquebramin;
    int quebramax, ultquebramax;
    
    // Métodos
    
    void atualizaTabela() throws SQLException { // Metodo usado para atualizar os dados da tabela
        
        Connection conn = ConexaoDB.getConexaoMySQL(); // conexão com banco de dados    
        ObservableList <Dados> data;
        data = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("select * from registros");
        
        while(rs.next()){  
            
            data.add(new Dados(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
        
        }
        
        // setando valores na lista de objetos
        colJogo.setCellValueFactory(new PropertyValueFactory<>("jogo"));
        colPlacar.setCellValueFactory(new PropertyValueFactory<>("placar"));
        colPMin.setCellValueFactory(new PropertyValueFactory<>("mintemp"));
        colPMax.setCellValueFactory(new PropertyValueFactory<>("maxtemp"));
        colQuebraMin.setCellValueFactory(new PropertyValueFactory<>("quebramin"));
        colQuebraMax.setCellValueFactory(new PropertyValueFactory<>("quebramax"));
        
        // Setar valores na Tabela
        tabelaDados.setItems(null);
        tabelaDados.setItems(data);
        
        ConexaoDB.FecharConexao(); // fechar conexção com banco de dados        
        setarDadosGrafico(); // Atualiza valores do gráfico na aba desempenho na aba de desempenho
    }  
    
    @FXML
    void setarDadosGrafico() throws SQLException{ // Seta os dados contidos no banco de dados no gráfico de linha da aba de desempenho
        
        XYChart.Series <String, Number> jogo = new XYChart.Series <String, Number>();
        int somaPlacar = 0, recMin = 0, recMax = 0, numeroDeJogos = 0, auxPontos, mediaPlacar, qMin = 0, qMax = 0;
        String auxJogo;      
        
        //Inicio: Procurar dados no banco
        
        Connection conn = ConexaoDB.getConexaoMySQL();

        ResultSet rs = conn.createStatement().executeQuery("select * from registros");          
        
        while(rs.next()){
            
            auxJogo = "" + rs.getInt(1);
            auxPontos = rs.getInt(2);
            recMin = rs.getInt(3);
            recMax = rs.getInt(4);
            qMin = rs.getInt(5);
            qMax = rs.getInt(6);
                    
            jogo.getData().add(new XYChart.Data<String, Number>(auxJogo,auxPontos));
            
            somaPlacar = somaPlacar + rs.getInt(2);
            numeroDeJogos = rs.getInt(1);
        
        }

        // Setando as entradas dos Labels
        mediaPlacar =  somaPlacar / numeroDeJogos;
        String aux1 = "" + mediaPlacar + " Pontos";
        String aux2 = "" + recMin + " Pontos";
        String aux3 = "" + recMax + " Pontos";
        String aux4 = "Você quebrou seu recorde mínimo " + qMin + " vezes e,";
        String aux5;
        if (qMin == 0 && qMax == 0){
            aux5 = "seu recorde máximo " + qMax + " vezes. Não Desista. VOCÊ CONSEGUE!!!";
        }
        else{
            aux5 = "seu recorde máximo " + qMax + " vezes. Parabéns, continue progredindo!!!";
        }        
        
        // setar Labels da aba de desempenho
        labelMedia.setText(aux1);
        labelRecMin.setText(aux2);
        labelRecMax.setText(aux3);
        labelInfoPontMin.setText(aux4);
        labelInfoPontMax.setText(aux5);
        
        // atualiza informações do gráfico 
        graficoLinha.getData().clear();
        graficoLinha.getData().add(jogo);
        
  
    } 
    
    @FXML
    private void botaoAdiciona() throws SQLException{
 
       try{
           
            placar = Integer.parseInt(JOptionPane.showInputDialog(null, "Qual o placar do Jogo?"));
            
            if(placar >= 0){
                
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
            else{
                // nao existe placar negativo
                JOptionPane.showMessageDialog(null,"Pontuação Inválida!");
            }
       }
       catch(HeadlessException | NumberFormatException e){
         
           JOptionPane.showMessageDialog(null,"Provavelmente você inseriu algo errado!");
       
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
            //Usuário clicou em não.
        }     
        
    }    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    try {
        atualizaTabela();
        setarDadosGrafico();
        
    } catch (SQLException ex) {
        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
    }

    } 

}

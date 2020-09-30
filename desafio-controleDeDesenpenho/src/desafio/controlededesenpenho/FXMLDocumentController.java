/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desafio.controlededesenpenho;

import conexaodb.ConexaoDB;
import java.sql.Connection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
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
    
    @FXML
    private void botaoAdiciona(){
        System.out.println("deu certo");
    }
    
    @FXML
    private void botaoExclui(){
        System.out.println("deu certo...");
    }
    
    @FXML
    void atualizaTabela(ActionEvent event) throws SQLException {
        // fazer conexao com banco
        Connection conn = ConexaoDB.getConexaoMySQL();
        
        data=FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("select * from registros");
        
        while(rs.next()){
            
            data.add(new Dados(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
        }
        
        colJogo.setCellValueFactory(new PropertyValueFactory<>("jogo"));
        colPlacar.setCellValueFactory(new PropertyValueFactory<>("placar"));
        colPMin.setCellValueFactory(new PropertyValueFactory<>("mintemp"));
        colPMax.setCellValueFactory(new PropertyValueFactory<>("maxtemp"));
        colQuebraMin.setCellValueFactory(new PropertyValueFactory<>("quebramin"));
        colQuebraMax.setCellValueFactory(new PropertyValueFactory<>("quebramax"));
        
        tabelaDados.setItems(null);
        tabelaDados.setItems(data);
        
    }    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dc = new ConexaoDB();
    } 
}

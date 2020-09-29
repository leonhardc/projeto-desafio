/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desafio.controlededesenpenho;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author Leonardo
 */
public class FXMLDocumentController implements Initializable {
    
@FXML
    private Tab tabDados;

    @FXML
    private TableView<?> tabelaDados;

    @FXML
    private TableColumn<?, ?> colJogo;

    @FXML
    private TableColumn<?, ?> colPlacar;

    @FXML
    private TableColumn<?, ?> colPMin;

    @FXML
    private TableColumn<?, ?> colPMax;

    @FXML
    private TableColumn<?, ?> colQuebraMin;

    @FXML
    private TableColumn<?, ?> colQuebraMax;

    @FXML
    private Button botaoAdicionar;

    @FXML
    private Button botaoExcluir;

    @FXML
    private Tab tabDesempenho;

    @FXML
    void adicionarJogo(ActionEvent event) {

    }
    
    @FXML
    void excluirJogo(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    
}

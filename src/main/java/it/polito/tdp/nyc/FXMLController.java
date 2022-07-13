/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.nyc;

import java.net.URL;
import java.util.ResourceBundle;
import it.polito.tdp.nyc.model.Model;
import it.polito.tdp.nyc.model.Vicini;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaLista"
    private Button btnCreaLista; // Value injected by FXMLLoader

    @FXML // fx:id="cmbProvider"
    private ComboBox<String> cmbProvider; // Value injected by FXMLLoader

    @FXML // fx:id="cmbQuartiere"
    private ComboBox<String> cmbQuartiere; // Value injected by FXMLLoader

    @FXML // fx:id="txtMemoria"
    private TextField txtMemoria; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML // fx:id="clQuartiere"
    private TableColumn<Vicini, String> clQuartiere; // Value injected by FXMLLoader
 
    @FXML // fx:id="clDistanza"
    private TableColumn<Vicini, Double> clDistanza; // Value injected by FXMLLoader
    
    @FXML // fx:id="tblQuartieri"
    private TableView<Vicini> tblQuartieri; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	cmbQuartiere.getItems().clear();
    	if(!(cmbProvider.getValue()==null))
    	{
    		this.model.creaGrafo(cmbProvider.getValue());
    		txtResult.appendText("Grafo Creato con successo!\n");
    		txtResult.appendText("#Vertici "+ this.model.getNVertici()+"\n");
    		txtResult.appendText("#Archi "+ this.model.getNArchi()+"\n");
    		
    		for(String s : this.model.getQuartieri(cmbProvider.getValue()))
    		{
    			cmbQuartiere.getItems().add(s);
    		}
    	}
    	else
    	{
    		txtResult.appendText("Selezionare un provider");
    		return;
    	}
    	
    	
    }

    @FXML
    void doQuartieriAdiacenti(ActionEvent event) {
    	
    	String quartieri  = cmbQuartiere.getValue();
    	if(!(cmbQuartiere.getValue()==null))
    	{
    		tblQuartieri.setItems(FXCollections.observableArrayList(this.model.getVicini(quartieri)));
//    		for(Vicini v : this.model.getVicini(cmbQuartiere.getValue()))
//    		{
//    			clQuartiere.setText(v.getC());
//    			clDistanza.setText(v.getDistance());
//    		}
    	}
    	else
    	{
    		txtResult.appendText("Selezionare un quartiere");
    		return;
    	}
    	
    }

    @FXML
    void doSimula(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaLista != null : "fx:id=\"btnCreaLista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProvider != null : "fx:id=\"cmbProvider\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbQuartiere != null : "fx:id=\"cmbQuartiere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMemoria != null : "fx:id=\"txtMemoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clDistanza != null : "fx:id=\"clDistanza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clQuartiere != null : "fx:id=\"clQuartiere\" was not injected: check your FXML file 'Scene.fxml'.";
        
        clQuartiere.setCellValueFactory(new PropertyValueFactory<Vicini,String>("c"));
        clDistanza.setCellValueFactory(new PropertyValueFactory<Vicini,Double>("distance"));
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	for(String s : this.model.getAllProvider())
    	{
    		cmbProvider.getItems().add(s);
    	}
    }

}

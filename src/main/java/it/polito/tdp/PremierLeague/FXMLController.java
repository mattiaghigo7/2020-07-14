/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Team;
import it.polito.tdp.PremierLeague.model.TeamPunti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnClassifica"
    private Button btnClassifica; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSquadra"
    private ComboBox<Team> cmbSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doClassifica(ActionEvent event) {
    	Team t = this.cmbSquadra.getValue();
    	if(t==null) {
    		this.txtResult.setText("Selezionare una squadra.");
    		return;
    	}
    	List<TeamPunti> migliori = model.getMigliori(t);
    	List<TeamPunti> peggiori = model.getPeggiori(t);
    	TeamPunti team = model.getTP(t);
    	this.txtResult.setText("SQUADRE MIGLIORI\n");
    	for(TeamPunti tp : migliori) {
    		this.txtResult.appendText(tp.getTeam().getName()+"("+tp.getPunti()+")\n");
    	}
    	this.txtResult.appendText("\nSQUADRE PEGGIORI\n");
    	for(TeamPunti tp : peggiori) {
    		this.txtResult.appendText(tp.getTeam().getName()+"("+tp.getPunti()+")\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	model.creaGrafo();
    	this.txtResult.setText("Grafo creato\n");
    	this.txtResult.appendText("# VERTICI: "+model.getVerticiSize()+"\n");
    	this.txtResult.appendText("# ARCHI: "+model.getArchiSize()+"\n");
    	this.cmbSquadra.getItems().addAll(model.getAllTeam());
    }

    @FXML
    void doSimula(ActionEvent event) {
    	String n = this.txtN.getText();
    	String x = this.txtX.getText();
    	if(n=="" || x=="") {
    		this.txtResult.setText("Inserire i valori per N e X.");
    		return;
    	}
    	try {
    		Integer N = Integer.parseInt(n);
    		Integer X = Integer.parseInt(x);
    		model.simula(N, X);
    		this.txtResult.setText("Simulazione eseguita.\n");
    		this.txtResult.appendText("Media report: "+model.getMedia()+"\n");
    		this.txtResult.appendText("Partite critiche: "+this.model.getCritici()+"\n");
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Inserire dei valori validi");
    		return;
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClassifica != null : "fx:id=\"btnClassifica\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbSquadra != null : "fx:id=\"cmbSquadra\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}

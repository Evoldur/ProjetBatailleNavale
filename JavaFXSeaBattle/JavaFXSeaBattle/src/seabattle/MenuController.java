/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seabattle;
import Metier.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author guforce
 */
public class MenuController implements Initializable {
    Stage primaryStage;
    String J1;
    String J2;
    
    
    @FXML private TextField j1;
    @FXML private TextField j2;
    @FXML private ComboBox comboDim; 
    @FXML private Label nbbateau; 
     
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboDim.valueProperty().setValue("10 x 10");
        j1.textProperty().setValue("toto");
        j2.textProperty().setValue("titi");
    }    
    
    public MenuController(Stage primaryStage){
        this.primaryStage = primaryStage;
    }    
    @FXML
    public void fight() throws IOException{
        Stage sta = new Stage();
        J1 = j1.textProperty().getValue();
        J2 = j2.textProperty().getValue();
        Player p1 = new Player(J1, new Board());
        Player p2 = new Player(J2, new Board());
        FXMLLoader root = new FXMLLoader(getClass().getResource("BoatPlaceWin.fxml"));
        root.setController(new BoatPlaceWinController(p1, p2,sta));
        sta.setScene(new Scene(root.load()));
        //sta.resizableProperty().set(false);
        sta.show();
        primaryStage.hide();
    }
    
    @FXML
    public void quit(){
        this.primaryStage.close();
    }
}

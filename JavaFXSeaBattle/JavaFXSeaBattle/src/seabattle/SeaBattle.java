/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seabattle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author guforce
 */
public class SeaBattle extends Application {
    
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader root = new FXMLLoader(getClass().getResource("Menu.fxml"));
        root.setController(new MenuController(stage));
        stage.minHeightProperty().setValue(500);
        stage.maxHeightProperty().setValue(500);
        stage.minWidthProperty().setValue(462);
        stage.maxWidthProperty().setValue(462);
        stage.setScene(new Scene(root.load()));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

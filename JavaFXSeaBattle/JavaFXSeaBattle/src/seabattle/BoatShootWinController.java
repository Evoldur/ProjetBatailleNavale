/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seabattle;

import javafx.beans.binding.NumberBinding;
import Metier.Alignment;
import Metier.Board;
import Metier.Coordonnees;
import Metier.Player;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Powerofcloud
 */
public class BoatShootWinController implements Initializable {
    private Alignment algt = Alignment.Horizontal;
    private Player p1,p2;
    private String str;
    public Player selectedP, oppenant;
    private int steps=0;
    private int nbBoat=5,pos;
    private Coordonnees position;
    private Image background= new Image("/images/whitebackground.jpg");
    private Image circle5 = new Image("/images/circle5.png");
    private Image circle4 = new Image("/images/circle4.png");
    private Image circle3 = new Image("/images/circle3.png");
    private Image circle2 = new Image("/images/circle2.png");
    private Image cross = new Image("/images/cross.png");
    private Image red_cross=new Image("/images/redcross.png");
    private ImageView[] tabCrossImage=new ImageView[5];
    private ImageView[][] imageV=new ImageView[10][10];
    private GridPane  main_grid1= new GridPane();
    private GridPane  main_grid2= new GridPane();
    private GridPane reset= new GridPane();
    
    @FXML private GridPane selected_grid;
    @FXML private GridPane grid_select_boat;
    @FXML private String txtName;
    @FXML private Label PName,pv1,pv2,pv3,pv4,pv5;
    @FXML private Label instructions;
    @FXML private Button ValBtn;
    @FXML private Label hpLabel;
    @FXML private Label winLabel;
 
    /**
     * Constructeur
     * @param p1
     * @param p2 
     */
    public BoatShootWinController(Player p1, Player p2) {
        this.p1=p1;
        this.p2=p2;
        selectedP=p1;
        oppenant = p2;
        //txtName = selectedP.getName();
    }

    /**
     * initialise la fenetre
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       main_grid1 = initialiseGrid(selected_grid, background);
       PName.textProperty().bind(selectedP.nameProperty());
       bindHealth();
       instructions.textProperty().setValue("Shoot a Position!");
       placeImage(circle5,5,0,tabCrossImage);
       placeImage(circle4,4,0,tabCrossImage);
       placeImage(circle3,3,0,tabCrossImage);
       placeImage(circle3,3,1,tabCrossImage);
       placeImage(circle2,2,0,tabCrossImage);
    }
        
    /**
     * permet le binding des propriétés de divers label sur les HP des bateaux et du joueur
     */
    private void bindHealth(){
        pv1.textProperty().bind(selectedP.getCa().getHp().asString());
        pv2.textProperty().bind(selectedP.getBa().getHp().asString());
        pv3.textProperty().bind(selectedP.getCu().getHp().asString());
        pv4.textProperty().bind(selectedP.getSu().getHp().asString());
        pv5.textProperty().bind(selectedP.getDe().getHp().asString());
        hpLabel.textProperty().bind(selectedP.getVieRestante().asString());
    }
    
    /**
     * Place les bateaux disponible dans une grille
     * @param url
     * @param taille taille du bateau
     * @param num numero du bateau (sert pour faire la difference entre 2 bateaux ayant la meme taille)
     * @param tabCrossImage 
     */
    private void placeImage(Image url,int taille,int num,ImageView[] tabCrossImage){ 
       pos=taille;
       int posfinal;
       if(pos==2){
           posfinal = 4;
           pos=1;
       }
       else if(num==1&&taille==3){
           posfinal = 3;
           pos=2;
       }
       else posfinal = 5 -taille;
       ImageView bateau=new ImageView(url);
       bateau.setFitHeight(28);
       bateau.setFitWidth(28*taille);
      
       grid_select_boat.add(bateau,0,5-pos);
    }
   
    
    /**
     * Initialise la grille principale avec des imagesView qui utilise l'image background
     * @param grid
     * @param background 
     */
    public GridPane initialiseGrid(GridPane grid, Image background){
        int i;
        for(i=1;i<11;i++){
            grid.add(new Label(""+i), i, 0);
        }
        i=1;
        for(char alphabet = 'A'; alphabet <= 'J';alphabet++) {
           str = Character.toString(alphabet);
           grid.add(new Label(str), 0, i);
           i++;
        }
        Coordonnees c = new Coordonnees(0,0);
        for(i=1;i<11;i++){
            for(int j=1;j<11;j++){
                c.setX(i);
                c.setY(j);
                if(oppenant.getBoard().checkCase(c)==0 || oppenant.getBoard().checkCase(c)==1 ){
                    imageV[i-1][j-1]=new ImageView(background);
                }
                else if(oppenant.getBoard().checkCase(c)==2 ){
                    imageV[i-1][j-1]=new ImageView(cross);
                }
                else if(oppenant.getBoard().checkCase(c)==3 ){
                    imageV[i-1][j-1]=new ImageView(red_cross);
                }
                imageV[i-1][j-1].setFitHeight(45);
                imageV[i-1][j-1].setFitWidth(53);
                int x = i;
                int y = j;
                imageV[i-1][j-1].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {                
                            gridShootEvent(x,y);
                    }
                });
                grid.add(imageV[i-1][j-1], i, j);   
           }
        }
        return grid;
    }
    
    /**
     * Controle le tir sur une case
     * @param x coordonnée x de la case ciblée
     * @param y coordonnée y de la case ciblée
     */
    public void gridShootEvent(int x, int y){
        if(steps==0){
            position = new Coordonnees(x,y);
            if(oppenant.getBoard().checkShoot(position)){
                if(oppenant.getBoard().shootPosition(position)){
                    //lancer une fonction pour afficher une pitite croix du touché
                      imageV[x-1][y-1].imageProperty().set(red_cross);
                      oppenant.decVie();
                      //if(oppenant.getVie()==0){
                      if(oppenant.getVieRestante().getValue().intValue() == 0){
                          winLabel.textProperty().bind(PName.textProperty());
                          ValBtn.disableProperty().setValue(true);
                      }
                }
                else{
                    //lancer une fonction pour afficher une pitite croix du loupé
                    imageV[x-1][y-1].imageProperty().set(cross);
                    //selected_grid.add(imageV[position.getX()-1][position.getY()-1], position.getX(), position.getY());
                }
                steps=1;
            }
            else{
                instructions.textProperty().setValue("Shot cancelled: position already tried");
            }
        }
    }
    
    /**
     * Permet de passer son tour et actualise la fenetre
     * @throws IOException 
     */
    @FXML
    public void validate() throws IOException{
        if(steps == 1){
            if(selectedP.equals(p1)){
                selectedP = p2;
                oppenant = p1;
                bindHealth();
                main_grid2 = initialiseGrid(selected_grid, background);
                instructions.textProperty().setValue(p2.getName() + "turn");
                PName.textProperty().bind(selectedP.nameProperty());
            }
            else{
                selectedP = p1;
                oppenant = p2;
                bindHealth();
                main_grid1 = initialiseGrid(selected_grid, background);
                instructions.textProperty().setValue(p1.getName() + "turn");
                PName.textProperty().bind(selectedP.nameProperty());
            }
            steps=0;
        }
    }
}

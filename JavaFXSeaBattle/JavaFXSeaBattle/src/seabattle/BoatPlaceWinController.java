/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seabattle;

import Metier.*;
import java.io.IOException;
import java.net.URL;
import java.time.Clock;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author guforce
 */
public class BoatPlaceWinController implements Initializable {
    Stage primaryStage;
    private Alignment algt = Alignment.Horizontal;
    private Player p1,p2;
    private String str;
    public Player selectedP;
    private int steps=0;
    private int nbBoat,pos,tailleBoat,numBoat;
    private Coordonnees position;
    private Image background= new Image("/images/whitebackground.jpg");
    private Image circle5 = new Image("/images/circle5.png");
    private Image circle4 = new Image("/images/circle4.png");
    private Image circle3 = new Image("/images/circle3.png");
    private Image circle2 = new Image("/images/circle2.png");
    private Image cross = new Image("/images/arrow.png");
    private Image bobato=new Image("/images/boat.png");
    private ImageView[] tabCrossImage=new ImageView[5];
    private ImageView[][] imageV=new ImageView[10][10];
    final ToggleGroup rb_group=new ToggleGroup();
    
    @FXML private GridPane main_grid;
    @FXML private GridPane grid_select_boat;
    @FXML private String txtName;
    @FXML private Label PName;
    @FXML private RadioButton rb_horizon;
    @FXML private RadioButton rb_vert;
    @FXML private Label instructions;
    @FXML private Button acceptBtn,ValBtn;
    
    
    /**
     * Appel la fonction d'initialisation de la fenetre
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       ValBtn.disableProperty().setValue(true);
       setWindow();
       createToggleGroup();
       placeImage(circle5,5,0,tabCrossImage);
       placeImage(circle4,4,0,tabCrossImage);
       placeImage(circle3,3,0,tabCrossImage);
       placeImage(circle3,3,1,tabCrossImage);
       placeImage(circle2,2,0,tabCrossImage);
    }
    
    /**
     * initialise la fenetre
     */
    private void setWindow(){
       nbBoat = 5;
       acceptBtn.disableProperty().setValue(true);
       PName.textProperty().bind(selectedP.nameProperty());
       instructions.textProperty().setValue("Select a boat");
       //fin remplisage grille plateau
       /*Images Bateaux*/   
       placerCroix(tabCrossImage,nbBoat);
       //remplisage de la grille de jeu
       initialiseGrid(main_grid, background);
    }
      
    /**
     * Crée un groupe pour des radiobuttons
     */
    private void createToggleGroup(){
        rb_horizon.setToggleGroup(rb_group);
        rb_horizon.selectedProperty().setValue(Boolean.TRUE);
        rb_vert.setToggleGroup(rb_group);
        rb_group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
        @Override
        public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
            RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle();
            if(algt == Alignment.Horizontal){
                algt = Alignment.Vertical;
            }
            else algt = Alignment.Horizontal;
        }
       });
    }
    
    /**
     * place une fleche à coté du bateau selectionné
     * @param tab
     * @param nbBoat 
     */
    private void placerCroix(ImageView[] tab, int nbBoat){
        for(int i =0;i<nbBoat;i++){
            tab[i] = new ImageView(cross);
            tab[i].setFitHeight(28);
            tab[i].setFitWidth(140);
        }
    }
    

    /**
     * Initialise la grille principale avec des imagesView qui utilise l'image background
     * @param grid
     * @param background 
     */
    public void initialiseGrid(GridPane grid, Image background){
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
        for(i=1;i<11;i++){
            for(int j=1;j<11;j++){
                imageV[i-1][j-1]=new ImageView(background);
                imageV[i-1][j-1].setFitHeight(45);
                imageV[i-1][j-1].setFitWidth(53);
                int x = i;
                int y = j;
                    imageV[i-1][j-1].setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            initGridEvent(x,y);
                        }
                    });
                
                grid.add(imageV[i-1][j-1], i, j);
            }
        }
    }
    
    /**
     * Permet de déclencher un evenement lorsqu'on veut placer un bateau sur la grille principaleS
     * appelle la fonction checkPosition de la classe métier Board
     * @param x
     * @param y 
     */
    private void initGridEvent(int x,int y){
        if(steps==1){
            position = new Coordonnees(x,y);
            /*position.setX(x);
            position.setY(y);*/
            System.out.println(selectedP.getBoard().checkPosition(tailleBoat, position, algt));
            if(selectedP.getBoard().checkPosition(tailleBoat, position, algt)){
                if(algt==Alignment.Horizontal){
                    for(int i=0;i<tailleBoat;i++){
                        imageV[x+i-1][y-1].imageProperty().set(bobato); 
                    }
                }
                else{
                    for(int i=0;i<tailleBoat;i++){
                        imageV[x-1][y+i-1].imageProperty().set(bobato); 
                    }
                }

                acceptBtn.disableProperty().setValue(false);
                instructions.textProperty().setValue("Click 'Accept' to place the next boat");
                System.out.println(y+":"+x);
                steps=2;
            }
            else{
                instructions.textProperty().setValue("Impossible positioning");
            }
        }
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
       bateau.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               placeImageEvent(num, taille, posfinal);
           } 
       });
       grid_select_boat.add(bateau,0,5-pos);
    }
    
    /**
     * 
     * @param num   numero du bateau(permet de différencier 2 bateaux de la meme taille)
     * @param taille taille du bateau
     * @param posfinal position final du bateau 
     */
    private void placeImageEvent(int num, int taille, int posfinal){
        if(steps<=1){
            numBoat=num;
            tailleBoat=taille;
            instructions.textProperty().setValue("Select an alignment then place the boat on the grid");
            effacerFleche(tabCrossImage);
            tabCrossImage[posfinal].visibleProperty().set(true);
            try{
                grid_select_boat.add(tabCrossImage[posfinal],1,posfinal);
            }
            catch(Exception e){}
            steps=1;
        }
    }
    
    /**
     * change la propriété des imagesView correspondant aux bateaux à placer afin de les rendres visibles
     */
    private void rendreImageVisible(){
            grid_select_boat.getChildren().get(6).visibleProperty().set(true);
            grid_select_boat.getChildren().get(7).visibleProperty().set(true);
            grid_select_boat.getChildren().get(8).visibleProperty().set(true);
            grid_select_boat.getChildren().get(9).visibleProperty().set(true);
            grid_select_boat.getChildren().get(10).visibleProperty().set(true);
    }
    
    /**
     * efface la fleche placée à coté du bateau selectionner si on clique sur annuler
     * @param tabCrossImage 
     */
    public void effacerFleche(ImageView[] tabCrossImage){
        for (ImageView imageView : tabCrossImage) {
            if(imageView==null){}
            else imageView.visibleProperty().set(false);
        }
    }
    
    /**
     * le constructeur
     * @param p1
     * @param p2
     * @param primaryStage 
     */
    public BoatPlaceWinController(Player p1,Player p2,Stage primaryStage) {
        this.p1=p1;
        this.p2=p2;
        selectedP = this.p1;
        txtName = selectedP.getName();
        this.primaryStage=primaryStage;
    }
    
    /**
     * permet de passer à la phase de placement du second joueur
     * @throws IOException 
     */
    @FXML
    public void validate() throws IOException{
        if(selectedP.getName()==p1.getName()){
            selectedP = this.p2;
            setWindow();
            rendreImageVisible();
        }
        else{
            Stage sta = new Stage();
            FXMLLoader root = new FXMLLoader(getClass().getResource("BoatShootWin.fxml"));
            root.setController(new BoatShootWinController(p1, p2));
            sta.setScene(new Scene(root.load()));
            sta.show();
            primaryStage.hide();
        }
        ValBtn.disableProperty().setValue(true);
        steps = 0;
    }
    
    /**
     * permet d'annuler le placement d'un bateau (à faire avant d'accepter)
     */
    @FXML
    public void cancel(){
        if(steps==2){
            effacerFleche(tabCrossImage);
            if(algt==Alignment.Vertical){
                for(int i=0;i<tailleBoat;i++){
                    imageV[position.getX()-1][position.getY()+i-1].imageProperty().set(background); 
                }
            }
            else{
                for(int i=0;i<tailleBoat;i++){
                    imageV[position.getX()+i-1][position.getY()-1].imageProperty().set(background); 
                }
            }
            instructions.textProperty().setValue("Please select a boat");
            tailleBoat=0;
            position.setX(0);
            position.setY(0);
            steps=0;
        }
        else{
            instructions.textProperty().setValue("impossible to cancel");
        }
    }
    
    /**
     * permet de valider le placement d'un bateau
     */
    @FXML
    public void accept(){
        if(steps==2){
            effacerFleche(tabCrossImage);
            try{
                if(tailleBoat==2){
                    grid_select_boat.getChildren().get(10).visibleProperty().setValue(false);
                    System.out.println(grid_select_boat.getChildren().get(10));
                }
                else{
                    grid_select_boat.getChildren().get(11-tailleBoat+numBoat).visibleProperty().setValue(false);
                    System.out.println(grid_select_boat.getChildren().get(11-tailleBoat+numBoat));
                }
            }
            catch(Exception e){}
            nbBoat --;
            selectedP.getBoard().placeBoat(selectedP,tailleBoat, position, algt, numBoat);
            position.getX();
            position.getY();
            System.err.println(nbBoat);
            if(nbBoat == 0){
                instructions.textProperty().setValue("Click 'Validate'");
            }
            else{
                instructions.textProperty().setValue("Please select another boat");
                acceptBtn.disableProperty().setValue(true);
            }
            if(nbBoat==0){
                ValBtn.disableProperty().setValue(false);
            }
        }
        steps=0;
    }  
}

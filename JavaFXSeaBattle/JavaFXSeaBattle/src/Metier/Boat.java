/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Guillaume
 */
public abstract class Boat {
    //nom du bateau
    private String boatName;
    //taille du bateau, le nombre de case qu'il occupera
    private int size;
    //la position, la case, initiale du bateau
    private Coordonnees pos;
    //orientation du bateau sur la grille: vertical ou horizontal
    private Alignment alignment; 
    //le nombre de points de vie du bateau
    private IntegerProperty hp = new SimpleIntegerProperty();
    
    //hp properties
    public void setHp(int hp) {
        this.hp.setValue(hp);
    }
    
    public void decrementHp() {
        this.hp.subtract(1);
    }
    
    public IntegerProperty getHp() {
        return hp;
    }

    //name properties
    public String getName(){
        return this.boatName;
    }
    public void setName(String name){
        this.boatName = name;
    }
    
    //Size properties
    public int getSize(){
          return this.size;
      }    
    public void setSize(int size){
        this.size = size;
    }
    
    //pos properties
    public Coordonnees getPos(){
        return this.pos;
    }
    public void setPos(Coordonnees pos){
        this.pos=pos;
    }
    
    //aligment properties
    public Alignment getAlignment(){
        return this.alignment;
    }
    public void setAlignment(Alignment align){
        this.alignment=align;
    }
    
    
}

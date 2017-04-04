/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author gumariot
 */
public class Player {
   // private String name;
   
    private StringProperty name = new SimpleStringProperty();
    private Board board;
    private Carrier ca;
    private Cruiser cu;
    private BattleShip ba;
    private Submarine su;
    private Destroyer de;
    //vieRestante est la somme de tout les HP des bateaux
    private IntegerProperty vieRestante = new SimpleIntegerProperty();
    
    /**
     * Constructeur
     * @param name le nom du joueur
     * @param board le plateau du joueur
     */
    public Player(String name, Board board){
        this.name.set(name);
        this.board = board;
        vieRestante.setValue(17);
    }
    
    //name properties
    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    //board properties
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    
    //toutes les propriétés concernants les classes filles de Boat
    public Carrier getCa() {
        return ca;
    }

    public Cruiser getCu() {
        return cu;
    }

    public BattleShip getBa() {
        return ba;
    }

    public Submarine getSu() {
        return su;
    }

    public Destroyer getDe() {
        return de;
    }

    public void setCa(Carrier ca) {
        this.ca = ca;
    }

    public void setCu(Cruiser cu) {
        this.cu = cu;
    }

    public void setBa(BattleShip ba) {
        this.ba = ba;
    }

    public void setSu(Submarine su) {
        this.su = su;
    }

    public void setDe(Destroyer de) {
        this.de = de;
    }

    /**
     * permet de décrémenter la vie du joueur
     */
    public void decVie(){
        this.vieRestante.setValue(vieRestante.subtract(1).intValue());       
    }

    /**
     * retourne la vie du joueur
     * @return vieRestante
     */
    public IntegerProperty getVieRestante() {
        return vieRestante;
    }
    
    @Override
    public String toString() {
        return "Player{" + "name=" + name + '}';
    }   
}
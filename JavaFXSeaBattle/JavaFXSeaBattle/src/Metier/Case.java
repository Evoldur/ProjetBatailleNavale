/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

/**
 *
 * @author cltouresse
 */
public class Case {
    private Coordonnees pos;
    private int marque;
    
    public Case( Coordonnees pos, int marque ){
        this.pos = pos;
        this.marque = marque;
    }
    
    public Coordonnees getPos(){
        return this.pos;
    }
    public void setPos(Coordonnees pos){
        this.pos = pos;
    }
    
    public int getMarque(){
        return this.marque;
    }
    public void setMarque(int marque){
        this.marque = marque;
    }
    
}

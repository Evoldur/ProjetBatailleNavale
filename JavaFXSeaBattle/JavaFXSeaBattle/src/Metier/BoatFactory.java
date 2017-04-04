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
public class BoatFactory {
    private Carrier ca;
    private Cruiser cr;
    private Destroyer de;
    private BattleShip ba;
    private Submarine su;
    
    /**
     * constructeur de la factory
     */
    public BoatFactory(){    
    }
    
    /**
     * Crée un Boat pour un Joueur donné
     * @param p le joueur pour qui on crée le bateau
     * @param size  la taille du bateau à créer
     * @param pos   la position du bateau à créer
     * @param alignment l'alignement du bateau à créer
     * @param num   le numéro du bateau à créer (dans le cas où 2 bateaux ont la meme taille)
     * @return vrai ou faux selon si le bateau à bien été crée ou non
     */
    public boolean createBoat(Player p, int size, Coordonnees pos, Alignment alignment, int num){
        switch(size){
            case 5:
                this.ca= new Carrier(5,pos,alignment);
                p.setCa(ca);
                break;
            case 4:
                this.ba= new BattleShip(4, pos, alignment);
                p.setBa(ba);
                break;
            case 3:
                if(num == 0){
                    this.cr= new Cruiser(3, pos, alignment);
                    p.setCu(cr);
                }
                else{
                    this.su= new Submarine(3, pos, alignment);
                    p.setSu(su);
                }
                break;
            case 2:
                this.de= new Destroyer(2, pos, alignment);
                p.setDe(de);
                break;
            default:
                System.out.println("Error : Size not reconised");
                return false;
        }
        return true;
    }
}
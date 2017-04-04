
package Metier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author guforce
 */
public class Board {
    private int nbBateau;
    private BoatFactory bf;
    private Case[][] tab;// = new Case[9][9];

    /**
     * constructeur de Board
     */
    public Board(){
        this.nbBateau=5;
        tab = new Case[10][10];
        tab = initialiserTab(tab);
    }
    
    /**
     * Initialise toutes les marques des cases du plateau à 0
     * @param tab le tableau de cases à remplir
     * @return tab le tableau de cases rempli
     */
    public Case[][] initialiserTab(Case[][]tab){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                Coordonnees pos = new Coordonnees(i,j);
                tab[i][j] = new Case(pos, 0);
            }
        }
        return tab;
    }
    
    /**
     * permet à un joueur de placer un bateau
     * @param p le joueur qui place le bateau (à l'origine devait permettre au joueur
     * de savoir combien de pv avait son bateau au fur et a mesure de la partie mais 
     * cette fonctionnalité à été avorté par manque de temps)
     * @param size  taille du bateau
     * @param pos   position (X et Y) de la case cliquée pour poser le bateau
     * @param alignment alignement choisit pour la bateau (horizontal ou vertical)
     * @param num numero du bateau (utilise si 2 bateaux de meme taille);
     */
    public void placeBoat(Player p, int size, Coordonnees pos, Alignment alignment, int num){
        this.bf = new BoatFactory();
        
        
        if(!bf.createBoat(p, size, pos, alignment, num)){
            System.out.println("Error : failed to create the boat");
        }
        else{
            if(alignment == Alignment.Horizontal){
                for(int i=pos.getX();i<=(pos.getX()+(size-1));i++){
                    tab[i-1][pos.getY()-1].setMarque(1);
                } 
            }
            if(alignment == Alignment.Vertical){
                for(int i=pos.getY();i<=(pos.getY()+(size-1));i++){
                    tab[pos.getX()-1][i-1].setMarque(1);
                } 
            }
            System.out.println("Boat Placed");
        }         
    }
    
    /**
     * Permet de savoir si le bateau peut etre placé ou non
     * @param size  taille du bateau
     * @param pos position(X et Y) de la case cliquée pour poser le bateau
     * @param alignement alignement choisit pour la bateau (horizontal ou vertical)
     * @return boolean vrai ou faux selon si le bateau peut etre placé ou non
     */
    public boolean checkPosition(int size, Coordonnees pos, Alignment alignement){
        int posize;
        //fin optionnel
        if(tab[pos.getX()-1][pos.getY()-1].getMarque()==1){
            System.out.println("Error : Case already used 1");
            return false;
        }
        else{
            if(alignement == Alignment.Horizontal){
                posize = pos.getX()+(size-1);
                if(posize>10){
                    System.out.println("Error : Boat size go over Board limit, "
                                        + "select a smaller x coordonate");
                    return false;
                }
                for(int i=pos.getX();i<=posize;i++){
                    if(tab[i-1][pos.getY()-1].getMarque()==1){
                        System.out.println("Error : There's a boat placed in x axis");
                        return false;
                    }
                } 
            }
            else{//(alignement == Alignment.Vertical){
                posize = pos.getY()+(size-1);
                if(posize>10){
                    System.out.println("Error : Boat size go over Board limit, "
                                        + "select a higher y coordonate");
                    return false;
                }
                for(int i=pos.getY();i<=posize;i++){
                    if(tab[pos.getX()-1][i-1].getMarque()==1){
                        System.out.println("Error : There's a boat placed in Y axis");
                        return false;
                    }
                }
            }
        }
        return true;
    }
        
    /**
     * permet de savoir la marque d'une case à une position donnée
     * @param pos   la position de la case dont on souhaite connaitre la valeur de la mmarque
     * @return int : le numéro de la marque
     */
    public int checkCase(Coordonnees pos){
        return tab[pos.getX()-1][pos.getY()-1].getMarque();
    }
    
    /**
     * permet de savoir si on peux tirer sur une case ou non
     * @param pos   la position sur laquelle on souhaite tirer
     * @return boolean vrai ou faux
     */
    public boolean checkShoot(Coordonnees pos){
        if(pos.getX()>10 || pos.getX()<1){
            System.out.println("Error : x coordonate out of bound");
            return false;
        }
        if(pos.getY()>10 || pos.getY()<1){
            System.out.println("Error : y coordonate out of bound");
            return false;
        }
        if(tab[pos.getX()-1][pos.getY()-1].getMarque()>=2){
            System.out.println("Shot cancelled: position already tried");
            return false;
        }
        return true;
    }
    
    
    /**
     * permet de tirer sur une case et modifie la marque de la case sur laquelle on a tiré
     * @param pos   la position de la case ciblée
     * @return vrai ou faux selon si on a touché ou non
     */
    public boolean shootPosition(Coordonnees pos){
        // La Case ne contient rien, le tir fonctionne mais rien ne se passe
        if(tab[pos.getX()-1][pos.getY()-1].getMarque()==0){
            System.out.println("SPLUUUUCH!");
            tab[pos.getX()-1][pos.getY()-1].setMarque(2);
            return false;
        }
        // La Case contient un bateau, le tir fonctionne et on décrément la vie du boat touché 
        else{    
            System.out.println("Boat touched");
        }
        tab[pos.getX()-1][pos.getY()-1].setMarque(3);
        return true;                           
    }

    @Override
    public String toString() {
        return "Board{" + "nbBateau=" + nbBateau + ", bf=" + bf + ", tab=" + tab + '}';
    }   
}
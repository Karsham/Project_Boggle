package boggle.mots ;

import boggle.*; 
import java.util.* ;
import java.io.*;

/** 
 * La classe GrilleLettres permet de mettre en place le plateau de jeu, à savoir 16 lettres, et donc 16 faces visibles de 16 dés.
 */
public class GrilleLettres {

    /**
     * La constante définissant le nombre de <code>De</code> du plateau de jeu : 6.
     */
    protected final int NOMBRE_DES = 16 ;

    /**
     * La constante définissant le nombre de faces d'un <code>De</code> : 6.
     */
    protected final int NOMBRE_FACES = 6 ;

    /**
     * La plateau de <code>De</code> de la <code>GrilleLettres</code>, de la taille de NOMBRE_DES.
     */
    protected De[] des = new De[NOMBRE_DES];

    /** 
     * Le constructeur <code>GrilleLettres</code> qui permet d'initialiser les <code>De</code> du jeu.
     */
    public GrilleLettres(){
        this.initialiserDes();
    }

    /**
     * Initialise les <code>De</code> du jeu.
     */
    public void initialiserDes(){
        try{
            ImportFile dataDes = new ImportFile(new File("./").getAbsolutePath() + "/config/des-4x4.csv");
            String [][] datas = dataDes.getResultTab(NOMBRE_DES, NOMBRE_FACES);

            for (int i=0; i<NOMBRE_DES; i++) {
                des[i] = new De(datas[i]);
            }

        }catch(FileNotFoundException f){
            System.out.println(f.getMessage());
        }
    }

    /**
     * Retourne les faces visibles du jeu, à partir des <code>De</code> initialisés à la construction.
     * 
     * @return Un tableau des faces visibles du plateau de jeu.
     */
    public void getFacesVisibles(){
        for (int i=0; i<NOMBRE_DES; i++) {
            System.out.print( des[i].getFaceVisible() + " ");
            if (((i+1) % 4) == 0) {
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        GrilleLettres grille = new GrilleLettres();
        grille.getFacesVisibles();
    }

}

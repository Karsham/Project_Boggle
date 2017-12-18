package boggle.mots ;

import boggle.*; 
import java.util.* ;
import java.io.*;

/** La classe GrilleLettres permet de stocker de façon compacte et
 * d'accéder rapidement à un ensemble de mots.*/
public class GrilleLettres {

    protected final int NOMBRE_DES = 16 ;
    protected final int NOMBRE_FACES = 6 ;

    protected De[] des = new De[NOMBRE_DES];

    public GrilleLettres(){
        this.initialiserDes();
    }

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

    public String[] getFacesVisibles(){
        String[] facesVisibles = new String[NOMBRE_DES];
        for (int i=0; i<NOMBRE_DES; i++) {
            facesVisibles[i] = des[i].getFaceVisible() ;
        }
        return facesVisibles;
    }

    public static void main(String[] args) {
        GrilleLettres grille = new GrilleLettres();
        String[] tab = grille.getFacesVisibles();
        for (int i=0; i<tab.length; i++) {
            System.out.print(tab[i]);
            if (((i+1) % 4) == 0) {
                System.out.println();
            }
        }
    }

}

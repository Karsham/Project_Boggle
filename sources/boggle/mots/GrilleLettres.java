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
    * Les indices des lettre d'un mot dans la grille
    */
    protected ArrayList<Integer> indicesDuMotTrouve;

    /**
     * Le constructeur <code>GrilleLettres</code> qui permet d'initialiser les <code>De</code> du jeu.
     */
    public GrilleLettres(){
        this.initialiserDes();
    }

    public ArrayList<Integer> getIndices() {
      return this.indicesDuMotTrouve;
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

        } catch(FileNotFoundException f) {
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

    /**
    * Méthode vérifiant qu'un mot est bien dans la grille et construit de proche en proche,
    * sans utiliser 2 fois le même dé.
    *
    * @param mot le mot à vérifier
    * @param indicesLettres Indices des lettres du mot dans la grille
    */
    public void estMotValide(String mot, ArrayList<Integer> indicesLettres) {

      // Condition d'arrêt: le mot a été trouvé
      if(mot.length() != 0) {

        // Si indicesLettres est vide, on recherche toutes les 1ières lettres du mot
        if(indicesLettres.size() == 0) {

          String premiereLettre = mot.substring(0, 1).toUpperCase();

          for(int i = 0; i < NOMBRE_DES; i++) {
            if(des[i].getFaceVisible().equals(premiereLettre.toUpperCase())) {

                indicesLettres = new ArrayList<Integer>();
                indicesLettres.add(i);
                // System.out.println("Premiere lettre - - - - - - - - - - - -");
                // System.out.println("mot: " + mot.substring(1));
                // System.out.println("indicesLettres: " + indicesLettres);
                estMotValide(mot.substring(1), indicesLettres);

            }
          }
        }
        else {
        // Recherche des indices adjacents au dernier indice trouvé si indicesLettres n'est pas vide

          // Les indices adjacents à la dernière lettre
          int[] indicesAdjacents = GrilleLettres.indicesAdjacents(indicesLettres.get(indicesLettres.size() - 1));
          int indicesAdjacentsLength = indicesAdjacents.length;

          // Marquer les indices déjà parcourus de indicesAdjacents
          for(int i = 0; i < indicesAdjacentsLength; i++) {
            if(indicesLettres.contains(indicesAdjacents[i])) {
              indicesAdjacents[i] = -1;
            }
          }

          // La lettre à trouver suivante
          String lettreSuivante;
          String motSuivant;
          if(mot.length() == 1) {
            lettreSuivante = mot.toUpperCase();
            motSuivant = mot.substring(1).toUpperCase();
          }
          else {
            lettreSuivante = mot.substring(0, 1).toUpperCase();
            motSuivant = mot.substring(1).toUpperCase();
          }

          // Lancement des récursions
          for(int i = 0; i < indicesAdjacentsLength; i++) {

            ArrayList<Integer> nouvelIndicesLettres = new ArrayList<Integer>();
            nouvelIndicesLettres.addAll(indicesLettres);
            if(indicesAdjacents[i] != -1) {

              if(des[indicesAdjacents[i]].getFaceVisible().equals(lettreSuivante)) {

                nouvelIndicesLettres.add(indicesAdjacents[i]);
                // System.out.println("--------------------------------------");
                // System.out.println("mot: " + motSuivant);
                // System.out.println("indicesLettres: " + nouvelIndicesLettres);
                estMotValide(motSuivant, nouvelIndicesLettres);
              }
            }
          }
        }
      }
      else {
        this.indicesDuMotTrouve = indicesLettres;
      }
    }

    public int nbreDePointsDuMot(String mot) {

      int l = mot.length();

      switch(l) {
        case 0:
        case 1:
        case 2:
          return 0;
        case 3:
        case 4:
          return 1;
        case 5:
          return 2;
        case 6:
          return 3;
        case 7:
          return 5;
        default:
          return 11;
      }
    }


    /**
    * Méthode permettant de déterminer quels sont les indices adjacents d'un indice
    * en particulier dans la grille des lettres
    *
    * @param indice l'indice dans la grille pour lequel on recherche les indices adjacents.
    * @return le tableau contenant les indices adjacents.
    */
    private static int[] indicesAdjacents(int indice) {

      // A REFAIRE
      switch(indice) {
        case 0:
          return new int[] {1, 4, 5};
        case 1:
          return new int[] {0, 2, 4, 5, 6};
        case 2:
          return new int[] {1, 3, 5, 6, 7};
        case 3:
          return new int[] {2, 7, 7};
        case 4:
          return new int[] {0, 1, 5, 8, 9};
        case 5:
          return new int[] {0, 1, 2, 4, 6, 8, 9, 10};
        case 6:
          return new int[] {1, 2, 3, 5, 7, 9, 10, 11};
        case 7:
          return new int[] {2, 3, 6, 10, 11};
        case 8:
          return new int[] {4, 5, 9, 12, 13};
        case 9:
          return new int[] {4, 5, 6, 8, 10, 12, 13, 14};
        case 10:
          return new int[] {5, 6, 7, 9, 11, 13, 14, 15};
        case 11:
          return new int[] {6, 7, 10, 14, 15};
        case 12:
          return new int[] {8, 9, 13};
        case 13:
          return new int[] {8, 9, 10, 12, 14};
        case 14:
          return new int[] {9, 10, 11, 13, 15};
        default:
          return new int[] {10, 11, 14};
      }
    }



    // public static void main(String[] args) {
    //     GrilleLettres grille = new GrilleLettres();
    //     grille.getFacesVisibles();
    // }

}

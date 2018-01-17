package boggle.mots ;

import boggle.*;
import java.util.* ;
import java.io.*;

/**
 * La classe GrilleLettres permet de mettre en place le plateau de jeu, à savoir 16 lettres, et donc 16 faces visibles de 16 dés.
 */
public class GrilleLettres {

    /**
     * La constante définissant le nombre de faces d'un <code>De</code> : 6.
     */
    protected final int NOMBRE_FACES = 6 ;

    /**
     * Nombre de <code>De</code> du plateau de jeu
     */
    protected int nbDes;

    /**
    * Taille de la grille
    */
    protected int tailleGrille;

    /**
     * La plateau de <code>De</code> de la <code>GrilleLettres</code>, de la taille de NOMBRE_DES.
     */
    protected De[] des;

    /**
    * Les indices des lettre d'un mot dans la grille
    */
    protected ArrayList<Integer> indicesDuMotTrouve;

    /**
     * Le constructeur <code>GrilleLettres</code> qui permet d'initialiser les <code>De</code> du jeu.
     * @param n la taille de la grille (qui contiendra n*n dés)
     */
    public GrilleLettres(int n){
        this.tailleGrille = n;
        this.nbDes = n * n;
        this.des = new De[this.nbDes];
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
            String [][] datas = dataDes.getResultTab(this.nbDes, NOMBRE_FACES);

            for(int i = 0; i < this.nbDes; i++) {
                des[i] = new De(datas[i]);
            }

        } catch(FileNotFoundException f) {
            System.out.println(f.getMessage());
        }
    }

    /**
     * Retourne les faces visibles du jeu, à partir des <code>De</code> initialisés à la construction.
     */
    public void getFacesVisibles(){
        for (int i=0; i<this.nbDes; i++) {
            System.out.print( des[i].getFaceVisible() + " ");
            if (((i+1) % this.tailleGrille) == 0) {
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

          for(int i = 0; i < this.nbDes; i++) {
            if(des[i].getFaceVisible().equals(premiereLettre.toUpperCase())) {

                indicesLettres = new ArrayList<Integer>();
                indicesLettres.add(i);

                estMotValide(mot.substring(1), indicesLettres);

            }
          }
        }
        else {
        // Recherche des indices adjacents au dernier indice trouvé si indicesLettres n'est pas vide

          // Les indices adjacents à la dernière lettre
          int[] indicesAdjacents = GrilleLettres.indicesAdjacents(indicesLettres.get(indicesLettres.size() - 1), this.tailleGrille);
          // System.out.println("Indices adjacents pour " + indicesLettres.get(indicesLettres.size() - 1));
          // for(int i : indicesAdjacents) {
          //   System.out.println(i);
          // }

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
              //System.out.println(indicesAdjacents[i]);
              if(des[indicesAdjacents[i]].getFaceVisible().equals(lettreSuivante)) {
                // Vérifier que la lettre n'est pas déjà prise dans le mot
                if(!indicesLettres.contains(indicesAdjacents[i])) {
                  nouvelIndicesLettres.add(indicesAdjacents[i]);
                  estMotValide(motSuivant, nouvelIndicesLettres);
                }

              }
            }
          }
        }
      }
      else {
        this.indicesDuMotTrouve = indicesLettres;
      }
    }

    /**
    * Donne le nombre de points gagnés par le joueur pour un mot.
    * @param mot le mot valide donné par le joueur.
    * @return le nombre de points gagnés.
    */
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
    * @param n la taille de la grille (nombre de dés d'un côté du carré).
    * @return le tableau contenant les indices adjacents.
    */
    private static int[] indicesAdjacents(int indice, int n) {

      //System.out.println("Indice: " + indice);

        // Les coins du carré
        if(indice == 0) {
          return new int[] {1, n, n + 1};
        }
        else if(indice == n - 1) {
          return new int[] {n - 2, 2 * n - 1, 2 * n - 2};
        }
        else if(indice == n * n - n) {
          return new int[] {n * n - 2 * n , n * n - 2 * n + 1, n * n - n + 1};
        }
        else if(indice == n * n - 1) {
          return new int[] {n * n - n - 2, n * n - n - 1, n * n - 2};
        }
        // Bord haut
        else if(indice >= 1 && indice < n - 1) {
          return new int[] {indice - 1, indice + 1, indice + n - 1, indice + n, indice + n + 1 };
        }
        // Bord gauche
        else if(indice % n == 0 && indice != 0 && indice != n * n - n) {
          return new int[] {indice - n, indice - n + 1, indice + 1, indice + n, indice + n + 1 };
        }
        // Bord bas
        else if(indice > n * n - n && indice < n * n - 1) {
          return new int[] {indice - 1, indice - n - 1, indice - n, indice - n + 1, indice + 1};
        }
        // Bord droit
        else if(indice % n == n - 1) {
          return new int[] {indice - n, indice - n - 1, indice - 1, indice + n - 1, indice + n};
        }
        // A l'intérieur du carré
        else {
          return new int[] {indice - n - 1, indice - n, indice - n + 1, indice - 1, indice + 1, indice + n - 1, indice + n, indice + n + 1};
        }
    }



    // public static void main(String[] args) {
    //     GrilleLettres grille = new GrilleLettres();
    //     grille.getFacesVisibles();
    // }

}

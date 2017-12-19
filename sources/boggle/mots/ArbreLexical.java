package boggle.mots ;

import java.util.List ;
import boggle.ImportFile;

/** La classe ArbreLexical permet de stocker de façon compacte et
 * d'accéder rapidement à un ensemble de mots.*/
public class ArbreLexical {

    public static final int TAILLE_ALPHABET = 26 ;
    private boolean estMot ; // vrai si le noeud courant est la fin d'un mot valide
    private ArbreLexical[] fils; // les sous-arbres

    /** Crée un arbre vide (sans aucun mot) */
    public ArbreLexical() {
        this.estMot = false;
        this.fils = new ArbreLexical[TAILLE_ALPHABET] ;
    }


    /** Indique si le noeud courant est situé à l'extrémité d'un mot
     * valide */
    public boolean estMot() {
      return estMot ;
    }

    /**
    * Convertit un caractère en sa position dans l'alphabet
    * @param c le caractère à convertir
    * @return la position dans l'alphabet
    */
    private static int convert(char c) {
      return (Character.getNumericValue(Character.toLowerCase(c)) - 10);
    }


    /**
    * Ajoute un mot dans l'arbre
    * @param word le mot à ajouter
    * @return true si l'ajout a été effectué, false sinon
    */
    public boolean ajouter(String word) {

      int l = word.length();

      char[] charWord;
      charWord = word.toCharArray();

      int position = convert(charWord[0]);

      // Si la position ne contient pas d'arbre, on en crée un
      if(this.fils[position] == null) {
        this.fils[position] = new ArbreLexical();
        this.fils[position].estMot = false;
      }

      if(l == 1) {
        // Arrêt de la fonction récursive
        if(this.fils[position].estMot() == true) {
          // Le mot existe déjà, on ne le rajoute pas
          return false;
        }
        else {
          // Ajout du mot
          this.fils[position].estMot = true;
          return true;
        }
      }
      else {
        // On passe à l'étape suivante (c'est à dire au fils de this,
        // en transmetteant un mot amputé d'une lettre
        return this.fils[position].ajouter(word.substring(1));
      }
    }

    /**
    * Teste si l'arbre lexical contient le mot spécifié.
    * @param word le mot à rechercher
    * @return true si le mot est contenu dans l'arbre, false sinon.
    */
    public boolean contient(String word) {

      int l = word.length();
      char[] charWord;
      charWord = word.toCharArray();

      int position = convert(charWord[0]);

      // Si on est à la dernière lettre de word, on vérifie si estMot = true
      if(l == 1) {
        if(this.fils[position] == null) {
          return false;
        }
        else {
          return this.fils[position].estMot();
        }
      }

      // Si la position est contenue dans this, on passe à ce fils de this
      // Sinon, on retourne false
      if(this.fils[position] == null) {
        return false;
      }
      else {
        return this.fils[position].contient(word.substring(1));
      }

    }

    /** Ajoute à la liste <code>resultat<code> tous les mots de
     * l'arbre commençant par le préfixe spécifié.
     * @return <code>true</code> si <code>resultat</code> a été
     * modifié, <code>false</code> sinon.*/
    public boolean motsCommencantPar(String prefixe, List<String> resultat) {
        // à compléter
        return false ;
    }

    /** Crée un arbre lexical qui contient tous les mots du fichier
     * spécifié. */
    public static ArbreLexical lireMots(String fichier) {

      String chemin = new File('./').getAbsolutePath();

      String dico = chemin.substring(0, chemin.length() - 1) + "config/dict-fr.txt";

      try {
        ImportFile ifdico = new ImportFile(dico);
      }
      catch(Exception e) {
        System.out.println("Erreur : "+e.getMessage());
      }

      







        return null ;
    }


    public static void main(String [] args) {

      ArbreLexical a = new ArbreLexical();
      a.ajouter("les");
      System.out.println(a.contient("les"));
      System.out.println(a.contient("lese"));
      System.out.println(a.contient("ls"));

    }
}

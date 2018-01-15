package boggle.mots ;

import java.util.* ;
import boggle.ImportFile;
import java.io.*;
import java.text.*;

/** La classe ArbreLexical permet de stocker de façon compacte et
 * d'accéder rapidement à un ensemble de mots.*/
public class ArbreLexical {

    public static final int TAILLE_ALPHABET = 26 ;
    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private boolean estMot ; // vrai si le noeud courant est la fin d'un mot valide
    private ArbreLexical[] fils; // les sous-arbres

    /** Crée un arbre vide (sans aucun mot) */
    private ArbreLexical() {
        this.estMot = false;
        this.fils = new ArbreLexical[TAILLE_ALPHABET] ;
    }

    public static ArbreLexical getInstance(String fichier) {
      ArbreLexical a = new ArbreLexical();
      a = a.lireMots(fichier);
      return a;
    }


    /**
    * Indique si l'arbre lexical est un mot
    * @return true si c'est le cas, false sinon
    */
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
    * Fonction convertissant un mot en minuscules, sans accets ou caractères spéciaux
    * @param word le mot à transformer
    * @return le mot convertit, ou une chaîne vide s'il ne peut être convertit
    */
    public static String normalize(String word) {

      word = Normalizer.normalize(word, Normalizer.Form.NFD);
      word = word.replaceAll("[^\\p{ASCII}]", "");
      // Met en minuscules
      word = word.toLowerCase();
      // Supprime les caractères spéciaux
      if(word.matches("^[a-z]+$")) {
        return word;
      }
      else {
        return "";
      }
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

      // Normalisation du mot
      word = normalize(word);
      //System.out.println(word);

      int l = word.length();

      if(l == 0) {
        return false;
      }

      char[] charWord;
      charWord = word.toCharArray();

      int position = convert(charWord[0]);
      // System.out.println(position);

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
        // System.out.println(this.fils[position]);
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

      ArbreLexical a = null;

      // La liste des débuts de mots
      ArrayList<String> debutsMots = new ArrayList<String>();

      // Normalisation de la chaîne à rechercher
      prefixe = normalize(prefixe);
      if(prefixe.length() == 0) {
        return false;
      }

      // On se positionne sur l'arbre lexical de la dernière lettre du préfixe
      try {
        a = this.getTo(prefixe);
      }
      catch(Exception e) {
        // Cas où le préfixe n'est pas contenu dans l'arbre
        return false;
      }

      // Si a est un mot, on le rajoute dans la liste
      if(a.estMot()) {
        resultat.add(prefixe);
      }

      // Dans tous les cas, on rajoute le préfixe à la liste des débuts de mots
      debutsMots.add(prefixe);

      // On cherche tous les mots de this commençant par le mot de résultat
      resultat = this.searchAllWords(debutsMots, resultat);

      System.out.println(resultat);


      // Si on en a trouvé, on retourne true, sinon, false
      if(resultat.size() != 0) {
        return true;
      }
      else {
        return false;
      }

    }

    /**
    * Méthode qui cherche tous les mots contenus dans un arbre
    * @param a l'arbre dans lequel chercher
    * @param debutsMots la liste des commencements de mots possibles
    * @param motsFinis la liste des mots complets
    * @return la liste des mots finis
    */
    private List<String> searchAllWords(List<String> debutsMots, List<String> motsFinis) {

      int l = debutsMots.size();
      System.out.println("l: " + l);

      // Arbre lexical suivant
      ArbreLexical temp = null;

      // Liste temporaire de débuts de mots trouvés
      ArrayList<String> tempDebutsMots = new ArrayList<String>();

      // Condition d'arrêt: debutsMots est vide => retourner motsFinis
      if(l == 0) {
        return motsFinis;
      }

      // Pour chaque mot de debutsMots, on recherche l'arbre correspondant
      for(int j = 0; j < l; j++) {

        String word = debutsMots.get(j);

        System.out.println("word: " + word);

        // Aller à l'arbre correspondant
        try {
          temp = this.getTo(word);
        }
        catch(Exception e) {
          // Rien à faire ici, le mot est forcément contenu dans l'arbre
        }

        // Aller à toutes les lettres suivantes.
        for(int i = 0; i < 26; i++) {
          // Si l'arbre fils est null, on ne fait rien.
          if(temp.fils[i] != null) {
            // Si l'arbre résultant est un mot
            if(temp.fils[i].estMot()) {
              // On le rajoute à motsfinis
              String nouveauMotFini = word + alphabet.substring(i, i + 1);
              System.out.println("nouveauMotFini: " +  nouveauMotFini);
              motsFinis.add(nouveauMotFini);
            }
            // Si ça n'est pas un mot
            else {
              // On le rajoute à debutsMots
              String nouveauDebutDeMot = word + alphabet.substring(i, i + 1);
              System.out.println("nouveauDebutDeMot: " +  nouveauDebutDeMot);
              tempDebutsMots.add(nouveauDebutDeMot);
            }
          }
        }
      }

      // On remplace l'ancienne liste de débuts de mots qui viennent d'être examinés
      // par la nouvelle liste trouvée
      debutsMots = tempDebutsMots;

      // Lancer searchAllWords avec les 2 nouvelles listes
      return this.searchAllWords(debutsMots, motsFinis);

    }



    /**
    * Se positionne à la dernière lettre de word et renvoie l'arbre lexical correspondant
    * @param word le mot à chercher
    * @return l'arbre lexical correspondant à la dernière lettre de word
    * @throws Exception si le mot n'existe pas dans l'arbre
    */
    private ArbreLexical getTo(String word) throws Exception {

      int l = word.length();
      char[] charWord;
      charWord = word.toCharArray();

      int position = convert(charWord[0]);


      if(this.fils[position] == null) {
        throw new Exception("Mot qui n'existe pas");
      }
      else {
        if(l == 1) {
          return this.fils[position];
        }
        else {
          return this.fils[position].getTo(word.substring(1));
        }
      }
    }

    /**
    * Crée un arbre constitué des mots lus dans un fichier
    * @param fichier le fichier à lire
    * @return l'ArbreLexical construit
    */
    private ArbreLexical lireMots(String fichier) {

      ArbreLexical a = new ArbreLexical();
      int cpt = 0;
      String chemin = new File("./").getAbsolutePath();
      String dico = chemin.substring(0, chemin.length() - 1) + fichier;

      BufferedReader br = null;

      try {

        br = new BufferedReader(new FileReader(dico));
        String line;
        while((line = br.readLine()) != null) {

          // Normalisation du mot
          String word = normalize(line);
          if(word.length() != 0) {
            a.ajouter(word);
          }
          else {
            continue;
          }
          cpt++;
        }

      }
      catch(Exception e) {
        e.printStackTrace();
      }
      finally {
        try {
          br.close();
        }
        catch(Exception e) {
          e.printStackTrace();
        }
      }

      System.out.println(cpt + " mots ajoutés.");

        return a ;
    }

    // public static void main(String [] args) {
    //
    //   ArbreLexical a = getInstance("config/dict-fr.txt");
    //
    //   System.out.println(a.fils[1]);
    //
    //   System.out.println(a.contient("dans"));
    //
    //
    // }

}

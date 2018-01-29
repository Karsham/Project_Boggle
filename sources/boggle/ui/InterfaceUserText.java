package boggle.ui;

import boggle.*;
import boggle.mots.*;
import boggle.ui.*;
import boggle.jeu.*;
import java.util.* ;
import java.io.*;

/**
 * La classe InterfaceUserText fournit une interface texte au joueur.
 */
public class InterfaceUserText {

	/**
     * Exécute le jeu.
     */
	private static void jeu(){
		Game game = new Game();

	    // Initialiser les joueurs
	    game.initialiseJoueurs();

	    // Jeu
	    while(!game.estFini()) {
	      game.jouer(game.nextJoueur());
	    }

	    game.finDeJeu();
	}

	/**
     * Affiche une grille de jeu.
     */
	private static void grille(){
		GrilleLettres grille = new GrilleLettres(5, "config/des-5x5.csv");
        grille.getFacesVisibles();
	}

	/**
     * Affiche les règles à partir d'un fichier txt.
     */
	private static void regles(){
		String absolutePath = new File("./").getAbsolutePath();
		String path = absolutePath.substring(0, absolutePath.length()-1) + "config/regles.txt";
		try{
			ImportFile regles = new ImportFile( path );
			System.out.println( regles.getResult() );

		}catch( Exception e ){
			System.out.println("Erreur : "+e.getMessage());
		}

	}

	/**
     * Affiche le menu, et exécute la commande demandée.
		 * @return le choix du joueur.
     */
	public static int menu(){
		System.out.println("Que voulez-vous faire ?\n");

		System.out.println("1. Jouer");
		System.out.println("2. Afficher une grille");
		System.out.println("3. Voir les règles");
		System.out.println("4. Quitter\n");

		System.out.print("Tapez le chiffre pour le choix voulu : ");
		int choice = Clavier.readInt();

		System.out.print("\033[H\033[2J");
		if (choice == 1){
			jeu();
		}else if(choice == 2){
			grille();
		}else if(choice == 3){
			regles();
		}else if(choice == 4){
			System.out.println("AU REVUAR !");
		}else{
			System.out.println("BAD CHOICE ! Entrez un chiffre correspondant à ceux du menu s'il vous plaît. Merci ^^");
		}

		return choice;
	}

}

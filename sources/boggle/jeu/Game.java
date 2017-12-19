package boggle.jeu;

import boggle.*;
import boggle.ui.*;
import java.io.*;

/** La classe Game permet d'exécuter le cycle de jeu.
 */
public class Game {

	/**
     * Exécute le programme Project_Boogle.
     */
	public static void execute(){
		int choix;

		System.out.print("\033[H\033[2J");
		System.out.println("Bienvenue dans Project_Boggle.\n");		
		do{										
			choix = InterfaceUserText.menu();
			System.out.print("\nAppuyez sur une touche pour continuer...");
			Clavier.readString();
			System.out.print("\033[H\033[2J");
		}while(choix != 4);
	}

	/**
     * Le main qui exécute le programme.
     */
	public static void main(String[] args) {
		Game.execute();
	}

}

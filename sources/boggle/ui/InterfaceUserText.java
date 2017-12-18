package boggle.ui;

import boggle.*;
import boggle.mots.*;
import boggle.ui.*;
import java.util.* ;
import java.io.*;

/** 
 * La classe InterfaceUserText fournit une interface texte au joueur.
 */
public class InterfaceUserText {

	int choice = 0;

	public void grille(){
		GrilleLettres grille = new GrilleLettres();
        grille.getFacesVisibles();
	}

	public void regles(){
		String absolutePath = new File("./").getAbsolutePath();
		String path = absolutePath.substring(0, absolutePath.length()-1) + "config/regles.txt";
		try{
			ImportFile regles = new ImportFile( path );
			System.out.println( regles.getResult() );
			
		}catch( Exception e ){
			System.out.println("Erreur : "+e.getMessage());
		}
		
	}

	public void menu(){
		System.out.println("Que voulez-vous faire ?\n");

		System.out.println("1. Afficher une grille");
		System.out.println("2. Voir les règles");
		System.out.println("3. Quitter\n");

		System.out.print("Tapez le chiffre pour le choix voulu : ");
		choice = Clavier.readInt();

		if (choice == 1){
			grille();
		}else if(choice == 2){
			regles();
		}
	}

	public void execute(){
		System.out.println("Bienvenue dans Project_Boggle.\n");
		while(choice != 3){
			menu();
		}
	}

}

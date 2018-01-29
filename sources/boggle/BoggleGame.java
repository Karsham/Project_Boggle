package boggle;

import boggle.jeu.Game;
import boggle.ui.*;
import boggle.mots.*;

import java.util.Properties;
import java.io.BufferedReader;
import java.io.FileReader;

/**
* Classe permettant de lancer le jeu.
*
*/
public class BoggleGame {
    public static void main(String [] args) {
        int gameContinue;

        System.out.print("\033[H\033[2J");
        System.out.println("Bienvenue dans Project_Boggle.\n");
        do{
            gameContinue = InterfaceUserText.menu();
            System.out.print("\nAppuyez sur une touche pour continuer...");
            Clavier.readString();
            System.out.print("\033[H\033[2J");
        }
        while(gameContinue != 4);
    }
}
package boggle;

import boggle.jeu.Game;
import boggle.mots.*;

public class BoggleGame {

  public static void main(String [] args) {

    // Initialiser le jeu
    Game game = new Game();

    // Initialiser les joueurs
    game.initialiseJoueurs();

    // Jeu
    while(!game.estFini()) {
      game.jouer(game.nextJoueur());
    }

    game.finDeJeu();


  }
}

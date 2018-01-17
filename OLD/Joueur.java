package boggle.jeu;

import boggle.*;
import boggle.mots.*;
import boggle.ui.*;
import java.util.* ;
import java.io.*;

/**
 * La classe Joueur permet de créer un joueur et de répertorier ses statistiques.
 */
public class Joueur {

	/**
	 * Le nom du <code>Joueur</code>.
	 */
	private String nom;

	/**
	 * Le score du <code>Joueur</code>, un entier positif.
	 */
	private int score;

	/**
     * Le constructeur <code>Joueur</code> qui permet d'initialiser les statistiques de celui-ci.
     *
     * @param nom
     *				Le nom à donner au joueur.
     */
	public Joueur(String nom){
		this.nom = nom;
		score = 0;
	}

	/**
     * La fonction qui permet de connaître le score du <code>Joueur</code>.
		 * @return int
     */
	public int getScore(){
		return this.score;
	}

	/**
     * La fonction qui permet d'ajouter des points au score du <code>Joueur</code>.
     *
     * @param points
     *				Le nombre de points à ajouter au score du joueur.
     */
	public void addPoints(int points){
		score += points;
	}

}

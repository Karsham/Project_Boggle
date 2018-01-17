package boggle.jeu;

import boggle.*;
import boggle.ui.*;
import java.io.*;
import java.util.*;

import boggle.mots.*;

/** La classe Game permet d'exécuter le cycle de jeu.
 */
public class Game {

	/** La taille de la grille du jeu */
	private static final int N = 4;

	/** Le nombre de tours maximal */
	private static final int NBTOURS = 6;

	/** Fichier de configuration */
	private static final String CONFIG_FILE = "config/regles-4x4.config";

	/** Taille min des mots */
	private int taille_min;

	/** Fichier de config des dés */
	private String config_des;

	/** Fichier du dictionnaire de mots */
	private String dictFile;

	/** Le nombre de tours du jeu courant */
	private int tour;

	/** L'arbre lexical sur lequel le jeu est basé */
	private ArbreLexical a;

	/** Nombre de joueurs */
	private int nbJoueurs;

	/** La liste des joueurs pour le jeu */
	private ArrayList<Joueur> joueurs;

	/** Itérateur sur joueurs */
	private Iterator<Joueur> it;

	/** Constructeur qui construit l'arbre lexical */
	public Game() {

		this.getConfig(CONFIG_FILE);

		// System.out.println(this.dictFile);
		this.a = ArbreLexical.getInstance(this.dictFile);
		this.nbJoueurs = 0;
		this.joueurs = null;
		this.tour = 0;
	}

	/**
	* Récupère le fichier de configuration et lit les propriétés
	*
	* @param le nom du fichier de configuration.
	*/
	private void getConfig(String fileName) {

		// Récupération de la configuration
		BufferedReader br = null;
		Properties props = new Properties();

		try {
			br = new BufferedReader(new FileReader(fileName));

      props.load(br);

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

			// Récupération de la taille min d'un mot
			try {
				this.taille_min = Integer.parseInt(props.getProperty("taille-min"));
			}
			catch(Exception e) {
				System.out.println("Mauvais format de fichier de configuration du jeu.");
			}

			// Récupération des dés
			this.config_des = "config/";
			this.config_des += props.getProperty("des");


			// Récupération du fichier du dictionnaire
			this.dictFile = "config/";
			this.dictFile += props.getProperty("dictionnaire");
	}

	/**
	* Demande le nb de joueurs et initialise les joueurs et leur score.
	*/
	public void initialiseJoueurs() {

		System.out.println("Combien de joueurs?");
		this.nbJoueurs = Clavier.readInt();

		while(this.nbJoueurs < 1||this.nbJoueurs > 5) {
			System.out.println("Combien de joueurs? (entre 1 et 5)");
			this.nbJoueurs = Clavier.readInt();
		}

		this.joueurs = new ArrayList<Joueur>();

		for(int i = 0; i < nbJoueurs; i++) {
			System.out.println("Nom du joueur " + (i + 1) + " ?");
			String nom = Clavier.readString();
			joueurs.add(new Joueur(nom));
		}

		this.it = joueurs.iterator();

	}

	/**
	* Retourne le joueur suivant dans la liste des joueurs
	*
	* @return le Joueur suivant
	*/
	public Joueur nextJoueur() {

		this.tour++;

		if(this.it.hasNext()) {
			return it.next();
		}
		else {
			this.it = joueurs.iterator();
			return it.next();
		}
	}

	/**
	* Méthode qui détermine si le jeu est fini.
	* C'est le nombre de tours qui décide du jeu.
	*
	* @return <code>true</code> si le jeu est fini, <code>false</code> sinon.
	*/
	public boolean estFini() {

		int test = this.tour / this.nbJoueurs;

		if(test < 2) {
			return false;
		}
		return true;
	}

	/**
	* Jouer un tour pour un joueur j
	*
	* @param j le joueur dont c'est le tour
	*/
	public void jouer(Joueur j) {

		ArrayList<String> reponses = new ArrayList<String>();

		// Afficher le joueur et son score
		System.out.println(j.getNom() + " : " + j.getScore() + " point(s)");

		// Obtenir une grille de N*N lettres
		GrilleLettres grille = new GrilleLettres(N, config_des);
		grille.getFacesVisibles();

		// Demander les mots au joueur
		String mot = demanderMot();

		// Tant que le joueur propose des mots
		while(!mot.equals("S")) {

			// Vérifier que le mot est valide
			mot = ArbreLexical.normalize(mot);
			// Vérifier la taille du mot
			if(mot.length() < N - 1) {
				System.out.println ("Le mot est trop court.");
				mot = demanderMot();
				continue;
			}

			// Vérifier si le mot n'a pas déjà été trouvé
			if(reponses.contains(mot)) {
				System.out.println("Le mot a déjà été donné.");
				mot = demanderMot();
				continue;
			}

			// Vérifier que le mot est valide
			ArrayList<Integer> a = new ArrayList<Integer>();
			grille.estMotValide(mot, a);

		 	// System.out.println("grille.getIndices: " + grille.getIndices());

			if(grille.getIndices() == null) {
				System.out.println("Le mot n'est pas valide.");
				mot = demanderMot();
				continue;
			}

			// Vérifier que le mot est dans le dictionnaire
			if(!this.a.contient(mot)) {
				System.out.println("Le mot n'est pas dans le dico.");
				mot = demanderMot();
				continue;
			}
			else {
				// On rajoute les points au joueur, et le mot à la liste des mots trouvés
				j.addPoints(grille.nbreDePointsDuMot(mot));
				reponses.add(mot);

				// Affichage du résultat et de la grille
				System.out.println(j.getNom() + ", vous avez maintenant " + j.getScore() + " points.");
				grille.getFacesVisibles();
			}

			mot = demanderMot();

		}
	}

	/**
	* Méthode demandant un mot au joueur en cours.
	* @return le mot entré
	*/
	private static String demanderMot() {
		System.out.println("Quel mot proposez vous? (Tapez 'S' pour passer au joueur suivant) ");
		String mot = Clavier.readString();
		return mot;
	}

	/**
	* Gère la fin du jeu (affichage des scores)
	*/
	public void finDeJeu() {

		int meilleurScore = 0;
		ArrayList<Joueur> gagnants = new ArrayList<Joueur>();

		for(Joueur j: this.joueurs) {
			System.out.println(j.getNom() + ": " + j.getScore() + " points.\n");
			if(j.getScore() > meilleurScore) {
				meilleurScore = j.getScore();
				gagnants.clear();
				gagnants.add(j);
			}
			else if(j.getScore() == meilleurScore) {
				gagnants.add(j);
			}
		}

		System.out.println("Le meilleur score de " + meilleurScore + " a été atteint par:");
		for(Joueur j : gagnants) {
			System.out.println(j.getNom());
		}
	}

}

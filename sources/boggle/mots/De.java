package boggle.mots ;

import java.util.* ;
import java.io.*;

/**
 * La classe De permet de stocker des dés. Elle gère le fait qu'il y a dans le jeu exactement NxN dés, dont les valeurs sont spécifiées dans un fichier de configuration.
 */
public class De {

	/**
	 * La constante définissant le nombre de faces d'un <code>De</code> : 6.
	 */
	protected final int NOMBRE_FACES = 6 ;

	/**
	 * Le tableau des faces du <code>De</code>.
	 */
	protected String[] faces = new String[NOMBRE_FACES];

	/**
	 * La face visible lors du lancement du <code>De</code>.
	 */
	protected String faceVisible ;

	/**
	 * Le constructeur <code>De</code> qui permet d'instancier l'objet à partir d'un tableau.
	 *
	 * @param faces
	 *				Le tableau contenant les différents motifs à appliquer aux faces du <code>De</code>.
	 */
	public De( String[] faces ){
		this.faces = faces;
		this.setFaceVisible();
	}

	/**
	 * Retourne les faces du <code>De</code> sous forme de tableau.
	 *
	 * @return Un tableau des faces du <code>De</code>.
	 */
	public String[] getFaces(){
		return faces;
	}

	/**
	 * Lance le <code>De</code> et détermine sa face visible <strong>aléatoirement</strong>.
	 */
	public void setFaceVisible(){
		faceVisible = faces[(new Random()).nextInt(NOMBRE_FACES)];
	}

	/**
	 * Retourne la face visible du <code>De</code>.
	 *
	 * @return La face visible du <code>De</code>.
	 */
	public String getFaceVisible(){
		return faceVisible;
	}

}

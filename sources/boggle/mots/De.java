package boggle.mots ;

import boggle.*; 
import java.util.* ;
import java.io.*;

/** La classe De permet de stocker des dés.
 * Elle gère le fait qu'il y a dans le jeu exactement NxN dés 
 * dont les valeurs sont spécifiées dans un fichier de configuration. 
 */
public class De {

	protected final int NOMBRE_FACES = 6 ;

	protected String[] faces = new String[NOMBRE_FACES];
	protected String faceVisible ;

	public De( String[] f ){
		faces = f;
		this.setFaceVisible();
	}

	public String[] getFaces(){
		return faces;
	}

	public void setFaceVisible(){
		faceVisible = faces[(new Random()).nextInt(NOMBRE_FACES)];
	}

	public String getFaceVisible(){
		return faceVisible;
	}
    
}

//PAUSE
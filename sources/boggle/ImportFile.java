package boggle;

import java.io.*;

/** 
 * La classe ImportFile permet d'importer les données d'un fichier CSV.
 */
public class ImportFile {

	/** 
	 * La chaîne qui contient le résultat de l'extraction de données.
	 */
	private String result = "";

	/** 
	 * Le constructeur <code>ImportFile</code> qui permet d'instancier l'objet à partir du chemin d'un fichier.
	 *
	 * @param path
	 *				Le chemin du fichier à analyser.
	 */
	public ImportFile(String path) throws FileNotFoundException {
		try {

			BufferedReader br = new BufferedReader(new FileReader(path));
			String ligne;

			boolean flagCSV = false;
			if ( (path.substring(path.length()-3, path.length())).equals("csv") ) {
				flagCSV = true;
			}

			while ( (ligne = br.readLine()) != null ) {

				if (flagCSV == true) {

					String[] splitted = ligne.split("\\;");
					for (int i=0;i<splitted.length;i++){
						result += splitted[i];
						if (i!=5) {
							result += ";";
						}
					}
					result += "\n";
					
				}else if (flagCSV == false){
					result += ligne + "\n" ;
				}

				
			}
			result = result.substring(0, result.length()-1); //Pour enlever le dernier \n

			br.close();

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Retourne les données extraites.
	 * 
	 * @return Une chaîne de caractères, qui correspond aux données extraites.
	 */
	public String getResult(){
		return result;
	}

	/**
	 * Retourne les données de l'attribut <strong>result</strong> sous forme de tableau.
	 *
	 * @param ligne
	 *				Le nombre de lignes que contiendra le tableau retourné.
	 * @param colonne
	 *				Le nombre de colonnes que contiendra le tableau retourné.
	 *
	 * @return Une représentation sous forme de tableau des données extraites.
	 */
	public String[][] getResultTab(int ligne, int colonne){
		String[][] res = new String[ligne][colonne];

		String[] splitOne = result.split("\n");
		for (int i=0; i<splitOne.length; i++) {
			res[i] = splitOne[i].split(";");
		}

		return res;
	}

	public static void main(String[] args) {
		String tamere = new File("./").getAbsolutePath();
		tamere = tamere.substring(0, tamere.length()-1) + "config/regles.txt";
		try{
			ImportFile regles = new ImportFile( tamere );
			System.out.println( regles.getResult() );		
		}catch( Exception e ){
			System.out.println("Erreur : "+e.getMessage());
		}
	}

}

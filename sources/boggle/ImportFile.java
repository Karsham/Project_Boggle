package boggle;

import java.io.*;

/** La classe ImportFile permet d'importer les données d'un fihcier CSV.
 */
public class ImportFile {

	private String result = "";

	public ImportFile(String path) throws FileNotFoundException {

    	// FileInputStream fis = new FileInputStream(datas);
		try {

			BufferedReader br = new BufferedReader(new FileReader(path));
			String ligne;

			while ( (ligne = br.readLine()) != null ) {

				String[] splitted = ligne.split("\\;");
				for (int i=0;i<splitted.length;i++){
					result += splitted[i];
					if (i!=5) {
						result += ";";
					}
				}
				result += "\n";
			}
			result = result.substring(0, result.length()-1); //Pour enlever le dernier \n

			br.close();

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public String getResult(){
		return result;
	}

	public String[][] getResultTab(int ligne, int colonne){
		String[][] res = new String[ligne][colonne];

		String[] splitOne = result.split("\n");
		for (int i=0; i<splitOne.length; i++) {
			res[i] = splitOne[i].split(";");
		}

		return res;
	}

	public static void main(String[] args) throws FileNotFoundException {
		ImportFile txt = new ImportFile(new File("./").getAbsolutePath() + "/config/des-4x4.csv");
		System.out.println(txt.getResult());
		System.out.println();

		String[][] look = txt.getResultTab(16,6);
		for (int i=0; i<look.length; i++) {
			for (int j=0; j<look[i].length; j++) {
				System.out.print( (j+1) +" : "+look[i][j]+ " | " );
			}
			System.out.println();
		}
	}

}

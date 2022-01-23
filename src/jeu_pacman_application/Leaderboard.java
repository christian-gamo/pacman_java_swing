package jeu_pacman_application;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 * La classe Leaderboard permet d'établir un classement des 10 meilleurs joueurs de notre jeu.
 * Elle permet de créer un fichier texte contenant le classement.
 * Elle peut lire ce fichier texte s'il existe déjà et rajouter des joueurs battant les scores
 * des anciens joueurs présent sur ce classement.
 * 
 * @author Lisa-Marie
 */
public class Leaderboard {
	/** ArrayList de la classe Joueur permettant de stocker les joueurs */
	private static ArrayList<Joueur> classement;
	/** Constante contenant le chemin absolu du dossier où sera stocké le dossier du fichier texte créé */
	private final static String cheminFichier = "./assets/leaderboard";
	/** Constante contenant le chemin absolu du dossier où sera stocké le dossier du fichier texte créé */
	private final static String nomFichier = "leaderboard.txt";
	

	/**
	 * Constructeur qui va lire le fichier texte contenant le classement
	 * et ajouter dans l'ArrayList classement les joueurs présents dessus
	 * @param joueur 
	 * @param score
	*/
	public Leaderboard(String joueur, int score) {
		classement = new ArrayList<Joueur>();
		if(new File(cheminFichier, "leaderboard.txt").exists()) {
			BufferedReader lecteur;
			try {
				lecteur = new BufferedReader(new FileReader(cheminFichier+"/"+nomFichier));
				String ligne;
				while ((ligne = lecteur.readLine()) != null) {
					String[] s = ligne.split(" "); 
					String nom = s[0];
					int points = Integer.parseInt(s[1]);
					classement.add(new Joueur(nom, points));
				}
				lecteur.close();
			} catch
			(IOException e) {
				e.printStackTrace();
			}
		}
		classement.add(new Joueur(joueur, score));
	}
	
	/**
	 * Méthode sauvegarderLeaderboard() : Cette méthode permet de créer le fichier texte contenant le classement
	 * Le fichier texte ne récupère que les 10 premiers joueurs de l'ArrayList.
	 * @throws IOException
	*/
	public void sauvegarderLeaderboard() throws IOException{
		
		Collections.sort(classement,new Comparator<Joueur>() {
		    @Override
		    public int compare(Joueur j1, Joueur j2) {
		        return Integer.compare(j2.getScore(), j1.getScore());
		    }
		});
		
		File fout = new File(cheminFichier+"/"+nomFichier);
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		for (int i =0; i<10; ++i) {
			int score = classement.get(i).getScore();
            String nom = classement.get(i).getNom();
            bw.write(nom + " " + score);
            bw.newLine();
        }
		bw.close();
	}
	
	/**
	 * Méthode toString() : Cette méthode permet d'afficher sur la console le classement.
	 * Elle n'affiche que les 10 meilleurs joueurs.
	 * 
	 * @return classement des 10 meilleurs joueurs du jeu
	*/
	@Override
	public String toString() {
		
		Collections.sort(classement,new Comparator<Joueur>() {
		    @Override
		    public int compare(Joueur j1, Joueur j2) {
		        return Integer.compare(j2.getScore(), j1.getScore());
		    }
		});
		
		StringBuilder s = new StringBuilder();
		s.append("\n--------------------------\n");
		s.append("Classement:");
		s.append("\n--------------------------\n");
		for (int i =0; i<10; ++i) {
            int score = classement.get(i).getScore();
            String nom = classement.get(i).getNom();
            s.append(nom + " " + score + "\n");
        }
		return s.toString();
	}
	
	/**
	 * La classe Joueur permet de créer des objets représentant les joueurs 
	 * et de lier un nom à un score.
	 * @author Lisa-Marie
	 */
	private class Joueur{
		String nom;
		Integer score;
		/**
		 * Constructeur de la classe Joueur
		 * @param n
		 * @param s
		 */
		public Joueur (String n, int s) {
			nom = n;
			score = s;
		}
		/**
		 * Méthode getScore() : Cette méthode permet de récupérer le score d'un joueur
		 * @return score du joueur
		 */
		public int getScore() {
			return score;
		}
		/**
		 * Méthode getNom : Cette méthode permet de récupérer le pseudo d'un joueur.
		 * @return pseudo du joueur
		 */
		public String getNom() {
			return nom;
		}
	}
}

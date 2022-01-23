package jeu_pacman_application;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.Timer;

import jeu_pacman_entites.Blinky;
import jeu_pacman_entites.Clyde;
import jeu_pacman_entites.Entite;
import jeu_pacman_entites.Fantome;
import jeu_pacman_entites.Inky;
import jeu_pacman_entites.Pacman;
import jeu_pacman_entites.Pinky;
import jeu_pacman_entites.Fantome.Mode;
import jeu_pacman_labyrinthe.Labyrinthe;
/**
 * Classe repr�sentant la logique m�tier du jeu. C'est cette classe qui va regrouper toutes les autres classes
 * du jeu et permettre son bon fonctionnement.
 * Elle est donc reli� � l'interface graphique.
 * 
 * @author Christian Darl�ne Lisa-Marie
 *
 */
public class Jeu implements ActionListener {
	
	private static final int FRAMES_PAR_SECONDE = 40;
	private static String cheminNiveau = "./assets/niveaux/niveau.txt";
	

	private Pacman pacman;
	private List<Fantome> fantomes;
	private Labyrinthe labyrinthe;
	private String joueur;

	private Timer timer;
	//Audio
    private static Audio audio = new Audio();
    
	public Jeu(String j,Labyrinthe l, Pacman p, Blinky b, Clyde c, Inky i, Pinky pi) {
		joueur = j;
		labyrinthe = l;
		pacman = p;
		fantomes = Arrays.asList(b, c, i, pi);
		timer = new Timer(1000 / FRAMES_PAR_SECONDE, this);
	}
		
	/**
	 * M�thode actionPerformed() : Cette m�thode est h�rit� de la classe ActionListener qui est appel� � chaque �venement
	 * et permet la mise � jour constant de la fen�tre de jeu
	 * Elle permet de d�placer les entit�s dans le labyrinthe et de v�rifier
	 * si il y a collision entre Pacman et les autres fantomes.
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		if(getPacman().checkEnTrainDeManger(labyrinthe) ) {
			loopMange();
		}
		else {
			stopMange();
		}
		
		pacman.deplacer(entites(), labyrinthe);
		collision();
	
		 for(Fantome fantome : fantomes) {
			 fantome.deplacer(entites(), labyrinthe);
		 }
		 collision();
		 
		 if(fantomesEffrayes()) {
			 loopFantomeEffraye();
		 }
		 else {
			 stopFantomeEffraye();
		 }
		 niveauSuivant();
	}
	
	/**
	 * M�thode chargerDonnees() : Cette m�thode permet de cr�er une instance de la classe Jeu et la retourne. Elle contient toutes les propri�t�s du jeu
	 * et permet l'instantation du jeu et de toutes les classes en d�pendant.
	 * On y retrouve donc le labyrinthe qui est initialis� � travers un fichier texte et les entit�s.
	 * 
	 * @param joueur
	 * @throws IOException
	 * @throws URISyntaxException 
	 * @throws ClassNotFoundException 
	 */
	public static final Jeu chargerDonnees(String joueur) throws IOException, URISyntaxException, ClassNotFoundException {
		
		
		// Lecture du fichier texte permettant la cr�ation du labyrinthe.
		FileInputStream f = new FileInputStream(new File(cheminNiveau));
		byte[] bytes = new byte[f.available()];
		f.read(bytes);
		f.close();
		String s = new String(bytes, "UTF-8").replaceAll("\\s", "");
		
		// Initialisation du labyrinthe
		String niveau = s.replaceAll("\\D", "0");
		int longueur  = 28;
		int largeur = 36;
		Labyrinthe lab  = Labyrinthe.chargerLabyrinthe(niveau, longueur, largeur);
		
		// Initialisation du Pacman
		double vitessePacman = 0.35;
		
		Pacman pacman = new Pacman(trouverCaractere(lab, s, 'M'), vitessePacman);	
		
		// Initialisation des fantomes
		Point2D ghostExit = trouverCaractere(lab, s, 'S');
		Blinky blinky = new Blinky(trouverCaractere(lab, s, 'B'), trouverCaractere(lab, s, 'b'), ghostExit, Mode.IMMOBILE);
		Clyde clyde   = new Clyde (trouverCaractere(lab, s, 'C'), trouverCaractere(lab, s, 'c'), ghostExit, Mode.IMMOBILE);
		Inky inky 	  = new Inky  (trouverCaractere(lab, s, 'I'), trouverCaractere(lab, s, 'i'), ghostExit, Mode.IMMOBILE);
		Pinky pinky   = new Pinky (trouverCaractere(lab, s, 'P'), trouverCaractere(lab, s, 'p'), ghostExit, Mode.IMMOBILE);
	
		return new Jeu(joueur,lab, pacman, blinky, clyde, inky, pinky);
	}
	
	/** 
	 * M�thode getJoueur () : Cette m�thode est un Getter r�cup�rant le nom du joueur
	 * @return joueur
	 */
	public String getJoueur() {
		return joueur;
	}
	
	/** 
	 * M�thode setCheminNiveau() : Cette m�thode permet de modifier le chemin du fichier du niveau
	 * qui permet d'initialiser le labyrinthe
	 * @param niveau
	 */
	public static void setCheminNiveau(String niveau) {
		Jeu.cheminNiveau = niveau;
	}
	
	/** 
	 * M�thode getCheminNiveau () : Cette m�thode permet de r�cup�rer le chemin du fichier du niveau
	 * qui permet d'initialiser le labyrinthe
	 * @return cheminNiveau
	 */
	public static String getCheminNiveau() {
		return Jeu.cheminNiveau;
	}
	
	/** 
	 * M�thode getLabyrinthe() : Cette m�thode permet de r�cup�rer le labyrinthe
	 * @return labyrinthe
	 */
	public Labyrinthe getLabyrinthe() {
		return labyrinthe;
	}
	
	/** 
	 *  M�thode getPacman() : Cette m�thode permet de de r�cup�rer le pacman
	 * @return Pacman
	 */
	public Pacman getPacman() {
		return pacman;
	}
	
	/** 
	 * M�thode getFantomes() : Cette m�thode permet de r�cup�rer les fantomes
	 * @return fantomes
	 */
	public List<Fantome> getFantomes() {
		return fantomes;
	}
	
	/**
	 * M�thode trouverCaract�re() : Cette m�thode permet de retrouver un caract�re de type lettre dans la chaine de caract�res string
	 * r�cup�r�e depuis le fichier txt du niveau. Les lettres repr�sentes les points initiaux des entit�s
	 * et les points de dispersion des fantomes. Elle retourne donc un objet de type Point2D permettant
	 * de conserver les points initiaux et les points de dispersion.
	 * @param lab
	 * @param chaine
	 * @param val
	 * @return le Point correspondant aux coordonn�es de la lettre sur le labyrinthe
	 */
	private static final Point2D trouverCaractere(Labyrinthe lab, String chaine, char val) {
		int index = chaine.indexOf(val);
		int longueur = lab.getLongueur();
		
		return lab.centrePoint(new Point(index % longueur, index / longueur));
	}
	
	/**
	 * M�thode collision() : Cette m�thode permet de r�agir lors d'une collision entre un fantome et pacman.
	 * Si il y a collision, Pacman tente de manger le fantome.
	 * Si Pacman n'a plus de vie. On lance l'arr�t de la partie.
	 */
	public void collision() {
		for(Fantome fantome : fantomes) {
			if (labyrinthe.detectionCollision(pacman, fantome)) {
				if (fantome.getMode().equals(Mode.EFFRAYE)){
					playMangeFantome();
				}
				if(!pacman.manger(fantome)) {
					for(Entite entite : entites())
						entite.resetPoint();
					if(gameOver()) {
						stopMange();
						stopFantomeEffraye();
						playMort();
						arret();		
					}
				}
			}
		}
	}
	
	
	
	/** 
	 * M�thode ajoutant un objet de type ActionListener au timer du jeu permettant de
	 * lier le code du jeu � l'interface graphique et mettre � jour l'interface quand
	 * il le faut
	 * 
	 * @param listener
	 */
	public void ajouterActionListener(ActionListener listener) {
		timer.addActionListener(listener);
	}
	
	/** 
	 * M�thode permettant de lancer la partie.
	 */
	public void commencerPartie() {
		playStart();
		timer.start();
	}
	
	/** 
	 * M�thode permettant de passer au niveau suivant lorsque Pacman a ramass� tout les
	 * pac gommes.
	 */
	public void niveauSuivant() {
		if(labyrintheEstFini()) {
			labyrinthe.reset();
			pacman.resetNbrVies();
			for(Entite entite : entites())
				entite.resetPoint();
			for(Fantome fantome : fantomes) {
				//les fantomes deviennent plus rapides
				fantome.setDifficulte(fantome.getDifficulte()+0.4);
				fantome.setMode(Mode.IMMOBILE);
			}
			playStart();
		}
	}
	
	/** 
	 * M�thode permettant de savoir si il ya un GameOver
	 * lorsque le joueur n'a plus de vies.
	 */
	private boolean gameOver() {
		return pacman.getNbrVies() == 0;
	}
	
	/** 
	 * M�thode permettant de savoir si la partie est en cours
	 */
	public boolean estEnCours() {
		return timer.isRunning();
	}
	
	/** 
	 * M�thode permettant de savoir si le labyrinthe a �t� compl�t�
	 */
	public boolean labyrintheEstFini() {
		return labyrinthe.estComplete();
	}
	
	/** 
	 * M�thode permettant de lancer l'arr�t du timer et donc de la partie 
	 * et d'afficher le leaderboard et le score du joueur.
	 */
	public void arret() {
		timer.stop();
		Leaderboard l = new Leaderboard(joueur,pacman.getScore());
		System.out.println("\nVous avez perdu " + joueur + "!");
		System.out.println("Votre score : " + pacman.getScore());
		System.out.println(l.toString());
		try {
			l.sauvegarderLeaderboard();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	/** 
	 * M�thode permettant de recommencer la partie
	 */
	public void recommencerPartie() {
		for(Entite actor : entites())
			actor.resetPoint();
		labyrinthe.reset();
	}
	
	/** 
	 * M�thode permettant de savoir s'il reste des fantomes effray�s.
	 */
	private boolean fantomesEffrayes() {
		for(Fantome fantome : fantomes) {
			if(fantome.getMode().equals(Mode.EFFRAYE)){
				 return true;
			}
		}
		return false;
	}
	
	/** 
	 * M�thode permettant de r�cup�rer la liste des entites.
	 */
	public List<Entite> entites() {
		List<Entite> entites = new ArrayList<Entite>();
		entites.add(pacman);
		
		for(Fantome fantome : fantomes)
			entites.add(fantome);
		
		return entites;
	}
	
	/** 
	 * M�thode permettant de r�cup�rer les dimensions du labyrinthe selon la taille des
	 * cases du labyrinthe
	 */
	public Dimension getDimensions() {
		return new Dimension(labyrinthe.getLongueur() * Labyrinthe.getTailleCase(),
				labyrinthe.getLargeur() * Labyrinthe.getTailleCase());
	}
	
	/**
	 * M�thode retournant le nombre de frames que prend le temps en param�tre
	 * qui est en millisecondes. Il est d�pendant du framerate du jeu.
	 * 
	 * @param millis
	 * @return nombre de frames
	 */
	public static int conversionFrames(long millis) {
		return (int) (millis / 1000.0 * Jeu.FRAMES_PAR_SECONDE);
	}
	
	/**
	 * M�thode statique permettant de lancer le son de d�marrage du jeu
	 */
	public static void playStart() {
		audio.playStartAudio();
	}
	/**
	 * M�thode statique permettant de lancer le son de la mort d'un jeu
	 */
	public static void playMort() {
		audio.playMortAudio();
	}
	/**
	 * M�thode statique permettant d'arr�ter le son du Pacman qui mange
	 */
	public static void stopMange() {
		audio.stopMangeAudio();
	}
	/**
	 * M�thode statique permettant de lancer en boucle le son du Pacman qui mange
	 */
	public static void loopMange() {
		audio.loopMangeAudio();
	}
	/**
	 * M�thode statique permettant de lancer le son du Pacman qui mange un fantome
	 */
	public static void playMangeFantome() {
		audio.playMangeFantomeAudio();
	}
	/**
	 * M�thode statique permettant de lancer en boucle le son des fantomes �ffray�s
	 */
	public static void loopFantomeEffraye() {
		audio.loopFantomeEffrayeAudio();
	}
	/**
	 * M�thode statique permettant d'arr�ter le son des fantomes �ffray�s
	 */
	public static void stopFantomeEffraye() {
		audio.stopFantomeEffrayeAudio();
	}
}

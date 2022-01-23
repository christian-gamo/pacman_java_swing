package jeu_pacman_application;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.Normalizer;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import jeu_pacman_affichage.Sprite;
import jeu_pacman_entites.Entite;
import jeu_pacman_entites.Fantome;
import jeu_pacman_entites.Pacman;
import jeu_pacman_labyrinthe.Labyrinthe;

/**
 * La classe InterfaceGraphique permet de mettre en place l'interface graphique du jeu.
 * Il contient le main() et h�rite de la classe JFrame issue de la libraire Java Swing
 * permettant de mettre en place une interface graphique.
 * 
 * @author Christian Darl�ne Lisa-Marie
 * 
 */
public class InterfaceGraphique extends JFrame {
	
	/**
	 * M�thode main() : La m�thode main est la premi�re m�thode � �tre ex�cut�e en d�but et en fin de programme. 
	 * Cette m�thode permet de lancer le jeu et demande le pseudo au Joueur
	 * Il y a des v�rifications permettants de ne pas mettre des caract�res invalides concernant le pseudo.
	 */
	public static void main(String[] args) throws Exception {
		String joueur;
		boolean entreeValide = false;
		System.out.println("Bienvenue sur notre jeu Pacman !");
		System.out.println("Ce projet a �t� r�alis� par Christan, Darl�ne et Lisa-Marie "
		+ "dans le cadre du cours de Java Objet. \n");
		System.out.println("(Les caract�res sp�ciaux ne sont pas autoris�s)");
		System.out.println("Veuillez entrer votre pseudo:");
		
		/**
		 * Pour ne pas avoir de caract�res sp�ciaux dans le pseudo
		 */
		String pasDeCaracteresSpeciaux = "^((?=[A-Za-z0-9@])(?![_\\\\-]).)*$";
		Scanner scanner = new Scanner(System.in);
		while(!entreeValide) {
			/*
			 * Retire les espaces
			 */
			joueur = scanner.nextLine().replaceAll("\\s+","");
			if (joueur.length()> 0 
				&& joueur.length()<=15 
				&& joueur.matches(pasDeCaracteresSpeciaux)) {
				entreeValide = true;
				new InterfaceGraphique(Jeu.chargerDonnees(joueur));	
			}
			else {
				System.out.println("Votre nom est invalide. Veuillez r�essayer");
			}
		}
		
	}
	
	/**
	 * Constructeur de la classe InterfaceGraphique, le constructeur permet d'initialiser la fen�tre de Jeu et de l'afficher
	 * @param jeu
	 * 
	 */
	public InterfaceGraphique(Jeu jeu) throws IOException, FontFormatException, URISyntaxException {
		FenetreDeJeu fenetre = new FenetreDeJeu(jeu);
		this.setContentPane(fenetre);
		this.setTitle("Pacman : Projet Java");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(500, 100);
		this.pack();
		this.setAlwaysOnTop(true);
		this.setVisible(true);
		this.toFront();

	}
	
	/**
	 * La classe FenetreDeJeu repr�sente la fen�tre de jeu et permet de relier l'interface
	 * graphique au code du jeu.
	 * Elle impl�mente la classe ActionListener pour pouvoir recevoir les �venements se d�roulant
	 * dans le code du jeu et se mettre � jour constamment et h�rite de la classe JPanel permettant
	 * la mise en place d'une interface graphique. Cette classe est private car elle est utilis�e au
	 * sein m�me de la classe interface graphique.
	 * 
	 * @author Christian
	 * 
	 */
	private class FenetreDeJeu extends JPanel implements ActionListener {
		
		/**Constante contenant le chemin de la police d'�criture*/
		private static final String POLICE = "./assets/polices/emulogic.ttf";
		/**Sprites r�cup�r� depuis l'image contenant les sets de sprite*/
		private BufferedImage sprites;
		/**Objet de classe Jeu contenant le code du jeu et donc toute la "logique m�tier"*/
		private Jeu jeu;
		/**Police d'�criture des informations du joueur sur l'interface*/
		private Font police;
		
		/**
		 * Constructeur de InterfaceGraphique, ce constructeur permet d'initialiser la fen�tre de Jeu et de l'afficher
		 * @param game
		 * 
		 */
		public FenetreDeJeu(Jeu game) throws IOException, FontFormatException, URISyntaxException {		
			
			sprites = ImageIO.read(new File(Sprite.getSpriteChemin()));
			police = Font.createFont(Font.TRUETYPE_FONT, 
			new File(FenetreDeJeu.POLICE))
			.deriveFont(13f);
			
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        ge.registerFont(police);
	        
			jeu = game;
			jeu.ajouterActionListener(this);
			jeu.commencerPartie();
			
			this.setPreferredSize(jeu.getDimensions());
			this.setBackground(Color.BLACK);

			
			if(jeu.getPacman() instanceof Pacman) {
				this.addKeyListener((Pacman) jeu.getPacman());
				this.setFocusable(true);
				this.requestFocusInWindow();
			}
		}
		/**
		 * M�thode checkFinDePartie() : Cette m�thode permet de v�rifier si la partie est termin�e ou non.
		 * @return true si la partie est finie, false sinon
		 */
		public boolean checkFinDePartie() {
			return jeu.getPacman().getNbrVies()==0;
		}
			
		/**
		 * M�thode actionPerformed() : Cette m�thode est h�rit�e de la classe ActionListenerb qui est appel�e � chaque �venement
		 * et permet la mise � jour constante de la fen�tre de jeu
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			this.repaint();

		}
		
		/**
		 * M�thode paintComponent() :  Cette m�thode est h�rit�e de la classe JPanel et permet d'afficher les sprites
		 * sur la fen�tre de jeu 
		 * @param g
		 */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			dessinerLabyrinthe(g, sprites, jeu.getLabyrinthe());
			
			
			int tailleEntite = (int) (Labyrinthe.getTailleCase() * 1.5);
			
			for(Fantome fantome : jeu.getFantomes())
				dessinerEntite(g, sprites, fantome, tailleEntite);
			
			if(checkFinDePartie()) {
				dessinerGameOver(g);
			}
			else {
				dessinerEntite(g, sprites, jeu.getPacman(), tailleEntite);
			}
			
			dessinerInterface(g);
		}
		
		/**
		 * M�thode dessinerEntite() : Cette m�thode est h�rit�e de la classe JPanel et permet d'afficher les sprites
		 * sur la fen�tre de jeu 
		 * 
		 * @param g
		 * @param imageSprites
		 * @param e
		 * @param taille
		 */
		public void dessinerEntite(Graphics g, BufferedImage imageSprites, Entite e, int taille) {
			Point2D position = e.getPointActuel();
			Rectangle destination = new Rectangle(
			(int) (position.getX() - taille / 2), 
			(int) (position.getY() - taille / 2), 
			taille, taille);
			e.getSprite().dessiner(g, imageSprites, destination);
		}
		
		/**
		 * M�thode dessinerLabyrinthe() : Cette m�thode permet de dessiner le labyrinthe sur la fen�tre
		 * @param g
		 * @param imageSprites
		 * @param lab
		 */
		public void dessinerLabyrinthe(Graphics g, BufferedImage imageSprites, Labyrinthe lab) {		
			for(int x = 0; 
					x < lab.getLongueur() * Labyrinthe.getTailleCase(); 
					x += Labyrinthe.getTailleCase()){
				for(int y = 0;
						y < lab.getLargeur() * Labyrinthe.getTailleCase(); 
						y += Labyrinthe.getTailleCase()) {
					Point2D.Double pos = new Point2D.Double(x, y);				
					Rectangle dest = new Rectangle((int) pos.x, (int) pos.y, 
					Labyrinthe.getTailleCase(), Labyrinthe.getTailleCase());
					lab.getCase(pos).getSprite().dessiner(g, imageSprites, dest);
				}
			}
		}
		
		/**
		 * M�thode dessinerInterface() : Cette m�thode permet de dessiner les informations relatives � la partie
		 * du joueur sur la fen�tre
		 * @param g
		 */
		public void dessinerInterface(Graphics g) {
			g.setFont(police);
			g.setColor(Color.WHITE);
			g.drawString("score:" + jeu.getPacman().getScore(), 10, 20);
			g.drawString("joueur:" + jeu.getJoueur(), 8, 40);
			g.drawString("vies:" + jeu.getPacman().getNbrVies(), 350,20);
		}
		
		/**
		 * M�thode dessinerGameOver() : Cette m�thode permet de dessiner le texte de GameOver s'affichant en fin de partie sur l'interface du jeu
		 * @param g
		 */
		public void dessinerGameOver(Graphics g) {
			g.setFont(police);
			Font p1 = g.getFont();
			Font p2 = p1.deriveFont(30f);
			g.setFont(p2);
			g.setColor(Color.YELLOW);
			g.drawString("game over", 90, 300);
		}
	}

}

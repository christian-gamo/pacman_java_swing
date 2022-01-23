package jeu_pacman_entites;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.util.List;


import jeu_pacman_labyrinthe.Labyrinthe;
import jeu_pacman_affichage.SpriteAnime;
import jeu_pacman_application.Jeu;
import jeu_pacman_entites.Fantome.Mode;
import jeu_pacman_labyrinthe.Case;

/**
 * La classe Pacman repr�sente le personnage principal du jeu incarn� par le joueur. 
 * Ses d�placements sont contr�l�s avec les commandes 'Haut/Bas/Gauche/Droite'
 * 
 * Pacman est vuln�rable face aux fant�mes. Ainsi, lorsqu'il se d�place, son but sera
 * de les �viter afin de ne pas perdre. Cependant, lorsqu'il consomme une super gomme,
 * il devient invincible et a la possibilit� de manger les fant�mes et de les faire retourner
 * � leur point de d�part pour une dur�e d�termin�e.
 * 
 * Pacman gagne s'il arrive � manger toutes les gommes du labyrinthe, le score du joueur
 * est calcul� en fonction du nombre de gommes, de super gommes et de fant�mes qu'il mange.
 * 
 * Cette classe est li�e � la classe entit� par une relation d'h�ritage (Pacman �tant une entit� incarnable du jeu)
 * et impl�mente l'interface KeyListener disponible dans la librairie java.awt.event par d�faut, cette interface
 * contient des m�thodes prennant en charge des �v�nements/interactions clavier. Dans notre cas, nous avons
 * red�fini ces m�thodes en les adaptant � notre jeu, afin que le joueur puisse d�placer Pacman.
 *
 *
 * @author Christian
 * @author Darl�ne
 * @author Lisa-Marie
 *
 */
public class Pacman extends Entite implements KeyListener {
	
	/** Constante repr�sentant le nombre de points gagn�s par Pacman en mangeant une gomme. */
	private static final int POINTS_PACGOMME  = 10;
	/** Constante repr�sentant le nombre de points gagn�s par Pacman en mangeant un fant�me. */
	private static final int POINTS_FANTOME = 200;
	/** Constante repr�sentant le nombre de points gagn�s par Pacman en mangeant une super gomme. */
	private static final int POINTS_SUPERGOMME = 50;
	
	/** Constante repr�sentant la dur�e durant laquelle Pacman est en super forme apr�s
	 *  avoir mang� une super gomme. */
	private static final int DUREE_SUPERFORME = 3000;
	/**
	 * Bool�en permettant de savoir si Pacman est en super forme (true) ou non (false)
	 */
	private boolean estEnSuperForme;
	/**
	 * Entiers repr�sentant le score du joueur et le nombre de vies de Pacman
	 */
	private int score, nbrVies;
	/**
	 * Variable de type �num�r� pouvant prendre 4 valeurs (HAUT,BAS,GAUCHE,DROITE) 
	 * selon les d�placements de Pacman
	 */
	private Direction directionSuivante;
	/**
	 * Premier constructeur de la classe Pacman faisant appel au deuxi�me 
	 * constructeur de la classe Pacman, il est possible de faire appel � ce 
	 * constructeur en dehors de la classe Pacman.
	 * @param pointInitial
	 * @param vitesse
	 */
	public Pacman(Point2D pointInitial, double vitesse) {
		this(pointInitial, vitesse, Direction.GAUCHE);
	}
	/**
	 * Deuxi�me constructeur de la classe Pacman, pour initialiser les param�tres
	 * de la classe Entite dont Pacman h�rite, on ne peut pas faire appel � ce
	 * constructeur en dehors de la classe Pacman.
	 * @param pointInitial
	 * @param vitesse
	 * @param direction
	 */
	public Pacman(Point2D pointInitial, double vitesse, Direction direction) {
		super(pointInitial, new SpriteAnime(10, direction, 6), direction, vitesse);
		directionSuivante = direction;
		nbrVies = 3;
	}
	
	/**
	 * M�thode getScore() : Cette m�thode est un getter qui retourne le nombre de points du joueur.
	 * @return score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * M�thode getNbrVies() : Cette m�thode est un getter qui retourne le nombre de vies du joueur.
	 * @return nombre de vies
	 */
	public int getNbrVies() {
		return nbrVies;
	}
	/**
	 * M�thode resetNbrVies() : Cette m�thode permet de r�initialiser le nombre de vies de Pacman � 3
	 */
	public void resetNbrVies() {
		nbrVies = 3;
	}
	/**
	 * M�thode resetScore() : Cette m�thode permet de r�initialiser le score du joueur
	 */
	public void resetScore() {
		score = 0;
	}
	
	/**
	 * M�thode enSuperForme() : Cette m�thode permet de savoir si Pacman est en �tat de super forme
	 * ou non. L'�tat de super forme caract�rise le fait que Pacman puisse manger des fant�mes ou non.
	 * 
	 * @return true si Pacman est en super forme, false sinon
	 */
	public boolean enSuperForme() {
		return estEnSuperForme;
	}
	
	/**
	 * M�thode setSuperForme() : Cette m�thode permet d'activer le mode super forme de Pacman, en fonction
	 * du param�tre bool�en superForme r�cup�r� depuis la m�thode enSuperForme().
	 * Si superForme vaut true, le mode super forme est activ�, si superforme vaut false, il n'est pas activ�.
	 * @param superForme
	 */
	public void setSuperForme(boolean superForme) {
		estEnSuperForme =  superForme;
		setNbrDeplacements(0);
	}
	/**
	 * M�thode keyPressed() : Cette m�thode qui prend en param�tre un �v�nement (interaction clavier avec 
	 * les touches 'HAUT/BAS/GAUCHE/DROITE" permet � Pacman de changer de direction parmi les 4 directions
	 * disponibles en fonction de la touche press�e.
	 * 
	 * @param e
	 */
	@Override
	public void keyPressed(KeyEvent e) {		
		switch(e.getKeyCode()) {
			case KeyEvent.VK_DOWN:	directionSuivante = Direction.BAS;  break;
			case KeyEvent.VK_UP:	directionSuivante = Direction.HAUT;    break;
			case KeyEvent.VK_LEFT:	directionSuivante = Direction.GAUCHE;  break;
			case KeyEvent.VK_RIGHT:	directionSuivante = Direction.DROITE; break;
		}
	}

	/**
	 * M�thode d�placer() : Cette m�thode qui prend en param�tre une liste repr�sentant les entit�s du jeu
	 * (Pacman et les fant�mes) et un objet labyrinthe permet de savoir si Pacman est en d�placement ou non et
	 * quelles sont les cases que ce dernier traverse (cases vides, cases avec des gommes, cases avec 
	 * des super gommes
	 * 
	 * @return true si pacman est en �tat de d�placement (d�placement normal ou d�placement en super forme), false sinon.
	 */
	@Override
	public boolean deplacer(List<Entite> entites, Labyrinthe lab) {		
		/*
		 * On consid�re la valeur de la case actuelle (case vide, case pacgomme,
		 * case supergomme) et on cible la case suivante. Le score est augment� 
		 * en m�me temps que pacman se d�place vers la case suivante, en fonction
		 * du type de case (case vide, case pacgomme, case supergomme)
		 * 
		 */
		Point2D ptActuel = getPointActuel();
		switch(lab.getCase(ptActuel)) {
			case GOMME:
				lab.setCase(ptActuel, Case.VIDE);
				score += POINTS_PACGOMME;		
				break;
			case SUPER_GOMME:
				lab.setCase(ptActuel, Case.VIDE);
				setSuperForme(true);
				score += POINTS_SUPERGOMME;			
				break;
			default: 	
				break;
		}
		
		/**
		 * Si Pacman est dans l'�tat super forme, � partir d'un certain seuil, il doit
		 * alors revenir dans son �tat normal.
		 */
		if(estEnSuperForme && getNbrDeplacements() > Jeu.conversionFrames(DUREE_SUPERFORME))
			setSuperForme(false);
		
		return super.deplacer(entites, lab);
	}
	/**
	 * M�thode checkEnTrainDeManger() : Cette m�thode prend en param�tre un objet labyrinthe et v�rifie si
	 * Pacman est en train de manger une pacgomme ou non. 
	 * @param lab
	 * @return true si il y a une gomme sur la case, false sinon.
	 */
	public boolean checkEnTrainDeManger(Labyrinthe lab) {
		Point2D adj = lab.adjacent(getPointActuel(), getDirectionActuelle(),1);
		if(lab.getCase(getPointActuel()).contientNourriture() ||
		(lab.getCase(adj).contientNourriture())) {		
			return true;
		}
		return false;
	}
	/**
	 * M�thode getDirectionSuivante() : Cette m�thode prend en param�tre une liste d'entit�s (Pacman et les fant�mes) et un
	 * objet labyrinthe. Elle permet de renvoie la direction suivante prise par Pacman en fontion de sa position actuelle.
	 * 
	 * @param entites
	 * @param lab
	 * @return directionSuivante prise par Pacman par rapport � la case actuelle.
	 */
	@Override
	public Direction getDirectionSuivante(List<Entite> entites, Labyrinthe lab) {
		Point2D adj = lab.adjacent(getPointActuel(), directionSuivante,1);
		
		if(!peutSeDeplacer(lab.getCase(adj)))
			directionSuivante = getDirectionActuelle();
		
		return directionSuivante;
	}
	/**
	 * M�thode resetPoint() : Cette m�thode permet de r�initialiser une case du labyrinthe apr�s 
	 * les d�placements de Pacman.
	 */
	@Override
	public void resetPoint() {
		super.resetPoint();
		setSuperForme(false);
	}
	
	/**
	 * M�thode manger() : Cette m�thode prennant en param�tre un objet fant�me 
	 * permet de savoir si les tentatives de Pacman de manger les fant�mes
	 * ont r�ussi ou non. 
	 * 
	 * @param fantome
	 * @return Retourne true si Pacman a mang� un fant�me, false sinon.
	 */
	public boolean manger(Fantome fantome) {
		
		if(!(fantome.getMode().equals(Mode.EFFRAYE) 
		|| fantome.getMode().equals(Mode.ESTMANGE))) {
			--nbrVies;
			return false;
		}		
		else if(fantome.getMode().equals(Mode.EFFRAYE)) {
			score += POINTS_FANTOME;
			fantome.setMode(Mode.ESTMANGE);
			return true;
		}
		return true;
	}
	
	@Override
	/**
	 * Cette m�thode est non-utilis�e, mais elle doit n�c�ssairement �tre impl�ment�e
	 * pour assurer le bon fonctionnement de la classe car la classe h�rite de toutes
	 * les m�thodes de l'interface KeyListener.
	 */
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	/**
	 * Cette m�thode est non-utilis�e, mais elle doit n�c�ssairement �tre impl�ment�e
	 * pour assurer le bon fonctionnement de la classe car la classe h�rite de toutes
	 * les m�thodes de l'interface KeyListener.
	 */
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}

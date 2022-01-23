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
 * La classe Pacman représente le personnage principal du jeu incarné par le joueur. 
 * Ses déplacements sont contrôlés avec les commandes 'Haut/Bas/Gauche/Droite'
 * 
 * Pacman est vulnérable face aux fantômes. Ainsi, lorsqu'il se déplace, son but sera
 * de les éviter afin de ne pas perdre. Cependant, lorsqu'il consomme une super gomme,
 * il devient invincible et a la possibilité de manger les fantômes et de les faire retourner
 * à leur point de départ pour une durée déterminée.
 * 
 * Pacman gagne s'il arrive à manger toutes les gommes du labyrinthe, le score du joueur
 * est calculé en fonction du nombre de gommes, de super gommes et de fantômes qu'il mange.
 * 
 * Cette classe est liée à la classe entité par une relation d'héritage (Pacman étant une entité incarnable du jeu)
 * et implémente l'interface KeyListener disponible dans la librairie java.awt.event par défaut, cette interface
 * contient des méthodes prennant en charge des événements/interactions clavier. Dans notre cas, nous avons
 * redéfini ces méthodes en les adaptant à notre jeu, afin que le joueur puisse déplacer Pacman.
 *
 *
 * @author Christian
 * @author Darlène
 * @author Lisa-Marie
 *
 */
public class Pacman extends Entite implements KeyListener {
	
	/** Constante représentant le nombre de points gagnés par Pacman en mangeant une gomme. */
	private static final int POINTS_PACGOMME  = 10;
	/** Constante représentant le nombre de points gagnés par Pacman en mangeant un fantôme. */
	private static final int POINTS_FANTOME = 200;
	/** Constante représentant le nombre de points gagnés par Pacman en mangeant une super gomme. */
	private static final int POINTS_SUPERGOMME = 50;
	
	/** Constante représentant la durée durant laquelle Pacman est en super forme après
	 *  avoir mangé une super gomme. */
	private static final int DUREE_SUPERFORME = 3000;
	/**
	 * Booléen permettant de savoir si Pacman est en super forme (true) ou non (false)
	 */
	private boolean estEnSuperForme;
	/**
	 * Entiers représentant le score du joueur et le nombre de vies de Pacman
	 */
	private int score, nbrVies;
	/**
	 * Variable de type énuméré pouvant prendre 4 valeurs (HAUT,BAS,GAUCHE,DROITE) 
	 * selon les déplacements de Pacman
	 */
	private Direction directionSuivante;
	/**
	 * Premier constructeur de la classe Pacman faisant appel au deuxième 
	 * constructeur de la classe Pacman, il est possible de faire appel à ce 
	 * constructeur en dehors de la classe Pacman.
	 * @param pointInitial
	 * @param vitesse
	 */
	public Pacman(Point2D pointInitial, double vitesse) {
		this(pointInitial, vitesse, Direction.GAUCHE);
	}
	/**
	 * Deuxième constructeur de la classe Pacman, pour initialiser les paramètres
	 * de la classe Entite dont Pacman hérite, on ne peut pas faire appel à ce
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
	 * Méthode getScore() : Cette méthode est un getter qui retourne le nombre de points du joueur.
	 * @return score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * Méthode getNbrVies() : Cette méthode est un getter qui retourne le nombre de vies du joueur.
	 * @return nombre de vies
	 */
	public int getNbrVies() {
		return nbrVies;
	}
	/**
	 * Méthode resetNbrVies() : Cette méthode permet de réinitialiser le nombre de vies de Pacman à 3
	 */
	public void resetNbrVies() {
		nbrVies = 3;
	}
	/**
	 * Méthode resetScore() : Cette méthode permet de réinitialiser le score du joueur
	 */
	public void resetScore() {
		score = 0;
	}
	
	/**
	 * Méthode enSuperForme() : Cette méthode permet de savoir si Pacman est en état de super forme
	 * ou non. L'état de super forme caractérise le fait que Pacman puisse manger des fantômes ou non.
	 * 
	 * @return true si Pacman est en super forme, false sinon
	 */
	public boolean enSuperForme() {
		return estEnSuperForme;
	}
	
	/**
	 * Méthode setSuperForme() : Cette méthode permet d'activer le mode super forme de Pacman, en fonction
	 * du paramètre booléen superForme récupéré depuis la méthode enSuperForme().
	 * Si superForme vaut true, le mode super forme est activé, si superforme vaut false, il n'est pas activé.
	 * @param superForme
	 */
	public void setSuperForme(boolean superForme) {
		estEnSuperForme =  superForme;
		setNbrDeplacements(0);
	}
	/**
	 * Méthode keyPressed() : Cette méthode qui prend en paramètre un événement (interaction clavier avec 
	 * les touches 'HAUT/BAS/GAUCHE/DROITE" permet à Pacman de changer de direction parmi les 4 directions
	 * disponibles en fonction de la touche pressée.
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
	 * Méthode déplacer() : Cette méthode qui prend en paramètre une liste représentant les entités du jeu
	 * (Pacman et les fantômes) et un objet labyrinthe permet de savoir si Pacman est en déplacement ou non et
	 * quelles sont les cases que ce dernier traverse (cases vides, cases avec des gommes, cases avec 
	 * des super gommes
	 * 
	 * @return true si pacman est en état de déplacement (déplacement normal ou déplacement en super forme), false sinon.
	 */
	@Override
	public boolean deplacer(List<Entite> entites, Labyrinthe lab) {		
		/*
		 * On considère la valeur de la case actuelle (case vide, case pacgomme,
		 * case supergomme) et on cible la case suivante. Le score est augmenté 
		 * en même temps que pacman se déplace vers la case suivante, en fonction
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
		 * Si Pacman est dans l'état super forme, à partir d'un certain seuil, il doit
		 * alors revenir dans son état normal.
		 */
		if(estEnSuperForme && getNbrDeplacements() > Jeu.conversionFrames(DUREE_SUPERFORME))
			setSuperForme(false);
		
		return super.deplacer(entites, lab);
	}
	/**
	 * Méthode checkEnTrainDeManger() : Cette méthode prend en paramètre un objet labyrinthe et vérifie si
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
	 * Méthode getDirectionSuivante() : Cette méthode prend en paramètre une liste d'entités (Pacman et les fantômes) et un
	 * objet labyrinthe. Elle permet de renvoie la direction suivante prise par Pacman en fontion de sa position actuelle.
	 * 
	 * @param entites
	 * @param lab
	 * @return directionSuivante prise par Pacman par rapport à la case actuelle.
	 */
	@Override
	public Direction getDirectionSuivante(List<Entite> entites, Labyrinthe lab) {
		Point2D adj = lab.adjacent(getPointActuel(), directionSuivante,1);
		
		if(!peutSeDeplacer(lab.getCase(adj)))
			directionSuivante = getDirectionActuelle();
		
		return directionSuivante;
	}
	/**
	 * Méthode resetPoint() : Cette méthode permet de réinitialiser une case du labyrinthe après 
	 * les déplacements de Pacman.
	 */
	@Override
	public void resetPoint() {
		super.resetPoint();
		setSuperForme(false);
	}
	
	/**
	 * Méthode manger() : Cette méthode prennant en paramètre un objet fantôme 
	 * permet de savoir si les tentatives de Pacman de manger les fantômes
	 * ont réussi ou non. 
	 * 
	 * @param fantome
	 * @return Retourne true si Pacman a mangé un fantôme, false sinon.
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
	 * Cette méthode est non-utilisée, mais elle doit nécéssairement être implémentée
	 * pour assurer le bon fonctionnement de la classe car la classe hérite de toutes
	 * les méthodes de l'interface KeyListener.
	 */
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	/**
	 * Cette méthode est non-utilisée, mais elle doit nécéssairement être implémentée
	 * pour assurer le bon fonctionnement de la classe car la classe hérite de toutes
	 * les méthodes de l'interface KeyListener.
	 */
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}

package jeu_pacman_entites;

import java.awt.geom.Point2D;
import java.util.List;

import jeu_pacman_affichage.SpriteAnime;
import jeu_pacman_labyrinthe.Labyrinthe;

/**
 * La classe Pinky mod�lise le fant�me de couleur rose du jeu Pacman.
 * Cette classe est li�e � la classe Fant�me (donc par extension � la classe Entit�)
 * par une relation d'h�ritage.
 * 
 * @author Darl�ne
 *
 */
public class Pinky extends Fantome {
	/**
	 * Premier constructeur de la classe Pinky, faisant appel au deuxi�me 
	 * constructeur de la classe Pinky, il est possible de faire appel � ce 
	 * constructeur en dehors de la classe Pinky.
	 * 
	 * @param ptInitial
	 * @param ptDispersion
	 * @param ptSortie
	 * @param mode
	 */
	public Pinky(Point2D ptInitial, Point2D ptDispersion, Point2D ptSortie, Mode mode) {
		this(ptInitial, ptDispersion, ptSortie, mode, Direction.HAUT);
	}
	/**
	 * Deuxi�me constructeur de la classe Pinky, pour initialiser les param�tres
	 * de la classe Fant�me dont Pinky h�rite, on ne peut pas faire appel � ce
	 * constructeur en dehors de la classe Pinky.
	 * 
	 * @param ptInitial
	 * @param ptDispersion
	 * @param ptSortie
	 * @param mode
	 * @param dir
	 */
	public Pinky(Point2D ptInitial, Point2D ptDispersion, Point2D ptSortie, Mode mode, Direction dir) {
		super(ptInitial, ptDispersion, ptSortie, mode, new SpriteAnime(46, dir), dir);
	}
	/**
	 * M�thode getDureeImmobile() : Cette m�thode permet de d�finir la dur�e durant
	 * laquelle Pinky est immobile au point de d�part des fant�mes. C'est �galement
	 * la dur�e qui s'�coule avant la r�apparition du fant�me en mode chasse.
	 * 
	 * @return Dur�e d'immobilit� de Pinky au niveau du point de d�part des fant�mes
	 */
	@Override
	public int getDureeImmobile() {
		return 10000;
	}
	

	/**
	 * M�thode getPointChasse() : Cette m�thode permet de d�finir la mani�re selon laquelle le fant�me
	 * Pinky va se d�placer en mode chasse. Par d�faut, Pinky se d�place en ciblant une case 
	 * � une distance de quatre cases en face de la position actuelle de Pacman.
	 *
	 * @param entites
	 * @param lab
	 * @return la case se trouvant � une distance de quatre cases en face de la position actuelle de Pacman.
	 */
	@Override
	public Point2D getPointChasse(List<Entite> entites, Labyrinthe lab) {
		return lab.adjacent(entites.get(0),4);
	}

}

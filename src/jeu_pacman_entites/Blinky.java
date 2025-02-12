package jeu_pacman_entites;

import java.awt.geom.Point2D;
import java.util.List;

import jeu_pacman_affichage.SpriteAnime;
import jeu_pacman_labyrinthe.Labyrinthe;

/**
 * La classe Blinky mod�lise le fant�me de couleur rouge du jeu Pacman.
 * Cette classe est li�e � la classe Fant�me (donc par extension � la classe Entit�)
 * par une relation d'h�ritage.
 * 
 * @author Lisa-Marie
 * 
 */
public class Blinky extends Fantome {
	/**
	 * Premier constructeur de la classe Blinky, faisant appel au deuxi�me 
	 * constructeur de la classe Blinky, il est possible de faire appel � ce 
	 * constructeur en dehors de la classe Blinky.
	 * 
	 * @param ptInitial
	 * @param ptDispersion
	 * @param ptSortie
	 * @param mode
	 */
	public Blinky(Point2D ptInitial, Point2D ptDispersion, Point2D ptSortie, Mode mode) {
		this(ptInitial, ptDispersion, ptSortie, mode, Direction.HAUT);
	}
	/**
	 * Deuxi�me constructeur de la classe Blinky, pour initialiser les param�tres
	 * de la classe Fant�me dont Blinky h�rite, on ne peut pas faire appel � ce
	 * constructeur en dehors de la classe Blinky.
	 * @param ptInitial
	 * @param ptDispersion
	 * @param ptSortie
	 * @param mode
	 * @param direction
	 */
	public Blinky(Point2D ptInitial, Point2D ptDispersion, Point2D ptSortie, Mode mode, Direction direction) {
		super(ptInitial, ptDispersion, ptSortie, mode, new SpriteAnime(34, direction), direction);
	}
	/**
	 * M�thode getDureeImmobile() : Cette m�thode permet de d�finir la dur�e durant
	 * laquelle Blinky est immobile au point de d�part des fant�mes. C'est �galement
	 * la dur�e qui s'�coule avant la r�apparition du fant�me en mode chasse.
	 * 
	 * @return Dur�e d'immobilit� de Blinky au niveau du point de d�part des fant�mes
	 */
	@Override
	public int getDureeImmobile() {
		return 0;
	}
	
	/**
	 * M�thode getPointChasse() : Cette m�thode permet de d�finir la mani�re selon laquelle le fant�me
	 * Blinky va se d�placer en mode chasse. Par d�faut, Blinky se d�place en ciblant la case exacte
	 * sur laquelle se situe Pacman
	 *
	 * @param entites
	 * @param lab
	 * @return la case exacte o� se situe Pacman (position actuelle de Pacman).
	 */
	@Override
	public Point2D getPointChasse(List<Entite> entites, Labyrinthe lab) {
		return entites.get(0).getPointActuel();
	}

}

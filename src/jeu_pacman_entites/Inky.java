package jeu_pacman_entites;

import java.awt.geom.Point2D;
import java.util.List;

import jeu_pacman_affichage.SpriteAnime;
import jeu_pacman_labyrinthe.Labyrinthe;

/**
 * La classe Inky mod�lise le fant�me de couleur bleue du jeu Pacman.
 * Cette classe est li�e � la classe Fant�me (donc par extension � la classe Entit�)
 * par une relation d'h�ritage.
 * 
 * @author Christian
 */
public class Inky extends Fantome {
	/**
	 * Premier constructeur de la classe Inky, faisant appel au deuxi�me 
	 * constructeur de la classe Inky, il est possible de faire appel � ce 
	 * constructeur en dehors de la classe Inky.
	 * 
	 * @param ptInitial
	 * @param ptDispersion
	 * @param ptSortie
	 * @param mode
	 */
	public Inky(Point2D ptInitial, Point2D ptDispersion, Point2D ptSortie, Mode mode) {
		this(ptInitial, ptDispersion, ptSortie, mode, Direction.HAUT);
	}
	/**
	 * Deuxi�me constructeur de la classe Inky, pour initialiser les param�tres
	 * de la classe Fant�me dont Inky h�rite, on ne peut pas faire appel � ce
	 * constructeur en dehors de la classe Inky.
	 * 
	 * @param ptInitial
	 * @param ptDispersion
	 * @param ptSortie
	 * @param mode
	 * @param direction
	 */
	public Inky(Point2D ptInitial, Point2D ptDispersion, Point2D ptSortie, Mode mode, Direction direction) {
		super(ptInitial, ptDispersion, ptSortie, mode, new SpriteAnime(42, direction), direction);
	}
	/**
	 * M�thode getDureeImmobile() : Cette m�thode permet de d�finir la dur�e durant
	 * laquelle Inky est immobile au point de d�part des fant�mes. C'est �galement
	 * la dur�e qui s'�coule avant la r�apparition du fant�me en mode chasse.
	 * 
	 * @return Dur�e d'immobilit� d'Inky au niveau du point de d�part des fant�mes
	 */
	@Override
	public int getDureeImmobile() {
		return 6000; 
	}
	/**
	 * M�thode getPointChasse() : Cette m�thode permet de d�finir la mani�re selon laquelle le fant�me
	 * Inky va se d�placer en mode chasse. Par d�faut, Inky se d�place en ciblant une case 
	 * � une distance de deux cases en face de la position actuelle de Blinky.
	 *
	 * @param entites
	 * @param lab
	 * @return la case se trouvant � deux cases en face de la position actuelle du fant�me Blinky.
	 */
	@Override
	public Point2D getPointChasse(List<Entite> entites, Labyrinthe lab) {
		Point2D t1 = entites.get(1).getPointActuel();
		Point2D t2 = lab.adjacent(entites.get(0), 2);
		return new Point2D.Double(t1.getX() + 2 * (t2.getX() - t1.getX()), 
		t1.getY() - 2 * (t1.getX() - t2.getY()));
	}

}

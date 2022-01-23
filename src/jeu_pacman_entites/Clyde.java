package jeu_pacman_entites;

import java.awt.geom.Point2D;
import java.util.List;

import jeu_pacman_affichage.SpriteAnime;
import jeu_pacman_labyrinthe.Labyrinthe;

/**
 * La classe Clyde mod�lise le fant�me de couleur orange du jeu Pacman.
 * Cette classe est li�e � la classe Fant�me (donc par extension � la classe Entit�)
 * par une relation d'h�ritage.
 * 
 * @author Lisa-Marie
 * 
 */
public class Clyde extends Fantome {
	/**
	 * Premier constructeur de la classe Clyde, faisant appel au deuxi�me 
	 * constructeur de la classe Clyde, il est possible de faire appel � ce 
	 * constructeur en dehors de la classe Clyde.
	 * 
	 * @param ptInitial
	 * @param ptDispersion
	 * @param ptSortie
	 * @param mode
	 */
	public Clyde(Point2D ptInitial, Point2D ptDispersion, Point2D ptSortie, Mode mode) {
		this(ptInitial, ptDispersion, ptSortie, mode, Direction.HAUT);
	}
	/**
	 * Deuxi�me constructeur de la classe Clyde, pour initialiser les param�tres
	 * de la classe Fant�me dont Clyde h�rite, on ne peut pas faire appel � ce
	 * constructeur en dehors de la classe Clyde. 
	 * @param ptInitial
	 * @param ptDispersion
	 * @param ptSortie
	 * @param mode
	 * @param dir
	 */
	public Clyde(Point2D ptInitial, Point2D ptDispersion, Point2D ptSortie, Mode mode, Direction dir) {
		super(ptInitial, ptDispersion, ptSortie, mode, new SpriteAnime(38, dir), dir);
	}
	
	/**
	 * M�thode getDureeImmobile() : Cette m�thode permet de d�finir la dur�e durant
	 * laquelle Clyde est immobile au point de d�part des fant�mes. C'est �galement
	 * la dur�e qui s'�coule avant la r�apparition du fant�me en mode chasse.
	 * 
	 * @return Dur�e d'immobilit� de Clyde au niveau du point de d�part des fant�mes
	 */
	@Override
	public int getDureeImmobile() {
		return 2000;
	}
	/**
	 * M�thode getPointChasse() : Cette m�thode permet de d�finir la mani�re selon laquelle le fant�me
	 * Clyde va se d�placer en mode chasse. Par d�faut, Clyde se d�place en ciblant la case exacte ou se 
	 * situe Pacman si la distance entre la position actuelle de Pacman et la sienne est sup�rieure ou
	 * �gale � 8 cases. Sinon, il cible une case comme s'il �tait en mode dispersion
	 *
	 * @param entites
	 * @param lab
	 * @return la case cibl�e par Clyde en fonction des conditions indiqu�es pr�c�damment.
	 */
	@Override
	public Point2D getPointChasse(List<Entite> entites, Labyrinthe lab) {
		Point2D point = entites.get(0).getPointActuel();
		double dist = getPointActuel().distance(point);
		return (dist >= 8 * Labyrinthe.getTailleCase()) ? point : getPointDeDispersion();
	}

}

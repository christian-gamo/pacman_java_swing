package jeu_pacman_entites;

import java.awt.geom.Point2D;
import java.util.List;

import jeu_pacman_affichage.SpriteAnime;
import jeu_pacman_labyrinthe.Labyrinthe;

/**
 * La classe Clyde modélise le fantôme de couleur orange du jeu Pacman.
 * Cette classe est liée à la classe Fantôme (donc par extension à la classe Entité)
 * par une relation d'héritage.
 * 
 * @author Lisa-Marie
 * 
 */
public class Clyde extends Fantome {
	/**
	 * Premier constructeur de la classe Clyde, faisant appel au deuxième 
	 * constructeur de la classe Clyde, il est possible de faire appel à ce 
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
	 * Deuxième constructeur de la classe Clyde, pour initialiser les paramètres
	 * de la classe Fantôme dont Clyde hérite, on ne peut pas faire appel à ce
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
	 * Méthode getDureeImmobile() : Cette méthode permet de définir la durée durant
	 * laquelle Clyde est immobile au point de départ des fantômes. C'est également
	 * la durée qui s'écoule avant la réapparition du fantôme en mode chasse.
	 * 
	 * @return Durée d'immobilité de Clyde au niveau du point de départ des fantômes
	 */
	@Override
	public int getDureeImmobile() {
		return 2000;
	}
	/**
	 * Méthode getPointChasse() : Cette méthode permet de définir la manière selon laquelle le fantôme
	 * Clyde va se déplacer en mode chasse. Par défaut, Clyde se déplace en ciblant la case exacte ou se 
	 * situe Pacman si la distance entre la position actuelle de Pacman et la sienne est supérieure ou
	 * égale à 8 cases. Sinon, il cible une case comme s'il était en mode dispersion
	 *
	 * @param entites
	 * @param lab
	 * @return la case ciblée par Clyde en fonction des conditions indiquées précédamment.
	 */
	@Override
	public Point2D getPointChasse(List<Entite> entites, Labyrinthe lab) {
		Point2D point = entites.get(0).getPointActuel();
		double dist = getPointActuel().distance(point);
		return (dist >= 8 * Labyrinthe.getTailleCase()) ? point : getPointDeDispersion();
	}

}

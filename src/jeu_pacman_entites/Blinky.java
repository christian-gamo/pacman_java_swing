package jeu_pacman_entites;

import java.awt.geom.Point2D;
import java.util.List;

import jeu_pacman_affichage.SpriteAnime;
import jeu_pacman_labyrinthe.Labyrinthe;

/**
 * La classe Blinky modélise le fantôme de couleur rouge du jeu Pacman.
 * Cette classe est liée à la classe Fantôme (donc par extension à la classe Entité)
 * par une relation d'héritage.
 * 
 * @author Lisa-Marie
 * 
 */
public class Blinky extends Fantome {
	/**
	 * Premier constructeur de la classe Blinky, faisant appel au deuxième 
	 * constructeur de la classe Blinky, il est possible de faire appel à ce 
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
	 * Deuxième constructeur de la classe Blinky, pour initialiser les paramètres
	 * de la classe Fantôme dont Blinky hérite, on ne peut pas faire appel à ce
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
	 * Méthode getDureeImmobile() : Cette méthode permet de définir la durée durant
	 * laquelle Blinky est immobile au point de départ des fantômes. C'est également
	 * la durée qui s'écoule avant la réapparition du fantôme en mode chasse.
	 * 
	 * @return Durée d'immobilité de Blinky au niveau du point de départ des fantômes
	 */
	@Override
	public int getDureeImmobile() {
		return 0;
	}
	
	/**
	 * Méthode getPointChasse() : Cette méthode permet de définir la manière selon laquelle le fantôme
	 * Blinky va se déplacer en mode chasse. Par défaut, Blinky se déplace en ciblant la case exacte
	 * sur laquelle se situe Pacman
	 *
	 * @param entites
	 * @param lab
	 * @return la case exacte où se situe Pacman (position actuelle de Pacman).
	 */
	@Override
	public Point2D getPointChasse(List<Entite> entites, Labyrinthe lab) {
		return entites.get(0).getPointActuel();
	}

}

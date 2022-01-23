package jeu_pacman_entites;

import java.awt.geom.Point2D;
import java.util.List;

import jeu_pacman_affichage.SpriteAnime;
import jeu_pacman_labyrinthe.Labyrinthe;

/**
 * La classe Pinky modélise le fantôme de couleur rose du jeu Pacman.
 * Cette classe est liée à la classe Fantôme (donc par extension à la classe Entité)
 * par une relation d'héritage.
 * 
 * @author Darlène
 *
 */
public class Pinky extends Fantome {
	/**
	 * Premier constructeur de la classe Pinky, faisant appel au deuxième 
	 * constructeur de la classe Pinky, il est possible de faire appel à ce 
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
	 * Deuxième constructeur de la classe Pinky, pour initialiser les paramètres
	 * de la classe Fantôme dont Pinky hérite, on ne peut pas faire appel à ce
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
	 * Méthode getDureeImmobile() : Cette méthode permet de définir la durée durant
	 * laquelle Pinky est immobile au point de départ des fantômes. C'est également
	 * la durée qui s'écoule avant la réapparition du fantôme en mode chasse.
	 * 
	 * @return Durée d'immobilité de Pinky au niveau du point de départ des fantômes
	 */
	@Override
	public int getDureeImmobile() {
		return 10000;
	}
	

	/**
	 * Méthode getPointChasse() : Cette méthode permet de définir la manière selon laquelle le fantôme
	 * Pinky va se déplacer en mode chasse. Par défaut, Pinky se déplace en ciblant une case 
	 * à une distance de quatre cases en face de la position actuelle de Pacman.
	 *
	 * @param entites
	 * @param lab
	 * @return la case se trouvant à une distance de quatre cases en face de la position actuelle de Pacman.
	 */
	@Override
	public Point2D getPointChasse(List<Entite> entites, Labyrinthe lab) {
		return lab.adjacent(entites.get(0),4);
	}

}

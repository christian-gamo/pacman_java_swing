package jeu_pacman_entites;

import java.awt.geom.Point2D;
import java.util.List;

import jeu_pacman_affichage.SpriteAnime;
import jeu_pacman_labyrinthe.Labyrinthe;

/**
 * La classe Inky modélise le fantôme de couleur bleue du jeu Pacman.
 * Cette classe est liée à la classe Fantôme (donc par extension à la classe Entité)
 * par une relation d'héritage.
 * 
 * @author Christian
 */
public class Inky extends Fantome {
	/**
	 * Premier constructeur de la classe Inky, faisant appel au deuxième 
	 * constructeur de la classe Inky, il est possible de faire appel à ce 
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
	 * Deuxième constructeur de la classe Inky, pour initialiser les paramètres
	 * de la classe Fantôme dont Inky hérite, on ne peut pas faire appel à ce
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
	 * Méthode getDureeImmobile() : Cette méthode permet de définir la durée durant
	 * laquelle Inky est immobile au point de départ des fantômes. C'est également
	 * la durée qui s'écoule avant la réapparition du fantôme en mode chasse.
	 * 
	 * @return Durée d'immobilité d'Inky au niveau du point de départ des fantômes
	 */
	@Override
	public int getDureeImmobile() {
		return 6000; 
	}
	/**
	 * Méthode getPointChasse() : Cette méthode permet de définir la manière selon laquelle le fantôme
	 * Inky va se déplacer en mode chasse. Par défaut, Inky se déplace en ciblant une case 
	 * à une distance de deux cases en face de la position actuelle de Blinky.
	 *
	 * @param entites
	 * @param lab
	 * @return la case se trouvant à deux cases en face de la position actuelle du fantôme Blinky.
	 */
	@Override
	public Point2D getPointChasse(List<Entite> entites, Labyrinthe lab) {
		Point2D t1 = entites.get(1).getPointActuel();
		Point2D t2 = lab.adjacent(entites.get(0), 2);
		return new Point2D.Double(t1.getX() + 2 * (t2.getX() - t1.getX()), 
		t1.getY() - 2 * (t1.getX() - t2.getY()));
	}

}

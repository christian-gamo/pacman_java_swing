package jeu_pacman_labyrinthe;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

import jeu_pacman_entites.Entite;
import jeu_pacman_entites.Entite.Direction;


/**
 * 
 * @author Christian Darlène Lisa-Marie
 *
 */
public class Labyrinthe {
	/**Constante réprésentant la taille d'une case en pixels*/
	private static final int TAILLE_CASE = 16;
	/**ArrayList (Collection) à 2 dimensions contenant les cases d'un niveau. On
	 * conserve également une copie pour réinitialiser le niveau.*/
	private ArrayList<ArrayList<Case>> niveau, niveauInitial;
	
	/**
	 * Constructeur de la classe Labyrinthe,  il est possible de faire appel à ce 
	 * constructeur en dehors de la classe Labyrinthe pour en créer une nouvelle instance.
	 * 
	 * @param labyrinthe
	 */
	public Labyrinthe(ArrayList<ArrayList<Case>> labyrinthe) {
		
		this.niveau = labyrinthe;
		this.niveauInitial = getCopieLabyrinthe(labyrinthe);
	}
	/**
	 * Méthode getCopieLabyrinthe() : Cette méthode permet d'obtenir une copie du labyrinthe d'origine.
	 * Elle est notamment utile pour réinitialiser le labyrinthe
	 * 
	 * @param original
	 * @return ArrayList représentant une copie du labyrinthe original
	 */
	public ArrayList<ArrayList<Case>> getCopieLabyrinthe(ArrayList<ArrayList<Case>> original){
        ArrayList<ArrayList<Case>> copie = new ArrayList<>();

        for (ArrayList<Case> arr: original){
            copie.add(new ArrayList<Case>(arr));
        }

        return copie;
    }
	
	
	/**
	 * Méthode getLargeur() : Cette méthode renvoie le nombre de lignes dans la grille modélisant le labyrinthe.
	 * 
	 * @return nombre de lignes dans la grille représentant le labyrinthe
	 * 
	 */
	public int getLargeur() {
		return niveau.size();
	}
	
	/**
	 * Méthode getLongueur() : Cette méthode renvoie le nombre de colonnes dans la grille modélisant le labyrinthe.
	 * 
	 * @return nombre de colonnes dans la grille représentant le labyrinthe
	 * 
	 */
	public int getLongueur() {
		return niveau.get(0).size();
	}
	
	/**
	 * Méthode getTailleCase() : Cette méthode renvoie la taille des cases
	 * 
	 * @return taille des cases
	 * 
	 */
	public static int getTailleCase() {
		return TAILLE_CASE;
	}
	/**
	 * Méthode reset() : Cette méthode permet de réinitialiser le labyrinthe pour revenir à l'état initial
	 * du labyrinthe
	 */
	public void reset() {
		niveau = getCopieLabyrinthe(niveauInitial);
	}
	
	/**
	 * Méthode estFini() : Cette méthode permet de déterminer si il reste des gommes/super-gommes
	 * sur le labyrinthe ou non. Cette méthode est utilisée pour savoir si le joueur à gagné et s'il est possible de passer
	 * au niveau de difficulté suivant ou non.
	 * 
	 * @return true si il reste des gommes/super-gommes, false sinon
	 */
	public boolean estComplete() {
		for(int i = 0; i < getLargeur(); i++)
			for(int j = 0; j < getLongueur(); j++)
				if(niveau.get(i).get(j).equals(Case.GOMME) || 
						niveau.get(i).get(j).equals(Case.SUPER_GOMME))
					return false;
		return true;
	}
	
	
	/**
	 * Méthode getCase() : 
	 * Cette méthode permet de récupérer un objet de type Case à partir d'un objet de Type Point2D
	 * qui correspond à des coordonnées sur la fenêtre de jeu.
	 * L'objet Case récupéré correspond donc aux coordonnées d'un Point sur l'interface graphique.
	 * 
	 * @param point
	 * @return objet de type Case correspondant au point en paramètre dans le labyrinthe
	 */
	public Case getCase(Point2D point) {
		Point tile = pointLabyrinthe(point);
		return niveau.get(tile.y).get(tile.x);
	}
	/**
	 * Méthode setCase() : Cette méthode permet d'affecter une valeur spécifique associée au type énuméré Case, à
	 * une case du labyrinthe à partir de ses coordonnées dans la fenêtre
	 * 
	 * @param point
	 * @param terrain
	 */
	public void setCase(Point2D point, Case terrain) {
		Point tile = pointLabyrinthe(point);
		niveau.get(tile.y).set(tile.x, terrain);
	}
	
	/**
	 * Méthode pointLabyrinthe() : Cette méthode renvoie les coordonnées d'un point de la fenêtre 
	 * et les associe à une case du labyrinthe.
	 * @param point
	 * @return Point correspondant à une case du labyrinthe
	 */
	public Point pointLabyrinthe(Point2D point) {
		Point2D wrap = wrapAround(point);
		int longueurPoint  = Labyrinthe.TAILLE_CASE * getLongueur();
		int largeurPoint = Labyrinthe.TAILLE_CASE * getLargeur();
		return new Point((int) (wrap.getX() / longueurPoint * getLongueur()),
				(int) (wrap.getY() / largeurPoint * getLargeur()));
	}
	
	/**
	 * Méthode wrapAround() : Cette méthode permet d'effectuer un "wrap" sur une case donnée pour
	 * éviter les exceptions de type OutOfBoundsExceptions.
	 * Il est utilisé pour passer de l'autre coté de l'écran à travers un tunnel.
	 * Cet aspect des jeux rétros est appelé le wrapAround.
	 * @param point
	 * @return une case enveloppée
	 */
	public Point2D wrapAround(Point2D point) {
		int pWidth  = Labyrinthe.TAILLE_CASE * getLongueur();
		int pHeight = Labyrinthe.TAILLE_CASE * getLargeur();
		
		return new Point2D.Double((point.getX() % pWidth + pWidth) % pWidth, 
								  (point.getY() % pHeight + pHeight) % pHeight);
	}
	
	/**
	 * Méthode centrePoint () : Cette méthode permet de récupérer le centre d'une case en coordonnées
	 * la fenêtre de jeu.
	 * @param point
	 * @return centre de la case de classe Point
	 */
	public Point2D centrePoint2D(Point2D point) {
		return centrePoint(pointLabyrinthe(point));
	}
	
	/**
	 * Méthode centrePoint () : Cette méthode permet de récupérer le centre d'une case en coordonnées
	 * la fenêtre de jeu.
	 * @param point
	 * @return centre de la case de classe Point
	 */
	public Point2D centrePoint(Point point) {
		return wrapAround(new Point2D.Double(point.x * Labyrinthe.TAILLE_CASE + Labyrinthe.TAILLE_CASE / 2.0,
				point.y * Labyrinthe.TAILLE_CASE + Labyrinthe.TAILLE_CASE / 2.0));
	}
	
	/**
	 * Méthode adjacent() :
	 * Cette méthode permet de retourner le centre d'une case dans une direction donné d'une distance de x cases
	 * Elle est utilisé par les fantomes pour savoir vers quel case se diriger.
	 * @param ptDepart
	 * @param direction
	 * @param distance
	 * @return centre de la case visé
	 */
	public Point2D adjacent(Point2D ptDepart, Direction direction, double distance) {
		double pDist = distance * Labyrinthe.TAILLE_CASE;
		
		switch(direction) {
			case HAUT: 	return centrePoint2D(new Point2D.Double(ptDepart.getX(), ptDepart.getY() - pDist));
			case BAS: 	return centrePoint2D(new Point2D.Double(ptDepart.getX(), ptDepart.getY() + pDist));
			case GAUCHE: 	return centrePoint2D(new Point2D.Double(ptDepart.getX() - pDist, ptDepart.getY()));
			default: 	return centrePoint2D(new Point2D.Double(ptDepart.getX() + pDist, ptDepart.getY()));
		}
	}
	/**
	 * Méthode adjacent() : Permet de retourner le centre d'une case dans une direction donné d'une distance de x cases
	 * Elle est utilisé par les fantomes pour savoir vers quel case se diriger.
	 * @param entite
	 * @param distance
	 * @return centre de la case visé
	 */
	public Point2D adjacent(Entite entite, double distance) {
		return adjacent(entite.getPointActuel(), entite.getDirectionActuelle(), distance);
	}
	
	/**
	 * Méthode detectionCollision() : Cette méthode permet de déterminer si deux points de la fenêtre
	 * de jeu sont situés au même coordonnées.
	 * 
	 * @param p1
	 * @param p2
	 * @return true si les deux points sont aux même coordonnées, false sinon 
	 */
	public boolean detectionCollision(Point2D p1, Point2D p2) {
		return pointLabyrinthe(p1).equals(pointLabyrinthe(p2));
	}
	/**
	 * Méthode detectionCollision() : Cette méthode permet de déterminer 
	 * si deux entités sont entrées en collision sur une même case,
	 * 
	 * @param a1
	 * @param a2
	 * @return true si deux entités entrent en collision, false sinon
	 */
	public boolean detectionCollision(Entite a1, Entite a2) {
		return detectionCollision(a1.getPointActuel(), a2.getPointActuel());
	}
	
	/**
	 * Méthode chargerLabyrinthe() : Cette méthode permet de charger un niveau du labyrinthe à partir d'un fichier texte.
	 * Dans ce fichier texte (.txt) chaque caractère représente l'ordinal (valeur associée) à une case souhaitée dans parmi
	 * celles au choix dans la classe enum Case.
	 * 
	 * @param stringNiv
	 * @param longueur
	 * @param largeur
	 * @return labyrinthe
	 * @throws IOException
	 */
	public static Labyrinthe chargerLabyrinthe(String stringNiv, int longueur, int largeur) throws IOException {
		ArrayList<ArrayList<Case>> lab = new ArrayList<ArrayList<Case>>();
		
		for(int i = 0; i<largeur; i++) {
			lab.add(new ArrayList<Case>());
		}
		
		for(int i = 0; i<largeur; i++) {
			for(int j = 0; j<longueur; j++) {
				
				lab.get(i).add(Case.values()[stringNiv.charAt(i * longueur + j)-48]);			
			}
		}
		
		return new Labyrinthe(lab);
	}
}

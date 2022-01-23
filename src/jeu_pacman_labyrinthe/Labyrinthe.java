package jeu_pacman_labyrinthe;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

import jeu_pacman_entites.Entite;
import jeu_pacman_entites.Entite.Direction;


/**
 * 
 * @author Christian Darl�ne Lisa-Marie
 *
 */
public class Labyrinthe {
	/**Constante r�pr�sentant la taille d'une case en pixels*/
	private static final int TAILLE_CASE = 16;
	/**ArrayList (Collection) � 2 dimensions contenant les cases d'un niveau. On
	 * conserve �galement une copie pour r�initialiser le niveau.*/
	private ArrayList<ArrayList<Case>> niveau, niveauInitial;
	
	/**
	 * Constructeur de la classe Labyrinthe,  il est possible de faire appel � ce 
	 * constructeur en dehors de la classe Labyrinthe pour en cr�er une nouvelle instance.
	 * 
	 * @param labyrinthe
	 */
	public Labyrinthe(ArrayList<ArrayList<Case>> labyrinthe) {
		
		this.niveau = labyrinthe;
		this.niveauInitial = getCopieLabyrinthe(labyrinthe);
	}
	/**
	 * M�thode getCopieLabyrinthe() : Cette m�thode permet d'obtenir une copie du labyrinthe d'origine.
	 * Elle est notamment utile pour r�initialiser le labyrinthe
	 * 
	 * @param original
	 * @return ArrayList repr�sentant une copie du labyrinthe original
	 */
	public ArrayList<ArrayList<Case>> getCopieLabyrinthe(ArrayList<ArrayList<Case>> original){
        ArrayList<ArrayList<Case>> copie = new ArrayList<>();

        for (ArrayList<Case> arr: original){
            copie.add(new ArrayList<Case>(arr));
        }

        return copie;
    }
	
	
	/**
	 * M�thode getLargeur() : Cette m�thode renvoie le nombre de lignes dans la grille mod�lisant le labyrinthe.
	 * 
	 * @return nombre de lignes dans la grille repr�sentant le labyrinthe
	 * 
	 */
	public int getLargeur() {
		return niveau.size();
	}
	
	/**
	 * M�thode getLongueur() : Cette m�thode renvoie le nombre de colonnes dans la grille mod�lisant le labyrinthe.
	 * 
	 * @return nombre de colonnes dans la grille repr�sentant le labyrinthe
	 * 
	 */
	public int getLongueur() {
		return niveau.get(0).size();
	}
	
	/**
	 * M�thode getTailleCase() : Cette m�thode renvoie la taille des cases
	 * 
	 * @return taille des cases
	 * 
	 */
	public static int getTailleCase() {
		return TAILLE_CASE;
	}
	/**
	 * M�thode reset() : Cette m�thode permet de r�initialiser le labyrinthe pour revenir � l'�tat initial
	 * du labyrinthe
	 */
	public void reset() {
		niveau = getCopieLabyrinthe(niveauInitial);
	}
	
	/**
	 * M�thode estFini() : Cette m�thode permet de d�terminer si il reste des gommes/super-gommes
	 * sur le labyrinthe ou non. Cette m�thode est utilis�e pour savoir si le joueur � gagn� et s'il est possible de passer
	 * au niveau de difficult� suivant ou non.
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
	 * M�thode getCase() : 
	 * Cette m�thode permet de r�cup�rer un objet de type Case � partir d'un objet de Type Point2D
	 * qui correspond � des coordonn�es sur la fen�tre de jeu.
	 * L'objet Case r�cup�r� correspond donc aux coordonn�es d'un Point sur l'interface graphique.
	 * 
	 * @param point
	 * @return objet de type Case correspondant au point en param�tre dans le labyrinthe
	 */
	public Case getCase(Point2D point) {
		Point tile = pointLabyrinthe(point);
		return niveau.get(tile.y).get(tile.x);
	}
	/**
	 * M�thode setCase() : Cette m�thode permet d'affecter une valeur sp�cifique associ�e au type �num�r� Case, �
	 * une case du labyrinthe � partir de ses coordonn�es dans la fen�tre
	 * 
	 * @param point
	 * @param terrain
	 */
	public void setCase(Point2D point, Case terrain) {
		Point tile = pointLabyrinthe(point);
		niveau.get(tile.y).set(tile.x, terrain);
	}
	
	/**
	 * M�thode pointLabyrinthe() : Cette m�thode renvoie les coordonn�es d'un point de la fen�tre 
	 * et les associe � une case du labyrinthe.
	 * @param point
	 * @return Point correspondant � une case du labyrinthe
	 */
	public Point pointLabyrinthe(Point2D point) {
		Point2D wrap = wrapAround(point);
		int longueurPoint  = Labyrinthe.TAILLE_CASE * getLongueur();
		int largeurPoint = Labyrinthe.TAILLE_CASE * getLargeur();
		return new Point((int) (wrap.getX() / longueurPoint * getLongueur()),
				(int) (wrap.getY() / largeurPoint * getLargeur()));
	}
	
	/**
	 * M�thode wrapAround() : Cette m�thode permet d'effectuer un "wrap" sur une case donn�e pour
	 * �viter les exceptions de type OutOfBoundsExceptions.
	 * Il est utilis� pour passer de l'autre cot� de l'�cran � travers un tunnel.
	 * Cet aspect des jeux r�tros est appel� le wrapAround.
	 * @param point
	 * @return une case envelopp�e
	 */
	public Point2D wrapAround(Point2D point) {
		int pWidth  = Labyrinthe.TAILLE_CASE * getLongueur();
		int pHeight = Labyrinthe.TAILLE_CASE * getLargeur();
		
		return new Point2D.Double((point.getX() % pWidth + pWidth) % pWidth, 
								  (point.getY() % pHeight + pHeight) % pHeight);
	}
	
	/**
	 * M�thode centrePoint () : Cette m�thode permet de r�cup�rer le centre d'une case en coordonn�es
	 * la fen�tre de jeu.
	 * @param point
	 * @return centre de la case de classe Point
	 */
	public Point2D centrePoint2D(Point2D point) {
		return centrePoint(pointLabyrinthe(point));
	}
	
	/**
	 * M�thode centrePoint () : Cette m�thode permet de r�cup�rer le centre d'une case en coordonn�es
	 * la fen�tre de jeu.
	 * @param point
	 * @return centre de la case de classe Point
	 */
	public Point2D centrePoint(Point point) {
		return wrapAround(new Point2D.Double(point.x * Labyrinthe.TAILLE_CASE + Labyrinthe.TAILLE_CASE / 2.0,
				point.y * Labyrinthe.TAILLE_CASE + Labyrinthe.TAILLE_CASE / 2.0));
	}
	
	/**
	 * M�thode adjacent() :
	 * Cette m�thode permet de retourner le centre d'une case dans une direction donn� d'une distance de x cases
	 * Elle est utilis� par les fantomes pour savoir vers quel case se diriger.
	 * @param ptDepart
	 * @param direction
	 * @param distance
	 * @return centre de la case vis�
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
	 * M�thode adjacent() : Permet de retourner le centre d'une case dans une direction donn� d'une distance de x cases
	 * Elle est utilis� par les fantomes pour savoir vers quel case se diriger.
	 * @param entite
	 * @param distance
	 * @return centre de la case vis�
	 */
	public Point2D adjacent(Entite entite, double distance) {
		return adjacent(entite.getPointActuel(), entite.getDirectionActuelle(), distance);
	}
	
	/**
	 * M�thode detectionCollision() : Cette m�thode permet de d�terminer si deux points de la fen�tre
	 * de jeu sont situ�s au m�me coordonn�es.
	 * 
	 * @param p1
	 * @param p2
	 * @return true si les deux points sont aux m�me coordonn�es, false sinon 
	 */
	public boolean detectionCollision(Point2D p1, Point2D p2) {
		return pointLabyrinthe(p1).equals(pointLabyrinthe(p2));
	}
	/**
	 * M�thode detectionCollision() : Cette m�thode permet de d�terminer 
	 * si deux entit�s sont entr�es en collision sur une m�me case,
	 * 
	 * @param a1
	 * @param a2
	 * @return true si deux entit�s entrent en collision, false sinon
	 */
	public boolean detectionCollision(Entite a1, Entite a2) {
		return detectionCollision(a1.getPointActuel(), a2.getPointActuel());
	}
	
	/**
	 * M�thode chargerLabyrinthe() : Cette m�thode permet de charger un niveau du labyrinthe � partir d'un fichier texte.
	 * Dans ce fichier texte (.txt) chaque caract�re repr�sente l'ordinal (valeur associ�e) � une case souhait�e dans parmi
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

package jeu_pacman_entites;

import java.awt.geom.Point2D;
import java.util.List;

import jeu_pacman_affichage.SpriteAnime;
import jeu_pacman_labyrinthe.Labyrinthe;
import jeu_pacman_labyrinthe.Case;

/**
 * Cette super classe repr�sente toutes les entit�s mobiles au sein du jeu � savoir Pac-Man et les fant�mes
 * Cette classe est d�finie comme abstraite, car nous avons jug� qu'il �tait plus coh�rent qu'elle ne soit pas instanciable, chaque
 * entit� ayant des propri�t�s diff�rentes, il est donc plus judicieux d'instancier individuellement chacun des personnages.
 * 
 * @author Christian
 * @author Darl�ne
 * @author Lisa-Marie
 *
 */
public abstract class Entite {
	
	/**Variables point2D repr�sentant un ensemble de cordonn�es (case) repr�sentant les points initiaux et les points actuels*/
	private Point2D pointInitial, pointActuel;
	/**
	 * Variable de type �num�r� pouvant prendre 4 valeurs/�tats 
	 *(HAUT/BAS/GAUCHE/DROITE) selon les touches press�es par l'utilisateur ou selon l'algorithme relatif � l'entit�.
	 * 
	 */
	private Direction directionInitiale, directionActuelle;
	/**Objet repr�sentant le sprite anim� de l'entit� */
	private SpriteAnime sprite;
	
	private double vitesseDeplacement;
	private int nbrDeplacements;
	
	/**
	 * Constructeur de la classe Entite, il n'est pas possible de faire appel � ce constructeur au sein d'autres classes
	 * du projet car la classe par d�finition abstraite, donc non-instanciable.
	 * 
	 * @param point
	 * @param sprite
	 * @param direction
	 * @param vitesse
	 */
	public Entite(Point2D point, SpriteAnime sprite, Direction direction, double vitesse) {
		this.pointInitial = pointActuel = point;
		this.directionInitiale = directionActuelle = direction;
		this.vitesseDeplacement = vitesse;
		this.sprite = sprite;
	}
	
	/**
	 * D�claration des valeurs/�tats que peut prendre la variable de type �num�r� Direction
	 * Chaque valeur repr�sente une direction que peut prendre une entit�, soit elle peut aller vers le haut, soit elle
	 * peut aller vers le bas, soit elle peut aller vers la gauche ou vers la droite selon la direction choisie par l'utilisateur
	 * au clavier ou par la direction choisie par l'algorithme du fant�me.
	 */
	public enum Direction {
		/**
		 * Les orientations sont class�es par ordre d�croissant de priorit�. 
		 *Par cons�quent, la s�lection d'une cible fixe, si les distances sont �gales, 
		 *le fant�me essaiera d'abord de se d�placer vers le haut, puis vers la gauche, etc.
		 */
		HAUT,GAUCHE, BAS, DROITE;
		/**
		 * M�thode estInverseDe() : Cette m�thode permet aux entit�s de ne pas revenir sur leurs pas
		 * lorsqu'ils choisissent une direction en la comparant avec toutes les directions possibles.
		 * @param d
		 * @return true si la direction compar�e est l'inverse de la direction en param�tre,false sinon
		 *
		 */
		public boolean estInverseDe(Direction d) {
			if(this.equals(HAUT) && d.equals(BAS))
				return true;
			else if(this.equals(BAS) && d.equals(HAUT))
				return true;
			else if(this.equals(GAUCHE) && d.equals(DROITE))
				return true;
			else if(this.equals(DROITE) && d.equals(GAUCHE))
				return true;
			return false;
		}
	}
	
	/**
	 * M�thode getDirectionInitiale() : Cette m�thode renvoie la valeur du type �num�r� correspondant � la direction initiale 
	 * vers laquelle l'entit� se dirige � son point initial
	 * 
	 * @return la valeur du type �num�r� correspondant � la direction initiale vers laquelle l'entit� s'est dirig�e
	 */
	public Direction getDirectionInitiale() {
		return directionInitiale;
	}
	
	/** 
	 * M�thode getDirectionActuelle(): Cette m�thode renvoie la valeur du type �num�r� correspondant � la direction actuelle
	 * vers laquelle l'entit� est orient�e
	 * 
	 * @return la valeur du type �num�r� correspondant � la direction actuelle vers laquelle l'entit� est orient�e
	 * 
	 */
	public Direction getDirectionActuelle() {
		return directionActuelle;
	}
	
	public void setDirectionActuelle(Direction d) {
		directionActuelle = d;
	}
	
	/**
	 * M�thode getDirectionSuivante() : Cette m�thode renvoie la valeur du type �num�r� 
	 * correspondant � la direction suivante vers laquelle l'entit� se dirige lorsqu'il atteint le point central d'une case.
	 * Cette m�thode est abstraite car ses conditions sont variables selon chaque entit� individuelle, la m�thode sera donc
	 * red�finie au sein des sous-classes d'entit�.
	 * 
	 * @param entites
	 * @param labyrinthe
	 * @return la valeur du type �num�r� correspondant � la direction suivante vers laquelle l'entit� se dirige
	 */
	abstract public Direction getDirectionSuivante(List<Entite> entites, Labyrinthe labyrinthe);
	
	
	/**
	 * M�thode getNbrDeplacements() : Cette m�thode permet d'obtenir le nombre de d�placements qu'une entit� a effectu� depuis la derni�re
	 * r�initialisation du jeu. Cette fonction est utilis�e � des fins de chronom�trage ou parce que certains �v�nements se produisent qu'apr�s
	 * un nombre de d�placements d�termin�.
	 *
	 * @return nombre de d�placements de l'entit�
	 */
	public int getNbrDeplacements() {
		return nbrDeplacements;
	}
	/**
	 *  M�thode setNbrDeplacements() : Cette m�thode permet de d�finir le nombre de d�placement qu'une entit� va effectuer � l'initialisation et 
	 *  apr�s la r�initialisation du jeu.
	 *  
	 * @param dep
	 */
	public void setNbrDeplacements(int dep) {
		nbrDeplacements = dep;
	}
	
	/**
	 * M�thode getVitesse() : Cette m�thode permet d'obtenir la vitesse en pixel d'une entit� selon 
	 * la taille d'une case de Labyrinthe.
	 * 
	 * @return Vitesse actuelle (entre 0.0 and 1.0)
	 */
	public double vitessePixel() { 
		return Labyrinthe.getTailleCase() * vitesseDeplacement;
	}
	/**
	 * M�thode setVitesse() : Cette m�thode permet de d�finir la vitesse de d�placement d'une entit�
	 * 
	 * @param v
	 */
	public void setVitesse(double v) {
		vitesseDeplacement = v;
	}
	
	/**
	 * M�thode getSprite() : Cette m�thode permet de retourner le spirite par d�faut d'une entit�. Les sous-classes peuvent surcharger le
	 * comportement par d�faut pour impl�menter diff�rents sprites pour diff�rents �tats.
	 * 
	 * @return l'objet sprite par d�faut d'une entit�
	 */
	public SpriteAnime getSprite() {
		return sprite;
	}
	
	/**
	 * M�thode getPointInitial() : Cette m�thode renvoie la position initiale d'une entit�, elle est notamment utile pour
	 * r�initialiser la position d'une entit�.
	 * 
	 * @return coordonn�es de la case correspondant � la position initiale de l'entit�
	 */
	public Point2D getPointInitial() {
		return pointInitial;
	}
	
	/**
	 * M�thode getPointActuel() : Cette m�thode renvoie la position actuelle de l'acteur. Le centre de la case sur laquelle se trouve
	 * l'entit� est utilis�e pour d�terminer si ce dernier entre en collision avec un autre acteur ou un �l�ment du labyrinthe.
	 * 
	 * @return  
	 */
	public Point2D getPointActuel() {
		return pointActuel;
	}
	
	/**
	 * M�thode PointSuivant() : Cette m�thode renvoie la prochaine case � laquelle l'acteur se rendra lors de son prochain d�placement. 
	 * L'entit� se d�placera d'un certain nombre de pixels d�termin� par sa vitesse et sa vitesse maximale dans la direction de son orientation actuelle.
	 * 
	 * @return coordonn�es de la case correspondant � la position actuelle de l'entit�
	 */
	public Point2D pointSuivant(Labyrinthe lab) {

		Point2D center = lab.centrePoint2D(pointActuel);		
		Point2D adj	   = lab.adjacent(this, 1);
		boolean valid  = peutSeDeplacer(lab.getCase(adj));
		
		double dx = pointActuel.getX() - center.getX();
		double dy = pointActuel.getY() - center.getY();
		double pixels = vitessePixel();
		
		switch(getDirectionActuelle()) {
			case GAUCHE:  
				if(dx > 0 && Math.abs(dx) < pixels || dx == 0 && !valid) 
					return center;
				return lab.wrapAround(new Point2D.Double(pointActuel.getX() - pixels, pointActuel.getY()));
			case DROITE:
				if(dx < 0 && Math.abs(dx) < pixels || dx == 0 && !valid)
					return center;
				return lab.wrapAround(new Point2D.Double(pointActuel.getX() + pixels, pointActuel.getY()));
			case HAUT: 
				if(dy > 0 && Math.abs(dy) < pixels || dy == 0 && !valid)
					return center;
				return lab.wrapAround(new Point2D.Double(pointActuel.getX(), pointActuel.getY() - pixels));
			default:
				if(dy < 0 && Math.abs(dy) < pixels || dy == 0 && !valid)
					return center;
				return lab.wrapAround(new Point2D.Double(pointActuel.getX(), pointActuel.getY() + pixels));
		}
	}
	
	
	/**
	 * M�thode resetPoint() : M�thode resetPoint() : Cette m�thode permet de remettre l'entite
	 * � sa position initiale
	 */
	public void resetPoint() {
		pointActuel = getPointInitial();
		directionActuelle = getDirectionInitiale();
		setNbrDeplacements(0);
	}
	
	/**
	 * M�thode deplacer() : Cette m�thode permet � l'entit� de se d�placer vers sa prochaine position dans le labyrinthe. La position
	 * suivante est sp�cifi�e par la m�thode getPointSuivant() cette m�thode renvoie true si la position de l'acteur 
	 * a �t� modifi�e par le d�placement et false sinon.
	 * 
	 * @param entites
	 * @param labyrinthe
	 * @return true si l'entit� peut se d�placer, false sinon 
	 */
	public boolean deplacer(List<Entite> entites, Labyrinthe labyrinthe) {
		nbrDeplacements++;

		/**
		 * V�rification pour savoir si l'acteur peut se d�placer � travers le labyrinthe au point souhait�.
		 * Cela permet de v�rifier que les acteurs ne se d�placent pas en direction de cases non-valides
		 */
		Point2D suivant = pointSuivant(labyrinthe);
		if(!peutSeDeplacer(labyrinthe.getCase(suivant)))
			return false;
		/**
		 * 	Si l'acteur peut se d�placer sur la case en question du labyrinthe, alors on d�finit le nouvelle case comme �tant la position actuelle.
		 *  Le sprite anim� passe � la prochaine frame.
		 */
		if(labyrinthe.centrePoint2D(suivant).equals(suivant))
			directionActuelle = getDirectionSuivante(entites, labyrinthe);
		
		pointActuel = suivant;		
		getSprite().frameSuivante(getDirectionActuelle());
		return true;
	}
	
	/**
	 * M�thode peutSeDeplacer() : Cette m�thode permet de d�terminer si l'entit� peut se d�placer � travers un �l�ment du labyrinthe sp�cifique.
	 * Par exemple, s'il s'agit de la porte menant vers le point de d�part des fant�mes, il ne sera pas possible pour Pac-Man de franchir cet obstacle.
	 * 
	 * @return true si l'entit� peut se d�placer au niveau de la case, false sinon
	 */
	public boolean peutSeDeplacer(Case c) {
		return c.estTraversable();
	}
	
	
	
	
}

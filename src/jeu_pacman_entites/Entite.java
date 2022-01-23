package jeu_pacman_entites;

import java.awt.geom.Point2D;
import java.util.List;

import jeu_pacman_affichage.SpriteAnime;
import jeu_pacman_labyrinthe.Labyrinthe;
import jeu_pacman_labyrinthe.Case;

/**
 * Cette super classe représente toutes les entités mobiles au sein du jeu à savoir Pac-Man et les fantômes
 * Cette classe est définie comme abstraite, car nous avons jugé qu'il était plus cohérent qu'elle ne soit pas instanciable, chaque
 * entité ayant des propriétés différentes, il est donc plus judicieux d'instancier individuellement chacun des personnages.
 * 
 * @author Christian
 * @author Darlène
 * @author Lisa-Marie
 *
 */
public abstract class Entite {
	
	/**Variables point2D représentant un ensemble de cordonnées (case) représentant les points initiaux et les points actuels*/
	private Point2D pointInitial, pointActuel;
	/**
	 * Variable de type énuméré pouvant prendre 4 valeurs/états 
	 *(HAUT/BAS/GAUCHE/DROITE) selon les touches pressées par l'utilisateur ou selon l'algorithme relatif à l'entité.
	 * 
	 */
	private Direction directionInitiale, directionActuelle;
	/**Objet représentant le sprite animé de l'entité */
	private SpriteAnime sprite;
	
	private double vitesseDeplacement;
	private int nbrDeplacements;
	
	/**
	 * Constructeur de la classe Entite, il n'est pas possible de faire appel à ce constructeur au sein d'autres classes
	 * du projet car la classe par définition abstraite, donc non-instanciable.
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
	 * Déclaration des valeurs/états que peut prendre la variable de type énuméré Direction
	 * Chaque valeur représente une direction que peut prendre une entité, soit elle peut aller vers le haut, soit elle
	 * peut aller vers le bas, soit elle peut aller vers la gauche ou vers la droite selon la direction choisie par l'utilisateur
	 * au clavier ou par la direction choisie par l'algorithme du fantôme.
	 */
	public enum Direction {
		/**
		 * Les orientations sont classées par ordre décroissant de priorité. 
		 *Par conséquent, la sélection d'une cible fixe, si les distances sont égales, 
		 *le fantôme essaiera d'abord de se déplacer vers le haut, puis vers la gauche, etc.
		 */
		HAUT,GAUCHE, BAS, DROITE;
		/**
		 * Méthode estInverseDe() : Cette méthode permet aux entités de ne pas revenir sur leurs pas
		 * lorsqu'ils choisissent une direction en la comparant avec toutes les directions possibles.
		 * @param d
		 * @return true si la direction comparée est l'inverse de la direction en paramètre,false sinon
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
	 * Méthode getDirectionInitiale() : Cette méthode renvoie la valeur du type énuméré correspondant à la direction initiale 
	 * vers laquelle l'entité se dirige à son point initial
	 * 
	 * @return la valeur du type énuméré correspondant à la direction initiale vers laquelle l'entité s'est dirigée
	 */
	public Direction getDirectionInitiale() {
		return directionInitiale;
	}
	
	/** 
	 * Méthode getDirectionActuelle(): Cette méthode renvoie la valeur du type énuméré correspondant à la direction actuelle
	 * vers laquelle l'entité est orientée
	 * 
	 * @return la valeur du type énuméré correspondant à la direction actuelle vers laquelle l'entité est orientée
	 * 
	 */
	public Direction getDirectionActuelle() {
		return directionActuelle;
	}
	
	public void setDirectionActuelle(Direction d) {
		directionActuelle = d;
	}
	
	/**
	 * Méthode getDirectionSuivante() : Cette méthode renvoie la valeur du type énuméré 
	 * correspondant à la direction suivante vers laquelle l'entité se dirige lorsqu'il atteint le point central d'une case.
	 * Cette méthode est abstraite car ses conditions sont variables selon chaque entité individuelle, la méthode sera donc
	 * redéfinie au sein des sous-classes d'entité.
	 * 
	 * @param entites
	 * @param labyrinthe
	 * @return la valeur du type énuméré correspondant à la direction suivante vers laquelle l'entité se dirige
	 */
	abstract public Direction getDirectionSuivante(List<Entite> entites, Labyrinthe labyrinthe);
	
	
	/**
	 * Méthode getNbrDeplacements() : Cette méthode permet d'obtenir le nombre de déplacements qu'une entité a effectué depuis la dernière
	 * réinitialisation du jeu. Cette fonction est utilisée à des fins de chronométrage ou parce que certains événements se produisent qu'après
	 * un nombre de déplacements déterminé.
	 *
	 * @return nombre de déplacements de l'entité
	 */
	public int getNbrDeplacements() {
		return nbrDeplacements;
	}
	/**
	 *  Méthode setNbrDeplacements() : Cette méthode permet de définir le nombre de déplacement qu'une entité va effectuer à l'initialisation et 
	 *  après la réinitialisation du jeu.
	 *  
	 * @param dep
	 */
	public void setNbrDeplacements(int dep) {
		nbrDeplacements = dep;
	}
	
	/**
	 * Méthode getVitesse() : Cette méthode permet d'obtenir la vitesse en pixel d'une entité selon 
	 * la taille d'une case de Labyrinthe.
	 * 
	 * @return Vitesse actuelle (entre 0.0 and 1.0)
	 */
	public double vitessePixel() { 
		return Labyrinthe.getTailleCase() * vitesseDeplacement;
	}
	/**
	 * Méthode setVitesse() : Cette méthode permet de définir la vitesse de déplacement d'une entité
	 * 
	 * @param v
	 */
	public void setVitesse(double v) {
		vitesseDeplacement = v;
	}
	
	/**
	 * Méthode getSprite() : Cette méthode permet de retourner le spirite par défaut d'une entité. Les sous-classes peuvent surcharger le
	 * comportement par défaut pour implémenter différents sprites pour différents états.
	 * 
	 * @return l'objet sprite par défaut d'une entité
	 */
	public SpriteAnime getSprite() {
		return sprite;
	}
	
	/**
	 * Méthode getPointInitial() : Cette méthode renvoie la position initiale d'une entité, elle est notamment utile pour
	 * réinitialiser la position d'une entité.
	 * 
	 * @return coordonnées de la case correspondant à la position initiale de l'entité
	 */
	public Point2D getPointInitial() {
		return pointInitial;
	}
	
	/**
	 * Méthode getPointActuel() : Cette méthode renvoie la position actuelle de l'acteur. Le centre de la case sur laquelle se trouve
	 * l'entité est utilisée pour déterminer si ce dernier entre en collision avec un autre acteur ou un élément du labyrinthe.
	 * 
	 * @return  
	 */
	public Point2D getPointActuel() {
		return pointActuel;
	}
	
	/**
	 * Méthode PointSuivant() : Cette méthode renvoie la prochaine case à laquelle l'acteur se rendra lors de son prochain déplacement. 
	 * L'entité se déplacera d'un certain nombre de pixels déterminé par sa vitesse et sa vitesse maximale dans la direction de son orientation actuelle.
	 * 
	 * @return coordonnées de la case correspondant à la position actuelle de l'entité
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
	 * Méthode resetPoint() : Méthode resetPoint() : Cette méthode permet de remettre l'entite
	 * à sa position initiale
	 */
	public void resetPoint() {
		pointActuel = getPointInitial();
		directionActuelle = getDirectionInitiale();
		setNbrDeplacements(0);
	}
	
	/**
	 * Méthode deplacer() : Cette méthode permet à l'entité de se déplacer vers sa prochaine position dans le labyrinthe. La position
	 * suivante est spécifiée par la méthode getPointSuivant() cette méthode renvoie true si la position de l'acteur 
	 * a été modifiée par le déplacement et false sinon.
	 * 
	 * @param entites
	 * @param labyrinthe
	 * @return true si l'entité peut se déplacer, false sinon 
	 */
	public boolean deplacer(List<Entite> entites, Labyrinthe labyrinthe) {
		nbrDeplacements++;

		/**
		 * Vérification pour savoir si l'acteur peut se déplacer à travers le labyrinthe au point souhaité.
		 * Cela permet de vérifier que les acteurs ne se déplacent pas en direction de cases non-valides
		 */
		Point2D suivant = pointSuivant(labyrinthe);
		if(!peutSeDeplacer(labyrinthe.getCase(suivant)))
			return false;
		/**
		 * 	Si l'acteur peut se déplacer sur la case en question du labyrinthe, alors on définit le nouvelle case comme étant la position actuelle.
		 *  Le sprite animé passe à la prochaine frame.
		 */
		if(labyrinthe.centrePoint2D(suivant).equals(suivant))
			directionActuelle = getDirectionSuivante(entites, labyrinthe);
		
		pointActuel = suivant;		
		getSprite().frameSuivante(getDirectionActuelle());
		return true;
	}
	
	/**
	 * Méthode peutSeDeplacer() : Cette méthode permet de déterminer si l'entité peut se déplacer à travers un élément du labyrinthe spécifique.
	 * Par exemple, s'il s'agit de la porte menant vers le point de départ des fantômes, il ne sera pas possible pour Pac-Man de franchir cet obstacle.
	 * 
	 * @return true si l'entité peut se déplacer au niveau de la case, false sinon
	 */
	public boolean peutSeDeplacer(Case c) {
		return c.estTraversable();
	}
	
	
	
	
}

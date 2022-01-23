package jeu_pacman_entites;

import java.awt.geom.Point2D;
import java.util.List;

import jeu_pacman_affichage.SpriteAnime;
import jeu_pacman_application.Jeu;
import jeu_pacman_labyrinthe.Labyrinthe;
import jeu_pacman_labyrinthe.Case;

/**
 * La super classe fant�me repr�sente une cat�gorie sp�cifique de personnages du jeu qui sont les ennemis de Pacman.
 * 
 * Leurs d�placements sont contr�l�s ind�pendamment de toute int�raction du joueur avec le clavier car
 * c'est l'application en elle-m�me qui s'en charge, les fant�mes se comportent en quelque sorte comme des
 * formes d'IA simples.
 * 
 * Les d�placements de chacuns des fant�mes sont d�termin�s par des algorithmes pr�cis, propres � chaque
 * fant�mes. Ce processus de d�placement et de calcul de position est continu au cours du jeu.
 * 
 * Cette classe est li�e � la classe entit� par une relation d'h�ritage (Les fant�mes �tant des entit�s non-incarnables du jeu).
 * Cette classe est d�finie comme abstraite, car nous avons jug� qu'il �tait plus coh�rent qu'elle ne soit pas instanciable, chaque
 * fant�me ayant des propri�t�s diff�rentes, il est donc plus judicieux d'instancier individuellement chacun des fant�mes.
 * 
 * 
 * @author Christian
 * @author Darl�ne
 * @author Lisa-Marie
 * 
 */
public abstract class Fantome extends Entite {
	/** Constante repr�sentant la dur�e en mode chasse des fant�mes */
	private static final int DUREE_CHASSE   = 6000;
	/** Constante repr�sentant la dur�e en mode dispersion des fant�mes */
	private static final int DUREE_DISPERSION = 8000;
	/**Variable d�cimale repr�sentant la difficult� du niveau (qui influe le d�placement des fant�mes) si
	 * le joueur gagne la partie
	*/
	private double difficulte;
	/**
	 * Variable de type �num�r� pouvant prendre 4 valeurs/�tats 
	 *(CHASSE,DISPERSION,EFFRAYE,ESTMANGE,SORTIE,IMMOBILE) selon les d�placements des fant�mes.
	 * 
	 */
	private Mode mode;
	/**Variables point2D repr�sentant un ensemble de cordonn�es (case) repr�sentant les points de dispersion et de sortie*/
	private Point2D dispersion, sortie;
	
	/**
	 * Constructeur de la classe fant�me, il n'est pas possible de faire appel � ce constructeur au sein d'autres classes
	 * du projet car la classe par d�finition abstraite, donc non-instanciable.
	 * 
	 * @param ptInitial
	 * @param ptDispersion
	 * @param ptSortie
	 * @param m
	 * @param sprite
	 * @param direction
	 */
	public Fantome(Point2D ptInitial, Point2D ptDispersion, Point2D ptSortie, 
			Mode m, SpriteAnime sprite, Direction direction) {
		super(ptInitial, sprite, direction, 0.0);
		
		sortie = ptSortie;
		dispersion = ptDispersion;
		setMode(m);
		difficulte = 1;
	}
	
	/**
	 * D�claration des valeurs/�tats que peut prendre la variable de type �num�r� Mode
	 * Chaque valeur repr�sente un �tat sp�cifique des fant�mes, soit ils sont en train de chasser Pacman,
	 * Soit ils se dispersent � travers le labyrinthe, soit ils sont effray�s apr�s que Pacman ait mang�
	 * une super gomme, soit ils viennent de sortir du point de d�part, soit ils sont immobiles au niveau
	 * du point de d�part des fant�mes.
	 */
	
	public enum Mode {
		CHASSE(0.20),
		DISPERSION(0.20),
		EFFRAYE(0.15),
		ESTMANGE(0.15),
		SORTIE(0.20),
		IMMOBILE(0.00);
		/** Variable d�cimale r�pr�sentant la vitesse des fant�mes */
		private double vitesse;
		/**
		 * M�thode Mode() : Cette m�thode prend en param�tre un double et permet d'associer une
		 * vitesse � chaque �tat/mode du fant�me.
		 * 
		 * @param v
		 */
		private Mode(double v) {
			vitesse = v;
		}
		/**
		 * M�thode getSpeed() : Cette m�thode est un getter qui renvoie la vitesse d'un fant�me
		 * @return vitesse d'un fant�me
		 * 
		 */
		public double getSpeed() {
			return vitesse;
		}
	}
	
	/**
	 * M�thode getMode() : Cette m�thode est un getter qui renvoie l'�tat/le mode dans lequel
	 * le fant�me se trouve, tout en sachant que ce mode d�pend des d�placements des fant�mes,
	 * mais aussi de pacman au sein du labyrinthe
	 * 
	 * @return �tat/mode actuel du fant�me
	 */
	public Mode getMode() {
		return mode;
	}
	/**
	 * M�thode setMode() : Cette m�thode est un setter permettant d'attribuer un �tat � un fant�me, 
	 * mais aussi d'ajuster la vitesse du fant�me en fonction de son �tat et du niveau de difficult�
	 * actuel du jeu.
	 * 
	 * @param m
	 */
	public void setMode(Mode m) {
		setVitesse(m.getSpeed() * difficulte);
		mode = m;
		setNbrDeplacements(0);
	}
	/**
	 * M�thode getDifficulte() : Cette m�thode est un getter permettant d'obtenir la difficult� 
	 * actuelle du jeu.
	 * 
	 * @return difficult� actuelle du jeu
	 */
	public double getDifficulte() {
		return difficulte;
	}
	/**
	 * M�thode setDifficult�() : Cette m�thode est un setter permettant de d�finir la difficult�
	 * du jeu
	 * 
	 * @param d
	 */
	public void setDifficulte(double d) {
		difficulte = d;
	}
	
	/**
	 * M�thode getSortie() : Cette m�thode permet d'obtenir les coordon�es du point de sortie
	 * de la maison des fantomes. Cette position est utilis�e comme une cible fixe 
	 * pour aider les fant�mes � sortir.
	 * 
	 * @return Coordon�es du point de sortie des fant�mes
	 */
	public Point2D getSortie() {
		return sortie;
	}
	
	/**
	 * M�thode getPointDeDispersion() : Cette m�thode permet d'obtenir les coordonn�es du point
	 * de dispersion des fant�mes Ce point est utilis�e comme une cible fixe pour aider les fant�mes 
	 * � se disperser. Elle est situ� dans l'un des 4 coins du niveau selon le fantome.
	 * 
	 * @return Coordonn�es du point de dispersion des fant�mes
	 */
	public Point2D getPointDeDispersion() {
		return dispersion;
	}
	
	/**
	 * M�thode getSprite() : Cette m�thode renvoie un objet SpriteAnime diff�rent en fonction
	 * du mode du fant�me, si le fant�me est effray�, son sprite sera bleu, si il est mang�
	 * son sprite sera transparent sauf ses yeux, sinon chaque fant�me poss�de un sprite
	 * par d�faut.
	 * @return sprite du fant�me
	 */
	@Override
	public SpriteAnime getSprite() {
		switch(mode) {
			case EFFRAYE: return new SpriteAnime(50, getDirectionActuelle());
			case ESTMANGE: 	 return new SpriteAnime(62, getDirectionActuelle());
			default: 		 return super.getSprite();
		}
	}
	
	/**
	 * M�thode resetPoint() : M�thode resetPoint() : Cette m�thode permet de remettre les fantomes � leurs
	 * places initiale dans la maison � fantomes.
	 */
	@Override
	public void resetPoint() {
		super.resetPoint();
		mode = Mode.IMMOBILE;
	}
	
	/**
	 * M�thode peutSeDeplacer() : Cette m�thode permet de savoir sous quelles conditions le fant�me peut se d�placer ou non.
	 * Les fant�mes peuvent traverser la porte du point de d�part dans les �tats "ESTMANGE" ou "SORTIE", sinon ils se
	 * d�placent dans le labyrinthe de la m�me mani�re que toutes les autres entit�s.
	 * 
	 * @param c
	 * @return true si le fant�me peut se d�placer, false sinon
	 */
	@Override
	public boolean peutSeDeplacer(Case c) {
		return super.peutSeDeplacer(c)
				|| ((mode.equals(Mode.ESTMANGE) || mode.equals(Mode.SORTIE)) 
						&& c.equals(Case.PORTAIL));
	}
	
	/**
	 * M�thode deplacer() : Cette m�thode permet de d�finir la mani�re de se d�placer des fant�mes selon leur mode,
	 * tout en v�rifiant �galement s'ils peuvent se d�placer ou non vers la case dans laquelle il se dirige, si il ne peut
	 * pas se rendre dans la case vers laquelle il se dirige son mode peut �galement changer (tout comme un d�placement classique).
	 * 
	 * @return true si le fant�me peut se d�placer, false sinon.
	 */
	@Override
	public boolean deplacer(List<Entite> e, Labyrinthe l) {
		Pacman pacman = (Pacman) e.get(0);
		
		if(mode.equals(Mode.IMMOBILE) && getNbrDeplacements() 
				> Jeu.conversionFrames(getDureeImmobile()))
			setMode(Mode.SORTIE);
		
		else if(mode.equals(Mode.ESTMANGE) && getPointActuel().equals(getPointInitial()))
			setMode(Mode.SORTIE);
		
		else if(mode.equals(Mode.SORTIE) && getPointActuel().equals(getSortie()))
			setMode(Mode.CHASSE);
		
		else if(mode.equals(Mode.CHASSE) && getNbrDeplacements() 
				> Jeu.conversionFrames(getDureeChasse()))
			setMode(Mode.DISPERSION);
		
		else if(mode.equals(Mode.DISPERSION) && getNbrDeplacements() 
				> Jeu.conversionFrames(getDureeDispersion()))
			setMode(Mode.CHASSE);
		
		else if(getNbrDeplacements() > pacman.getNbrDeplacements() 
				&& (mode.equals(Mode.CHASSE) 
				|| mode.equals(Mode.DISPERSION)) && pacman.enSuperForme())
			setMode(Mode.EFFRAYE);
		
		else if(mode.equals(Mode.EFFRAYE) && !pacman.enSuperForme())
			setMode(Mode.CHASSE);
		
		return super.deplacer(e, l);
	}
	
	/**
	 * M�thode getDureeChasse() : Cette m�thode renvoie un entier correspondant � la dur�e
	 * durant laquelle les fant�mes sont dans l'�tat chasse. Plus la difficult� du jeu est importante,
	 * plus le mode chasse sera long.
	 * 
	 * @return Dur�e en millisecondes de l'�tat chasse.
	 */
	public int getDureeChasse() {
		return Fantome.DUREE_CHASSE;
	}
	
	/**
	 * M�thode getDureeDispersion() : Cette m�thode renvoie un entier correspondant � la dur�e
	 * durant laquelle les fant�mes sont dans l'�tat dispersion. Plus la difficult� du jeu est importante,
	 * moins le mode chasse sera long.
	 * 
	 * 
	 * @return Dur�e en millisecondes de l'�tat dispersion.
	 */
	public int getDureeDispersion() {
		return Fantome.DUREE_DISPERSION;
	}
	
	/**
	 * M�thode getDureeImmobile() : Cette m�thode renvoie un entier correspondant � la dur�e
	 * durant laquelle les fant�mes sont dans l'�tat d'immobilit�. La dur�e d'immobilit� �tant propre
	 * � chaque fant�me cette m�thode est abstraite pour red�finie au sein des classes sp�cifiques �
	 * chaque fant�mes (Blinky,Inky,Pinky et Clyde)
	 * 
	 * 
	 * @return Dur�e en millisecondes de l'�tat immobile.
	 */
	abstract public int getDureeImmobile();
	
	/**
	 * M�thode getDirectionSuivante() : Cette m�thode prennant en param�tre une liste d'entites
	 * et un objet labyrinthe permet aux renvoie une valeur du type enum�r� Direction variant
	 * selon le mode du fant�me.
	 * En mode Dispersion, le fantome se dirige vers le point de dispersion qui lui est attribu�
	 * Il existe 4 points de dispersion qui sont situ�s aux quatres coins du niveau.
	 * Cela permet de le disperser dans le niveau plus efficacement
	 * En mode Sortie, le fantome vient de sortir du mode Immobile et se dirige vers la sortie de la 
	 * maison des fantomes pour en sortir. C'est l'inverse pour le mode EstMange.
	 * En mode Effray�, le fantome se deplace de fa�on al�atoire
	 * En mode Chasse, le fantome cible Pacman selon leur nature. Chaque fantome cible
	 * Pacman d'une fa�on diff�rente.
	 * 
	 * @param lab
	 * @param entites
	 * @return getDirectionFixe() ou getDirectionAleatoire() selon le mode
	 */
	@Override
	public Direction getDirectionSuivante(List<Entite> entites, Labyrinthe lab) {
		switch(mode) {
			case CHASSE: 	 return getDirectionFixe(lab, getPointChasse(entites, lab));
			case DISPERSION: 	 return getDirectionFixe(lab, getPointDeDispersion());
			case EFFRAYE: return getDirectionAleatoire(lab, getPointActuel());
			case ESTMANGE: 	 return getDirectionFixe(lab, getPointInitial());
			case SORTIE: 		 return getDirectionFixe(lab, getSortie());
			default: 		 return getDirectionFixe(lab, getPointInitial());
		}
	}
	
	
	/**
	 * M�thode getDirectionAleatoire() : Cette m�thode permet au fant�me s�lectionner une case valide
	 * al�atoire vers laquelle il peut se d�placer. Contrairement � une case fixe,
	 * un fant�me qui traverse une case cible al�atoire peut revenir en arri�re par le chemin
	 * qu'il a emprunt�.
	 *
	 * @param lab
	 * @param ptDeDepart
	 * @return une case al�atoire valide
	 */
	private Direction getDirectionAleatoire(Labyrinthe lab, Point2D ptDeDepart) {
		Direction nouvelleDirection = null;
		Point2D adjacent = null;
		
		
		do {
			int rand = (int) (Math.random() * 4);
			nouvelleDirection = Direction.values()[rand];
			adjacent = lab.adjacent(ptDeDepart, nouvelleDirection,1);
		
		} while(!peutSeDeplacer(lab.getCase(adjacent)));
		
		return nouvelleDirection;
	}
	
	/**
	 * M�thode getDirectionFixe() : Cette m�thode permet au fant�me de s�lectionner une case valide vers laquelle
	 * il peut se d�placer en utilisant la s�lection de case � cible fixe. Cette s�lection de case � cible fixe
	 * minimise la distance entre le point actuel du fant�me et de son point d'arriv�e de sorte � ce qu'elle soit
	 * optimale. Dans le cas o� il existe deux m�mes chemins de valeur optimale, le fantome choisit la premi�re des
	 * deux directions dans l'ordre de l'enum Direction.
	 * @param ptArrive
	 * @return une case cible pr�cise valide
	 */
	private Direction getDirectionFixe(Labyrinthe lab, Point2D ptArrive) {	
		Direction nouvelleDirection = null;
		double distanceMinimal = Double.MAX_VALUE;

		for(Direction direction: Direction.values()) {
			Point2D adjacent = lab.adjacent(getPointActuel(), direction,1);
			double dist = adjacent.distance(ptArrive);
			Case caseAdjacent = lab.getCase(adjacent);
						
		   /* Si la nouvelle direction que l'on teste n'est pas la direction oppos�e
			* et que l'on peut se d�placer � la case adjacente dans cette direction (� un nbrdeCases donn�, ici 1)
			* et que la distance de cette case est inf�rieur � la distance de la case pour la direction
			* alors cette direction est la nouvelle choisie
			* et ainsi de suite pour chaque direction
			*/
			if(!getDirectionActuelle().estInverseDe(direction) 
				&& peutSeDeplacer(caseAdjacent) 
				&& dist < distanceMinimal) {
				distanceMinimal = dist;
				nouvelleDirection = direction;
			}
		}
		
		return nouvelleDirection;
	}
	
	/**
	 * M�thode getPointChasse() : Cette m�thode permet de d�finir la mani�re selon laquelle le fant�me
	 * va se d�placer en mode chasse. Chaque fant�me ayant une mani�re de se d�placer qui lui est propre,
	 * la m�thode est donc abstraite pour �tre red�finie au cas par cas pour chaque fant�me.
	 * 
	 * @param entites
	 * @param lab
	 * @return la case cibl�e en mode chasse
	 */
	abstract protected Point2D getPointChasse(List<Entite> entites, Labyrinthe lab);
	
}

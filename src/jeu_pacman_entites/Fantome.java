package jeu_pacman_entites;

import java.awt.geom.Point2D;
import java.util.List;

import jeu_pacman_affichage.SpriteAnime;
import jeu_pacman_application.Jeu;
import jeu_pacman_labyrinthe.Labyrinthe;
import jeu_pacman_labyrinthe.Case;

/**
 * La super classe fantôme représente une catégorie spécifique de personnages du jeu qui sont les ennemis de Pacman.
 * 
 * Leurs déplacements sont contrôlés indépendamment de toute intéraction du joueur avec le clavier car
 * c'est l'application en elle-même qui s'en charge, les fantômes se comportent en quelque sorte comme des
 * formes d'IA simples.
 * 
 * Les déplacements de chacuns des fantômes sont déterminés par des algorithmes précis, propres à chaque
 * fantômes. Ce processus de déplacement et de calcul de position est continu au cours du jeu.
 * 
 * Cette classe est liée à la classe entité par une relation d'héritage (Les fantômes étant des entités non-incarnables du jeu).
 * Cette classe est définie comme abstraite, car nous avons jugé qu'il était plus cohérent qu'elle ne soit pas instanciable, chaque
 * fantôme ayant des propriétés différentes, il est donc plus judicieux d'instancier individuellement chacun des fantômes.
 * 
 * 
 * @author Christian
 * @author Darlène
 * @author Lisa-Marie
 * 
 */
public abstract class Fantome extends Entite {
	/** Constante représentant la durée en mode chasse des fantômes */
	private static final int DUREE_CHASSE   = 6000;
	/** Constante représentant la durée en mode dispersion des fantômes */
	private static final int DUREE_DISPERSION = 8000;
	/**Variable décimale représentant la difficulté du niveau (qui influe le déplacement des fantômes) si
	 * le joueur gagne la partie
	*/
	private double difficulte;
	/**
	 * Variable de type énuméré pouvant prendre 4 valeurs/états 
	 *(CHASSE,DISPERSION,EFFRAYE,ESTMANGE,SORTIE,IMMOBILE) selon les déplacements des fantômes.
	 * 
	 */
	private Mode mode;
	/**Variables point2D représentant un ensemble de cordonnées (case) représentant les points de dispersion et de sortie*/
	private Point2D dispersion, sortie;
	
	/**
	 * Constructeur de la classe fantôme, il n'est pas possible de faire appel à ce constructeur au sein d'autres classes
	 * du projet car la classe par définition abstraite, donc non-instanciable.
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
	 * Déclaration des valeurs/états que peut prendre la variable de type énuméré Mode
	 * Chaque valeur représente un état spécifique des fantômes, soit ils sont en train de chasser Pacman,
	 * Soit ils se dispersent à travers le labyrinthe, soit ils sont effrayés après que Pacman ait mangé
	 * une super gomme, soit ils viennent de sortir du point de départ, soit ils sont immobiles au niveau
	 * du point de départ des fantômes.
	 */
	
	public enum Mode {
		CHASSE(0.20),
		DISPERSION(0.20),
		EFFRAYE(0.15),
		ESTMANGE(0.15),
		SORTIE(0.20),
		IMMOBILE(0.00);
		/** Variable décimale réprésentant la vitesse des fantômes */
		private double vitesse;
		/**
		 * Méthode Mode() : Cette méthode prend en paramètre un double et permet d'associer une
		 * vitesse à chaque état/mode du fantôme.
		 * 
		 * @param v
		 */
		private Mode(double v) {
			vitesse = v;
		}
		/**
		 * Méthode getSpeed() : Cette méthode est un getter qui renvoie la vitesse d'un fantôme
		 * @return vitesse d'un fantôme
		 * 
		 */
		public double getSpeed() {
			return vitesse;
		}
	}
	
	/**
	 * Méthode getMode() : Cette méthode est un getter qui renvoie l'état/le mode dans lequel
	 * le fantôme se trouve, tout en sachant que ce mode dépend des déplacements des fantômes,
	 * mais aussi de pacman au sein du labyrinthe
	 * 
	 * @return état/mode actuel du fantôme
	 */
	public Mode getMode() {
		return mode;
	}
	/**
	 * Méthode setMode() : Cette méthode est un setter permettant d'attribuer un état à un fantôme, 
	 * mais aussi d'ajuster la vitesse du fantôme en fonction de son état et du niveau de difficulté
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
	 * Méthode getDifficulte() : Cette méthode est un getter permettant d'obtenir la difficulté 
	 * actuelle du jeu.
	 * 
	 * @return difficulté actuelle du jeu
	 */
	public double getDifficulte() {
		return difficulte;
	}
	/**
	 * Méthode setDifficulté() : Cette méthode est un setter permettant de définir la difficulté
	 * du jeu
	 * 
	 * @param d
	 */
	public void setDifficulte(double d) {
		difficulte = d;
	}
	
	/**
	 * Méthode getSortie() : Cette méthode permet d'obtenir les coordonées du point de sortie
	 * de la maison des fantomes. Cette position est utilisée comme une cible fixe 
	 * pour aider les fantômes à sortir.
	 * 
	 * @return Coordonées du point de sortie des fantômes
	 */
	public Point2D getSortie() {
		return sortie;
	}
	
	/**
	 * Méthode getPointDeDispersion() : Cette méthode permet d'obtenir les coordonnées du point
	 * de dispersion des fantômes Ce point est utilisée comme une cible fixe pour aider les fantômes 
	 * à se disperser. Elle est situé dans l'un des 4 coins du niveau selon le fantome.
	 * 
	 * @return Coordonnées du point de dispersion des fantômes
	 */
	public Point2D getPointDeDispersion() {
		return dispersion;
	}
	
	/**
	 * Méthode getSprite() : Cette méthode renvoie un objet SpriteAnime différent en fonction
	 * du mode du fantôme, si le fantôme est effrayé, son sprite sera bleu, si il est mangé
	 * son sprite sera transparent sauf ses yeux, sinon chaque fantôme possède un sprite
	 * par défaut.
	 * @return sprite du fantôme
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
	 * Méthode resetPoint() : Méthode resetPoint() : Cette méthode permet de remettre les fantomes à leurs
	 * places initiale dans la maison à fantomes.
	 */
	@Override
	public void resetPoint() {
		super.resetPoint();
		mode = Mode.IMMOBILE;
	}
	
	/**
	 * Méthode peutSeDeplacer() : Cette méthode permet de savoir sous quelles conditions le fantôme peut se déplacer ou non.
	 * Les fantômes peuvent traverser la porte du point de départ dans les états "ESTMANGE" ou "SORTIE", sinon ils se
	 * déplacent dans le labyrinthe de la même manière que toutes les autres entités.
	 * 
	 * @param c
	 * @return true si le fantôme peut se déplacer, false sinon
	 */
	@Override
	public boolean peutSeDeplacer(Case c) {
		return super.peutSeDeplacer(c)
				|| ((mode.equals(Mode.ESTMANGE) || mode.equals(Mode.SORTIE)) 
						&& c.equals(Case.PORTAIL));
	}
	
	/**
	 * Méthode deplacer() : Cette méthode permet de définir la manière de se déplacer des fantômes selon leur mode,
	 * tout en vérifiant également s'ils peuvent se déplacer ou non vers la case dans laquelle il se dirige, si il ne peut
	 * pas se rendre dans la case vers laquelle il se dirige son mode peut également changer (tout comme un déplacement classique).
	 * 
	 * @return true si le fantôme peut se déplacer, false sinon.
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
	 * Méthode getDureeChasse() : Cette méthode renvoie un entier correspondant à la durée
	 * durant laquelle les fantômes sont dans l'état chasse. Plus la difficulté du jeu est importante,
	 * plus le mode chasse sera long.
	 * 
	 * @return Durée en millisecondes de l'état chasse.
	 */
	public int getDureeChasse() {
		return Fantome.DUREE_CHASSE;
	}
	
	/**
	 * Méthode getDureeDispersion() : Cette méthode renvoie un entier correspondant à la durée
	 * durant laquelle les fantômes sont dans l'état dispersion. Plus la difficulté du jeu est importante,
	 * moins le mode chasse sera long.
	 * 
	 * 
	 * @return Durée en millisecondes de l'état dispersion.
	 */
	public int getDureeDispersion() {
		return Fantome.DUREE_DISPERSION;
	}
	
	/**
	 * Méthode getDureeImmobile() : Cette méthode renvoie un entier correspondant à la durée
	 * durant laquelle les fantômes sont dans l'état d'immobilité. La durée d'immobilité étant propre
	 * à chaque fantôme cette méthode est abstraite pour redéfinie au sein des classes spécifiques à
	 * chaque fantômes (Blinky,Inky,Pinky et Clyde)
	 * 
	 * 
	 * @return Durée en millisecondes de l'état immobile.
	 */
	abstract public int getDureeImmobile();
	
	/**
	 * Méthode getDirectionSuivante() : Cette méthode prennant en paramètre une liste d'entites
	 * et un objet labyrinthe permet aux renvoie une valeur du type enuméré Direction variant
	 * selon le mode du fantôme.
	 * En mode Dispersion, le fantome se dirige vers le point de dispersion qui lui est attribué
	 * Il existe 4 points de dispersion qui sont situés aux quatres coins du niveau.
	 * Cela permet de le disperser dans le niveau plus efficacement
	 * En mode Sortie, le fantome vient de sortir du mode Immobile et se dirige vers la sortie de la 
	 * maison des fantomes pour en sortir. C'est l'inverse pour le mode EstMange.
	 * En mode Effrayé, le fantome se deplace de façon aléatoire
	 * En mode Chasse, le fantome cible Pacman selon leur nature. Chaque fantome cible
	 * Pacman d'une façon différente.
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
	 * Méthode getDirectionAleatoire() : Cette méthode permet au fantôme sélectionner une case valide
	 * aléatoire vers laquelle il peut se déplacer. Contrairement à une case fixe,
	 * un fantôme qui traverse une case cible aléatoire peut revenir en arrière par le chemin
	 * qu'il a emprunté.
	 *
	 * @param lab
	 * @param ptDeDepart
	 * @return une case aléatoire valide
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
	 * Méthode getDirectionFixe() : Cette méthode permet au fantôme de sélectionner une case valide vers laquelle
	 * il peut se déplacer en utilisant la sélection de case à cible fixe. Cette sélection de case à cible fixe
	 * minimise la distance entre le point actuel du fantôme et de son point d'arrivée de sorte à ce qu'elle soit
	 * optimale. Dans le cas où il existe deux mêmes chemins de valeur optimale, le fantome choisit la première des
	 * deux directions dans l'ordre de l'enum Direction.
	 * @param ptArrive
	 * @return une case cible précise valide
	 */
	private Direction getDirectionFixe(Labyrinthe lab, Point2D ptArrive) {	
		Direction nouvelleDirection = null;
		double distanceMinimal = Double.MAX_VALUE;

		for(Direction direction: Direction.values()) {
			Point2D adjacent = lab.adjacent(getPointActuel(), direction,1);
			double dist = adjacent.distance(ptArrive);
			Case caseAdjacent = lab.getCase(adjacent);
						
		   /* Si la nouvelle direction que l'on teste n'est pas la direction opposée
			* et que l'on peut se déplacer à la case adjacente dans cette direction (à un nbrdeCases donné, ici 1)
			* et que la distance de cette case est inférieur à la distance de la case pour la direction
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
	 * Méthode getPointChasse() : Cette méthode permet de définir la manière selon laquelle le fantôme
	 * va se déplacer en mode chasse. Chaque fantôme ayant une manière de se déplacer qui lui est propre,
	 * la méthode est donc abstraite pour être redéfinie au cas par cas pour chaque fantôme.
	 * 
	 * @param entites
	 * @param lab
	 * @return la case ciblée en mode chasse
	 */
	abstract protected Point2D getPointChasse(List<Entite> entites, Labyrinthe lab);
	
}

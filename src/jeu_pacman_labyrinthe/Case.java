package jeu_pacman_labyrinthe;

import jeu_pacman_affichage.Sprite;

/**
 * Cette classe représente un type énuméré qui modélise les différents éléments qui peuvent constituer une case du labyrinthe.
 * Chaque élément d'une case du labyrinthe correspond à un sprite qui se trouve dans le fichier sprite.png
 * 
 * @author Christian 
 * @author Darlène 
 * @author Lisa-Marie
 *
 */
public enum Case {
	/**
	 * S : Case de sortie des fantômes
	 * M : Case de départ de Pac-Man
	 * B : Case de départ de Blinky
	 * P : Case de départ de Pinky
	 * C : Case de départ de Clyde
	 * I : Case de départ de Inky
	 * b : Case du point de dispersion de Blinky
	 * p : Case du point de dispersion de Pinky
	 * c : Case du point de dispersion de Clyde
	 * i : Case du point de dispersion de Inky
	 */
	
	/**
	 * 0 : Case vide
	 */
	VIDE(true,false),
	/**
	 * 1 : Case gomme
	 */
	GOMME(true,true),
	/**
	 * 2 : Case super-Gomme
	 */
	SUPER_GOMME(true,true),
	/**
	 * 3 : Case du mur à l'horizontale
	 */
	MUR_HORIZONTALE(false,false),
	/**
	 * 4 : Case du mur à la verticale
	 */
	MUR_VERTICALE(false,false),
	/**
	 * 5 : Case du mur supérieur gauche
	 */
	MUR_SUPERIEUR_GAUCHE(false,false),
	/**
	 * 6 : Case du mur supérieur droit
	 */
	MUR_SUPERIEUR_DROIT(false,false),
	/**
	 * 7 : Case du mur inférieur gauche
	 */
	MUR_INFERIEUR_GAUCHE(false,false),
	/**
	 * 8 : Case du mur inférieur droit
	 */
	MUR_INFERIEUR_DROIT(false,false),
	/**
	 * 9 : Case du portail
	 */
	PORTAIL(false,false);
	/** Variable représentant l'objet Sprite */
	private Sprite sprite;
	/** Variable booléenne permettant de savoir si la case est traversable ou non*/
	private boolean estTraversable;
	/** Variable booléenne permettant de savoir si la case contient de la nourriture ou non*/
	private boolean contientNourriture;
	/**
	 * Constructeur de l'énumérateur Case qui est appelé lorsque l'on attribut l'une des valeurs
	 * de l'énumérateur à un objet de type Case.
	 * Les constructeurs des énumarateurs sont toujours privés.
	 * 
	 * @param estTraversable
	 * @param contientNourriture
	 */
	private Case(boolean estTraversable, boolean contientNourriture) {
		sprite = new Sprite(this.ordinal());
		this.estTraversable = estTraversable;
		this.contientNourriture = contientNourriture;
	}
	
	/**
	 * Méthode estTraversable() : Cette méthode permet de savoir si les entités peuvent se déplacer sur la case
	 * en question ou non. Par exemple, la porte menant au point de départ des fantômes n'est pas traversable par Pac-Man.
	 * 
	 * @return true si la case est traversable, false sinon
	 */
	public boolean estTraversable() {
		return estTraversable;
	}
	/**
	 * Méthode contientNourriture() : Cette méthode permet de savoir si la case donnée contient de la nourriture (Une pac-gomme
	 * ou une super-gomme) ou non 
	 * 
	 * @return true si de la nourriture se trouve sur la case, false sinon
	 */
	public boolean contientNourriture() {
		return contientNourriture;
	}
	
	/**
	 * Méthode getSprite() : Cette méthode retourne le sprite associé à une case donnée
	 * Cette méthode est notamment utilisée pour reconstruire le labyrinthe.
	 * 
	 * @return Objet sprite associé à la case donnée
	 */
	public Sprite getSprite() {
		return sprite;
	}
}

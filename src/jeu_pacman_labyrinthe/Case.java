package jeu_pacman_labyrinthe;

import jeu_pacman_affichage.Sprite;

/**
 * Cette classe repr�sente un type �num�r� qui mod�lise les diff�rents �l�ments qui peuvent constituer une case du labyrinthe.
 * Chaque �l�ment d'une case du labyrinthe correspond � un sprite qui se trouve dans le fichier sprite.png
 * 
 * @author Christian 
 * @author Darl�ne 
 * @author Lisa-Marie
 *
 */
public enum Case {
	/**
	 * S : Case de sortie des fant�mes
	 * M : Case de d�part de Pac-Man
	 * B : Case de d�part de Blinky
	 * P : Case de d�part de Pinky
	 * C : Case de d�part de Clyde
	 * I : Case de d�part de Inky
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
	 * 3 : Case du mur � l'horizontale
	 */
	MUR_HORIZONTALE(false,false),
	/**
	 * 4 : Case du mur � la verticale
	 */
	MUR_VERTICALE(false,false),
	/**
	 * 5 : Case du mur sup�rieur gauche
	 */
	MUR_SUPERIEUR_GAUCHE(false,false),
	/**
	 * 6 : Case du mur sup�rieur droit
	 */
	MUR_SUPERIEUR_DROIT(false,false),
	/**
	 * 7 : Case du mur inf�rieur gauche
	 */
	MUR_INFERIEUR_GAUCHE(false,false),
	/**
	 * 8 : Case du mur inf�rieur droit
	 */
	MUR_INFERIEUR_DROIT(false,false),
	/**
	 * 9 : Case du portail
	 */
	PORTAIL(false,false);
	/** Variable repr�sentant l'objet Sprite */
	private Sprite sprite;
	/** Variable bool�enne permettant de savoir si la case est traversable ou non*/
	private boolean estTraversable;
	/** Variable bool�enne permettant de savoir si la case contient de la nourriture ou non*/
	private boolean contientNourriture;
	/**
	 * Constructeur de l'�num�rateur Case qui est appel� lorsque l'on attribut l'une des valeurs
	 * de l'�num�rateur � un objet de type Case.
	 * Les constructeurs des �numarateurs sont toujours priv�s.
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
	 * M�thode estTraversable() : Cette m�thode permet de savoir si les entit�s peuvent se d�placer sur la case
	 * en question ou non. Par exemple, la porte menant au point de d�part des fant�mes n'est pas traversable par Pac-Man.
	 * 
	 * @return true si la case est traversable, false sinon
	 */
	public boolean estTraversable() {
		return estTraversable;
	}
	/**
	 * M�thode contientNourriture() : Cette m�thode permet de savoir si la case donn�e contient de la nourriture (Une pac-gomme
	 * ou une super-gomme) ou non 
	 * 
	 * @return true si de la nourriture se trouve sur la case, false sinon
	 */
	public boolean contientNourriture() {
		return contientNourriture;
	}
	
	/**
	 * M�thode getSprite() : Cette m�thode retourne le sprite associ� � une case donn�e
	 * Cette m�thode est notamment utilis�e pour reconstruire le labyrinthe.
	 * 
	 * @return Objet sprite associ� � la case donn�e
	 */
	public Sprite getSprite() {
		return sprite;
	}
}

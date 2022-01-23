package jeu_pacman_affichage;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * La classe Sprite permet de r�cup�rer un set de sprites � partir d'une image (fichier.png)
 * 
 * @author Darl�ne
 */
public class Sprite {

	/** Constantes permettant d'assembler le chemin de l'image contenant le set de sprites */
	private static final String SPRITE_CHEMIN = "./assets/sprites/sprites.png";
	private static final int LONGUEUR = 10;
	private static final int LARGEUR = 10;
	
	/** Constante pr�cisant la taille de chaque sprite dans le set de sprites */
	public static final int SIZE = 100;
	
	/** 
	 * Variables enti�res r�pr�sentant l'index pour lequel l'ensemble de sprites de l'entite se trouve
	 * et le nombre de rang�es et de colonnes dont cet ensemble est constitu�
	 */
	private int _index, rangees, colonnes;
	/**
	 * Premier constructeur de la classe sprite, si on a que l'index en information il prend par defaut les valeur 1,1 
	 * ce constructeur appelle le second constructeur. Ce constructeur peut �tre appel� en dehors de la classe
	 * @param index
	 */
	public Sprite(int index) {
		this(index, 1, 1);
	}
	/**
	 * Second constructeur de la classe sprite, qui prend le nombre de rang�es, de colonnes et l'index pour lequel
	 * l'ensemble de sprites de l'entit� se trouve. Ce constructeur peut �tre appel� en dehors de la classe
	 * @param i
	 * @param r
	 * @param c
	 */
	public Sprite(int i, int r, int c) {
		rangees = r;
		colonnes = c;
		setIndex(i);
	}
	
	/**
	 * M�thode setIndex() : Cette m�thode permet de d�finir l'index du sprite
	 * @param index
	 */
	public void setIndex(int index) {
		if(index < 0 || index >= LONGUEUR * LARGEUR)
			throw new IllegalArgumentException("Invalid sprite index");
		_index = index;
	}
	
	/**
	 * M�thode getDimensions(): Cette m�thode retourne un rectangle d�finissant la localisation du sprite
	 * sur l'image. Elle est utilis�e pour afficher le sprite sur la fen�tre de jeu
	 * 
	 * @return new Rectangle(x, y, rangees * SIZE, colonnes * SIZE)
	 */
	public Rectangle getDimensions() {
		int x = (_index % LONGUEUR) * SIZE;
		int y = (_index / LONGUEUR) * SIZE;
		return new Rectangle(x, y, rangees * SIZE, colonnes * SIZE);
	}
	
	/**
	 * M�thode getSpriteChemin() : Cette m�thode retourne un rectangle d�finissant la localisation du sprite
	 * sur l'image. Elle est utilis�e pour afficher le sprite sur la fen�tre de jeu.
	 * 
	 * @return SPRITE_CHEMIN
	 */
	public static String getSpriteChemin() {
		return SPRITE_CHEMIN;
	}
	
	/**
	 * M�thode dessiner() : Cette m�thode sert � pouvoir dessiner un sprite sur la fen�tre de jeu.
	 */
	public void dessiner(Graphics g, BufferedImage image, Rectangle destination) {
		Rectangle rect = this.getDimensions();
		g.drawImage(image, destination.x, destination.y, 
					destination.x + destination.width, 
					destination.y + destination.height, 
				rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, null);
	}
	
}

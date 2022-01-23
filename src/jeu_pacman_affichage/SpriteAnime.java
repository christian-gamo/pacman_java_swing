package jeu_pacman_affichage;

import jeu_pacman_entites.Entite.Direction;

/**
 * La classe SpriteAnime est la classe qui sera associ�e � la classe Entite pour pouvoir
 * afficher des sprites sur la fen�tre de jeu. Cette classe h�rite de la classe Sprite car par extension
 * un sprite anime est un sprite.
 * 
 * @author Christian
 *
 */
public class SpriteAnime extends Sprite {
	/**Variables enti�res repr�sentant le nombre de frames, le compteur permettant de savoir quelle sprite
	 * de l'animation choisir et la base qui est l'index du sprite */
	private int frames, compteur, base;
	/** 
	 * Premier constructeur de la classe SpriteAnime, si il manque des informations par rapport aux 2�me et 3�me 
	 * constructeurs de la classe des valeurs par d�faut seront attribu�es aux param�tres du constructeur.
	 * Ce constructeur fait appel au 3�me constructeur de la classe
	 * et peut �tre appel� en dehors de la classe Sprite anime.
	 * 
	 * @param base
	 * @param dir
	 */
	public SpriteAnime(int base, Direction dir) {
		this(base, dir, 1);
	}
	/**
	 * Deuxi�me constructeur de la classe SpriteAnime, si il manque des informations par rapport au 3�me constructeur de la classe
	 * des valeurs par d�faut seront attribu�es aux param�tres du constructeur. 
	 * Ce constructeur fait appel au 3�me constructeur de la classe
	 * et peut �tre appel� en dehors de la classe Sprite anime.
	 * 
	 * @param base
	 * @param dir
	 * @param frames
	 */
	public SpriteAnime(int base, Direction dir, int frames) {
		this(base, dir, 1, 1, frames);
	}
	/**
	 * Troisi�me constructeur de la classe SpriteAnime, si toutes les informations sont pr�sentes, elles sont attribu�es
	 * aux param�tres du constructeur
	 * 
	 * @param b
	 * @param dir
	 * @param r
	 * @param c
	 * @param f
	 */
	public SpriteAnime(int b, Direction dir, int r, int c, int f) {
		/**
		 * Appel au constructeur de Sprite
		 */
		super(b, r, c);
		
		base = b;
		frames = f;
		compteur = -1;
		frameSuivante(dir);
	}
	
	/**
	 * M�thode frameSuivante() : Cette m�thode de permet passer � la frame suivante de l'animation. Il incr�mente le compteur
	 * et met l'index du sprite � la bonne valeur
	 * @param dir
	 * @return sprite de la frame suivante associ�e � une entit�
	 */
	public SpriteAnime frameSuivante(Direction dir) {
		int index = base + dir.ordinal() * frames;
		compteur = (compteur + 1) % frames;
		setIndex(index + compteur);
		return this;
	}
}

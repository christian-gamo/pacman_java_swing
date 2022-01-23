package jeu_pacman_affichage;

import jeu_pacman_entites.Entite.Direction;

/**
 * La classe SpriteAnime est la classe qui sera associée à la classe Entite pour pouvoir
 * afficher des sprites sur la fenêtre de jeu. Cette classe hérite de la classe Sprite car par extension
 * un sprite anime est un sprite.
 * 
 * @author Christian
 *
 */
public class SpriteAnime extends Sprite {
	/**Variables entières représentant le nombre de frames, le compteur permettant de savoir quelle sprite
	 * de l'animation choisir et la base qui est l'index du sprite */
	private int frames, compteur, base;
	/** 
	 * Premier constructeur de la classe SpriteAnime, si il manque des informations par rapport aux 2ème et 3ème 
	 * constructeurs de la classe des valeurs par défaut seront attribuées aux paramètres du constructeur.
	 * Ce constructeur fait appel au 3ème constructeur de la classe
	 * et peut être appelé en dehors de la classe Sprite anime.
	 * 
	 * @param base
	 * @param dir
	 */
	public SpriteAnime(int base, Direction dir) {
		this(base, dir, 1);
	}
	/**
	 * Deuxième constructeur de la classe SpriteAnime, si il manque des informations par rapport au 3ème constructeur de la classe
	 * des valeurs par défaut seront attribuées aux paramètres du constructeur. 
	 * Ce constructeur fait appel au 3ème constructeur de la classe
	 * et peut être appelé en dehors de la classe Sprite anime.
	 * 
	 * @param base
	 * @param dir
	 * @param frames
	 */
	public SpriteAnime(int base, Direction dir, int frames) {
		this(base, dir, 1, 1, frames);
	}
	/**
	 * Troisième constructeur de la classe SpriteAnime, si toutes les informations sont présentes, elles sont attribuées
	 * aux paramètres du constructeur
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
	 * Méthode frameSuivante() : Cette méthode de permet passer à la frame suivante de l'animation. Il incrémente le compteur
	 * et met l'index du sprite à la bonne valeur
	 * @param dir
	 * @return sprite de la frame suivante associée à une entité
	 */
	public SpriteAnime frameSuivante(Direction dir) {
		int index = base + dir.ordinal() * frames;
		compteur = (compteur + 1) % frames;
		setIndex(index + compteur);
		return this;
	}
}



import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.Test;

import jeu_pacman_application.Jeu;
import jeu_pacman_entites.Fantome;
import jeu_pacman_entites.Pacman;
import jeu_pacman_entites.Pinky;
import jeu_pacman_entites.Entite.Direction;
import jeu_pacman_entites.Fantome.Mode;
import jeu_pacman_labyrinthe.Case;
import jeu_pacman_labyrinthe.Labyrinthe;

/**
 * Test JUnit permettant de tester les d�placements de Pacman dans le labyrinthe et ses interactions
 * avec ce dernier et les autres fantomes.
 * @author Christian 
 *
 */
class JeuTest {

	
	/**
	 * Test pour v�rifier si Pacman mange bien la gomme lorsqu'il passe par une Case
	 * en contenant une.
	 * La Case doit �tre donc vide apr�s son passage.
	 * Ici le niveau est constitu� d'une unique couloir contenant des gommes et un fantome;
	 * Ce couloir est ouvert des deux cot�s et permet de passer de l'autre c�t� de l'�cran.
	 */
	@Test
	void testDeplacement1() {
		try {
			String cheminNiveau = "./assets/niveaux/niveauTest.txt";
			Jeu.setCheminNiveau(cheminNiveau);
			Jeu jeu = Jeu.chargerDonnees("Joueur");
			
			//On v�rifie si le chemin du fichier a bien �t� chang�
			assertEquals(cheminNiveau,Jeu.getCheminNiveau());
			
			Labyrinthe lab = jeu.getLabyrinthe();
			Pacman pacman = jeu.getPacman();
			
			pacman.setDirectionActuelle(Direction.DROITE);
			//On v�rifie si la direction actuelle de pacman a bien �t� chang�
			assertEquals(pacman.getDirectionActuelle(),Direction.DROITE);
			
			//On choisit une case du labyrinthe
			Point2D adjacent = lab.adjacent
			(pacman.getPointInitial(), pacman.getDirectionActuelle(), 1);
			//On v�rifie si la case contient bien une �num�ration gomme
			assertEquals(lab.getCase(adjacent),Case.GOMME);
			
			
			//Tant que la case actuelle de pacman n'est pas �gale
			//� la case choisi
			while(!(lab.pointLabyrinthe(pacman.getPointActuel())
			.equals(lab.pointLabyrinthe(adjacent)))) {
				pacman.deplacer(jeu.entites(), lab);
			}
			
			//On d�place pacman encore une fois pour qu'il mange la gomme
			pacman.deplacer(jeu.entites(),lab);
			
			//On v�rifie si la case est vide
			assertEquals(lab.getCase(adjacent),Case.VIDE);
			 
		} catch (ClassNotFoundException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Test pour v�rifier si Pacman passe bien de l'autre c�t� de l'�cran lorsqu'il atteint
	 * le bout du tunnel. Pour cela on v�rifie si Pacman a mang� une gomme se trouvant
	 * de l'autre c�t� de l'�cran.
	 * Ici le niveau est constitu� d'une unique couloir contenant des gommes et un fantome;
	 * Ce couloir est ouvert des deux cot�s et permet de passer de l'autre c�t� de l'�cran.
	 */
	@Test
	void testDeplacement2() {
		try {
			String cheminNiveau = "./assets/niveaux/niveauTest.txt";
			Jeu.setCheminNiveau(cheminNiveau);
			Jeu jeu = Jeu.chargerDonnees("Joueur");
			
			//On v�rifie si le chemin du fichier a bien �t� chang�
			assertEquals(cheminNiveau,Jeu.getCheminNiveau());
			
			Labyrinthe lab = jeu.getLabyrinthe();
			Pacman pacman = jeu.getPacman();
			
			pacman.setDirectionActuelle(Direction.DROITE);
			//On v�rifie si la direction actuelle de pacman a bien �t� chang�
			assertEquals(pacman.getDirectionActuelle(),Direction.DROITE);
			
			//On choisit une case du labyrinthe qui n'est accessible 
			//seulement en traversant le tunnel
			Point2D caseGauche = lab.adjacent
			(pacman.getPointInitial(), Direction.GAUCHE, 12);
			//On v�rifie si la case contient bien une gomme
			assertEquals(lab.getCase(caseGauche),Case.GOMME);
		
			//Tant que la case actuelle de pacman n'est pas �gale
			//� la case choisi
			while(!(lab.pointLabyrinthe(pacman.getPointActuel())
			.equals(lab.pointLabyrinthe(caseGauche)))) {
				pacman.deplacer(jeu.entites(), lab);
			}
			
			//On d�place pacman encore une fois pour qu'il mange la gomme
			pacman.deplacer(jeu.entites(),lab);
			
			//On v�rifie si la case est bien vide
			//Si cela est v�rifi� Pacman a bien emprunt� le tunnel pour
			//passer de l'autre c�t�
			assertEquals(lab.getCase(caseGauche),Case.VIDE);
			 
		} catch (ClassNotFoundException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Test pour v�rifier si Pacman entre bien en collision avec un fantome Pinky situ�
	 * 6 cases � sa gauche. On teste donc la m�thode collision() de la classe Jeu.
	 * Ici le niveau est constitu� d'une unique couloir contenant des gommes et un fantome;
	 * Ce couloir est ouvert des deux cot�s et permet de passer de l'autre c�t� de l'�cran.
	 */
	@Test
	void testCollision() {
		try {
			String cheminNiveau = "./assets/niveaux/niveauTest.txt";
			Jeu.setCheminNiveau(cheminNiveau);
			Jeu jeu = Jeu.chargerDonnees("Joueur");
			
			//On v�rifie si le chemin du fichier a bien �t� chang�
			assertEquals(cheminNiveau,Jeu.getCheminNiveau());
			
			Labyrinthe lab = jeu.getLabyrinthe();
			Pacman pacman = jeu.getPacman();
			
			//Ici Pinky correspond � fantomes.get(3)
			//�tant le dernier des 4 ajout�s � la liste
			List<Fantome> fantomes = jeu.getFantomes();
		
			//Ici le fantome se situe � gauche de Pacman
			pacman.setDirectionActuelle(Direction.GAUCHE);
			//On v�rifie si la direction actuelle de pacman a bien �t� chang�
			assertEquals(pacman.getDirectionActuelle(),Direction.GAUCHE);
			
			
			//Tant que la case actuelle de pacman n'est pas �gale
			//� celle du fantome
			while(!(lab.pointLabyrinthe(pacman.getPointActuel())
			.equals(lab.pointLabyrinthe(fantomes.get(3).getPointActuel())))) {
				pacman.deplacer(jeu.entites(), lab);
			}
			
			//Ici on peut s'assurer qu'il y a bien une collision entre Pacman et Pinky
			//Cette fonction est d�ja appel� dans la m�thode collision de Jeu
			assertTrue(lab.detectionCollision(pacman, fantomes.get(3)));
			jeu.collision();
			
			//Ici on v�rifie que Pacman a bien perdu une vie
			//car Pinky n'est pas en Mode Effraye
			assertEquals(pacman.getNbrVies(),2);
			
			//On passe Pinky en mode effraye
			fantomes.get(3).setMode(Mode.EFFRAYE);;
			jeu.collision();
			
			//On v�rifie siPacman peut bien le manger sans perdre de vie.
			assertEquals(pacman.getNbrVies(),2);
			
		} catch (ClassNotFoundException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Test pour v�rifier si Pacman a bien mang� toutes les gommes du Labyrinthe.
	 * et passe au niveau suivant.
	 * On teste donc la fonction NiveauSuivant de Jeu.
	 * Ici le niveau est constitu� d'une unique couloir contenant des gommes et un fantome;
	 * Ce couloir est ouvert des deux cot�s et permet de passer de l'autre c�t� de l'�cran.
	 */
	@Test
	void testNiveauSuivant() {
		try {
			String cheminNiveau = "./assets/niveaux/niveauTest.txt";
			Jeu.setCheminNiveau(cheminNiveau);
			Jeu jeu = Jeu.chargerDonnees("Joueur");
			
			//On v�rifie si le chemin du fichier a bien �t� chang�
			assertEquals(cheminNiveau,Jeu.getCheminNiveau());
			
			Labyrinthe lab = jeu.getLabyrinthe();
			Pacman pacman = jeu.getPacman();
			
			Point2D pointInitial = pacman.getPointInitial();
			
			pacman.setDirectionActuelle(Direction.DROITE);
			//On v�rifie si la direction actuelle de pacman a bien �t� chang�
			assertEquals(pacman.getDirectionActuelle(),Direction.DROITE);
			
			//On choisit une case du labyrinthe qui n'est accessible 
			//seulement en traversant le tunnel
			Point2D caseGauche = lab.adjacent
			(pacman.getPointInitial(), Direction.GAUCHE, 7);
			//On v�rifie si la case contient bien une gomme
			assertEquals(lab.getCase(caseGauche),Case.GOMME);
		
			//Tant que le labyrinthe n'est ps vide de gommes
			while(!jeu.labyrintheEstFini()) {
				pacman.deplacer(jeu.entites(), lab);
			}
			
			//On v�rifie si la case est bien vide
			//En th�orie Pacman a mang� toutes les gommes du labyrinthe
			assertEquals(lab.getCase(caseGauche),Case.VIDE);
			
			//on passe au niveau suivant
			jeu.niveauSuivant();
						
			//On v�rifie si Pacman est bien revenu � la case initial
			assertTrue(pointInitial.equals(pacman.getPointActuel()));
			 
		} catch (ClassNotFoundException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}	
	}
	
	
}
		






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
 * Test JUnit permettant de tester les déplacements de Pacman dans le labyrinthe et ses interactions
 * avec ce dernier et les autres fantomes.
 * @author Christian 
 *
 */
class JeuTest {

	
	/**
	 * Test pour vérifier si Pacman mange bien la gomme lorsqu'il passe par une Case
	 * en contenant une.
	 * La Case doit être donc vide après son passage.
	 * Ici le niveau est constitué d'une unique couloir contenant des gommes et un fantome;
	 * Ce couloir est ouvert des deux cotés et permet de passer de l'autre côté de l'écran.
	 */
	@Test
	void testDeplacement1() {
		try {
			String cheminNiveau = "./assets/niveaux/niveauTest.txt";
			Jeu.setCheminNiveau(cheminNiveau);
			Jeu jeu = Jeu.chargerDonnees("Joueur");
			
			//On vérifie si le chemin du fichier a bien été changé
			assertEquals(cheminNiveau,Jeu.getCheminNiveau());
			
			Labyrinthe lab = jeu.getLabyrinthe();
			Pacman pacman = jeu.getPacman();
			
			pacman.setDirectionActuelle(Direction.DROITE);
			//On vérifie si la direction actuelle de pacman a bien été changé
			assertEquals(pacman.getDirectionActuelle(),Direction.DROITE);
			
			//On choisit une case du labyrinthe
			Point2D adjacent = lab.adjacent
			(pacman.getPointInitial(), pacman.getDirectionActuelle(), 1);
			//On vérifie si la case contient bien une énumération gomme
			assertEquals(lab.getCase(adjacent),Case.GOMME);
			
			
			//Tant que la case actuelle de pacman n'est pas égale
			//à la case choisi
			while(!(lab.pointLabyrinthe(pacman.getPointActuel())
			.equals(lab.pointLabyrinthe(adjacent)))) {
				pacman.deplacer(jeu.entites(), lab);
			}
			
			//On déplace pacman encore une fois pour qu'il mange la gomme
			pacman.deplacer(jeu.entites(),lab);
			
			//On vérifie si la case est vide
			assertEquals(lab.getCase(adjacent),Case.VIDE);
			 
		} catch (ClassNotFoundException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Test pour vérifier si Pacman passe bien de l'autre côté de l'écran lorsqu'il atteint
	 * le bout du tunnel. Pour cela on vérifie si Pacman a mangé une gomme se trouvant
	 * de l'autre côté de l'écran.
	 * Ici le niveau est constitué d'une unique couloir contenant des gommes et un fantome;
	 * Ce couloir est ouvert des deux cotés et permet de passer de l'autre côté de l'écran.
	 */
	@Test
	void testDeplacement2() {
		try {
			String cheminNiveau = "./assets/niveaux/niveauTest.txt";
			Jeu.setCheminNiveau(cheminNiveau);
			Jeu jeu = Jeu.chargerDonnees("Joueur");
			
			//On vérifie si le chemin du fichier a bien été changé
			assertEquals(cheminNiveau,Jeu.getCheminNiveau());
			
			Labyrinthe lab = jeu.getLabyrinthe();
			Pacman pacman = jeu.getPacman();
			
			pacman.setDirectionActuelle(Direction.DROITE);
			//On vérifie si la direction actuelle de pacman a bien été changé
			assertEquals(pacman.getDirectionActuelle(),Direction.DROITE);
			
			//On choisit une case du labyrinthe qui n'est accessible 
			//seulement en traversant le tunnel
			Point2D caseGauche = lab.adjacent
			(pacman.getPointInitial(), Direction.GAUCHE, 12);
			//On vérifie si la case contient bien une gomme
			assertEquals(lab.getCase(caseGauche),Case.GOMME);
		
			//Tant que la case actuelle de pacman n'est pas égale
			//à la case choisi
			while(!(lab.pointLabyrinthe(pacman.getPointActuel())
			.equals(lab.pointLabyrinthe(caseGauche)))) {
				pacman.deplacer(jeu.entites(), lab);
			}
			
			//On déplace pacman encore une fois pour qu'il mange la gomme
			pacman.deplacer(jeu.entites(),lab);
			
			//On vérifie si la case est bien vide
			//Si cela est vérifié Pacman a bien emprunté le tunnel pour
			//passer de l'autre côté
			assertEquals(lab.getCase(caseGauche),Case.VIDE);
			 
		} catch (ClassNotFoundException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Test pour vérifier si Pacman entre bien en collision avec un fantome Pinky situé
	 * 6 cases à sa gauche. On teste donc la méthode collision() de la classe Jeu.
	 * Ici le niveau est constitué d'une unique couloir contenant des gommes et un fantome;
	 * Ce couloir est ouvert des deux cotés et permet de passer de l'autre côté de l'écran.
	 */
	@Test
	void testCollision() {
		try {
			String cheminNiveau = "./assets/niveaux/niveauTest.txt";
			Jeu.setCheminNiveau(cheminNiveau);
			Jeu jeu = Jeu.chargerDonnees("Joueur");
			
			//On vérifie si le chemin du fichier a bien été changé
			assertEquals(cheminNiveau,Jeu.getCheminNiveau());
			
			Labyrinthe lab = jeu.getLabyrinthe();
			Pacman pacman = jeu.getPacman();
			
			//Ici Pinky correspond à fantomes.get(3)
			//étant le dernier des 4 ajoutés à la liste
			List<Fantome> fantomes = jeu.getFantomes();
		
			//Ici le fantome se situe à gauche de Pacman
			pacman.setDirectionActuelle(Direction.GAUCHE);
			//On vérifie si la direction actuelle de pacman a bien été changé
			assertEquals(pacman.getDirectionActuelle(),Direction.GAUCHE);
			
			
			//Tant que la case actuelle de pacman n'est pas égale
			//à celle du fantome
			while(!(lab.pointLabyrinthe(pacman.getPointActuel())
			.equals(lab.pointLabyrinthe(fantomes.get(3).getPointActuel())))) {
				pacman.deplacer(jeu.entites(), lab);
			}
			
			//Ici on peut s'assurer qu'il y a bien une collision entre Pacman et Pinky
			//Cette fonction est déja appelé dans la méthode collision de Jeu
			assertTrue(lab.detectionCollision(pacman, fantomes.get(3)));
			jeu.collision();
			
			//Ici on vérifie que Pacman a bien perdu une vie
			//car Pinky n'est pas en Mode Effraye
			assertEquals(pacman.getNbrVies(),2);
			
			//On passe Pinky en mode effraye
			fantomes.get(3).setMode(Mode.EFFRAYE);;
			jeu.collision();
			
			//On vérifie siPacman peut bien le manger sans perdre de vie.
			assertEquals(pacman.getNbrVies(),2);
			
		} catch (ClassNotFoundException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Test pour vérifier si Pacman a bien mangé toutes les gommes du Labyrinthe.
	 * et passe au niveau suivant.
	 * On teste donc la fonction NiveauSuivant de Jeu.
	 * Ici le niveau est constitué d'une unique couloir contenant des gommes et un fantome;
	 * Ce couloir est ouvert des deux cotés et permet de passer de l'autre côté de l'écran.
	 */
	@Test
	void testNiveauSuivant() {
		try {
			String cheminNiveau = "./assets/niveaux/niveauTest.txt";
			Jeu.setCheminNiveau(cheminNiveau);
			Jeu jeu = Jeu.chargerDonnees("Joueur");
			
			//On vérifie si le chemin du fichier a bien été changé
			assertEquals(cheminNiveau,Jeu.getCheminNiveau());
			
			Labyrinthe lab = jeu.getLabyrinthe();
			Pacman pacman = jeu.getPacman();
			
			Point2D pointInitial = pacman.getPointInitial();
			
			pacman.setDirectionActuelle(Direction.DROITE);
			//On vérifie si la direction actuelle de pacman a bien été changé
			assertEquals(pacman.getDirectionActuelle(),Direction.DROITE);
			
			//On choisit une case du labyrinthe qui n'est accessible 
			//seulement en traversant le tunnel
			Point2D caseGauche = lab.adjacent
			(pacman.getPointInitial(), Direction.GAUCHE, 7);
			//On vérifie si la case contient bien une gomme
			assertEquals(lab.getCase(caseGauche),Case.GOMME);
		
			//Tant que le labyrinthe n'est ps vide de gommes
			while(!jeu.labyrintheEstFini()) {
				pacman.deplacer(jeu.entites(), lab);
			}
			
			//On vérifie si la case est bien vide
			//En théorie Pacman a mangé toutes les gommes du labyrinthe
			assertEquals(lab.getCase(caseGauche),Case.VIDE);
			
			//on passe au niveau suivant
			jeu.niveauSuivant();
						
			//On vérifie si Pacman est bien revenu à la case initial
			assertTrue(pointInitial.equals(pacman.getPointActuel()));
			 
		} catch (ClassNotFoundException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}	
	}
	
	
}
		




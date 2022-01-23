package jeu_pacman_application;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * La classe Audio permet de jouer les sons originaux du jeu Pac-Man au sein de notre version du jeu, à partir de fichiers
 * audios sources données (en l'occurence ici des fichiers.wav) 
 * 
 * @author Darlène Lisa-Marie
 *
 */
public class Audio {
	/**Chaîne de caractères contenant le chemin absolu du fichier audio contenant le son de démarrage du jeu*/
	private String startPath ="./assets/sons/pacman_start.wav";
	/**Chaîne de caractères contenant le chemin absolu du fichier audio contenant le son de mort de Pac-Man*/
	private String mortPath  = "./assets/sons/pacman_mort.wav";
	/**Chaîne de caractères contenant le chemin absolu du fichier audio contenant le son lorsque Pac-Man mange une gomme*/
	private String mangePath  = "./assets/sons/pacman_mange.wav";
	/**Chaîne de caractères contenant le chemin absolu du fichier audio contenant le son lorsque Pac-Man mange un fantôme*/
	private String mangeFantomePath  = "./assets/sons/pacman_mange_fantome.wav";
	/**Chaîne de caractères contenant le chemin absolu du fichier audio contenant le son lorsque les fantômes sont effrayés*/
	private String fantomeEffrayePath = "./assets/sons/fantome_effraye.wav";
	
	/**Objets clip représentant des instances de l'interface clip qui représentent les différentes pistes audio
	 * nécessaires au bon fonctionnement du jeu */
	private Clip startAudio, mortAudio, mangeAudio, mangeAudio2, mangeFantomeAudio, fantomeEffrayeAudio;
	/**
	 * Constructeur de la classe Audio, ce constructeur peut être appelé en dehors de la classe Audio afin 
	 * de créer une nouvelle instance de cette classe pour jouer les différents sons du jeu.
	 * 
	 * Ce constructeur vérifie également à l'aide d'un try/catch si les chemins les fichiers sont bien des fichiers sons
	 * et si le chemin absolu des fichiers est bien correct, si ce n'est pas le cas une exception sera levée.
	 * 
	 */
	public Audio () {
		
		try {
			startAudio = AudioSystem.getClip();
			mortAudio = AudioSystem.getClip();
			mangeAudio = AudioSystem.getClip();
			mangeAudio2 = AudioSystem.getClip();
			mangeFantomeAudio = AudioSystem.getClip();
			fantomeEffrayeAudio = AudioSystem.getClip();
			
			startAudio.open(AudioSystem.getAudioInputStream(new File(startPath).getAbsoluteFile() ) );
			mortAudio.open(AudioSystem.getAudioInputStream(new File(mortPath).getAbsoluteFile() ) );
			mangeAudio.open(AudioSystem.getAudioInputStream(new File(mangePath).getAbsoluteFile() ) );
			mangeAudio2.open(AudioSystem.getAudioInputStream(new File(mangePath).getAbsoluteFile() ) );
			mangeFantomeAudio.open(AudioSystem.getAudioInputStream(new File(mangeFantomePath).getAbsoluteFile() ) );
			fantomeEffrayeAudio.open(AudioSystem.getAudioInputStream(new File(fantomeEffrayePath).getAbsoluteFile() ) );
			
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
			 
	}
	/**
	 * Méthode playStartAudio() : Cette méthode permet de jouer le son de démarrage du jeu, ce son n'est joué qu'une seule fois
	 * au démarrage du jeu
	 */
	public void playStartAudio () {
		startAudio.setFramePosition(0);
		startAudio.start();
	}
	/**
	 * Méthode playMortAudio() : Cette méthode permet de jouer le son de mort de Pac-Man, ce son n'est joué qu'une seule
	 * fois à la mort de Pac-Man
	 */
	public void playMortAudio() {
		mortAudio.setFramePosition(0);
		mortAudio.start();
	}
	
	/**
	 * Méthode loopMangeAudio() : Cette méthode permet de jouer le son lorsque Pac-Man mange une gomme
	 */
	public void loopMangeAudio() {
		/**
		 * Si l'audio lorsque Pac-Man mange une gomme n'est pas en cours de lecture, il faut le jouer continuellement 
		 * Cela permet de reproduire le son de la version originale du jeu avec exactitude lorsque Pac-Man
		 * mange une gomme
		 */
		if(!mangeAudio.isRunning()) {
			mangeAudio.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	/**
	 * Méthode stopMangeAudio() : Cette méthode permet de stopper le son lorsque Pac-Man mange une gomme
	 */
	public void stopMangeAudio() {
		mangeAudio.stop();
		/** Réinitialisation du fichier audio*/
        try {
			mangeAudio = AudioSystem.getClip();
			mangeAudio.open(AudioSystem.getAudioInputStream(new File(mangePath).getAbsoluteFile()));
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
    
    }
	/**
	 * Méthode playMangeFantomeAudio() : Cette méthode permet de jouer le son lorsque Pac-Man mange un fantôme
	 */
	public void playMangeFantomeAudio() {
		/**
		 * Si l'audio lorsque Pac-Man mange une fantôme n'est pas en cours de lecture, il faut le reinitialiser 
		 * Cela permet de reproduire le son de la version originale du jeu avec exactitude lorsque Pac-Man
		 * mange un fantôme
		 */
		if(!mangeFantomeAudio.isRunning()) {
			mangeFantomeAudio.setFramePosition(0);
		}
		mangeFantomeAudio.start();
	}
	/**
	 * Méthode loopFantomeEffrayeAudio() : Cette méthode permet de jouer le son lorsque un fantôme est effrayé
	 */
	public void loopFantomeEffrayeAudio() {
		/**
		 * Si l'audio lorsque un fantôme est effrayé n'est pas en cours de lecture, il faut le jouer continuellement 
		 * Cela permet de reproduire le son de la version originale du jeu avec exactitude lorsque les fantômes 
		 * sont effrayés
		 */
		if(!fantomeEffrayeAudio.isRunning()) {
			fantomeEffrayeAudio.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	/**
	 * Méthode stopFantomeEffrayeAudio() : Cette méthode permet de stopper le son lorsque un fantôme est effrayé
	 */
	public void stopFantomeEffrayeAudio(){
		fantomeEffrayeAudio.stop();
		/** Réinitialisation du fichier audio*/
		try {
			fantomeEffrayeAudio = AudioSystem.getClip();
			fantomeEffrayeAudio.open(AudioSystem.getAudioInputStream(new File(fantomeEffrayePath).getAbsoluteFile() ) );
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
    }
	
}

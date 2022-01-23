package jeu_pacman_application;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * La classe Audio permet de jouer les sons originaux du jeu Pac-Man au sein de notre version du jeu, � partir de fichiers
 * audios sources donn�es (en l'occurence ici des fichiers.wav) 
 * 
 * @author Darl�ne Lisa-Marie
 *
 */
public class Audio {
	/**Cha�ne de caract�res contenant le chemin absolu du fichier audio contenant le son de d�marrage du jeu*/
	private String startPath ="./assets/sons/pacman_start.wav";
	/**Cha�ne de caract�res contenant le chemin absolu du fichier audio contenant le son de mort de Pac-Man*/
	private String mortPath  = "./assets/sons/pacman_mort.wav";
	/**Cha�ne de caract�res contenant le chemin absolu du fichier audio contenant le son lorsque Pac-Man mange une gomme*/
	private String mangePath  = "./assets/sons/pacman_mange.wav";
	/**Cha�ne de caract�res contenant le chemin absolu du fichier audio contenant le son lorsque Pac-Man mange un fant�me*/
	private String mangeFantomePath  = "./assets/sons/pacman_mange_fantome.wav";
	/**Cha�ne de caract�res contenant le chemin absolu du fichier audio contenant le son lorsque les fant�mes sont effray�s*/
	private String fantomeEffrayePath = "./assets/sons/fantome_effraye.wav";
	
	/**Objets clip repr�sentant des instances de l'interface clip qui repr�sentent les diff�rentes pistes audio
	 * n�cessaires au bon fonctionnement du jeu */
	private Clip startAudio, mortAudio, mangeAudio, mangeAudio2, mangeFantomeAudio, fantomeEffrayeAudio;
	/**
	 * Constructeur de la classe Audio, ce constructeur peut �tre appel� en dehors de la classe Audio afin 
	 * de cr�er une nouvelle instance de cette classe pour jouer les diff�rents sons du jeu.
	 * 
	 * Ce constructeur v�rifie �galement � l'aide d'un try/catch si les chemins les fichiers sont bien des fichiers sons
	 * et si le chemin absolu des fichiers est bien correct, si ce n'est pas le cas une exception sera lev�e.
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
	 * M�thode playStartAudio() : Cette m�thode permet de jouer le son de d�marrage du jeu, ce son n'est jou� qu'une seule fois
	 * au d�marrage du jeu
	 */
	public void playStartAudio () {
		startAudio.setFramePosition(0);
		startAudio.start();
	}
	/**
	 * M�thode playMortAudio() : Cette m�thode permet de jouer le son de mort de Pac-Man, ce son n'est jou� qu'une seule
	 * fois � la mort de Pac-Man
	 */
	public void playMortAudio() {
		mortAudio.setFramePosition(0);
		mortAudio.start();
	}
	
	/**
	 * M�thode loopMangeAudio() : Cette m�thode permet de jouer le son lorsque Pac-Man mange une gomme
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
	 * M�thode stopMangeAudio() : Cette m�thode permet de stopper le son lorsque Pac-Man mange une gomme
	 */
	public void stopMangeAudio() {
		mangeAudio.stop();
		/** R�initialisation du fichier audio*/
        try {
			mangeAudio = AudioSystem.getClip();
			mangeAudio.open(AudioSystem.getAudioInputStream(new File(mangePath).getAbsoluteFile()));
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
    
    }
	/**
	 * M�thode playMangeFantomeAudio() : Cette m�thode permet de jouer le son lorsque Pac-Man mange un fant�me
	 */
	public void playMangeFantomeAudio() {
		/**
		 * Si l'audio lorsque Pac-Man mange une fant�me n'est pas en cours de lecture, il faut le reinitialiser 
		 * Cela permet de reproduire le son de la version originale du jeu avec exactitude lorsque Pac-Man
		 * mange un fant�me
		 */
		if(!mangeFantomeAudio.isRunning()) {
			mangeFantomeAudio.setFramePosition(0);
		}
		mangeFantomeAudio.start();
	}
	/**
	 * M�thode loopFantomeEffrayeAudio() : Cette m�thode permet de jouer le son lorsque un fant�me est effray�
	 */
	public void loopFantomeEffrayeAudio() {
		/**
		 * Si l'audio lorsque un fant�me est effray� n'est pas en cours de lecture, il faut le jouer continuellement 
		 * Cela permet de reproduire le son de la version originale du jeu avec exactitude lorsque les fant�mes 
		 * sont effray�s
		 */
		if(!fantomeEffrayeAudio.isRunning()) {
			fantomeEffrayeAudio.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	/**
	 * M�thode stopFantomeEffrayeAudio() : Cette m�thode permet de stopper le son lorsque un fant�me est effray�
	 */
	public void stopFantomeEffrayeAudio(){
		fantomeEffrayeAudio.stop();
		/** R�initialisation du fichier audio*/
		try {
			fantomeEffrayeAudio = AudioSystem.getClip();
			fantomeEffrayeAudio.open(AudioSystem.getAudioInputStream(new File(fantomeEffrayePath).getAbsoluteFile() ) );
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
    }
	
}

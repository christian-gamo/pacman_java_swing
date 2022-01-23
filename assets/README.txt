--- Projet Java L3 MIAGE | README.txt ---

Au sein de ce fichier, vous trouverez les diff�rentes �tapes � suivre afin d'assurer la bonne ex�cution de notre
programme. Pour rappel, nous avons choisi la th�matique n�1, en r�alisant notre propre version du jeu Pac-Man,
dont vous retrouverez les r�gles au niveau de la page 2 de notre rapport, joint � l'archive de notre projet.

(NB) : Le son du jeu est parfois assez �lev�, nous vous invitons donc d'avance � diminuer le volume de votre ordinateur 

1) Vous devez tout d'abord importer notre projet au sein de votre propre repository Eclipse afin que ce dernier
puisse s'afficher au niveau de votre environnement de d�veloppement (Package Explorer), pour cela vous pouvez
suivre les instructions suivantes : (File -> Import -> Import projects from file system or archive -> s�lectionner notre archive et
cocher le dossier associ� -> Finish)

2) Compiler et ex�cuter notre projet en faisant un clic droit sur le dossier Java_Projet_Pacman puis en cliquant sur 
'Run As Java Application', ainsi le programme va compiler et ex�cuter la fonction main se trouvant au sein de la
classe 'InterfaceGraphique.java' qui constitue le point de d�part de notre programme.

3) Vous devrez indiquer au sein de la console votre pseudo, cependant les caract�res sp�ciaux ne sont pas autoris�s (y compris les
lettres accentu�es) et appuyer sur entr�e pour lancer le jeu (la fen�tre d'application s'ouvrira).

4) Vous devez cliquer avec votre souris sur la fen�tre de jeu et vous pourrez ainsi contr�ler les d�placements de Pac-Man. Votre
but sera de r�colter toutes les gommes qui se trouvent sur le plateau de jeu, tout en �vitant les fant�mes. Si vous touchez un 
fant�me en dehors du mode super forme (en ayant pas consomm� de super-gomme qui sont au nombre de 4 sur le plateau) vous perdrez
une vie (elles sont au nombre de 3)

5) Si vous perdez la partie, vous devez fermer la fen�tre de jeu et vous trouverez le classement affich� au niveau de la console.
Le classement �tant sauvegard� sous la forme du fichier texte, vous y aurez �galement acc�s sans lancer le programme et vous retrouverez
ce m�me classement (mis-�-jour � chaque partie) dans le cas o� vous lanceriez une nouvelle partie.

6) Un autre scenario serait que vous remportiez la partie, ainsi le niveau se r�initialisera et la difficult� sera augment�e �
chaque fois que vous gagnerez la partie (c'est-�-dire que la vitesse des fant�mes qui vous poursuiverons sera d'autant plus importante).

-- Par GAMO Christian | SOUCCOUCHETTY Darl�ne | ZHENG Lisa-Marie
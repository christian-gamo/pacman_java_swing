--- Projet Java L3 MIAGE | README.txt ---

Au sein de ce fichier, vous trouverez les différentes étapes à suivre afin d'assurer la bonne exécution de notre
programme. Pour rappel, nous avons choisi la thématique n°1, en réalisant notre propre version du jeu Pac-Man,
dont vous retrouverez les règles au niveau de la page 2 de notre rapport, joint à l'archive de notre projet.

(NB) : Le son du jeu est parfois assez élevé, nous vous invitons donc d'avance à diminuer le volume de votre ordinateur 

1) Vous devez tout d'abord importer notre projet au sein de votre propre repository Eclipse afin que ce dernier
puisse s'afficher au niveau de votre environnement de développement (Package Explorer), pour cela vous pouvez
suivre les instructions suivantes : (File -> Import -> Import projects from file system or archive -> sélectionner notre archive et
cocher le dossier associé -> Finish)

2) Compiler et exécuter notre projet en faisant un clic droit sur le dossier Java_Projet_Pacman puis en cliquant sur 
'Run As Java Application', ainsi le programme va compiler et exécuter la fonction main se trouvant au sein de la
classe 'InterfaceGraphique.java' qui constitue le point de départ de notre programme.

3) Vous devrez indiquer au sein de la console votre pseudo, cependant les caractères spéciaux ne sont pas autorisés (y compris les
lettres accentuées) et appuyer sur entrée pour lancer le jeu (la fenêtre d'application s'ouvrira).

4) Vous devez cliquer avec votre souris sur la fenêtre de jeu et vous pourrez ainsi contrôler les déplacements de Pac-Man. Votre
but sera de récolter toutes les gommes qui se trouvent sur le plateau de jeu, tout en évitant les fantômes. Si vous touchez un 
fantôme en dehors du mode super forme (en ayant pas consommé de super-gomme qui sont au nombre de 4 sur le plateau) vous perdrez
une vie (elles sont au nombre de 3)

5) Si vous perdez la partie, vous devez fermer la fenêtre de jeu et vous trouverez le classement affiché au niveau de la console.
Le classement étant sauvegardé sous la forme du fichier texte, vous y aurez également accès sans lancer le programme et vous retrouverez
ce même classement (mis-à-jour à chaque partie) dans le cas où vous lanceriez une nouvelle partie.

6) Un autre scenario serait que vous remportiez la partie, ainsi le niveau se réinitialisera et la difficulté sera augmentée à
chaque fois que vous gagnerez la partie (c'est-à-dire que la vitesse des fantômes qui vous poursuiverons sera d'autant plus importante).

-- Par GAMO Christian | SOUCCOUCHETTY Darlène | ZHENG Lisa-Marie
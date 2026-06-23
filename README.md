Mon Compilateur — Projet de Compilation (Java)

Mini-compilateur développé en Java avec une interface graphique Swing, illustrant les différentes phases de la compilation d'un petit langage impératif : analyse lexicale, analyse syntaxique, analyse sémantique et génération de code.

Fonctionnalités

L'interface graphique permet de saisir un programme et d'exécuter, étape par étape, les phases du compilateur :


Lexical : découpe le code source en tokens (mots-clés, identifiants, nombres, opérateurs...)
Syntaxique : construit l'arbre syntaxique abstrait (AST) à partir des tokens
Sémantique : évalue/interprète l'AST (gestion d'une table de symboles)
Générer : produit un code intermédiaire (pile d'instructions de type EMPILER, CHARGER, ADD, STOCKER, etc.)


Langage supporté

Le compilateur reconnaît un petit langage impératif avec :


Affectations (a = 3)
Opérateurs arithmétiques : + - * /
Opérateurs de comparaison : == != < > <= >=
Opérateurs logiques : and, or, not
Structures de contrôle : if ... then ... else, while ... do ... done


Exemple de programme :

IF a > 0 THEN
  b = 3
  c = a - b
else
  b = 0

Structure du projet

FichierDescriptionMain_GUI.javaPoint d'entrée du programme, interface graphique SwingAnalyse_lexical.javaAnalyseur lexical (tokenizer)Analyse_syntaxique.javaAnalyseur syntaxique, construction de l'ASTAnalyse_semantique.javaAnalyse/évaluation sémantique avec table de symbolesGeneration_code.javaGénération du code intermédiaire à partir de l'ASTASTNode.javaStructure de nœud de l'arbre syntaxique abstraitMonProgramme.jarExécutable compilé du projet

Prérequis


JDK (Java Development Kit) version 8 ou supérieure


Compilation et exécution

Depuis les sources

bashcd src/Compilation
javac *.java
java Compilation.Main_GUI

Depuis le .jar fourni

bashjava -jar MonProgramme.jar

Utilisation


Lancer l'application : une fenêtre "Mon Compilateur" s'ouvre.
Saisir ou modifier le code dans la zone de texte.
Cliquer sur les boutons Lexical, Syntaxique, Sémantique ou Générer pour visualiser le résultat de chaque phase dans la zone de résultat.


Technologies utilisées


Java — langage de développement
Swing — interface graphique (JFrame, JTextArea, JButton...)


Auteur

Projet réalisé par yahyasafadi.

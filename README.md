# Mon Compilateur — Projet de Compilation (Java)

Mini-compilateur développé en **Java** avec une interface graphique **Swing**, illustrant les différentes phases de la compilation d'un petit langage impératif : analyse lexicale, analyse syntaxique, analyse sémantique et génération de code.

## Fonctionnalités

L'interface graphique permet de saisir un programme et d'exécuter, étape par étape, les phases du compilateur :

- **Lexical** : découpe le code source en tokens (mots-clés, identifiants, nombres, opérateurs...)
- **Syntaxique** : construit l'arbre syntaxique abstrait (AST) à partir des tokens
- **Sémantique** : évalue/interprète l'AST (gestion d'une table de symboles)
- **Générer** : produit un code intermédiaire (pile d'instructions de type `EMPILER`, `CHARGER`, `ADD`, `STOCKER`, etc.)

## Langage supporté

Le compilateur reconnaît un petit langage impératif avec :

- Affectations (`a = 3`)
- Opérateurs arithmétiques : `+ - * /`
- Opérateurs de comparaison : `== != < > <= >=`
- Opérateurs logiques : `and`, `or`, `not`
- Structures de contrôle : `if ... then ... else`, `while ... do ... done`

Exemple de programme :
```
IF a > 0 THEN
  b = 3
  c = a - b
else
  b = 0
```

## Structure du projet

| Fichier | Description |
|---|---|
| `Main_GUI.java` | Point d'entrée du programme, interface graphique Swing |
| `Analyse_lexical.java` | Analyseur lexical (tokenizer) |
| `Analyse_syntaxique.java` | Analyseur syntaxique, construction de l'AST |
| `Analyse_semantique.java` | Analyse/évaluation sémantique avec table de symboles |
| `Generation_code.java` | Génération du code intermédiaire à partir de l'AST |
| `ASTNode.java` | Structure de nœud de l'arbre syntaxique abstrait |
| `MonProgramme.jar` | Exécutable compilé du projet |

## Prérequis

- **JDK** (Java Development Kit) version 8 ou supérieure

## Compilation et exécution

### Depuis les sources
```bash
cd src/Compilation
javac *.java
java Compilation.Main_GUI
```

### Depuis le `.jar` fourni
```bash
java -jar MonProgramme.jar
```

## Utilisation

1. Lancer l'application : une fenêtre **"Mon Compilateur"** s'ouvre.
2. Saisir ou modifier le code dans la zone de texte.
3. Cliquer sur les boutons **Lexical**, **Syntaxique**, **Sémantique** ou **Générer** pour visualiser le résultat de chaque phase dans la zone de résultat.

## Technologies utilisées

- **Java** — langage de développement
- **Swing** — interface graphique (`JFrame`, `JTextArea`, `JButton`...)

## Auteur

Projet réalisé par **yahyasafadi**.

package Compilation;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class Main_GUI {

    public static void main(String[] args) {

        JFrame fenetre = new JFrame("Mon Compilateur");
        fenetre.setSize(600, 700);
        fenetre.setLayout(null);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel labelCode = new JLabel("Écrivez votre code ici ");
        labelCode.setBounds(20, 10, 400, 20);
        fenetre.add(labelCode);

        // Exemple mis à jour : "IF" en majuscule, pas de "fin" à la fin du bloc
        JTextArea zoneCode = new JTextArea("IF a > 0 THEN\n  b = 3\n  c = a - b\nelse\n  b = 0");
        JScrollPane scrollCode = new JScrollPane(zoneCode);
        scrollCode.setBounds(20, 30, 540, 200);
        fenetre.add(scrollCode);

        JButton btnLexical = new JButton("Lexical");
        btnLexical.setBounds(20, 240, 120, 30);
        fenetre.add(btnLexical);

        JButton btnSyntaxique = new JButton("Syntaxique");
        btnSyntaxique.setBounds(150, 240, 120, 30);
        fenetre.add(btnSyntaxique);

        JButton btnSemantique = new JButton("Sémantique");
        btnSemantique.setBounds(280, 240, 120, 30);
        fenetre.add(btnSemantique);

        JButton btnGenerer = new JButton("Générer");
        btnGenerer.setBounds(410, 240, 120, 30);
        fenetre.add(btnGenerer);

        JLabel labelResultat = new JLabel("Résultat :");
        labelResultat.setBounds(20, 280, 200, 20);
        fenetre.add(labelResultat);

        JTextArea zoneResultat = new JTextArea();
        zoneResultat.setEditable(false);
        JScrollPane scrollResultat = new JScrollPane(zoneResultat);
        scrollResultat.setBounds(20, 300, 540, 300);
        fenetre.add(scrollResultat);

        // --- Logique des Boutons ---

        btnLexical.addActionListener(e -> {
            try {
                Analyse_lexical.Lexer lexer = new Analyse_lexical.Lexer(zoneCode.getText());
                List<Analyse_lexical.Token> tokens = lexer.tokenize();
                zoneResultat.setText("Tokens trouvés (Normalisés) :\n" + tokens.toString());
            } catch (Exception ex) {
                zoneResultat.setText("Erreur Lexicale : " + ex.getMessage());
            }
        });

        btnSyntaxique.addActionListener(e -> {
            try {
                Analyse_lexical.Lexer lexer = new Analyse_lexical.Lexer(zoneCode.getText());
                Analyse_syntaxique syntaxique = new Analyse_syntaxique(lexer.tokenize());
                ASTNode arbre = syntaxique.analyser();
                zoneResultat.setText("Arbre Syntaxique :\n" + arbre.toString());
            } catch (Exception ex) {
                zoneResultat.setText("Erreur Syntaxique : " + ex.getMessage());
            }
        });

        btnSemantique.addActionListener(e -> {
            try {
                Analyse_lexical.Lexer lexer = new Analyse_lexical.Lexer(zoneCode.getText());
                Analyse_syntaxique syntaxique = new Analyse_syntaxique(lexer.tokenize());
                ASTNode arbre = syntaxique.analyser();
                Analyse_semantique sem = new Analyse_semantique();
                sem.analyser(arbre);
                zoneResultat.setText("Table des symboles :\n" + sem.tableSymboles.toString());
            } catch (Exception ex) {
                zoneResultat.setText("Erreur Sémantique : " + ex.getMessage());
            }
        });

        btnGenerer.addActionListener(e -> {
            try {
                Analyse_lexical.Lexer lexer = new Analyse_lexical.Lexer(zoneCode.getText());
                Analyse_syntaxique syntaxique = new Analyse_syntaxique(lexer.tokenize());
                ASTNode arbre = syntaxique.analyser();
                Generation_code gen = new Generation_code();
                gen.generer(arbre);
                zoneResultat.setText("Code Assembleur généré :\n");
                for(String ligne : gen.instructions) {
                    zoneResultat.append(ligne + "\n");
                }
            } catch (Exception ex) {
                zoneResultat.setText("Erreur de Génération : " + ex.getMessage());
            }
        });

        fenetre.setVisible(true);
    }
}
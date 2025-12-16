package Compilation;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class Main_GUI {

    public static void main(String[] args) {

        // 1. Créer la fenêtre
        JFrame fenetre = new JFrame("Mon Compilateur");
        fenetre.setSize(600, 700); // Largeur: 600, Hauteur: 700
        fenetre.setLayout(null);   // IMPORTANT : On désactive le placement automatique
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 2. Créer la zone de texte pour écrire le code (En haut)
        JLabel labelCode = new JLabel("Écrivez votre code ici :");
        labelCode.setBounds(20, 10, 200, 20); // x, y, largeur, hauteur
        fenetre.add(labelCode);

        JTextArea zoneCode = new JTextArea("if a>0 :\nb = 3\nif a > b then\n  c = a - b\nfin");
        JScrollPane scrollCode = new JScrollPane(zoneCode);
        scrollCode.setBounds(20, 30, 540, 200); // On place la boite
        fenetre.add(scrollCode);

        // 3. Créer les 4 boutons (Au milieu)
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

        // 4. Créer la zone de résultat (En bas)
        JLabel labelResultat = new JLabel("Résultat :");
        labelResultat.setBounds(20, 280, 200, 20);
        fenetre.add(labelResultat);

        JTextArea zoneResultat = new JTextArea();
        JScrollPane scrollResultat = new JScrollPane(zoneResultat);
        scrollResultat.setBounds(20, 300, 540, 300);
        fenetre.add(scrollResultat);

        // ==========================================
        // 5. DONNER VIE AUX BOUTONS (Les Actions)
        // ==========================================

        // --- Bouton Lexical ---
        btnLexical.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String texte = zoneCode.getText();
                    Analyse_lexical.Lexer lexer = new Analyse_lexical.Lexer(texte);
                    List<Analyse_lexical.Token> tokens = lexer.tokenize();

                    zoneResultat.setText("Tokens trouvés :\n" + tokens.toString());
                } catch (Exception ex) {
                    zoneResultat.setText("Erreur : " + ex.getMessage());
                }
            }
        });

        // --- Bouton Syntaxique ---
        btnSyntaxique.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String texte = zoneCode.getText();
                    Analyse_lexical.Lexer lexer = new Analyse_lexical.Lexer(texte);
                    List<Analyse_lexical.Token> tokens = lexer.tokenize();
                    Analyse_syntaxique syntaxique = new Analyse_syntaxique(tokens);
                    ASTNode arbre = syntaxique.analyser();

                    zoneResultat.setText("Arbre Syntaxique :\n" + arbre.toString());
                } catch (Exception ex) {
                    zoneResultat.setText("Erreur : " + ex.getMessage());
                }
            }
        });

        // --- Bouton Sémantique ---
        btnSemantique.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // On refait lexical + syntaxique d'abord
                    String texte = zoneCode.getText();
                    Analyse_lexical.Lexer lexer = new Analyse_lexical.Lexer(texte);
                    List<Analyse_lexical.Token> tokens = lexer.tokenize();
                    Analyse_syntaxique syntaxique = new Analyse_syntaxique(tokens);
                    ASTNode arbre = syntaxique.analyser();

                    // Puis sémantique
                    Analyse_semantique sem = new Analyse_semantique();
                    sem.analyser(arbre);

                    zoneResultat.setText("Table des symboles :\n" + sem.tableSymboles.toString());
                } catch (Exception ex) {
                    zoneResultat.setText("Erreur : " + ex.getMessage());
                }
            }
        });

        // --- Bouton Génération ---
        btnGenerer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String texte = zoneCode.getText();
                    Analyse_lexical.Lexer lexer = new Analyse_lexical.Lexer(texte);
                    List<Analyse_lexical.Token> tokens = lexer.tokenize();
                    Analyse_syntaxique syntaxique = new Analyse_syntaxique(tokens);
                    ASTNode arbre = syntaxique.analyser();

                    Generation_code gen = new Generation_code();
                    gen.generer(arbre);

                    zoneResultat.setText("Code Assembler généré :\n");
                    for(String ligne : gen.instructions) {
                        zoneResultat.append(ligne + "\n");
                    }
                } catch (Exception ex) {
                    zoneResultat.setText("Erreur : " + ex.getMessage());
                }
            }
        });

        // 6. Afficher la fenêtre à la fin
        fenetre.setVisible(true);
    }
}
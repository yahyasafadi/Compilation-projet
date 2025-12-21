package Compilation;
import java.util.List;
import java.util.ArrayList;
import Compilation.Analyse_lexical.Token;
import Compilation.Analyse_lexical.TokenType;

public class Analyse_syntaxique {
    private List<Token> tokens;
    private Token currentToken;
    private int position = -1;

    public Analyse_syntaxique(List<Token> tokens) {
        this.tokens = tokens;
        advance();
    }

    private void advance() {
        position++;
        currentToken = (position < tokens.size()) ? tokens.get(position) : null;
    }

    public ASTNode analyser() throws Exception {
        ASTNode programme = new ASTNode("PROGRAMME");
        while (currentToken != null && currentToken.type != TokenType.EOF) {
            programme.addChild(instruction());
        }
        return programme;
    }

    private ASTNode instruction() throws Exception {
        if (currentToken.type == TokenType.ID) return affectation();
        if (currentToken.type == TokenType.IF) return conditionnelle();
        if (currentToken.type == TokenType.WHILE) return boucle();
        throw new Exception("Instruction inattendue : " + currentToken);
    }

    private ASTNode affectation() throws Exception {
        String varName = currentToken.value;
        advance();
        if (currentToken.type != TokenType.ASSIGN) throw new Exception("= attendu");
        advance();
        ASTNode expr = expression();
        ASTNode node = new ASTNode("AFFECTATION", varName);
        node.addChild(expr);
        return node;
    }

    private ASTNode conditionnelle() throws Exception {
        advance(); // if
        ASTNode condition = expression();
        if (currentToken.type != TokenType.THEN) throw new Exception("then attendu");
        advance(); // then

        ASTNode blocIf = new ASTNode("BLOC");
        blocIf.children = bloc();

        ASTNode blocElse = null;
        if (currentToken.type == TokenType.ELSE) {
            advance(); // else
            blocElse = new ASTNode("BLOC");
            blocElse.children = bloc();
        }


        ASTNode node = new ASTNode("IF");
        node.addChild(condition);
        node.addChild(blocIf);
        if (blocElse != null) node.addChild(blocElse);
        return node;
    }

    private ASTNode boucle() throws Exception {
        advance(); // while
        ASTNode condition = expression();
        if (currentToken.type != TokenType.DO) throw new Exception("do attendu");
        advance(); // do

        ASTNode blocWhile = new ASTNode("BLOC");
        blocWhile.children = bloc();

        if (currentToken.type == TokenType.DONE) {
            advance(); // On consomme 'done' s'il existe pour fermer la boucle proprement
        }

        ASTNode node = new ASTNode("WHILE");
        node.addChild(condition);
        node.addChild(blocWhile);
        return node;
    }

    private List<ASTNode> bloc() throws Exception {
        List<ASTNode> instrs = new ArrayList<>();
        // Le bloc s'arrête s'il rencontre des mots réservés de structures de contrôle
        while (currentToken != null &&
                currentToken.type != TokenType.ELSE &&
                currentToken.type != TokenType.DONE &&
                currentToken.type != TokenType.EOF) {

            // Sécurité : si c'est un IF ou WHILE, on traite l'instruction normalement
            // mais on ne veut pas boucler indéfiniment
            instrs.add(instruction());

            // Dans une version sans 'fin', on s'arrête souvent après une seule instruction
            // ou on définit une règle de fin de ligne. Ici, on lit jusqu'au prochain mot clé.
            if (currentToken.type == TokenType.IF || currentToken.type == TokenType.WHILE) break;
        }
        return instrs;
    }

    // Les méthodes expression(), terme() et facteur() restent identiques...
    private ASTNode expression() throws Exception {
        ASTNode node = terme();
        while (currentToken.type == TokenType.PLUS || currentToken.type == TokenType.MINUS ||
                currentToken.type == TokenType.AND || currentToken.type == TokenType.OR ||
                currentToken.type == TokenType.EQ || currentToken.type == TokenType.LT ||
                currentToken.type == TokenType.GT || currentToken.type == TokenType.LTE ||
                currentToken.type == TokenType.GTE || currentToken.type == TokenType.NEQ) {
            String op = currentToken.value;
            advance();
            ASTNode right = terme();
            ASTNode opNode = new ASTNode("OP_BIN", op);
            opNode.addChild(node);
            opNode.addChild(right);
            node = opNode;
        }
        return node;
    }

    private ASTNode terme() throws Exception {
        ASTNode node = facteur();
        while (currentToken.type == TokenType.MUL || currentToken.type == TokenType.DIV) {
            String op = currentToken.value;
            advance();
            ASTNode right = facteur();
            ASTNode opNode = new ASTNode("OP_BIN", op);
            opNode.addChild(node);
            opNode.addChild(right);
            node = opNode;
        }
        return node;
    }

    private ASTNode facteur() throws Exception {
        if (currentToken.type == TokenType.NUMBER) {
            ASTNode node = new ASTNode("NOMBRE", currentToken.value);
            advance();
            return node;
        } else if (currentToken.type == TokenType.ID) {
            ASTNode node = new ASTNode("VARIABLE", currentToken.value);
            advance();
            return node;
        } else if (currentToken.type == TokenType.LPAREN) {
            advance();
            ASTNode node = expression();
            if (currentToken.type != TokenType.RPAREN) throw new Exception(") attendue");
            advance();
            return node;
        } else if (currentToken.type == TokenType.NOT) {
            advance();
            ASTNode node = new ASTNode("NOT");
            node.addChild(facteur());
            return node;
        }
        throw new Exception("Facteur inattendu : " + currentToken);
    }
}
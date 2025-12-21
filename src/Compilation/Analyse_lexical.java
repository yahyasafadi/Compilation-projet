package Compilation;
import java.util.*;

public class Analyse_lexical {
    public enum TokenType {
        NUMBER, ID, PLUS, MINUS, MUL, DIV, ASSIGN,
        LPAREN, RPAREN, EQ, LT, GT, LTE, GTE, NEQ,
        IF, THEN, ELSE, WHILE, DO, DONE, AND, OR, NOT, EOF

    }

    public static class Token {
        public final TokenType type;
        public final String value;
        public Token(TokenType type, String value) { this.type = type; this.value = value; }
        @Override
        public String toString() { return "(" + type + ", \"" + value + "\")"; }
    }

    public static class Lexer {
        private final String input;
        private int pos = 0;
        private char currentChar;
        private static final Map<String, TokenType> keywords;

        static {
            keywords = new HashMap<>();
            // Tous les mots-clés sont enregistrés en minuscules
            keywords.put("if", TokenType.IF);
            keywords.put("then", TokenType.THEN);
            keywords.put("else", TokenType.ELSE);
            keywords.put("while", TokenType.WHILE);
            keywords.put("do", TokenType.DO);
            keywords.put("done", TokenType.DONE);
            keywords.put("and", TokenType.AND);
            keywords.put("or", TokenType.OR);
            keywords.put("not", TokenType.NOT);
        }

        public Lexer(String input) {
            this.input = input;
            this.currentChar = input.length() > 0 ? input.charAt(0) : '\0';
        }

        private void advance() {
            pos++;
            currentChar = (pos < input.length()) ? input.charAt(pos) : '\0';
        }

        private char peek() { return (pos + 1 < input.length()) ? input.charAt(pos + 1) : '\0'; }

        public List<Token> tokenize() {
            List<Token> tokens = new ArrayList<>();
            while (currentChar != '\0') {
                if (Character.isWhitespace(currentChar)) { advance(); continue; }
                if (Character.isLetter(currentChar)) { tokens.add(identifier()); continue; }
                if (Character.isDigit(currentChar)) { tokens.add(number()); continue; }

                switch (currentChar) {
                    case '+': tokens.add(new Token(TokenType.PLUS, "+")); break;
                    case '-': tokens.add(new Token(TokenType.MINUS, "-")); break;
                    case '*': tokens.add(new Token(TokenType.MUL, "*")); break;
                    case '/': tokens.add(new Token(TokenType.DIV, "/")); break;
                    case '(': tokens.add(new Token(TokenType.LPAREN, "(")); break;
                    case ')': tokens.add(new Token(TokenType.RPAREN, ")")); break;
                    case '!': if(peek() == '=') { advance(); tokens.add(new Token(TokenType.NEQ, "!=")); } break;
                    case '=':
                        if (peek() == '=') { advance(); tokens.add(new Token(TokenType.EQ, "==")); }
                        else tokens.add(new Token(TokenType.ASSIGN, "="));
                        break;
                    case '<':
                        if (peek() == '=') { advance(); tokens.add(new Token(TokenType.LTE, "<=")); }
                        else tokens.add(new Token(TokenType.LT, "<"));
                        break;
                    case '>':
                        if (peek() == '=') { advance(); tokens.add(new Token(TokenType.GTE, ">=")); }
                        else tokens.add(new Token(TokenType.GT, ">"));
                        break;
                }
                advance();
            }
            tokens.add(new Token(TokenType.EOF, ""));
            return tokens;
        }

        private Token number() {
            StringBuilder sb = new StringBuilder();
            while (Character.isDigit(currentChar)) { sb.append(currentChar); advance(); }
            return new Token(TokenType.NUMBER, sb.toString());
        }

        private Token identifier() {
            StringBuilder sb = new StringBuilder();
            while (Character.isLetterOrDigit(currentChar) || currentChar == '_') {
                sb.append(currentChar); advance();
            }
            String val = sb.toString();
            // On vérifie la correspondance en ignorant la casse
            TokenType type = keywords.get(val.toLowerCase());
            return new Token(type != null ? type : TokenType.ID, val);
        }
    }
}
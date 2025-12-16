package Compilation;
import java.util.HashMap;
import java.util.Map;

public class Analyse_semantique {
    public Map<String, Double> tableSymboles = new HashMap<>();

    public void analyser(ASTNode node) throws Exception {
        if (node.type.equals("PROGRAMME") || node.type.equals("BLOC")) {
            for (ASTNode child : node.children) {
                analyser(child);
            }
        } else if (node.type.equals("AFFECTATION")) {
            double val = evaluer(node.children.get(0));
            tableSymboles.put(node.value, val);
        } else if (node.type.equals("IF")) {
            double cond = evaluer(node.children.get(0));
            if (cond != 0) {
                analyser(node.children.get(1));
            } else if (node.children.size() > 2) {
                analyser(node.children.get(2));
            }
        } else if (node.type.equals("WHILE")) {
            while (evaluer(node.children.get(0)) != 0) {
                analyser(node.children.get(1));
            }
        }
    }

    private double evaluer(ASTNode node) throws Exception {
        if (node.type.equals("NOMBRE")) return Double.parseDouble(node.value);
        if (node.type.equals("VARIABLE")) {
            if (!tableSymboles.containsKey(node.value)) throw new Exception("Variable non définie: " + node.value);
            return tableSymboles.get(node.value);
        }
        if (node.type.equals("NOT")) return evaluer(node.children.get(0)) == 0 ? 1.0 : 0.0;

        if (node.type.equals("OP_BIN")) {
            double left = evaluer(node.children.get(0));
            double right = evaluer(node.children.get(1));
            switch (node.value) {
                case "+": return left + right;
                case "-": return left - right;
                case "*": return left * right;
                case "/": if (right == 0) throw new Exception("Division par zéro"); return left / right;
                case "<": return left < right ? 1.0 : 0.0;
                case ">": return left > right ? 1.0 : 0.0;
                case "<=": return left <= right ? 1.0 : 0.0;
                case ">=": return left >= right ? 1.0 : 0.0;
                case "==": return left == right ? 1.0 : 0.0;
                case "!=": return left != right ? 1.0 : 0.0;
                case "AND": return (left != 0 && right != 0) ? 1.0 : 0.0;
                case "OR": return (left != 0 || right != 0) ? 1.0 : 0.0;
            }
        }
        return 0;
    }
}
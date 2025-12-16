package Compilation;
import java.util.*;

public class Generation_code {
    public List<String> instructions = new ArrayList<>();
    private int labelCount = 0;

    private String newLabel() { return "L" + (++labelCount); }

    public void generer(ASTNode node) {
        if (node.type.equals("PROGRAMME") || node.type.equals("BLOC")) {
            for (ASTNode child : node.children) generer(child);
        } else if (node.type.equals("AFFECTATION")) {
            generer(node.children.get(0));
            instructions.add("STOCKER " + node.value);
        } else if (node.type.equals("NOMBRE")) {
            instructions.add("EMPILER " + node.value);
        } else if (node.type.equals("VARIABLE")) {
            instructions.add("CHARGER " + node.value);
        } else if (node.type.equals("NOT")) {
            generer(node.children.get(0));
            instructions.add("NOT");
        } else if (node.type.equals("OP_BIN")) {
            generer(node.children.get(0));
            generer(node.children.get(1));
            switch(node.value) {
                case "+": instructions.add("ADD"); break;
                case "-": instructions.add("SUB"); break;
                case "*": instructions.add("MUL"); break;
                case "/": instructions.add("DIV"); break;
                case "<": instructions.add("LT"); break;
                case ">": instructions.add("GT"); break;
                case "<=": instructions.add("LTE"); break;
                case ">=": instructions.add("GTE"); break;
                case "==": instructions.add("EQ"); break;
                case "!=": instructions.add("NEQ"); break;
                case "AND": instructions.add("AND"); break;
                case "OR": instructions.add("OR"); break;
            }
        } else if (node.type.equals("IF")) {
            String elseLabel = newLabel();
            String endLabel = newLabel();
            generer(node.children.get(0)); // Cond
            instructions.add("JZ " + elseLabel);
            generer(node.children.get(1)); // Bloc IF
            instructions.add("JUMP " + endLabel);
            instructions.add("LABEL " + elseLabel);
            if (node.children.size() > 2) generer(node.children.get(2)); // Bloc ELSE
            instructions.add("LABEL " + endLabel);
        } else if (node.type.equals("WHILE")) {
            String startLabel = newLabel();
            String endLabel = newLabel();
            instructions.add("LABEL " + startLabel);
            generer(node.children.get(0)); // Cond
            instructions.add("JZ " + endLabel);
            generer(node.children.get(1)); // Bloc
            instructions.add("JUMP " + startLabel);
            instructions.add("LABEL " + endLabel);
        }
    }
}
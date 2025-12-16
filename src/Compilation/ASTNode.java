package Compilation;
import java.util.*;

public class ASTNode {
    public String type;
    public String value;
    public List<ASTNode> children;

    public ASTNode(String type, String value) {
        this.type = type;
        this.value = value;
        this.children = new ArrayList<>();
    }

    public ASTNode(String type) {
        this(type, null);
    }

    public void addChild(ASTNode child) {
        if (child != null) this.children.add(child);
    }

    @Override
    public String toString() {
        if (value != null) return "(" + type + ": " + value + ")";
        return "(" + type + " " + children + ")";
    }
}
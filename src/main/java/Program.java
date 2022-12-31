import com.dev21.program.Evaluator;
import com.dev21.program.SyntaxNode;
import com.dev21.program.SyntaxTree;
import com.dev21.program.Token;

import java.util.Iterator;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        boolean showTree = false;

        while (true) {
            var scanner = new Scanner(System.in);
            var input = scanner.nextLine();
            if (input.isBlank() || input.isEmpty()) {
                return;
            }

            if (input.equalsIgnoreCase("#showTree")) {

                showTree = !showTree;
                System.out.println(showTree ? "Showing parse trees" : "Not showing parse trees");
                continue;
            }

            var syntaxTree = SyntaxTree.parse(input);
            if (showTree) {
                prettyPrint(syntaxTree.getRoot(), "", true);
            }

            if (syntaxTree.getDiagnostics().isEmpty()) {
                var eval = new Evaluator(syntaxTree.getRoot());
                var result = eval.evaluate();
                System.out.println(result);
            } else {
                syntaxTree.getDiagnostics().forEach(System.out::println);
            }
        }
    }

    static void prettyPrint(SyntaxNode node, String indent, boolean isLast) {
        var marker = isLast ? "└──" : "├──";

        System.out.print(indent);
        System.out.print(marker);
        System.out.print(node.kind());

        if (node instanceof Token && ((Token) node).getValue() != null) {
            Object val = ((Token) node).getValue();
            System.out.printf("%2d", val);
        }

        System.out.println();

        indent += isLast ? "    " : "│   ";

        SyntaxNode lastChild = null;
        Iterator<SyntaxNode> iterator = node.getChildren().iterator();
        while (iterator.hasNext()) {
            lastChild = iterator.next();
        }

        for (var child : node.getChildren()) {
            prettyPrint(child, indent, child == lastChild);
        }
    }
}

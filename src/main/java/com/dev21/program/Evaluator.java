package com.dev21.program;

public class Evaluator {

    private ExpressionSyntax root;

    public Evaluator(ExpressionSyntax root) {
        this.root = root;
    }

    public int evaluate() {
        return evaluateExpression(root);
    }

    private int evaluateExpression(ExpressionSyntax node) {

        if (node instanceof LiteralExpressionSyntax) {
            return (int) ((LiteralExpressionSyntax) node).getLiteralToken().getValue();
        }
        if (node instanceof BinaryExpressionSyntax) {
            var binaryExpression = (BinaryExpressionSyntax) node;
            var left = evaluateExpression(binaryExpression.getLeft());
            var right = evaluateExpression(binaryExpression.getRight());

            SyntaxKind kind = binaryExpression.getOperator().kind();
            if (kind == SyntaxKind.ADDITION_TOKEN)
                return left + right;
            else if (kind == SyntaxKind.SUBTRACTION_TOKEN)
                return left - right;
            else if (kind == SyntaxKind.MULTIPLICATION_TOKEN)
                return left * right;
            else if (kind == SyntaxKind.DIVISION_TOKEN)
                return left / right;
            else
                throw new RuntimeException("Unexpected binary operator " + kind);

        }
        if (node instanceof ParenthesizedExpressionSyntax) {
            var parenthesizedExpression = (ParenthesizedExpressionSyntax) node;
            return evaluateExpression(parenthesizedExpression.getExpression());
        }

        throw new RuntimeException("Unexpected node " + node.kind());
    }
}

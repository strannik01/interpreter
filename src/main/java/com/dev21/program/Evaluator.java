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

        if (node instanceof LiteralExpressionSyntax l) {
            return (int) l.getLiteralToken().getValue();
        }

        if (node instanceof UnaryExpressionSyntax u) {
            var operand = evaluateExpression(u.getOperand());

            if (u.getOperator().getKind() == SyntaxKind.ADDITION_TOKEN) {
                return operand;
            } else if (u.getOperator().getKind() == SyntaxKind.SUBTRACTION_TOKEN) {
                return -operand;
            } else {
                throw new RuntimeException("Unexpected unary operator " + u.getOperator().getKind());
            }
        }

        if (node instanceof BinaryExpressionSyntax be) {
            var left = evaluateExpression(be.getLeft());
            var right = evaluateExpression(be.getRight());

            SyntaxKind kind = be.getOperator().getKind();
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
        if (node instanceof ParenthesizedExpressionSyntax pe) {
            return evaluateExpression(pe.getExpression());
        }

        throw new RuntimeException("Unexpected node " + node.getKind());
    }
}

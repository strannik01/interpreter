package com.dev21.program;

import java.util.Arrays;
import java.util.List;

public class BinaryExpressionSyntax implements ExpressionSyntax {

    private ExpressionSyntax left;
    private Token operator;
    private ExpressionSyntax right;

    public BinaryExpressionSyntax(ExpressionSyntax left, Token operator, ExpressionSyntax right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public SyntaxKind getKind() {
        return SyntaxKind.BINARY_EXPRESSION;
    }

    @Override
    public List<SyntaxNode> getChildren() {
        return Arrays.asList(left, operator, right);
    }

    public ExpressionSyntax getLeft() {
        return left;
    }

    public Token getOperator() {
        return operator;
    }

    public ExpressionSyntax getRight() {
        return right;
    }
}

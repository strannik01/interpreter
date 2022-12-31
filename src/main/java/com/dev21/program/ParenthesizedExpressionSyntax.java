package com.dev21.program;

import java.util.Arrays;
import java.util.List;

public class ParenthesizedExpressionSyntax implements ExpressionSyntax {

    private Token openParenthesisToken;
    private ExpressionSyntax expression;
    private Token closeParenthesisToken;

    public ParenthesizedExpressionSyntax(Token openParenthesisToken, ExpressionSyntax expression, Token closeParenthesisToken) {
        this.openParenthesisToken = openParenthesisToken;
        this.expression = expression;
        this.closeParenthesisToken = closeParenthesisToken;
    }

    public Token getOpenParenthesisToken() {
        return openParenthesisToken;
    }

    public ExpressionSyntax getExpression() {
        return expression;
    }

    public Token getCloseParenthesisToken() {
        return closeParenthesisToken;
    }

    @Override
    public SyntaxKind kind() {
        return SyntaxKind.PARENTHESIZED_EXPRESSION;
    }

    @Override
    public List<SyntaxNode> getChildren() {
        return Arrays.asList(openParenthesisToken, expression, closeParenthesisToken);
    }
}

package com.dev21.program;

import java.util.Arrays;
import java.util.List;

public class LiteralExpressionSyntax implements ExpressionSyntax {

    private final Token literalToken;

    public LiteralExpressionSyntax(Token literalToken) {
        this.literalToken = literalToken;
    }

    @Override
    public SyntaxKind kind() {
        return SyntaxKind.LITERAL_EXPRESSION;
    }

    @Override
    public List<SyntaxNode> getChildren() {
        return Arrays.asList(literalToken);
    }

    public Token getLiteralToken() {
        return literalToken;
    }
}

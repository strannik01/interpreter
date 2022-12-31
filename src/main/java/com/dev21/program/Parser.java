package com.dev21.program;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final Token[] tokens;
    private List<String> diagnostics = new ArrayList<>();
    private int position;

    public Parser(String text) { 
        var tokens = new ArrayList<Token>();

        var lexer = new Lexer(text);
        Token token;
        do {
            token = lexer.nextToken();
            if (token.kind() != SyntaxKind.BAD_TOKEN)
                tokens.add(token);
        } while (token.kind() != SyntaxKind.END_OF_FILE_TOKEN);

        this.tokens = tokens.toArray(Token[]::new);
        diagnostics.addAll(lexer.getDiagnostics());
    }

    public List<String> getDiagnostics() {
        return diagnostics;
    }

    private Token peek(int offset) {
        var index = position + offset;
        if (index >= tokens.length) {
            return tokens[tokens.length - 1];
        }
        return tokens[index];
    }

    private Token currentToken() {
        return peek(0);
    }

    private Token nextToken() {
        var currentToken = currentToken();
        position++;
        return currentToken;
    }

    public SyntaxTree parse() {
        var expression = parseExpression(0);
        var endOfFileToken = matchToken(SyntaxKind.END_OF_FILE_TOKEN);
        return new SyntaxTree(diagnostics, expression, endOfFileToken);
    }

    private ExpressionSyntax parseExpression(int parentPrecedence) {
        var left = parsePrimaryExpression();
        while (true) {
            var precedence = getBinaryOperatorPrecedence(currentToken().kind());
            if (precedence == 0 || precedence <= parentPrecedence) {
                break;
            }
            var operatorToken = nextToken();
            var right = parseExpression(precedence);
            left = new BinaryExpressionSyntax(left, operatorToken, right);
        }

        return left;
    }

    private static int getBinaryOperatorPrecedence(SyntaxKind kind) {
        switch (kind) {
            case MULTIPLICATION_TOKEN:
            case DIVISION_TOKEN:
                return 2;
            case ADDITION_TOKEN:
            case SUBTRACTION_TOKEN:
                return 1;
            default:
                return 0;
        }
    }

    private ExpressionSyntax parsePrimaryExpression() {
        if (currentToken().kind() == SyntaxKind.OPEN_PARENTHESIS_TOKEN) {
            var left = nextToken();
            var expression = parseExpression(0);
            var right = matchToken(SyntaxKind.CLOSE_PARENTHESIS_TOKEN);
            return new ParenthesizedExpressionSyntax(left, expression, right);
        }

        var numberToken = matchToken(SyntaxKind.NUMBER_TOKEN);
        return new LiteralExpressionSyntax(numberToken);
    }

    private Token matchToken(SyntaxKind kind) {
        if (currentToken().kind() == kind) {
            return nextToken();
        }
        diagnostics.add("ERROR: Unexpected token <" + currentToken().kind() + ">, expected <" + kind + ">");
        return new Token(kind, currentToken().getPosition(), null, null);
    }
}

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
            token = lexer.getToken();
            if (token.getKind() != SyntaxKind.BAD_TOKEN)
                tokens.add(token);
        } while (token.getKind() != SyntaxKind.END_OF_FILE_TOKEN);

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

    private Token advanceNextToken() {
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
        ExpressionSyntax left;
        var unaryOperatorPrecedence = SyntaxFacts.getUnaryOperatorPrecedence(currentToken().getKind());
        if (unaryOperatorPrecedence != 0 && unaryOperatorPrecedence >= parentPrecedence) {
            var operatorToken = advanceNextToken();
            var operand = parseExpression(unaryOperatorPrecedence);
            left = new UnaryExpressionSyntax(operatorToken, operand);
        } else {
            left = parsePrimaryExpression();
        }
        while (true) {
            var precedence = SyntaxFacts.getBinaryOperatorPrecedence(currentToken().getKind());
            if (precedence == 0 || precedence <= parentPrecedence) {
                break;
            }
            var operatorToken = advanceNextToken();
            var right = parseExpression(precedence);
            left = new BinaryExpressionSyntax(left, operatorToken, right);
        }
        return left;
    }

    private ExpressionSyntax parsePrimaryExpression() {
        if (currentToken().getKind() == SyntaxKind.OPEN_PARENTHESIS_TOKEN) {
            var left = advanceNextToken();
            var expression = parseExpression(0);
            var right = matchToken(SyntaxKind.CLOSE_PARENTHESIS_TOKEN);
            return new ParenthesizedExpressionSyntax(left, expression, right);
        }

        var numberToken = matchToken(SyntaxKind.NUMBER_TOKEN);
        return new LiteralExpressionSyntax(numberToken);
    }

    private Token matchToken(SyntaxKind kind) {
        if (currentToken().getKind() == kind) {
            return advanceNextToken();
        }
        diagnostics.add("ERROR: Unexpected token <" + currentToken().getKind() + ">, expected <" + kind + ">");
        return new Token(kind, currentToken().getPosition(), null, null);
    }
}

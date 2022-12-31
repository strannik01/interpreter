package com.dev21.program;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int position;
    private List<String> diagnostics = new ArrayList<>();

    public Lexer(String input) {
        this.input = input;
    }

    public List<String> getDiagnostics() {
        return diagnostics;
    }

    char currentChar() {
        if (position >= input.length())
            return '\u0000';
        else
            return input.charAt(position);
    }

    void advance() {
        position++;
    }

    public Token nextToken() {

        if (position >= input.length())
            return new Token(SyntaxKind.END_OF_FILE_TOKEN, position, "\u0000", null);

        while (Character.isWhitespace(currentChar()))
            advance();

        if (Character.isDigit(currentChar())) {
            return getNumericToken(position);
        } else if (currentChar() == '+') {
            advance();
            return new Token(SyntaxKind.ADDITION_TOKEN, position, "+", null);
        } else if (currentChar() == '-') {
            advance();
            return new Token(SyntaxKind.SUBTRACTION_TOKEN, position, "-", null);
        } else if (currentChar() == '*') {
            advance();
            return new Token(SyntaxKind.MULTIPLICATION_TOKEN, position, "*", null);
        } else if (currentChar() == '/') {
            advance();
            return new Token(SyntaxKind.DIVISION_TOKEN, position, "/", null);
        } else if (currentChar() == '(') {
            advance();
            return new Token(SyntaxKind.OPEN_PARENTHESIS_TOKEN, position, "(", null);
        } else if (currentChar() == ')') {
            advance();
            return new Token(SyntaxKind.CLOSE_PARENTHESIS_TOKEN, position, ")", null);
        } else {
            diagnostics.add("ERROR: bad character input: " + currentChar());
            var text = String.valueOf(currentChar());
            advance();
            return new Token(SyntaxKind.BAD_TOKEN, position, text, null);
        }
    }

    private Token getNumericToken(int position) {
        var numeral = new StringBuilder();
        while (Character.isDigit(currentChar())) {
            numeral.append(currentChar());
            advance();
        }
        var text = numeral.toString();

        int value = 0;
        try {
            value = Integer.valueOf(text);
        } catch (NumberFormatException e) {
            diagnostics.add("The number " + text + " isn't valid int");
        }
        return new Token(SyntaxKind.NUMBER_TOKEN, position, text, value);
    }
}

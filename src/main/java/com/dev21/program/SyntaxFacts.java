package com.dev21.program;

public class SyntaxFacts {

    public static int getUnaryOperatorPrecedence(SyntaxKind kind) {
        switch (kind) {
            case ADDITION_TOKEN:
            case SUBTRACTION_TOKEN:
                return 3;
            default:
                return 0;
        }
    }

    public static int getBinaryOperatorPrecedence(SyntaxKind kind) {
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
}

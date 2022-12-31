package com.dev21.program;

public class Program {

    enum SyntaxKind {

    }

    class Token {
        private SyntaxKind kind;
        private int position;
        private String text;

        public Token(SyntaxKind kind, int position, String text) {
            this.kind = kind;
            this.position = position;
            this.text = text;
        }
    }

    class Lexer {
        private String input;

        public Lexer(String input) {
            this.input = input;
        }

        public Token nextToken() {
            return null;
        }
    }

    public static void main(String[] args) {

        String input = "2 + 2";


    }
}

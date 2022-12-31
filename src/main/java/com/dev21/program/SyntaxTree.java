package com.dev21.program;

import java.util.List;

public class SyntaxTree {

    private List<String> diagnostics;
    private ExpressionSyntax root;
    private Token endOfFileToken;

    public SyntaxTree(List<String> diagnostics, ExpressionSyntax root, Token endOfFileToken) {
        this.diagnostics = diagnostics;
        this.root = root;
        this.endOfFileToken = endOfFileToken;
    }

    public List<String> getDiagnostics() {
        return diagnostics;
    }

    public ExpressionSyntax getRoot() {
        return root;
    }

    public Token getEndOfFileToken() {
        return endOfFileToken;
    }

    public static SyntaxTree parse(String text) {
        var parser = new Parser(text);
        return parser.parse();
    }
}

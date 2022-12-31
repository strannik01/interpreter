package com.dev21.program;

import java.util.Collections;
import java.util.List;

public class Token implements SyntaxNode {
    private SyntaxKind kind;
    private int position;
    private String text;
    private Object value;

    public Token(SyntaxKind kind, int position, String text, Object value) {
        this.kind = kind;
        this.position = position;
        this.text = text;
        this.value = value;
    }

    @Override
    public SyntaxKind getKind() {
        return kind;
    }

    @Override
    public List<SyntaxNode> getChildren() {
        return Collections.emptyList();
    }

    public int getPosition() {
        return position;
    }

    public String getText() {
        return text;
    }

    public Object getValue() {
        return value;
    }
}

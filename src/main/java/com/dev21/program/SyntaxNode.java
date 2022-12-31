package com.dev21.program;

import java.util.List;

public interface SyntaxNode {

    SyntaxKind kind();

    List<SyntaxNode> getChildren();
}

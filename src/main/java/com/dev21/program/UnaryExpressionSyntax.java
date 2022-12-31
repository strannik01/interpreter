package com.dev21.program;

import java.util.Arrays;
import java.util.List;

public class UnaryExpressionSyntax implements ExpressionSyntax {

  private final Token operator;
  private final ExpressionSyntax operand;

  public UnaryExpressionSyntax(Token operator, ExpressionSyntax operand) {
    this.operator = operator;
    this.operand = operand;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.UNARY_EXPRESSION;
  }

  @Override
  public List<SyntaxNode> getChildren() {
    return Arrays.asList(operator, operand);
  }

  public Token getOperator() {
    return operator;
  }

  public ExpressionSyntax getOperand() {
    return operand;
  }
}

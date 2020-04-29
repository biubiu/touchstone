package com.shawn.touchstone.interpreter.alert;

import java.util.Map;

public class AlertInterpreter {

    private Expressions.Expression expression;

    public AlertInterpreter(String ruleExpr) {
        this.expression = new Expressions.OrExpression(ruleExpr);
    }

    public boolean interpret(Map<String, Long> stats) {
        return expression.interpret(stats);
    }
}

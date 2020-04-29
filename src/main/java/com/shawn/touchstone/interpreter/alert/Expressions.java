package com.shawn.touchstone.interpreter.alert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Expressions {

    public static class AndExpression implements Expression {

        private List<Expression> expressions = new ArrayList<>();

        public AndExpression(String exprs) {
            String[] strExprs = exprs.split("&&");
            for (String exp : strExprs) {
                if (exp.contains(">")) expressions.add(new GreaterExpression(exp));
                else if (exp.contains("<")) expressions.add(new LessExpression(exp));
                else if (exp.contains("==")) expressions.add(new EqualExpression(exp));
                else throw new RuntimeException("AND Expression is invalid: " + exp);
            }
        }

        public AndExpression(List<Expression> expressions) {
            expressions.addAll(expressions);
        }

        @Override
        public boolean interpret(Map<String, Long> stats) {
            for (Expression expression : expressions) {
                if (!expression.interpret(stats)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static class OrExpression implements Expression {

        private List<Expression> expressions = new ArrayList<>();

        public OrExpression(String expr) {
            String[] strExprs = expr.split("\\|\\|");
            for (String strExpr : strExprs) {
                expressions.add(new AndExpression(strExpr));
            }
        }

        @Override
        public boolean interpret(Map<String, Long> stats) {
            for (Expression expr : expressions) {
                if (expr.interpret(stats)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class GreaterExpression implements Expression {
        private String key;
        private long val;

        public GreaterExpression(String expr) {
            String[] elements = expr.trim().split("\\s+");
            if (elements.length != 3 || !elements[1].trim().equals(">")) {
                throw new RuntimeException("Greater Expression is invalid " +expr);
            }
            this.key = elements[0].trim();
            this.val = Long.parseLong(elements[2].trim());
        }

        @Override
        public boolean interpret(Map<String, Long> stats) {
            if (!stats.containsKey(key)) {
                return false;
            }
            long statVal = stats.get(key);
            return statVal > val;
        }
    }

    public static class LessExpression implements Expression {
        private String key;
        private long val;

        public LessExpression(String expr) {
            String[] elements = expr.trim().split("\\s+");
            if (elements.length != 3 || !elements[1].trim().equals("<")) {
                throw new RuntimeException("Less Expression is invalid " +expr);
            }
            this.key = elements[0].trim();
            this.val = Long.parseLong(elements[2].trim());
        }

        @Override
        public boolean interpret(Map<String, Long> stats) {
            if (!stats.containsKey(key)) {
                return false;
            }
            long statVal = stats.get(key);
            return statVal < val;
        }
    }
    public static class EqualExpression implements Expression {
        private String key;
        private long val;

        public EqualExpression(String expr) {
            String[] elements = expr.trim().split("\\s+");
            if (elements.length != 3 || !elements[1].trim().equals("==")) {
                throw new RuntimeException("Equal Expression is invalid " +expr);
            }
            this.key = elements[0].trim();
            this.val = Long.parseLong(elements[2].trim());
        }

        @Override
        public boolean interpret(Map<String, Long> stats) {
            if (!stats.containsKey(key)) {
                return false;
            }
            long statVal = stats.get(key);
            return statVal == val;
        }
    }

    interface Expression {
        boolean interpret(Map<String, Long> stats);
    }
}

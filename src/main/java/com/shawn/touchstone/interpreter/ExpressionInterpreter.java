package com.shawn.touchstone.interpreter;

import java.util.Deque;
import java.util.LinkedList;

public class ExpressionInterpreter {

    private Deque<Long> numbers = new LinkedList<>();

    public Long interpret(String expression) {
        String[] elements = expression.split(" ");
        int len = elements.length;
        for (int i = 0; i < (len + 1) / 2; i++) {
            numbers.addLast(Long.parseLong(elements[i]));
        }

        for (int i = (len + 1) / 2; i < len; i++) {
            String operator = elements[i];
            boolean isValid = "+".equals(operator) || "-".equals(operator) || "*".equals(operator) || "/".equals(operator);
            if (!isValid) {
                throw new RuntimeException("operator is not valid");
            }


            long num1 = numbers.pollFirst();
            long num2 = numbers.pollFirst();
            long result = 0;
            if (operator.equals("+")) {
                result = num1 + num2;
            } else if (operator.equals("-")) {
                result = num1 - num2;
            } else if (operator.equals("*")) {
                result = num1 * num2;
            } else if(operator.equals("/")) {
                result = num1 / num2;
            }
            numbers.addFirst(result);
        }
        if (numbers.size() != 1) {
            throw new RuntimeException("expression is invalid: " + expression);
        }
        return  numbers.pop();
    }
}

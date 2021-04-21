package com.shawn.touchstone.functional.patterns;

public class StrategyExp {

     static class IsNumeric implements ValidationStrategy {
        public boolean execute(String s){
            return s.matches("\\d+");
        }
    }

    static class IsAllowCase implements ValidationStrategy {
        @Override
        public boolean execute(String s) {
            return s.matches("[a-z]+");
        }
    }
    interface ValidationStrategy {
        boolean execute(String s);
    }

    static class Validator {
        private final ValidationStrategy validationStrategy;

        public Validator(ValidationStrategy validationStrategy) {
            this.validationStrategy = validationStrategy;
        }

        public boolean validate(String s) {
            return validationStrategy.execute(s);
        }
    }

    public static void main(String[] args) {
        Validator validator = new Validator(new IsNumeric());
        validator.validate("abc");

        Validator validator1 = new Validator(str -> str.matches("[a-z]+"));
        validator1.validate("abc");
    }
}

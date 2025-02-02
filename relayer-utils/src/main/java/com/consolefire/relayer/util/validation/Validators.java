package com.consolefire.relayer.util.validation;

public class Validators {

    public static final Validator<Object> NOT_NULL_VALIDATOR = new NotNullValidator<>();

    private enum Operator {
        AND, OR
    }

    private final ValidatorNode root;

    private ValidatorNode current;


    private Validators(Validator<Object> validator, Object input) {
        this.root = new ValidatorNode(validator, input);
        this.current = this.root;
    }

    public static <T> Validators startWith(Validator<T> validator, T input) {
        return new Validators((Validator<Object>) validator, input);
    }

    public <T> Validators and(Validator<T> validator, T input) {
        ValidatorNode last = new ValidatorNode((Validator<Object>) validator, input);
        if (current == null) {
            current = root;
        }
        current.next = last;
        current.operator = Operator.AND;
        current = current.next;
        return this;
    }

    public <T> Validators or(Validator<T> validator, T input) {
        ValidatorNode last = new ValidatorNode((Validator<Object>) validator, input);
        if (current == null) {
            current = root;
        }
        current.next = last;
        current.operator = Operator.OR;
        current = current.next;
        return this;
    }

    public ValidationResult validateAll() {
        if (null == root.next) {
            return root.validator.validate(root.input);
        }
        ValidatorNode head = root;
        ValidationResult headResult = head.validator.validate(head.input);
        while (null != head.next) {
            ValidatorNode next = head.next;
            ValidationResult nextResult = next.validator.validate(next.input);
            if (Operator.AND.equals(head.operator)) {
                boolean result1 = headResult.isValid() && nextResult.isValid();
                if (!(result1)) {
                    return ValidationResult.builder(head.validator, head.input)
                        .withTest(() -> result1)
                        .withError(headResult.getValidatorId().toString(), headResult.getErrors())
                        .withError(nextResult.getValidatorId().toString(), nextResult.getErrors())
                        .build();
                }
            }
            if (Operator.OR.equals(head.operator)) {
                boolean result2 = headResult.isValid() || nextResult.isValid();
                headResult = ValidationResult.builder(head.validator, head.input)
                    .withTest(() -> result2)
                    .build();
            }
            head = head.next;
        }
        return headResult;
    }

    private static class ValidatorNode {

        private final Validator<Object> validator;
        private final Object input;
        private ValidatorNode previous;
        private ValidatorNode next;
        private Operator operator;

        public ValidatorNode(Validator<Object> validator, Object input) {
            this.validator = validator;
            this.input = input;
        }
    }

}

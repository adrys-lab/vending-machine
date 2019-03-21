package com.dexma.adrian.rebollo.model.evaluator.result;

/*
 * Wraps the evaluator message result.
 */
public class EvaluatorMessage {

    private final String message;

    public EvaluatorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static EvaluatorMessage createEmptyMessage() {
        return new EvaluatorMessage("");
    }
}

package com.dexma.adrian.rebollo.model.evaluator.result;

import java.util.ArrayList;
import java.util.List;

/*
 * Wraps the evaluator chain message result.
 */
public class EvaluatorResult {

    private final List<EvaluatorMessage> evaluatorMessages = new ArrayList<>();
    private boolean hasMessages = false;

    public void addEvaluatorMessage(final EvaluatorMessage evaluatorMessage) {
        if (!evaluatorMessage.getMessage().isEmpty()) {
            evaluatorMessages.add(evaluatorMessage);
            hasMessages = true;
        }
    }

    public List<EvaluatorMessage> getEvaluatorMessages() {
        return evaluatorMessages;
    }

    public boolean hasMessages() {
        return hasMessages;
    }
}

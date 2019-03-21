package com.dexma.adrian.rebollo.model.evaluator;

import com.dexma.adrian.rebollo.model.evaluator.result.EvaluatorMessage;
import com.dexma.adrian.rebollo.model.product.Product;

/**
 * Abstract class inherited by all product evaluators.
 */
abstract class AbstractProductEvaluator<P extends Product> implements Evaluator<P> {

    /**
     * Evaluate condition for a given product.
     * If a product doesn't pass the evaluation (depending on each evaluator),
     * then an evaluation message is created (depending on each evaluator).
     */
    @Override
    public EvaluatorMessage evaluate(final Product product) {
        if(!doEvaluate(product)) {
            return new EvaluatorMessage(getMessage(product));
        }
        return EvaluatorMessage.createEmptyMessage();
    }

    public abstract boolean doEvaluate(final Product product);
    public abstract String getMessage(final Product product);
}
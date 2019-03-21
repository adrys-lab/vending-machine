package com.dexma.adrian.rebollo.model.evaluator;

import com.dexma.adrian.rebollo.model.evaluator.result.EvaluatorMessage;
import com.dexma.adrian.rebollo.model.product.Product;

/**
 * Interface to evaluate some condition for extended product classes.
 */
@FunctionalInterface
public interface Evaluator<P extends Product> {

    /**
     * Evaluate condition for given parameter.
     */
    EvaluatorMessage evaluate(final P t);
}
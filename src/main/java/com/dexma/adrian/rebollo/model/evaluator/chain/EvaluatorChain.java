package com.dexma.adrian.rebollo.model.evaluator.chain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexma.adrian.rebollo.model.evaluator.Evaluator;
import com.dexma.adrian.rebollo.model.evaluator.result.EvaluatorResult;
import com.dexma.adrian.rebollo.model.product.Product;

/*
 * Executes a set of evaluators in chain and wraps it's result.
 * Evaluators are added into the chain, then evaluated, then the result should be obtained containing all chain list evaluators list of results.
 */
public class EvaluatorChain<P extends Product> {

    private final EvaluatorResult evaluatorResult = new EvaluatorResult();

    /*
     * Add evaluators as chain based on Map -> each evaluator of each class can only be added once.
     */
    private final Map<Class, Evaluator<P>> evaluatorChain = new HashMap<>();

    /*
     * Add evaluators to the chain.
     */
    public EvaluatorChain<P> addEvaluators(final List<Evaluator<P>> t) {
        t.forEach(p -> evaluatorChain.put(p.getClass(), p));
        return this;
    }

    public final EvaluatorChain<P> evaluateAll(final P p) {
        evaluatorChain.values()
                .forEach(evaluator -> evaluatorResult.addEvaluatorMessage(evaluator.evaluate(p)));
        return this;
    }

    public final EvaluatorResult getResult() {
        return evaluatorResult;
    }


}

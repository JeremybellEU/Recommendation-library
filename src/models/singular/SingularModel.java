package models.singular;

import models.DataMapModel;
import predictors.Predictor;
import datastructures.dataholders.Data;
import datastructures.input.DataMap;

/**
 * Create a model that has 1 predictor.
 *
 * @param <P>
 *            The predictor.
 * @param <F>
 *            The features to train/predict from.
 * @param <V>
 *            The value to predict to.
 */
public abstract class SingularModel<P extends Predictor<F, V>, F extends Data<F>, V>
implements DataMapModel<F, V> {

    /**
     * The predictor.
     */
    private final P predictor;

    /**
     * Creates a model with 1 predictor.
     *
     * @param _predictor
     *            {@link #predictor}.
     */
    protected SingularModel(final P _predictor) {
        this.predictor = _predictor;
    }

    @Override
    public abstract void train(DataMap<F> input, DataMap<V> output);

    /**
     * Get the predictor.
     *
     * @return {@link #predictor}.
     */
    public P getPredictor() {
        return this.predictor;
    }

    @Override
    public void reset() {
        this.predictor.reset();
    }
}

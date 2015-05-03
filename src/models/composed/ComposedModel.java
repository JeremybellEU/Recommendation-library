package models.composed;

import java.util.List;

import models.DataMapModel;
import predictors.Predictor;
import datastructures.dataholders.Data;
import datastructures.input.DataMap;

/**
 * A model that can handle multiple predictors and then predict on an equation
 * of all.
 *
 * @param <F>
 *            The features to train/predict on.
 * @param <V>
 *            The values as output
 */
public abstract class ComposedModel<F extends Data<F>, V> implements
		DataMapModel<F, V> {

	/**
	 * The predictors that will be trained.
	 */
	private final List<Predictor<F, V>> predictors;

	/**
	 * Create a composed model using the list of predictors.
	 *
	 * @param _predictors
	 *            The predictors for this composedModel.
	 */
	public ComposedModel(final List<Predictor<F, V>> _predictors) {
		this.predictors = _predictors;
	}

	@Override
	public final void train(final DataMap<F> input, final DataMap<V> output) {
		for (Integer i = input.firstKey(); i != null; i = input.higherKey(i)) {
			for (final Predictor<F, V> p : this.predictors) {
				p.train(input.get(i), output.get(i));
			}
		}
	}

	/**
	 * Get the predictors for this composed model.
	 *
	 * @return {@link #predictors}.
	 */
	public final List<Predictor<F, V>> getPredictors() {
		return this.predictors;
	}

	@Override
	public final void reset() {
		for (final Predictor<?, ?> p : this.getPredictors()) {
			p.reset();
		}
	}
}

package models;

import java.util.Map;
import datastructures.dataholders.Data;
import datastructures.input.DataMap;
import datastructures.output.WritableMap;

/**
 * A model that can be trained and predicted on from features to values.
 *
 * @param <F>
 *            The feature to train/predict.
 * @param <V>
 *            The value of the output.
 */
public interface DataMapModel<F extends Data<F>, V> extends Model<F, V> {

	/**
	 * Train the model on an input with the expected output.
	 *
	 * @param input
	 *            The input to train on.
	 * @param output
	 *            The expected output of the given input.
	 */
	void train(DataMap<F> input, DataMap<V> output);

	/**
	 * Predict the values for the given input.
	 *
	 * @param input
	 *            The input to be predicted.
	 * @return
	 * @return A writablemap as output.
	 */
	@Override
	WritableMap<V> predict(Map<?, F> input);

	/**
	 * Get the type of outputmap.
	 *
	 * @return The outputmap to write to.
	 */
	WritableMap<V> getOutputMap();

	/**
	 * Reset the model, used for retraining in the kfoldvalidator.
	 */
	void reset();
}

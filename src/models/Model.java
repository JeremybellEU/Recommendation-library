package models;

import java.util.Map;

/**
 * A model that can be trained and predicted on from features to values.
 *
 * @param <F>
 *            The feature to train/predict.
 * @param <V>
 *            The value of the output.
 */
public interface Model<F, V> {
	
	/**
     * Predict the values for the given input.
     *
     * @param input
     *            The input to be predicted.
     * @return A set of values as output.
     */
    Map<?, V> predict(Map<?, F> input);

}

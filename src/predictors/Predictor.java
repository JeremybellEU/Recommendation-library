package predictors;

import validators.KFoldValidator;

/**
 * A predictor can train and then predict from features F to values V.
 *
 * @param <F>
 *            The features to train/predict on.
 * @param <V>
 *            The value as output.
 */
public interface Predictor<F, V> {

    /**
     * Train on a feature with the expected output.
     *
     * @param vector
     *            The feature as input.
     * @param actualResult
     *            The value as expected output.
     */
    void train(F vector, V actualResult);

    /**
     * Predict on a vector which returns a value V.
     *
     * @param vector
     *            The feature to predict on.
     * @return The value that is predicted.
     */
    V predict(F vector);

    /**
     * Reset the predictor. Used in the {@link KFoldValidator}.
     */
    void reset();

}

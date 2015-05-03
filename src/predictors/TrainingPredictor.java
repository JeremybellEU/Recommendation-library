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
public interface TrainingPredictor<F, V> extends Predictor<F, V> {

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
     * Reset the predictor. Used in the {@link KFoldValidator}.
     */
    void reset();

}

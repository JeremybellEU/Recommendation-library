package predictors;

public interface Predictor<F, V> {

	/**
	 * Predict on a vector which returns a value V.
	 *
	 * @param vector
	 *            The feature to predict on.
	 * @return The value that is predicted.
	 */
	public abstract V predict(F vector);

}
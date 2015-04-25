package errorMetric;

/**
 * Implementing classes should compute an error given two data elements. This
 * can for example be the Sum of Squares.
 *
 * @param <V>
 *            - The type of elements to compute error on.
 */
public interface ErrorMetric<V> {

    /**
     * Compute the error between the predicted and the actual value.
     *
     * @param predicted
     *            The predicted value.
     * @param actual
     *            The actual value.
     * @return The error.
     */
    double computeError(V predicted, V actual);
}

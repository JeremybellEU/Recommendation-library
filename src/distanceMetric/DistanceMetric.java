package distanceMetric;

/**
 * Implementing classes should compute the distance between two data elements.
 * This can for example be Euclidian distance or Hamming distance.
 *
 * @param <V>
 *            - The type of elements to compute distances on.
 */
public interface DistanceMetric<V> {

    /**
     * The distance between some and other.
     *
     * @param some
     *            The first element.
     * @param other
     *            The second element.
     * @return The distance between the 2 elements.
     */
    double distanceBetween(V some, V other);
}

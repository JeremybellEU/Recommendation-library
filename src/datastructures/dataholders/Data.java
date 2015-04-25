package datastructures.dataholders;

/**
 *
 * @author Tim Laptop
 *
 * @param <E>
 *            itself.
 */
public interface Data<E extends Data<E>> extends Comparable<E> {

    /**
     * Copy this element returning the same type of this.
     *
     * @return A copy, but a different object.
     */
    E copy();

    int getIndex();
}

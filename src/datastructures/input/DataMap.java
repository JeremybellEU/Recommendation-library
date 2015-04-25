package datastructures.input;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * A dataMap which is a treemap of D elements with Integers as keys.
 *
 * @param <D>
 *            The element that is in the treemap.
 */
public abstract class DataMap<D> extends ConcurrentSkipListMap<Integer, D> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
}

package datastructures;

import datastructures.input.DataMap;

/**
 * A DataMap that encapsulates a subset of data from another map.
 *
 * @param <E>
 */
public class SubMap<E> extends DataMap<E> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * The data from the whole map.
     */
    private final DataMap<E> data;

    /**
     * The range defined for this submap.
     */
    private final int keyFrom, keyTo;

    /**
     * The state, could be {@link NormalDataState} or {@link InvertedDataState}.
     */
    private DataState dataState;

    /**
     * Create a new map from a subset of data. The range of this subset is
     * specified by keyFrom and keyTo.
     *
     * @param _data
     *            The data to encapsulate a subset from.
     * @param _keyFrom
     *            The lower bound of the subset.
     * @param _keyTo
     *            The upper bound of the subset.
     */
    public SubMap(final DataMap<E> _data, final int _keyFrom, final int _keyTo) {
        this.data = _data;
        this.keyFrom = _keyFrom;
        this.keyTo = _keyTo;
        this.dataState = new NormalDataState();
    }

    /**
     * A {@link SubMap#dataState} for the submap.
     *
     */
    private abstract class DataState {

        /**
         * Invert the state.
         *
         * @return The new DataState.
         */
        public abstract DataState invert();

        /**
         * Get an item from the submap.
         *
         * @param key
         *            The key.
         * @return The value. Throws an exception if out of the range.
         */
        public abstract E get(Object key);

        /**
         * The last key of the submap.
         *
         * @return The integer as key.
         */
        public abstract Integer lastKey();

        /**
         * The first key of the submap.
         *
         * @return The integer as key.
         */
        public abstract Integer firstKey();

        /**
         * The higher key given the current key.
         *
         * @param currentKey
         *            The previous key.
         * @return The next key.
         */
        public abstract Integer higherKey(Integer currentKey);

        /**
         * The lower key given the current key.
         *
         * @param currentKey
         *            The latter key.
         * @return The previous key.
         */
        public abstract Integer lowerKey(Integer currentKey);

        /**
         * The size of the submap considering the in-/excluded range.
         *
         * @return The size.
         */
        public abstract int size();
    }

    /**
     * A normal state that includes everything except the range given.
     */
    private class NormalDataState extends DataState {

        @Override
        public DataState invert() {
            return new InvertedDataState();
        }

        @Override
        public E get(final Object key) {
            final Integer integerKey = (Integer) key;

            if (integerKey < SubMap.this.keyFrom
                    || integerKey > SubMap.this.keyTo) {
                throw new IndexOutOfBoundsException(integerKey
                        + " is not within bounds [" + SubMap.this.keyFrom
                        + ", " + SubMap.this.keyTo + "]");
            }

            return SubMap.this.data.get(key);
        }

        @Override
        public Integer lastKey() {
            return SubMap.this.keyTo;
        }

        @Override
        public Integer firstKey() {
            return SubMap.this.keyFrom;
        }

        @Override
        public Integer higherKey(final Integer currentKey) {
            Integer nextKey = currentKey + 1;

            if (nextKey > SubMap.this.keyTo) {
                nextKey = null;
            }

            return nextKey;
        }

        @Override
        public Integer lowerKey(final Integer currentKey) {
            Integer nextKey = currentKey - 1;

            if (nextKey < SubMap.this.keyFrom) {
                nextKey = null;
            }

            return nextKey;
        }

        @Override
        public int size() {
            return SubMap.this.keyTo - SubMap.this.keyFrom + 1;
        }
    }

    /**
     * An inverted state that excludes everything except the range given.
     */
    private class InvertedDataState extends DataState {

        @Override
        public DataState invert() {
            return new NormalDataState();
        }

        @Override
        public E get(final Object key) {
            final Integer integerKey = (Integer) key;

            if (integerKey >= SubMap.this.keyFrom
                    && integerKey <= SubMap.this.keyTo) {
                throw new IndexOutOfBoundsException(integerKey
                        + " is not within bounds ["
                        + SubMap.this.data.firstKey() + ", "
                        + SubMap.this.keyFrom + ") and (" + SubMap.this.keyTo
                        + ", " + SubMap.this.data.lastKey() + "]");
            }

            return SubMap.this.data.get(key);
        }

        @Override
        public Integer lastKey() {
            return this.lowerKey(SubMap.this.data.lastKey() + 1);
        }

        @Override
        public Integer firstKey() {
            return this.higherKey(SubMap.this.data.firstKey() - 1);
        }

        @Override
        public Integer higherKey(final Integer currentKey) {
            Integer nextKey = currentKey + 1;

            if (nextKey >= SubMap.this.keyFrom && nextKey <= SubMap.this.keyTo) {
                nextKey = SubMap.this.keyTo + 1;
            }
            if (nextKey > SubMap.this.data.lastKey()) {
                nextKey = null;
            }

            return nextKey;
        }

        @Override
        public Integer lowerKey(final Integer currentKey) {
            Integer nextKey = currentKey - 1;

            if (nextKey >= SubMap.this.keyFrom && nextKey <= SubMap.this.keyTo) {
                nextKey = SubMap.this.keyFrom - 1;
            }
            if (nextKey < SubMap.this.data.firstKey()) {
                nextKey = null;
            }

            return nextKey;
        }

        @Override
        public int size() {
            return SubMap.this.data.size()
                    - (SubMap.this.keyTo - SubMap.this.keyFrom + 1);
        }
    }

    /**
     * @return The size of the subset of data.
     */
    @Override
    public final int size() {
        return this.dataState.size();
    }

    @Override
    public final Integer firstKey() {
        return this.dataState.firstKey();
    }

    @Override
    public final Integer lastKey() {
        return this.dataState.lastKey();
    }

    @Override
    public final Integer higherKey(final Integer currentKey) {
        return this.dataState.higherKey(currentKey);
    }

    @Override
    public final Integer lowerKey(final Integer currentKey) {
        return this.dataState.lowerKey(currentKey);
    }

    @Override
    public final E get(final Object key) {
        return this.dataState.get(key);
    }

    /**
     * Inverts the subset of data that this map encapsulates. For example: if
     * the original map has 100 keys, from 0 to 99. And this subset was created
     * on keys 20 to 30. Then invert will cause this map to instead encapsulate
     * keys 0-19 and 31-99.
     */
    public final void invert() {
        this.dataState = this.dataState.invert();
    }
}

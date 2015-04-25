package algorithms;

import datastructures.dataholders.Pair;
import distanceMetric.DistanceMetric;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class KNearestNeighbours<E> {
    private final NeighbourComparator comparator = new NeighbourComparator();
    private final DistanceMetric<E> distanceMetric;
    private final Collection<E> dataPoints;

    public KNearestNeighbours(final Collection<E> _dataPoints,
            final DistanceMetric<E> distanceMetric) {
        this.distanceMetric = distanceMetric;
        this.dataPoints = _dataPoints;
    }

    public Collection<Pair<Double, E>> getNearestNeighbours(final int k,
            final E query) {
        final LinkedList<Pair<Double, E>> neighbours = new LinkedList<Pair<Double, E>>();

        for (final E n : this.dataPoints) {
            if (n.equals(query)) {
                continue;
            }

            final Double dist = this.distanceMetric.distanceBetween(n, query);

            if (neighbours.size() < k) {
                neighbours.add(new Pair<>(dist, n));
                Collections.sort(neighbours, this.comparator);
            } else if (neighbours.getLast().getLeft() > dist) {
                neighbours.removeLast();
                neighbours.add(new Pair<>(dist, n));
                Collections.sort(neighbours, this.comparator);
            }
        }
        return neighbours;
    }

    private class NeighbourComparator implements Comparator<Pair<Double, E>> {

        @Override
        public int compare(final Pair<Double, E> o1, final Pair<Double, E> o2) {
            return o1.getLeft().compareTo(o2.getLeft());
        }
    }

}

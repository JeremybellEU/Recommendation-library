package datastructures.dataholders;

public class Pair<L, R> {

    private final L left;

    private final R right;

    public Pair(final L left, final R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return this.left;
    }

    public R getRight() {
        return this.right;
    }

    @Override
    public int hashCode() {
        return this.left.hashCode() ^ this.right.hashCode();
    }

    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof Pair)) {
            return false;
        }
        final Pair<?, ?> pairo = (Pair<?, ?>) object;
        return this.left.equals(pairo.getLeft())
                && this.right.equals(pairo.getRight());
    }

    @Override
    public String toString() {
        return this.left.toString() + ":" + this.right.toString();
    }
}

package datastructures.output;

import java.io.OutputStream;
import java.io.PrintWriter;

import datastructures.input.DataMap;

/**
 * A datamap that can write to a file.
 *
 * @param <E>
 *            The element type that is in this map.
 */
public abstract class WritableMap<E> extends DataMap<E> implements Writable<E> {

    /**
     * Default ID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void writeResultsFile(final OutputStream stream) {
        final PrintWriter pw = new PrintWriter(stream);

        for (int i = 1; i <= this.size(); i++) {
            pw.println(i + "," + this.getOutputLine(this.get(i)));
        }
        pw.close();
    }
}

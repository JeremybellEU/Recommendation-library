package datastructures.output;

import java.io.OutputStream;
import java.io.PrintWriter;

public interface Writable<E> {

    /**
     * Write the contents of this map to the given file.
     *
     * @param fileName
     *            The name of the file to write to.
     */
    void writeResultsFile(final OutputStream stream);

    String getOutputLine(final E value);

    void writeFieldNames(final PrintWriter pw);

}
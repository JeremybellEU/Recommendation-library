package datastructures.input;

import java.io.Reader;

public interface Readable<E> {

    /**
     * Reads in a file with movies data.
     *
     * @param filename
     *            The name of the file to read from.
     */
    void readFile(final Reader stream);

    E readLine(final String line);

}
package datastructures.input;

import java.io.BufferedReader;
import java.io.Reader;

import datastructures.dataholders.Data;

/**
 * A datamap that can read its data from a file.
 *
 * @param <E>
 *            The element that the content of the file represent.
 */
public abstract class ReadableMap<E extends Data<E>> extends DataMap<E>
implements Readable<E> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public final void readFile(final Reader stream) {
        BufferedReader br = null;
        String line;
        try {
            br = new BufferedReader(stream);
            int i = 1;
            while ((line = br.readLine()) != null) {
                this.put(i++, this.readLine(line));
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

package datastructures.hybrid;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;

import datastructures.input.DataMap;
import datastructures.input.Readable;
import datastructures.output.Writable;

public abstract class Hybrid<E> extends DataMap<E> implements Readable<E>,
        Writable<E> {

    private static final long serialVersionUID = 1L;

    @Override
    public void writeResultsFile(final OutputStream stream) {
        final PrintWriter pw = new PrintWriter(stream);

        for (int i = 1; i <= this.size(); i++) {
            pw.println(i + "," + this.getOutputLine(this.get(i)));
        }
        pw.close();
    }

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

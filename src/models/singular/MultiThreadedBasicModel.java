package models.singular;

import datastructures.dataholders.Rating;
import datastructures.input.DataMap;
import datastructures.output.WritableMap;
import predictors.Predictor;

import java.util.ArrayList;
import java.util.List;

/**
 * A basic model that can turn ratings into ratings.
 */
public class MultiThreadedBasicModel extends
        BasicModel {

    protected static final int THREAD_COUNT = 8;

    /**
     * Get a predictor which turns ratings into ratings.
     *
     * @param predictor
     *            The predictor.
     */
    public MultiThreadedBasicModel(final Predictor<Rating, Rating> predictor) {
        super(predictor);
    }

    @Override
    public WritableMap<Rating> predict(final DataMap<Rating> input) {
        List<Thread> workers = new ArrayList<>(THREAD_COUNT);

        final int[] foldSize = this.initFoldSizes(input.size());
        int startKey, endKey;
        WritableMap<Rating> out = this.getOutputMap();

        for (int i = 0; i < THREAD_COUNT; i++) {
            startKey = i * foldSize[0] + input.firstKey();
            endKey = (i + 1) * foldSize[0] + input.firstKey() - 1;
            if (i == THREAD_COUNT - 1) {
                endKey = i * foldSize[0] + foldSize[1];
            }

            Thread worker = new Thread(new WorkerRunnable(startKey, endKey, input, out));
            workers.add(worker);
            System.out.println("Started thread " + i);
            worker.start();
        }

        for (int i = 0; i < THREAD_COUNT; i++) {
            try {
                workers.get(i).join();
                System.out.println("Thread " + i + " finished");
            } catch (InterruptedException e) {
                // Will probably not happen
            }
        }


        return out;
    }

    private class WorkerRunnable implements Runnable {

        private final Integer startKey;
        private final Integer endKey;
        private final DataMap<Rating> data;
        private final WritableMap<Rating> output;

        public WorkerRunnable(Integer startKey, Integer endKey, DataMap<Rating> data, WritableMap<Rating> output) {
            this.startKey = startKey;
            this.endKey = endKey;
            this.data = data;
            this.output = output;
        }

        @Override
        public void run() {
            for (Integer i = startKey; i != null && i <= endKey; i = data.higherKey(i)) {
                output.put(i, getPredictor().predict(data.get(i)));
            }
        }

        public WritableMap<Rating> getOutput() {
            return output;
        }
    }

    private int[] initFoldSizes(final int dataSize) {
        final int[] foldSize = new int[2];

        foldSize[0] = dataSize / THREAD_COUNT;

        // Size of the last fold may be larger than the size of all other folds
        // To make sure all datapoints are in a fold
        foldSize[1] = dataSize - foldSize[0] * (THREAD_COUNT - 1);
        return foldSize;
    }


}

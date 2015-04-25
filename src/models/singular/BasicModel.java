package models.singular;

import predictors.Predictor;
import datastructures.dataholders.Rating;
import datastructures.input.DataMap;
import datastructures.output.OutputRatingMap;
import datastructures.output.WritableMap;

/**
 * A basic model that can turn ratings into ratings.
 */
public class BasicModel extends
        SingularModel<Predictor<Rating, Rating>, Rating, Rating> {

    /**
     * Get a predictor which turns ratings into ratings.
     *
     * @param predictor
     *            The predictor.
     */
    public BasicModel(final Predictor<Rating, Rating> predictor) {
        super(predictor);
    }

    @Override
    public void train(final DataMap<Rating> input, final DataMap<Rating> output) {
//        for (Integer i = input.firstKey(); i != null; i = input.higherKey(i)) {
//            this.getPredictor().train(input.get(i), output.get(i));
//        }
    }

    @Override
    public WritableMap<Rating> predict(final DataMap<Rating> input) {
        final WritableMap<Rating> output = this.getOutputMap();

        long start = System.currentTimeMillis();
        int count = 0;
        for (Integer i = input.firstKey(); i != null; i = input.higherKey(i)) {
            output.put(i, this.getPredictor().predict(input.get(i)));
            count++;
            final long now = System.currentTimeMillis();
            if (now - start >= 1000) {
                start = now;
                System.out.println("Performing " + count
                        + " predictions per second.");
                count = 0;
            }
        }

        return output;
    }

    @Override
    public final WritableMap<Rating> getOutputMap() {
        return new OutputRatingMap();
    }
}
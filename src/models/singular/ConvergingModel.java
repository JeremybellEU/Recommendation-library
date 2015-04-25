package models.singular;

import predictors.ConvergingPredictor;
import datastructures.dataholders.Rating;
import datastructures.input.DataMap;
import datastructures.output.OutputRatingMap;
import datastructures.output.WritableMap;

/**
 * A model that will train its predictor until the predictor
 * {@link ConvergingPredictor#isConverged()}.
 */
public class ConvergingModel extends
SingularModel<ConvergingPredictor<Rating, Rating>, Rating, Rating> {

    /**
     * The current index that it should train on.
     */
    private Integer index;

    /**
     * A converging model that uses 1 predictor from ratings to rating.
     *
     * @param predictor
     *            The predictor that predicts ratings from ratings.
     */
    public ConvergingModel(final ConvergingPredictor<Rating, Rating> predictor) {
        super(predictor);
    }

    @Override
    public final void train(final DataMap<Rating> input,
            final DataMap<Rating> output) {
        while (!this.getPredictor().isConverged()) {
            if (this.index == null) {
                this.index = input.firstKey();
            }

            this.adjustOne(input.get(this.index), output.get(this.index));

            this.index = input.higherKey(this.index);
        }
    }

    /**
     * Train on one feature/output line.
     *
     * @param input
     *            The feature to train on.
     * @param output
     *            The expected output.
     */
    private void adjustOne(final Rating input, final Rating output) {
        this.getPredictor().train(input, output);
    }

    @Override
    public final WritableMap<Rating> predict(final DataMap<Rating> input) {
        final WritableMap<Rating> output = this.getOutputMap();

        for (Integer i = input.firstKey(); i != null; i = input.higherKey(i)) {
            output.put(i, this.getPredictor().predict(input.get(i)));
        }

        return output;
    }

    @Override
    public final WritableMap<Rating> getOutputMap() {
        return new OutputRatingMap();
    }

}

package validators;

import models.DataMapModel;
import datastructures.SubMap;
import datastructures.dataholders.Data;
import datastructures.input.DataMap;
import datastructures.output.WritableMap;
import errorMetric.ErrorMetric;

/**
 * Divide the data into k equally sized parts. Trains and validates the model on
 * k different, disjoint folds of the data and returns the mean error of the k
 * validations.
 *
 * @author Thomas Smith
 */
public class KFoldValidator<F extends Data<F>, V> implements
        CrossValidator<F, V> {

    /**
     * The number of folds.
     */
    private int k;

    /**
     * The errormetric used to determine the average error of the folds.
     */
    private ErrorMetric<V> errorMetric;

    /**
     * To make sure you can't make a KFoldValidator without a k.
     */
    private KFoldValidator() {
    }

    /**
     * Initialize the validator.
     *
     * @param _k
     *            The amount of folds to use.
     *
     * @param _errorMetric
     *            {@link #errorMetric}.
     */
    public KFoldValidator(final int _k, final ErrorMetric<V> _errorMetric) {
        this.k = _k;
        this.errorMetric = _errorMetric;
    }

    @Override
    public final double validate(final DataMapModel<F, V> model,
            final DataMap<F> features, final DataMap<V> labels) {
        if (features.size() != labels.size()) {
            throw new IllegalArgumentException(
                    "Please provide an equal amount of features and labels");
        }

        double errorSum = 0.0;
        final int[] foldSize = this.initFoldSizes(features.size());

        SubMap<F> currentTrainingData;
        SubMap<V> currentLabelData;

        int foldStart, foldEnd;

        for (int fold = 0; fold < this.k; fold++) {

            foldStart = fold * foldSize[0] + 1;
            foldEnd = (fold + 1) * foldSize[0];
            if (fold == this.k - 1) {
                foldEnd = fold * foldSize[0] + foldSize[1];
            }
            System.out.println("Training fold " + (fold + 1) + " of " + this.k);
            currentTrainingData = new SubMap<>(features, foldStart, foldEnd);
            currentLabelData = new SubMap<>(labels, foldStart, foldEnd);

            // Switch to training set
            currentTrainingData.invert();
            currentLabelData.invert();

            // Train
            model.train(currentTrainingData, currentLabelData);

            System.out.println("Validating...");
            // Switch to validation set
            currentTrainingData.invert();
            currentLabelData.invert();

            final WritableMap<V> predictions = model
                    .predict(currentTrainingData);
            errorSum += this.computeError(predictions, currentLabelData);

            model.reset();
        }

        return errorSum / features.size();
    }

    /**
     * Compute the error using the {@link #errorMetric}.
     *
     * @param predictions
     *            The predictions produced.
     * @param labels
     *            The expected output.
     * @return The error of this validation.
     */
    private double computeError(final DataMap<V> predictions,
            final DataMap<V> labels) {
        double errorSum = 0.0;
        for (Integer key = predictions.firstKey(); key != null; key = predictions
                .higherKey(key)) {

            final V prediction = predictions.get(key);
            final V actual = labels.get(key);

            final double currentError = this.errorMetric.computeError(
                    prediction, actual);

            errorSum += currentError;
        }
        return errorSum;
    }

    /**
     * Create the k folds with size dataSize.
     *
     * @param dataSize
     *            The size.
     * @return The sizes that also take the remainder in consideration.
     */
    private int[] initFoldSizes(final int dataSize) {
        final int[] foldSize = new int[2];

        foldSize[0] = dataSize / this.k;

        // Size of the last fold may be larger than the size of all other folds
        // To make sure all datapoints are in a fold
        foldSize[1] = dataSize - foldSize[0] * (this.k - 1);
        return foldSize;
    }
}

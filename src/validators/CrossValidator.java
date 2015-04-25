package validators;

import models.Model;
import datastructures.dataholders.Data;
import datastructures.input.DataMap;

/**
 * Interface for applying cross validation on a model.
 *
 * @param <F>
 *            The features that it cross validates.
 * @param <V>
 *            The values that it cross validates to.
 */
public interface CrossValidator<F extends Data<F>, V> {

    /**
     * Implementing classes should apply cross validation on the given model
     * with the given data. Data will be split into training and validation
     * parts by implemeting classes.
     *
     * @param model
     *            - The model to train and validate
     * @param features
     *            - All features to train on
     * @param output
     *            - The correct output for each feature
     * @return The error measure that results from the validation.
     */
    double validate(Model<F, V> model, DataMap<F> features, DataMap<V> output);

}

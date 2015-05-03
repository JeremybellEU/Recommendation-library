package predictors;

public abstract class ConvergingPredictor<F, V> implements TrainingPredictor<F, V> {
	
	public abstract boolean isConverged();

}

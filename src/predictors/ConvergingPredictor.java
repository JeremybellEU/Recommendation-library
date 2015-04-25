package predictors;

public abstract class ConvergingPredictor<F, V> implements Predictor<F, V> {
	
	public abstract boolean isConverged();

}

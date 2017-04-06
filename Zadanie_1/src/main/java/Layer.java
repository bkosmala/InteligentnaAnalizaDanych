package main.java;

import java.lang.*;

public class Layer {

	private int singleInputsCount;
	private int neuronsCount;
	private double[][] weightsMatrix;
	private double[][] deltaWeights;
	private boolean isBias;
	private double beta;
	
	//To Learn Mode
	public double[] savedNeuronsOutput;
	public double[] savedNeuronsInSum;
	public double[] backError;

	// klasa symuluj�ca ca�� werstw� neuron�w
	public Layer(int neuronsCount, int singleInputsCount, boolean isBias, double beta) {
		// singleInputsCount - ilo�� wej�� dla pojedynczego neuronu, czyli r�wnie� ilo�� wag
		// + opr�cz tego dochodzi ewentualnie bias
		// neuronsCount - ilo�� neuron�w w warstwie

		// w klasie tej operujemy na wektorach i macierzach. Je�li potraktujemy wej�cie X oraz wyj�cie Y jako wektory liczb rzeczywistych,
		// to warstwa neuron�w zachowuje si� jako macierz N i mamy r�wnanie Y = N * X
		// mno�enie macie�y wymaga zgodno�ci wymiar�w tzn macie� o wymiarach [n,m] * [m,p] => [n,p]
		// u nas n - to ilo�� neuron�w w warstwie, m - ilo�� wag, a p jest zawsze r�wne 1 (na wyj�ciu otrzymujemy wektor o wymiarach [n,1] )
		this.beta = beta;
		this.singleInputsCount = singleInputsCount;
		this.neuronsCount = neuronsCount;
		this.isBias = isBias;
			if(this.isBias){this.singleInputsCount++;}
		this.weightsMatrix = new double[neuronsCount][this.singleInputsCount];
		this.deltaWeights = new double[neuronsCount][this.singleInputsCount];
		

		for (int i = 0; i < neuronsCount; i++) // inicjalizacja wag liczbami losowymi
		{
			for (int k = 0; k < this.singleInputsCount; k++) {
				this.weightsMatrix[i][k] = randomNumber(-1, 1);
				this.deltaWeights[i][k] = 0;
				 //System.out.println("\nNeuron: "+ i +" Waga " + k + " Warto��: " + this.weightsMatrix[i][k]);
			}
		}
		
		this.savedNeuronsOutput = new double[this.neuronsCount];
		this.savedNeuronsInSum = new double[this.neuronsCount];
		this.backError = new double[neuronsCount];
	}

	public double[] Learn(double[] inputs) {
		
		inputs = this.addBiasIfNeeded(inputs);
	
		double[] result = new double[this.neuronsCount];

		for (int i = 0; i < neuronsCount; i++) {
			for (int k = 0; k < this.singleInputsCount; k++) {
				//System.out.println("Wej�cie " + k + " warto�� " + inputs[k]);
				result[i] += this.weightsMatrix[i][k] * inputs[k];
			}
			this.savedNeuronsInSum[i]=result[i];
			result[i] = sigmoidActivation(this.beta, result[i]);
			//System.out.println("Neuron " + i + " odpowied� " + result[i]);
		}
		this.savedNeuronsOutput=result;
		return result;
	}

	public double[] getOutput(double[] inputs) {
		
		inputs = this.addBiasIfNeeded(inputs);
		
		double[] result = new double[this.neuronsCount];

		for (int i = 0; i < neuronsCount; i++) {
			for (int k = 0; k < this.singleInputsCount; k++) {
				//System.out.println("Wej�cie " + k + " warto�� " + inputs[k]);
				result[i] += this.weightsMatrix[i][k] * inputs[k];
			}
			result[i] = sigmoidActivation(this.beta, result[i]);
			//System.out.println("Neuron " + i + " odpowied� " + result[i]);
		}
		return result;
	}
	
	public void calculateBackError(double[] outputs)
	{
		for(int s=0; s<neuronsCount;s++)
		{
		this.backError[s]= (
				sigmoidActivation(this.beta, savedNeuronsInSum[s]) * (1 - sigmoidActivation(this.beta, savedNeuronsInSum[s]))
			  ) * (outputs[s] - savedNeuronsOutput[s]);
		}	
	}
	
	public void calculateBackError(Layer nextLayer)
	{
		for(int i=0; i<neuronsCount;i++)
		{
			backError[i]=0;
			for(int k=0;k<nextLayer.getNeuronsCount();k++)
			{
				backError[i] += (nextLayer.getWeightsMatrix())[k][i] * nextLayer.backError[k];
			}
			backError[i] = backError[i] * (
				sigmoidActivation(this.beta, savedNeuronsInSum[i]) * (1 - sigmoidActivation(this.beta, savedNeuronsInSum[i])));
		}	
	}
	
	
	public void modifyWeights(double step, double moment, double[] inputs) { 
		
		inputs = this.addBiasIfNeeded(inputs);	
		double prevWeight;
		for (int i = 0; i < this.neuronsCount; i++)
		{
			for (int k = 0; k < this.singleInputsCount; k++) {	
				prevWeight = this.weightsMatrix[i][k];
				this.weightsMatrix[i][k] +=  step * this.backError[i] * inputs[k] + moment * this.deltaWeights[i][k];
				this.deltaWeights[i][k] = this.weightsMatrix[i][k] - prevWeight;	 
			}
		}
	}

	public static double sigmoidActivation(double beta, double x) {
		// sigmoidalna funkcja aktywacji, postaci f(s)=1/(1+exp(bs)). gdzie x - argument
		// beta - wsp�czynnik, gdy d��y do nieskonczono��i funkcja zmienia si� w funkcj� skokow�.
		// domy�lnie beta=1
		return 1.0 / (1.0 + Math.exp(-1 * beta * x));
	}

	public static double randomNumber(double min, double max) {
		double d = min + Math.random() * (max - min);
		return d;
	}
	
	public double[] addBiasIfNeeded(double[] inputs)
	{
		if(this.isBias){
			double[] newInputs = new double[inputs.length +1];
			for(int i = 0; i<inputs.length;i++)
			{
				newInputs[i] = inputs[i];
			}
			newInputs[inputs.length] = 1; // bias
			return newInputs;
		}
		return inputs;	
	}
	
	public int getNeuronsCount()
	{
		return this.neuronsCount;
	}
	
	public double[][] getWeightsMatrix()
	{
		return this.weightsMatrix;
	}
	
}	

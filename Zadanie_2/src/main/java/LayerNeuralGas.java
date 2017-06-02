package main.java;

import java.lang.*;

public class LayerNeuralGas {

	private int singleInputsCount;
	private int neuronsCount;
	private double[][] weightsMatrix;
	private double[] potential;
	private double minPotential;
	private int[] winnersCount;

	// klasa symuluj¹ca ca³¹ werstwê neuronów
	public LayerNeuralGas(int neuronsCount, int singleInputsCount, double minPotential) {
		this.singleInputsCount = singleInputsCount;
		this.neuronsCount = neuronsCount;
		this.weightsMatrix = new double[neuronsCount][this.singleInputsCount];
		this.potential = new double[neuronsCount];
		this.winnersCount = new int[neuronsCount];
		this.minPotential = minPotential;
		
		for (int i = 0; i < neuronsCount; i++) //inicjalizacja po³o¿enia neuronów - losowe wartoœci
		{
			this.weightsMatrix[i][0] = randomDouble(-11,13);
			this.weightsMatrix[i][1] = randomDouble(-12,9);
				 //System.out.println("\nNeuron: "+ i +" Waga " + k + " Wartoœæ: " + this.weightsMatrix[i][k]);
			
			this.potential[i] = minPotential;
			this.winnersCount[i] = 1;
		}
		
		
	}

	
	public double[] LearnNeuralGas(double[] inputs, double step, double radius) {
		
		int[] sortedIndexes;
		double[] result = new double[this.neuronsCount];
		for(int i = 0; i<this.neuronsCount;i++){result[i]=0;}

		for (int i = 0; i < neuronsCount; i++) {
			for (int k = 0; k < this.singleInputsCount; k++) {
				result[i] +=  (inputs[k] - this.weightsMatrix[i][k])*(inputs[k] - this.weightsMatrix[i][k]);
			}
			result[i] = Math.sqrt(result[i]);
			//System.out.println("Neuron " + i +" (" + this.weightsMatrix[i][0] + "," + this.weightsMatrix[i][1] + ") " +  " odpowiedŸ " + result[i]);
		}
		//System.out.println("Zwyciêzca : " + winnerIndex);
		sortedIndexes = getSortedNeurons(result);
		modifyWeights(inputs,step,sortedIndexes,radius);
		//setPotential(winnerIndex);
		//preventDeadNeurons(step,winnerIndex);
		
		return result;

	}
	
	
	public void modifyWeights(double[] inputs, double step, int[] sorted ,double radius) { 
		
		for (int i = 0; i < this.neuronsCount; i++)
		{
			if(i==sorted[0]) continue;
			for (int k = 0; k < this.singleInputsCount; k++) {	
				this.weightsMatrix[i][k] +=  step * neighborhoodFunction(sorted,i,radius) * (inputs[k] - this.weightsMatrix[i][k]);	 
			}
		}
	}
	
	private double neighborhoodFunction(int[] sorted,int index, double radius)
	{
		double temp = Math.exp(-1*getPosition(sorted,index)/radius);
		return temp;
	}
	
	private int getPosition(int[] sorted, int neuron)
	{
		for(int i = 0; i < this.neuronsCount;i++)
		{
			if(sorted[i]==neuron) return i;
		}
		
		return this.neuronsCount + 100;
	}
	
	private void setPotential(int winnerNumber)
	{
		for(int i =0; i< this.potential.length;i++){
			
			if(i == winnerNumber) 
			{
				this.potential[winnerNumber] -= this.minPotential;	
			}
			else
			{
				//this.potential[i] += 1.0/this.potential.length;
				this.potential[i] += 1.0/this.neuronsCount;
				//System.out.println(this.potential[i]);
				if(this.potential[i]>1.0) this.potential[i]=1.0;
			}	
		}	
	}
	
	public double getErrorAfterEpoch( double[][] inputs)
	{
		//znalezienie odleg³oœci do najblizszego wektora wejœciowego dla ka¿dego neuronu
		double result = 0;
		double temp1 = 0;
		double temp2 = 1000000;
		for(int n = 0 ; n < this.neuronsCount; n++)
		{
		temp2 = 1000000;
		for(int i = 0; i<inputs.length;i++ )
		{
			temp1=0;
			for (int k = 0; k < this.singleInputsCount; k++) 
			{
				temp1 += (inputs[i][k] - this.weightsMatrix[n][k])*
					     (inputs[i][k] - this.weightsMatrix[n][k]);
			}
			temp1 = Math.sqrt(temp1);
			if(temp2 > temp1) temp2=temp1;
		}
		result +=  temp2;
		}
		return result/this.neuronsCount;
	}
	
	private int[] getSortedNeurons(double[] data)
	{
		int[] result = new int[this.neuronsCount];
		double[] temp = new double[this.neuronsCount];
		
		for(int i=0;i<this.neuronsCount;i++)
		{
			temp[i]=data[i];
			result[i]=i;
		}
		double temp1;
		int temp2;
		for(int i=0;i<this.neuronsCount;i++)
		for(int j=1;j<this.neuronsCount-i;j++)
		{
		    if(temp[j-1]>temp[j])
		    {
		    	temp1=temp[j-1];
		    	temp2=result[j-1];
		    	temp[j-1]=temp[j];
		    	result[j-1]=result[j];
		    	temp[j]=temp1;
		    	result[j]=temp2;
		    }
		}
		return result;
	}
	
	public int removeDeadNeurons( double[][] inputs)
	{
		//znalezienie odleg³oœci do najblizszego wektora wejœciowego dla ka¿dego neuronu
		double result = 0;
		double temp1 = 0;
		double temp2 = 1000000;
		int counter =0;
		for(int n = 0 ; n < this.neuronsCount; n++)
		{
		temp2 = 1000000;
		for(int i = 0; i<inputs.length;i++ )
		{
			temp1=0;
			for (int k = 0; k < this.singleInputsCount; k++) 
			{
				temp1 += (inputs[i][k] - this.weightsMatrix[n][k])*
					     (inputs[i][k] - this.weightsMatrix[n][k]);
			}
			temp1 = Math.sqrt(temp1);
			if(temp2 > temp1) temp2=temp1;
		}
		// za³o¿ona maksymalna odleg³oœæ
		if(temp2>0.5){
			this.weightsMatrix[n][0] = 100; 
			this.weightsMatrix[n][1] = 100;
			counter++;
		}
		result +=  temp2;
		}
		return counter;
	}
	
	public static double randomDouble(double min, double max) {
		double d = min + (Math.random() * (max - min));
		return d;
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

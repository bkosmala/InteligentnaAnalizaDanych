package main.java;

import java.lang.*;

public class LayerKohonen {

	private int singleInputsCount;
	private int neuronsCount;
	private double[][] weightsMatrix;
	private double[] potential;
	private double minPotential;
	private int[] winnersCount;

	// klasa symuluj¹ca ca³¹ werstwê neuronów
	public LayerKohonen(int neuronsCount, int singleInputsCount, double minPotential) {
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

	public void LearnKohonen(double[] inputs, double step, double radius) {
		
		int winnerIndex;
		double[] result = new double[this.neuronsCount];
		for(int i = 0; i<this.neuronsCount;i++){result[i]=0;}

		for (int i = 0; i < neuronsCount; i++) {
			for (int k = 0; k < this.singleInputsCount; k++) {
				result[i] +=  (inputs[k] - this.weightsMatrix[i][k])*(inputs[k] - this.weightsMatrix[i][k]);
			}
			result[i] = Math.sqrt(result[i]);
			//System.out.println("Neuron " + i +" (" + this.weightsMatrix[i][0] + "," + this.weightsMatrix[i][1] + ") " +  " odpowiedŸ " + result[i]);
		}
		winnerIndex = getWinnerNumber(result);
		//System.out.println("Zwyciêzca : " + winnerIndex);
		modifyWeights(inputs,step,winnerIndex,radius);
		setPotential(winnerIndex);
		//preventDeadNeurons(step,winnerIndex,inputs);
	}
	
	
	public void modifyWeights(double[] inputs, double step, int winnerIndex,double radius) { 
		
		for (int i = 0; i < this.neuronsCount; i++)
		{
			if(i==winnerIndex) continue;
			for (int k = 0; k < this.singleInputsCount; k++) {	
				this.weightsMatrix[i][k] +=  step * neighborhoodFunction(winnerIndex,i,radius) * (inputs[k] - this.weightsMatrix[i][k]);	 
			}
		}
		// wagi zwyciêzcy s¹ aktualizowane na koñcu, tak by zasady modyfikacji by³y równe dla wszystkich pozosta³ych neuronów
		for (int k = 0; k < this.singleInputsCount; k++) {	
			this.weightsMatrix[winnerIndex][k] += step * neighborhoodFunction(winnerIndex,winnerIndex,radius) * (inputs[k] - this.weightsMatrix[winnerIndex][k]);	
		}
	}
	
	private double neighborhoodFunction(int winnerIndex, int otherIndex, double radius)
	{
		double temp = Math.exp((-1*distance(winnerIndex,otherIndex))/(2*radius*radius));
		return temp;
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
	
	private double distance(int indexNeuron1, int indexNeuron2)
	{
		double result = 0;
		for (int k = 0; k < this.singleInputsCount; k++) {
			result +=  (this.weightsMatrix[indexNeuron1][k] - this.weightsMatrix[indexNeuron2][k])*
					   (this.weightsMatrix[indexNeuron1][k] - this.weightsMatrix[indexNeuron2][k]);
		}
		return result;
	}
	
	private int getWinnerNumber(double[] data)
	{
		int winnerNumber = 0;
		for(int i =0; i< data.length;i++){
			
			if(this.potential[i]<this.minPotential) continue;
			
			if(data[i]<data[winnerNumber])
			{
				winnerNumber = i;
			}
		}
		this.winnersCount[winnerNumber]++;
		return winnerNumber;
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
				this.potential[i] += 1.0/ this.neuronsCount;
				//System.out.println(this.potential[i]);
				if(this.potential[i]>1.0) this.potential[i]=1.0;
			}	
		}	
	}
	
	
	private void preventDeadNeurons(double step, int winnerIndex,double[] inputs)
	{
		//System.out.println(getErrorAfterIteration(inputs));
		for(int i = 0; i< this.neuronsCount;i++){	
			if(this.winnersCount[i] <= 1)
			{
				for (int k = 0; k < this.singleInputsCount; k++) {	
					this.weightsMatrix[i][k] += step*0.02 * (this.weightsMatrix[winnerIndex][k] - this.weightsMatrix[i][k])  ;	
				}
			}
		}
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

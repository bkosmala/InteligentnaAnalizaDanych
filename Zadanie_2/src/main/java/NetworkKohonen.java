package main.java;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.jfree.data.xy.XYSeries;

public class NetworkKohonen {
	// sie� jednowarstwowa, jedna warstwa (poza wej�ciow�)
	private LayerKohonen layer;

	public NetworkKohonen(int neuronsPerLayer, int inputsPerNeuron, double minPotential) {
		//System.out.println("Warstwy ukryte - tworzenie.");
		this.layer = new LayerKohonen(neuronsPerLayer, inputsPerNeuron, minPotential);
	}

	public double  learnNetworkKohonen(double[][] inputs, XYSeries visualisationHandle, double initialStep, double initialRadious,int epoch, boolean testMode)
	{
		if(!testMode) visualise(visualisationHandle);
		
		int [][] order;
		double[][] randomInputs;
		
		double[] inputsToLayer ={};
		double[] result = { 0 };
		int counter = 0;
		double step;
		double radious;
		int maxEpochCount = epoch;
		double maxIteration = (double) (maxEpochCount*inputs.length);
		//if(!testMode) System.out.println(maxIteration);
		double epochError = 0;
		boolean shot = true;
			
		do
		{
			order = getRandomOrder(inputs);
			randomInputs = getRandomData(inputs,order);
			
			epochError = 0;
			//if(!testMode) System.out.println(counter/randomInputs.length);
			for (int k = 0; k < randomInputs.length; k++) // kolejne dane treningowe
			{
				radious = initialRadious * Math.exp(-1*((maxEpochCount*counter)/maxIteration)); // wyk�adnicze zmniejszanie promienia s�siedztwa
				//radious = initialRadious * ((maxIteration - counter)/maxIteration); // liniowe zmniejszanie wsp�czynnika
				step = initialStep * ((maxIteration - counter)/maxIteration); // liniowe zmniejszanie wsp�czynnika

				inputsToLayer = randomInputs[k];
				this.layer.LearnKohonen(inputsToLayer, step, radious);
				if(!testMode){
				/*try {
					TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {e.printStackTrace();}
					
					visualise(visualisationHandle);*/
				
				if((double)(counter/randomInputs.length)/(double)epoch > 0.5 && shot) {
					visualise(visualisationHandle);
					shot = false;}
				}
				//System.out.println((double)(counter/randomInputs.length)/(double)epoch);
				counter++;
			}
			epochError = this.layer.getErrorAfterEpoch(randomInputs);
			//if(!testMode) System.out.println("B��d dla epoki: " + epochError);
			if(!testMode) System.out.println(epochError);
			
		} 
		while(counter<maxIteration);
		visualise(visualisationHandle);
		return epochError;
	}
		
	public XYSeries visualise(XYSeries dataSet)
	{
		dataSet.clear();
		double[][] data = this.layer.getWeightsMatrix();
		
		for (int i = 0; i < data.length; i++) 
		{
			dataSet.add(data[i][0], data[i][1]);
		}
		return dataSet;
	}
	
	public int[][] getRandomOrder(double[][] inputs) 
	{
		int[][] order = new int[inputs.length][2];
		// wylosowana kolejno�� + powi�zane indeksy
		
		for(int k=0;k<inputs.length;k++)
		{
			order[k][0] = randomInt(0,1000);
			order[k][1] = k;
		}
		
		// sortowanie b�belkowowo tablicy kolejno�ci
		int temp1,temp2;
		for(int i=0;i<inputs.length;i++)
		for(int j=1;j<inputs.length-i;j++)
		{
		    if(order[j-1][0]>order[j][0])
		    {
		    	temp1=order[j-1][0];
		    	temp2=order[j-1][1];
		    	order[j-1][0]=order[j][0];
		    	order[j-1][1]=order[j][1];
		    	order[j][0]=temp1;
		    	order[j][1]=temp2;
		    }
		}
		return order;
	}
	
	public int removeDeadNeurons( double[][] inputs)
	{
		return this.layer.removeDeadNeurons(inputs);
	}
	
	public double[][] getRandomData(double[][] inputs, int[][] order) 
	{
		double[][] result = new double[inputs.length][inputs[0].length];		
		for(int k=0;k<inputs.length;k++)
		{
			for(int s=0; s<inputs[0].length;s++)
			{
			result[k][s]=inputs[order[k][1]][s];
			}
		}
		return result;
	}
	
	public static int randomInt(int min, int max) {
		int d = min + (int)(Math.random() * (max - min));
		return d;
	}
	
}

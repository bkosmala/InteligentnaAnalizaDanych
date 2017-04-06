package main.java;

import java.util.Arrays;

public class Network {// klasa b�d�ca modelem ca�ej sieci neuron�w (zawiera warstwy)

	private Layer[] layers;
	private Layer outputLayer;
	private int hiddenLayersCount; // hiddenLayersCount - liczba warstw neuron�w, u nas zwykle 1
	
	
	//Statistics
	public double[] errorHistory;

	

	public Network(int hiddenLayersCount, int[] neuronsPerLayer, int[] inputsPerNeuron, int outputsCount, boolean isBias) {
		// konstruowanie sieci
		this.hiddenLayersCount = hiddenLayersCount;

		if (neuronsPerLayer.length != hiddenLayersCount	|| inputsPerNeuron.length != hiddenLayersCount)
		{// sprawdzenie poprawno�ci danych
			System.out.println("Network - b��dne dane wej�ciowe");
			return;
		}
		
		//System.out.println("Warstwy ukryte - tworzenie.");
		this.layers = new Layer[hiddenLayersCount];
		for (int i = 0; i < hiddenLayersCount; i++) {
			this.layers[i] = new Layer(neuronsPerLayer[i], inputsPerNeuron[i],isBias);
		}
		//System.out.println("Warstwa wyj�ciowa - tworzenie.");
		this.outputLayer = new Layer(outputsCount, neuronsPerLayer[neuronsPerLayer.length - 1],isBias);
		// warstwa wyj�ciowa, ilo�� wej�� jest r�wna ilo�ci wyj�� poprzedniej warstwy ukrytej
	}

	public void learnNetwork(double[][] inputs, double[][] outputs, int epochCount, double minError, double step, double moment)
	{
		// inputs symuluje warstw� wej�ciow�, zak�adam �e sie� po��cze� jest
		// g�sta i dane z wej�cia trafiaj� do wszystkich neuron�w z warstwy
		// ilo�� wej�� w inputs = ilo�� wej�� ka�dego pojedynczego neuronu
		// uwzgl�dnia obliczanie b��du �rednokwadratowego
		// funkcja dla etapu uczenia sieci
		
		//Statistics
		this.errorHistory = new double[epochCount];
		
		double error = 0;
		double[] result = { 0 };
		double[] inputsToLayer ={};
		double[] outputsToLayer ={};
		double[][] randomInputs={};
		double[][] randomOutputs={};
		double licznik;
		int procent;
		
		
		for (int epoka = 1; epoka <= epochCount; epoka++) 
		{
			System.out.println("\nEpoka: " + epoka + " - Pocz�tek.\n");
			licznik = 0;
			int [][] order = getRandomOrder(inputs);
			randomInputs = getRandomData(inputs,order);
			randomOutputs = getRandomData(outputs,order);
			
			for (int k = 0; k < randomInputs.length; k++) // kolejne dane treningowe
			{			
				inputsToLayer = randomInputs[k];
				outputsToLayer = randomOutputs[k];
				
				
				for(int in=0; in<inputsToLayer.length;in++){  System.out.println("Wej�cie " + in + " warto�� " + inputsToLayer[in]);}
				
				for (int i = 0; i < this.layers.length; i++) // wynik dla warstwy
				{
					result = this.layers[i].Learn(inputsToLayer);
					for (int j = 0; j < result.length; j++)// dalej zb�dne
					{
						//System.out.println("Warstwa " + i + " neuron " + j + " rezultat " + result[j]);
					}
					inputsToLayer = result; // wynik warstwy poprzedniej to wej�cie dla warstwy kolejnej, w tym wypadku wyj�ciowej
				}
				result = this.outputLayer.Learn(inputsToLayer);
				
				for (int j = 0; j < result.length; j++)// dalej zb�dne
				{
					System.out.println("Warstwa wyj�ciowa, neuron " + j + " rezultat " + result[j]);
				}
				
				for(int s=0; s<result.length;s++)
				{//b��d �redniokwadratowy
					error+=(result[s] - outputsToLayer[s]) * (result[s] - outputsToLayer[s]);
					
					//obliczenie sygna�u zwrotnego dla wszystkich warstw - propagacja wsteczna b��du
					this.outputLayer.calculateBackError(outputsToLayer);
					
					// zmieni� w przypadku, gdy sie� b�dzie mia�a wi�cej warstw ukrytych
					this.layers[0].calculateBackError(this.outputLayer);
					
					//modyfikacja wag
					this.layers[0].modifyWeights(step, moment, randomInputs[k]);
					this.outputLayer.modifyWeights(step, moment, this.layers[0].savedNeuronsOutput);
				
					if (Math.abs((outputsToLayer[s] - result[s])) < 0.1)// testowo czy wynik jest poprawny
					{
						System.out.println("Wyj�cie "+s+" Odpowied� poprawna. (Poprawnie: "+outputsToLayer[s]+")");
						licznik = licznik + 1;
					} else {
						System.out.println("Wyj�cie "+s+" Odpowied� b��dna. (Poprawnie: "+outputsToLayer[s]+")");
					}
				}
				System.out.println("");
			}
			error = 0.5 * error;
			System.out.println("Epoka: " + epoka + " Koniec Testu. B��d sredniokwadratowy: " + error);
			this.errorHistory[epoka-1] = error;
			
			procent = (int)((licznik/(outputs.length*outputLayer.getNeuronsCount()))*100);
			System.out.println("\nKoniec epoki, procentowa poprawno�� dopasowania: " + procent + "%");
			
			if (error < minError) {
				System.out.println("\nOsi�gni�to zadan� dok�adno�� wyniku.");
				return;
			}
		}
		System.out.println("\nOsi�gni�to zadan� liczb� epok.");

	}

	public void getAnswer(double[][] inputs, double[][] outputs) {

		double error = 0;
		double[] result = { 0 };
		double[] inputsToLayer ={};
		double[] outputsToLayer ={};
		double licznik;
		int procent;
		
		
			System.out.println("START\n");
			licznik = 0;
			
			for (int k = 0; k < inputs.length; k++) // kolejne dane treningowe
			{			
				inputsToLayer = inputs[k];
				outputsToLayer = outputs[k];
				
				
				for(int in=0; in<inputsToLayer.length;in++){  System.out.println("Wej�cie " + in + " warto�� " + inputsToLayer[in]);}
				
				for (int i = 0; i < this.layers.length; i++) // wynik dla warstwy
				{
					result = this.layers[i].getOutput(inputsToLayer);
					inputsToLayer = result; // wynik warstwy poprzedniej to wej�cie dla warstwy kolejnej, w tym wypadku wyj�ciowej
				}
				result = this.outputLayer.getOutput(inputsToLayer);
				
				for(int s=0; s<result.length;s++)
				{//b��d �redniokwadratowy
					error+=(result[s] - outputsToLayer[s]) * (result[s] - outputsToLayer[s]);
									
					if (Math.abs((outputsToLayer[s] - result[s])) < 0.1)// testowo czy wynik jest poprawny
					{
						System.out.println("Wyj�cie "+s+" Odpowied� poprawna. (Poprawnie: "+outputsToLayer[s]+")");
						licznik = licznik + 1;
					} else {
						System.out.println("Wyj�cie "+s+" Odpowied� b��dna. (Poprawnie: "+outputsToLayer[s]+")");
					}
				}
				System.out.println("");
			}
			error = 0.5 * error;
			System.out.println("Koniec. B��d sredniokwadratowy: " + error);
			
			procent = (int)((licznik/(outputs.length*outputLayer.getNeuronsCount()))*100);
			System.out.println("\nKoniec epoki, procentowa poprawno�� dopasowania: " + procent + "%");		
		
	}
	
	public int[][] getRandomOrder(double[][] inputs) 
	{
		int[][] order = new int[inputs.length][2];
		
		// wylosowana kolejno�� + powi�zane indeksy
		
		for(int k=0;k<inputs.length;k++)
		{
			order[k][0] = randomInt(0,1000);
			order[k][1] = k;
			//System.out.println(order[k][0] + " indeks: " + k);
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
		
		/*
		for(int k=0;k<inputs.length;k++)
		{
			System.out.println(order[k][0] + " indeks: " + order[k][1]);
		}
		
		System.out.println("");*/
		 
		
		return order;
	}
	
	public double[][] getRandomData(double[][] inputs, int[][] order) 
	{
		double[][] result = new double[inputs.length][inputs[0].length];
		
		/*
		for(int k=0;k<inputs.length;k++)
		{	
			System.out.println();
			for(int s=0; s<inputs[0].length;s++)
			{
			System.out.print(inputs[k][s] + " ");
			}
		}*/
		
		for(int k=0;k<inputs.length;k++)
		{
			for(int s=0; s<inputs[0].length;s++)
			{
			result[k][s]=inputs[order[k][1]][s];
			}
		}
		/*
		System.out.println("");
		
		for(int k=0;k<inputs.length;k++)
		{	
			System.out.println();
			for(int s=0; s<inputs[0].length;s++)
			{
			System.out.print(result[k][s] + " ");
			}
		}*/
		
		return result;
	}
	
	public static int randomInt(int min, int max) {
		int d = min + (int)(Math.random() * (max - min));
		return d;
	}
	
	public double[] getErrorHistory()
	{
		System.out.println("");
		for(int k=0;k<this.errorHistory.length;k++)
		{	
			System.out.println(this.errorHistory[k]);
		}
		return this.errorHistory;
	}
	
}

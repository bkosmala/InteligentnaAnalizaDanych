package main.java;

import java.util.Arrays;

public class Network {// klasa bêd¹ca modelem ca³ej sieci neuronów (zawiera warstwy)

	private Layer[] layers;
	private Layer outputLayer;
	private int hiddenLayersCount; // hiddenLayersCount - liczba warstw neuronów, u nas zwykle 1
	

	public Network(int hiddenLayersCount, int[] neuronsPerLayer, int[] inputsPerNeuron, int outputsCount) {
		// konstruowanie sieci
		this.hiddenLayersCount = hiddenLayersCount;

		if (neuronsPerLayer.length != hiddenLayersCount	|| inputsPerNeuron.length != hiddenLayersCount)
		{// sprawdzenie poprawnoœci danych
			System.out.println("Network - b³êdne dane wejœciowe");
			return;
		}
		
		//System.out.println("Warstwy ukryte - tworzenie.");
		this.layers = new Layer[hiddenLayersCount];
		for (int i = 0; i < hiddenLayersCount; i++) {
			this.layers[i] = new Layer(neuronsPerLayer[i], inputsPerNeuron[i]);
		}
		//System.out.println("Warstwa wyjœciowa - tworzenie.");
		this.outputLayer = new Layer(outputsCount, neuronsPerLayer[neuronsPerLayer.length - 1]);
		// warstwa wyjœciowa, iloœæ wejœæ jest równa iloœci wyjœæ poprzedniej warstwy ukrytej
	}

	public void learnNetwork(double[][] inputs, double[] outputs, int epochCount, double minError, double step) {
		// inputs symuluje warstwê wejœciow¹, zak³adam ¿e sieæ po³¹czeñ jest
		// gêsta i dane z wejœcia trafiaj¹ do wszystkich neuronów z warstwy
		// iloœæ wejœæ w inputs = iloœæ wejœæ ka¿dego pojedynczego neuronu
		// uwzglêdnia obliczanie b³êdu œrednokwadratowego
		// funkcja dla etapu uczenia sieci

		double error = 0;
		double singleCaseError = 0;
		double[] result = { 0 };
		double[] inputsToLayer ={};
		double[][] randomInputs={};
		double licznik;
		int procent;
		
		
		for (int epoka = 1; epoka <= epochCount; epoka++) 
		{
			System.out.println("\nEpoka: " + epoka + " - Pocz¹tek.\n");
			licznik = 0;
			randomInputs = getRandomInputs(inputs);
			for (int k = 0; k < randomInputs.length; k++) // kolejne dane treningowe
			{
				singleCaseError=0;				
				inputsToLayer = randomInputs[k];
				
				for(int in=0; in<inputsToLayer.length;in++){  System.out.println("Wejœcie " + in + " wartoœæ " + inputsToLayer[in]);}
				
				for (int i = 0; i < this.layers.length; i++) // wynik dla warstwy
				{
					result = this.layers[i].Learn(inputsToLayer);
					for (int j = 0; j < result.length; j++)// dalej zbêdne
					{
						//System.out.println("Warstwa " + i + " neuron " + j + " rezultat " + result[j]);
					}
					inputsToLayer = result; // wynik warstwy poprzedniej to wejœcie dla warstwy kolejnej, w tym wypadku wyjœciowej
				}
				result = this.outputLayer.Learn(inputsToLayer);
				
				for (int j = 0; j < result.length; j++)// dalej zbêdne
				{
					System.out.println("Warstwa wyjœciowa, neuron " + j + " rezultat " + result[j]);
				}
				
				double[] backError = new double[result.length];
				for(int s=0; s<result.length;s++)
				{//b³¹d œredniokwadratowy
					singleCaseError+=(result[s] - outputs[s]) * (result[s] - outputs[s]);
					error += singleCaseError;
					
					//obliczenie sygna³u zwrotnego dla wszystkich warstw - propagacja wsteczna b³êdu
					this.outputLayer.calculateBackError(outputs);
					
					// tymczasowe - zmieniæ w przypadku, gdy sieæ bêdzie mia³a wiêcej warstw ukrytych
					this.layers[0].calculateBackError(this.outputLayer);
					
					//modyfikacja wag
					this.layers[0].modifyWeights(step, randomInputs[k]);
					this.outputLayer.modifyWeights(step, this.layers[0].savedNeuronsOutput);
				
					if (Math.abs((outputs[s] - result[s])) < 0.1)// testowo czy wynik jest poprawny
					{
						System.out.println("OdpowiedŸ poprawna.\n");
						licznik = licznik + 1;
					} else {
						System.out.println("OdpowiedŸ b³êdna. (Poprawnie: "+outputs[s]+")\n");
					}
				}
				singleCaseError=0.5*singleCaseError;
			}
			error = 0.5 * error;
			System.out.println("Epoka: " + epoka + " Koniec Testu. B³¹d sredniokwadratowy: " + error);
			
			procent = (int)((licznik/outputs.length)*100);
			System.out.println("\nKoniec epoki, procentowa poprawnoœæ dopasowania: " + procent + "%");
			
			if (error < minError) {
				System.out.println("\nOsi¹gniêto zadan¹ dok³adnoœæ wyniku.");
				return;
			}
		}
		System.out.println("\nOsi¹gniêto zadan¹ liczbê epok.");

	}

	public void getAnswer(double[][] inputs) {
		// inputs symuluje warstwê wejœciow¹, zak³adam ¿e sieæ po³¹czeñ jest
		// gêsta i dane z wejœcia trafiaj¹ do wszystkich neuronów z warstwy
		// iloœæ wejœæ w inputs = iloœæ wejœæ ka¿dego pojedynczego neuronu
		// funkcja dla sieci ju¿ nauczonej

		// TODO - poprawiæ - wersja tymczasowa, tylko dla jednego neuronu w jednej warstwie

		double[] result = { 0 };

		for (int k = 0; k < inputs.length; k++) {
			for (int i = 0; i < this.layers.length; i++) {
				result = this.layers[i].getOutput(inputs[k]);
				for (int j = 0; j < result.length; j++) {
					System.out.println("Warstwa " + i + " neuron " + j + " rezultat " + result[j]);
				}
			}
		}
	}
	
	public double[][] getRandomInputs(double[][] inputs) {
		
		int[][] order = new int[inputs.length][2];
		double[][] result = new double[inputs.length][inputs[0].length];
		// wylosowana kolejnoœæ + powi¹zane indeksy
		
		for(int k=0;k<inputs.length;k++)
		{
			order[k][0] = randomInt(0,1000);
			order[k][1] = k;
			//System.out.println(order[k][0] + " indeks: " + k);
		}
		
		// sortowanie b¹belkowowo tablicy kolejnoœci
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
		System.out.println("");
		for(int k=0;k<inputs.length;k++)
		{
			System.out.println(order[k][0] + " indeks: " + order[k][1]);
		}
		
		System.out.println("");
		
		for(int k=0;k<inputs.length;k++)
		{
			System.out.println(inputs[k][0] + " indeks: " + inputs[k][1]);
		}*/
		
		for(int k=0;k<inputs.length;k++)
		{
			for(int s=0; s<inputs[0].length;s++)
			{
			result[k][s]=inputs[order[k][1]][s];
			}
		}
		
		/*System.out.println("");
		
		for(int k=0;k<inputs.length;k++)
		{
			System.out.println(result[k][0] + " indeks: " + result[k][1]);
		}*/
		
		return result;
	}
	
	public static int randomInt(int min, int max) {
		int d = min + (int)(Math.random() * (max - min));
		return d;
	}
	
}

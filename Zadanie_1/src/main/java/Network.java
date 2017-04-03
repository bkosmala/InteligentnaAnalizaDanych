package main.java;

public class Network {
//klasa bêd¹ca modelem ca³ej sieci neuronów (zawiera warstwy)
	
	Layer[] layers;
	public Network(int hiddenLayersCount, int[] neuronsPerLayer, int[] inputsPerNeuron)
	{
		//konstruowanie sieci
		//hiddenLayersCount - liczba warstw neuronów, u nas zwykle 1
		
		//sprawdzenie poprawnoœci danych
		if(neuronsPerLayer.length != hiddenLayersCount || inputsPerNeuron.length != hiddenLayersCount)
		{
			System.out.println("Network - b³êdne dane wejœciowe");
			return;
		}
		
		this.layers = new Layer[hiddenLayersCount];
		for(int i = 0; i<hiddenLayersCount;i++)
		{
			this.layers[i] = new Layer(neuronsPerLayer[i],inputsPerNeuron[i]);
		}	
	}
	
	public void learnNetwork(double[][] inputs, double[] outputs, int epochCount, double minError)
	{
		//  inputs symuluje warstwê wejœciow¹, zak³adam ¿e sieæ po³¹czeñ jest gêsta i dane z wejœcia trafiaj¹ do wszystkich neuronów z warstwy 
		//  iloœæ wejœæ w inputs = iloœæ wejœæ ka¿dego pojedynczego neuronu
		//  uwzglêdnia obliczanie b³êdu œrednokwadratowego
		//  funkcja dla etapu uczenia sieci
		
		// TODO - poprawiæ - wersja tymczasowa, tylko dla jednego neuronu w jednej warstwie
		
		double error = 0;
		double[] result = {0};
		
		for(int epoka=1; epoka<=epochCount; epoka++)
		{
		
		for(int k=0; k<inputs.length;k++) // kolejne dane treningowe
		{
		for(int i = 0; i<this.layers.length;i++) //wynik dla warstwy
		{
			result = this.layers[i].Learn(inputs[k]);
			
			for(int j = 0; j<result.length;j++)// dalej zbêdne
			{
				System.out.println("Warstwa " + i + " neuron " + j + " rezultat " + result[j]);
			}		
		}	
		//tymczasowe, sprawdziæ dalej
		error += (result[0]-outputs[0])*(result[0]-outputs[0]);
		
		if((outputs[0]-result[0])<0.1)// testowo, dla okreœlenia poprawnoœci wyniku
		{
			System.out.println("OdpowiedŸ poprawna.\n");
		}
		else
		{
			System.out.println("OdpowiedŸ b³êdna.\n");
		}
		}
		error = 0.5 * error;
		
		System.out.println("\nEpoka: " + epoka + " Koniec Testu. B³¹d sredniokwadratowy: " + error);
		
		if(error<minError)
		{
			System.out.println("\nOsi¹gniêto zadan¹ dok³adnoœæ wyniku.");
			return;
		}
		
		}
		System.out.println("\nOsi¹gniêto zadan¹ liczbê epok.");
		
	}
	
	public void getAnswer(double[][] inputs)
	{
		//  inputs symuluje warstwê wejœciow¹, zak³adam ¿e sieæ po³¹czeñ jest gêsta i dane z wejœcia trafiaj¹ do wszystkich neuronów z warstwy 
		//  iloœæ wejœæ w inputs = iloœæ wejœæ ka¿dego pojedynczego neuronu
		// funkcja dla sieci ju¿ nauczonej
		
		// TODO - poprawiæ - wersja tymczasowa, tylko dla jednego neuronu w jednej warstwie
		
		double[] result = {0};
		
		for(int k=0; k<inputs.length;k++)
		{
		for(int i = 0; i<this.layers.length;i++)
		{
			result = this.layers[i].getOutput(inputs[k]);
			
			for(int j = 0; j<result.length;j++)
			{
				System.out.println("Warstwa " + i + " neuron " + j + " rezultat " + result[j]);
			}		
		}	
		}
	}
}

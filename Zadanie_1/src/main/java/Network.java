package main.java;

public class Network {
	// klasa bêd¹ca modelem ca³ej sieci neuronów (zawiera warstwy)

	Layer[] layers;
	Layer outputLayer;

	public Network(int hiddenLayersCount, int[] neuronsPerLayer, int[] inputsPerNeuron, int outputsCount) {
		// konstruowanie sieci
		// hiddenLayersCount - liczba warstw neuronów, u nas zwykle 1

		// sprawdzenie poprawnoœci danych
		if (neuronsPerLayer.length != hiddenLayersCount	|| inputsPerNeuron.length != hiddenLayersCount) {
			System.out.println("Network - b³êdne dane wejœciowe");
			return;
		}
		
		System.out.println("Warstwy ukryte - tworzenie.");
		this.layers = new Layer[hiddenLayersCount];
		for (int i = 0; i < hiddenLayersCount; i++) {
			this.layers[i] = new Layer(neuronsPerLayer[i], inputsPerNeuron[i]);
		}
		System.out.println("Warstwa wyjœciowa - tworzenie.");
		// warstwa wyjœciowa, iloœæ wejœæ jest równa iloœci wyjœæ poprzedniej warstwy ukrytej
		this.outputLayer = new Layer(outputsCount, neuronsPerLayer[neuronsPerLayer.length - 1]);
	}

	public void learnNetwork(double[][] inputs, double[] outputs, int epochCount, double minError) {
		// inputs symuluje warstwê wejœciow¹, zak³adam ¿e sieæ po³¹czeñ jest
		// gêsta i dane z wejœcia trafiaj¹ do wszystkich neuronów z warstwy
		// iloœæ wejœæ w inputs = iloœæ wejœæ ka¿dego pojedynczego neuronu
		// uwzglêdnia obliczanie b³êdu œrednokwadratowego
		// funkcja dla etapu uczenia sieci

		// TODO - poprawiæ - wersja tymczasowa, tylko dla jednego neuronu w jednej warstwie

		double error = 0;
		double[] result = { 0 };
		double[] inputsToLayer ={};
		double licznik;
		int procent;

		for (int epoka = 1; epoka <= epochCount; epoka++) 
		{
			licznik = 0;
			for (int k = 0; k < inputs.length; k++) // kolejne dane treningowe
			{
				
				inputsToLayer = inputs[k];
				for (int i = 0; i < this.layers.length; i++) // wynik dla warstwy
				{
					result = this.layers[i].Learn(inputsToLayer);
					for (int j = 0; j < result.length; j++)// dalej zbêdne
					{
						System.out.println("Warstwa " + i + " neuron " + j + " rezultat " + result[j]);
					}
					inputsToLayer = result; // wynik warstwy poprzedniej to wejœcie dla warstwy kolejnej, w tym wypadku wyjœciowej
				}
				System.out.println("Warstwa wyjœciowa.");
				result = this.outputLayer.Learn(inputsToLayer);
				
				for(int s=0; s<result.length;s++)
				{//b³¹d œredniokwadratowy
				error += (result[s] - outputs[s]) * (result[s] - outputs[s]);
				
				if (Math.abs((outputs[s] - result[s])) < 0.1)// testowo czy wynik jest poprawny
				{
					System.out.println("OdpowiedŸ poprawna.\n");
					licznik = licznik + 1;
				} else {
					System.out.println("OdpowiedŸ b³êdna.\n");
				}
				}
			}
			error = 0.5 * error;
			System.out.println("\nEpoka: " + epoka + " Koniec Testu. B³¹d sredniokwadratowy: " + error);
			
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
}

package main.java;

public class Network {
	// klasa b�d�ca modelem ca�ej sieci neuron�w (zawiera warstwy)

	Layer[] layers;
	Layer outputLayer;

	public Network(int hiddenLayersCount, int[] neuronsPerLayer, int[] inputsPerNeuron, int outputsCount) {
		// konstruowanie sieci
		// hiddenLayersCount - liczba warstw neuron�w, u nas zwykle 1

		// sprawdzenie poprawno�ci danych
		if (neuronsPerLayer.length != hiddenLayersCount	|| inputsPerNeuron.length != hiddenLayersCount) {
			System.out.println("Network - b��dne dane wej�ciowe");
			return;
		}
		
		System.out.println("Warstwy ukryte - tworzenie.");
		this.layers = new Layer[hiddenLayersCount];
		for (int i = 0; i < hiddenLayersCount; i++) {
			this.layers[i] = new Layer(neuronsPerLayer[i], inputsPerNeuron[i]);
		}
		System.out.println("Warstwa wyj�ciowa - tworzenie.");
		// warstwa wyj�ciowa, ilo�� wej�� jest r�wna ilo�ci wyj�� poprzedniej warstwy ukrytej
		this.outputLayer = new Layer(outputsCount, neuronsPerLayer[neuronsPerLayer.length - 1]);
	}

	public void learnNetwork(double[][] inputs, double[] outputs, int epochCount, double minError) {
		// inputs symuluje warstw� wej�ciow�, zak�adam �e sie� po��cze� jest
		// g�sta i dane z wej�cia trafiaj� do wszystkich neuron�w z warstwy
		// ilo�� wej�� w inputs = ilo�� wej�� ka�dego pojedynczego neuronu
		// uwzgl�dnia obliczanie b��du �rednokwadratowego
		// funkcja dla etapu uczenia sieci

		// TODO - poprawi� - wersja tymczasowa, tylko dla jednego neuronu w jednej warstwie

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
					for (int j = 0; j < result.length; j++)// dalej zb�dne
					{
						System.out.println("Warstwa " + i + " neuron " + j + " rezultat " + result[j]);
					}
					inputsToLayer = result; // wynik warstwy poprzedniej to wej�cie dla warstwy kolejnej, w tym wypadku wyj�ciowej
				}
				System.out.println("Warstwa wyj�ciowa.");
				result = this.outputLayer.Learn(inputsToLayer);
				
				for(int s=0; s<result.length;s++)
				{//b��d �redniokwadratowy
				error += (result[s] - outputs[s]) * (result[s] - outputs[s]);
				
				if (Math.abs((outputs[s] - result[s])) < 0.1)// testowo czy wynik jest poprawny
				{
					System.out.println("Odpowied� poprawna.\n");
					licznik = licznik + 1;
				} else {
					System.out.println("Odpowied� b��dna.\n");
				}
				}
			}
			error = 0.5 * error;
			System.out.println("\nEpoka: " + epoka + " Koniec Testu. B��d sredniokwadratowy: " + error);
			
			procent = (int)((licznik/outputs.length)*100);
			System.out.println("\nKoniec epoki, procentowa poprawno�� dopasowania: " + procent + "%");
			
			if (error < minError) {
				System.out.println("\nOsi�gni�to zadan� dok�adno�� wyniku.");
				return;
			}
		}
		System.out.println("\nOsi�gni�to zadan� liczb� epok.");

	}

	public void getAnswer(double[][] inputs) {
		// inputs symuluje warstw� wej�ciow�, zak�adam �e sie� po��cze� jest
		// g�sta i dane z wej�cia trafiaj� do wszystkich neuron�w z warstwy
		// ilo�� wej�� w inputs = ilo�� wej�� ka�dego pojedynczego neuronu
		// funkcja dla sieci ju� nauczonej

		// TODO - poprawi� - wersja tymczasowa, tylko dla jednego neuronu w jednej warstwie

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

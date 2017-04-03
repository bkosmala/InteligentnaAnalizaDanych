package main.java;

public class Network {
//klasa b�d�ca modelem ca�ej sieci neuron�w (zawiera warstwy)
	
	Layer[] layers;
	public Network(int hiddenLayersCount, int[] neuronsPerLayer, int[] inputsPerNeuron)
	{
		//konstruowanie sieci
		//hiddenLayersCount - liczba warstw neuron�w, u nas zwykle 1
		
		//sprawdzenie poprawno�ci danych
		if(neuronsPerLayer.length != hiddenLayersCount || inputsPerNeuron.length != hiddenLayersCount)
		{
			System.out.println("Network - b��dne dane wej�ciowe");
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
		//  inputs symuluje warstw� wej�ciow�, zak�adam �e sie� po��cze� jest g�sta i dane z wej�cia trafiaj� do wszystkich neuron�w z warstwy 
		//  ilo�� wej�� w inputs = ilo�� wej�� ka�dego pojedynczego neuronu
		//  uwzgl�dnia obliczanie b��du �rednokwadratowego
		//  funkcja dla etapu uczenia sieci
		
		// TODO - poprawi� - wersja tymczasowa, tylko dla jednego neuronu w jednej warstwie
		
		double error = 0;
		double[] result = {0};
		
		for(int epoka=1; epoka<=epochCount; epoka++)
		{
		
		for(int k=0; k<inputs.length;k++) // kolejne dane treningowe
		{
		for(int i = 0; i<this.layers.length;i++) //wynik dla warstwy
		{
			result = this.layers[i].Learn(inputs[k]);
			
			for(int j = 0; j<result.length;j++)// dalej zb�dne
			{
				System.out.println("Warstwa " + i + " neuron " + j + " rezultat " + result[j]);
			}		
		}	
		//tymczasowe, sprawdzi� dalej
		error += (result[0]-outputs[0])*(result[0]-outputs[0]);
		
		if((outputs[0]-result[0])<0.1)// testowo, dla okre�lenia poprawno�ci wyniku
		{
			System.out.println("Odpowied� poprawna.\n");
		}
		else
		{
			System.out.println("Odpowied� b��dna.\n");
		}
		}
		error = 0.5 * error;
		
		System.out.println("\nEpoka: " + epoka + " Koniec Testu. B��d sredniokwadratowy: " + error);
		
		if(error<minError)
		{
			System.out.println("\nOsi�gni�to zadan� dok�adno�� wyniku.");
			return;
		}
		
		}
		System.out.println("\nOsi�gni�to zadan� liczb� epok.");
		
	}
	
	public void getAnswer(double[][] inputs)
	{
		//  inputs symuluje warstw� wej�ciow�, zak�adam �e sie� po��cze� jest g�sta i dane z wej�cia trafiaj� do wszystkich neuron�w z warstwy 
		//  ilo�� wej�� w inputs = ilo�� wej�� ka�dego pojedynczego neuronu
		// funkcja dla sieci ju� nauczonej
		
		// TODO - poprawi� - wersja tymczasowa, tylko dla jednego neuronu w jednej warstwie
		
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

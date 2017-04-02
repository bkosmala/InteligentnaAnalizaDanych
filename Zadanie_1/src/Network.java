
public class Network {
//klasa bêd¹ca modelem ca³ej sieci neuronów (zawiera warstwy)
	
	Layer[] layers;
	public Network(int hiddenLayersCount, int[] neuronsPerLayer, int[] inputsPerNeuron)
	{
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
	
	public void getAnswer(double[] inputs)
	{
		//  inputs symuluje warstwê wejœciow¹, zak³adam ¿e sieæ po³¹czeñ jest gêsta i dane z wejœcia trafiaj¹ do wszystkich neuronów z warstwy 
		//  iloœæ wejœæ w inputs = iloœæ wejœæ ka¿dego pojedynczego neuronu
		
		// TODO - poprawiæ - wersja tymczasowa, tylko dla jednego neuronu w jednej warstwie
		
		for(int i = 0; i<this.layers.length;i++)
		{
			double[] result = this.layers[i].getOutput(inputs);
			for(int j = 0; j<result.length;j++)
			{
				System.out.println("Warstwa " + i + " neuron " + j + " rezultat " + result[j]);
			}
			
		}
		
	}
}

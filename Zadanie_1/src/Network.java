
public class Network {
//klasa b�d�ca modelem ca�ej sieci neuron�w (zawiera warstwy)
	
	Layer[] layers;
	public Network(int hiddenLayersCount, int[] neuronsPerLayer, int[] inputsPerNeuron)
	{
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
	
	public void getAnswer(double[] inputs)
	{
		//  inputs symuluje warstw� wej�ciow�, zak�adam �e sie� po��cze� jest g�sta i dane z wej�cia trafiaj� do wszystkich neuron�w z warstwy 
		//  ilo�� wej�� w inputs = ilo�� wej�� ka�dego pojedynczego neuronu
		
		// TODO - poprawi� - wersja tymczasowa, tylko dla jednego neuronu w jednej warstwie
		
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

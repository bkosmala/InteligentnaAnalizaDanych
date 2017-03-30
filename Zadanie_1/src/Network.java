
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
}

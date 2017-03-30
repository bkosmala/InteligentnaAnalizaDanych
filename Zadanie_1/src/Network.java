
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
}

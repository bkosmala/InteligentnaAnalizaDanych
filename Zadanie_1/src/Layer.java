
public class Layer {
	
	private int singleInputsCount;
	private int neuronsCount;
	private double[][] weightsMatrix;
	
	// klasa symuluj�ca ca�� werstw� neuron�w
	public Layer(int neuronsCount,int singleInputsCount)
	{
		//singleInputsCount - ilo�� wej�� dla pojedynczego neuronu, czyli r�wnie� ilo�� wag
		//                    + opr�cz tego dochodzi ewentualnie bias
		//neuronsCount      - ilo�� neuron�w w warstwie
		
		// w klasie tej operujemy na wektorach i macierzach
		// Je�li potraktujemy wej�cie X oraz wyj�cie Y jako wektory liczb rzeczywistych, 
		// to warstwa neuron�w zachowuje si� jako macierz N
		// i mamy r�wnanie Y = N * X
		// mno�enie macie�y wymaga zgodno�ci wymiar�w tzn macie� o wymiarach [n,m] * [m,p] => [n,p]
		// u nas n - to ilo�� neuron�w w warstwie, m - ilo�� wag, a p jest zawsze r�wne 1 (na wyj�ciu otrzymujemy wektor o wymiarach [n,1] )
		
		this.singleInputsCount = singleInputsCount;
		this.neuronsCount = neuronsCount;
		
		this.weightsMatrix = new double[neuronsCount][singleInputsCount];
		//inicjalizacja wag liczbami losowymi
		
		for(int i = 0; i<neuronsCount;i++)
		{
			for(int k = 0; k<singleInputsCount;k++)
			{
				this.weightsMatrix[i][k] = randomNumber(0,1);
			}
		}
		
	}
	
	public void Learn(double[] inputs)
	{
		
		
	}
	
    private double[] getOutput(double[] inputs)
    {
    	// realizacja dzia�ania warstwy: mno�enie macierzy zgodnie z r�wnaniem Y = N * X
    	
    	double[] result = new double[this.neuronsCount];
    	
		for(int i = 0; i<neuronsCount;i++)
		{
			for(int k = 0; k<singleInputsCount;k++)
			{
				result[i] += this.weightsMatrix[i][k] * inputs[k];
			}
		}
    	   	
    	return result;
    	
    }
    
    public static double randomNumber(double min, double max){
        double d = min+Math.random()*(max-min);
        return d;
    }
}

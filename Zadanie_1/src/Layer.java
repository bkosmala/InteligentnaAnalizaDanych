
public class Layer {
	
	private int singleInputsCount;
	private int neuronsCount;
	private double[][] weightsMatrix;
	
	// klasa symuluj¹ca ca³¹ werstwê neuronów
	public Layer(int neuronsCount,int singleInputsCount)
	{
		//singleInputsCount - iloœæ wejœæ dla pojedynczego neuronu, czyli równie¿ iloœæ wag
		//                    + oprócz tego dochodzi ewentualnie bias
		//neuronsCount      - iloœæ neuronów w warstwie
		
		// w klasie tej operujemy na wektorach i macierzach
		// Jeœli potraktujemy wejœcie X oraz wyjœcie Y jako wektory liczb rzeczywistych, 
		// to warstwa neuronów zachowuje siê jako macierz N
		// i mamy równanie Y = N * X
		// mno¿enie macie¿y wymaga zgodnoœci wymiarów tzn macie¿ o wymiarach [n,m] * [m,p] => [n,p]
		// u nas n - to iloœæ neuronów w warstwie, m - iloœæ wag, a p jest zawsze równe 1 (na wyjœciu otrzymujemy wektor o wymiarach [n,1] )
		
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
    	// realizacja dzia³ania warstwy: mno¿enie macierzy zgodnie z równaniem Y = N * X
    	
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

import java.lang.*;

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
				//System.out.println("\nNeuron: "+ i +" Waga " + k + " Wartoœæ: " + this.weightsMatrix[i][k]);
			}
		}
				
	}
	
	public double[] Learn(double[] inputs)
	{
		// TODO - algorytm nauki gradientowy, dodaæ
		
    	// realizacja dzia³ania warstwy: mno¿enie macierzy zgodnie z równaniem Y = N * X
    	
    	double[] result = new double[this.neuronsCount];
    	
		for(int i = 0; i<neuronsCount;i++)
		{
			for(int k = 0; k<singleInputsCount;k++)
			{
				System.out.println("Wejœcie " + k + " wartoœæ " + inputs[k]);
				result[i] += this.weightsMatrix[i][k] * inputs[k];
			}
			result[i] = sigmoidActivation(1,result[i]);
			System.out.println("Neuron " + i + " odpowiedŸ " + result[i]);
		}
    	return result;
		
	}
	
    public double[] getOutput(double[] inputs)
    {
    	// realizacja dzia³ania warstwy: mno¿enie macierzy zgodnie z równaniem Y = N * X
    	
    	double[] result = new double[this.neuronsCount];
    	
		for(int i = 0; i<neuronsCount;i++)
		{
			for(int k = 0; k<singleInputsCount;k++)
			{
				System.out.println("Wejœcie " + k + " wartoœæ " + inputs[k]);
				result[i] += this.weightsMatrix[i][k] * inputs[k];
			}
			result[i] = sigmoidActivation(1,result[i]);
			System.out.println("Neuron " + i + " odpowiedŸ " + result[i]);
		}
    	return result;
    }
    
    public static double randomNumber(double min, double max){
        double d = min+Math.random()*(max-min);
        return d;
    }
    
    public static double sigmoidActivation(double beta, double x)
    {
    	// sigmoidalna funkcja aktywacji, postaci f(s)=1/(1+exp(bs))
    	// x -argument
    	// beta - wspó³czynnik, gdy d¹¿y do nieskonczonoœæi funkcja zmienia siê w funkcjê skokow¹.
    	// domyœlnie beta=1
    	
    	return 1.0/(1.0 + Math.exp(-1*beta*x));	
    }
}

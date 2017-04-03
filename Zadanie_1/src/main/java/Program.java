package main.java;

public class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int epoka = 100;
		double krok = 0.1;
		System.out.println("Test dla pojedynczego neuronu - p.d. z zaj��");
		int[] neuronsCount = {1};
		int[] inputsCount = {2};
		Network network1 = new Network(1 ,neuronsCount,inputsCount);
		
		double[][] inputs = {{3,4},{1,4},{2,4},{1,5},{2,5},{2,3},
				 {4,-1},{5,-1},{4,-2},{5,-2},{6,-2},{5,-3},{6,-3}};
		double outputs[] = {1,1,1,1,1,1,0,0,0,0,0,0,0};
		
		// warunki stopu algorytmu uczenia: liczba epok lub dok�adno�� wyniku (b��d �redniokwadratowy poni�ej jaiej� warto�ci)
		int epochCount = 10;
		double minError = 0.05;
		network1.learnNetwork(inputs, outputs, epochCount, minError);
		//network1.getAnswer(inputs);
	}
	//TODO - doda� bias
	//TODO - zaimplementwa� metod� uczenia: wstecznej propagacji b��du z gradientem
	//TODO - wczytywanie danych z pliku (pliki txt podane w tre�ci zadania)

}

package main.java;

public class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int epoka = 100;
		double krok = 0.1;
		System.out.println("Test dla pojedynczego neuronu - p.d. z zajêæ");
		int[] neuronsCount = {1};
		int[] inputsCount = {2};
		Network network1 = new Network(1 ,neuronsCount,inputsCount);
		
		double[][] inputs = {{3,4},{1,4},{2,4},{1,5},{2,5},{2,3},
				 {4,-1},{5,-1},{4,-2},{5,-2},{6,-2},{5,-3},{6,-3}};
		double outputs[] = {1,1,1,1,1,1,0,0,0,0,0,0,0};
		
		// warunki stopu algorytmu uczenia: liczba epok lub dok³adnoœæ wyniku (b³¹d œredniokwadratowy poni¿ej jaiejœ wartoœci)
		int epochCount = 10;
		double minError = 0.05;
		network1.learnNetwork(inputs, outputs, epochCount, minError);
		//network1.getAnswer(inputs);
	}
	//TODO - dodaæ bias
	//TODO - zaimplementwaæ metodê uczenia: wstecznej propagacji b³êdu z gradientem
	//TODO - wczytywanie danych z pliku (pliki txt podane w treœci zadania)

}

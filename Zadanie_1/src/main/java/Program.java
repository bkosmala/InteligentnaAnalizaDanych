package main.java;

public class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Test dla pojedynczego neuronu.");
		int[] neuronsCount = {1};
		int[] inputsCount = {2};
		int outputCount = 1;
		Network network1 = new Network(1 ,neuronsCount,inputsCount,outputCount);
		
		double[][] inputs = {{3,4},{1,4},{2,4},{1,5},{2,5},{2,3},
				 {4,-1},{5,-1},{4,-2},{5,-2},{6,-2},{5,-3},{6,-3}};
		double outputs[] = {1,1,1,1,1,1,0,0,0,0,0,0,0};
		
		// warunki stopu algorytmu uczenia: liczba epok lub dok³adnoœæ wyniku (b³¹d œredniokwadratowy poni¿ej jaiejœ wartoœci)
		int epochCount = 100;
		double step = 0.7;  //wspó³czynnik nauki
		double minError = 0.1;
		// tryb nauki
		network1.learnNetwork(inputs, outputs, epochCount, minError,step);
		
		//tryb sieci nauczonej
		//network1.getAnswer(inputs);
	}
	//TODO - dodaæ bias
	//TODO - dodaæ wspó³czynnik nauki i momentum
	//TODO - zaimplementwaæ metodê uczenia: wstecznej propagacji b³êdu z gradientem
	//TODO - wczytywanie danych z pliku (pliki txt podane w treœci zadania)
	
	
	//INFO 1: wykorzystany zosta³ przyrostowy sposób modyfikacji wag, tzn
	//        dla ka¿dego przypadku ucz¹cego osobno, st¹d koniecznoœæ wprowadzenia ka¿dorazowego losowania
	//        kolejnoœci przypadków ucz¹cych ze zbioru treningowego
}

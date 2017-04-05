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
		
		// warunki stopu algorytmu uczenia: liczba epok lub dok�adno�� wyniku (b��d �redniokwadratowy poni�ej jaiej� warto�ci)
		int epochCount = 100;
		double step = 0.7;  //wsp�czynnik nauki
		double minError = 0.1;
		// tryb nauki
		network1.learnNetwork(inputs, outputs, epochCount, minError,step);
		
		//tryb sieci nauczonej
		//network1.getAnswer(inputs);
	}
	//TODO - doda� bias
	//TODO - doda� wsp�czynnik nauki i momentum
	//TODO - zaimplementwa� metod� uczenia: wstecznej propagacji b��du z gradientem
	//TODO - wczytywanie danych z pliku (pliki txt podane w tre�ci zadania)
	
	
	//INFO 1: wykorzystany zosta� przyrostowy spos�b modyfikacji wag, tzn
	//        dla ka�dego przypadku ucz�cego osobno, st�d konieczno�� wprowadzenia ka�dorazowego losowania
	//        kolejno�ci przypadk�w ucz�cych ze zbioru treningowego
}

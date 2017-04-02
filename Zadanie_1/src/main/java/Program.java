

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
		int outputs[] = {1,1,1,1,1,1,0,0,0,0,0,0,0};
		
		for(int i = 0;i<inputs.length;i++)
		{
			network1.getAnswer(inputs[i]);
		}
	}
	//TODO - dodaæ bias
	//TODO - wczytywanie danych z pliku (pliki txt podane w treœci zadania)

}

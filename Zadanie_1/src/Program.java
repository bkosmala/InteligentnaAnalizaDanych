

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
	}

}

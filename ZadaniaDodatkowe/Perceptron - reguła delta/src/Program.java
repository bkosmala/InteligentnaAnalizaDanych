
public class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int epoka = 100;
		double prog = 1.5;
		double krok = 0.1;
		
		double inputs[][] = {{3,4},{1,4},{2,4},{1,5},{2,5},{2,3},
							 {4,-1},{5,-1},{4,-2},{5,-2},{6,-2},{5,-3},{6,-3}};
		int outputs[] = {1,1,1,1,1,1,0,0,0,0,0,0,0};
		
		Perceptron perceptron1 = new Perceptron(inputs[0].length,1.5);
		
		Start(epoka,inputs,outputs,perceptron1,krok);
		
		double inputs2[][] = {{6,1},{5,1},{4,1},{3,2},{5,3},{6,2}};
		int outputs2[] = {0,0,0,1,1,0};
		
		System.out.println("\nTest 2\n");
		
		Start(epoka,inputs2,outputs2,perceptron1,krok);
		
		

	}
	
	public static void Start (int epoka,double inputs[][],int outputs[], Perceptron perceptron1, double krok)
	{
		// 1 - klasa A punktów
		// 0 - klasa B punktów
		
		double licznik;
		int procent;
		
		for(int i = 0; i<epoka;i++)
		{
			licznik = 0;
			System.out.println("\nEpoka " + (i+1));
			System.out.println();
			for(int j = 0; j<outputs.length;j++)
			{
				System.out.println("Punkt " + inputs[j][0] + "," + inputs[j][1]);
				if(perceptron1.Learn(inputs[j], outputs[j], krok) == outputs[j])
				{
					if(outputs[j]==1){
						System.out.print(" Poprawnie, klasa A \n");	
					}
					else
					{
						System.out.print(" Poprawnie, klasa B \n");	
					}
					licznik = licznik + 1;
				}
				else
				{
					System.out.print(" B³êdnie \n");
				}
					
			}
     
			procent = (int)((licznik/outputs.length)*100);
			System.out.println("\nKoniec epoki, procentowa poprawnoœæ dopasowania: " + procent + "%");	
			if(procent==100)break;
		}	
		
	}

}

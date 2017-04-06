package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Program {

	public static void main(String[] args) {
		
		//Ró¿ne przypadki - zakomentowane linie równie¿ s¹ poprawne
		//by sprawdziæ dzia³anie odkomentowaæ wybrany fragment
		
		//***************** PRZYPADEK TESTOWY ***********
		/*
		System.out.println("Test dla pojedynczego neuronu.");
		int[] neuronsCount = {1};
		int[] inputsCount = {2};
		int outputCount = 1;
		boolean isBias = true;
		Network network1 = new Network(1 ,neuronsCount,inputsCount,outputCount,isBias);
		
		double[][] inputs = {{3,4},{1,4},{2,4},{1,5},{2,5},{2,3},
				 {4,-1},{5,-1},{4,-2},{5,-2},{6,-2},{5,-3},{6,-3}};
		double outputs[][] = {{1},{1},{1},{1},{1},{1},{0},{0},{0},{0},{0},{0},{0}};
		
		// warunki stopu algorytmu uczenia: liczba epok lub dok³adnoœæ wyniku (b³¹d œredniokwadratowy poni¿ej jaiejœ wartoœci)
		int epochCount = 100;
		double step = 0.7;   //wspó³czynnik nauki
		double moment = 0.1; //wspó³czynnik momentu
		double minError = 0.09;
		// tryb nauki
		network1.learnNetwork(inputs, outputs, epochCount, minError,step,moment);
				
		//tryb sieci nauczonej
		//network1.getAnswer(inputs);*/
		
		
		//***************** 3 - Transformacja ***********
		
		System.out.println("3 - Transformacja.");
		
		// Parametry sieci
		int[] neuronsCount = {1}; // iloœæ neuronów w warstwie ukrytej
		int[] inputsCount = {4};  // iloœæ wejœæ
		int outputCount = 4;	  // iloœæ wyjœæ
		boolean isBias = false;	  // bies
		Network network3 = new Network(1 ,neuronsCount,inputsCount,outputCount,isBias);
		
		double[][] inputs = wczytajZPliku("./src/main/resources/transformation.txt");
		double outputs[][] = inputs;
		
		// Parametry nauki
		int epochCount = 1000;	  //maksymalna liczba epok
		double step = 0.7;        //wspó³czynnik nauki
		double moment = 0.1;      //wspó³czynnik momentu
		double minError = 0.05;   //wartoœæ b³êdu œredniokwdratowego, dla której nastêpuje koniec obliczeñ
		// tryb nauki
		network3.learnNetwork(inputs, outputs, epochCount, minError,step,moment);
		
		//network3.getErrorHistory();
		
		//***************** 4 - Aproksymacja ***********
		/*
		System.out.println("4 - Aproksymacja");
		
		int[] neuronsCount = {10};
		int[] inputsCount = {1};
		int outputCount = 1;
		boolean isBias = true;
		Network network4 = new Network(1 ,neuronsCount,inputsCount,outputCount,isBias);
		
		double[][] temp = wczytajZPliku("./src/main/resources/approximation_train_1.txt");
		
		double[][] inputs = new double[temp.length][1];
		double[][] outputs = new double[temp.length][1];
		
		for(int k=0;k<temp.length;k++)
		{	
			System.out.println();
				inputs[k][0]=temp[k][0];
				outputs[k][0]=temp[k][1];
			System.out.print(inputs[k][0] + " " + outputs[k][0]);
			
		}
		
		// warunki stopu algorytmu uczenia: liczba epok lub dok³adnoœæ wyniku (b³¹d œredniokwadratowy poni¿ej jaiejœ wartoœci)
		int epochCount = 500;
		double step = 0.3;   //wspó³czynnik nauki
		double moment = 0.2; //wspó³czynnik momentu
		double minError = 0.1;
		// tryb nauki
		network4.learnNetwork(inputs, outputs, epochCount, minError,step,moment);
		
		double[][] temp2 = wczytajZPliku("./src/main/resources/approximation_train_2.txt");
		
		double[][] inputs2 = new double[temp2.length][1];
		double[][] outputs2 = new double[temp2.length][1];
		
		for(int k=0;k<temp2.length;k++)
		{	
			System.out.println();
				inputs2[k][0]=temp2[k][0];
				outputs2[k][0]=temp2[k][1];
			System.out.print(inputs2[k][0] + " " + outputs2[k][0]);
			
		}
		
		network4.learnNetwork(inputs2, outputs2, epochCount, minError,step,moment);*/
		/*
		double[][] temp3 = wczytajZPliku("./src/main/resources/approximation_test.txt");
		
		double[][] inputs3 = new double[temp3.length][1];
		double[][] outputs3 = new double[temp3.length][1];
		
		for(int k=0;k<temp3.length;k++)
		{	
			System.out.println("");
				inputs3[k][0]=temp3[k][0];
				outputs3[k][0]=temp3[k][1];
			System.out.print(inputs3[k][0] + " " + outputs3[k][0]);
		}
		System.out.println("bbbbbbb");
		network4.getAnswer(inputs3, outputs3);*/
		
	}
	
	
	//INFO 1: wykorzystany zosta³ przyrostowy sposób modyfikacji wag, tzn
	//        dla ka¿dego przypadku ucz¹cego osobno, st¹d koniecznoœæ wprowadzenia ka¿dorazowego losowania
	//        kolejnoœci przypadków ucz¹cych ze zbioru treningowego

	public static double[][] wczytajZPliku(String url)
	{
		File plik = new File(url);

		System.out.println("Odczyt z pliku:");
		String linia="";
		ArrayList<String> input = new ArrayList<>();
		try {
			FileReader strumienOdczytu = new FileReader(plik);	// Konstrukcja i otwarcie strumienia odczytujacego
			BufferedReader bufor = new BufferedReader(strumienOdczytu);
		     while((linia = bufor.readLine()) != null){
		         System.out.println(linia);
		         input.add(linia);
		     }
			strumienOdczytu.close();
		}		
		catch (FileNotFoundException io)												
			{System.out.println(io.getMessage());}
		catch (IOException io)												
		{System.out.println(io.getMessage());} 

		int counter = 0;
		String tab[] = {};
		double[][] result={};
		if(input.size()>0)
		{
			//System.out.println("Odkodowane:");
		result = new double[input.size()][input.get(0).split(" ").length];
		for(String s : input)
		{
			tab = s.split(" ");
			for(int k=0;k<tab.length;k++)
			{
				result[counter][k] = Double.parseDouble(tab[k]);
				System.out.print(result[counter][k] + "\n");
			}
			counter++;
			//System.out.println("");	
		}
		}
		
		return result;
	}
}
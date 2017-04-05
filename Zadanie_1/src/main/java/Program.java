package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Program {

	public static void main(String[] args) {
		
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
		
		int[] neuronsCount = {1};
		int[] inputsCount = {4};
		int outputCount = 4;
		boolean isBias = true;
		Network network3 = new Network(1 ,neuronsCount,inputsCount,outputCount,isBias);
		
		double[][] inputs = wczytajZPliku("./src/main/resources/transformation.txt");
		double outputs[][] = inputs;
		
		// warunki stopu algorytmu uczenia: liczba epok lub dok³adnoœæ wyniku (b³¹d œredniokwadratowy poni¿ej jaiejœ wartoœci)
		int epochCount = 1000;
		double step = 0.7;   //wspó³czynnik nauki
		double moment = 0.1; //wspó³czynnik momentu
		double minError = 0.05;
		// tryb nauki
		network3.learnNetwork(inputs, outputs, epochCount, minError,step,moment);

		
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
				//System.out.print(result[counter][k] + "\n");
			}
			counter++;
			//System.out.println("");	
		}
		}
		
		return result;
	}
}
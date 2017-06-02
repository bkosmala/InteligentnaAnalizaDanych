package main.java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;

public class Program {
	
	private static int pointsCount = 100;

	public static void main(String[] args) {
		
		//***************** 3 - Kwantyzacja przestrzeni za pomoc¹ samorganizuj¹cej siê sieci neuronowej ***********
	       // WYSWIETLANIE WYKRESOW W OKNIE SWING 
	       // Tworzenie okienka Swing: 
	        JFrame jframe = new JFrame("IAD - 2");
	        jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
	        jframe.getContentPane().setLayout(new GridLayout( 1,1));
	        jframe.setSize(860,640);
	        
	        /*XYSeries dataSet= new XYSeries("Neurony");
	        	for(int i=0;i<pointsCount;i++)
	        	{
	        		dataSet.add(randomDouble(0,100), randomDouble(0,100));
	        	}*/
	        XYSeries dataSet= new XYSeries("Zbiór treningowy");
	        XYSeries dataSet2= new XYSeries("Neurony");
	        
	        double[][] trainingSet ={};
	        
	        //trainingSet = addTrainingData(trainingSet,35.0,45.0,10,15,false);
	        //trainingSet = addTrainingData(trainingSet,70.0,55.0,10,25,false);
	        trainingSet = wczytajZPliku("./src/main/resources/attract.txt");
	        trainingDataToVisualisation(dataSet,trainingSet);
	        	        
	        XYSeriesCollection xySeriesCollection = new XYSeriesCollection(dataSet2);
	        xySeriesCollection.addSeries(dataSet);
	        XYDataset xyDataset = xySeriesCollection;
	        JFreeChart points = ChartFactory.createScatterPlot(
	        		"1. Kwantyzacja przestrzeni za pomoc¹ samorganizuj¹cej siê sieci neuronowej ",
	                "",
	                "",
	                xyDataset,
	                PlotOrientation.VERTICAL,
	                true,
	                true,
	                false);
	             
	        ChartPanel chartPanel = new ChartPanel(points);
	        jframe.getContentPane().add(chartPanel);
	        points.getXYPlot().getRangeAxis().setRange(-12.0, 9.0);
	        points.getXYPlot().getDomainAxis().setRangeWithMargins(-11.0, 13.0);
	        jframe.setVisible(true);   
	  
	    //***************** Algorytm Kohonena *********************
	        /*double minPotential = 0.75;
	        NetworkKohonen networkKohonen = new NetworkKohonen(1000,2,minPotential);
	        double step = 0.1; // pocz¹tkowy wspó³czynnik uczenia 
	        double radious = 2; // pocz¹tkowy promieñ s¹siedztwa      
	        int maxEpoch = 18;
	        double lastError = 0;/*
	        for(double a = 1; a<15; a= a + 1)
	        {
	        //for(int i = 1;i<24;i++)
	        //{
	        lastError = networkKohonen.learnNetworkKohonen(trainingSet,dataSet2, step, a, maxEpoch, true);
	        //}
	        System.out.println("B³¹d : " + lastError + " Liczba epok : " + 100 + " Parametr : " + a);
			}*//*
	        lastError = networkKohonen.learnNetworkKohonen(trainingSet,dataSet2, step, radious,maxEpoch,false);	
	        System.out.println("B³¹d : " + lastError + " Liczba epok : " + maxEpoch); 
	        System.out.println("Iloœæ usuniêtych neuronów: " + networkKohonen.removeDeadNeurons(trainingSet));
	        networkKohonen.visualise(dataSet2);
	        System.out.println("Koniec");*/
	        
	    //***************** Algorytm gazu neuronowego *********************
	        
	        double minPotential2 = 0.75;
	        NetworkNeuralGas networkNeuralGas = new NetworkNeuralGas(1000,2,minPotential2);
	        double stepmax = 0.7; // pocz¹tkowy wspó³czynnik uczenia 
	        double stepmin = 0.05;
	        double radiousmax = 100; // pocz¹tkowy promieñ s¹siedztwa 
	        double radiousmin = 0.06;
	        int maxEpochCount = 6;
	        double lastError = 0;/*
	        for(int a = 1; a<500; a= a + 1)
	        {
	        for(int i = 1;i<a;i++)
	        {
	        lastError = networkNeuralGas.learnNetworkNeuralGas(trainingSet,dataSet2,stepmin, stepmax, radiousmin,radiousmax,i, true);	
	        }
	        System.out.println("B³¹d : " + lastError + " Liczba epok : " + 100 + " Parametr : " + a);
			}*/
	        lastError = networkNeuralGas.learnNetworkNeuralGas(trainingSet,dataSet2,stepmin, stepmax, radiousmin,radiousmax,maxEpochCount, false);	
	        System.out.println("B³¹d : " + lastError + " Liczba epok : " + maxEpochCount);
	        //System.out.println("Iloœæ usuniêtych neuronów: " + networkNeuralGas.removeDeadNeurons(trainingSet));
	        //networkNeuralGas.visualise(dataSet2);
	        System.out.println("Koniec");
	}
	
	
	public static double randomDouble(double min, double max) {
		double d = min + (Math.random() * (max - min));
		return d;
	}
	
	public static double[][] concateRows(double[][] a, double[][] b)
	{
		double[][] result = new double[a.length+b.length][b[0].length];
		int counter = 0;
		for (int i = 0; i < a.length; i++) 
		{
			for (int k = 0; k < a[0].length; k++) 
			{
				result[counter][k] = a[i][k];
			}
			counter++;
		}
		
		for (int i = 0; i < b.length; i++) 
		{
			for (int k = 0; k < b[0].length; k++) 
			{
				result[counter][k] = b[i][k];
			}
			counter++;
		}
		
		return result;						
	}
	
	public static void trainingDataToVisualisation(XYSeries dataSeries, double[][] data)
	{
		for (int i = 0; i < data.length; i++) 
		{
			dataSeries.add(data[i][0], data[i][1]);
		}		
	}
	
	public static double[][] addTrainingData(double[][] data, double a, double b, double r, int pointsCount, boolean isRectangle)
	{
		// z wykorzystaniem równania okrêgu (x-a)^2 + (x-b)^2 = r^2
		// isRectangle = true - prostok¹t, false - ko³o
		
		double[][] result = new double[pointsCount][2];
		
		double zakresX0 = a-r;
		double zakresX1 = a+r;
		//System.out.println(zakresX0);
		//System.out.println(zakresX1);
		double zakresY0 = b-r;
		double zakresY1 = b+r;
		//System.out.println(zakresY0);
		//System.out.println(zakresY1);
		
		double x;
		double y;
		int counter = 0 ;
		while (counter<pointsCount)
		{
			x = randomDouble(zakresX0,zakresX1);
			y = randomDouble(zakresY0,zakresY1);
			if(((x-a)*(x-a) + (y-b)*(y-b)<r*r) || isRectangle)
			{
				result[counter][0]=x;
				result[counter][1]=y;
				counter++;
			}
		}
		
		if(data.length < 1)
		{
			data = result;
		}
		else{
			data = concateRows(data,result);
		}
		return data;
	}
	
	public static double[][] wczytajZPliku(String url)
	{
		File plik = new File(url);

		//System.out.println("Odczyt z pliku:");
		String linia="";
		ArrayList<String> input = new ArrayList<>();
		try {
			FileReader strumienOdczytu = new FileReader(plik);	// Konstrukcja i otwarcie strumienia odczytujacego
			BufferedReader bufor = new BufferedReader(strumienOdczytu);
		     while((linia = bufor.readLine()) != null){
		         //System.out.println(linia);
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
		result = new double[input.size()][input.get(0).split(",").length];
		for(String s : input)
		{
			tab = s.split(",");
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
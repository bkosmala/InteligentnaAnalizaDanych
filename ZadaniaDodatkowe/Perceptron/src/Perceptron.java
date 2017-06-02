public class Perceptron {
	private int inputsCount;
	private double[] weights;
	private double result;
	private double treshold; // próg
	
	public Perceptron(int inputsCount, double treshold)
	{
		this.treshold = treshold;
		this.inputsCount = inputsCount;
		weights = new double[inputsCount];
		
		//inicjalizacja wag
		for(int i=0;i<inputsCount;i++)
		{
			weights[i] = randomNumber(0,1);	
		}
		//System.out.println("Wagi "+ weights[0] + "," + weights[1]);
	}
	
	public int Learn(double[] inputs, int output, double step )
	{
		int temp = getOutput(inputs);
		
		for(int i=0;i<inputs.length;i++)
		{
		weights[i] = weights[i]	+ (output-temp)*step*inputs[i];	
		}
		//System.out.println("Wagi "+ weights[0] + "," + weights[1]);
		System.out.println("Wspó³czynnik "+ weights[0]/weights[1]);
			
		return temp;	
	}
    
    private int getOutput(double[] inputs)
    {
    	result = 0;
    	for(int i = 0; i<inputsCount;i++)
    	{
    	result += weights[i]*inputs[i];	
    	}
    	
    	if(result>treshold)
    		return 1;
    	else
    		return 0;
    	
    }
    
    public static double randomNumber(double min, double max){
        double d = min+Math.random()*(max-min);
        return d;
    }
    
}

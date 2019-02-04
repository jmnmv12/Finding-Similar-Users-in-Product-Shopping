public class EstocasticoCounter {
	private double probabilidade=0.1;
	private int counter;
	private double final_count;
	
	
	 public EstocasticoCounter() {
	        this.counter=0;
	        this.final_count=0;//Inicializa o counter
	    }

	
	
	
	public void Contador () {
        double rand = Math.random(); 
        if(rand<probabilidade) {
        	this.counter++;
        }
    }




	public double getFinal_count() {
		this.final_count=(counter/probabilidade);
		return final_count;
	}


	

}

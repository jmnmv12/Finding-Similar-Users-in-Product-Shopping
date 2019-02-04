import java.util.*;


public class MinHash {
	
	private int matrix[][];
	private int a[];
	private int b[];
	private int prime=12345;
    private static Random r = new Random();
	private int nhashfunc;
    //private Map<Integer, Set<Integer>> mapa = new HashMap<Integer, Set<Integer>>();

    
	
	
	
	
    public MinHash(int nhashfunc,int unique_users) {

		this.nhashfunc = nhashfunc;
		//this.mapa = mapa;
		this.matrix=new int [nhashfunc][unique_users];
		this.a=new int[nhashfunc];
    	this.b=new int[nhashfunc];
		for (int m=0;m<this.nhashfunc;m++){
			this.a[m]=r.nextInt(prime-1+1)+1;
			this.b[m]=r.nextInt(prime-1+1)+1;
		}
		System.out.println("Minhash: número de funções de hash--> "+this.nhashfunc);

	}




    
    public void CalcMatrix (Set<Integer> elementa,int indice) {
    	int mina;
    	
    	
		int value;
    	
    	for (int hf=0;hf<nhashfunc;hf++) {
    		
    		Iterator<Integer> it = elementa.iterator();
    		int value_initial_a=it.next();
    		
    		

    		mina=(a[hf]*value_initial_a+b[hf])%prime;// calcules first min value
    		while (it.hasNext() ) {
    	        value = it.next();
    	        int tempa=(a[hf]*value+b[hf])%prime;
    	        if(tempa<mina) {
    	        	mina=tempa;
    	        }
    	      }
    		
    		
    		matrix[hf][indice]=mina;
    		

    		
    	}
    		
    }

	
	public double CalcSimilarity (int keya, int keyb) {
    	int num = 0;
    	double final_value;
    	
    	for (int hf=0;hf<this.nhashfunc;hf++) {
    		
    		if(matrix[hf][keya]==matrix[hf][keyb]) {
    			num++;
    			
    		}	
    	}
    	final_value=1-(double)num/(double)this.nhashfunc;
    	

    	
    	return final_value;	
    }
	
}

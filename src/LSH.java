import java.util.Iterator;
import java.util.Random;
import java.util.Set;


public class LSH {
	
	private int matrix[][];
	private int matrix_LSH[][];
	private int a[];
	private int b[];
	private int rows=4;//Este valor está definido para a aplicaçao, quando usamos no teste 3 passamos o número de funçoes de hash e o número de rows
	private int bandas=250;//numero de  bandas=hf/nrows
	private int prime=12345;
    private static Random r = new Random();
	private int nhashfunc;

    
	
	
	
	
    public LSH(int unique_users) {

		this.nhashfunc=this.bandas*this.rows;
		this.matrix_LSH=new int [this.bandas][unique_users];
		this.matrix=new int [nhashfunc][unique_users];
		this.a=new int[nhashfunc];
    	this.b=new int[nhashfunc];
		for (int m=0;m<this.nhashfunc;m++){
			this.a[m]=r.nextInt(prime-1+1)+1;
			this.b[m]=r.nextInt(prime-1+1)+1;
		}
		System.out.println("LSH: bandas--> "+this.bandas+" rows--> "+this.rows+" número de funções de hash--> "+this.nhashfunc);

	}
    
    public LSH(int unique_users,int nhashfunc,int rows) {
		
		this.nhashfunc = nhashfunc;
		this.rows=rows;
		this.bandas=this.nhashfunc/this.rows;
		System.out.println("LSH: bandas--> "+this.bandas+" rows--> "+this.rows+" número de funções de hash--> "+this.nhashfunc);
		this.matrix_LSH=new int [this.bandas][unique_users];
		this.matrix=new int [nhashfunc][unique_users];
		this.a=new int[nhashfunc];
    	this.b=new int[nhashfunc];
		for (int m=0;m<this.nhashfunc;m++){
			this.a[m]=r.nextInt(prime-1+1)+1;
			this.b[m]=r.nextInt(prime-1+1)+1;
		}
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
    	CalcLSH(matrix,indice);

    		
    }

	
	
	private void CalcLSH (int matriz [][],int indice) {
    	int init=0,fim=rows;
    	
    	for (int k=0;k<bandas;k++) {
    	
    		int valuea=0;
    		
    			for(int j=init;j<fim;j++) {
        			valuea+=matriz[j][indice];
        			
        			
        		}
        		valuea=(a[0]*valuea+b[0])%prime;
        		this.matrix_LSH[k][indice]=valuea;
        	
    		
    		
    	
    		
    		init+=rows;
    		fim+=rows;
    	}
    	
    	
    }
	
	public double CalcSimilarity (int keya,int keyb) {
    	int num = 0,num_LSH=0;
    	double final_value;
    	
    	for (int k=0;k<this.bandas;k++) {
    		if(matrix_LSH[k][keya]==matrix_LSH[k][keyb]) {
    			num_LSH++;
    			break;
    			
    		}
    	}
    	if(num_LSH>0/* || keya==5866 && keyb==5918*/) {//vai ser par candidato
    		for (int hf=0;hf<this.nhashfunc;hf++) {
        		
        		if(matrix[hf][keya]==matrix[hf][keyb]) {
        			num++;
        			
        		}	
        	}
        
        	final_value=1-(double)num/(double)this.nhashfunc;
        
    		
    	}
    	else//nao e par candidato
    		final_value=1;

    	
    	return final_value;
    	
    	
    	
    	

    	
    }
	
}

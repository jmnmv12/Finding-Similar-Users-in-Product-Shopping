import java.util.Random;

public class CountingBloomFilter_Strings {
    private int[] Filter ;
    private  int [] [] a;
    private  int [] [] b;
    private int prime=12345;
    private static Random r = new Random();
    private int n;
    private int k;
  

    public CountingBloomFilter_Strings(int m,double freq) {//Number of diferent elements and frequence of false positives
    	
		n=(int) Math.round(m*(Math.abs(Math.log(freq))/(Math.log(2)*Math.log((2)))));
        this.Filter=new int[n];
        this.k=(int) Math.round((n/m)*Math.log(2));
        //this.k=50;
        
        this.a=new int[k][40];
    	this.b=new int[k][40];
		
		for (int w=0;w<k;w++){
			for(int j=0;j<40;j++) {
				this.a[w][j]=r.nextInt(prime);
				this.b[w][j]=r.nextInt(prime);
			}
			
		}
		
	
	
    System.out.println("n= "+n+", k= "+k);//Inicializa o filtro
}

public CountingBloomFilter_Strings(int n,int k) {//Tamanho do bloom filter e numero de hash functions
		//size do filtro=n/fator de carga
        this.Filter=new int[n];
        this.k=k;
        //this.k=50;
        
        this.a=new int[k][40];
    	this.b=new int[k][40];
		
		for (int w=0;w<k;w++){
			for(int j=0;j<40;j++) {
				this.a[w][j]=r.nextInt(prime);
				this.b[w][j]=r.nextInt(prime);
			}
			
		}
		
	
	
    System.out.println("n= "+n+", k= "+k);//Inicializa o filtro
}

	public void Inserir (String element) {
	
	   
	
	    for (int i=0;i<k;i++){
	    	int hash=0;
	    	for(int j=0;j<element.length();j++) {
	    		hash+=((a[i][j]*(int)element.charAt(j)+b[i][j])%prime);
	    	}
	   
	
	        hash=hash%Filter.length;
	        Filter[(int)hash]++;
	        
	     
	    }
	
	
	}


    public boolean Verificar_Pertence (String element) {
       
        boolean pertence=true;
       
        for (int i=0;i<k;i++){
	    	int hash=0;
	    	for(int j=0;j<element.length();j++) {
	    		hash+=((a[i][j]*(int)element.charAt(j)+b[i][j])%prime);
	    	}
	   
	
	        hash=hash%Filter.length;
	       
	        
	        if(Filter[(int)hash]==0){
                pertence=false;
                break;
            }
	
	    }
	
        return pertence;

	
	}

    
    
   
    public int Contador (String element) {
        int min=0;
        
       

         for (int i=0;i<k;i++){
	    	int hash=0;
	    	for(int j=0;j<element.length();j++) {
	    		hash+=((a[i][j]*(int)element.charAt(j)+b[i][j])%prime);
	    	}
	   
	
	        hash=hash%Filter.length;
            if(i==0) {
            	min=Filter[(int)hash];
            }
            
            else {
            	if(min>Filter[(int)hash]) {
                	min=Filter[(int)hash];
                }
            	
            }

        }
        return min;
    }
    
    public void Delete(String element) {
        int pos;
        

         for (int i=0;i<k;i++){
	    	int hash=0;
	    	for(int j=0;j<element.length();j++) {
	    		hash+=((a[i][j]*(int)element.charAt(j)+b[i][j])%prime);
	    	}
	   
	
	        hash=hash%Filter.length;
            pos=hash;
            if(Filter[pos]!=0){
                
                Filter[pos]-=1;
                

            }



        }

    }
    
}

import java.util.Random;

public class CountingBloomFilter2 {
    private int[] Filter ;
    private  int [] a;
    private  int [] b;
    private int prime=123457;
    private static Random r = new Random();
  
    private int n;
    private int k;

    public CountingBloomFilter2(int m,double freq) {//Number of diferent elements and frequrence of false positives
    	
		n=(int) Math.round(m*(Math.abs(Math.log(freq))/(Math.log(2)*Math.log((2)))));
        this.Filter=new int[n];
        this.k=(int) Math.round((n/m)*Math.log(2));
        //this.k=50;
        
        this.a=new int[k];
    	this.b=new int[k];
		
		for (int w=0;w<k;w++){
			this.a[w]=r.nextInt(prime-1+1)+1;
			this.b[w]=r.nextInt(prime-1+1)+1;
		}
		
	
	
    System.out.println("n= "+n+", k= "+k);//Inicializa o filtro
}

public CountingBloomFilter2(int n,int k) {//Numero de elementos e numero de hash functions
		//size do filtro=n/fator de carga
        this.Filter=new int[n];
        this.k=k;
        //this.k=50;
        
        this.a=new int[k];
    	this.b=new int[k];
		
		for (int m=0;m<k;m++){
			this.a[m]=r.nextInt(prime-1+1)+1;
			this.b[m]=r.nextInt(prime-1+1)+1;
		}
		
	
	
    System.out.println("n= "+n+", k= "+k);//Inicializa o filtro
}

    public void Inserir (int element) {
  
        int hash;
        for (int i=0;i<k;i++){
            
        	
        	hash=(a[i]*element+b[i])%prime;
            hash=hash%Filter.length;


          
            
            //System.out.println(element+"-->"+hash);
            Filter[(int)hash]+=1;// nao pode ser assim

        }
      
    }

    public boolean Verificar_Pertence (int element) {
      
        boolean pertence=true;
       
        int hash;

        for (int i=0;i<k;i++){
            
            
            	
        	hash=(a[i]*element+b[i])%prime;
            hash=hash%Filter.length;
            
            
            //System.out.println(str+"-->"+pos);
            if(Filter[(int)hash]==0){
                pertence=false;
                break;
            }



        }
        return pertence;
    }
    
    
    public int Contador (int element) {
        int min=0;
        
        int hash; //para strings element.hashcode

        for (int i=0;i<k;i++){
        	
        	
        	hash=(a[i]*element+b[i])%prime;
            hash=hash%Filter.length;
            //pos=hash;
            //System.out.println(str+"-->"+pos);
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

    public void Delete(int element) {
        int pos;
        

        for (int i=0;i<k;i++){
        	int hash;
        	
        	hash=(a[i]*element+b[i])%prime;
            hash=hash%Filter.length;
            pos=hash;
            //System.out.println(str+"-->"+pos);
            if(Filter[pos]!=0){
                System.out.println("count="+Filter[pos]);
                Filter[pos]-=1;
                System.out.println("count2="+Filter[pos]);

            }



        }

    }
}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

public class Aplicacao {

	public static void main(String[] args) throws IOException {
		JFrame f = new JFrame("JProgressBar Sample");
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    Container content = f.getContentPane();
	    JProgressBar progressBar = new JProgressBar();
	
	    progressBar.setStringPainted(true);
	    Border border = BorderFactory.createTitledBorder("Reading...");
	    progressBar.setBorder(border);
	    content.add(progressBar, BorderLayout.NORTH);
	    f.setSize(300, 100);
	    f.setVisible(true);
        int count=0;
        double threshold=0.6;
        System.out.println("--Contador estocastico para prever o numero total de produtos comprados--");
        
        Map<Integer, Set<Integer>> mapa = new HashMap<Integer, Set<Integer>>();
		EstocasticoCounter counting = new EstocasticoCounter ();
		
		File file = new File("BlackFriday.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = null;

        while( (line = br.readLine())!= null ){
        		int percentage=(int)(((double)count/537578)*100);

            	progressBar.setValue(percentage);
            	if(count!=0) {
            		String [] tokens = line.split(",");
            		
            		String key = tokens[0]; // for example 
            		counting.Contador();
            		Set<Integer> set = mapa.get(Integer.parseInt(key)); 
            		if (set == null) {
            		    set = new HashSet<Integer>();
            		    set.add(Integer.parseInt(tokens[1]));
            		    mapa.put(Integer.parseInt(key), set);
            		}
            		else
            			mapa.get(Integer.parseInt(key)).add(Integer.parseInt(tokens[1]));
                    //user=tokens[0];
                    //product=tokens[1];
                    
        		
            	}
            
            
            // \\s+ means any number of whitespaces between tokens
        	
            
            count++;

            
        }
        br.close();
       List<Integer> keyList = new ArrayList<Integer>(mapa.keySet());

        double value=counting.getFinal_count();
        System.out.println("Foram comprados "+value+" produtos no total");
        System.out.println("Vamos inicializar o Counting Bloom Filter com: "+keyList.size());
        double fpr = 0.01;//falsos positivos aceitados
        
        CountingBloomFilter2 filtro = new CountingBloomFilter2 (keyList.size(),fpr);//incializar de forma correta
        System.out.println("Vamos inserir o user no counting bloom filer e o numero de produtos q ele comprou");
        count=0;
        border = BorderFactory.createTitledBorder("Inserting in Bloom...");
	    progressBar.setBorder(border);
	    file = new File("BlackFriday.csv");
        br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        while( (line = br.readLine())!= null ){
    		int percentage=(int)(((double)count/537578)*100);
    		
        	progressBar.setValue(percentage);
        	if(count!=0) {
        		String [] tokens = line.split(",");
        		
        		String key = tokens[0]; // for example 
        		
               
        		filtro.Inserir(Integer.parseInt(key));
    		
        	}
        
        
        // \\s+ means any number of whitespaces between tokens
    	
        
        count++;

        
        }
        br.close();
        System.out.println("Vamos verificar quantos produtos compraram cada user");
        count=0;
        border = BorderFactory.createTitledBorder("Checking Bloom..");
	    progressBar.setBorder(border);
	    file = new File("BlackFriday.csv");
       
        int num;
        
        for(int i = 0; i < keyList.size(); i++) {
        	int percentage=(int)(((double)i/keyList.size())*100);
        	progressBar.setValue(percentage);

        	
            Integer keya = keyList.get(i);
            num=filtro.Contador(keya);
            System.out.println(keya+" comprou "+num+" produtos!");
            

            
 
        
    }
        System.out.println("Vamos verificar a similaridade entre users em realção aos produtos comprados (LSH)");
        border = BorderFactory.createTitledBorder("Creating Matrix");
	    progressBar.setBorder(border);
	    
	    LSH teste_LSH = new LSH (keyList.size());
        for(int i = 0; i < keyList.size(); i++) {
            //System.out.println("finish");

        	int percentage=(int)(((double)i/keyList.size())*100);
        	//System.out.println("--"+keyList.size());
        	//System.out.println("teste: "+(double)i/keyList.size());
        	//int testevalue=10+i;
    	    progressBar.setValue(percentage);
        	//double sim = 0;
            Integer keya = keyList.get(i);
            Set<Integer> valuea = mapa.get(keya);
            teste_LSH.CalcMatrix(valuea, i);
            
            
        }
        border = BorderFactory.createTitledBorder("Checking similarity");
	    progressBar.setBorder(border);
        for(int i = 0; i < keyList.size(); i++) {
        	int percentage=(int)(((double)i/keyList.size())*100);
        	//System.out.println("--"+keyList.size());
        	//System.out.println("teste: "+(double)i/keyList.size());
        	//int testevalue=10+i;
    	    progressBar.setValue(percentage);
        	double sim = 0;
            //Integer keya = keyList.get(i);
            //Set<Integer> valuea = mapa.get(keya);
            
            for(int j = i+1; j < keyList.size(); j++) {
                //Integer keyb = keyList.get(j);
                //Set<Integer> valueb = mapa.get(keyb);
                sim=teste_LSH.CalcSimilarity(i, j);// calc nao pode ser acediadd ca fora
                
                if(sim<=threshold) {
                	System.out.println(keyList.get(i)+"--"+keyList.get(j)+"-->"+sim);
                }
                
            }
            
        }
        System.out.println("Vamos verificar a similaridade entre users em realção aos produtos comprados (MinHash)");

        MinHash teste = new MinHash (1000,keyList.size());
        for(int i = 0; i < keyList.size(); i++) {
            //System.out.println("finish");

        	int percentage=(int)(((double)i/keyList.size())*100);
        	//System.out.println("--"+keyList.size());
        	//System.out.println("teste: "+(double)i/keyList.size());
        	//int testevalue=10+i;
    	    progressBar.setValue(percentage);
        	//double sim = 0;
            Integer keya = keyList.get(i);
            Set<Integer> valuea = mapa.get(keya);
            teste.CalcMatrix(valuea, i);
            
            
        }
        border = BorderFactory.createTitledBorder("Checking similarity");
	    progressBar.setBorder(border);
        for(int i = 0; i < keyList.size(); i++) {
        	int percentage=(int)(((double)i/keyList.size())*100);
        	//System.out.println("--"+keyList.size());
        	//System.out.println("teste: "+(double)i/keyList.size());
        	//int testevalue=10+i;
    	    progressBar.setValue(percentage);
        	double sim = 0;
            //Integer keya = keyList.get(i);
            //Set<Integer> valuea = mapa.get(keya);
            
            for(int j = i+1; j < keyList.size(); j++) {
                //Integer keyb = keyList.get(j);
                //Set<Integer> valueb = mapa.get(keyb);
                sim=teste.CalcSimilarity(i, j);// calc nao pode ser acediadd ca fora
                
                if(sim<=threshold) {
                	System.out.println(keyList.get(i)+"--"+keyList.get(j)+"-->"+sim);
                }
                
            }
            
        }
        System.out.println("finish");

        br.close();
       
	}

}
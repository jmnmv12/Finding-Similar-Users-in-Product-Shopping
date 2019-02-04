//Validar files
import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

public class Teste3 {

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
        double threshold=0.4;
        System.out.println("--MinHash aplicado a um ficehiro data do projeto Movielens--");
        
        Map<Integer, Set<Integer>> mapa = new HashMap<Integer, Set<Integer>>();

		File file = new File("u.data");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = null;

        while( (line = br.readLine())!= null ){
        		int percentage=(int)(((double)count/537578)*100);

            	progressBar.setValue(percentage);
            	if(count!=0) {
            		String [] tokens = line.split("\\s+");
            		
            		String key = tokens[0]; // for example 

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
            
            
        	
            
            count++;

            
        }
       
        List<Integer> keyList = new ArrayList<Integer>(mapa.keySet());
        MinHash teste = new MinHash (1000,keyList.size());
        long startTime = System.currentTimeMillis();
        border = BorderFactory.createTitledBorder("Creating MinHash Matrix...");
	    progressBar.setBorder(border);
        for(int i = 0; i < keyList.size(); i++) {
        	int percentage=(int)(((double)i/keyList.size())*100);
        	
    	    progressBar.setValue(percentage);
            Integer keya = keyList.get(i);
            Set<Integer> valuea = mapa.get(keya);
            teste.CalcMatrix(valuea, i);
            
            
        }
        border = BorderFactory.createTitledBorder("Calculating similarity..");
	    progressBar.setBorder(border);
	    
        for(int i = 0; i < keyList.size(); i++) {
        	int percentage=(int)(((double)i/keyList.size())*100);
        	
    	    progressBar.setValue(percentage);
        	double sim = 0;
            
            
            for(int j = i+1; j < keyList.size(); j++) {
               
                sim=teste.CalcSimilarity(i, j);// calc nao pode ser acediadd ca fora
              
                if(sim<=threshold) {
                	System.out.println(keyList.get(i)+"--"+keyList.get(j)+"-->"+sim);
                }
                
            }
            
        }
        
        long stopTime = System.currentTimeMillis();
        System.out.println("Minhash demorou "+(stopTime-startTime)+" milissegundos");
        border = BorderFactory.createTitledBorder("Creating LSH Matrix...");
	    progressBar.setBorder(border);
        LSH teste_LSH = new LSH (keyList.size(),1000,8);
        startTime = System.currentTimeMillis();
        for(int i = 0; i < keyList.size(); i++) {
        	int percentage=(int)(((double)i/keyList.size())*100);
        	
    	    progressBar.setValue(percentage);
            Integer keya = keyList.get(i);
            Set<Integer> valuea = mapa.get(keya);
            teste_LSH.CalcMatrix(valuea, i);
            
            
        }
        border = BorderFactory.createTitledBorder("Calculating similarity...");
	    progressBar.setBorder(border);
        for(int i = 0; i < keyList.size(); i++) {
        	int percentage=(int)(((double)i/keyList.size())*100);
        	
    	    progressBar.setValue(percentage);
        	double sim = 0;
            
            
            for(int j = i+1; j < keyList.size(); j++) {
                
                sim=teste_LSH.CalcSimilarity(i, j);// calc nao pode ser acediadd ca fora
                
                if(sim<=threshold) {
                	System.out.println(keyList.get(i)+"--"+keyList.get(j)+"-->"+sim);
                }
                
            }
            
        }
        stopTime = System.currentTimeMillis();
        System.out.println("LSH demorou "+(stopTime-startTime)+" milissegundos");

        br.close();
        

    }
}

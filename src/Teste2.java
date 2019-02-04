import java.awt.BorderLayout;
import java.awt.Container;
import java.io.*;//Validar files
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.Border;
import java.util.Random;

import java.io.PrintWriter;
public class Teste2 {

    public static void main(String[] args) throws IOException {
    	JFrame f = new JFrame("Counting Bloom Filter"); //Codigo para a progess bar
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    Container content = f.getContentPane();
	    JProgressBar progressBar = new JProgressBar();

	    progressBar.setStringPainted(true);
	    Border border = BorderFactory.createTitledBorder("Adding...");
	    progressBar.setBorder(border);
	    content.add(progressBar, BorderLayout.NORTH);
	    f.setSize(500, 200);
	    f.setVisible(true);
        int count=0,added_1=0,added_2=0,dif=0,count_1=0;
        double fpr = 0.01;//falsos positivos aceitados
        int size=2;
        CountingBloomFilter_Strings filtro_v1 = new CountingBloomFilter_Strings (size,fpr);//palavras unicas e frquencia de falsos positivos
        System.out.println("--Teste 1 Counting Bloom Filter aplicado a uma palavra--");

        
        boolean flag;
        filtro_v1.Inserir("Bea");
        filtro_v1.Inserir("Bea");
        filtro_v1.Inserir("Joao");

        flag=filtro_v1.Verificar_Pertence("Bea");
        System.out.println("Bea pertence? "+flag);
        int num=filtro_v1.Contador("Bea");
        System.out.println("Bea aparece: "+num+" vezes");
        filtro_v1.Delete("Bea");
        num=filtro_v1.Contador("Bea");
        System.out.println("Bea aparece: "+num+" vezes");
        
      
        
        
        System.out.println("--Teste 2 Counting Bloom Filter aplicado a um ficheiro de texto--");
        
        border = BorderFactory.createTitledBorder("Teste 2");
	    progressBar.setBorder(border);
       
        size = 12000;//palavras unicas
        
        CountingBloomFilter_Strings filtro = new CountingBloomFilter_Strings (size,fpr);//incializar de forma correta
        //---Teste1---
        File file = new File("texto1.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = null,var1;
        Map<String, Integer> mapa = new HashMap<String, Integer>();
       
        	while( (line = br.readLine())!= null ){
         
        	

            String [] tokens = line.split("\\s+");
            for (int k=0;k<tokens.length;k++){
            	added_1++;
            	int percentage=(int)(((double)added_1/64599)*100);

            	progressBar.setValue(percentage);
                var1=tokens[k];
              
                filtro.Inserir(var1);
                if(!mapa.containsKey(var1)) {
                	mapa.put(var1, 1);
                }
                else {
                	mapa.put(var1, mapa.get(var1)+1);
                }

            }
        }
        System.out.println("Added: "+added_1);
        
        br.close();
        File file1 = new File("texto1.txt");
        BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(file1)));
        border = BorderFactory.createTitledBorder("Checking...");
        progressBar.setBorder(border);
        
        List<String> keyList = new ArrayList<String>(mapa.keySet());
        
        for(int i = 0; i < keyList.size(); i++) {
        	
        	int count2;
            //int count;
            boolean belong;
        	
    	  
            var1 = keyList.get(i);
            added_2++;
        	int percentage=(int)(((double)added_2/keyList.size())*100);

        	progressBar.setValue(percentage);
        	
            
            belong=filtro.Verificar_Pertence(var1);
            
            if (belong==true) {
            	count2=filtro.Contador(var1);
            	if(count2!=mapa.get(var1)) {
            		dif++;
            	}
                System.out.println(var1+"--->(valor no bloom filter) "+count2+" --->(valor real) "+mapa.get(var1));
            }
            else
            	count++;
         
            
        }
     
        
        System.out.println("Numero de palavras do livro que não estão presentes no Bloom Filter: "+count);
        System.out.println("Numero de palavras do livro que têm uma contagem diferente da realidade: "+dif);

        br2.close();
      
        System.out.println("--Teste 3 1000 strings aleatórias--");
        char[] alphabet = "abcdefghijklmnopqrstuvwxyzçABCDEFGHIJKLMNOPQRSTUVWXYZÇ".toCharArray();
        Random r = new Random();
        CountingBloomFilter_Strings filtro_2 = new CountingBloomFilter_Strings (8000,3);//taamnho do filtro e numero de hf
        border = BorderFactory.createTitledBorder("Teste 3");
	    progressBar.setBorder(border);

        for(int m=0;m<1000;m++) {
        	
        	int percentage=(int)(((double)m/1000)*100);

        	progressBar.setValue(percentage);
        	String str = "";
        	int value;
        	for(int w=0;w<40;w++) {
            	value=r.nextInt((alphabet.length-1)+1);

        		str=str+alphabet[value];
        	}
        	filtro_2.Inserir(str);
        }
        
        for(int m=0;m<10000;m++) {
        	
        	int percentage=(int)(((double)m/10000)*100);

        	progressBar.setValue(percentage);
        	String str = "";
        	boolean belong_2;
        	int value;
        	for(int w=0;w<40;w++) {
            	value=r.nextInt((alphabet.length-1)+1);

        		str=str+alphabet[value];
        	}
        	belong_2=filtro_2.Verificar_Pertence(str);
        	if(belong_2==true) {
        		count_1++;
        		
        	}
        	
        }
        System.out.println("Falsos positivos: "+count_1);
        
       
        System.out.println("--Teste 4 1000 strings aleatórias variando k hash functions--");
        border = BorderFactory.createTitledBorder("Teste 4");
	    progressBar.setBorder(border);
        PrintWriter pw = new PrintWriter(new File("test.csv"));
        StringBuilder sb = new StringBuilder();
        sb.append("k");
        sb.append(',');
        sb.append("fake");
        sb.append('\n');

        for (int k=1;k<=15;k++) {
        	int percentage=(int)(((double)k/15)*100);

        	progressBar.setValue(percentage);
        	
        
        CountingBloomFilter_Strings filtro_3 = new CountingBloomFilter_Strings(8000,k);//incializar de forma correta
        
	        for(int m=0;m<1000;m++) {
	        	String str = "";
	        	int value;
	        	for(int w=0;w<40;w++) {
	            	value=r.nextInt((alphabet.length-1)+1);
	
	        		str=str+alphabet[value];
	        	}
	        	filtro_3.Inserir(str);
	        	//System.out.println("str: "+str);
	        }
	        
	        for(int m=0;m<10000;m++) {
	        	String str = "";
	        	boolean belong_2;
	        	int value;
	        	for(int w=0;w<40;w++) {
	            	value=r.nextInt((alphabet.length-1)+1);
	
	        		str=str+alphabet[value];
	        	}
	        	belong_2=filtro_3.Verificar_Pertence(str);
	        	if(belong_2==true) {
	        		count_1++;
	        		
	        	}
	        	//System.out.println("str: "+str);
	        }
	        System.out.println(k+"---Falsos positivos:"+count_1);
	        sb.append(k);
	        sb.append(',');
	        sb.append(count_1);
	        sb.append('\n');

	        count_1=0;
        }
        pw.write(sb.toString());
        pw.close();

    }
    
}

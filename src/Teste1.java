import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class Teste1 {

	public static void main(String[] args) throws IOException {
		System.out.println("--Contador estocastico aplicado a um ficheiro de texto do projeto Guttenberg");
		EstocasticoCounter counting = new EstocasticoCounter ();
		try{
			File file = new File("texto1.txt");
	        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
	        
	        String line;
	        int added=0;
	       
	        while( (line = br.readLine())!= null ){
	            // \\s+ means any number of whitespaces between tokens
	        	

	            String [] tokens = line.split("\\s+");
	            for (int k=0;k<tokens.length;k++){
	            	added++;
	        		counting.Contador();
	            }
	        }
	        double value=counting.getFinal_count();
			System.out.println("Valor previsto: "+value);
	        System.out.println("Valor real : "+added);
	        
	        br.close();

		}catch (FileNotFoundException e) {
			System.out.print("File not found");
		}
		
        
		

	}

}

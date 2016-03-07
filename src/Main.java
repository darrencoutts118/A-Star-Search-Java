import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException{
		
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileWriter("java_results.csv"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int dirs = 4;
		int [] dx = {1, 0, -1, 0};
		int [] dy = {0, 1, 0, -1};
		
		int map_size = 5;
		int m;
		int n;
		int xA = 0, yA = 0, xB = 0, yB = 0;

		out.println("MapSize,MapID,Average,Values");
		
		ArrayList<Integer> Sizes = new ArrayList<Integer>(Arrays.asList(5, 10, 15, 20, 40, 50, 75, 100, 150, 200, 250, 300, 400, 500, 750));
		
		System.out.println("hello");
		
		for(Integer size: Sizes){
			for(int id = 0; id < 20; id ++){		
			map_size = size;
			
			n = m = 2 * map_size + 2;
			
			int [] [] the_map = new int [m][n];
			
			String file = "maze"+size+"_"+id+".mz";
		
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			    String line;
			    int row = 0;
			    int x = 0;
			    while ((line = br.readLine()) != null) {
			       for(int col = 0; col < line.toCharArray().length ; col++){
			    	   char value = line.charAt(col);
			    	   if(value == (".".charAt(0))){
			    		   x = 0;
			    	   } else if(value == ("+".charAt(0))){
			    		   x = 1;
			    	   } else if(value == ("S".charAt(0))){
			    		   x = 2;
			    		   xA = col;
			    		   yA = row;
			    		   
			    	   } else if(value == ("F".charAt(0))){
			    		   x = 4;
			    		   xB = col;
			    		   yB = row;
			    	   }
			    	   
			    	   the_map[row][col] = x;
			       }
			       row ++;
			    }
			}
			
			double [] allResults = new double[10];
			double sum = 0;
			String resultslist = "";
			for(int run = 0; run < 10; run ++){
			long startTime = System.nanoTime();
			Search s = new Search(the_map, m , n , dirs, dx, dy, xA, yA, xB, yB);
			
			double runtime = (((System.nanoTime() - startTime) / 1000000000.0));
			allResults[run] = runtime;
			sum += runtime;
			resultslist = resultslist + ", "+runtime;
			System.out.printf("%f\n",runtime);
			}
			
			double average = sum / 10;
			
			out.printf(size+","+id+", "+ "%f" + " ,"+"\""+resultslist+"\" \n", average);
					
		}
		}
	
		out.close();

		
	}
	
}

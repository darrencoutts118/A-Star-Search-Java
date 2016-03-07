import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Search {

	String path = null;
	
	//public Search(int[ ][ ] the_map, int n, int m, int dirs, ArrayList<Integer>dx, ArrayList<Integer>dy, int xA, int yA, int xB, int yB){
		
	public Search(int [ ] [ ] the_map, int m, int n, int dirs, int [ ] dx, int [ ] dy, int xA, int yA, int xB, int yB){
	
		Boolean pathFound = false;
		
		Heap heap = new Heap();
		
		int[ ][ ] closed_nodes_map = new int[m][n];
		int[ ][ ] open_nodes_map = new int[m][n];
		int[ ][ ] dir_map = new int[m][n];
		
	    int [ ] row = new int [n];
	    for(int i=0; i < n; i++ ){
	    	row[i] = 0;
	    }

	    for(int i=0; i < m; i++){ //// create 2d arrays
	    	for(int j = 0; j<n; j++){
	    		closed_nodes_map[ i ][j] = 0;
	    		open_nodes_map [ i ][j] = 0;
	    		dir_map [ i ][j] = 0;
	    	}
	    }
	    
	    
	    int pqi = 0; // priority queue index
		// create the start node and push into list of open nodes
	    Node n0 = new Node(xA, yA, 0, 0);
	    n0.updatePriority(xB, yB);
	    heap.push(pqi, n0);
	    
	    open_nodes_map[yA][xA] = n0.priority; // mark it on the open nodes map
	    
	    // A* search
	    while(heap.heap.get(pqi).size() > 0){
	        // get the current node w/ the highest priority
	        // from the list of open nodes
	        Node n1 = heap.getTopItem(pqi);
	        n0 = new Node(n1.xPos, n1.yPos, n1.distance, n1.priority);
	        int x = n0.xPos;
	        int y = n0.yPos;

	        heap.pop(pqi); // remove the node from the open list
	        open_nodes_map[y][x] = 0;
	        closed_nodes_map[y][x] = 1; // mark it on the closed nodes map
	        
	        
	        // quit searching when the goal is reached
	        // if n0.estimate(xB, yB) == 0:
	        if(x == xB && y == yB){
	            // generate the path from finish to start
	            // by following the dirs
	            String path = "";
                
            	while (!(x == xA && y == yA)){
	            		pathFound = true;
            			int j = dir_map[y][x];
	            		String c = Integer.toString((j + dirs / 2) % dirs);
	            		path = c + path;
	            		x += dx[j];
	            		y += dy[j];
	            	
	            } // End While Loop
	      
            	this.path = path;
	       } // End, if found
	       
	       //generate moves (child nodes) in all possible dirs
	        for( int i = 0; i < dirs; i++){
	            int xdx = x + dx[i];
	            int ydy = y + dy[i];
	            
	           
	            if(!(xdx < 0 || xdx > n-1 || ydy < 0 || ydy > m - 1
	                    || the_map[ydy][xdx] == 1 || closed_nodes_map[ydy][xdx] == 1)){
	            	
	            	// generate a child node
	                Node m0 = new Node(xdx, ydy, n0.distance, n0.priority);
	                m0.nextMove(dirs, i);
	                m0.updatePriority(xB, yB);
	                
	                //if it is not in the open list then add into that
	                
	                if(open_nodes_map[ydy][xdx] == 0){
	                    open_nodes_map[ydy][xdx] = m0.priority;
	                    heap.push(pqi, m0);
	                    //heappush(pq[pqi], m0)
	                    //mark its parent node direction
	                    dir_map[ydy][xdx] = (i + dirs / 2) % dirs;
	                    
	                    
	                	} else if(open_nodes_map[ydy][xdx] > m0.priority){
	                	
	                    // update the priority
	                    open_nodes_map[ydy][xdx] = m0.priority;
	                    // update the parent direction
	                    dir_map[ydy][xdx] = (i + dirs / 2) % dirs;
	                    
	                    // replace the node
	                    // by emptying one pq to the other one
	                    // except the node to be replaced will be ignored
	                    // and the new node will be pushed in instead
	                    while(!(heap.getTopItem(pqi).xPos == xdx && heap.getTopItem(pqi).yPos == ydy)){
	                        heap.push(1-pqi, heap.getTopItem(pqi));
	                        heap.pop(pqi);
	                    }
                        heap.pop(pqi);
	                    // empty the larger size priority queue to the smaller one
	                    if(heap.heap.get(pqi).size() > heap.heap.get(1-pqi).size()){
	                        pqi = 1 - pqi;
	                    }
	                    while(heap.heap.get(pqi).size() > 0){
	                        
	                    	heap.push(1-pqi, heap.getTopItem(pqi));
	                    	heap.pop(pqi);
	                    	
	                    	//heappush(pq[1-pqi], pq[pqi][0])
	                        //heappop(pq[pqi])    
	                    }
	                    pqi = 1 - pqi;
	                    heap.push(pqi, m0);
//	                    heappush(pq[pqi], m0) // add the better node instead
	            }// End IF
	                
	    	}

	        } // END: For
	        
	    } // End: While loop which checks the size of the queue.

	 // mark the route on the map
        if(pathFound){
            int x = xA;
            int y = yA;
            the_map[y][x] = 2;
            for(int i=0; i < this.path.length(); i++){
                int j = Character.getNumericValue(this.path.charAt(i));
                x += dx[j];
                y += dy[j];
                the_map[y][x] = 3;
            }
            the_map[y][x] = 4;
        } 
        
	}

	private void printDirMap(int [] [] dir_map) {
		// TODO Auto-generated method stub
		
		for(int [] row : dir_map){
			for (int v : row){
				System.out.print(v);
			}
			System.out.println();
		}
		
	}
	
	private void arrayPrint(int [] [] map, int rows, int cols){
		
		for(int i = 0; i < rows; i ++){
			for(int j = 0; j < cols; j ++){
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
		
	}
	
	private void arrayPrint(int [] map, int cols){
		
			for(int j = 0; j < cols; j ++){
				System.out.print(map[j]);
			System.out.println();
		}
		
	}
	
	@SuppressWarnings("unused")
	private int[] [] updateDirMap(int[] [] dir_map, int x, int y, int value){
		
		int[] row = dir_map[y];
		row[x] = value;
		System.out.print(Arrays.asList(row));
		dir_map[y] = row;
		
		return dir_map;
		
	}
}
import java.util.ArrayList;
import java.util.HashMap;

public class Heap {

	HashMap<Integer, ArrayList<Node>> heap = new HashMap<Integer, ArrayList<Node>>();

	public Heap(){
		this.heap = new HashMap<Integer, ArrayList<Node>>();
	}
	
	public void push(int pri, Node node){
		ArrayList<Node> thiselm = this.heap.getOrDefault(pri, new ArrayList<Node>());
		thiselm.add(node);
		this.heap.put(pri, thiselm);
	}
	
	public void pop(int pri){
		ArrayList<Node> thiselm = this.heap.get(pri);
		thiselm.remove(thiselm.size()-1);
		this.heap.put(pri, thiselm);
	}
	
	public Node getTopItem(int pri){
		ArrayList<Node> thiselm = this.heap.get(pri);
		return thiselm.get(thiselm.size()-1);
	}
}

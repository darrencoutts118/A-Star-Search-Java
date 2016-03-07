
public class Node {
	
	int xPos = 0; // x position
    int yPos = 0; // y position
    int distance = 0; // total distance already travelled to reach the node
    int priority = 0; //# priority = distance + remaining distance estimate
    
    public Node(int xPos, int yPos, int distance, int priority){
        this.xPos = xPos;
        this.yPos = yPos;
        this.distance = distance;
        this.priority = priority;
    }
    
    public void updatePriority(int xDest, int yDest){
    	this.priority = this.distance + this.estimate(xDest, yDest) * 10; // # A*
    }
    
    // give higher priority to going straight instead of diagonally
    public void nextMove(int dirs, int d){ //# d: direction to move
        if(dirs == 8 && d % 2 != 0){
            this.distance += 140;
        } else {
            this.distance += 10;
        }
    }
    
    // Estimation function for the remaining distance to the goal.
    public int estimate(int xDest, int yDest){
        int xd = xDest - this.xPos;
        int yd = yDest - this.yPos;
        int d = (int) java.lang.Math.sqrt(xd * xd + yd * yd);
        return(d);
    }
}

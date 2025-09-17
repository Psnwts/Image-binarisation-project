public class Node {

	private Node next;
	private int  val;
    private int capacity;
	private int flow;
	
	
	public Node(int val, Node next,int capacity,int flow) 
	{
		this.val=val;
		this.next = next;
		this.capacity=capacity;
	}		
	
	public void setNext(Node w) {
		this.next=w;
	}
	
	public Node getNext() {
		return(this.next);
	}
	
	public void setVal(int w) {
		this.val=w;
	}
	
	public int getVal() {
		return(this.val);
	}

	public int getCapacity(){
		return(this.capacity);
	}

	public void setCapacity(int c){
		this.capacity = c;
	}


	public int getFlow(){
		return(this.flow);
	}

	public void setFlow(int f){
		this.flow = f;
	}
	

}

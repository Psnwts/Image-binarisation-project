/**
 * Noden
 */
public class Vertex {

    private Vertex next;
	private int  val;
    
    public Vertex(int val,Vertex next){
        this.val=val;
        this.next=next;

    }

    public Vertex getNext() {
        return next;
    }
    public void setNext(Vertex next) {
        this.next = next;
    }
    public int getVal() {
        return val;
    }
    public void setVal(int val) {
        this.val = val;
    }



}
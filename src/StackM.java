public class StackM {

    private Vertex top;
    private int size;

    public StackM() {
        this.top = null;
        this.size = 0;
    }

    public void push(Vertex p) {
        Vertex temp = p;
        temp.setNext(this.top);
        this.top = temp;
    }

    public void pop() {
        if (this.top != null) {
            top = top.getNext();
        }
    }

    public boolean isEmpty(){
        return(this.top == null);
    }
    public Vertex getTop() {
        return top;
    }
    public int getSize() {
        return size;
    }

    
}

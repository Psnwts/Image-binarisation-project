public class QueueM {
    private Vertex front;
    private Vertex rear;
    private int queuesize;

    public QueueM() {
        front = null;
        rear = null;
        queuesize = 0;

    }

    public boolean isEmpty() {
        return (queuesize == 0);
    }

    public void dequeue() {
        front = front.getNext();
        if (isEmpty()) {
            rear = null;
        }
        queuesize--;

    }

    public void enqueue(Vertex elt) {
        Vertex oldrear = this.rear;
        rear = elt;
        if (isEmpty()) {
            front = rear;
        } else {
            oldrear.setNext(rear);
        }
        queuesize++;
    }

    public Vertex getFront() {
        return front;
    }

    public void setFront(Vertex front) {
        this.front = front;
    }

    public Vertex getRear() {
        return rear;
    }

    public void setRear(Vertex rear) {
        this.rear = rear;
    }
    
}

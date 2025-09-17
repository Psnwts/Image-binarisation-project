public class NodeR {
    private NodeR next;
	private int val;
	private int capacityR;
	private int  isback; //if back == -1 otherwise == 1

	public NodeR(int val, NodeR next, int capacityR,int isback) {
		this.val = val;
		this.next = next;
		this.capacityR = capacityR;
		this.isback = isback;

	}

	public int getIsback() {
		return isback;
	}
	public void setIsback(int isback) {
		this.isback = isback;
	}
	public void setNext(NodeR w) {
		this.next = w;
	}

	public NodeR getNext() {
		return (this.next);
	}

	public void setVal(int w) {
		this.val = w;
	}

	public int getVal() {
		return (this.val);
	}

	public int getCapacityR() {
		return (this.capacityR);
	}

	public void setCapacityR(int c) {
		this.capacityR = c;
	}
}

// BinomialHeapNode.java
// Tui Popenoe

public class BinomialHeapNode {
	// Highest degree child node
	protected BinomialHeapNode child;
	protected BinomialHeapNode left;
	protected BinomialHeapNode right;
	protected int key;

	// Rank of the tree with this node as the root
	protected int degree;

	// Default Constructor
	public BinomialHeapNode()
	{

	}

	public BinomialHeapNode(int data){
		this.key = data;
		right = this;
		left = this;
	}

	public String toString(){
			return Integer.toString(left.key)+" - " + Integer.toString(key) +" - "+ Integer.toString(right.key);
	}
}

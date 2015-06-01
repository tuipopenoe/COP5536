// LeftistTreeNode.java
// Tui Popenoe


class LeftistTreeNode {
	protected int data, sValue = 0;
	protected LeftistTreeNode leftChild, rightChild = null;
	protected boolean visited = false;

	// Default Constructor
	public LeftistTreeNode() {
		if ((leftChild == null) || (rightChild == null)) {
			sValue = 1;
		}
		else if(leftChild.sValue < rightChild.sValue){
			sValue = leftChild.sValue + 1;
		}
		else{
			sValue = rightChild.sValue + 1;
		}
	}
}

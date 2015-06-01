// LeftistTree.java
// Tui Popenoe


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LeftistTree {

	/* Declare Root when initializing for the first time */
	LeftistTreeNode root = null;

	/* Insert new element into tree */
	protected void insert(int Data) {
		LeftistTreeNode leftistTreeNode = new LeftistTreeNode();
		leftistTreeNode.data = Data;
		root = meldTrees(root, leftistTreeNode);
	}

	/* Meld two trees using recursion */
	private LeftistTreeNode meldTrees(LeftistTreeNode Node1,
			LeftistTreeNode Node2) {
		if (Node1 == null && Node2 == null) {
			return null;
		} else if (Node1 == null) {
			return Node2;
		} else if (Node2 == null) {
			return Node1;
		} else {
			if (Node1.data < Node2.data) {
				LeftistTreeNode meldedTree = meldTrees(Node1.rightChild, Node2);
				Node1.rightChild = meldedTree;
				Node1 = violationCheck(Node1);
				return Node1;
			} else {
				LeftistTreeNode meldedTree = meldTrees(Node2.rightChild, Node1);
				Node2.rightChild = meldedTree;
				Node2 = violationCheck(Node2);
				return Node2;
			}
		}
	}

	/* Check for violation of s-value at a node */
	private LeftistTreeNode violationCheck(LeftistTreeNode Node) {
		if (Node.leftChild == null && Node.rightChild == null) {
			Node.sValue = 1;
			return Node;
		} else if (Node.leftChild == null) {
			Node.leftChild = Node.rightChild;
			Node.rightChild = null;
			Node.sValue = 1;
			return Node;
		} else if (Node.rightChild == null) {
			Node.sValue = 1;
			return Node;
		} else if (Node.leftChild.sValue < Node.rightChild.sValue) {
			LeftistTreeNode leftChildNode = Node.leftChild;
			Node.leftChild = Node.rightChild;
			Node.rightChild = leftChildNode;
			if (Node.leftChild.sValue < Node.rightChild.sValue) {
				Node.sValue = Node.leftChild.sValue + 1;
			} else {
				Node.sValue = Node.rightChild.sValue + 1;
			}
		}
		return Node;
	}

	/* Delete Minimum element from tree */
	protected void deleteMin() {
		if (root != null) {
			root = meldTrees(root.leftChild, root.rightChild);
		}
	}

	/* Define Queues for Level order traversing of the tree */
	LeftistTreeNode leftistQueue1[] = new LeftistTreeNode[5000];
	LeftistTreeNode leftistQueue2[] = new LeftistTreeNode[5000];
	int queueFront1 = 0, queueFront2 = 0, queueRear1 = 0, queueRear2 = 0;

	/* Write the tree into the given file */
	protected void writeTree() throws IOException {
		BufferedWriter treeOutput = new BufferedWriter(new FileWriter(
				"result_left.txt"));
		LeftistTreeNode node = new LeftistTreeNode();
		pushIntoQueue1(root);

		int i = 1;
		System.out.print("Level " + i + ": [");
		while (queueFront1 != queueRear1) {
			node = popFromQueue1();

			treeOutput.write(node.data + " ");

			if (node.leftChild != null) {
				pushIntoQueue2(node.leftChild);
			}

			if (node.rightChild != null) {
				pushIntoQueue2(node.rightChild);
			}
			if((queueFront1 == queueRear1) && (queueFront2 != queueRear2)){
				System.out.print(node.data);
			}
			else if(queueFront1 == queueRear1)
			{
				System.out.print(node.data);
			}
			else{
				System.out.print(node.data + ",");
			}
			if ((queueFront1 == queueRear1) && (queueFront2 != queueRear2)) {
				i++;
				System.out.println("]");
				System.out.print("Level " + i + ": [");
				treeOutput.write("\n");
				while (queueFront2 != queueRear2) {

					LeftistTreeNode node1 = new LeftistTreeNode();
					node1 = popFromQueue2();
					pushIntoQueue1(node1);
				}
			}

		}
		System.out.print("]");
		treeOutput.close();
	}

	private void pushIntoQueue1(LeftistTreeNode node) {
		queueFront1++;
		leftistQueue1[queueFront1] = node;
	}

	private void pushIntoQueue2(LeftistTreeNode node) {
		queueFront2++;
		leftistQueue2[queueFront2] = node;
	}

	private LeftistTreeNode popFromQueue1() {
		queueRear1++;
		return leftistQueue1[queueRear1];
	}

	private LeftistTreeNode popFromQueue2() {
		queueRear2++;
		return leftistQueue2[queueRear2];
	}

}

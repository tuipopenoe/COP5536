// BinomiaHeap.java
// Tui Popenoe


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BinomialHeap
{
        private BinomialHeapNode min;
        private int n;   //the number of nodes in the heap

	//True if the heap is empty
    public boolean empty()
    {
        return min == null;
    }

	// Insert an element in the the heap
	public void insert(int value)
    {
        BinomialHeapNode newNode = new BinomialHeapNode();
        newNode.degree = 0;
        newNode.child = null;
        newNode.key = value;
        newNode.left = newNode;
        newNode.right = newNode;
        BinomialHeap children = new BinomialHeap();
        children.min = newNode;
        children.n = 1;
        meld(children);
        if(newNode.key < min.key)
        {
                min = newNode;
        }
    }

   // Delete the minimum value from the heap
    public void deleteMin()
    {
        if (!empty())
        {
                BinomialHeapNode curMin = min;
                BinomialHeap children = new BinomialHeap();
                children.min = min.child;
                children.n = 0;   //the children are already counted in this.n

                meld(children);

                curMin.right.left = curMin.left;
                curMin.left.right = curMin.right;

                if (curMin == curMin.right) //i.e. $prev(this.n)=1
                {
                        min = null;
                }
                else
                {
                        min = curMin.right;
                        consolidate();
                }
                n--;
        }
    }

   // Returns the min value
    public int findMin()
    {
        return min.key;
    }

   // Meld two BinomialHeaps
    public void meld (BinomialHeap heap2)
    {
        // Specific Case
        if (heap2.empty()){return;}
        if (this.empty()){
                this.min=heap2.min;
                this.n=heap2.n;
                return;
        }
        // The General Case
          BinomialHeapNode first1=this.min;
          BinomialHeapNode first2=heap2.min;
          BinomialHeapNode last1=this.min.left;
          BinomialHeapNode last2=heap2.min.left;

          first1.left=last2;
          last2.right=first1;
          first2.left=last1;
          last1.right=first2;
          if (this.min.key>heap2.min.key){
                  this.min=heap2.min;
          }
          this.n+=heap2.n;
    }

   // Return the size of the heap
    public int size()
    {
        return n;
    }

	// Returns true if the heap is valid
    public boolean isValid()
    {
        if (min == null){
                return true;
        }
        // cycle through subtrees in the heap
        BinomialHeapNode curTree = min;
        int deg = getNoOfChildren(curTree);
        do
                {
                        if (!isBinomialTree(curTree, deg))
                        {
                                return false;
                        }
                        curTree = curTree.right;
                        deg = getNoOfChildren(curTree);
                } while(!curTree.equals(min));
        return true;
    }

    // Consolidate two trees after linking
    private void consolidate(){
        BinomialHeapNode[] A = new BinomialHeapNode[(int) Math.floor(Math.log(n)/Math.log(2))+1];

	   int numOfRoots=0;
        BinomialHeapNode w=min;
        do{
                numOfRoots++;
                w=w.right;
        }while (w!=min);

        BinomialHeapNode[] roots=new BinomialHeapNode[numOfRoots];
        for (int i=0;i<roots.length;i++){
                roots[i]=w;
                w=w.right;
        }

        // Traverse all roots and link
        for(BinomialHeapNode r: roots){
                BinomialHeapNode x=r;
                int d=x.degree;
                while (A[d]!=null){
                        BinomialHeapNode y=A[d];
                        if (x.key>y.key){
                                // swap x and y
                                BinomialHeapNode temp=x;
                                x=y;
                                y=temp;
                        }
                        binomialLink(y,x);
                        A[d]=null;
                        d++;
                }
                A[d]=x;
        }

        //Reestablish the root list that points by min
        this.min=null;
        for (int i=0;i<A.length;i++){
                if (A[i]!=null){
                        if (min==null){
                                min=A[i];
                                min.left=min;
                                min.right=min;
                        }else{
                                BinomialHeapNode temp=min.right;
                                min.right=A[i];
                                A[i].left=min;
                                temp.left=A[i];
                                A[i].right=temp;

                                if (A[i].key<min.key){
                                        min=A[i];
                                }
                        }
                }
        }
    }

	// Hang the BinomialHeap rooted at y on x
	private void binomialLink(BinomialHeapNode y, BinomialHeapNode x){
        //remove y from the list of roots
        BinomialHeapNode left=y.left;
        BinomialHeapNode right=y.right;

        left.right=right;
        right.left=left;
        if (x.child==null)
		{
                y.left=y;
                y.right=y;
        }
		else
		{
        BinomialHeapNode lastChild=x.child.left;
        y.right=x.child;
        x.child.left=y;
        y.left=lastChild;
        lastChild.right=y;
        }
        x.child=y;
        x.degree++;
    }

    private boolean isBinomialTree(BinomialHeapNode curTree , int degree)
	{
        if(degree == 0)
        {
                return true;
        }
        BinomialHeapNode temp = curTree.child;
        boolean[] treeChildDeg = new boolean[degree];
        int treeDeg;

        do {
                if (temp==null){
                        return false;
                }
                if (temp.key<curTree.key){
                        return false;
                }
                treeDeg = getNoOfChildren(temp);
                if (!isBinomialTree(temp, treeDeg))
                {
                        return false;
                }
                if(treeDeg >= degree || treeChildDeg[treeDeg] == true)  //two children from the same degree
                {
                        return false;
                }
                else
                {
                        treeChildDeg[treeDeg] = true;
                }
                temp = temp.left;
        } while(temp != curTree.child);
        return arrChildTrue(treeChildDeg);
    }

    // returns true if all child trees at the degree at valid
    private boolean arrChildTrue(boolean[] treeChildDeg) {
        for (boolean i: treeChildDeg){
                if (i==false){
                        return false;
                }
        }
        return true;
    }

    //returns the number of children the root has
	private int getNoOfChildren(BinomialHeapNode start) {
        if (start.child == null)
        {
                return 0;
        }
        int counter = 1;
        BinomialHeapNode temp = start.child.right;
        while(temp != start.child)
        {
                counter++;
                temp = temp.right;
        }
        return counter;
    }

	BinomialHeapNode binomialQueue1[] = new BinomialHeapNode[11000];
	BinomialHeapNode binomialQueue2[] = new BinomialHeapNode[11000];
	int biFront1 =-1, biFront2 = -1, biRear1 = -1, biRear2 = -1;

	public void writeHeap() throws IOException {
		BufferedWriter heapOutput = new BufferedWriter(new FileWriter(
				"result_binomial.txt"));

		int maxDegree = (int) (Math.log(n) / 0.693);
		maxDegree++;
		BinomialHeapNode temp, temp1, x, node, child;
		int topLevelCount = 1;
		temp = min;

		temp1 = temp.right;

		if(min != null) {
			while(temp != temp1) {
				topLevelCount++;
				temp1 = temp1.right;
			}
		}
		else
		{
			topLevelCount = 0;
			return;
		}
		int levelI = 1;
		System.out.print("Level " + levelI+ ": [");
		for(int d = 0; d <= maxDegree; d++){
			x = min;

			if( x != null) {
				for(int i= 0; i< topLevelCount; i++){
					BinomialHeapNode y = x.right;

					if(x.degree == d) {
						pushIntoBinomialQueue1(x);

						while(biFront1 != biRear1){
							node = popFromQueue1();

							heapOutput.write(node.key + ",");
							child = node.child;

							for(int k = 0; k < node.degree; k++){
								if(child != null) {
									pushIntoBinomialQueue2(child);
								}
								child = child.right;
							}

							if((biFront1 == biRear1) && (biFront2 != biRear2))
							{
								System.out.print(node.key);
							}
							else
							{
								System.out.print(node.key + ",");
							}
							if((biFront1 == biRear1) && (biFront2 != biRear2)){
								System.out.print("]\nLevel " + levelI+ ": [");
								heapOutput.write("\n");
								while(biFront2 != biRear2) {
									BinomialHeapNode node1 = null;
									node1 = popFromQueue2();
									pushIntoBinomialQueue1(node1);
								}
							}
						}
					}
					x = y;
				}
			}
			levelI++;
		}
		System.out.print("]");
		heapOutput.close();
	}

	private void pushIntoBinomialQueue1(BinomialHeapNode node){
		biFront1 ++;
		binomialQueue1[biFront1] = node;
	}

	private void pushIntoBinomialQueue2(BinomialHeapNode node){
		biFront2 ++;
		binomialQueue2[biFront2] = node;
	}

	private BinomialHeapNode popFromQueue1() {
		biRear1++;
		return binomialQueue1[biRear1];
	}

	private BinomialHeapNode popFromQueue2() {
		biRear2++;
		return binomialQueue2[biRear2];
	}
}

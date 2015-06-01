// heap.java
// Tui Popenoe

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class heap {

	// throws an IO exception
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		/* Run in random mode if the command line argument contains "-r" */
		if (args[0].contains("-r")) {
			long leftistAverageTime = 0, binomialAverageTime = 0;
			for (int run = 0; run < 5; run++) {
				int n[] = { 100, 500, 1000, 2000, 3000, 4000, 5000 };
				for (int i = 0; i < n.length; i++) {
					LeftistTree leftistTree = new LeftistTree();
					BinomialHeap binomialHeap = new BinomialHeap();

					int count = n[i];
					Random randomInteger = new Random();
					Random randomOperation = new Random();
					/*
					 * Initialize Leftist Tree and Fib Heap by inserting n
					 * elements
					 */
					for (int j = 0; j < count; j++) {
						leftistTree.insert(randomInteger.nextInt());
						binomialHeap.insert(randomInteger.nextInt());
					}
					long start = 0, stop = 0, time = 0;
					start = System.currentTimeMillis(); // Start timer
					for (int k = 0; k < 5000; k++) {
						int d = randomOperation.nextInt(2);
						if (d == 0) {
							leftistTree.insert(randomInteger.nextInt());
						} else {
							leftistTree.deleteMin();
						}
					}
					stop = System.currentTimeMillis(); //Stop timer
					time = (stop - start) * 1000; //Calculate time taken

					System.out
							.println("Leftist Tree Time per operation for n = "
									+ count + " is : " + time + " microseconds");

					leftistAverageTime = leftistAverageTime + (time / 5000);

					start = System.currentTimeMillis();
					for(int k = 0; k < 5000; k++){
						int d = randomOperation.nextInt(2);
						if(d == 0) {
							binomialHeap.insert(randomInteger.nextInt());
						}
						else {
							// May need to change this method
							binomialHeap.deleteMin();
						}
					}

					stop = System.currentTimeMillis();
					time = (stop -start) * 1000;
					System.out.println("Binomial Heap Time per operation for n =  "
					+ count + "is : " + time + " microseconds");

					binomialAverageTime = binomialAverageTime + (time/5000);
				}
			}
			leftistAverageTime = leftistAverageTime / 7;
			binomialAverageTime = binomialAverageTime / 7;
			System.out
					.println("Overall Leftist Tree Average Time per operation = "
							+ leftistAverageTime + " microseconds");
			System.out
					.println("Overall Binomial Heap Average Time per operation = "
							+ binomialAverageTime + " microseconds");
		}

		else if (args[0].contains("-il")) {
			System.out.println("Leftist Tree");
			BufferedReader inputOperations = new BufferedReader(new FileReader(args[1]));
			LeftistTree minLeftistTree = new LeftistTree();
			String inputSequence = null;

			while ((inputSequence = inputOperations.readLine()) != null) {
				if (inputSequence.contains("I")) {// Insert operation
					minLeftistTree.insert(Integer.parseInt(inputSequence
							.substring(2)));
				} else if (inputSequence.contains("D")) {// Delete operation
					minLeftistTree.deleteMin();
				}
			}

			minLeftistTree.writeTree(); // Write leftist tree into file

		}

		else if(args[0].equals("-ib")) {
			System.out.println("Binomial Heap");
			BufferedReader inputOperations = new BufferedReader(new FileReader(args[1]));
			BinomialHeap binomialHeap = new BinomialHeap();
			String inputSequence = null;

			while ((inputSequence = inputOperations.readLine()) != null) {
				if (inputSequence.contains("I")) {// Insert operation
					binomialHeap.insert(Integer.parseInt(inputSequence
							.substring(2)));
				} else if (inputSequence.contains("D")) {// Delete operation
					binomialHeap.deleteMin();
				}
			}

			binomialHeap.writeHeap();

		}
		else {
			System.out.println("An error has occured");
		}
	}

}

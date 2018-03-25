package eg.edu.alexu.csd.filestructure.avl.cs;

public class AVLvsBST {

	private final static int TEST_SIZE = 10000000;
	public static void main(String[] args) {
		RandomGenerator inputGenerator = new RandomGenerator();
		int[] randomInput = new int[TEST_SIZE];
		for (int i = 0; i < TEST_SIZE; i++) {// Generating input.
			randomInput[i] = inputGenerator.nextInt();
		}
		Long timeBeforeBST = System.currentTimeMillis();
		BST<Integer> bst = new BST<Integer>();
		for (int i = 0; i < TEST_SIZE; i++) {// BST insertion.
			bst.insert(randomInput[i]);
		}
		Long timeAfterBST = System.currentTimeMillis();
		Long timeBST = timeAfterBST - timeBeforeBST;
		System.out.println("BST took " + timeBST + " milliseconds to insert.");
		System.out.println("BST height is " + bst.height());

		Long timeBeforeAVL = System.currentTimeMillis();
		AVLTree<Integer> avl = new AVLTree<Integer>();
		for (int i = 0; i < TEST_SIZE; i++) {// AVL insertion.
			avl.insert(randomInput[i]);
		}
		Long timeAfterAVL = System.currentTimeMillis();
		Long timeAVL = timeAfterAVL - timeBeforeAVL;
		System.out.println("AVL took " + timeAVL + " milliseconds to insert.");
		System.out.println("AVL height is " + avl.height());
		
	}
}

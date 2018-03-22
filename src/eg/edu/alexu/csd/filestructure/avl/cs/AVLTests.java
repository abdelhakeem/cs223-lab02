package eg.edu.alexu.csd.filestructure.avl.cs;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import eg.edu.alexu.csd.filestructure.avl.IAVLTree;
import eg.edu.alexu.csd.filestructure.avl.IDictionary;
import eg.edu.alexu.csd.filestructure.avl.INode;
import junit.textui.TestRunner;

public class AVLTests {
	static AVLUtil<Integer> validator = new AVLUtil<Integer>();

	public static void main(String[] arg0) throws FileNotFoundException {
		System.out.println("Avl Tree Tests:\n");
		System.out.println("1- Integration Test (To check if interfaces are implemented correctly)");
		testInsert();
		System.out.println("2- testInsert passed");
		testInsertSkewed();
		System.out.println("3- testInsertSkewed passed");
		testSearch();
		System.out.println("4- testSearch passed");
		testDelete();
		System.out.println("5- testDelete passed");
		// Smoke tests
		System.out.println("\nSmoke Tests:\n");
		testSearchSmoke();
		System.out.println("6- testSearchSmoke passed");
		testHeight();
		System.out.println("7- testHeight passed");
		//Test dictionary
		System.out.println("\nDictionary Tests:\n");
		testLoad();
		System.out.println("8- testLoad passed");
		testLookup();
		System.out.println("9- testLookup passed");
		testDeleteFromDictionary();
		System.out.println("10- testDeleteFromDictionary passed");
	}

	@org.junit.Test
	public static void testInsert() {
		@SuppressWarnings("unchecked")
		IAVLTree<Integer> avl = new AVLTree<Integer>();
		int[] input = { 13, 8, 5, 9, 4, 6, 12, 2, 1, 3 };
		 //int[] height = {0,1,1,2,2,2,2,3,3,3};
		int[] height = { 1, 2, 2, 3, 3, 3, 3, 4, 4, 4 };

		for (int i = 0; i < input.length; ++i) {
			avl.insert(input[i]);
			assertEquals(avl.height(), height[i]);
		}
	}

	@org.junit.Test
	public static void testInsertSkewed() {
		@SuppressWarnings("unchecked")
		IAVLTree<Integer> avl = new AVLTree<Integer>();
		for (int i = 1; i < 1000; ++i)
			avl.insert(i);
		assertTrue(validator.validateAVL(avl.getTree()));
	}

	@org.junit.Test
	public static void testSearch() {
		@SuppressWarnings("unchecked")
		IAVLTree<Integer> avl = new AVLTree<Integer>();
		int[] input = { 13, 8, 5, 9, 4, 6, 12, 2, 1, 3 };
		int[] positive = { 8, 12, 3 };
		int[] negative = { 0, 11, 20 };

		for (int i = 0; i < input.length; ++i) {
			avl.insert(input[i]);
		}
		for (int q : positive)
			assertTrue(avl.search(q));
		for (int q : negative)
			assertFalse(avl.search(q));
	}

	@org.junit.Test
	public static void testDelete() {
		@SuppressWarnings("unchecked")
		IAVLTree<Integer> avl = new AVLTree<Integer>();
		int[] input = { 13, 8, 5, 9, 4, 6, 12, 2, 1, 3 };

		for (int i = 0; i < input.length; ++i)
			avl.insert(input[i]);
		// try deleting non-existing elements
		for (int i = -1; i >= -5; --i) {
			assertFalse(avl.delete(i));
		}
		// check that the tree structure is not affected
		assertTrue(validator.validateAVL(avl.getTree()));
		// delete all existing elements
		int[] deleteOrder = { 8, 4, 2, 12, 9, 13, 5, 3, 1, 6 };
		for (int element : deleteOrder) {
			assertTrue(avl.delete(element));
			assertTrue(validator.validateAVL(avl.getTree()));
		}
	}

	@org.junit.Test
	public static void testHeight() {
		@SuppressWarnings("unchecked")
		IAVLTree<Integer> avl = new AVLTree<Integer>();
		assertEquals("Invalid height", 0, avl.height());
		avl.insert(123);
		assertEquals("Invalid height", 1, avl.height());
		avl.insert(456);
		assertEquals("Invalid height", 2, avl.height());
		avl.delete(456);
		assertEquals("Invalid height", 1, avl.height());
		avl.delete(123);
		assertEquals("Invalid height", 0, avl.height());
		for (int i = 0; i < 1000; i++)
			avl.insert(55);
		assertTrue("Invalid height after adding duplicates", avl.height() > 1);
	}

	@org.junit.Test
	public static void testSearchSmoke() {
		@SuppressWarnings("unchecked")
		IAVLTree<Integer> avl = new AVLTree<Integer>();
		Set<Integer> elements = new HashSet<Integer>();
		while (elements.size() < 1000)
			elements.add((int) (Math.random() * 100000));
		for (Integer e : elements) {
			assertFalse("Find non existing element", avl.search(e));
			avl.insert(e);
			assertTrue("Can't non existing element", avl.search(e));
		}
	}

	@org.junit.Test(timeout = 7000)
	public static void testLoad() {
		IDictionary dict = new Dictionary();
		File input = new File("src//eg//edu//alexu//csd//filestructure//avl//cs//res//dictionary.txt");
		dict.load(input);
		assertEquals(9123, dict.size()); // out of the 10,000 input words, there
											// are only 9123 unique words
		assertEquals(16, dict.height());
	}

	@org.junit.Test(timeout = 10000)
	public static void testLookup() throws FileNotFoundException {
		IDictionary dict = new Dictionary();
		File dictFile = new File("src//eg//edu//alexu//csd//filestructure//avl//cs//res//dictionary.txt");
		dict.load(dictFile);
		File inputFile = new File("src//eg//edu//alexu//csd//filestructure//avl//cs//res//queries.txt");
		Scanner sc = new Scanner(inputFile);
		// there are 15 queries. the first 10 exist, and the remaining 5 don't
		for (int i = 0; i < 10; ++i)
			assertTrue(dict.exists(sc.next()));
		for (int i = 0; i < 5; ++i)
			assertFalse(dict.exists(sc.next()));
		sc.close();
	}

	@org.junit.Test(timeout = 7000)
	public static void testDeleteFromDictionary() throws FileNotFoundException {
		IDictionary dict = new Dictionary();
		File dictFile = new File("src//eg//edu//alexu//csd//filestructure//avl//cs//res//dictionary.txt");
		dict.load(dictFile);
		File inputFile = new File("src//eg//edu//alexu//csd//filestructure//avl//cs//res//deletions.txt");
		Scanner sc = new Scanner(inputFile);
		// there are 300 words to delete, but only 290 unique words in them
		while (sc.hasNext())
			dict.delete(sc.next());
		sc.close();
		System.out.println(dict.size());
		assertEquals(9123 - 290, dict.size()); // out of the 10,000 input words,
												// there are only 9123 unique
												// words
		assertEquals(15, dict.height());
	}

	public static class AVLUtil<T extends Comparable<T>> {
		public boolean validateAVL(INode<T> root) {
			return validateBST(root) && checkBalance(root);
		}

		public boolean validateBST(INode<T> root) {
			if (root == null)
				return true;

			ArrayList<T> inOrder = new ArrayList<T>();
			inOrderTraversal(root, inOrder);
			for (int i = 1; i < inOrder.size(); ++i) {
				if (inOrder.get(i).compareTo(inOrder.get(i - 1)) < 0)
					return false;
			}
			return true;
		}

		private void inOrderTraversal(INode<T> node, ArrayList<T> list) {
			if (node == null)
				return;
			inOrderTraversal(node.getLeftChild(), list);
			list.add(node.getValue());
			inOrderTraversal(node.getRightChild(), list);
		}

		public boolean checkBalance(INode<T> root) {
			return checkBalanceAux(root) != -1;
		}

		private int checkBalanceAux(INode<T> node) {
			if (node == null)
				return 0;
			int left = checkBalanceAux(node.getLeftChild());
			int right = checkBalanceAux(node.getRightChild());
			if (left == -1 || right == -1)
				return -1;
			if (Math.abs(left - right) > 1) {
				return -1;
			}
			return 1 + Math.max(left, right);
		}
	}
}
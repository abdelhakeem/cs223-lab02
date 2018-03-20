package eg.edu.alexu.csd.filestructure.avl.cs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import eg.edu.alexu.csd.filestructure.avl.IDictionary;

/**
 * Dictionary implementation using AVL Tree.
 * @author H
 *
 */
public class Dictionary implements IDictionary {

    private AVLTree<String> tree;

    public Dictionary() {
        tree = new AVLTree<String>();
    }

    @Override
    public void load(File file) {
        FileReader reader = null;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(reader);
        String inputWord;
        try {
            inputWord = br.readLine();
            while (inputWord != null) {
                insert(inputWord);
                inputWord = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean insert(String word) {
        if (tree.search(word)) {
            return false;
        } else {
            tree.insert(word);
            return true;
        }
    }

    @Override
    public boolean exists(String word) {
        return tree.search(word);
    }

    @Override
    public boolean delete(String word) {
        return tree.delete(word);
    }

    @Override
    public int size() {
        return tree.getSize();
    }

    @Override
    public int height() {
        return tree.height();
    }

}

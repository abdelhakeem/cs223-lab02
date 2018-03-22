package eg.edu.alexu.csd.filestructure.avl.cs;

import java.util.Random;

public class RandomGenerator {
	long seed = 1521392473768L;
	Random generator;

	public RandomGenerator() {
		generator = new Random(seed);
	}

	public int nextInt(){
		return generator.nextInt();
	}
}

package sudoku;

import java.util.Random;

public class Generator {
	private int randomIndex, r;

	public Generator(int r) {
		this.r = r;
	}

	public int getRandomIndex() {
		Random random = new Random();
		randomIndex = random.nextInt(r);
		return randomIndex;
	}
}

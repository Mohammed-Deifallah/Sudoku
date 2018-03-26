package sudoku;

import java.io.File;
import java.io.FileNotFoundException;

public class Model {
	private int board[][];
	private FileReader fr;
	private Generator gen;

	public Model(int numBoards) {
		board = new int[10][10];
		fr = new FileReader();
		gen = new Generator(numBoards);
	}

	public void fillBoard(File sudoku) {
		int rand = gen.getRandomIndex();
		int temp[][][];
		try {
			temp = fr.read(sudoku);
			board = temp[rand];
		} catch (FileNotFoundException e) {
		}
	}

	public int[][] getboard() {
		return board.clone();
	}
}

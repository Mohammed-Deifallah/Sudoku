package sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
	private int arr[][][];

	public FileReader() {
		arr = new int[2][10][10];
	}

	public int[][][] read(File sudoku) throws FileNotFoundException {
		Scanner scan = new Scanner(sudoku);
		for (int i = 0; i < 2; i++) {
			for (int j = 1; j < 10; j++) {
				for (int k = 1; k < 10; k++) {
					arr[i][j][k] = scan.nextInt();
				}
			}
		}
		scan.close();
		return arr.clone();
	}
}

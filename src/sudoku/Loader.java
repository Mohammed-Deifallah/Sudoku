package sudoku;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JTextField;

public class Loader {
	private Scanner in;
	private ResultRead res;
	private int[][] sud;
	private JTextField[][] board;

	public boolean load() throws FileNotFoundException {
		in = new Scanner(new File("src/Resources/savedGame.txt"));
		if (!in.hasNextLine()) {
			return false;
		}
		res = new ResultRead();
		sud = res.getSudoku();
		board = res.getBoard();
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 9; j++) {
				sud[i][j] = in.nextInt();
			}
			in.nextLine();
		}
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 9; j++) {
				board[i][j] = new JTextField();
				String text = in.next();
				if (text.equals("!")) {
					board[i][j].setText("");
				} else {
					board[i][j].setText(text);
				}
				String color = in.next();
				String edit = in.next();
				if (color.equals("BLACK")) {
					board[i][j].setForeground(Color.black);
				} else if (color.equals("BLUE")) {
					board[i][j].setForeground(Color.blue);
				} else {
					board[i][j].setForeground(Color.red);
				}
				if (edit.equals("TRUE")) {
					board[i][j].setEditable(true);
				} else {
					board[i][j].setEditable(false);
				}
			}
			in.nextLine();
		}
		return true;
	}

	public JTextField[][] getBoard() {
		return board;
	}

	public int[][] getSudouku() {
		return sud;
	}
}

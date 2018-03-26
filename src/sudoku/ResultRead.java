package sudoku;

import javax.swing.JTextField;

public class ResultRead {
	private int[][] sud;
	private JTextField[][] board;

	public ResultRead() {
		sud = new int[10][10];
		board = new JTextField[10][10];
	}

	public JTextField[][] getBoard() {
		return board;
	}

	public int[][] getSudoku() {
		return sud;
	}
}

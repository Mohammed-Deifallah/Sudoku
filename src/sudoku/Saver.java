package sudoku;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JTextField;

public class Saver {
	private PrintWriter p;

	public void save(int sud[][], JTextField[][] board) throws FileNotFoundException {
		p = new PrintWriter(new File("src/Resources/savedGame.txt"));
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 9; j++) {
				p.print(sud[i][j] + " ");
			}
			p.println();
		}
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 9; j++) {
				String text = board[i][j].getText().trim();
				if (text.equals("")) {
					p.print("! ");
				} else {
					p.print(text + " ");
				}
				if (board[i][j].getForeground().equals(Color.BLACK)) {
					p.print("BLACK ");
				} else if (board[i][j].getForeground().equals(Color.BLUE)) {
					p.print("BLUE ");
				} else {
					p.print("RED ");
				}

				if (board[i][j].isEditable()) {
					p.print("TRUE ");
				} else {
					p.print("False ");
				}
			}
			p.println();
		}
		p.close();
	}
}

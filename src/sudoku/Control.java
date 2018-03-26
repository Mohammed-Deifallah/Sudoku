package sudoku;

import java.io.File;

public class Control {
	private Model model;

	public Control() {
		model = new Model(2);
		model.fillBoard(new File("src/Resources/sudoku.txt"));
		new View(model.getboard());
	}

	public Model getModel() {
		return model;
	}
}

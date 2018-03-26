package sudoku;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class View extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton easy, medium, hard, solve, load, save, back;
	private JTextField board[][];
	private int sud[][];
	private Saver saver;
	private Loader loader;
	private ImageIcon imgIcon;
	private JLabel image, time;
	private long start, end;
	private TimeParser parser;

	/**
	 * Create the application.
	 */
	public View(int arr[][]) {
		sud = arr.clone();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
					setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Sudoku");
		setBounds(0, 0, 1500, 700);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		easy = new JButton();
		easy.setBounds(new Rectangle(500, 250, 100, 100));
		easy.setBackground(Color.gray);
		easy.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				startGame(25);
			}
		});
		// bfim = ImageIO.read(new File("src/sudoku/easy.PNG"));
		imgIcon = new ImageIcon(View.class.getResource("/Resources/easy.PNG"));
		easy.setIcon(imgIcon);
		easy.setBackground(Color.darkGray);
		getContentPane().add(easy);

		medium = new JButton();
		medium.setBounds(new Rectangle(650, 250, 100, 100));
		medium.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				startGame(35);
			}
		});
		imgIcon = new ImageIcon(View.class.getResource("/Resources/medium.PNG"));
		medium.setIcon(imgIcon);
		getContentPane().add(medium);

		hard = new JButton();
		hard.setBounds(new Rectangle(800, 250, 100, 100));
		hard.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				startGame(45);
			}
		});
		imgIcon = new ImageIcon(View.class.getResource("/Resources/hard.PNG"));
		hard.setIcon(imgIcon);
		getContentPane().add(hard);

		imgIcon = new ImageIcon(View.class.getResource("/Resources/sudoku.jpg"));
		image = new JLabel(imgIcon);
		image.setBounds(800, 450, imgIcon.getIconWidth(), imgIcon.getIconHeight());
		getContentPane().add(image);

		loader = new Loader();
		saver = new Saver();

		imgIcon = new ImageIcon(View.class.getResource("/Resources/load.jpg"));
		load = new JButton();
		load.setBounds(new Rectangle(590, 10, imgIcon.getIconWidth(), imgIcon.getIconHeight()));
		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					load();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		load.setIcon(imgIcon);
		getContentPane().add(load);
	}

	private void emptyBoard(int counter) {
		int count = 0;
		Random rand = new Random();
		while (count < counter) {
			int row = rand.nextInt(9) + 1;
			int column = rand.nextInt(9) + 1;
			if (board[row][column].isEditable()) {
				continue;
			}
			board[row][column].setText("");
			board[row][column].setEditable(true);
			board[row][column].setForeground(Color.BLUE);
			count++;
		}
	}

	/**
	 * Start game.
	 *
	 * @param counter
	 *            the counter
	 */
	private void startGame(int counter) {
		initializeBoard();
		emptyBoard(counter);
	}

	private void checkSolution() {
		int rem = 0;
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 9; j++) {
				if (board[i][j].isEditable()) {
					String s = board[i][j].getText().trim();
					try {
						if (s.equals("")) {
							rem++;
						} else if (Integer.parseInt(s) == sud[i][j]) {
							board[i][j].setForeground(Color.black);
						} else {
							board[i][j].setForeground(Color.red);
							rem++;
						}
					} catch (Exception e) {
						rem++;
					}
				}
			}
		}
		if (rem == 0) {
			finish();
		}
	}

	private void finish() {
		solve.setEnabled(false);
		save.setEnabled(false);
		end = System.currentTimeMillis();
		parser = new TimeParser(end - start);
		time.setVisible(true);
		time.setText("Estimated time: " + parser.getTimeFormat());
		JOptionPane.showMessageDialog(null, "Congratulations !!");
	}

	private void save() throws FileNotFoundException {
		saver.save(sud, board);
	}

	private void load() throws FileNotFoundException {
		if (loader.load()) {
			board = loader.getBoard();
			sud = loader.getSudouku();
			loadBoard();
		} else {
			JOptionPane.showMessageDialog(null, "There's no saved game");
		}
	}

	private void back() {
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 9; j++) {
				board[i][j].setVisible(false);
			}
		}
		easy.setVisible(true);
		medium.setVisible(true);
		hard.setVisible(true);
		load.setVisible(true);
		save.setVisible(false);
		solve.setVisible(false);
		back.setVisible(false);
		time.setVisible(false);
	}

	private void initializeBoard() {
		startFrame();
		board = new JTextField[10][10];
		for (int column = 1; column <= 9; column++) {
			for (int row = 1; row <= 9; row++) {
				board[row][column] = new JTextField();
				board[row][column].setText("" + sud[row][column]);
				board[row][column].setForeground(Color.BLACK);
				board[row][column].setEditable(false);
			}
		}
		commonFactors();
		addListeners();
	}

	private void loadBoard() {
		startFrame();
		commonFactors();
		addListeners();
	}

	public void startFrame() {
		easy.setVisible(false);
		medium.setVisible(false);
		hard.setVisible(false);
		load.setVisible(false);

		time = new JLabel();
		time.setForeground(Color.WHITE);
		time.setBounds(800, 150, 500, 200);
		time.setFont(new Font("Hobo Std", Font.PLAIN, 20));
		getContentPane().add(time);
		solve = new JButton();
		solve.setBounds(350, 10, 80, 85);
		solve.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkSolution();
			}
		});
		imgIcon = new ImageIcon(View.class.getResource("/Resources/solve.PNG"));
		solve.setIcon(imgIcon);
		getContentPane().add(solve);

		back = new JButton();
		imgIcon = new ImageIcon(View.class.getResource("/Resources/back.PNG"));
		back.setBounds(10, 10, imgIcon.getIconWidth(), imgIcon.getIconHeight());
		back.setIcon(imgIcon);
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				back();
			}
		});
		getContentPane().add(back);

		save = new JButton();
		imgIcon = new ImageIcon(View.class.getResource("/Resources/save.PNG"));
		save.setBounds(500, 20, imgIcon.getIconWidth(), imgIcon.getIconHeight());
		save.setIcon(imgIcon);
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					save();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		getContentPane().add(save);

		start = System.currentTimeMillis();
	}

	private void addListeners() {
		for (int row = 1; row <= 9; row++) {
			for (int column = 1; column <= 9; column++) {
				JTextField temp = board[row][column];
				board[row][column].addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent arg0) {
					}

					@Override
					public void keyReleased(KeyEvent arg0) {
					}

					@Override
					public void keyPressed(KeyEvent arg0) {
						if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
							if (temp.isEditable()) {
								String s = temp.getText().trim();
								try {
									if (Integer.parseInt(s) >= 0 && Integer.parseInt(s) <= 9) {
										temp.setForeground(Color.BLACK);
										temp.setText(s);
									} else {
										throw new Exception();
									}
								} catch (Exception e) {
									JOptionPane.showMessageDialog(null, "Enter a valid number !!");
									temp.setText("");
									return;
								}
							}
						}
					}
				});
				board[row][column].addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent arg0) {
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
					}

					@Override
					public void mouseClicked(MouseEvent arg0) {
						if (temp.isEditable()) {
							temp.setForeground(Color.blue);
						}
					}
				});
			}
		}
	}

	private void commonFactors() {
		int row, column = 1;
		for (int i = 100; column <= 9; i += 50, column++) {
			row = 1;
			for (int j = 170; row <= 9; j += 50, row++) {
				board[row][column].setBounds(j, i, 50, 50);
				board[row][column].setHorizontalAlignment(SwingConstants.CENTER);
				board[row][column].setBackground(Color.ORANGE);
				board[row][column].setFont(new Font("Hobo Std", Font.PLAIN, 20));
				getContentPane().add(board[row][column]);
				if (row % 3 == 0) {
					j += 10;
				}
			}
			if (column % 3 == 0) {
				i += 10;
			}
		}
	}
}
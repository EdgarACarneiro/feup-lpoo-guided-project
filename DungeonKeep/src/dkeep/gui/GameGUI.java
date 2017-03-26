package dkeep.gui;

import dkeep.logic.*;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SpinnerNumberModel;

public class GameGUI {

	private static enum State {
		INITIAL, GAME, EDIT
	};

	public static final int EDIT_MIN_LINES = 5;
	public static final int EDIT_MAX_LINES = 15;
	
	private JFrame frame;
	private JTextField textField;

	private GameHandler game;

	private JLabel lblStatus;

	private JPanel Initial;
	private JPanel Game;
	private JPanel Edit;

	private JPanel gamePanel;
	private JPanel editPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGUI window = new GameGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 710, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		initializeInitialPanel();
		initializeGamePanel();
		initializeEditPanel();

		switchState(State.INITIAL);
	}

	private void initializeInitialPanel() {
		Initial = new JPanel();
		Initial.setBounds(0, 0, 694, 561);
		frame.getContentPane().add(Initial);
		Initial.setLayout(null);

		JButton btnStdGame = new JButton("Standard Game");
		btnStdGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((GamePanel) gamePanel).setGameHandler(null);
				switchState(State.GAME);
			}
		});

		btnStdGame.setBounds(286, 68, 138, 60);
		Initial.add(btnStdGame);

		JButton btnCustomGame = new JButton("Custom Game");
		btnCustomGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchState(State.EDIT);
			}
		});
		btnCustomGame.setBounds(286, 190, 138, 60);
		Initial.add(btnCustomGame);

		JButton btnLoadGame = new JButton("Load Game");
		btnLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				game = new GameHandler();

				((GamePanel) gamePanel).setGameHandler(game);
				switchState(State.GAME);

			}
		});
		btnLoadGame.setBounds(286, 312, 138, 60);
		Initial.add(btnLoadGame);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(286, 434, 138, 60);
		Initial.add(btnExit);
	}

	private void initializeGamePanel() {
		Game = new JPanel();
		Game.setBounds(0, 0, 710, 561);
		frame.getContentPane().add(Game);
		Game.setLayout(null);

		gamePanel = new GamePanel(game);
		gamePanel.setBounds(16, 61, 500, 500);
		Game.add(gamePanel);

		JLabel lblNumberOfOgres = new JLabel("Number of Ogres");
		lblNumberOfOgres.setBounds(547, 231, 109, 19);
		Game.add(lblNumberOfOgres);
		lblNumberOfOgres.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumberOfOgres.setFont(new Font("Malayalam MN", Font.PLAIN, 13));

		textField = new JTextField();
		textField.setBounds(536, 262, 130, 26);
		Game.add(textField);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setText("2");
		textField.setColumns(10);

		JLabel lblGuardPersonality = new JLabel("Guard Personality");
		lblGuardPersonality.setBounds(549, 380, 107, 19);
		Game.add(lblGuardPersonality);
		lblGuardPersonality.setHorizontalAlignment(SwingConstants.CENTER);
		lblGuardPersonality.setFont(new Font("Malayalam MN", Font.PLAIN, 13));

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(542, 411, 124, 27);
		Game.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Rookie", "Drunken", "Suspicious" }));
		comboBox.setToolTipText("Persona");

		lblStatus = new JLabel("Game Status Placeholder");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setBounds(16, 20, 485, 29);
		Game.add(lblStatus);
		lblStatus.setFont(new Font("Malayalam MN", Font.PLAIN, 20));

		JButton btnSaveGame = new JButton("Save Game");
		btnSaveGame.setBounds(547, 78, 109, 30);
		Game.add(btnSaveGame);
		btnSaveGame.setEnabled(true);
		btnSaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.saveGame();
				gamePanel.requestFocusInWindow();
			}
		});

		JButton btnExitGame = new JButton("Exit");
		btnExitGame.setBounds(567, 514, 75, 29);
		Game.add(btnExitGame);
		btnExitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchState(State.INITIAL);
			}
		});

		JButton btnNewGame = new JButton("New Game");
		btnNewGame.setBounds(547, 37, 110, 29);
		Game.add(btnNewGame);
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Guard.Personality gp = null;

				switch ((String) comboBox.getSelectedItem()) {
				case "Rookie":
					gp = Guard.Personality.ROOKIE;
					break;
				case "Drunken":
					gp = Guard.Personality.DRUNKEN;
					break;
				case "Suspicious":
					gp = Guard.Personality.SUSPICIOUS;
					break;
				default:
					System.err.println("Invalid Personality");
				}

				int numOgres;
				try {
					numOgres = Integer.parseInt(textField.getText());
				} catch (NumberFormatException exc) {
					lblStatus.setText("Invalid input as number of Ogres!");
					return;
				}
				if (numOgres >= 0 && numOgres <= 5 && gp != null) {
					game = new GameHandler(gp, numOgres);
					lblStatus.setText("Game in progress!");
				} else {
					lblStatus.setText("Invalid number of Ogres.");
					return;
				}

				ImageLoader.getInstance().reset();
				((GamePanel) gamePanel).setGameHandler(game);

				gamePanel.requestFocusInWindow();
			}
		});
	}

	private void initializeEditPanel() {
		Edit = new JPanel();
		Edit.setBounds(0, 0, 710, 578);
		frame.getContentPane().add(Edit);
		Edit.setLayout(null);

		editPanel = new MapEditPanel();
		editPanel.setBounds(17, 62, 500, 500);
		Edit.add(editPanel);

		JLabel lblRows = new JLabel("Rows:");
		lblRows.setBounds(38, 6, 61, 16);
		Edit.add(lblRows);

		JLabel lblColumns = new JLabel("Columns:");
		lblColumns.setBounds(38, 34, 61, 16);
		Edit.add(lblColumns);

		//int min_lines = MapEditPanel.MIN_LINES;
		//int max_lines = MapEditPanel.MAX_LINES;
		int min_lines = EDIT_MIN_LINES;
		int max_lines = EDIT_MAX_LINES;

		JSpinner spinnerRows = new JSpinner();
		spinnerRows.setModel(new SpinnerNumberModel(10, min_lines, max_lines, 1));
		spinnerRows.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				((MapEditPanel) editPanel).setRows(spinnerRows.getValue());
			}
		});
		spinnerRows.setBounds(111, 1, 48, 26);
		spinnerRows.setValue(10);
		Edit.add(spinnerRows);

		JSpinner spinnerCols = new JSpinner();
		spinnerCols.setModel(new SpinnerNumberModel(10, min_lines, max_lines, 1));
		spinnerCols.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				((MapEditPanel) editPanel).setCols(spinnerCols.getValue());
			}
		});
		spinnerCols.setBounds(111, 29, 48, 26);
		spinnerCols.setValue(10);
		Edit.add(spinnerCols);

		JButton btnFloor = new JButton(new ImageIcon("images/img0.png"));
		btnFloor.setText("Floor");
		btnFloor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				((MapEditPanel) editPanel).setSelection('B');
			}
		});
		btnFloor.setBounds(588, 100, 60, 60);
		Edit.add(btnFloor);

		JButton btnWall = new JButton(new ImageIcon("images/img1.png"));
		btnWall.setText("Wall");
		btnWall.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				((MapEditPanel) editPanel).setSelection('X');
			}
		});
		btnWall.setBounds(588, 170, 60, 60);
		Edit.add(btnWall);

		JButton btnOgre = new JButton(new ImageIcon("images/img4.png"));
		btnOgre.setText("Ogre");
		btnOgre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				((MapEditPanel) editPanel).setSelection('O');
			}
		});
		btnOgre.setBounds(588, 240, 60, 60);
		Edit.add(btnOgre);

		JButton btnDoor = new JButton("Door", new ImageIcon("images/img8.png"));
		btnDoor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				((MapEditPanel) editPanel).setSelection('I');
			}
		});
		btnDoor.setBounds(588, 310, 60, 60);
		Edit.add(btnDoor);

		JButton btnKey = new JButton("Key", new ImageIcon("images/img6.png"));
		btnKey.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				((MapEditPanel) editPanel).setSelection('k');
			}
		});
		btnKey.setBounds(588, 380, 60, 60);
		Edit.add(btnKey);

		JButton btnHero = new JButton("Hero", new ImageIcon("images/img9.png"));
		btnHero.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				((MapEditPanel) editPanel).setSelection('A');
			}
		});
		btnHero.setBounds(588, 450, 60, 60);
		Edit.add(btnHero);

		JButton btnDone = new JButton("Done!");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Level new_level = ((MapEditPanel) editPanel).getLevel();
				if (new_level == null) {
					System.err.println("Invalid map characteristics");
					return;
				}

				game = new GameHandler(new_level);
				((GamePanel) gamePanel).setGameHandler(game);

				switchState(State.GAME);
			}
		});
		btnDone.setBounds(554, 34, 117, 58);
		Edit.add(btnDone);
		Edit.setVisible(false);
	}

	/**
	 * State-Machine - used to control possible transitions and their associated
	 * actions (e.g. visible panels)
	 * 
	 * @param st
	 *            State to change to
	 */
	private void switchState(State st) {

		switch (st) {
		case INITIAL:
			Initial.setVisible(true);
			Game.setVisible(false);
			Edit.setVisible(false);
			break;
		case GAME:
			Initial.setVisible(false);
			Game.setVisible(true);
			Edit.setVisible(false);
			gamePanel.requestFocusInWindow();
			break;
		case EDIT:
			Initial.setVisible(false);
			Game.setVisible(false);
			Edit.setVisible(true);
			((MapEditPanel) editPanel).reset();
			break;
		}

	}

}

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import sweeper.Box;

public class JavaSweeper extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Game game;

	private JPanel panel;
	private JLabel label;
	private final int COLS = 9;
	private final int ROWS = 9;
	private final int BOMBS = 10;
	private final int IMAGE_SIZE = 50;

	public static void main(String[] args) {
		new JavaSweeper();
	}

	private JavaSweeper() {

		game = new Game(COLS, ROWS, BOMBS);
		game.start();
		setImeges();
		initLabel();
		initPanel();
		initFrame();
	}
	
	private void initLabel() {
		label = new JLabel("Welcome!");
		add(label, BorderLayout.SOUTH);
	}

	private void initPanel() {
		panel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				for (Coord coord : Ranges.getAllCoords()) {

					g.drawImage((Image) game.getBox(coord).image, coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
				}
			}
		};

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int x  = e.getX() / IMAGE_SIZE;
				int y  = e.getY() / IMAGE_SIZE;
				Coord coord = new Coord(x, y);
				if(e.getButton() == MouseEvent.BUTTON1)
					game.pressLeftButton (coord);
				if(e.getButton() == MouseEvent.BUTTON3)
					game.pressRightButton (coord);
				if(e.getButton() == MouseEvent.BUTTON2)
					game.start();
				label.setText(getMessage());
				panel.repaint();
			}

		
		});

		panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE, Ranges.getSize().y * IMAGE_SIZE));
		add(panel);
	}
	private String getMessage() {
		
		switch (game.getState()) {
		case PLAYED: return "Think twice!";
		case BOMBED: return "You lose";
		case WINNER: return "CONGRAGULETIONS!";
		default  : return "Welcome!";
		}
	}

	private void initFrame() {

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Java Sweeper");
		
		setResizable(false);
		setVisible(true);
		pack();
		setLocationRelativeTo(null);
		setIconImage(getImage("icon"));

	}

	private void setImeges() {
		for (Box box : Box.values()) {
			box.image = getImage(box.name().toLowerCase());
		}
		;
	}

	private Image getImage(String name) {
		String fileName = "/img/" + name.toLowerCase() + ".png";
		ImageIcon icon = new ImageIcon(getClass().getResource(fileName));
		return icon.getImage();
	}

}

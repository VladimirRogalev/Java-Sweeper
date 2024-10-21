

import java.awt.*;
import sweeper.Box;
import sweeper.Coord;

import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import sweeper.Box;

public class JavaSweeper extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private final int COLS = 15;
	private final int ROWS = 1;
	private final int IMAGE_SIZE = 50;

	public static void main(String[] args) {
		new JavaSweeper().setVisible(true);
	}

	private JavaSweeper() {
		setImeges();
		initPanel();
		initFrame();
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
				for(Box box: Box.values()) {
					Coord coord = new Coord(box.ordinal()*IMAGE_SIZE, 0);
				
				g.drawImage((Image)box.image,coord.x, coord.y, this);
			}
			}
		};
		panel.setPreferredSize(new Dimension(COLS * IMAGE_SIZE, ROWS * IMAGE_SIZE));
		add(panel);
	}

	private void initFrame() {
		pack();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Java Sweeper");
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setIconImage(getImage("icon"));

	}
	
	private void setImeges() {
		for( Box box : Box.values() ) {
			box.image = getImage(box.name().toLowerCase());
		};
	}

	private Image getImage(String name) {
		String fileName = "/img/" + name.toLowerCase() + ".png";
		ImageIcon icon = new ImageIcon(getClass().getResource(fileName));
		return icon.getImage();
	}

}

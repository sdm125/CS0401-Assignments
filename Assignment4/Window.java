import javax.swing.*;
import java.awt.*;

public class Window extends JFrame
{
	public Window(int numBallots)
	{
		final int WINDOW_WIDTH = 550;
		final int WINDOW_HEIGHT = 250;

		setTitle("E-Vote 1.0");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		/*
			Takes the @param numBallots to set the correct number of columns in the window.
			Adds one for the login and vote buttons.
		*/

		setLayout(new GridLayout(1, numBallots + 1));
	}
}
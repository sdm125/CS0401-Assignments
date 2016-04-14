import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.awt.*;
import java.awt.event.*;

public class Ballot
{
	private JPanel panel = new JPanel();
	private JLabel officeLabel;
	private String[] strParts;  // Holds split string.
	private int ballotId;
	private String office;
	private String[] candidates;
	private int[] votes;
	private String vote;
	private static int line = 1; // Tracks line in ballot text file.

	public Ballot(ArrayList<String> ballotFileContents)
	{
		strParts = ballotFileContents.get(line++).split(":");
		ballotId = Integer.parseInt(strParts[0]);
		office = strParts[1];
		candidates = strParts[2].split(",");
		votes = new int[candidates.length];
	}

	// Builds ballot panel with contents from ballot.txt.
	private void buildPanel()
	{
		officeLabel = new JLabel(office, SwingConstants.CENTER);
		panel.add(officeLabel);
		panel.setLayout(new GridLayout(candidates.length + 1, 1));

		for(int i = 0; i < candidates.length; i++){
			panel.add(new JButton(candidates[i]));
		}
	}

	// Returns built ballot panel. 
	public JPanel getPanel()
	{
		buildPanel();
		toggleBtns("off");
		return this.panel;
	}

	// Toggles ballot buttons on or off @ param toggle.
	public void toggleBtns(String toggle)
	{
		Component[] components = panel.getComponents();
		for(int i = 0; i < components.length; i++){
			if(components[i] instanceof JButton){
				if(toggle.equals("off")){
            		components[i].setEnabled(false);
            	}
            	else if(toggle.equals("on")){
            		JButton btn = (JButton)components[i];
            		btn.setEnabled(true);
            		btn.addActionListener(new BallotBtnListener());
            	}
			}
		}
	}

	// Resets ballot buttons text to black.
	public void resetBtns()
	{
		Component[] components = panel.getComponents();
		for(int i = 0; i < components.length; i++){
			if(components[i] instanceof JButton){
				JButton btn = (JButton)components[i];
				btn.setForeground(Color.BLACK);
			}
		}
	}

	// Logs vote casts by user to votes array. Writes results to ballot file.
	public void vote(String candidate)
	{
		for(int i = 0; i < candidates.length; i++){
			if(candidates[i].equals(candidate)){
				votes[i]++;
			}
		}
		writeFile();
	}

	// Returns vote cast in ballot.
	public String getVote()
	{
		return this.vote;
	}

	// Sets vote variable to ballot button ActionListener e.getActionCommand().
	public void setVote(String candidate)
	{
		this.vote = candidate;
	}

	/*
		Updates the ballotId.txt file once the user has voted. Creates a backup copy first
		then copies the backup file to the main ballotId.txt file. Deletes the backup file
		once it has been copied to the ballotId.txt file.
	*/

	private boolean writeFile()
	{
		try{
			PrintWriter outputFile = new PrintWriter("backup.txt");
			for(int i = 0; i < candidates.length; i++){
				outputFile.println(candidates[i] + ":" + votes[i]);
			}
			outputFile.close();
			File a = new File(this.ballotId + ".txt");
			File b = new File("backup.txt");
			Files.copy(b.toPath(), a.toPath(), StandardCopyOption.REPLACE_EXISTING);
			Files.delete(b.toPath());
			return true;
		}catch (IOException ioex){
			return false;
		}
	}

	/*
		Resets all ballot buttons text to black. Sets clicked ballot button text to red.
		Calls setVotes and passes clicked ballot button e.getActionCommand().
	*/
	
	private class BallotBtnListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			resetBtns();
			JButton btn = (JButton)e.getSource();
			btn.setForeground(Color.RED);
			setVote(e.getActionCommand());
		}
	}
}
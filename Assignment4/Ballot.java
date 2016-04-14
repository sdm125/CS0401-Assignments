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
	private String[] strParts;
	private int ballotId;
	private String office;
	private String[] candidates;
	private int[] votes;
	private String vote;
	private static int line = 1;

	public Ballot(ArrayList<String> ballotFileContents)
	{
		strParts = ballotFileContents.get(line++).split(":");
		ballotId = Integer.parseInt(strParts[0]);
		office = strParts[1];
		candidates = strParts[2].split(",");
		votes = new int[candidates.length];
	}

	private void buildPanel()
	{
		officeLabel = new JLabel(office, SwingConstants.CENTER);
		panel.add(officeLabel);
		panel.setLayout(new GridLayout(candidates.length + 1, 1));

		for(int i = 0; i < candidates.length; i++){
			panel.add(new JButton(candidates[i]));
		}
	}

	public JPanel getPanel()
	{
		buildPanel();
		toggleBtns("off");
		return this.panel;
	}

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

	public void vote(String candidate)
	{
		for(int i = 0; i < candidates.length; i++){
			if(candidates[i].equals(candidate)){
				votes[i]++;
			}
		}
		writeFile();
	}

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

	public String getVote()
	{
		return this.vote;
	}

	public void setVote(String candidate)
	{
		this.vote = candidate;
	}

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
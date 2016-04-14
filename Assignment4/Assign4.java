import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;

public class Assign4
{
	private static ArrayList<Voter> voters = new ArrayList<>();
	private static ArrayList<Ballot> ballots = new ArrayList<>();
	private static JButton login = new JButton("Login to Vote");
	private static JButton vote = new JButton("Cast Your Vote");
	private static int id; // Holds the voters id entered by the user

	public static void main(String[] args) throws IOException
	{
		FileReader ballotReader = new FileReader(args[0]); // Reads in ballots.txt from command line.
		FileReader voterReader = new FileReader("voters.txt");	
		Window window = new Window(ballots.size()); // Passed ballots.size() to get number of columns.
		JPanel loginPanel = new JPanel();
		JPanel votePanel = new JPanel();

		// Create Ballot objects in ballots array.
		for(int i = 1; i < ballotReader.getContent().size(); i++){
			ballots.add(new Ballot(ballotReader.getContent()));
		}
		
		// Create Voter objects in ballots array.
		for(int i = 0; i < voterReader.getContent().size(); i++){
			voters.add(new Voter(voterReader.getContent()));
		}

		// Add ballot JPanels to window.
		for(int i = 0; i < ballots.size(); i++){
			window.add(ballots.get(i).getPanel());
		}

		loginPanel.setLayout(new GridBagLayout());
		votePanel.setLayout(new GridBagLayout());

		login.addActionListener(new LoginBtnListener());
		vote.addActionListener(new VoteBtnListener());
		vote.setEnabled(false);

		loginPanel.add(login);
		votePanel.add(vote);
		window.add(loginPanel);
		window.add(votePanel);

		window.setVisible(true);
	}

	private static class LoginBtnListener implements ActionListener
	{
		/*
			Checks user entered id against voters objects. If there is a match and 
			the user hasn't voted yet, the ballot and Cast Vote buttons are enabled.
			If the user has already voted a message will display saying they've already
			voted.
		*/

		public void actionPerformed(ActionEvent e)
		{
			String input = JOptionPane.showInputDialog("Please enter your voter id");
			id = Integer.parseInt(input);
			for(int i = 0; i < voters.size(); i++){
				if(id == voters.get(i).getId()){
					if(voters.get(i).getHasVoted() == false){
						JOptionPane.showMessageDialog(null, voters.get(i).getName() 
						+ ", please make your choices");
						for(int j = 0; j < ballots.size(); j++){
							ballots.get(j).toggleBtns("on");
						}
						vote.setEnabled(true);
						login.setEnabled(false);
					}
					else{
						JOptionPane.showMessageDialog(null, voters.get(i).getName() 
						+ ", you have already voted!");
					}
				}
			}
		}
	}

	private static class VoteBtnListener implements ActionListener
	{	
		/*
			Dialog confirms user votes. The votes are recorded in the individual ballot files.
			The ballot and Cast Vote buttons and disabled again. The users voter id has indicated
			that they have cast their vote.
		*/
		public void actionPerformed(ActionEvent e)
		{
			int dialogResult = JOptionPane.showConfirmDialog (null, "Confirm Vote?");
			if(dialogResult == JOptionPane.YES_OPTION){
				JOptionPane.showMessageDialog(null, "Thank you for your vote!");
				for(int i = 0; i < voters.size(); i++){
					if(id == voters.get(i).getId()){
						voters.get(i).setVoted(true);
					}
				}
				writeFile();
				for(int j = 0; j < ballots.size(); j++){
					ballots.get(j).toggleBtns("off");
					ballots.get(j).resetBtns();
				}
				vote.setEnabled(false);
				login.setEnabled(true);
				for(int i = 0; i < ballots.size(); i++){
					ballots.get(i).vote(ballots.get(i).getVote());
				}
			}
		}
	}

	/*
		Updates the voters.txt file once the user has voted. Creates a backup copy first
		then copies the backup file to the main voters.txt file. Deletes the backup file
		once it has been copied to the voters.txt file.
	*/

	public static boolean writeFile()
	{
		try{
			PrintWriter outputFile = new PrintWriter("backup.txt");
			for(int i = 0; i < voters.size(); i++){
				outputFile.println(voters.get(i).getId() + ":" + voters.get(i).getName() 
				+ ":" + voters.get(i).getHasVoted());
			}
			outputFile.close();
			File a = new File("voters.txt");
			File b = new File("backup.txt");
			Files.copy(b.toPath(), a.toPath(), StandardCopyOption.REPLACE_EXISTING);
			Files.delete(b.toPath());
			return true;
		}catch (IOException ioex){
			return false;
		}
	}
}

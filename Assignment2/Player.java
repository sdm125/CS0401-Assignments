import java.util.Scanner;
import java.io.*;

public class Player {

	private String name;
	private float money;
	private int handsPlayed;
	private int handsWon;

	public Player(){
		name = "";
		money = 100;
		handsPlayed = 0;
		handsPlayed = 0;
	}

	// Sets user name

	public void setName(String name){
		this.name = name;
	}

	// Returns name

	public String getName(){
		return this.name;
	}

	// Returns hands played

	public int getHandsPlayed(){
		return handsPlayed;
	}

	// Returms hands won

	public int getHandsWon(){
		return handsWon;
	}

	// Increments handsPlayed by 1

	public void setHandsPlayed(){
		handsPlayed++;
	}

	// Increments hands won by 1

	public void setHandsWon(){
		handsWon++;
	}

	// Returns money

	public float getMoney(){
		return money;
	}

	/*
		Adds money if type is add. Subtracts from money if type is sub.
		If money is 0, user information is saved and game exits.
	*/

	public void setMoney(String type, float amount) throws IOException{
		if(type == "add"){
			money += amount;
		}
		else if(type == "sub"){
			money -= amount;
			if(money == 0){
				System.out.println("You have no more money to bet!");
				System.out.println("Thank you for playing Infinite Blackjack!");
				writeFile();
				System.exit(0);
			}
		}
	}

	// Writes user information to a file NAME.txt

	public void writeFile() throws IOException{
		PrintWriter outputFile = new PrintWriter(name.toUpperCase() + ".txt");
		outputFile.println(money);
		outputFile.println(handsPlayed);
		outputFile.println(handsWon);
		outputFile.close();
	}

	// Reads data from user file if file exists. Returns false is doesn't exist.

	public boolean readFile(String name) throws IOException{
		boolean exists;
		this.name = name;
		File userFile = new File(name + ".txt");
		if(userFile.exists()){
			exists = true;
			Scanner inputFile = new Scanner(userFile);
			money = inputFile.nextFloat();
			handsPlayed = inputFile.nextInt();
			handsWon = inputFile.nextInt();
			inputFile.close();
		}
		else {
			exists = false;
		}
		return exists;
	}

}
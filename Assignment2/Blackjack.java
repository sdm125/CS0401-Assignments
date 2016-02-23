import java.util.Scanner;
import java.io.*;

public class Blackjack {

	// Finds best score for hand accounting for ace value being 1 or 11

	private static int getScore(int points, int numAces) {
		int score = 0;
		
		if (numAces == 0 || points <= 21) {
		    score = points;
		} 
		else {
		    int bestScore = -1;
		    int potentialScore = points;
		    
		    for (int j = 0; j <= numAces; j++) {
				potentialScore = (points - (10 * j));
				
				if (potentialScore > bestScore && potentialScore <= 21) {
				    bestScore = potentialScore;
				}
		    }

		    if (bestScore == -1) {
				score = potentialScore;
		    } 
		    else {
				score = bestScore;
		    }
		}
		return score;
    }

    // Determines if player or deal won a hand. Adds to hands won if hand is won. Adds, subtracts money won or lost.

	public static void whoWon(Player player, int playerScore, int dealerScore, float bet) throws IOException{
		if(playerScore > dealerScore){
			System.out.println("Player won!");
			System.out.println("You won " + bet + "!");
			player.setMoney("add", bet);
			player.setHandsWon();
		}
		else if(dealerScore > playerScore){
			System.out.println("Dealer won!");
			System.out.printf("You lost %.2f", bet);
			System.out.print("!\n");
			player.setMoney("sub", bet);
		}
		else {
			System.out.println("Push");
		}
	}

	public static void main(String[] args) throws IOException{

		float bet;
		String name;
		String playHand;
		String hitOrStay;
		Scanner input = new Scanner(System.in);
		Player player = new Player();
		Card card1 = new Card();
		Card card2 = new Card();
		int playerScore;
		int dealerScore;
		int numAces;

		// If a user has played before their game information will be loaded

		System.out.println("Welcome to Infinite Blackjack!\n");
		System.out.print("Enter name > ");
		if(player.readFile(input.next())){
			System.out.println("Welcome, Back " + player.getName() + "!");
			System.out.println("Everything is just how you left it.");
		}
		else {
			System.out.println("Welcome, " + player.getName() + "!");
			System.out.println("Have fun on your first time playing Infinite Blackjack.");	
		}

		do{
			playerScore = 0;
			dealerScore = 0;
			numAces = 0;
			bet = 0;
			System.out.println("\nName:\t\t\t" + player.getName());
			System.out.println("Total Hands:\t\t" + player.getHandsPlayed());
			System.out.println("Hands Won:\t\t" + player.getHandsWon());
			System.out.printf("Money:\t\t\t%.2f\n\n", player.getMoney());

			/* 
				Input repeats unless users enter y, yes, n, no. If no, player's 
				information is saved to their file
			*/

			do{
				System.out.print("Play a hand? (Y/N) > ");
				playHand = input.next().toLowerCase();
			}while(!playHand.equals("y") && !playHand.equals("n") && !playHand.equals("yes") && !playHand.equals("no"));
			if(playHand.equals("n")){
				player.writeFile();
				input.close();
				System.exit(0);
			}

			/*
				Input repeats unless bet is <= the amount of money they have and
				a positive floating point number.
			*/

			do{
				System.out.print("\nEnter amount to bet > ");
				if(input.hasNextFloat()){
					bet = input.nextFloat();
					if(bet < 0){
						System.out.println("Please enter a valid value, from 0.01 " + player.getMoney());
					}
					else if(bet > player.getMoney()){
						System.out.println("Please enter a valid value, from 0.01 " + player.getMoney());
						System.out.print("\nEnter amount to bet > ");
					}
					else{
						break;
					}
				}
				else{
					System.out.println("Please enter a value in valid format (e.g. 4.50).");
					input.next();
				}
			}while(bet < 0 || !input.hasNextFloat() || bet > player.getMoney());

			/*
				player.setHandsPlayed() adds one to total hands played.
				card1.cardGen() generates a new random card.
			*/

			player.setHandsPlayed();
			card1.cardGen();
			card2.cardGen();

			// if the card is an ace, the value will be 11

			if(card1.getCardVal() == 11){
				numAces++;
			}
			else if(card2.getCardVal() == 11){
				numAces++;
			}
			System.out.println("PLAYER DEAL");
			System.out.println("Cards: " + card1.getCardStr() + " " + card2.getCardStr());
			
			// calculates player score based on the two card values and number of aces.

			playerScore = getScore(card1.getCardVal() + card2.getCardVal(), numAces);
			System.out.println("Score: " + playerScore);
			if(playerScore == 21){
				System.out.println("Blackjack!");
				System.out.println("You won $" + (bet * 1.5));
				player.setMoney("add", bet * (float)1.5);
				player.setHandsWon();
			}
			else{
				do{
					System.out.print("[H]it or [S]tay? > ");
					hitOrStay = input.next().toLowerCase();
				}while(!hitOrStay.equals("h") && !hitOrStay.equals("hit") && !hitOrStay.equals("s") && !hitOrStay.equals("stay"));
				
				if(hitOrStay.equals("h") || hitOrStay.equals("hit")){
					/* 
						Generates a new random card each time loop executes.
						getScore method calculates the score for each new card
						added to playerScore.
					*/
					do{
						card1.cardGen();
						if(card1.getCardVal() == 11){
							numAces++;
						}
						playerScore = getScore(playerScore + card1.getCardVal(), numAces);
						System.out.println("Card dealt: " + card1.getCardStr());
						System.out.println("Score: " + playerScore);
						if(playerScore > 21){
							System.out.println("Player busted!");
							System.out.printf("You lost $%.2f\n", bet);
							player.setMoney("sub", bet);
							break;
						}
						do{
							System.out.print("[H]it or [S]tay? > ");
							hitOrStay = input.next().toLowerCase();
						}while(!hitOrStay.equals("h") && !hitOrStay.equals("hit") && !hitOrStay.equals("s") && !hitOrStay.equals("stay"));
					}while(hitOrStay.equals("h") || hitOrStay.equals("hit"));
				}
				if(hitOrStay.equals("s") || hitOrStay.equals("stay") || playerScore <= 21){
					System.out.println("Player score: " + playerScore);
					

					// Player stays dealer draws two cards. numAces reset for dealer hand.

					System.out.println("DEALER DEAL");
					card1.cardGen();
					card2.cardGen();
					numAces = 0;
					if(card1.getCardVal() == 11){
						numAces++;
					}
					else if(card2.getCardVal() == 11){
						numAces++;
					}
					dealerScore = getScore(card1.getCardVal() + card2.getCardVal(), numAces);
					System.out.println("Cards: " + card1.getCardStr() + " " + card2.getCardStr());
					System.out.println("Score: " + dealerScore);

					/*
						If the score is greater than 18 and less than 21, it will stay.
						If the score is 17 without any aces, it will stay.
						whoWon function will determine winner.
					*/ 

					if((dealerScore >= 18 && dealerScore <= 21) || (dealerScore == 17 && numAces == 0)){
						System.out.println("Stay!");
						System.out.println("Player score: " + playerScore);
						System.out.println("Dealer score: " + dealerScore);
						whoWon(player, playerScore, dealerScore, bet);
					}

					/*
						If the score is 17 and numAces > 0 or score is less than 17,
						dealer will hit. Dealer will continue hitting until busted or falls
						into if statement above.
					*/ 

					else if((dealerScore == 17 && numAces > 0) || dealerScore < 17){
						do{
							System.out.println("Hit!");
							card1.cardGen();
							if(card1.getCardVal() == 11){
								numAces++;
							}
							dealerScore = getScore(dealerScore + card1.getCardVal(), numAces);
							System.out.println("Card dealt: " + card1.getCardStr());
							System.out.println("Score: " + dealerScore);
							if(dealerScore > 21){
								System.out.println("Dealer busted!");
								System.out.printf("You won $%.2f\n", bet);
								player.setMoney("add", bet);
								player.setHandsWon();
								break;
							}
							if(dealerScore >= 18){
								System.out.println("Stay!");
								System.out.println("Player score: " + playerScore);
								System.out.println("Dealer score: " + dealerScore);
								whoWon(player, playerScore, dealerScore, bet);
							}
						}while(dealerScore < 18);
					}
				}	
			}
		}while(playHand.equals("y") || playHand.equals("yes"));
		input.close();
		System.exit(0);
	}

}
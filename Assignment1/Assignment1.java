/* Stacy Mackin
   CS401 TuesThurs 1:00PM - 2:15PM
   Section 10769 Class #1140
   This is a point-of-sale system for a book store. Users can select quantities of books, bookmarks, and paintings of books
   to calculate the total cost for the customer. Every third customer gets a 10% discount. 
*/

import java.util.Scanner;

public class Assignment1 {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		boolean newCustomer = true;
		int menuSelection;
		int customerQueue = 1;
		int customerCount = 0;
		int books = 0;
		int bookmarkTotal = 0;
		int bookmarkPacks = 0;
		int bookmarks = 0;
		int paintings = 0;
		double subTotal;
		double discount = 0;
		double tax;
		double total;
		float paid;
		double change;

		//Loop while there are customer to wait on
		while(customerQueue == 1){
			//Checks if there is a new customer or not
			if(newCustomer){
				System.out.print("More customers in line? (1 = Yes, 2 = No) ");
				customerQueue = scan.nextInt();
				if(customerQueue == 2){
					System.out.println("No more customers! Closing!");
					System.exit(0);
				}
				// New customer set to false so new customer prompt will not run until after checkout
				newCustomer = false;
			}
			System.out.print("1 - Buy Books - $5.00 each" +
							 "\n2 - Buy Bookmarks - $1.00 each, $5.00 for a six pack" +
							 "\n3 - Buy Paintings of Books - $100.00 each" +
							 "\n4 - See current order" +
							 "\n5 - Checkout" + "\n");
			System.out.print("Please enter a valid option (1 through 5) > ");
			menuSelection = scan.nextInt();
			//If menu input is greater than 5 or less than one repeat this questions
			while(menuSelection > 5 || menuSelection < 1){
				System.out.print("Please enter a valid option (1 through 5) > ");
				menuSelection = scan.nextInt();
			}
			if(menuSelection == 1){
				System.out.println("Currently in cart: " + books + " books.");
				System.out.print("How many would you like to buy? > ");
				books = scan.nextInt();
				//Loops until books bought is 0 or greater
				while(books < 0){
					System.out.print("Enter a valid number of books - 0 or more > ");
					books = scan.nextInt();
				}
			}
			else if(menuSelection == 2){
				System.out.println("Currently in cart: " + 	bookmarkTotal + " bookmarks.");
				System.out.print("How many would you like to buy? > ");
				bookmarkTotal = scan.nextInt();
				//Loops until bookmarks bought is 0 or greater
				while(bookmarkTotal < 0){
					System.out.print("Enter a valid number of bookmarks - 0 or more > ");
					bookmarkTotal = scan.nextInt();
				}
				// Divides bookmarks into packs. Assigns remainder to single bookmarks.
				bookmarkPacks = bookmarkTotal / 6;
				bookmarks = bookmarkTotal % 6;
			}
			else if(menuSelection == 3){
				System.out.println("Currently in cart: " + paintings + " paintings.");
				System.out.print("How many would you like to buy? > ");
				paintings = scan.nextInt();
				//Loops until paintings bought is 0 or greater
				while(paintings < 0){
					System.out.print("Enter a valid number of paintings - 0 or more > ");
					paintings = scan.nextInt();
				}
			}
			else if(menuSelection == 4){
				System.out.print("Currently in cart:" +
								  "\nBooks: " + books +
								  "\nBookmarks: " + bookmarkTotal + 
								  "\nPaintings of Books: " + paintings + "\n");
			}
			else if(menuSelection == 5){
				System.out.println("--------------------------------------");

				subTotal = (books * 5) + bookmarks + (bookmarkPacks * 5) + (paintings * 100);
				//Determine whether or not to apply discount
				if(customerCount == 2){
					System.out.println("You won a 10% discount!");
					discount = subTotal * 0.10;
					customerCount = 0;
				}
				else {
					System.out.println("You did not get a discount! Better luck next time!");
					//Increment customer count so every 3rd customer gets the 10% discount
					customerCount++;
				}

				subTotal -= discount;
				tax = subTotal * 0.07;
				total = subTotal + tax;

				if(books > 0){
					System.out.println("\t" + books + " Book(s):" + "\t\t$" + (books * 5) + ".00");
				}
				if(bookmarkPacks > 0){
					System.out.println("\t" + bookmarkPacks + " Bookmark Pack(s):" + "\t$" + (bookmarkPacks * 5) + ".00");
				}
				if(bookmarks > 0){
					System.out.println("\t" + bookmarks + " Bookmark(s):" + "\t\t$" + bookmarks + ".00");
				}
				if(paintings > 0){
					System.out.println("\t" + paintings + " Painting(s):" + "\t\t$" + (paintings * 100));
				}
				if(discount > 0){
					System.out.printf("\n" + "Discount! Saved:" + "\t       -$%,.2f", discount);
				}
				System.out.printf("\n" + "SubTotal:" + "\t\t\t$%,.2f", subTotal);
				System.out.printf("\n" + "Tax:" + "\t\t\t\t$%,.2f\n", tax);
				System.out.printf("\n" + "Total:" + "\t\t\t\t$%,.2f", total);
				System.out.println("\n" + "--------------------------------------");
				System.out.print("\n" + "Enter amount paid (no dollar sign) > ");
				paid = scan.nextFloat();
				//if amount paid is less than total, prompt will ask to re-enter
				while(paid < total){
					System.out.println("Not enough money, please re-enter.");
					System.out.print("Enter amount paid (no dollar sign) > ");
					paid = scan.nextFloat();
				}
				change = paid - total;
				System.out.printf("\n" + "Your change is: $%,.2f", change);
				System.out.println("\n" + "Thank you for shopping!");

				//Reset variables
				newCustomer = true;
				books = 0;
				bookmarkTotal = 0;
				bookmarks = 0;
				bookmarkPacks = 0;
				paintings = 0;
				discount = 0;
				tax = 0;
				subTotal = 0;
				total = 0;
			}
		}
		scan.close();
		System.exit(0);
	}

}
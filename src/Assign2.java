import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Purpose:  This class is the method main for Assignment 2
 * It contains the main menu options for the user.
 * This assignment further develops the first one by adding input and output to files, changing from Array to ArrayList for the resourcesBorrowed, and incorporating insertionSort and binary search.
 * @author Jamie Harnum
 * Course: CST8130
 * Lab Section: 313
 *
 * Known Issues: There is no handling for bad String input in this program
 */
public class Assign2 {

	public static void main(String[] args) {
		
		MyDate todayDate = new MyDate();
		
		int option = 0;
		
		Library myLib = new Library();
		
		Scanner in = new Scanner(System.in);
		Scanner fileInput; //for input from file
		
		do { //menu until 9 is selected
			
			System.out.println("Library Checkout System Menu");
			System.out.println("---------------------------------");
			System.out.println("Enter 1 to add a resource to borrow");
			System.out.println("Enter 2 to display overdue items");
			System.out.println("Enter 3 to display currently checked out resources");
			System.out.println("Enter 4 to delete a borrowed resource when it is returned");
			System.out.println("Enter 5 to change today's date");
			System.out.println("Enter 6 to add resources from a file");
			System.out.println("Enter 7 to save resources to a file");
			System.out.println("Enter 8 to search for a resource");
			System.out.println("Enter 9 to quit");
			
			System.out.println("Today's date is currently set to: " + todayDate.toString());
			
			try {
				
				option = in.nextInt();
			
			} catch (InputMismatchException e) {
				
				System.out.println("Invalid option, please input an integer");
				in.next();//dump invalid input
				
			}
			
			if (option==1) {
				
				//add resource to borrow
				if (myLib.inputResource(in, todayDate)) {
					System.out.println("Resource has been successfully added");
				} else {
					System.out.println("Something went wrong - resource has not been added.");
				}
	
			} else if (option==2) {
				
				//display overdue items
				System.out.println(myLib.resourcesOverdue(todayDate));
				
			} else if (option==3) {
				
				//display currently checked out resources (entire array)
				System.out.println(myLib.toString());
			
			} else if (option==4) {
				
				//display all items first
				System.out.println(myLib.toString());
				//delete borrowed resource
				myLib.deleteResource(in, todayDate);
			
				//display all items again to verify that it worked lol
				System.out.println(myLib.toString());
				
			} else if (option==5) {

				//change today's date
				System.out.println("Change today's date");
				todayDate.inputDate(in);

			} else if (option==6) {

				boolean stopRead = false;

				do {
					System.out.println("Please input the file you would like to read from or enter 'stop' to return to menu");
					String fileName = in.next();

					if (fileName.equalsIgnoreCase("stop")) {

						stopRead = true;

					} else if(fileName.matches("(.*).txt")) {

						try {
							File file = new File(fileName);
							fileInput = new Scanner(file);

							System.out.println("Reading from file...");

							if(myLib.inFromFile(fileInput)) {
								//print everything including added resources
								stopRead = true;
							}
						} catch(FileNotFoundException e) {
							System.out.println("File not found");
						}

					} else {
						System.out.println(fileName + " is not a valid filename. Please enter a .txt file");
					}
				} while(!stopRead);


			} else if (option==7) {

				myLib.fileSave(in);


			} else if (option==8) {

				myLib.searchResources(in);

			} else if (option>9 || option<1) {
				
				System.out.println("Invalid option, please input a value between 1 and 9");
				
			}

			if(option!=9) {
				option = 0; //reset option to 0 so that if user inputs a bad value on the next go around it won't automatically do whatever the last successful option was
			}

		} while (option!=9); //continue to show menu until user selects 9
		
		in.close();
		System.out.println("Exiting program");
		
	}

}

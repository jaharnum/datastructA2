import java.util.Scanner;

/**
 * Purpose:  The Book class is a child of the Resource class that adds an author parameter.
 * @author Jamie Harnum
 * Course: CST8130
 * Lab Section: 313
 *
 * Data Members: Inherits data members from Resource
 * 				 author: String - the author of the book.
 *
 * Methods:		Book() - default constructor, sets overdueCost
 * 				inputResource() - extends Resource inputResource() and gets user input for the author
 * 				inFromFile - Uses a String from the Library inFromFile method to create a new Rersource, returns true if successfull
 * 				calcDueDate() - returns date 14 days after today
 * 				saveResource() - returns a string representation of a DVD formatted to be able to be read by the program
 *				toString() - returns string representation of the Book's data members formatted for user readability
 * 
 */
public class Book extends Resource {
	
	private String author;
	
	public Book() {
		overdueCost = 2;
	}
	
	public boolean inputResource(Scanner in, MyDate today) {
	
		System.out.println("Who is the author?");
		author = in.next();
		
		return super.inputResource(in, today);
		
	}

	public boolean inFromFile(String[] array){
		if(super.inFromFile(array)) {
			this.author = array[7];
			return true;
		} else {
			return false;
		}
	}

	public MyDate calcDueDate(MyDate today) { //adds 14 days to get the dueDate

		for(int i=0; i<14; i++) {
			dueDate.addOne();
		}

		return dueDate;
	}

	public String saveResource() {
		return "b " + super.saveResource() + " " + this.author + "\n";
	}

	public String toString() {
		return "Author: " + this.author + " " + super.toString();
	}
}

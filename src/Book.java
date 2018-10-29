import java.util.Scanner;

/**
 * Purpose:  The Book class is a child of the Resource class that adds an author parameter.
 * @author Jamie Harnum
 * Course: CST8130
 * Lab Section: 313
 * Data Members: Inherits data members from Resource
 * 				 author: String - the author of the book.
 * Methods:		Book() - default constructor, sets overdueCost
 * 				inputResource() - extends Resource inputResource() and gets user input for the author
 * 				toString() - returns string representation of the Book's data members
 * 				calcDueDate() - returns date 14 days after today
 * 
 */
public class Book extends Resource {
	
	protected String author;
	
	public Book() {
		overdueCost = 2;
	}
	
	public boolean inputResource(Scanner in, MyDate today) {
	
		System.out.println("Who is the author?");
		author = in.next(); //TODO check is not null
		
		if(super.inputResource(in, today)) {
		
		return true;
		} else {
			return false;
		}
		
	}

	public boolean inFromFile(String[] array){
		if(super.inFromFile(array)) {
			this.author = array[7];
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		String s = "Author: " + this.author + " " + super.toString();
		return s;
	}

	public MyDate calcDueDate(MyDate today) { //adds 14 days to get the dueDate

		for(int i=0; i<14; i++) {
			dueDate.addOne();
		}

		return dueDate;
	}
}

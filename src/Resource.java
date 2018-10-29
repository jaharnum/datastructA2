import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Purpose:  This class is the parent class for all resources available to be borrowed from the Library.
 * @author Jamie Harnum
 * Course: CST8130
 * Lab Section: 313
 * Data Members: title: String - title of the Resource
 * 				 borrower: String - Name of the person borrowing the Resource
 * 				 dueDate: MyDate - Date the Resource is due
 * 				 overdueCost: float - amount it will cost if overdue
 * 
 * Methods:		Resource() - default constructor, initializes dueDate and sets default overdueCost
 * 				inputResource() - Requests user input for Resource values, returns a boolean representation of if the Resources was added successfully
 * 				toString() - Returns a String representation of the Resource data
 * 				isOverDue() - checks if today's date is greater than the due date
 * 				displayOverDue() - displays the String representations of only Resources that are overdue
 */
public class Resource {
	
	protected String title;
	protected String borrower;
	protected MyDate dueDate;
	protected float overdueCost;
	
	public Resource() {
		//default constructor sets cost to 1 (most common) and initializes dueDate
		this.dueDate = new MyDate();
		overdueCost = 1;
	}
	
	public boolean inputResource(Scanner in, MyDate today) {
		//to add new resources being taken out of the library
		this.dueDate = calcDueDate(today); //due two weeks from today
		
		System.out.println("Who is borrowing this resource?");
		borrower = in.next();
		
		System.out.println("What is the title?");
		title = in.next(); 
		
		//TODO if time: check for null inputs, figure out how to dump input after spaces
		
		return true;
		
	}

	public boolean inFromFile(String[] array){
		//TODO exception handling - index out of bounds should be taken care of by the length checks previous to this, but need parseInt checks
		this.title = array[1];
		this.borrower = array[2];
		int month = Integer.parseInt(array[3]);
		int day = Integer.parseInt(array[4]);
		int year = Integer.parseInt(array[5]);
		this.dueDate = new MyDate(month, day, year);

		return true;
	}
	
	public String toString() {
		
		return "Title: " + this.title + " Borrower: " + this.borrower + " Due: " + dueDate.toString() + "\nOverdue fees are: $" + this.overdueCost; //TODO if there's time - get float to display to 2 decimal places
	}

	public boolean isOverDue(MyDate today) {
		
		if(today.isGreaterThan(dueDate)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String displayOverDue() {
			
			return "This resource is overdue. Overdue fees are: $" + overdueCost;
	}

	public MyDate calcDueDate(MyDate today){

		dueDate = today;

		return dueDate;

	}

	public void saveResource(FileWriter outFile) {

		//TODO save handling
		//find out how to tell what child of resource it is coming from - might be able to call super AFTER "b" "m" or "d" are added to file
	}
}

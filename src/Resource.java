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
 * 				Resource(String) - alternate constructor, used to initialize temp Resource with a particular title for comparison
 * 				inputResource(Scanner, MyDate) - Requests user input for Resource values, returns true if the Resource was added successfully
 * 				inFromFile(String) - Uses a String from the Library inFromFile method to create a new Rersource, returns true if successfull
 * 				isOverDue() - checks if today's date is greater than the due date
 * 				displayOverDue() - displays the String representations of only Resources that are overdue
 * 				calcDueDate(MyDate) - uses today's MyDate to calculate the due date of the Resource
 * 				saveResource() - returns a string representation of a Resource formatted to be able to be read by the program
 * 				compareResource(Resource) - Checks if a given newResource is lexigraphically greater than, less than, or equal to the Resource. Returns an int representing the result.
 * 				toString() - Returns a String representation of the Resource data formatted for user readability
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

	public Resource(String title){
		//constructor for search functions

		this.title = title;

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

	public boolean inFromFile(String[] array) {
		//TODO exception handling - index out of bounds should be taken care of by the length checks previous to this, but need parseInt checks
		this.title = array[1];
		this.borrower = array[2];
		int month = Integer.parseInt(array[3]);
		int day = Integer.parseInt(array[4]);
		int year = Integer.parseInt(array[5]);
		this.dueDate = new MyDate(month, day, year);

		return true;
	}

	public boolean isOverDue(MyDate today) {

		if (today.isGreaterThan(dueDate)) {
			return true;
		} else {
			return false;
		}
	}

	public String displayOverDue() {

		return "This resource is overdue. Overdue fees are: $" + overdueCost;
	}

	public MyDate calcDueDate(MyDate today) {

		dueDate = today;

		return dueDate;

	}

	public String saveResource() {

		String save = this.title + " " + this.borrower + " " + this.dueDate.toString() + " " + this.overdueCost;

		return save;

	}

	public int compareResource(Resource newResource) {

		String currentTitle = this.title.toLowerCase();
		String newTitle = newResource.title.toLowerCase();

		if (currentTitle.compareTo(newTitle) < 0) { //if this is less than new, this title should come before new title alphabetically
			return -1;
		} else if(currentTitle.compareTo(newTitle) == 0) {
			return 0; //they are equal
		} else {
			return 1; //this should come after new, new is before this alphabetically
		}
	}

	public String toString() {

		return "Title: " + this.title + " Borrower: " + this.borrower + " Due: " + dueDate.toString() + "\nOverdue fees are: $" + this.overdueCost + "\n"; //TODO if there's time - get float to display to 2 decimal places
	}

}

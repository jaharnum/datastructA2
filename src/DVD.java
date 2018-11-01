import java.util.Scanner;

/**
 * Purpose:  The DVD class is a child of the Resource class that adds a type parameter.
 * @author Jamie Harnum
 * Course: CST8130
 * Lab Section: 313
 *
 * Data Members: Inherits data members from Resource
 * 				type: String - representation of type of DVD. Currently no restrictions set on what types available.
 * 
 * Methods: 	DVD() - default constructor, inherits overdue cost from Resource
 * 				inputResource() - extends Resource inputResource() and gets user input for the DVD type
 * 				inFromFile - Uses a String from the Library inFromFile method to create a new Rersource, returns true if successfull
 * 				calcDueDate() - adds 3 days to the date the resource was checked out
 * 				saveResource() - returns a string representation of a DVD formatted to be able to be read by the program
 * 				toString() - returns string representation of the DVD's data members formatted for user readability
 * 
 */
public class DVD extends Resource {

	private String type;
	
	public DVD() {
		
	}
	
	public boolean inputResource(Scanner in, MyDate today) {

		System.out.println("What type of DVD?");
		type = in.next();

		return super.inputResource(in, today);
	}

	public boolean inFromFile(String[] array){
		if (super.inFromFile(array)){
			this.type = array[7];
			return true;
		} else {
			return false;
		}
	}

	public MyDate calcDueDate(MyDate today) { //adds 3 days to get the dueDate

		for(int i=0; i<3; i++) {
			dueDate.addOne();
		}

		return dueDate;
	}

	public String saveResource() {
		return "d " + super.saveResource() + " " + type + "\n";
	}

	public String toString() {
		return"Type of DVD: " + this.type + " " + super.toString();
	}
}

import java.util.Scanner;

/**
 * Purpose:  The DVD class is a child of the Resource class that adds a type parameter.
 * @author Jamie Harnum
 * Course: CST8130
 * Lab Section: 313
 * Data Members: Inherits data members from Resource
 * 				type: String - representation of type of DVD. Currently no restrictions set on what types available.
 * 
 * Methods: 	DVD() - default constructor, inherits overdue cost from Resource
 * 				inputResource() - extends Resource inputResource() and gets user input for the DVD type
 * 				toString() - returns string representation of the DVD's data members
 * 
 */
public class DVD extends Resource {

	protected String type;
	
	public DVD() {
		
	}
	
	public boolean inputResource(Scanner in, MyDate today) {

		System.out.println("What type of DVD?");
		type = in.next();

		
		if(super.inputResource(in, today)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean inFromFile(String[] array){
		if (super.inFromFile(array)){
			this.type = array[7];
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		String s = "Type of DVD: " + this.type + " " + super.toString();
		return s;
	}

	public MyDate calcDueDate(MyDate today) { //adds 3 days to get the dueDate

		for(int i=0; i<3; i++) {
			dueDate.addOne();
		}

		return dueDate;
	}

	public String saveResource() {

		String save = "d " + super.saveResource() + " " + type + "\n";

		return save;
	}
}

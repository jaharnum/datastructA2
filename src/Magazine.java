import java.util.Scanner;

/**
 * Purpose:  The DVD class is a child of the Resource class that adds a type parameter.
 * @author Jamie Harnum
 * Course: CST8130
 * Lab Section: 313
 * Data Members: Inherits data members from Resource
 * 				 edition: MyDate - the date the Magazine was published
 * 
 * Methods: 	Magazine() - default constructor, inherits overdue cost from Resource
 * 				inputResource() - extends super.inputResource() and gets user input for the edition
 * 				toString() - returns string representation of the Magazine's data members
 * 				calcDueDate() - adds 7 days to the date the resource was checked out
 * 
 */
public class Magazine extends Resource {
	
	protected MyDate edition;
	
	public Magazine() {
		
	}
	
	public boolean inputResource(Scanner in, MyDate today) {
		
		System.out.println("What edition is this magazine?");
		edition = new MyDate();
		edition.inputDate(in); 
		
		super.inputResource(in, today);
		
		//TODO if all values are not null
		return true;
		
	}
	
	public String toString() {
		String s = "Edition: " + edition.toString() + super.toString();
		return s; 
	}

	public MyDate calcDueDate(MyDate today) { //adds 7 days to get the dueDate

		for(int i=0; i<7; i++) {
			dueDate.addOne();
		}

		return dueDate;
	}


}

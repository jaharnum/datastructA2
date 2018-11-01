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
 * 				calcDueDate() - adds 7 days to the date the resource was checked out
 * 				saveResource() - returns a string representation of a Magazine formatted to be able to be read by the program
 * 				toString() - returns string representation of the Magazine's data members formatted for user readability
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

	public boolean inFromFile(String[] array){

		if(super.inFromFile(array)) {

			try {

				int month = Integer.parseInt(array[7]);
				int day = Integer.parseInt(array[8]);
				int year = Integer.parseInt(array[9]);
				edition = new MyDate(month, day, year);

				return true;

			} catch(NumberFormatException e){
				System.out.println("File formatted incorrectly, unable to read file");
				return false;
			}

		} else {
			return false;
		}
	}

	public MyDate calcDueDate(MyDate today) { //adds 7 days to get the dueDate

		for(int i=0; i<7; i++) {
			dueDate.addOne();
		}

		return dueDate;
	}

	public String saveResource() {

		String save = "m " + super.saveResource() + " " + edition.toString() + "\n";

		return save;
	}

	public String toString() {
		String s = "Edition: " + edition.toString() + " " + super.toString();
		return s;
	}

}


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Purpose:  The Library class contains the array of Resources that have been borrowed. 
 * It handles adding and deleting new resources.
 * @author Jamie Harnum
 * Course: CST8130
 * Lab Section: 313
 *
 * Data Members: resourcesBorrowed: ArrayList - main array of Resources for the Library
 * 				 numResources: int - total current number of resources (starting at 0)
 * 
 * Methods: 	Library(): default constructor, initializes the resourcesBorrowed ArrayList
 * 				inputResource(Scanner, MyDate): selects resource type and test user input. Returns true if input is valid.
 * 				inFromFile(Scanner): handles input from file. Returns true if input is valid.
 * 				findIndex(Resource): uses binary search to find the index where a Resource is or should be, based on title. Returns an integer representing the index.
 * 				searchResources(Scanner): Finds a resource matching a title input by the user and either prints the found resource or an error message
 * 				resourcesOverdue(MyDate): Prints a list of all resources overdue as of MyDate given
 * 				deleteResource(Scanner, MyDate): deletes a user selected resource from the array
 *				toString(): a String representation of all items in the resourcesBorrowed array
 */
public class Library {
	
	private ArrayList<Resource> resourcesBorrowed;
	private int numResources = 0; //size() does the same thing but its more convenient to have a variable rather than use a method every time. Possibly more efficient also?
	
	public Library() {

		resourcesBorrowed = new ArrayList();

	}
	
	public boolean inputResource(Scanner in, MyDate today) {

		boolean typeSelected = false;

		Resource newResource = null;
		do { //type selection and initialization
			System.out.println("What type of resource would you like to borrow?");
			System.out.println("Options: ");
			System.out.println("B for book");
			System.out.println("D for DVD");
			System.out.println("M for magazine");

			String resourceType = in.next();

			if (resourceType.equalsIgnoreCase("B")) {
				//book
				newResource = new Book();
				typeSelected = true;

			} else if (resourceType.equalsIgnoreCase("D")) {
				//dvd
				newResource = new DVD();
				typeSelected = true;

			} else if (resourceType.equalsIgnoreCase("M")) {
				//magazine
				newResource = new Magazine();
				typeSelected = true;

			} else {
				System.out.println("Sorry, that's not a valid option");

			}
		} while (!typeSelected);


		if (newResource.inputResource(in, today)) {

			int index = findIndex(newResource);

			if(index<0){
				index = -index;
			} //if index is not negative, item with that title already exists in the array - but we'll add it next to that one anyways

			resourcesBorrowed.add(index, newResource);

			System.out.println("New resource added: " + resourcesBorrowed.get(numResources));
			numResources++;

			return true;
		} else {
			return false;
		}

	}

	public boolean inFromFile(Scanner in){

		if(!in.hasNext()){
			return false; //if there's nothing in the file, what is the point
		}

		while(in.hasNext()){

			String line = in.nextLine();
			System.out.println(line);

			String lineArray[] = line.split(" ");

			int length = lineArray.length;

			if(length!=8&&length!=10){ //every resource must have either 8 or 10 data members
				System.out.println("File incorrectly formatted, cannot read from file");
				return false;
			}

			Resource newResource = null;

			switch(lineArray[0]){ //first item of each line is the character denoting what type of resource it is;
				case "b":
					newResource = new Book();
					break;
				case "d":
					newResource = new DVD();
					break;
				case "m":
					if(length!=10){ //magazines have 10 data members when each member of MyDate is counted separately
						System.out.println("File incorrectly formatted, cannot read from file");
						return false;
					} else {
						newResource = new Magazine();
					}
			}

			if(newResource.inFromFile(lineArray)){

				int index = findIndex(newResource);
				if(index<0){
					index = -index;
				} //if index is not negative, item with that title already exists in the array - but we'll add it next to that one anyways

				resourcesBorrowed.add(index, newResource);
				System.out.println("New resource added: " + resourcesBorrowed.get(numResources));
				numResources++;


			} else {
				System.out.println("Could not read from file");
				return false;
			}
		}

		return true;
	}

	private int findIndex(Resource newResource){

		int index = -1;
		int low = 0;
		int high = resourcesBorrowed.size();
		int mid;

		if(numResources>3) {

			while (low <= high) {

				mid = (low + high) / 2;
				if (resourcesBorrowed.get(mid).compareResource(newResource) < 0)
					low = mid + 1;
				else if (resourcesBorrowed.get(mid).compareResource(newResource) > 0)
					high = mid - 1;
				else
					return mid;
			}

		} else {
			for(int i = 0; i<numResources; i++){
				if(resourcesBorrowed.get(low).compareResource(newResource)<0){
					low++;
				} else if (resourcesBorrowed.get(low).compareResource(newResource)>0){
					break;
				}
			}
		}
		return low;

	}

	public void searchResources(Scanner in){

		System.out.println("Enter the title you wish to search for:");

		String title = in.next();

		Resource newResource = new Resource(title);

		int index = findIndex(newResource);

		int ifFound = -1;

		if(index>=0 && index<numResources) { //if index is not out of bounds, double check that its the same title as the title at that index
			ifFound = resourcesBorrowed.get(index).compareResource(newResource);
		}

		if(ifFound!=0){ //compareResource() will return 0 if they are a complete match
			System.out.println("Sorry, we couldn't find a resource with that title");
		} else {
			System.out.println("Found resource: \n");
			System.out.println("[" + (index+1) + "] - " + resourcesBorrowed.get(index).toString());
		}

	}
	
	public String resourcesOverdue(MyDate today) {
		//what resources are overdue today?
		int numOverdue = 0;
		
		for(int i = 0; i<numResources; i++) {
			if(resourcesBorrowed.get(i).isOverDue(today)) {
				System.out.println("[" + i + "] is overdue.");
				System.out.println("Overdue item info: " + resourcesBorrowed.get(i).toString());
				numOverdue++;
			}
			
		}
		return "Overdue items: " + numOverdue;
	}

	public void deleteResource (Scanner in, MyDate today) {


		if (numResources == 0) {
			System.out.println ("No resources to delete\n");
			return;
		}

		System.out.println ("List of resources checked out in the library: ");
		for (int i=0; i < numResources; i++) {
			System.out.print("[" + (i+1) + "]: " + resourcesBorrowed.get(i).toString());
		}


		int numToDelete = 0;

		do {
			System.out.println ("Enter number of resource to delete: ");
			if (in.hasNextInt()) {
				numToDelete = in.nextInt();
				if (numToDelete < 0 || numToDelete > numResources) {
					System.out.println ("Invalid ...  please reenter number between 1 and " + numResources);
					numToDelete = 0;
				}
			}
			else {
				System.out.println ("Invalid, please enter a valid resource number");
				in.next(); //dump invalid input
			}

		} while (numToDelete==0);

		if (resourcesBorrowed.get(numToDelete-1).isOverDue(today)) {
			resourcesBorrowed.get(numToDelete-1).displayOverDue();
		}

		resourcesBorrowed.remove(numToDelete-1);
		numResources --;
	}

	public void fileSave(Scanner in){

		if(numResources==0){
			System.out.println("There are no resources to save");
		} else { //don't need to ask about saving if there are no resources currently in the system

			String fileName;
			FileWriter outFile;
			boolean goodName;

			do {
				System.out.println("Enter the name of the save file");

				fileName = in.next();

				if(fileName.matches("(.*).txt")) {
					goodName = true;
				} else {
					goodName = false;
					System.out.println(fileName + " is not a valid filename. Please enter a .txt file");
				}

			} while(!goodName);

			try {

				outFile = new FileWriter(fileName, true);

				int unsaved = 0;

				for(int i = 0; i<numResources; i++) {

					try {

						String resource = resourcesBorrowed.get(i).saveResource();
						outFile.append(resource);

					}catch (IOException e){
						System.out.println("Resource " + (i+1) + " was unable to be saved");
						unsaved++; //if any one resource is unable to be saved, an error will be sent at that time and then it will be totaled at the end
					}

				}

				outFile.close();

				if(unsaved==0) {
					System.out.println("Successfully saved " + numResources + " resources to " + fileName);
				} else {
					System.out.println(unsaved + " out of " + numResources + " were unable to be saved to " + fileName);
				}


			} catch (IOException e) {
				System.out.println("Could not open file, save incomplete");

			}

		}

	}

	public String toString() {
		//loop toString for all resources

		if(numResources == 0) {
			return "There are not currently any resources checked out of the library";
		} else {
			String s = "Number of resources currently checked out: " + numResources;
			for(int i = 0; i<numResources; i++) {
				String temp = "\n[" + (i+1) + "] - " + resourcesBorrowed.get(i);
				s = s+temp;
			}
			return s;
		}

	}


}

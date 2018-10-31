
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Purpose:  The Library class contains the array of Resources that have been borrowed. 
 * It handles adding and deleting new resources.
 * @author Jamie Harnum
 * Course: CST8130
 * Lab Section: 313
 * Data Members: resourcesBorrowed[]: Resource - main array of Resources for the Library
 * 				 copyResources[]: Resource - secondary array to use when deleting items or increasing the size of the array
 * 				 numResources: int - total current number of resources (starting at 0)
 * 				 max: int - The current size of the array
 * 
 * Methods: 	Library(int): default constructor, sets the max size and initializes the resourcesBorrowed array
 * 				inputResource(Scanner, MyDate): select resource type and test for input. This method also handles increasing the size of the array when needed (1 before reaching the max size)
 * 				toString(): a String representation of all items in the resourcesBorrowed array
 * 				resourcesOverdue(MyDate): a list of all resources overdue as of MyDate given
 * 				deleteResource(Scanner, MyDate): deletes a user selected resource from the array
 */
public class Library {
	
	private Resource[] resourcesBorrowed; //TODO move to arraylist
	private Resource[] copyResources;
	private int numResources = 0;
	private int max;
	
	public Library(int max) {
		
		this.max = max;
		resourcesBorrowed = new Resource[max];
		//initializes array but does *not* allocate memory to the resources, only to the references *to* resources that have yet to be created
	}
	
	public boolean inputResource(Scanner in, MyDate today) {
		
		boolean typeSelected = false;
		
		do { //type selection and initialization
			System.out.println("What type of resource would you like to borrow?");
			System.out.println("Options: ");
			System.out.println("B for book");
			System.out.println("D for DVD");
			System.out.println("M for magazine");
			
			String resourceType = in.next();
			
			if (resourceType.equalsIgnoreCase("B")) {
				//book
				resourcesBorrowed[numResources] = new Book();
				typeSelected = true;
				
			} else if (resourceType.equalsIgnoreCase("D")) {
				//dvd
				resourcesBorrowed[numResources] = new DVD();
				typeSelected = true;
				
			} else if (resourceType.equalsIgnoreCase("M")) {
				//magazine
				resourcesBorrowed[numResources] = new Magazine();
				typeSelected = true;
				
			} else {
				System.out.println("Sorry, that's not a valid option");
				
			}
		} while (!typeSelected);
		
		if (resourcesBorrowed[numResources].inputResource(in, today)) {
			
			checkMax();
			
			System.out.println("New resource added: " + resourcesBorrowed[numResources].toString());
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

			switch(lineArray[0]){ //first item of each line is the character denoting what type of resource it is;
				case "b":
					resourcesBorrowed[numResources] = new Book();
					break;
				case "d":
					resourcesBorrowed[numResources] = new DVD();
					break;
				case "m":
					if(length!=10){ //magazines have 10 data members when each member of MyDate is counted separately
						System.out.println("File incorrectly formatted, cannot read from file");
						return false;
					} else {
						resourcesBorrowed[numResources] = new Magazine();
					}
			}

			if(resourcesBorrowed[numResources].inFromFile(lineArray)){

				checkMax();
				System.out.println("New resource added: " + resourcesBorrowed[numResources].toString());
				numResources++;


			} else {
				System.out.println("Could not read from file");
				return false;
			}
		}

		return true;
	}

	public void checkMax(){

		if(numResources==max-1) {
			copyResources = new Resource[max*2];

			for(int i = 0; i < numResources+1; i++) {
				copyResources[i] = resourcesBorrowed[i];
			}

			resourcesBorrowed = copyResources;
			max = max*2;

		}

	}

	public String toString() {
		//loop toString for all resources

		if(numResources == 0) {
			return "There are not currently any resources checked out of the library";
		} else {
			String s = "Number of resources currently checked out: " + numResources;
			for(int i = 0; i<numResources; i++) {
				String temp = "\n[" + (i+1) + "] - " + resourcesBorrowed[i].toString();
				s = s+temp;
			}
			return s;
		}
		
	}
	
	public String resourcesOverdue(MyDate today) {
		//what resources are overdue today?
		int numOverdue = 0;
		
		for(int i = 0; i<numResources; i++) {
			if(resourcesBorrowed[i].isOverDue(today)) {
				System.out.println("[" + i + "] is overdue.");
				System.out.println("Overdue item info: " + resourcesBorrowed[i].toString());
				numOverdue++;
			}
			
		}
		return "Overdue items: " + numOverdue;
	}

	public void deleteResource (Scanner in, MyDate today) {
		//remove a resource and copy to a new array to fill any indexing gaps
		//TODO check what you can change after you turn to array list

		if (numResources == 0) {
			System.out.println ("No resources to delete\n");
			return;
		}

		System.out.println ("List of resources checked out in the library: ");
		for (int i=0; i < numResources; i++) {
			System.out.print("[" + (i+1) + "]: " + resourcesBorrowed[i].toString());
		}


		System.out.println ("Which resource to delete: ");
		int numToDelete = 0;
		boolean reEnter = true;
		do {
			if (in.hasNextInt()) {
				numToDelete = in.nextInt();
				if (numToDelete < 0 || numToDelete > numResources) {
					System.out.println ("Invalid ...  please reenter number between 0 and " + numResources);
				}
				else reEnter = false;
			}
			else {
				System.out.println ("Invalid...please reenter valid integer");
				in.next();
			}

		} while (reEnter);

		if (resourcesBorrowed[numToDelete-1].isOverDue(today)) {
			resourcesBorrowed[numToDelete-1].displayOverDue();
		}

		for (int i=numToDelete; i < numResources; i++) {
			resourcesBorrowed[i-1]= resourcesBorrowed[i];
		}
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
					System.out.println("Sorry, we can only save to .txt files, please input your filename again.");
				}

			} while(!goodName);

			try {

				outFile = new FileWriter(fileName, true);

				int unsaved = 0;

				for(int i = 0; i<numResources; i++) {

					try {

						String resource = resourcesBorrowed[i].saveResource();
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

}

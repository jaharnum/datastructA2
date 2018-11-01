import java.util.Scanner;

/**
 * Purpose:  This class models a simple date by keeping day, month, and year information. Leap years are not accomodated in this class.
 * @author Jamie Harnum
 * Course: CST8130
 * Lab Section: 313
 *
 * Data Members: 	day : int - value between 1 and 31 inclusive (depending on value in month)
               		month: int - value between 1 and 12 inclusive
               		year: int - positive value
               		
 * Methods: 		default constructor - sets date to Jan 1, 2018
         			toString (): String - prints date in year/month/day format
         			inputDate(Scanner): boolean - reads a valid date from the Scanner parameter and returns through boolean if the input was successful or not
         			addOne(): void - adds one to the day field and then adjusts month and year as needed.
         			isGreaterThan(MyDate): checks if the day is greater than a specified date
 * 
 */
public class MyDate {

	private int year;
	private int month;
	private int day;
	
	public MyDate() {
		//if no date is set, this date is the default
		year = 2018;
		month = 1;
		day = 1;
		
	}

	public MyDate(int month, int day, int year){
		this.month = month;
		this.day = day;
		this.year = year;
	}
	
	public boolean inputDate(Scanner in) { //taken directly from Lab1
		month = 0;
		day = 0; 
		year = 0;
		
		do {
				
			System.out.print ("Enter month - between 1 and 12: \n");
			if (in.hasNextInt())
				this.month = in.nextInt();
			else {
				System.out.println ("Invalid month input");
				in.next();
			}
		} while (this.month <= 0 || this.month > 12);
		
		do {
			
			System.out.print ("Enter day - between 1 and 31: \n");
			if (in.hasNextInt())
				this.day = in.nextInt();
			else {
				System.out.println ("Invalid day input");
				in.next();
			}
		} while (this.day <= 0 || this.day > 31 || (this.month == 2 && this.day > 29) || (this.day > 30 && (this.month == 9 ||this.month == 4 ||this.month == 6 ||this.month == 11) ) ); 
		
		do {
			System.out.print ("Enter year: \n");
			if (in.hasNextInt())
				this.year = in.nextInt();
			else {
				System.out.println ("Invalid day input");
				in.next();
			}
		} while (this.year <= 0);
			
		return true;		
	}
	
	
	public String toString() {
		return this.month + " " + this.day + " " + this.year;
	}
	
	public void addOne() {
		//add an extra day & handle change in month/year as needed
		if ((this.month == 2 && this.day < 29) || (this.day < 30 && (this.month == 9 ||this.month == 4 ||this.month == 6 ||this.month == 11) ) || this.day < 31 ) {
			this.day++;
		} else {
			if (this.month != 12) {
				this.month++;
				this.day = 1;
			} else {
				this.year++;
				this.month = 1;
				this.day = 1;
			}
		}
		
	}
	
	public boolean isGreaterThan(MyDate dueDate) {
		//if today > dueDate, return true
		if(this.year>dueDate.year) {
			return true;
		} else if (this.year==dueDate.year && this.month>dueDate.month) {
			return true;
		} else if (this.year==dueDate.year && this.month==dueDate.month && this.day>dueDate.day) {
			return true;
		} else {
			return false;
		}
	}
}

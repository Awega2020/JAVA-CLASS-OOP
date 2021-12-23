
import java.util.Scanner;

import Exceptions.InavliedNameException;
import Exceptions.InvalidCarNumberSelection;
import Exceptions.InvalidCustomerNumberSelection;

public class CarRental {
	private Scanner scn;
	private CarMgr carM;
	private CustMgr custM;
	
	public CarRental() {
		scn = new Scanner(System.in);
		custM = new CustMgr();
		carM = new CarMgr(custM);
		home();
	}

	private void home(){
		int choice = -1;
		do {
			System.out.println("*****Home Page******");
			System.out.println("Enter your choice");
			System.out.println("1. Manage Car");
			System.out.println("2. Manage Customer");
			System.out.println("0. Exit");
			try {
			choice = Integer.parseInt(scn.next());}
			catch (NumberFormatException e) {
				System.out.println("please enter valied choice");
				continue;
			}
			switch (choice) {
			case 1:
				carM.manageCar();
				break;
			case 2:
				custM.manageCustomer();
				break;
			case 0:
				System.out.println("****THANK YOU FOR USING OUR APP****");
				break;
			default:
				System.out.println("Please Enter valied choice");
			}
		} while (choice != 0);
	}


}

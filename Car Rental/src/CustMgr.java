import java.util.ArrayList;
import java.util.Scanner;

import Interfaces.ICar;
import Interfaces.ICustomer;
import Exceptions.InavliedNameException;
import Exceptions.InvalidCustomerNumberSelection;

public class CustMgr {

	private Scanner scn;

	ArrayList<ICustomer> custs;

	public CustMgr() {
		scn = new Scanner(System.in);
		custs = new ArrayList<ICustomer>();
	}

	// Customer Management
	public void manageCustomer(){
		System.out.println("managing Customer...");
		int choice =-1;
		do {
			System.out.println("*****Customer Page******");
			System.out.println("Enter your choice");
			System.out.println("1. Add Customer");
			System.out.println("2. Remove Customer");
			System.out.println("3. Report All Customers");
			System.out.println("0. Back");
			try {
				choice = Integer.parseInt(scn.next());
				}
			catch (NumberFormatException e) {
				System.out.println("please enter valied choice");
				continue;
				}
		switch (choice) {
		case 1:
			addCustomer();
			break;
		case 2:
			removeCustomer();
			break;
		case 3:
			reportAllCustomers();
			break;
		case 0:
			break;
		default:
			System.out.println("Please Enter valied choice");
		}
			
		} while (choice != 0);
	}

	private void addCustomer(){
		Boolean isCustomerCreated = false;
		String name = null;
		dd:do {
			System.out.println("Adding Customer... Or Type to 'exit' ");
			System.out.println("Enter FistName");
			name = scn.next();
			if(name.equals("exit"))
				break;
			for (ICustomer y : custs) {
				if (y.getFName().equals(name)) {
					System.out.println(name + " name Already exist ");
					continue dd;
				}
			}
			try {
				ICustomer cust = ICustomer.createCustomer(name);
				custs.add(cust);
				isCustomerCreated = true;
				System.out.println(name + " customer has added");
				break;
			} catch (InavliedNameException ex) {
				System.err.println(ex.getMessage());
				isCustomerCreated = false;
				continue;
			}
		}
		while(isCustomerCreated != true);
		

	}
	private void removeCustomer(){
		Boolean isCustomerRemoved = false;
		int choice;
		ICustomer cu=null;
		do {
			System.out.println("Removing Customer...");
			if(custs.size() <= 0)
			{
				System.out.println("There is no customer to be removed");
				break;
			}
			
			System.out.println("Choose Customer Number or Type to 'exit' ");
			reportAllCustomers();
			try {
				String word = scn.next();
				if(word.equals("exit"))
					break;
				choice = Integer.parseInt(word);
				cu = this.getCustById(choice);
				this.checkCustomerRentedOrNot(cu);
				custs.remove(choice-1);
				isCustomerRemoved = true;
				System.out.println(" customer has Removed");
				
			}
			catch (NumberFormatException e) {
				System.err.println("please enter valied choice");
				continue;
				}
			catch (InvalidCustomerNumberSelection ex) {
				System.err.println(ex.getMessage());
				continue;
			} 
			
			
			
		}
		while(isCustomerRemoved != true);
		

	}

	public void reportAllCustomers() {
		System.out.println("*** All Customers Report ***");
		System.out.println("No\t\tName");
		int i = 0;
		for (ICustomer x : custs) {
			i++;
			System.out.println(i + "\t\t" + x);
		}
	}

	public ICustomer getCustById(int custNo) throws InvalidCustomerNumberSelection {
		if(custNo > custs.size() || custNo <= 0 )
			throw new InvalidCustomerNumberSelection(custNo + " is Invalid Customer selection");
		return custs.get(custNo - 1);
	}
	
	public void checkCustomerRentedOrNot(ICustomer cu) throws InvalidCustomerNumberSelection{
		for (ICar x : CarMgr.cars) {
			if(x.getRenter() == cu) {
				throw new InvalidCustomerNumberSelection(cu.getFName() + " is Rented car So can not be removed");
			}
		 }
	}
}

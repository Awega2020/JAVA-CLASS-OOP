import java.util.ArrayList;

import java.util.Scanner;

import Exceptions.InvalidCarNumberSelection;
import Exceptions.InvalidCustomerNumberSelection;
import Interfaces.ICar;
import Interfaces.ICustomer;

public class CarMgr {

	private Scanner scn;
	public static ArrayList<ICar> cars;
	private CustMgr custM;

	public CarMgr(CustMgr custM) {
		scn = new Scanner(System.in);
		cars = new ArrayList<ICar>();
		this.custM = custM;
	}

	// Car management
	public void manageCar() {
		System.out.println("managing car...");
		int choice = -1;
		do {
			System.out.println("*****Car Page******");
			System.out.println("Enter your choice");
			System.out.println("1. Add Car");
			System.out.println("2. Rent Car");
			System.out.println("3. Return Car");
			System.out.println("4. Report All Cars");
			System.out.println("5. Report Free Cars");
			System.out.println("6. Report Rented Cars");
			System.out.println("7. Remove Cars");
			System.out.println("0. Back");

			try {
				choice = Integer.parseInt(scn.next());

				switch (choice) {
				case 1:
					addCar();
					break;
				case 2:
					rentCar();
					break;
				case 3:
					returnCar();
					break;
				case 4:
					printCars(cars, "All");
					break;
				case 5:
					ArrayList<ICar> freeCars = reportFreeCars();
					printCars(freeCars, "Free");
					break;
				case 6:
					ArrayList<ICar> rentedCars = reportRentedCars();
					printCars(rentedCars, "Rented");
					break;
				case 7:
					removeCar();
					break;
				case 0:
					break;
				default:
					System.out.println("Please Enter valied choice");

				}
			} catch (NumberFormatException e) {
				System.out.println("please enter valied choice");
				continue;
			}

		} while (choice != 0);
	}

	private void addCar() {
		Boolean isCarAdded = false;
		dd:do {
			System.out.println("Adding car...");
			System.out.println("Enter Car Brand or Type to exit");
			String brand = scn.next();
			if (brand.equals("exit"))
				break;
			else {
				ICar x = ICar.create(brand);
				for (ICar y : cars) {
					if (y.getBrand().equals(brand)) {
						System.out.println(brand + "name Already exist ");
						continue dd;
					}
				}
				
				cars.add(x);
				isCarAdded = true;
				System.out.println(brand + " car is added");
				
			}
		} while (!isCarAdded);

	}

	private void rentCar() {
		int carNo = 0;
		int custNo = 0;
		ICustomer cu = null;
		ICar ca = null;
		Boolean isCarNoValid = false;
		Boolean isCustomerNoValid = false;

		do {
			try {
				System.out.println("Renting car...");
				ArrayList<ICar> freeCars = reportFreeCars();
				if (freeCars.size() <= 0) {
					System.out.println("There is no free Cars");
					break;
				}
				if (custM.custs.size() <= 0) {
					System.out.println("There is no Customers to be rented");
					break;
				}
				if (!isCarNoValid) {
					System.out.println("Choose Car No Or Type to 'exit'");
					this.printCars(freeCars, "Free");
					String word = scn.next();
					if (word.equals("exit"))
						break;
					carNo = Integer.parseInt(word);
					ca = this.getCarById(carNo, freeCars);
					isCarNoValid = true;
				}
				if (!isCustomerNoValid) {
					System.out.println("Choose Customer No Or Type to 'exit' ");
					custM.reportAllCustomers();
					String word = scn.next();
					if (word.equals("exit"))
						break;
					custNo = Integer.parseInt(word);
					cu = custM.getCustById(custNo);
					isCustomerNoValid = true;
				}
				ca.rentTo(cu);
			} catch (NumberFormatException e) {
				System.out.println("please enter valied .... choice");
				continue;
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
				continue;
			}


		} while (isCarNoValid != true || isCustomerNoValid != true);
	}

	private void returnCar() {
		int carNo = 0;
		ICar ca = null;
		do {
			try {
				System.out.println("Returning car...");
				ArrayList<ICar> rentedCars = reportRentedCars();
				if (rentedCars.size() <= 0) {
					System.out.println("There is no cars to return");
					break;
				}
				System.out.println("Choose Car No Or type to 'exit' ");
				printCars(rentedCars, "Rented");
				String word = scn.next();
				if (word.equals("exit"))
					break;
				carNo = Integer.parseInt(word);
				ca = this.getCarById(carNo, rentedCars);
				ca.returnCar();
				break;
			} catch (NumberFormatException e) {
				System.out.println("please enter valied .... choice");
				continue;
			} catch (InvalidCarNumberSelection ex) {
				System.err.println(ex.getMessage());
				continue;
			}
		} while (true);

	}

	private void printCars(ArrayList<ICar> cars, String title) {
		if (cars.size() <= 0) {
			System.out.println("*** " + title + " Cars Report is Null ***");
		} else {
			System.out.println("*** " + title + " Cars Report ***");
			System.out.println("No\t\tBrand\t\tCustomer");
			int i = 0;
			for (ICar x : cars) {
				i++;
				System.out.println(i + "\t\t" + x.getBrand() + "\t\t" + x.getRenter());
			}
		}
	}

	private ArrayList<ICar> reportFreeCars() {
		ArrayList<ICar> freeCars = new ArrayList<ICar>();
		for (ICar x : cars) {
			if (!x.isRented()) {
				freeCars.add(x);
			}
		}
		return freeCars;
	}

	private ArrayList<ICar> reportRentedCars() {
		ArrayList<ICar> freeCars = new ArrayList<ICar>();
		for (ICar x : cars) {
			if (x.isRented()) {
				freeCars.add(x);
			}
		}
		return freeCars;
	}

	public ICar getCarById(int carNo, ArrayList<ICar> arr) throws InvalidCarNumberSelection {
		if (carNo > arr.size() || carNo <= 0)
			throw new InvalidCarNumberSelection(carNo + " is Invalid Car selection");
		return arr.get(carNo - 1);

	}

	private void removeCar() {
		Boolean isCarRemoved = false;
		int choice;
		ICar ca = null;
		do {
			System.out.println("Removing Car...");
			if (cars.size() <= 0) {
				System.out.println("There is no Car to remove");
				break;
			}
			try {
				System.out.println("Choose Car Number Or type to exit");
				printCars(cars, "Free");
				String word = scn.next();
				if (word.equals("exit"))
					break;
				choice = Integer.parseInt(word);
				ca = this.getCarById(choice, cars);
				this.checkCarRentedOrNot(ca);
				cars.remove(ca);
				isCarRemoved = true;
				System.out.println(" Car has Removed");

			} catch (NumberFormatException e) {
				System.err.println("please enter valied choice");
				continue;
			} catch (InvalidCarNumberSelection ex) {
				System.err.println(ex.getMessage());
				continue;
			}

		} while (isCarRemoved != true);
	}

	public void checkCarRentedOrNot(ICar ca) throws InvalidCarNumberSelection {
		if (ca.isRented()) {
			throw new InvalidCarNumberSelection(ca.getBrand() + " is Rented Car So can not be removed from cars");
		}
	}
}

package Models;

import Interfaces.ICar;
import Interfaces.ICustomer;

public class Car implements ICar{
	private String brand;
	private ICustomer renter;// null free/ obj rented

	public Car(String brand) {
		this.brand = brand;
	}

	@Override
	public String getBrand() {
		return brand;
	}

	@Override
	public ICustomer getRenter() {
		return renter;
	}

	@Override
	public void rentTo(ICustomer c) {
		if (c != null)
			renter = c;
	}

	@Override
	public void returnCar() {
		renter = null;
	}

	@Override
	public boolean isRented() {
		return renter != null; // null = false else true;
	}

	public String toString() {
		return brand;
	}
}

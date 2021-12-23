package Interfaces;

import Exceptions.InavliedNameException;
import Models.Customer;


public interface ICustomer {
    public String getFName();
	
	public static ICustomer createCustomer (String fName) throws InavliedNameException {
		return new Customer(fName);
	}
}
package Models;

import Exceptions.InavliedNameException;
import Interfaces.ICustomer;

public class Customer implements ICustomer {
	private String fName;
	
	public Customer(String fName) throws InavliedNameException {
		if(!fName.matches("[a-zA-Z]+"))
			throw new InavliedNameException( fName + " is not valid name");
		this.fName = fName;
	}
    @Override
    
	public String getFName() {
		return fName;
	}
	
	public String toString() {
		return fName;
	}
	
	
}

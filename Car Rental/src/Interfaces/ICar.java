package Interfaces;

import Models.Car;
public interface ICar {
	public String getBrand();
	public ICustomer getRenter();
	public void rentTo(ICustomer c) ;
	public void returnCar();
	public boolean isRented();

	public static ICar create(String brand){
		return new Car(brand);
	}
}

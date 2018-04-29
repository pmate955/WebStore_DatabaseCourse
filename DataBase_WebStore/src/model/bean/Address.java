package model.bean;

public class Address {
	private int Zipcode;
	private String City;
	private String Street;
	private int HouseNumber;
	
	public Address(int postalCode, String city, String street, int houseNumber) {
		Zipcode = postalCode;
		City = city;
		Street = street;
		HouseNumber = houseNumber;
	}

	public int getZipcode() {
		return Zipcode;
	}

	public void setZipcode(int postalCode) {
		Zipcode = postalCode;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getStreet() {
		return Street;
	}

	public void setStreet(String street) {
		Street = street;
	}

	public int getHouseNumber() {
		return HouseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		HouseNumber = houseNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((City == null) ? 0 : City.hashCode());
		result = prime * result + HouseNumber;
		result = prime * result + ((Street == null) ? 0 : Street.hashCode());
		result = prime * result + Zipcode;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (City == null) {
			if (other.City != null)
				return false;
		} else if (!City.equals(other.City))
			return false;
		if (HouseNumber != other.HouseNumber)
			return false;
		if (Street == null) {
			if (other.Street != null)
				return false;
		} else if (!Street.equals(other.Street))
			return false;
		if (Zipcode != other.Zipcode)
			return false;
		return true;
	}
	
	
}

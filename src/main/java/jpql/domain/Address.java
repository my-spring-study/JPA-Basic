package jpql.domain;

import static javax.persistence.AccessType.*;

import javax.persistence.Access;
import javax.persistence.Embeddable;

@Embeddable
@Access(value = FIELD)
public class Address {

	public String city;
	public String street;
	public String zipcode;

	public Address() {
	}

	public Address(String city, String street, String zipcode) {
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}

	public String getCity() {
		return city;
	}

	public String getStreet() {
		return street;
	}

	public String getZipcode() {
		return zipcode;
	}
}

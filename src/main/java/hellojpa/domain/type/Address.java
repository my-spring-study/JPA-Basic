package hellojpa.domain.type;

import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD) // (엔티티의) 속성에 접근할 때, FIELD 액세스 방법을 사용하도록 명시. (getter, setter 를 사용하는 프로퍼티 접근 방식은 지양)
public class Address {

	private String city;
	private String street;
	private String zipcode;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Address address = (Address)o;
		return Objects.equals(city, address.city)
			&& Objects.equals(street, address.street)
			&& Objects.equals(zipcode, address.zipcode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, street, zipcode);
	}
}
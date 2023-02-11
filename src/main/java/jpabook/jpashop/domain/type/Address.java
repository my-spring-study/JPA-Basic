package jpabook.jpashop.domain.type;

import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Address {

	@Column(length = 10)
	private String city;

	@Column(length = 20)
	private String street;

	@Column(length = 5)
	private String zipcode;

	public Address() {
	}

	public Address(String city, String street, String zipcode) {
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}

	// 값 타입으로 추출해냄으로써, 의미있는 비즈니스 메서드 작성 가능
	public String fullAddress() {
		return getCity() + " " + getStreet() + " " + getZipcode();
	}

	public boolean isValid() {
		// city, street, zipcode 다 있는지 확인
		return true;
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

	// equals 재정의할 때 getter 사용. 필드에 직접 접근하면 프록시 객체일 때 계산이 안 된다.
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Address address = (Address)o;
		return Objects.equals(getCity(), address.getCity())
			&& Objects.equals(getStreet(), address.getStreet())
			&& Objects.equals(getZipcode(), address.getZipcode());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCity(), getStreet(), getZipcode());
	}
}
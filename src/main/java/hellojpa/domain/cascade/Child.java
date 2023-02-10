package hellojpa.domain.cascade;

import static javax.persistence.FetchType.*;

import java.beans.FeatureDescriptor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Child {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "PARENT_ID")
	private Parent parent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}
}

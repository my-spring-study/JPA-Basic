package hellojpa.domain.cascade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Parent {

	@Id
	@GeneratedValue
	@Column(name = "PARENT_ID")
	private Long id;

	private String name;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST)
	private List<Child> childList = new ArrayList<>();

	public void addChild(Child child) {
		this.childList.add(child);
		child.setParent(this);
	}

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
}

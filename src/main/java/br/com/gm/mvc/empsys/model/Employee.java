package br.com.gm.mvc.empsys.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // gerado automaticamente para cada funcionário

	private String firstName; // required: yes
	private String middleName; // required: no
	private String lastName; // required: yes
	private LocalDate birthDate; // required: yes
	private String position; // required: yes
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", fetch = FetchType.LAZY)
	private List<Compensation> compensations;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	public void addCompensation(Compensation compensation) {
	    compensations.add(compensation);
	}
	
	public List<Compensation> getCompensations(){
	    return compensations;
	}

	
	@Override
	public boolean equals(Object obj) {
		Employee e = (Employee) obj;
		
		if (this.getFirstName().equals(e.getFirstName()) && this.getMiddleName().equals(e.getMiddleName()) 
				&& this.getLastName().equals(e.getLastName()) && this.getBirthDate().equals(e.getBirthDate()) 
				&& this.getPosition().equals(e.getPosition()))
			return true;
		
		return false;

	}

    @Override
    public String toString() {
        return "Employee [id=" + id + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
                + lastName + ", birthDate=" + birthDate + ", position=" + position + "]";
    }

}

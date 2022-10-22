package br.com.gm.mvc.empsys.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import br.com.gm.mvc.empsys.model.Employee;

public class EmployeeDto {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	// Os nomes precisam ser iguais aos 'names' dos inputs passados na requisição
	// POST.
	@NotNull
	@Pattern(regexp = "^\\S.*$", message="Este campo não pode estar em branco.")
	@Pattern(regexp = "^.{2,20}$", message="''First Name'' deve conter no mínimo 2 e no máximo 20 caracteres.")
	@Pattern(regexp = "^[A-Z].*$", message="''First Name'' deve iniciar com letra maiúscula.")
	@Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñë ]*$", message="''First Name'' deve conter apenas caracteres válidos. [A-Z], [a-z], [áàâãéèêíïóôõöúçñë ]")
	private String firstName;
	
	
	private String middleName;
	
	@NotNull
	@Pattern(regexp = "^\\S.*$", message="Este campo não pode estar em branco.")
	@Pattern(regexp = "^.{2,20}$", message="''Last Name'' deve conter no mínimo 2 e no máximo 20 caracteres.")
	@Pattern(regexp = "^[A-Z].*$", message="''Last Name'' deve iniciar com letra maiúscula.")
	@Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñë ]*$", message="''First Name'' deve conter apenas caracteres válidos. [A-Z], [a-z], [áàâãéèêíïóôõöúçñë ]")
	private String lastName;

	@NotNull(message = "A data de nascimento deve estar preenchida.")
	@Pattern(regexp = "^\\S.*$", message="Este campo não pode estar em branco.")
	@Pattern(regexp = "^[\\d-]*$", message="''Birth Date'' deve ser preenchida apenas com números.")
//	@Past(message = "A data de nascimento não pode ser maior que a data de hoje.")
	private String birthDate;
	
	
	@NotNull
	@Pattern(regexp = "^\\S.*$", message="Este campo não pode estar em branco.")
	@Pattern(regexp = "^.{2,20}$", message="''Position'' deve conter no mínimo 2 e no máximo 20 caracteres.")
	@Pattern(regexp = "^[A-Z].*$", message="''Position'' deve iniciar com letra maiúscula.")
	@Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñë ]*$", message="''First Name'' deve conter apenas caracteres válidos. [A-Z], [a-z], [áàâãéèêíïóôõöúçñë ]")
	private String position;

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

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Employee toEmployee() {
		
		Employee employee = new Employee();
		
		employee.setFirstName(this.firstName);
		employee.setMiddleName(this.middleName);
		employee.setLastName(this.lastName);
		employee.setBirthDate(LocalDate.parse(this.birthDate, formatter));
		employee.setPosition(this.position);
		
		return employee;
	}


}

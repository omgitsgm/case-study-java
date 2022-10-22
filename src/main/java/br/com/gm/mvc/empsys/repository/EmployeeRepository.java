package br.com.gm.mvc.empsys.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gm.mvc.empsys.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	/*
	@Query("SELECT e FROM Employee e WHERE e.firstName = :firstName AND e.middleName = :middleName AND e.lastName = :lastName AND e.birthDate = :birthDate AND e.position = :position")
	List<Employee> alreadyExist(@Param("firstName") String firstName, @Param("middleName") String middleName,
			@Param("lastName") String lastName, @Param("birthDate") LocalDate birthDate,
			@Param("position") String position);
	*/

}

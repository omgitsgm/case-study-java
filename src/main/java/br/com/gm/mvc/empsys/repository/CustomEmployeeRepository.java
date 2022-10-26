package br.com.gm.mvc.empsys.repository;

import java.util.List;

import br.com.gm.mvc.empsys.model.Employee;

public interface CustomEmployeeRepository {

    public List<Employee> searchByParametersWithCriteria(String firstName, String lastName, String position);

}

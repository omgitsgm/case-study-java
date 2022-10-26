package br.com.gm.mvc.empsys.repository;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.gm.mvc.empsys.model.Employee;

public interface CustomEmployeeRepository {

    public List<Employee> searchByParametersWithCriteria(String firstName, String lastName, String position);

}

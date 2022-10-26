package br.com.gm.mvc.empsys.repository.implementation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.gm.mvc.empsys.model.Employee;
import br.com.gm.mvc.empsys.repository.CustomEmployeeRepository;

public class CustomEmployeeRepositoryImpl implements CustomEmployeeRepository {
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public List<Employee> searchByParametersWithCriteria(String firstName, String lastName, String position) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
        Root<Employee> from = query.from(Employee.class);

        Predicate filtros = builder.and();
        if (firstName != null && !firstName.trim().isEmpty()) {
            filtros = builder.and(filtros, builder.equal(from.get("firstName"), firstName));
        }
        if (lastName != null && !lastName.trim().isEmpty()) {
            filtros = builder.and(filtros, builder.equal(from.get("lastName"), lastName));
        }
        if (position != null && !position.trim().isEmpty()) {
            filtros = builder.and(filtros, builder.equal(from.get("position"), position));
        }
        query.where(filtros);
        
        return em.createQuery(query).getResultList();
    }

}

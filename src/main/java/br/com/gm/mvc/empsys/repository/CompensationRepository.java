package br.com.gm.mvc.empsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gm.mvc.empsys.model.Compensation;

public interface CompensationRepository extends JpaRepository<Compensation, Long> {

}

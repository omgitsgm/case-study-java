package br.com.gm.mvc.empsys.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.gm.mvc.empsys.dto.EmployeeDto;
import br.com.gm.mvc.empsys.model.Employee;
import br.com.gm.mvc.empsys.repository.EmployeeRepository;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/formulario")
	public String formulario(EmployeeDto employeeDto) {
		return "employee/formulario";
	}
	
	@PostMapping("/novo")
	public String novo(@Valid EmployeeDto employeeDto, BindingResult result, Model model) {
		// O Spring checa se os dados em 'employeeDto' são válidos.
		// O BindingResult nos informa quantos erros ocorreram.
		
		// Verifica a entrada de dados do usuário
		System.out.println(result.getErrorCount() + " erro(s) detectado(s) durante a inserção do funcionário.");
		if(result.hasErrors()) {
			model.addAttribute("message", "Formulário preenchido incorretamente.");
			model.addAttribute("feedback", false);
			return "employee/formulario";
		} 
		Employee employee = employeeDto.toEmployee();
		
		/* Verifica se o funcionário cadastrado já existe */
		List<Employee> employees = employeeRepository.findAll();
		if(employees.contains(employee)) {
			System.out.println("O funcionário não foi cadastrado, pois já está cadastrado.");
			model.addAttribute("message", "Esse funcionário já existe.");
			model.addAttribute("feedback", false);
			return "employee/formulario";
		}
		
		/* Caso de sucesso: entradas válidas e funcionário ainda não existe. */
		employeeRepository.save(employee);
		model.addAttribute("feedback", true);
		model.addAttribute("message", "Funcionário cadastrado com sucesso!");
		System.out.println("O funcionário foi cadastrado com sucesso.");
		
		
		
		return"employee/formulario";
		
	}
	
}

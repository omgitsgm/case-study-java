package br.com.gm.mvc.empsys.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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

        ArrayList<String> errorMessages = new ArrayList<String>();
        boolean valido = true;

        // Verifica a entrada de dados do usuário
        System.out.println(result.getErrorCount() + " erro(s) detectado(s) durante a inserção do funcionário.");
        if (result.hasErrors()) {
            errorMessages.add("O formulário foi preenchido incorretamente.");
            valido = false;
//			model.addAttribute("message", "Formulário preenchido incorretamente.");
//			return "employee/formulario";
        } else {
            Employee employee = employeeDto.toEmployee();

            /* Verifica se o funcionário cadastrado já existe */
            List<Employee> employees = employeeRepository.findAll();
            if (employees.contains(employee)) {
                System.out.println("Erro: O funcionário não foi cadastrado, pois já está cadastrado.");
                errorMessages.add("Esse funcionário já foi cadastrado.");
                valido = false;
//	          model.addAttribute("message", "Esse funcionário já existe.");
//	          model.addAttribute("feedback", false);
//	          return "employee/formulario";
            }

            /* Verifica se a data de nascimento é maior do que a data atual */
            if (employee.getBirthDate().isAfter(LocalDate.now())) {
                System.out.println("Erro: A data de nascimento é maior do que a data atual.");
                errorMessages.add("A data de nascimento não pode ser maior do que a data atual.");
                valido = false;
//	          model.addAttribute("message", "A data de nascimento não pode ser maior do que a data atual.");
//	          model.addAttribute("feedback", false);
            }

            /* Verifica se o funcionário cadastrado tem pelo menos 18 anos */
            if (employee.getBirthDate().plusYears(18).isAfter(LocalDate.now())) {
                System.out.println("Erro: o funcionário não tem 18 anos.");
                errorMessages.add("O funcionário deve ter pelo menos 18 anos.");
                valido = false;
            }

            if (valido) { // Caso de sucesso: entradas válidas e funcionário ainda não existe.
                employeeRepository.save(employee);
                System.out.println("O funcionário foi cadastrado com sucesso.");
            }

        }

        if (!valido) { // Em caso de invalidade, anexa as mensagens de erro na requisição.
            model.addAttribute("messages", errorMessages);
        }

        model.addAttribute("valido", valido);
        return "employee/formulario";

    }

    @GetMapping("/formulario-search")
    public String formularioSearch(EmployeeDto employeeDto, Model model) {
        return "employee/formulario-search";
    }

    @GetMapping("/search")
    public String search(EmployeeDto employeeDto, Model model) {
        
        String firstName = employeeDto.getFirstName();
        String lastName = employeeDto.getLastName();
        String position = employeeDto.getPosition();
        
        System.out.println("Criterios:" + firstName + "," + lastName + "," + position + ".");

        
        List<Employee> employees = employeeRepository.searchByParametersWithCriteria(firstName, lastName, position);
        System.out.println(employees);
        System.out.println(employees.size());
        
        model.addAttribute("employees", employees);
        model.addAttribute("size", employees.size());
        model.addAttribute("enviado", true);
        
       
        return "employee/formulario-search";
    }
}

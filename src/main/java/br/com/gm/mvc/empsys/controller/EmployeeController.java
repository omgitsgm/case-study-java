package br.com.gm.mvc.empsys.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/formulario-add")
    public String formularioAdd(EmployeeDto employeeDto) {
        return "employee/formulario-add";
    }

    @PostMapping("/add")
    public String add(@Valid EmployeeDto employeeDto, BindingResult result, Model model) {
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
        return "employee/formulario-add";

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
        System.out.println("Resultados da busca: " + employees);
        System.out.println("Quantidade de resultados: " + employees.size());

        model.addAttribute("employees", employees);
        model.addAttribute("size", employees.size());
        model.addAttribute("enviado", true);

        return "employee/formulario-search";
    }

    @GetMapping("/formulario-edit")
    public String formularioEdit(EmployeeDto employeeDto, HttpServletRequest request, Model model)
            throws ServletException, IOException {

        String parameterId = request.getParameter("id");
        Long id = Long.valueOf(parameterId);

        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        Employee employee = optionalEmployee.get();

        System.out.println("Employee da requisicao: " + employee);

        model.addAttribute("employee", employee);
        /*
         * request.setAttribute("employee", employee);
         * request.setAttribute("enviado", true);
         */

        return "employee/formulario-edit";

    }

    @PostMapping("/edit")
    public String edit(@Valid EmployeeDto employeeDto, BindingResult result, Model model, HttpServletRequest request) {

        // Definimos variáveis para auxiliar na validação
        boolean valido = true;
        List<String> errorMessages = new ArrayList<String>();

        // Pegamos o parâmetro id da requisição
        String parameterId = request.getParameter("id");
        Long id = Long.valueOf(parameterId);

        // Convertemos o EmployeeDto em Employee
        Employee employeeForm = employeeDto.toEmployee();
        employeeForm.setId(id);

        /* CHECA OS INPUTS DO FORMULÁRIO */
        if (result.hasErrors()) {
            System.out.println(result.getErrorCount() + " erros encontrados no preenchimento do formulário.");
            errorMessages.add(result.getErrorCount() + " errors found at form input.");
            valido = false;
        } else {

            /* IDENTICAL EMPLOYEE FOUND */
            List<Employee> employees = employeeRepository.findAll();
            if (employees.contains(employeeForm)) {
                System.out.println("ERROR: Identical employee found!");
                errorMessages.add("Identical employee found.");
                valido = false;
            }
            
            /* Verifica se a data de nascimento é maior do que a data atual */
            if (employeeForm.getBirthDate().isAfter(LocalDate.now())) {
                System.out.println("Erro: A data de nascimento é maior do que a data atual.");
                errorMessages.add("Birth date should not be later than current date.");
                valido = false;
//            model.addAttribute("message", "A data de nascimento não pode ser maior do que a data atual.");
//            model.addAttribute("feedback", false);
            }

            /* Verifica se o funcionário cadastrado tem pelo menos 18 anos */
            if (employeeForm.getBirthDate().plusYears(18).isAfter(LocalDate.now())) {
                System.out.println("Erro: o funcionário não tem 18 anos.");
                errorMessages.add("Employee must be, at least, 18 years old.");
                valido = false;
            }

            /* CASO DE SUCESSO */
            if (valido) {
                // Pegamos a referência do objeto que deve ser alterado no banco de dados.
                Employee employeeReference = employeeRepository.getReferenceById(id);

                // Atualizamos os valores
                employeeReference.setFirstName(employeeForm.getFirstName());
                employeeReference.setMiddleName(employeeForm.getMiddleName());
                employeeReference.setLastName(employeeForm.getLastName());
                employeeReference.setBirthDate(employeeForm.getBirthDate());
                employeeReference.setPosition(employeeForm.getPosition());

                // Salvamos no banco de dados
                employeeRepository.save(employeeReference);

                // Adicionamos como atributo o objeto atualizado
                model.addAttribute("employee", employeeReference);
            }
        }

        if (!valido) {
            // Adicionamos como atributo o objeto do formulário
            model.addAttribute("employee", employeeForm);
            // Adicionamos como atributo a lista de mensagens de erro.
            model.addAttribute("errorMessages", errorMessages);
        }

        model.addAttribute("valido", valido);

        return "employee/formulario-edit";
    }
}
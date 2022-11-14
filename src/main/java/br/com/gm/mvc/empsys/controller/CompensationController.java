package br.com.gm.mvc.empsys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.gm.mvc.empsys.dto.CompensationDto;
import br.com.gm.mvc.empsys.model.Compensation;
import br.com.gm.mvc.empsys.model.Employee;
import br.com.gm.mvc.empsys.repository.CompensationRepository;
import br.com.gm.mvc.empsys.repository.EmployeeRepository;

@Controller
@RequestMapping("/compensation")
public class CompensationController {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private CompensationRepository compensationRepository;
    
    @GetMapping("/formulario-add")
    public String formularioAdd(CompensationDto compensationDto, HttpServletRequest request, Model model) {
        
       String parameterId = request.getParameter("id");
       Long id = Long.valueOf(parameterId);
       
       model.addAttribute("id", id);
        
       return "compensation/formulario-add";
    }
    
    @PostMapping("/add")
    public String add(CompensationDto compensationDto, HttpServletRequest request, Model model) {
        System.out.println("Chegamos em add!");
        
        String parameterId = request.getParameter("id");
        Long id = Long.parseLong(parameterId);
        
        Compensation compensation = compensationDto.toCompensation();
        
        Employee employee = employeeRepository.getReferenceById(id);
        compensation.setEmployee(employee);
        employee.addCompensation(compensation);
        
        compensationRepository.save(compensation);
        
        System.out.println("Compensation: " + compensation);
        
        System.out.println(employee.getCompensations());
        
        model.addAttribute("id", id);
        
        return "compensation/formulario-add";
    }
    
}

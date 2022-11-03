package br.com.gm.mvc.empsys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.gm.mvc.empsys.dto.CompensationDto;

@Controller
@RequestMapping("/compensation")
public class CompensationController {
    
    @GetMapping("/formulario-add")
    public String formularioAdd(CompensationDto compensationDto, HttpServletRequest request, Model model) {
        
       String parameterId = request.getParameter("id");
       Long id = Long.valueOf(parameterId);
       
       model.addAttribute("id", id);
        
       return "compensation/formulario-add";
    }
    
    @PostMapping("/add")
    public String add(CompensationDto compensationDto, HttpServletRequest request, Model model) {
        
        // Id está chegando como null
        String id = request.getParameter("id");
        
        System.out.println("Id: " + id);
        System.out.println(compensationDto);
        
        return "compensation/formulario-add";
    }
    
}

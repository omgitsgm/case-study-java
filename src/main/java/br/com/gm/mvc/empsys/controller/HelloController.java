package br.com.gm.mvc.empsys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class HelloController {
	
	@GetMapping()
	public String hello(Model model) {
//		Model substitui o HttpServletRequest
		model.addAttribute("nomeAtributo", "valorAtributo");
		return "hello"; // Direciona o usuário para "hello.html"
	}
	
}

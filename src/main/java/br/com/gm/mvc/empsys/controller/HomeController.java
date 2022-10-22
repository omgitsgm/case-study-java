package br.com.gm.mvc.empsys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/") // Sempre que o usuário acessa o site, ele é aciona o HomeController
public class HomeController {
	
	@GetMapping()
	public String home() {
		return "home"; // Direciona o usuário para a página home
	}
	
}

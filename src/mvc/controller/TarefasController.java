package mvc.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import mvc.model.Tarefa;
import mvc.model.DAO;

@Controller
public class TarefasController {
	
	@RequestMapping("/")
	public String execute() {
		System.out.println("Lógica do MVC");
		return "info";
	}
	
	@RequestMapping("criaTarefa")
	public String form() {
		return "fromTarefa";
	}

}

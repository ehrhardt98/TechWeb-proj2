package mvc.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TarefasController {
	
	@RequestMapping("/a")
	public String execute() {
		System.out.println("Lógica do MVC");
		return "info";
	}
	
	@RequestMapping("criaTarefa")
	public String form() {
		return "fromTarefa";
	}

}

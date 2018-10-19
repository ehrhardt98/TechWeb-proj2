package mvc.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mvc.model.DAO;
import mvc.model.Mural;
import mvc.model.Nota;
import mvc.model.Usuario;

@Controller
public class ControllerGeral {

	@RequestMapping(value = {"", "/"})
	public String execute() {
		return "inicio";
	}
	
	@RequestMapping("criaLogin")
	public String criaLogin(@RequestParam(value="username-create") String username_create, @RequestParam(value="senha-create") String senha_create, @RequestParam(value="email-create") String email_create, Model model ) {
		DAO dao = new DAO();
		Usuario usuario = new Usuario();
		
		usuario.setNome(username_create);
		usuario.setSenha(senha_create);
		usuario.setEmail(email_create);
		
		dao.adicionaUsuario(usuario);

		dao.close();
		return "inicio";
	}
	
	@RequestMapping("entraLogin")
	public String entraLogin(@RequestParam(value="username-login") String username_login, @RequestParam(value="senha-login") String senha_login, Model model) {
		DAO dao = new DAO();
		Usuario usuario = new Usuario();
		String site;
		
		site = "inicio";		
		
		for(Usuario u : dao.getListaUsuarios()) {
			if(u.getNome().equals(username_login) && u.getSenha().equals(senha_login)) {
				site = "home";
				usuario.setId(u.getId());
				usuario.setEmail(u.getEmail());
				usuario.setNome(u.getNome());
				usuario.setSenha(u.getSenha());
			}
		}
		
		dao.close();
		model.addAttribute("id_usuario", (usuario.getId()));
		return site;
	}
	
	@RequestMapping("selecionaMural")
	public String selecionaMural(@RequestParam(value="id_mural") String sid_mural, @RequestParam(value="id_usuario") String sid_usuario, Model model) {
		DAO dao = new DAO();
		Integer id_mural = Integer.parseInt(sid_mural);
		Integer id_usuario = Integer.parseInt(sid_usuario);
		
		System.out.println(id_mural);
		
		dao.close();
		
		model.addAttribute("id_mural", id_mural);
		model.addAttribute("id_usuario", id_usuario);
		return "mural";
	}
	
	@RequestMapping("alteraMural")
	public String alteraMural(Mural mural) {
		DAO dao = new DAO();
		dao.adicionaMural(mural);
		return "home";
	}
	
	@RequestMapping("deletaMural")
	public String deletaMural(Integer id_mural) {
		DAO dao = new DAO();
		dao.removeMural(id_mural);
		return "home";
	}
	
	@RequestMapping("criaMural")
	public String criaMural(@RequestParam(value="id_usuario") Integer id_usuario, Model model) {
		DAO dao = new DAO();

		dao = new DAO();

		Mural mural = new Mural();
		mural.setIdUsuario(id_usuario);
		mural.setNome("Novo Mural");

		dao.adicionaMural(mural);
		dao.close();

		model.addAttribute("id_usuario", id_usuario);
		return "home";
	}
	
	@RequestMapping("voltaHome")
	public String voltaHome(@RequestParam(value="homde") Integer shome, ) {
		DAO dao = new DAO();
		Integer id_usuario = Integer.parseInt(shome);

		dao.close();
		request.setAttribute("id_usuario", id_usuario);
		return "home";
	}
	
	@RequestMapping("criaNota")
	public String criaNota(@RequestParam(value="id_usuario") Integer id_usuario, @RequestParam(value="id_mural") Integer id_mural, @RequestParam(value="create_note") String create_note, Model model) {
		DAO dao = new DAO();
		Nota nota = new Nota();
		nota.setTipo("texto");
		nota.setConteudo(create_note);
		
		nota.setIdMural(id_mural);

		dao.adicionaNota(nota);

		dao.close();

		model.addAttribute("id_mural", id_mural);
		model.addAttribute("id_usuario", id_usuario);
		return "mural";
	}
	
	@RequestMapping("deletaNota")
	public String deletaNota(@RequestParam(value="id_nota") String id_nota, @RequestParam(value="id_mural") Integer id_mural,@RequestParam(value="id_usuario") Integer id_usuario, Model model) {
		DAO dao = new DAO();
		String idstring = id_nota;
		Integer id = Integer.parseInt(idstring);
		
		dao.removeNota(id);
		
		dao.close();
		
		model.addAttribute(id_mural);
		model.addAttribute(id_usuario);
		return "mural";
	}
	
	@RequestMapping("alteraNota")
	public String alteraNota(@RequestParam(value="id_mural") Integer id_mural, @RequestParam(value="id_usuario") Integer id_usuario, @RequestParam(value="id_nota") Integer id_nota, @RequestParam(value="tipo_nota") String tipo_nota, @RequestParam(value="edit_nota") String edit_nota, Model model) {
		DAO dao = new DAO();
		Nota nota = new Nota();
		
		nota.setId(Integer.valueOf(id_nota));
		nota.setTipo(tipo_nota);
		nota.setConteudo(edit_nota);
		
		dao.alteraNota(nota);
		dao.close();
		
		model.addAttribute("id_mural", id_mural);
		model.addAttribute("id_usuario", id_usuario);
		return "mural";
	}
	
	@RequestMapping("criaBlob")
	public String criaBlob(Nota nota) {
		DAO dao = new DAO();
		dao.adicionaBlob(nota);
		return "mural";
	}
	
	@RequestMapping("mostraBlob")
	public String mostraBlob(Nota nota) {
		DAO dao = new DAO();
		dao.viewBlob(nota);
		return "mural";
	}
}



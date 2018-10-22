package mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mvc.model.DAO;
import mvc.model.Mural;
import mvc.model.Nota;
import mvc.model.Usuario;

@Controller
public class ControllerGeral {

	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String execute() {
		return "inicio";
	}

	@RequestMapping(value = "criaLogin", method = RequestMethod.POST)
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

	@RequestMapping(value = "entraLogin", method = RequestMethod.POST)
	public String entraLogin(@RequestParam(value="username-login") String username_login, @RequestParam(value="senha-login") String senha_login, Model model) {
		System.out.println("faidhfiajsdhfh");
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
	public String alteraMural(@RequestParam(value="id_usuario") String sid_usuario, @RequestParam(value="id_mural") String sid_mural, @RequestParam(value="nome_mural") String nome_mural, Model model) {
		DAO dao;
		Integer id_usuario = Integer.parseInt(sid_usuario);
		dao = new DAO();
		Mural mural = new Mural();
		mural.setId(Integer.parseInt(sid_mural));
		mural.setNome(nome_mural);

		dao.alteraMural(mural);
		dao.close();
		model.addAttribute("id_usuario", id_usuario);

		return "home";
	}

	@RequestMapping("deletaMural")
	public String deletaMural(@RequestParam(value="id_usuario") String sid_usuario,@RequestParam(value="id_mural") String sid_mural, Model model) {
		DAO dao;

		dao = new DAO();

		Integer id_usuario = Integer.parseInt(sid_usuario);

		dao.removeMural(Integer.parseInt(sid_mural));

		dao.close();

		model.addAttribute("id_usuario", id_usuario);
		return "home";
	}

	@RequestMapping("criaMural")
	public String criaMural(@RequestParam(value="id_usuario") String sid_usuario, Model model) {
		DAO dao = new DAO();

		dao = new DAO();

		Mural mural = new Mural();
		mural.setIdUsuario(Integer.parseInt(sid_usuario));
		mural.setNome("Novo Mural");

		dao.adicionaMural(mural);
		dao.close();

		model.addAttribute("id_usuario", sid_usuario);
		return "home";
	}

	@RequestMapping("voltaHome")
	public String voltaHome(@RequestParam(value="homde") String shome, Model model) {
		DAO dao = new DAO();
		Integer id_usuario = Integer.parseInt(shome);

		dao.close();
		model.addAttribute("id_usuario", id_usuario);
		return "home";
	}

	@RequestMapping("criaNota")
	public String criaNota(@RequestParam(value="id_usuario") String id_usuario, @RequestParam(value="id_mural") String sid_mural, @RequestParam(value="create_note") String create_note, Model model) {
		DAO dao = new DAO();
		Nota nota = new Nota();
		nota.setTipo("texto");
		nota.setConteudo(create_note);

		nota.setIdMural(Integer.parseInt(sid_mural));

		dao.adicionaNota(nota);

		dao.close();

		model.addAttribute("id_mural", sid_mural);
		model.addAttribute("id_usuario", id_usuario);
		return "mural";
	}

	@RequestMapping("deletaNota")
	public String deletaNota(@RequestParam(value="id_nota") String id_nota, @RequestParam(value="id_mural") String id_mural,@RequestParam(value="id_usuario") String id_usuario, Model model) {
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
	public String alteraNota(@RequestParam(value="id_mural") String id_mural, @RequestParam(value="id_usuario") String id_usuario, @RequestParam(value="id_nota") String sid_nota, @RequestParam(value="tipo_nota") String tipo_nota, @RequestParam(value="edit_nota") String edit_nota, Model model) {
		DAO dao = new DAO();
		Nota nota = new Nota();

		nota.setId(Integer.parseInt(sid_nota));
		nota.setTipo(tipo_nota);
		nota.setConteudo(edit_nota);

		dao.alteraNota(nota);
		dao.close();

		model.addAttribute("id_mural", id_mural);
		model.addAttribute("id_usuario", id_usuario);
		return "mural";
	}

	@RequestMapping("criaBlob")
	public String criaBlob(@RequestParam(value="id_mural") String sid_mural, @RequestParam(value="id_usuario") String sid_usuario, @RequestParam(value="id_nota") String sid_nota, @RequestParam(value="blob") Part blob, Model model) {
		DAO dao;

		Integer id_mural = Integer.parseInt(sid_mural);
		Integer id_usuario = Integer.parseInt(sid_usuario);

		Part part = blob;

		if (part != null) {
			try {
				dao = new DAO();

				Nota nota = new Nota();
				InputStream input = part.getInputStream();

				nota.setId(Integer.parseInt(sid_nota));
				nota.setBlob(input);

				nota.setIdMural(id_mural);

				dao.adicionaBlob(nota);

				dao.close();

				model.addAttribute("id_mural", id_mural);
				model.addAttribute("id_usuario", id_usuario);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return "mural";
	}

	@RequestMapping("mostraBlob")
	public String mostraBlob(@RequestParam(value="id_mural") String sid_mural, @RequestParam(value="id_usuario") String sid_usuario, @RequestParam(value="id_nota") String sid_nota, HttpServletResponse response, Model model) {
		DAO dao;
		Integer id_mural = Integer.parseInt(sid_mural);
		Integer id_usuario = Integer.parseInt(sid_usuario);



		dao = new DAO();

		Nota nota = new Nota();

		nota.setId(Integer.parseInt(sid_nota));

		byte[] a = dao.viewBlob(nota);

		model.addAttribute("id_mural", id_mural);
		model.addAttribute("id_usuario", id_usuario);
		response.setContentType("image/gif");

		byte byteArray[] = a;
		String base64 = new String(Base64Utils.encode(byteArray));
		//			OutputStream output = response.getOutputStream();
		//			output.write(base64);
		//			output.flush();
		//			output.close();
		try {
			response.getWriter().println(base64);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dao.close();
		return "mural";

	}
}



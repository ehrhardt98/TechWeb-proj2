package mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import mvc.model.DAO;
import mvc.model.Mural;
import mvc.model.Nota;
import mvc.model.Usuario;

@Controller
@MultipartConfig
public class ControllerGeral {

	@RequestMapping(value = {"", "/", "voltaInicio"}, method = RequestMethod.GET)
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

	@RequestMapping(value = "selecionaMural", method = RequestMethod.POST)
	public String selecionaMural(@RequestParam(value="id_mural") String sid_mural, @RequestParam(value="id_usuario") String sid_usuario, Model model) {
		DAO dao = new DAO();
		Integer id_mural = Integer.parseInt(sid_mural);
		Integer id_usuario = Integer.parseInt(sid_usuario);

		dao.close();

		model.addAttribute("id_mural", id_mural);
		model.addAttribute("id_usuario", id_usuario);
		return "mural";
	}

	@RequestMapping(value = "alteraMural", method = RequestMethod.POST)
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

	@RequestMapping(value = "deletaMural", method = RequestMethod.POST)
	public String deletaMural(@RequestParam(value="id_usuario") String sid_usuario,@RequestParam(value="id_mural") String sid_mural, Model model) {
		DAO dao;

		dao = new DAO();

		Integer id_usuario = Integer.parseInt(sid_usuario);

		dao.removeMural(Integer.parseInt(sid_mural));

		dao.close();

		model.addAttribute("id_usuario", id_usuario);
		return "home";
	}

	@RequestMapping(value = "criaMural", method = RequestMethod.POST)
	public String criaMural(@RequestParam(value="id_usuario") String sid_usuario, Model model) {
		DAO dao;

		dao = new DAO();

		Mural mural = new Mural();
		
		Integer id_usuario = Integer.parseInt(sid_usuario);
		
		mural.setIdUsuario(Integer.parseInt(sid_usuario));
		mural.setNome("Novo Mural");

		dao.adicionaMural(mural);
		dao.close();

		model.addAttribute("id_usuario", id_usuario);
		return "home";
	}

	@RequestMapping(value = "voltaHome", method = RequestMethod.POST)
	public String voltaHome(@RequestParam(value="home") String shome, Model model) {
		DAO dao = new DAO();
		Integer id_usuario = Integer.parseInt(shome);

		dao.close();
		model.addAttribute("id_usuario", id_usuario);
		return "home";
	}

	@RequestMapping(value = "criaNota", method = RequestMethod.POST)
	public String criaNota(@RequestParam(value="id_usuario") String sid_usuario, @RequestParam(value="id_mural") String sid_mural, @RequestParam(value="create_note") String create_note, Model model) {
		DAO dao = new DAO();
		Nota nota = new Nota();
		nota.setTipo("texto");
		nota.setConteudo(create_note);

		Integer id_mural = Integer.parseInt(sid_mural);
		Integer id_usuario = Integer.parseInt(sid_usuario);
		
		nota.setIdMural(id_mural);

		dao.adicionaNota(nota);

		dao.close();

		model.addAttribute("id_mural", id_mural);
		model.addAttribute("id_usuario", id_usuario);
		return "mural";
	}

	@RequestMapping(value = "deletaNota", method = RequestMethod.POST)
	public String deletaNota(@RequestParam(value="id_nota") String sid_nota, @RequestParam(value="id_mural") String sid_mural,@RequestParam(value="id_usuario") String sid_usuario, Model model) {
		DAO dao = new DAO();
		Integer id_nota = Integer.parseInt(sid_nota);
		Integer id_mural = Integer.parseInt(sid_mural);
		Integer id_usuario = Integer.parseInt(sid_usuario);

		dao.removeNota(id_nota);

		dao.close();

		model.addAttribute("id_mural", id_mural);
		model.addAttribute("id_usuario", id_usuario);
		return "mural";
	}

	@RequestMapping(value = "alteraNota", method = RequestMethod.POST)
	public String alteraNota(@RequestParam(value="id_mural") String sid_mural, @RequestParam(value="id_usuario") String sid_usuario, @RequestParam(value="id_nota") String sid_nota, @RequestParam(value="tipo_nota") String tipo_nota, @RequestParam(value="edit_nota") String edit_nota, Model model) {
		DAO dao = new DAO();
		Nota nota = new Nota();
		Integer id_mural = Integer.parseInt(sid_mural);
		Integer id_usuario = Integer.parseInt(sid_usuario);

		nota.setId(Integer.parseInt(sid_nota));
		nota.setTipo(tipo_nota);
		nota.setConteudo(edit_nota);

		dao.alteraNota(nota);
		dao.close();

		model.addAttribute("id_mural", id_mural);
		model.addAttribute("id_usuario", id_usuario);
		return "mural";
	}
	
	@RequestMapping(value = "criaBlob", method = RequestMethod.POST)
	public String criaBlob(@RequestParam(value="id_mural") String sid_mural, @RequestParam(value="id_usuario") String sid_usuario, @RequestParam(value="id_nota") String sid_nota, Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DAO dao;
		Integer id_mural = Integer.parseInt(sid_mural);
		Integer id_usuario = Integer.parseInt(sid_usuario);
		Integer id_nota = Integer.parseInt(sid_nota);
		
		PrintWriter out = response.getWriter();
		Part part = request.getPart("blob");

		if (part != null) {
			try {
				dao = new DAO();

				Nota nota = new Nota();
				InputStream input = part.getInputStream();

				nota.setId(id_nota);
				nota.setBlob(input);

				nota.setIdMural(id_mural);

				dao.adicionaBlob(nota);

				dao.close();

				model.addAttribute("id_mural", id_mural);
				model.addAttribute("id_usuario", id_usuario);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/mural.jsp");
				dispatcher.forward(request, response);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return "mural";
	}

	@RequestMapping(value = "mostraBlob", method = RequestMethod.POST)
	public String mostraBlob(@RequestParam(value="id_mural") String sid_mural, @RequestParam(value="id_usuario") String sid_usuario, @RequestParam(value="id_nota") String sid_nota, HttpServletResponse response, Model model) {
		DAO dao;
		Integer id_mural = Integer.parseInt(sid_mural);
		Integer id_usuario = Integer.parseInt(sid_usuario);
		Integer id_nota = Integer.parseInt(sid_nota);



		dao = new DAO();

		Nota nota = new Nota();

		nota.setId(id_nota);

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



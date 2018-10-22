package mvc.model;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO {
	private Connection connection = null;
	public DAO() {
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/projeto1", "root", "senhamysql");
			
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
	}
	

	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void adicionaUsuario(Usuario usuario) {
		String sql = "INSERT INTO usuarios " + 
				"(nome,email,senha) values(?,?,?)";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, usuario.getNome());
			stmt.setString(2, usuario.getEmail());
			stmt.setString(3, usuario.getSenha());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void alteraUsuario(Usuario usuario) {
		String sql = "UPDATE usuarios SET " + 
				"nome=?, email=?, senha=? WHERE id_usuario=?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1,  usuario.getNome());
			stmt.setString(2, usuario.getEmail());
			stmt.setString(3, usuario.getSenha());
			stmt.setInt(4, usuario.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removeUsuario(Integer idUsuario) {
		String sql = "DELETE FROM usuarios WHERE id_usuario=?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setLong(1, idUsuario);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Usuario> getListaUsuarios() {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		String sql = "SELECT * FROM usuarios";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id_usuario"));
				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
				usuarios.add(usuario);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuarios;
	}
	
	public void adicionaMural(Mural mural) {
		String sql = "INSERT INTO murais " + 
				"(id_usuario, nome) values(?,?)";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, mural.getIdUsuario());
			stmt.setString(2, mural.getNome());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void alteraMural(Mural mural) {
		String sql = "UPDATE murais SET " + 
				"nome=?, estilo=? WHERE id_mural=?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, mural.getNome());
			stmt.setString(2, mural.getEstilo());
			stmt.setInt(3, mural.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removeMural(Integer idMural) {
		String sql1 = "DELETE FROM notas WHERE id_mural = ?";
		String sql2 = "DELETE FROM murais WHERE id_mural=?";
		PreparedStatement stmt1;
		PreparedStatement stmt2;
		try {
			stmt1 = connection.prepareStatement(sql1);
			stmt1.setLong(1, idMural);
			stmt1.execute();
			stmt1.close();
			
			stmt2 = connection.prepareStatement(sql2);
			stmt2.setLong(1, idMural);
			stmt2.execute();
			stmt2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getNomeMural(int idMural) {
		String nomeMural = null;
		String sql = "SELECT * FROM murais WHERE " + "id_mural=?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, idMural);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				nomeMural = rs.getString("nome");
			}
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nomeMural;
	}
	
	public ArrayList<Mural> getListaMurais(int idUsuario) {
		ArrayList<Mural> murais = new ArrayList<Mural>();
		String sql = "SELECT * FROM murais WHERE " + "id_usuario=?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, idUsuario);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Mural mural = new Mural();
				mural.setId(rs.getInt("id_mural"));
				mural.setNome(rs.getString("nome"));
				mural.setDataCriacao(rs.getTimestamp("data_criacao"));
				mural.setUltimaMod(rs.getTimestamp("ultima_mod"));
				mural.setEstilo(rs.getString("estilo"));
				mural.setIdUsuario(rs.getInt("id_usuario"));
				murais.add(mural);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return murais;
	}
	

	public void adicionaNota(Nota nota) {
		String sql = "INSERT INTO notas " + 
				"(tipo, conteudo, id_mural) VALUES (?,?,?)";
		PreparedStatement stmt;
		
		try {
			
			//File file = new File(fileName);
			//FileInputStream input = new FileInputStream(file);
			
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, nota.getTipo());
			stmt.setString(2, nota.getConteudo());
			stmt.setInt(3, nota.getIdMural());
			
			stmt.execute();
			stmt.close();
		} catch (SQLException  e) {
			e.printStackTrace();
		}
	}
	
	public void adicionaBlob(Nota nota) {
		String sql = "UPDATE notas SET bloob=?" + 
					"WHERE id_nota=?";
		PreparedStatement stmt;
		
		try {
			
			stmt = connection.prepareStatement(sql);
			
			stmt.setBlob(1, nota.getBlob());
			stmt.setInt(2, nota.getId());
			
			stmt.execute();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public byte[] viewBlob(Nota nota) {
		String sql = "SELECT bloob FROM notas WHERE id_nota=?";
		PreparedStatement stmt;
		
		try {
			stmt = connection.prepareStatement(sql);
			
			stmt.setInt(1, nota.getId());
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				Blob blob = rs.getBlob("bloob");
				byte byteArray[] = blob.getBytes(1, (int) blob.length());
				return byteArray;
			}
			else {
				System.out.println("Deu ruim");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public void alteraNota(Nota nota) {
		String sql = "UPDATE notas SET " + 
				"tipo=?, conteudo=? WHERE id_nota=?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, nota.getTipo());
			stmt.setString(2, nota.getConteudo());
			stmt.setLong(3, nota.getId());
			
			stmt.execute();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Nota> getListaNotas(int idMural) {
		ArrayList<Nota> notas = new ArrayList<Nota>();
		String sql = "SELECT * FROM notas WHERE " + "id_mural=?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setLong(1, idMural);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Nota nota = new Nota();
				nota.setId(rs.getInt("id_nota"));
				nota.setTipo(rs.getString("tipo"));
				nota.setConteudo(rs.getString("conteudo"));
				nota.setIdMural(rs.getInt("id_mural"));
				notas.add(nota);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return notas;
	}
	
	public void removeNota(Integer idNota) {
		String sql = "DELETE FROM notas WHERE id_nota=?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setLong(1, idNota);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
//	public void filtroMural(Integer idMural) {
//		String sql = "SELECT * FROM murais " 
//				+ "WHERE nome = '%?%'";
//		PreparedStatement stmt;
//		try {
//			stmt = connection.prepareStatement(sql);
//			stmt.setString(1, "obaaaaaaaaa");
//			
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		
//	}
	
}

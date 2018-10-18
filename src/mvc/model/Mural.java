package mvc.model;

import java.sql.Timestamp;

public class Mural {
	private Integer id;
	private String nome;
	private Timestamp dataCriacao;
	private Timestamp ultimaMod;
	private String estilo;
	private Integer idUsuario;
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setDataCriacao(Timestamp dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public void setUltimaMod(Timestamp ultimaMod) {
		this.ultimaMod = ultimaMod;
	}
	
	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}
	
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public Timestamp getDataCriacao() {
		return dataCriacao;
	}
	
	public Timestamp getUltimaMod() {
		return ultimaMod;
	}
	
	public String getEstilo() {
		return estilo;
	}
	
	public Integer getIdUsuario() {
		return idUsuario;
	}
}

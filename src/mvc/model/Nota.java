package mvc.model;

import java.io.InputStream;

public class Nota {
	private Integer id;
	private String tipo;
	private String conteudo;
	private Integer idMural;
	private InputStream blob;
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	public void setIdMural(Integer idMural) {
		this.idMural = idMural;
	}
	
	public void setBlob(InputStream blob) {
		this.blob = blob;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public String getConteudo() {
		return conteudo;
	}
	
	public Integer getIdMural() {
		return idMural;
	}
	
	public InputStream getBlob() {
		return blob;
	}
}

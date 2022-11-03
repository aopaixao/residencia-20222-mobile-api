package br.com.residencia.api_livraria.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "autores")
public class Autor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_autor")
	private Integer codigoAutor;

	@Column(name = "nome_autor")
	private String nomeAutor;

	@OneToMany(mappedBy = "autor")
	private Set<Livro> livros;

	public Integer getCodigoAutor() {
		return codigoAutor;
	}

	public void setCodigoAutor(Integer codigoAutor) {
		this.codigoAutor = codigoAutor;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public void setNomeAutor(String nomeAutor) {
		this.nomeAutor = nomeAutor;
	}

	public Set<Livro> getLivros() {
		return livros;
	}

	public void setLivros(Set<Livro> livros) {
		this.livros = livros;
	}
}
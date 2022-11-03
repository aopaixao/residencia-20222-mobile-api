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
@Table(name = "editora")
public class Editora {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_editora")
	private Integer codigoEditora;

	@Column(name = "nome_editora")
	private String nomeEditora;
	
	@Column(name = "nome_imagem")
	private String nomeImagem;

	@Column(name = "nome_arquivo_imagem")
	private String nomeArquivoImagem;

	@Column(name = "url_imagem")
	private String urlImagem;
	
	@OneToMany(mappedBy = "editora")
	private Set<Livro> livros;

	public Integer getCodigoEditora() {
		return codigoEditora;
	}

	public void setCodigoEditora(Integer codigoEditora) {
		this.codigoEditora = codigoEditora;
	}

	public String getNomeEditora() {
		return nomeEditora;
	}

	public void setNomeEditora(String nomeEditora) {
		this.nomeEditora = nomeEditora;
	}

	public String getNomeImagem() {
		return nomeImagem;
	}

	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}

	public String getNomeArquivoImagem() {
		return nomeArquivoImagem;
	}

	public void setNomeArquivoImagem(String nomeArquivoImagem) {
		this.nomeArquivoImagem = nomeArquivoImagem;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public Set<Livro> getLivros() {
		return livros;
	}

	public void setLivros(Set<Livro> livros) {
		this.livros = livros;
	}
}
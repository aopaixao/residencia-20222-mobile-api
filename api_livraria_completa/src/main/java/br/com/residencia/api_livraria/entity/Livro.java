package br.com.residencia.api_livraria.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "livros")
public class Livro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_livro")
	private Integer codigoLivro;

	@Column(name = "nome_livro")
	private String nomeLivro;

	@Column(name = "data_lancamento")
	private Instant dataLancamento;

	@Column(name = "codigo_isbn")
	private Integer codigoIsbn;

	@Column(name = "nome_imagem")
	private String nomeImagem;

	@Column(name = "nome_arquivo_imagem")
	private String nomeArquivoImagem;

	@Column(name = "url_imagem")
	private String urlImagem;

	@ManyToOne
	@JoinColumn(name = "codigo_editora", referencedColumnName = "codigo_editora")
	private Editora editora;
	
	@ManyToOne
	@JoinColumn(name = "codigo_autor", referencedColumnName = "codigo_autor")
	private Autor autor;

	public Integer getCodigoLivro() {
		return codigoLivro;
	}

	public void setCodigoLivro(Integer codigoLivro) {
		this.codigoLivro = codigoLivro;
	}

	public String getNomeLivro() {
		return nomeLivro;
	}

	public void setNomeLivro(String nomeLivro) {
		this.nomeLivro = nomeLivro;
	}

	public Instant getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Instant dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public Integer getCodigoIsbn() {
		return codigoIsbn;
	}

	public void setCodigoIsbn(Integer codigoIsbn) {
		this.codigoIsbn = codigoIsbn;
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

	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
}
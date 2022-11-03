package br.com.residencia.api_livraria.dto;

import java.time.Instant;

public class LivroDTO {
	private Integer codigoLivro;
	private String nomeLivro;
	private Instant dataLancamento;
	private Integer codigoIsbn;
	private String nomeImagem;
	private String nomeArquivoImagem;
	private String urlImagem;
	private EditoraDTO editoraDTO;
	private AutorDTO autorDTO;

	public LivroDTO() {

	}

	public LivroDTO(Integer codigoLivro, String nomeLivro, Instant dataLancamento, Integer codigoIsbn,
			String nomeImagem, String nomeArquivoImagem, String urlImagem, EditoraDTO editoraDTO, AutorDTO autorDTO) {
		super();
		this.codigoLivro = codigoLivro;
		this.nomeLivro = nomeLivro;
		this.dataLancamento = dataLancamento;
		this.codigoIsbn = codigoIsbn;
		this.nomeImagem = nomeImagem;
		this.nomeArquivoImagem = nomeArquivoImagem;
		this.urlImagem = urlImagem;
		this.editoraDTO = editoraDTO;
		this.autorDTO = autorDTO;
	}
	
	public LivroDTO(Integer codigoLivro, String nomeLivro, Instant dataLancamento, Integer codigoIsbn,
			String nomeImagem, String nomeArquivoImagem, String urlImagem) {
		super();
		this.codigoLivro = codigoLivro;
		this.nomeLivro = nomeLivro;
		this.dataLancamento = dataLancamento;
		this.codigoIsbn = codigoIsbn;
		this.nomeImagem = nomeImagem;
		this.nomeArquivoImagem = nomeArquivoImagem;
		this.urlImagem = urlImagem;
	}

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

	public EditoraDTO getEditoraDTO() {
		return editoraDTO;
	}

	public void setEditoraDTO(EditoraDTO editoraDTO) {
		this.editoraDTO = editoraDTO;
	}

	public AutorDTO getAutorDTO() {
		return autorDTO;
	}

	public void setAutorDTO(AutorDTO autorDTO) {
		this.autorDTO = autorDTO;
	}
}
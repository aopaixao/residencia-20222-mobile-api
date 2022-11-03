package br.com.residencia.api_livraria.dto;

import java.util.List;

public class EditoraDTO {
	
	private Integer codigoEditora;
	private String nomeEditora;
	private String nomeImagem;
	private String nomeArquivoImagem;
	private String urlImagem;	
	private List<LivroDTO> listaLivrosDTO;

	public EditoraDTO() {
	}

	public EditoraDTO(Integer codigoEditora, String nomeEditora, String nomeImagem, String nomeArquivoImagem,
			String urlImagem, List<LivroDTO> listaLivrosDTO) {
		super();
		this.codigoEditora = codigoEditora;
		this.nomeEditora = nomeEditora;
		this.nomeImagem = nomeImagem;
		this.nomeArquivoImagem = nomeArquivoImagem;
		this.urlImagem = urlImagem;
		this.listaLivrosDTO = listaLivrosDTO;
	}

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

	public List<LivroDTO> getListaLivrosDTO() {
		return listaLivrosDTO;
	}

	public void setListaLivrosDTO(List<LivroDTO> listaLivrosDTO) {
		this.listaLivrosDTO = listaLivrosDTO;
	}
}
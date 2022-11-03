package br.com.residencia.api_livraria.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.residencia.api_livraria.dto.AutorDTO;
import br.com.residencia.api_livraria.dto.EditoraDTO;
import br.com.residencia.api_livraria.dto.LivroDTO;
import br.com.residencia.api_livraria.dto.imgbb.ImgBBDTO;
import br.com.residencia.api_livraria.entity.Autor;
import br.com.residencia.api_livraria.entity.Editora;
import br.com.residencia.api_livraria.entity.Livro;
import br.com.residencia.api_livraria.repository.EditoraRepository;
import br.com.residencia.api_livraria.repository.LivroRepository;

@Service
public class LivroService {
	@Autowired
	LivroRepository livroRepository;
	
	@Autowired
	EditoraRepository editoraRepository;
	
	@Value("${imgbb.host.url}")
	private String imgBBHostUrl;
	
	@Value("${imgbb.host.key}")
    private String imgBBHostKey;
	
	public List<LivroDTO> getAllLivros(){
		List<Livro> listaLivro = livroRepository.findAll();
		List<LivroDTO> listaLivroDTO = new ArrayList<>();
		
		for(Livro livro: listaLivro) {
			LivroDTO livroDTO = toDTO(livro);
			listaLivroDTO.add(livroDTO);
		}
		
		return listaLivroDTO;
	}
	
	public LivroDTO getLivroById(Integer id) {
		Livro livro = livroRepository.findById(id).orElse(null);
		LivroDTO livroDTO = new LivroDTO();
		
		if(livro != null)
			livroDTO = toDTO(livro);
		
		return livroDTO;
	}
	
	public List<LivroDTO> getAllLivrosByEditora(Integer codigoEditora) {
		
		Editora editora = editoraRepository.findById(codigoEditora).orElse(null);
		List<Livro> listaLivros = new ArrayList<>();
		List<LivroDTO> listaLivrosDTO = new ArrayList<>();
		
		if(editora != null)
			listaLivros = livroRepository.findByEditora(editora);
		
		
		if(!listaLivros.isEmpty()) 
			listaLivrosDTO = listaLivros.stream()
			        .map(entity -> new LivroDTO(entity.getCodigoLivro(), entity.getNomeLivro(), entity.getDataLancamento(),
			        		entity.getCodigoIsbn(), entity.getNomeImagem(), entity.getNomeArquivoImagem(), entity.getUrlImagem()))
			        .collect(Collectors.toList());
		
		return listaLivrosDTO;
	}
	
	public LivroDTO saveLivro(
			String livroDTO,
			MultipartFile file
	) throws IOException {
		RestTemplate restTemplate = new RestTemplate();
		String serverUrl = imgBBHostUrl + imgBBHostKey;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
		
		ContentDisposition contentDisposition = ContentDisposition
				.builder("form-data")
				.name("image")
				.filename(file.getOriginalFilename())
				.build();
		
		fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
		
		HttpEntity<byte[]> fileEntity = new HttpEntity<>(file.getBytes(), fileMap);
		
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("image", fileEntity);
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity =
				new HttpEntity<>(body, headers);
		
		ResponseEntity<ImgBBDTO> response = null;
		ImgBBDTO imgDTO = new ImgBBDTO();
		Livro novaLivro = new Livro(); 
		try {
			response = restTemplate.exchange(
					serverUrl,
					HttpMethod.POST,
					requestEntity,
					ImgBBDTO.class);
			
			imgDTO = response.getBody();
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		
		if(null != imgDTO) {
			Livro livroFromJson = convertLivroFromStringJson(livroDTO);
			livroFromJson.setNomeArquivoImagem(imgDTO.getData().getImage().getFilename());
			livroFromJson.setNomeImagem(imgDTO.getData().getTitle());
			livroFromJson.setUrlImagem(imgDTO.getData().getUrl());
			novaLivro = livroRepository.save(livroFromJson);
		}
		
		return toDTO(novaLivro);
	}	
	
	public LivroDTO updateLivro(LivroDTO livroDTO, Integer id) {
		Livro livroExistenteNoBanco = toEntidade(getLivroById(id));
		LivroDTO livroAtualizadaDTO = new LivroDTO();
		
		if(livroExistenteNoBanco != null) {
			livroDTO.setCodigoLivro(livroExistenteNoBanco.getCodigoLivro());
			livroExistenteNoBanco = toEntidade(livroDTO);
			
			Livro livroAtualizada = livroRepository.save(livroExistenteNoBanco);
			
			livroAtualizadaDTO = toDTO(livroAtualizada);
		}
		
		return livroAtualizadaDTO;
	}
	
	public LivroDTO deleteLivro(Integer id) {
		if(getLivroById(id) != null) {
			livroRepository.deleteById(id);
		}
		
		return getLivroById(id);	
	}
	
	public Long count() {
		return livroRepository.count();
	}
	
	private Livro toEntidade(LivroDTO livroDTO ) {
		Livro livro = new Livro();
		Editora editora = new Editora();
		Autor autor = new Autor();
		
		BeanUtils.copyProperties(livroDTO, livro);
		BeanUtils.copyProperties(livroDTO.getEditoraDTO(), editora);
		BeanUtils.copyProperties(livroDTO.getAutorDTO(), autor);
		
		livro.setEditora(editora);
		livro.setAutor(autor);
		
		return livro;
	}
	
	public LivroDTO toDTO(Livro livro) {
		LivroDTO livroDTO = new LivroDTO();
		EditoraDTO editoraDTO = new EditoraDTO();
		AutorDTO autorDTO = new AutorDTO();
		
		BeanUtils.copyProperties(livro, livroDTO);
		BeanUtils.copyProperties(livro.getEditora(), editoraDTO);
		BeanUtils.copyProperties(livro.getAutor(), autorDTO);
		
		livroDTO.setEditoraDTO(editoraDTO);
		livroDTO.setAutorDTO(autorDTO);
		
		return livroDTO;
	}
	
	private Livro convertLivroFromStringJson(String livroJson) {
		LivroDTO livroDTO = new LivroDTO();
		
		try {
			ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);

			objectMapper.registerModule(new JavaTimeModule());
			livroDTO = objectMapper.readValue(livroJson, LivroDTO.class);
		} catch (IOException err) {
			System.out.printf("Ocorreu um erro ao tentar converter a string json para um instância do DTO Livro", err.toString());
		}
		
		return toEntidade(livroDTO);
	}
}
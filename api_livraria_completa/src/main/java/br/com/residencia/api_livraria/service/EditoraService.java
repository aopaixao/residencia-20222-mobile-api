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

import br.com.residencia.api_livraria.dto.EditoraDTO;
import br.com.residencia.api_livraria.dto.LivroDTO;
import br.com.residencia.api_livraria.dto.imgbb.ImgBBDTO;
import br.com.residencia.api_livraria.entity.Editora;
import br.com.residencia.api_livraria.entity.Livro;
import br.com.residencia.api_livraria.repository.EditoraRepository;
import br.com.residencia.api_livraria.repository.LivroRepository;

@Service
public class EditoraService {
	@Autowired
	EditoraRepository editoraRepository;
	
	@Autowired
	LivroRepository livroRepository;
	
	@Autowired
	LivroService livroService;
	
	@Value("${imgbb.host.url}")
	private String imgBBHostUrl;
	
	@Value("${imgbb.host.key}")
    private String imgBBHostKey;
	
	public List<EditoraDTO> getAllEditoras(){
		List<Editora> listaEditora = editoraRepository.findAll();
		List<EditoraDTO> listaEditoraDTO = new ArrayList<>();
		
		for(Editora editora: listaEditora) {
			EditoraDTO editoraDTO = toDTO(editora);
			listaEditoraDTO.add(editoraDTO);
		}
		
		return listaEditoraDTO;
	}
	
	public EditoraDTO getEditoraById(Integer id) {
		Editora editora = editoraRepository.findById(id).orElse(null);
		EditoraDTO editoraDTO = new EditoraDTO();
		
		if(editora != null)
			editoraDTO = toDTO(editora);
		
		return editoraDTO;
	}
	
	public EditoraDTO saveEditora(
			String editoraDTO,
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
		Editora novaEditora = new Editora(); 
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
			Editora editoraFromJson = convertEditoraFromStringJson(editoraDTO);
			editoraFromJson.setNomeArquivoImagem(imgDTO.getData().getImage().getFilename());
			editoraFromJson.setNomeImagem(imgDTO.getData().getTitle());
			editoraFromJson.setUrlImagem(imgDTO.getData().getUrl());
			novaEditora = editoraRepository.save(editoraFromJson);
		}
		
		return toDTO(novaEditora);
	}	
	
	public EditoraDTO updateEditora(EditoraDTO editoraDTO, Integer id) {
		Editora editoraExistenteNoBanco = toEntidade(getEditoraById(id));
		EditoraDTO editoraAtualizadaDTO = new EditoraDTO();
		
		if(editoraExistenteNoBanco != null) {
			editoraDTO.setCodigoEditora(editoraExistenteNoBanco.getCodigoEditora());
			editoraExistenteNoBanco = toEntidade(editoraDTO);
			
			Editora editoraAtualizada = editoraRepository.save(editoraExistenteNoBanco);
			
			editoraAtualizadaDTO = toDTO(editoraAtualizada);
		}
		
		return editoraAtualizadaDTO;
	}
	
	public EditoraDTO deleteEditora(Integer id) {
		if(getEditoraById(id) != null) {
			editoraRepository.deleteById(id);
		}
		
		return getEditoraById(id);	
	}
	
	public Long count() {
		return editoraRepository.count();
	}
	
	private Editora toEntidade(EditoraDTO editoraDTO ) {
		Editora editora = new Editora();
		BeanUtils.copyProperties(editoraDTO, editora);
		
		return editora;
	}
	
	private EditoraDTO toDTO(Editora editora) {
		EditoraDTO editoraDTO = new EditoraDTO();
		List<LivroDTO> listaLivrosDTO = new ArrayList<>();
		
		BeanUtils.copyProperties(editora, editoraDTO);
		
		listaLivrosDTO = editora.getLivros().stream()
	        .map(entity -> new LivroDTO(entity.getCodigoLivro(), entity.getNomeLivro(), entity.getDataLancamento(),
	        		entity.getCodigoIsbn(), entity.getNomeImagem(), entity.getNomeArquivoImagem(), entity.getUrlImagem()))
	        .collect(Collectors.toList());
		
		editoraDTO.setListaLivrosDTO(listaLivrosDTO);
		return editoraDTO;
	}
	
	public List<EditoraDTO> getAllEditorasLivrosDTO(){
		List<Editora> listaEditora = editoraRepository.findAll();
		List<EditoraDTO> listaEditoraDTO = new ArrayList<>();
		
		for(Editora editora: listaEditora) {
			EditoraDTO editoraDTO = toDTO(editora);
			List<Livro> listaLivros = new ArrayList<>();
			List<LivroDTO> listaLivrosDTO = new ArrayList<>();
			
			listaLivros = livroRepository.findByEditora(editora);
			for(Livro livro : listaLivros) {
				LivroDTO livroDTO = livroService.toDTO(livro);
				listaLivrosDTO.add(livroDTO);
			}
			editoraDTO.setListaLivrosDTO(listaLivrosDTO);
			
			listaEditoraDTO.add(editoraDTO);
		}
		
		return listaEditoraDTO;
	}
	
	private Editora convertEditoraFromStringJson(String editoraJson) {
		EditoraDTO editoraDTO = new EditoraDTO();
		
		try {
			ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);

			objectMapper.registerModule(new JavaTimeModule());
			editoraDTO = objectMapper.readValue(editoraJson, EditoraDTO.class);
		} catch (IOException err) {
			System.out.printf("Ocorreu um erro ao tentar converter a string json para um instância do DTO Editora", err.toString());
		}
		
		return toEntidade(editoraDTO);
	}
}
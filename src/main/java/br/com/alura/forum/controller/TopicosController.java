package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.ActualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoReposiroty;
import br.com.alura.forum.repository.TopicoRespository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRespository topicoRepository;
	@Autowired
	private CursoReposiroty cursoRepository;
	
	@GetMapping
	public List<TopicoDto> lista(String nomeCurso){
		List<Topico> topicos = (nomeCurso == null) ? this.topicoRepository.findAll(): this.topicoRepository.findByCursoNome(nomeCurso);
		return TopicoDto.convert(topicos);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.convert(cursoRepository);
		this.topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {
		Optional<Topico> topico = this.topicoRepository.findById(id);
		if(topico.isPresent())
			return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> actualizar(@PathVariable Long id, @RequestBody @Valid ActualizacaoTopicoForm form){
		Optional<Topico> topico = this.topicoRepository.findById(id);
		if(topico.isPresent()) {
			Topico topicoEncontrado = form.actualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDto(topicoEncontrado));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Topico> topico = this.topicoRepository.findById(id);
		if(topico.isPresent()) {
			this.topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	
}

package br.com.alura.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRespository;

@Controller
public class TopicosController {
	
	@Autowired
	private TopicoRespository topicoRepository;
	
	@RequestMapping("/topicos")
	@ResponseBody
	public List<TopicoDto> lista(){
		List<Topico> topicos = this.topicoRepository.findAll();
		return TopicoDto.convert(topicos);
	}

}

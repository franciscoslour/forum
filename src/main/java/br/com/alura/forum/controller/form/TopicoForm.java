package br.com.alura.forum.controller.form;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoReposiroty;

public class TopicoForm {

	@NotNull
	@NotEmpty
	@Size(min = 5, message = "O titulo tem de ter pelo menos 5 caracteres")
	private String titulo;
	@NotNull
	@NotEmpty
	@Size(min = 5, message = "O mensagem tem de ter pelo menos 5 caracteres")
	private String mensagem;
	@NotNull
	@NotEmpty
	private String nomeCurso;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getNomeCurso() {
		return nomeCurso;
	}

	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}

	public Topico convert(CursoReposiroty cursoRepository) {
		Curso curso = cursoRepository.findByNome(nomeCurso);
		return new Topico(titulo, mensagem, curso);
	}

}

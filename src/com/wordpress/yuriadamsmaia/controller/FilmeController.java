package com.wordpress.yuriadamsmaia.controller;

import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.com.caelum.vraptor.view.Results;

import com.wordpress.yuriadamsmaia.dao.FilmeService;
import com.wordpress.yuriadamsmaia.model.Filme;

@Resource
public class FilmeController {

	private final Result result;
	private FilmeService filmeService;
	private Validator validator;
	
	public FilmeController(Result result, FilmeService filmeService, Validator validator) {
		this.result = result;
		this.filmeService = filmeService;
		this.validator = validator;
	}
	
	@Path("/filme")
	public void index(){
	}
	
	@Get
	@Path("/filme/novo")
	public void novo() {
	}
	
	@Post
	@Path("/filme/novo/salvar")
	public void novo(Filme filme) {
		validator.onErrorUse(Results.page()).of(FilmeController.class).novo();
		
		validaCamposObrigatorios(filme);
		
		try {
			filmeService.salvar(filme);
			result.include("notice", "Filme " + filme.getTitulo() + " Adicionado com Sucesso!");
		} catch (Exception e) {
			result.include("erros", e.getMessage());
		}
	
		result.forwardTo(this).index();
	}
	
	@Get
	@Path("/filme/{codigo}")
	public void filme(Integer codigo) {
		Filme filme = filmeService.loadById(codigo);
		result.include("filme", filme);
	}
	
	@Get
	@Path("/filme/{codigo}/alterar")
	public void edita(Integer codigo) {
		try{
			Filme filme = filmeService.loadById(codigo);
			result.include("editar", true);
			result.include("filme", filme);
			result.forwardTo(this).filme(codigo);
		}catch (Exception e) {
			result.include("erros", e.getMessage());
		}	
	}

	@Post
	@Path("/filme/alterar/edita")
	public void edita(Filme filme) {
		try {
			filmeService.editar(filme);
			result.include("notice", "Filme " + filme.getTitulo() + " editado com sucesso!");
		} catch (Exception e) {
			result.include("erros", e.getMessage());
		}	
		result.redirectTo(this).index();
	}
	
	@Get
	@Path("/filme/pesquisa")
	public void pesquisa(Filme filme){
		List<Filme> loadFilme = null;
		try{
			loadFilme = filmeService.pesquisar(filme);
			if(loadFilme.isEmpty()){
				result.include("notice", "Nenhum produto encontrado!!");
			}
			result.include("filmes", loadFilme);
			result.forwardTo(this).index();
		}catch(Exception e){
			result.include("erros", e.getMessage());
		} 
	
	}
	
	@Get
	@Path("/filme/deletar/{codigo}")
	public void deletar(Integer codigo) {
		try {
			filmeService.remover(codigo);
			result.include("notice", "Filme removido com sucesso");
			
		} catch (Exception e) {
			result.include("erros", e.getMessage());
		}
		result.forwardTo(this).index();
	}
	
	private void validaCamposObrigatorios(final Filme filme) {
		validator.checking(new Validations(){{
	        that(!filme.getTitulo().trim().isEmpty(), "ss", "required_field", "Titulo");
		}});
		
		validator.checking(new Validations(){{
	        that(filme.getAno() != null, "ss", "required_field", "Ano");
		}});
		
		validator.onErrorUse(Results.page()).of(FilmeController.class).novo();
	}
	
}

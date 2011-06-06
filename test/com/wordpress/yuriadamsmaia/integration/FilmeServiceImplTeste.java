package com.wordpress.yuriadamsmaia.integration;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wordpress.yuriadamsmaia.dao.FilmeService;
import com.wordpress.yuriadamsmaia.integration.base.dbunit.DbUnitManager;
import com.wordpress.yuriadamsmaia.model.Filme;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:./WebRoot/WEB-INF/config/spring/applicationContext.xml",
		"file:./WebRoot/WEB-INF/config/spring/applicationContext-persistence-test.xml"
})
public class FilmeServiceImplTeste {

	private static final int FILME_CODIGO = 10;

	private static final String FILME_ANO = "2002";

	private static final int LISTA_TAMANHO = 5;

	private static final String DATASET = "./test/com/wordpress/yuriadamsmaia/integration/base/dbunit/xml/FilmeServiceImplTeste.xml";

	private static final String FILME_TITULO = "HANNIBAL";
	
	@Autowired DbUnitManager dbUnitManager;
	@Autowired FilmeService filmeService;
	
	@Before
	public void setUp() {
		dbUnitManager.cleanAndInsert(DATASET);
	}
	
	@Test
	public void deveriaPesquisarFilmesSemCamposEspecificados(){
		Filme filme = new Filme();
		List<Filme> pesquisa = filmeService.pesquisar(filme);
		verificaListaRetornoPesquisa(pesquisa);
		assertEquals("deveria estar trazendo a lista de tamanho correto", pesquisa.size(), LISTA_TAMANHO);
		
	}
	
	@Test
	public void deveriaPesquisarFilmesPeloTitulo(){
		Filme filme = new Filme();
		filme.setTitulo(FILME_TITULO);
		List<Filme> pesquisa = filmeService.pesquisar(filme);
		verificaListaRetornoPesquisaPeloTitulo(pesquisa);
	}
	
	@Test
	public void deveriaPesquisarFilmesPeloAno(){
		Filme filme = new Filme();
		filme.setAno(FILME_ANO);
		List<Filme> pesquisa = filmeService.pesquisar(filme);
		verificaListaRetornoPesquisaPeloAno(pesquisa);
	}
	
	@Test
	public void deveriaPesquisarFilmesPorTituloENome(){
		Filme filme = new Filme();
		filme.setTitulo(FILME_TITULO);
		filme.setAno(FILME_ANO);
		List<Filme> pesquisa = filmeService.pesquisar(filme);
		verificaListaRetornoPesquisa(pesquisa);	
		assertEquals("deveria trazer um unico filme", pesquisa.size(), 1);
	}
	
	@Test
	public void deveriaCarregarUmPaciente(){
		Filme filme = filmeService.loadById(FILME_CODIGO);
		assertNotNull("nao deveria ser nulo", filme);
		assertEquals("deveria trazer o filme correto", filme.getTitulo(), FILME_TITULO);
	}
	
	@Test
	public void deveriaSalvarUmFilme(){
		Filme filme = criarUmFilme();
		Filme filmeSalvo = filmeService.salvar(filme);
		assertNotNull("Filme não deveria ser nulo.",filmeSalvo);
		assertEquals("os filmes deveriam ser iguais", filme, filmeSalvo);
	}
	
	@Test
	public void deveriaEditarUmFilme(){
		Filme filme = criarUmFilme();
		filme.setTitulo("MudouTitulo");
		Filme filmeSalvo = filmeService.editar(filme);
		assertNotNull("Filme não deveria ser nulo.",filmeSalvo);
		assertEquals("os filmes deveriam ser iguais", filme, filmeSalvo);
		assertEquals("MudouTitulo", filmeSalvo.getTitulo());
	}
	
	@Test
	public void deveriaExcluirFilme(){
		filmeService.remover(FILME_CODIGO);
		Filme filme = filmeService.loadById(FILME_CODIGO);
		assertNull("deveria não existir esse filme na base apos a remoção", filme);
	}
	
	private void verificaListaRetornoPesquisa(List<Filme> pesquisa) {
		assertNotNull("nao deveria ser nula", pesquisa);
		assertFalse("nao deveria retornar a lista vazia", pesquisa.isEmpty());
		assertEquals("deveria estar trazendo a lista correta", pesquisa.get(0).getTitulo(), FILME_TITULO);
		assertEquals("deveria estar trazendo a lista correta", pesquisa.get(0).getAno(), FILME_ANO);
	}
	
	private void verificaListaRetornoPesquisaPeloAno(List<Filme> pesquisa) {
		assertNotNull("nao deveria ser nula", pesquisa);
		assertFalse("nao deveria retornar a lista vazia", pesquisa.isEmpty());
		assertEquals("deveria estar trazendo a lista correta", pesquisa.get(0).getAno(), FILME_ANO);
	}
	
	private void verificaListaRetornoPesquisaPeloTitulo(List<Filme> pesquisa) {
		assertNotNull("nao deveria ser nula", pesquisa);
		assertFalse("nao deveria retornar a lista vazia", pesquisa.isEmpty());
		assertEquals("deveria estar trazendo a lista correta", pesquisa.get(0).getTitulo(), FILME_TITULO);
	}
	
	private Filme criarUmFilme() {
		Filme filme = new Filme();
		filme.setTitulo(FILME_TITULO);
		filme.setAno(FILME_ANO);
		return filme;
	}

}

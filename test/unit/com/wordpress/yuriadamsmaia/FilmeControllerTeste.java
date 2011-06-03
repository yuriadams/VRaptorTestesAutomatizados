package unit.com.wordpress.yuriadamsmaia;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.ValidationException;

import com.wordpress.yuriadamsmaia.controller.FilmeController;
import com.wordpress.yuriadamsmaia.dao.FilmeService;
import com.wordpress.yuriadamsmaia.model.Filme;

public class FilmeControllerTeste {
	public static final Integer CODIGO_FILME = 1;
	
	private Result result;
	private Validator validator;
	private FilmeController filmeController;
	
	@Mock private FilmeService filmeService;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		result = new MockResult();
		validator = new MockValidator();
		filmeController = new FilmeController(result, filmeService, validator);
	}
	
	@Test
	public void deveriaAbrirTelaInicialDeFilmes(){
		filmeController.index();
		assertFalse("não deveria haver erros!", result.included().containsKey("erros"));
	}
	
	
	@Test
	public void deveriaPesquisarFilmes(){
		when(filmeService.pesquisar(any(Filme.class))).thenReturn(new ArrayList<Filme>());
		filmeController.pesquisa(any(Filme.class));
		
		assertTrue("deve haver uma lista de Filmes", result.included().containsKey("filmes"));
		assertFalse("não deve exibir nenhuma msg de erro)", result.included().containsKey("erros"));
	}
	
	@Test
	public void deveriaTratarORetornoDeUmaListaNull(){
		when(filmeService.pesquisar(any(Filme.class))).thenReturn(new ArrayList<Filme>());
		filmeController.pesquisa(any(Filme.class));

		assertTrue("deve exibir uma mensagem de erro", result.included().containsKey("erros"));
		assertFalse("não deve exibir nenhuma msg de aviso)", result.included().containsKey("notice"));
		assertFalse("nao deve haver uma lista de Filmes", result.included().containsKey("Filmes"));
	}
	
	@Test
	public void deveriaTratarORetornoDeUmaListaVazia(){
		when(filmeService.pesquisar(criaFilme())).thenReturn(new ArrayList<Filme>());
		filmeController.pesquisa(criaFilme());
		assertTrue("deve exibir uma mensagem de aviso", result.included().containsKey("notice"));
		assertFalse("não deve exibir nenhuma msg de erro", result.included().containsKey("erros"));
	}
	
	@Test
	public void deveriaAbrirATelaDeCadastroDeFilmes(){
		filmeController.novo();
		assertFalse("nao deve exibir mensagens de erro", result.included().containsKey("erros"));
	}
	
	@Test
	public void deveriaCadastrarUmFilme(){
		Filme p = criaFilme();
		when(filmeService.salvar(p)).thenReturn(p);
		
		filmeController.novo(p);
		
		assertFalse("nao deve lançar msg de erro", result.included().containsKey("erro"));
		assertTrue("deve lançar uma msg de sucesso", result.included().containsKey("notice"));
	}
	
	@Test(expected=ValidationException.class)
	public void NaoDeveriaCadastrarUmFilmeVazio(){
		Filme f = criaFilmeVazio();
		when(filmeService.salvar(f)).thenReturn(f);
		
		filmeController.novo(f);
		
		assertFalse("nao deve lançar msg de sucesso", result.included().containsKey("notice"));
		assertTrue("deve lançar uma msg de erro", result.included().containsKey("erros"));
		assertTrue("deve lançar uma msg de validaçao", validator.hasErrors());
	}
	
	@Test
	public void NaoDeveriaCadastrarUmFilmeException(){
		Filme f = criaFilme();
		
		when(filmeService.salvar(f)).thenThrow(new RuntimeException());

		filmeController.novo(f);
		
		assertFalse("nao deve lançar msg de sucesso", result.included().containsKey("notice"));
		assertTrue("deve lançar uma msg de erro", result.included().containsKey("erros"));
	}
	
	@Test
	public void deveriaExcluirUmFilme(){
		filmeController.deletar(CODIGO_FILME);

		verify(filmeService).remover(CODIGO_FILME);
		
		assertTrue("deveria deletar o Filme", result.included().containsKey("notice"));
		assertFalse("deveria deletar o Filme", result.included().containsKey("erros"));
	}
	
	@Test
	public void naoDeveriaExcluirUmFilmeException() {
		doThrow(new RuntimeException()).when(filmeService).remover(CODIGO_FILME);
		
		filmeController.deletar(new Integer(1));
		
		assertTrue("deve lançar msg de erro", result.included().containsKey("erros"));
		assertFalse("nao deve lançar msg de aviso", result.included().containsKey("notice"));
	}
	
	@Test
	public void deveriaAbrirATelaDeVisualizarFilme(){
		when(filmeService.loadById(CODIGO_FILME)).thenReturn(new Filme());
		filmeController.filme(CODIGO_FILME);
		
		assertTrue("deve carregar Filme", result.included().containsKey("filme"));
		assertFalse("nao deve lançar msg de erros", result.included().containsKey("erros"));
	}
	
	@Test
	public void naoDeveriaAbrirTelaDeEditarException(){
		when(filmeService.loadById(CODIGO_FILME)).thenThrow(new RuntimeException());
		
		filmeController.edita(CODIGO_FILME);
		
		assertFalse("nao deve carregar Filme", result.included().containsKey("Filme"));
		assertTrue("nao deve lançar msg de erros", result.included().containsKey("erros"));
	}
	@Test
	public void deveriaAbrirTelaDeEditar(){
		when(filmeService.loadById(CODIGO_FILME)).thenReturn(new Filme());
		
		filmeController.edita(CODIGO_FILME);
		
		assertTrue("deve carregar Filme", result.included().containsKey("filme"));
		assertFalse("nao deve lançar msg de erros", result.included().containsKey("erros"));
	}
	
	@Test
	public void deveriaEditarUmFilme(){
		when(filmeService.editar(criaFilme())).thenReturn(criaFilme());
		
		filmeController.edita(criaFilme());
		
		assertFalse("nao deve possuir msg de erro", result.included().containsKey("erros"));
		assertTrue("deve possuir msg de aviso", result.included().containsKey("notice"));
		
	}
	
	@Test
	public void naoDeveriaEditarPacienteException(){
		when(filmeService.editar(criaFilme())).thenThrow(new RuntimeException());
		
		filmeController.edita(criaFilme());
		
		assertFalse("nao deve possuir msg de aviso", result.included().containsKey("notice"));
		assertTrue("deve possuir msg de erro", result.included().containsKey("erros"));
		
	}
	
	public Filme criaFilme(){
		Filme filme = new Filme();
		filme.setTitulo("chaves");
		filme.setAno("2002");
		return filme;
	}
	
	public Filme criaFilmeVazio(){
		Filme filme = new Filme();
		filme.setTitulo("");
		filme.setAno("");
		
		return filme;
	}

}

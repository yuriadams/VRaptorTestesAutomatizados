package com.wordpress.yuriadamsmaia.dao;

import java.util.List;

import com.wordpress.yuriadamsmaia.model.Filme;

public interface FilmeService {
	
	public Filme salvar(Filme filme);
	
	public void remover(Integer id);
	
	public Filme loadById(Integer id);
	
	public List<Filme> pesquisar(Filme filme);
	
	public Filme editar(Filme filme);

	
}

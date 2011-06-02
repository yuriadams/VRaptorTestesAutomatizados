package com.wordpress.yuriadamsmaia.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wordpress.yuriadamsmaia.dao.FilmeService;
import com.wordpress.yuriadamsmaia.model.Filme;

@Service("filmeService")
public class FilmeServiceImpl implements FilmeService{
	@PersistenceContext
	private EntityManager entityManager;
	private Session getSession() {
		return ((Session) entityManager.getDelegate());
	}
	
	@Override
	public Filme editar(Filme filme) {
		getSession().merge(filme);
		return filme;
	}

	@Override
	public Filme loadById(Integer id) {
		Criteria criteria = getSession().createCriteria(Filme.class);
		
		criteria.add(Restrictions.eq("codigo", id));
		
		return (Filme) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Filme> pesquisar(Filme filme) {
		Criteria criteria = getSession().createCriteria(Filme.class);
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		if (!StringUtils.isEmpty(filme.getTitulo()))
			criteria.add(Restrictions.like("titulo", "%" + filme.getTitulo() + "%"));
		if (!StringUtils.isEmpty(filme.getAno()))
			criteria.add(Restrictions.like("ano", "%" + filme.getAno() + "%"));
		
		criteria.addOrder(Order.asc("codigo"));
		return criteria.list();
	}

	@Override
	public void remover(Integer id) {
		Filme filme = loadById(id);
		entityManager.remove(filme);
	}

	@Override
	public Filme salvar(Filme filme) {
		getSession().save(filme);
		return filme;
	}

}

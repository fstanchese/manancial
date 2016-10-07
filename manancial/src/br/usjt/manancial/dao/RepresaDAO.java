package br.usjt.manancial.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import br.usjt.manancial.model.Represa;
import br.usjt.manancial.model.Sistema;

@Repository
public class RepresaDAO {

	@PersistenceContext
	EntityManager manager;
	
	public void adicionar(Represa represa) {
		manager.persist(represa);
	}	
	
	public void alterar(Represa represa) {
		manager.merge(represa);
	}

	public void remover(Represa represa) {
		manager.remove(represa);
	}

	public Represa buscaPorId(Long id) {
		Represa represa = manager.find(Represa.class, id);
		if (represa != null)
			Hibernate.initialize(represa.getSistema());
		return represa;
	}
	
	@SuppressWarnings("unchecked")
	public List<Represa> listar() {
		List<Represa> represas = manager.createNamedQuery("Represa.listar").getResultList();
		for (Represa represa : represas) {
			Hibernate.initialize(represa.getSistema());
		}
		return represas;
	}

	@SuppressWarnings("unchecked")
	public List<Represa> listarSistemaOrderByNome(Sistema sistema) {
		List<Represa> represas = manager.createNamedQuery("Represa.listarPorSistema").setParameter("sistema", sistema).getResultList();
		for (Represa represa : represas) {
			Hibernate.initialize(represa.getSistema());
		}
		return represas;		
	}
}

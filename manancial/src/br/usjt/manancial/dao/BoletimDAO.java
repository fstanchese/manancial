package br.usjt.manancial.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import br.usjt.manancial.model.Boletim;
import br.usjt.manancial.model.Represa;
import br.usjt.manancial.model.Sistema;

@Repository
public class BoletimDAO {

	@PersistenceContext
	EntityManager manager;
	
	public void adicionar(Boletim boletim) {
		manager.persist(boletim);
	}

	public void alterar(Boletim boletim) {
		manager.merge(boletim);
	}
	
	public void remover(Boletim boletim) {
		manager.remove(boletim);
	}
	
	public Boletim buscaPorId(Long id) {
		Boletim boletim = manager.find(Boletim.class, id);
		Hibernate.initialize(boletim.getRepresa().getSistema());
		return boletim;
	}

	@SuppressWarnings("unchecked")
	public List<Boletim> listar() {
		List<Boletim> boletins = manager.createNamedQuery("Boletim.listar").getResultList();
		for (Boletim boletim : boletins) {
			Hibernate.initialize(boletim.getRepresa().getSistema());
		}
		return boletins;
	}

	@SuppressWarnings("unchecked")
	public List<Boletim> ListarSistemaOrRepresaOrderByData(Sistema sistema, Represa represa) {
		List<Boletim> boletins = manager.createNamedQuery("Boletim.listarSistemaOrRepresa").
								setParameter("sistema", sistema).
								setParameter("represa", represa).getResultList();
		for (Boletim boletim : boletins) {
			Hibernate.initialize(boletim.getRepresa().getSistema());
		}		
		return boletins;
	}	

}

package br.usjt.manancial.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.usjt.manancial.model.Municipio;

@Repository
public class MunicipioDAO {

	@PersistenceContext
	EntityManager manager;

	public void adicionar(Municipio municipio) {
		manager.persist(municipio);
	}

	public void alterar(Municipio municipio) {
		manager.merge(municipio);
	}

	public void remover(Municipio municipio) {
		manager.remove(municipio);
	}
	
	public Municipio buscaPorId(Long id) {
		return manager.find(Municipio.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Municipio> listar() {
		return manager.createNamedQuery("Municipio.listar").getResultList();
	}
	
}

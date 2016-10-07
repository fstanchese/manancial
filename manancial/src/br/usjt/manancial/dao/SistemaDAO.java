package br.usjt.manancial.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.usjt.manancial.model.Sistema;

@Repository
public class SistemaDAO {

	@PersistenceContext
	EntityManager manager;

	public void salvar(Sistema sistema) {
		if (sistema.getId()==null) {
			manager.persist(sistema);
		} else {
			manager.merge(sistema);
		}
	}

	public void remover(Sistema sistema) {
		manager.remove(sistema);
	}
	
	public Sistema buscaPorId(Long id) {
		Sistema sistema = manager.find(Sistema.class, id);
		return sistema;
	}

	@SuppressWarnings("unchecked")
	public List<Sistema> listar() {
		List<Sistema> sistemas = manager.createNamedQuery("Sistema.listar").getResultList();
		return sistemas;
	}
	
}

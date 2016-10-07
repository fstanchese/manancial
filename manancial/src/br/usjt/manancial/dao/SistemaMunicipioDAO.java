package br.usjt.manancial.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.usjt.manancial.model.Municipio;
import br.usjt.manancial.model.Sistema;
import br.usjt.manancial.model.SistemaMunicipio;

@Repository
public class SistemaMunicipioDAO {

	@PersistenceContext
	EntityManager manager;

	public void salvar(SistemaMunicipio sistemaMunicipio) {
		if (sistemaMunicipio.getId()==null) {
			manager.persist(sistemaMunicipio);
		} else {
			manager.merge(sistemaMunicipio);
		}		
	}

	public void remover(SistemaMunicipio sistemaMunicipio) {
		manager.remove(sistemaMunicipio);
	}
	
	public SistemaMunicipio buscaPorId(Long id) {
		SistemaMunicipio sistemaMunicipio = manager.find(SistemaMunicipio.class, id);
		return sistemaMunicipio;
	}

	@SuppressWarnings("unchecked")
	public List<SistemaMunicipio> listar(Sistema sistema) {
		List<SistemaMunicipio> sistemaMunicipios = manager.createNamedQuery("SistemaMunicipio.listar").
				setParameter("sistema", sistema).getResultList();
		return sistemaMunicipios;
	}
	
	@SuppressWarnings("unchecked")
	public List<Municipio> listarMunicipios(Sistema sistema) {
		List<Municipio> municipios = manager.createNamedQuery("SistemaMunicipio.listarSistema").
				setParameter("sistema", sistema).getResultList();
		return municipios;
	}
	
}

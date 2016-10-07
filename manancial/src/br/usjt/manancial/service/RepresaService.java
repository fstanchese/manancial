package br.usjt.manancial.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usjt.manancial.dao.RepresaDAO;
import br.usjt.manancial.model.Represa;
import br.usjt.manancial.model.Sistema;

@Transactional
@Service
public class RepresaService {
	
	private RepresaDAO daoRepresa;

	@Autowired
	public RepresaService(RepresaDAO daoRepresa) {
		this.daoRepresa = daoRepresa;
	}
	
	public void adicionar(Represa represa) {
		daoRepresa.adicionar(represa);
	}

	public void alterar(Represa represa) {
		daoRepresa.alterar(represa);
	}
	
	public void remover(Long id) {
		Represa represa = buscaPorId(id);
		daoRepresa.remover(represa);
	}
	
	public Represa buscaPorId(Long id) {
		return daoRepresa.buscaPorId(id);
	}
	
	public List<Represa> listar() {
		return daoRepresa.listar();
	}
	
	public List<Represa> buscaPorSistema(Sistema sistema) {
		return daoRepresa.listarSistemaOrderByNome(sistema);
	}
	
}
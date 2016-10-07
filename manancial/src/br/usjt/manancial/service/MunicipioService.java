package br.usjt.manancial.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usjt.manancial.dao.MunicipioDAO;
import br.usjt.manancial.model.Municipio;

@Transactional
@Service
public class MunicipioService {

	private MunicipioDAO daoMunicipio;

	@Autowired
	public MunicipioService(MunicipioDAO daoMunicipio) {
		this.daoMunicipio = daoMunicipio;
	}
	
	public void adicionar(Municipio municipio) {
		daoMunicipio.adicionar(municipio);
	}
	
	public void alterar(Municipio municipio) {
		daoMunicipio.alterar(municipio);
	}
	
	public void remover(Long id) {
		Municipio municipio = buscaPorId(id);
		daoMunicipio.remover(municipio);
	}
	
	public Municipio buscaPorId(Long id) {
		return daoMunicipio.buscaPorId(id);
	}
	
	public List<Municipio> listar() {
		return daoMunicipio.listar();
	}
}
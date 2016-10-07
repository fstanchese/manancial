package br.usjt.manancial.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usjt.manancial.dao.SistemaMunicipioDAO;
import br.usjt.manancial.model.Municipio;
import br.usjt.manancial.model.Sistema;
import br.usjt.manancial.model.SistemaMunicipio;

@Transactional
@Service
public class SistemaMunicipioService {
	
	private SistemaMunicipioDAO daoSistemaMunicipio;

	@Autowired
	public SistemaMunicipioService(SistemaMunicipioDAO daoSistemaMunicipio) {
		this.daoSistemaMunicipio = daoSistemaMunicipio;
	}

	public void salvar(SistemaMunicipio sistemaMunicipio) {
		daoSistemaMunicipio.salvar(sistemaMunicipio);
	}
	
	public void remover(Long id) {
		SistemaMunicipio sistemaMunicipio = buscaPorId(id);
		daoSistemaMunicipio.remover(sistemaMunicipio);
	}
	
	public SistemaMunicipio buscaPorId(Long id) {
		return daoSistemaMunicipio.buscaPorId(id);
	}
	
	public List<SistemaMunicipio> listar(Sistema sistema) {
		return daoSistemaMunicipio.listar(sistema);
	}	
	
	public List<Municipio> listarMunicipios(Sistema sistema) {
		return daoSistemaMunicipio.listarMunicipios(sistema);
	}
}

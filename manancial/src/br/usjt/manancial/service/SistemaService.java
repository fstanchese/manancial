package br.usjt.manancial.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usjt.manancial.dao.SistemaDAO;
import br.usjt.manancial.dao.SistemaMunicipioDAO;
import br.usjt.manancial.model.Municipio;
import br.usjt.manancial.model.Sistema;
import br.usjt.manancial.model.SistemaMunicipio;

@Transactional
@Service
public class SistemaService {

	private SistemaDAO daoSistema;
	private SistemaMunicipioDAO daoSistemaMunicipio;
	private SistemaMunicipioService serviceSistemaMunicipio;

	@Autowired
	public SistemaService(SistemaDAO daoSistema, SistemaMunicipioDAO daoSistemaMunicipio,
			SistemaMunicipioService serviceSistemaMunicipio) {
		this.daoSistema = daoSistema;
		this.daoSistemaMunicipio = daoSistemaMunicipio;
		this.serviceSistemaMunicipio = serviceSistemaMunicipio;
	}

	public void salvar(Sistema sistema) {
		daoSistema.salvar(sistema);

		List<SistemaMunicipio> sistemaMunicipios = serviceSistemaMunicipio.listar(sistema);
		List<Municipio> municipios = sistema.getMunicipios();

		if (sistemaMunicipios != null) {
			excluirSistemaMunicipios(sistemaMunicipios, municipios);
			sistemaMunicipios = serviceSistemaMunicipio.listar(sistema);
		}
		if (municipios != null) {
			incluirSistemaMunicipios(sistema, sistemaMunicipios, municipios);
		}
	}

	private void incluirSistemaMunicipios(Sistema sistema, List<SistemaMunicipio> sistemaMunicipios,List<Municipio> municipios) {
		for (Municipio municipio : municipios) {
			Boolean incluir = true;
			for (SistemaMunicipio sistemaMunicipio : sistemaMunicipios) {
				if (sistemaMunicipio.getMunicipio().getId().equals(municipio.getId()))
					incluir = false;
			}
			if (incluir) {
				SistemaMunicipio sistemaMunicipio = new SistemaMunicipio();
				sistemaMunicipio.setMunicipio(municipio);
				sistemaMunicipio.setSistema(sistema);
				sistemaMunicipios.add(sistemaMunicipio);
			}
		}
		for (SistemaMunicipio sistemaMunicipio : sistemaMunicipios) {
			daoSistemaMunicipio.salvar(sistemaMunicipio);
		}
	}

	private void excluirSistemaMunicipios(List<SistemaMunicipio> sistemaMunicipios, List<Municipio> municipios) {
		for (SistemaMunicipio sistemaMunicipio : sistemaMunicipios) {
			Boolean excluir = true;
			if (municipios != null) {
				for (Municipio municipio : municipios) {
					if (municipio.getId().equals(sistemaMunicipio.getMunicipio().getId())) {
						excluir = false;
					}
				}
			}
			if (excluir) {
				daoSistemaMunicipio.remover(sistemaMunicipio);
			}
		}
	}

	public void remover(Long id) {
		Sistema sistema = buscaPorId(id);
		daoSistema.remover(sistema);
	}

	public Sistema buscaPorId(Long id) {
		return daoSistema.buscaPorId(id);
	}

	public List<Sistema> listar() {
		return daoSistema.listar();
	}
}

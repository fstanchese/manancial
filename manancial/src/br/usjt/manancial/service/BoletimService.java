package br.usjt.manancial.service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usjt.manancial.dao.BoletimDAO;
import br.usjt.manancial.model.Boletim;
import br.usjt.manancial.model.Represa;
import br.usjt.manancial.model.Sistema;

@Transactional
@Service
public class BoletimService {
	
	private BoletimDAO daoBoletim;

	@Autowired
	public BoletimService(BoletimDAO daoBoletim) {
		this.daoBoletim = daoBoletim;
	}
	
	public void adicionar(Boletim boletim) {
		daoBoletim.adicionar(boletim);
	}
	
	public void alterar(Boletim boletim) {
		daoBoletim.alterar(boletim);
	}
	
	public void remover(Long id) {
		Boletim boletim = this.buscaPorId(id);
		daoBoletim.remover(boletim);
	}
	
	public Boletim buscaPorId(Long id) {
		return daoBoletim.buscaPorId(id);
	}
	
	public List<Boletim> listar() {
		return daoBoletim.listar();
	}

	public List<Boletim> findAllBySistemaOrRepresaOrderByData(Sistema sistema, Represa represa) {
		List<Boletim> boletins = daoBoletim.ListarSistemaOrRepresaOrderByData(sistema,represa);
		if (boletins.size() > 0) {
			Boletim boletimInicio = boletins.get(0);
			Boletim boletimFim = boletins.get(0);
			
			Date dataInicio 	= boletimInicio.getDataBoletim();
			Date dataFim		= boletimInicio.getDataBoletim();
			Represa represaIni 	= boletimInicio.getRepresa();
			
			for (Boletim boletim : boletins) {
				if (!boletim.getDataBoletim().equals(dataInicio) &&	!boletim.getRepresa().getId().equals(represaIni.getId())) {		
					dataInicio 	= boletim.getDataBoletim();
					boletimInicio = boletim;
					dataFim		= boletim.getDataBoletim();
					boletimFim = boletim;
					represaIni 	= boletim.getRepresa();					
				}
				if (boletim.getDataBoletim().equals(dataInicio) && boletim.getRepresa().getId().equals(represaIni.getId())) {
					dataFim 	= boletim.getDataBoletim();
					boletimFim = boletim;
					represaIni 	= boletim.getRepresa();					
				}
				if (!boletim.getDataBoletim().equals(dataInicio) && boletim.getRepresa().getId().equals(represaIni.getId())) {
					dataFim = boletim.getDataBoletim();
					boletimFim = boletim;
				}
				Double variacao = this.calculaVariacaoPorBoletins(boletimInicio, boletimFim);
				boletim.setVariacao(variacao);
				if (!boletim.getDataBoletim().equals(dataInicio) && boletim.getRepresa().getId().equals(represaIni.getId())) {
					dataInicio 	= dataFim;
					boletimInicio = boletim;
				}
			}
		}
		return boletins;
	}
	
	public double calculaVariacaoPorBoletins(Boletim boletimInicio, Boletim boletimFim) {
		Double volumeInicio = boletimInicio.getVolumeDia();
		Double volumeFim	= boletimFim.getVolumeDia();
		
		Double valor = ((100*(volumeFim - volumeInicio))/volumeInicio);
        DecimalFormat formato = new DecimalFormat("##.##");
		String dx = formato.format(valor);
		return Double.parseDouble(dx.replace(",", "."));
	}
}
package br.usjt.manancial.properties;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.usjt.manancial.dao.MunicipioDAO;
import br.usjt.manancial.model.Municipio;

@Component
public class MunicipioPropertyEditor extends PropertyEditorSupport {

	@Autowired 
	private MunicipioDAO dao;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id;
		Municipio municipio;
		try {
			id = Long.parseLong(text);
			municipio = dao.buscaPorId(id);
		} catch (Exception e) {
			municipio = null;
		}
		setValue(municipio);
	}
}

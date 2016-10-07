package br.usjt.manancial.properties;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.usjt.manancial.dao.RepresaDAO;
import br.usjt.manancial.model.Represa;

@Component
public class RepresaPropertyEditor extends PropertyEditorSupport {

	@Autowired 
	private RepresaDAO dao;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id;
		Represa represa;
		try {
			id = Long.parseLong(text);
			represa = dao.buscaPorId(id);
		} catch (Exception e) {
			represa = null;
		}
		setValue(represa);
	}
}

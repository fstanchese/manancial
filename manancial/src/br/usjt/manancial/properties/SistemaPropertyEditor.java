package br.usjt.manancial.properties;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.usjt.manancial.dao.SistemaDAO;
import br.usjt.manancial.model.Sistema;

@Component
public class SistemaPropertyEditor extends PropertyEditorSupport {

	@Autowired 
	private SistemaDAO dao;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id;
		Sistema sistema;
		try {
			id = Long.parseLong(text);
			sistema = dao.buscaPorId(id);
		} catch (Exception e) {
			sistema = null;
		}
		setValue(sistema);
	}
}

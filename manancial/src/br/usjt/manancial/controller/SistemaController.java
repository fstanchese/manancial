package br.usjt.manancial.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.Connection;

import br.usjt.manancial.model.Municipio;
import br.usjt.manancial.model.Sistema;
import br.usjt.manancial.properties.MunicipioPropertyEditor;
import br.usjt.manancial.service.MunicipioService;
import br.usjt.manancial.service.SistemaMunicipioService;
import br.usjt.manancial.service.SistemaService;
import br.usjt.manancial.util.GeradorRelatorio;
import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/sistemas")
public class SistemaController {

	private SistemaService serviceSistema;
	private MunicipioService serviceMunicipio;
	private SistemaMunicipioService serviceSistemaMunicipio;
	private MunicipioPropertyEditor municipioPropertyEditor;
	private DataSource dataSource;
	
	@Autowired
	public SistemaController(SistemaService serviceSistema, MunicipioService serviceMunicipio,
			SistemaMunicipioService serviceSistemaMunicipio, MunicipioPropertyEditor municipioPropertyEditor,
			DataSource dataSource) {
		this.serviceSistema = serviceSistema;
		this.serviceMunicipio = serviceMunicipio;
		this.serviceSistemaMunicipio = serviceSistemaMunicipio;
		this.municipioPropertyEditor = municipioPropertyEditor;
		this.dataSource = dataSource;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String listarTodos(Model model) {
		List<Sistema> sistemas = serviceSistema.listar();
		for (Sistema sistema : sistemas) {
			List<Municipio> municipios = serviceSistemaMunicipio.listarMunicipios(sistema);
			sistema.setMunicipios(municipios);
		}
		model.addAttribute("sistemas",sistemas);
		return "sistema/lista";
	}

	@RequestMapping(value="/novo")
	public String add(Model model) {
		model.addAttribute("sistema",new Sistema());
		model.addAttribute("municipioLista",serviceMunicipio.listar());
		return "sistema/crud";
	}

	@RequestMapping(value="/edit/{idSistema}",method = RequestMethod.GET)
	public String edit(Model model, @PathVariable("idSistema") Long idSistema) {
		model.addAttribute("sistema",serviceSistema.buscaPorId(idSistema));
		model.addAttribute("municipioLista",serviceMunicipio.listar());
		return "sistema/crud";
	}

	@RequestMapping(value="/delete/{idSistema}",method = RequestMethod.GET)
	public String delete(Model model, @PathVariable("idSistema") Long idSistema) {
		serviceSistema.remover(idSistema);
		model.addAttribute("sistemas",serviceSistema.listar());
		return "redirect:/sistemas";
	}
	
	@RequestMapping(value="/crudSistema",method = RequestMethod.POST)
	public String crud(@Valid Sistema sistema,BindingResult result, Model model) {
		if (!result.hasErrors()) {
			serviceSistema.salvar(sistema);
			return "redirect:/sistemas";
		} else {
			model.addAttribute("sistema",sistema);
			model.addAttribute("municipioLista",serviceMunicipio.listar());
			return "sistema/crud";
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "edit/sistemas/{id}")
	@ResponseBody
	public Sistema buscarSistema(@PathVariable Long id) {
		Sistema sistema = serviceSistema.buscaPorId(id);
		List<Municipio> municipios = serviceSistemaMunicipio.listarMunicipios(sistema);
		sistema.setMunicipios(municipios);
		return sistema;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(Municipio.class, municipioPropertyEditor);
	}
	
	@RequestMapping(value = "/relatorio", method = RequestMethod.GET)
	@ResponseBody
	public void getRelatorio(HttpServletRequest request,HttpServletResponse response) throws JRException, IOException, SQLException, ClassNotFoundException {
		String nome = request.getServletContext().getRealPath("/WEB-INF/report/sistemas.jasper");
        Map<String, Object> parametros = new HashMap<String, Object>();
		Connection connection = (Connection) dataSource.getConnection();

        GeradorRelatorio gerador = new GeradorRelatorio(nome, parametros, connection);
        gerador.geraPDFParaOutputStream(response.getOutputStream());
        connection.close();

	}
	
}

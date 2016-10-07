package br.usjt.manancial.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.Transactional;
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

import br.usjt.manancial.model.Represa;
import br.usjt.manancial.model.Sistema;
import br.usjt.manancial.properties.SistemaPropertyEditor;
import br.usjt.manancial.service.RepresaService;
import br.usjt.manancial.service.SistemaService;
import br.usjt.manancial.util.GeradorRelatorio;
import net.sf.jasperreports.engine.JRException;

@Transactional
@Controller
@RequestMapping("/represas")
public class RepresaController {

	private RepresaService serviceRepresa;
	private SistemaService serviceSistema;
	private SistemaPropertyEditor sistemaPropertyEditor;
	private DataSource dataSource;
	
	@Autowired
	public RepresaController(RepresaService serviceRepresa, 
			SistemaPropertyEditor sistemaPropertyEditor, SistemaService serviceSistema,DataSource dataSource) {
		this.serviceRepresa = serviceRepresa;
		this.sistemaPropertyEditor = sistemaPropertyEditor;
		this.serviceSistema = serviceSistema;
		this.dataSource = dataSource;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String listarTodos(Model model) {
		model.addAttribute("represas",serviceRepresa.listar());
		return "represa/lista";
	}
	
	@RequestMapping(value="/novo")
	public String add(Model model) {
		model.addAttribute("represa",new Represa());
		model.addAttribute("sistemas",serviceSistema.listar());
		return "represa/crud";
	}

	@RequestMapping(value="/edit/{idRepresa}",method = RequestMethod.GET)
	public String edit(Model model, @PathVariable("idRepresa") Long idRepresa) {
		model.addAttribute("represa",serviceRepresa.buscaPorId(idRepresa));
		model.addAttribute("sistemas",serviceSistema.listar());
		return "represa/crud";
	}

	@RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
	public String delete(Model model, @PathVariable("id") Long id) {
		serviceRepresa.remover(id);
		model.addAttribute("represas",serviceRepresa.listar());
		return "redirect:/represas";
	}
	
	@RequestMapping(value="/crudRepresa",method = RequestMethod.POST)
	public String crud(@Valid Represa represa, BindingResult result, Model model) {
		System.out.println("represa "+represa.toString());
		if (!result.hasErrors()) {
			if (represa.getId()==null){
				serviceRepresa.adicionar(represa);
			} else {
				serviceRepresa.alterar(represa);
			}
			return "redirect:/represas";
		} else {
			model.addAttribute("sistemas",serviceSistema.listar());
			model.addAttribute("represa",represa);
			return "represa/crud";
		}
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(Sistema.class, sistemaPropertyEditor);
	}
	
	@RequestMapping(value = "/relatorio", method = RequestMethod.GET)
	@ResponseBody
	public void getRelatorio(HttpServletRequest request,HttpServletResponse response) throws JRException, IOException, SQLException, ClassNotFoundException {
		String nome = request.getServletContext().getRealPath("/WEB-INF/report/represas.jasper");
        Map<String, Object> parametros = new HashMap<String, Object>();
		Connection connection = (Connection) dataSource.getConnection();
        
        GeradorRelatorio gerador = new GeradorRelatorio(nome, parametros, connection);
        gerador.geraPDFParaOutputStream(response.getOutputStream());
        connection.close();
	}
}

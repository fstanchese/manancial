package br.usjt.manancial.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.Connection;

import br.usjt.manancial.model.Municipio;
import br.usjt.manancial.service.MunicipioService;
import br.usjt.manancial.util.GeradorRelatorio;
import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/municipios")
public class MunicipioController {

	private MunicipioService serviceMunicipio;
	private DataSource dataSource;
	
	@Autowired
	public MunicipioController(MunicipioService serviceMunicipio,DataSource dataSource) {
		this.serviceMunicipio = serviceMunicipio;
		this.dataSource = dataSource;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String listarTodos(Model model) {
		model.addAttribute("municipios",serviceMunicipio.listar());
		return "municipio/lista";
	}
	
	@RequestMapping(value="/novo")
	public String add(Model model) {
		model.addAttribute("municipio",new Municipio());
		return "municipio/crud";
	}

	@RequestMapping(value="/edit/{idMunicipio}",method = RequestMethod.GET)
	public String edit(Model model, @PathVariable("idMunicipio") Long idMunicipio) {
		model.addAttribute("municipio",serviceMunicipio.buscaPorId(idMunicipio));
		return "municipio/crud";
	}

	@RequestMapping(value="/delete/{idMunicipio}",method = RequestMethod.GET)
	public String delete(Model model, @PathVariable("idMunicipio") Long idMunicipio) {
		serviceMunicipio.remover(idMunicipio);
		model.addAttribute("municipios",serviceMunicipio.listar());
		return "redirect:/municipios";
	}
	
	@RequestMapping(value="/crudMunicipio",method = RequestMethod.POST)
	public String crud(@Valid Municipio municipio,BindingResult result, Model model) {
		if (!result.hasErrors()) {
			if (municipio.getId()==null) {
				serviceMunicipio.adicionar(municipio);
			} else {
				serviceMunicipio.alterar(municipio);
			}
			return "redirect:/municipios";
		} else {
			model.addAttribute("municipio",municipio);
			return "municipio/crud";
		}
	}

	@RequestMapping(value = "/relatorio", method = RequestMethod.GET)
	@ResponseBody
	public void getRelatorio(HttpServletRequest request,HttpServletResponse response) throws JRException, IOException, SQLException, ClassNotFoundException {
		String nome = request.getServletContext().getRealPath("/WEB-INF/report/municipios.jasper");
        Map<String, Object> parametros = new HashMap<String, Object>();
		Connection connection = (Connection) dataSource.getConnection();
        
        GeradorRelatorio gerador = new GeradorRelatorio(nome, parametros, connection);
        gerador.geraPDFParaOutputStream(response.getOutputStream());
        connection.close();
	}
}

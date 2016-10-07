package br.usjt.manancial.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.usjt.manancial.model.Boletim;
import br.usjt.manancial.model.Represa;
import br.usjt.manancial.model.Sistema;
import br.usjt.manancial.properties.RepresaPropertyEditor;
import br.usjt.manancial.service.BoletimService;
import br.usjt.manancial.service.RepresaService;
import br.usjt.manancial.service.SistemaService;

@Controller
@RequestMapping("/boletins")
public class BoletimController {

	private BoletimService serviceBoletim;
	private SistemaService serviceSistema;
	private RepresaService serviceRepresa;
	private RepresaPropertyEditor represaPropertyEditor;
	
	@Autowired
	public BoletimController(BoletimService serviceBoletim, SistemaService serviceSistema, RepresaService serviceRepresa,
			RepresaPropertyEditor represaPropertyEditor) {
		this.serviceBoletim = serviceBoletim;
		this.serviceSistema = serviceSistema;
		this.serviceRepresa = serviceRepresa;
		this.represaPropertyEditor = represaPropertyEditor;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String listarTodos(Model model, @RequestParam(value="sistemaid",defaultValue = "0") Long sistemaid, @RequestParam(value="represaid",defaultValue = "0") Long represaid, HttpSession sessao) throws ParseException {
		Sistema sistema = null;
		Represa represa = null;
		
		if (sistemaid != 0) {
			sessao.setAttribute("sistemaid", sistemaid);
			sessao.setAttribute("represaid", represaid);
		}
		
		if (represaid != 0)
			sessao.setAttribute("represaid", represaid);

		model.addAttribute("sistemaid",sessao.getAttribute("sistemaid"));
		model.addAttribute("represaid",sessao.getAttribute("represaid"));
		
		if (sessao.getAttribute("sistemaid") != null)
			sistema = serviceSistema.buscaPorId((Long) sessao.getAttribute("sistemaid"));
		
		if (sessao.getAttribute("represaid") != null)
			represa = serviceRepresa.buscaPorId((Long) sessao.getAttribute("represaid"));

		model.addAttribute("sistemasFiltro",serviceSistema.listar());
		model.addAttribute("represasFiltro",serviceRepresa.buscaPorSistema(sistema));
		model.addAttribute("boletins", serviceBoletim.findAllBySistemaOrRepresaOrderByData(sistema,represa));
		return "boletim/lista";
	}
	
	@RequestMapping(value="/novo")
	public String add(Model model, HttpSession sessao ) {
		model.addAttribute("boletim",new Boletim());
		model.addAttribute("sistemas",serviceSistema.listar());
		return "boletim/crud";
	}

	@RequestMapping(value="/edit/{idBoletim}",method = RequestMethod.GET)
	public String edit(Model model, @PathVariable("idBoletim") Long idBoletim) {
		Boletim boletim = serviceBoletim.buscaPorId(idBoletim);
		
		model.addAttribute("boletim",boletim);
		model.addAttribute("sistemas",serviceSistema.listar());
		model.addAttribute("represas",serviceRepresa.buscaPorSistema(boletim.getRepresa().getSistema()));
		return "boletim/crud";
	}

	@RequestMapping(value="/delete/{idBoletim}",method = RequestMethod.GET)
	public String delete(Model model, @PathVariable("idBoletim") Long idBoletim, HttpSession sessao) {
		serviceBoletim.remover(idBoletim);
		
		model.addAttribute("sistemaid",sessao.getAttribute("sistemaid"));
		model.addAttribute("represaid",sessao.getAttribute("represaid"));
		
		return "redirect:/boletins";
	}
	
	@RequestMapping(value="/crudBoletim",method = RequestMethod.POST)
	public String crud(@Valid Boletim boletim,BindingResult result, Model model, HttpSession sessao) {
		if (!result.hasErrors()) {
			if (boletim.getId()==null) {
				serviceBoletim.adicionar(boletim);
				return "redirect:/boletins/novo"; 
			} else {
				serviceBoletim.alterar(boletim);
				return "redirect:/boletins"; 
			}			
		} else {
			model.addAttribute("sistemas",serviceSistema.listar());
			model.addAttribute("represas",serviceRepresa.buscaPorSistema(boletim.getRepresa().getSistema()));
			model.addAttribute("boletim",boletim);
			return "boletim/crud";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/represas/{idSistema}")
	@ResponseBody
	public List<Represa> buscarRepresa(@PathVariable Long idSistema) {
		Sistema sistema = serviceSistema.buscaPorId(idSistema);
		List<Represa> represas = serviceRepresa.buscaPorSistema(sistema);
		return represas;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(Represa.class, represaPropertyEditor);
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);
	    webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}

package com.allissonjardel.projeto.webcontroller;

import com.allissonjardel.projeto.utils.ModelViewAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.allissonjardel.projeto.service.AdministradorService;
import com.allissonjardel.projeto.service.RecepcionistaService;

import java.util.Map;


@Controller
@RequestMapping("/administrador")
public class AdministradorWebController {

	@Autowired
	RecepcionistaService rService;
	
	@Autowired
	AdministradorService service;

	@Autowired
	ModelViewAdapter modelViewAdapter;

	@RequestMapping("/paginainicial")
	public ModelAndView paginaAdministrador(Long ida) {

		Map<String, Object> listMap = Map.of("administrador", service.findById(ida),
				"listaRecepcionistas", rService.getAll());

		return modelViewAdapter.generateModelView("PaginaAdministrador", listMap);
	}
	
	@RequestMapping("/paginainicial/{ida}")
	public ModelAndView voltarPaginaAdministrador(@PathVariable Long ida) {

		Map<String, Object> listMap = Map.of("administrador", service.findById(ida),
				"listaRecepcionistas", rService.getAll());

		return modelViewAdapter.generateModelView("PaginaAdministrador", listMap);
	}


}

package com.allissonjardel.projeto.utils;

import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.allissonjardel.projeto.model.Administrador;
import com.allissonjardel.projeto.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class ModelViewAdapter {

	public ModelAndView generateModelView(String pagina, Map<String, Object> map) {

		ModelAndView mv = new ModelAndView(pagina);
		mv.addAllObjects(map);
		return mv;

	}
	public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
}

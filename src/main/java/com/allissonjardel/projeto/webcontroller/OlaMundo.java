package com.allissonjardel.projeto.webcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OlaMundo {

	@RequestMapping("/olamundo")
	public String olaMundo() {
		return "OlaMundo";
	}
	
}

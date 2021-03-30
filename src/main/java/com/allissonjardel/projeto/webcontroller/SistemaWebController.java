package com.allissonjardel.projeto.webcontroller;

import com.allissonjardel.projeto.utils.ModelViewAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.allissonjardel.projeto.model.Administrador;
import com.allissonjardel.projeto.model.Medico;
import com.allissonjardel.projeto.model.Paciente;
import com.allissonjardel.projeto.model.Recepcionista;
import com.allissonjardel.projeto.service.AdministradorService;
import com.allissonjardel.projeto.service.MedicoService;
import com.allissonjardel.projeto.service.PacienteService;
import com.allissonjardel.projeto.service.RecepcionistaService;


@Controller
@RequestMapping("/sistema")
public class SistemaWebController {

	@Autowired
	PacienteService pservice;

	@Autowired
	MedicoService mservice;

	@Autowired
	RecepcionistaService rservice;

	@Autowired
	AdministradorService aservice;
	
	@Autowired
	AdministradorWebController admWebController;
	
	@Autowired
	PacienteWebController pacienteWebController;
	
	@Autowired
	MedicoWebController medicoWebController;
	
	@Autowired
	RecepcionistaWebController recepcionistaWebController;

	@Autowired
	ModelViewAdapter modelViewAdapter;

	@RequestMapping("/paginainicial")
	public String paginaInicial() {
		
		return "PaginaInicial";
	}
	
	@RequestMapping("/paginaInicial")
	public String paginaLogin() {
		
		return "PaginaInicial";
	}
	
	@RequestMapping("/login")
	public  ModelAndView loginUsuario(String login, String senha, String usuario) {
		
		try {
			
			switch(usuario) {
			
				case "administador":
		
					Administrador adm = aservice.findByLoginSenha(login, senha);
					return admWebController.paginaAdministrador(adm.getId());	
					
				case "recepcionista":
					
					Recepcionista r = rservice.findByLoginSenha(login, senha);
					return recepcionistaWebController.paginaRecepcionistaModel(r.getId());
					
					
				case "medico":
					
					Medico m = mservice.findByLoginSenha(login, senha);
					if(!m.getStatus())
						return new ModelAndView("paginaInicial");
					
					return medicoWebController.paginaMedicoModel(m.getId());
					
				case "paciente":
					
					Paciente p = pservice.findByLoginSenha(login, senha);
					
					if(!p.getStatus())
						return new ModelAndView("paginaInicial");
					
					
					return pacienteWebController.paginaPacienteModel(p.getId());
					
				default:
					System.out.println("Error");
					break;
				
			}
			
			return new ModelAndView("paginaInicial");
			
		}catch(Exception e) {
			
			return new ModelAndView("paginaInicial");
		}
		
	}
}

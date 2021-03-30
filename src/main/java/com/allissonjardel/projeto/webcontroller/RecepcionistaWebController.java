package com.allissonjardel.projeto.webcontroller;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.allissonjardel.projeto.utils.ModelViewAdapter;
import com.allissonjardel.projeto.utils.ModelAndViewPages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.allissonjardel.projeto.entities.enums.StatusConsulta;
import com.allissonjardel.projeto.model.Consulta;
import com.allissonjardel.projeto.model.Recepcionista;
import com.allissonjardel.projeto.service.AdministradorService;
import com.allissonjardel.projeto.service.ConsultaService;
import com.allissonjardel.projeto.service.MedicoService;
import com.allissonjardel.projeto.service.RecepcionistaService;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/recepcionista")
public class RecepcionistaWebController {
	
	@Autowired
	AdministradorService aService;
	
	@Autowired
	RecepcionistaService service;
	
	@Autowired
	MedicoService mService;
	
	@Autowired
	ConsultaService cService;

	@Autowired
	ModelViewAdapter modelViewAdapter;

	@Autowired
	ModelAndViewPages modelAndViewPages;
	
	@RequestMapping("/cadastro")
	public ModelAndView cadastroRecepcionista(Long ida) {

		Map<String, Object> listMap = Map.of(
				"administrador",aService.findById(ida)
		);
		return modelViewAdapter.generateModelView("CadastroRecepcionista", listMap);
	}
	
	@RequestMapping("/paginainicial")
	public String paginaRecepcionista() {
		return "PaginaRecepcionista";
	}
	
	@RequestMapping("/voltar/{idr}")
	public ModelAndView voltar(@PathVariable Long idr) {
		
		Recepcionista recepcionista = service.findById(idr);

		Map<String, Object> listMap = modelAndViewPages.mapPaginaRecepcionista(recepcionista);

		return modelViewAdapter.generateModelView("PaginaRecepcionista", listMap);
	}
	
	@RequestMapping("/marcar/{ids}")
	public ModelAndView marcarConsulta(@PathVariable String ids) {
		String[] id = ids.split("-");
		
		Consulta consulta = cService.findById(Long.parseLong(id[0]));
		consulta.setStatusConsulta(StatusConsulta.MARCADA);
		cService.insert(consulta);
		
		Recepcionista recepcionista = service.findById(Long.parseLong(id[1]));

		Map<String, Object> listMap = modelAndViewPages.mapPaginaRecepcionista(recepcionista);

		return modelViewAdapter.generateModelView("PaginaRecepcionista", listMap);

	}
	
	@RequestMapping("/atualizar")
	public ModelAndView atualizarRecepcionista(Long idr, String nome, String endereco, String telefone, String dataNascimento) throws ParseException{
		Recepcionista recepcionista = null;
		try {

			recepcionista = service.update(idr, new Recepcionista(nome, endereco, telefone,
					LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy")), "", ""));

		}catch(Exception e) {
			recepcionista = service.findById(idr);
		}

		Map<String, Object> listMap = modelAndViewPages.mapPaginaRecepcionista(recepcionista);

		return modelViewAdapter.generateModelView("PaginaRecepcionista", listMap);
		
	}
	
	public ModelAndView paginaRecepcionistaModel(Long id) {

	
		Recepcionista recepcionista = service.findById(id);

		Map<String, Object> listMap = modelAndViewPages.mapPaginaRecepcionista(recepcionista);

		return modelViewAdapter.generateModelView("PaginaRecepcionista", listMap);
	}
	
	@RequestMapping("/salvar")
	public ModelAndView salvarRecepcionista(
			Long idr, String login, String senha, String nome, String endereco, String telefone, String dataNascimento, Long ida) {
		String pagina;
		Map<String, Object> listMap = null;

		try {

			pagina = "PaginaAdministrador";
			Recepcionista recepcionista = new Recepcionista(nome, endereco, telefone, LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
					login, senha);
			service.insert(recepcionista);
			listMap = Map.of(
					"listaRecepcionistas", service.getAll(),
					"administrador", aService.findById(ida)
			);

		}catch(Exception e) {
			listMap = Map.of(
					"administrador", aService.findById(ida)
			);
			pagina = "CadastroRecepcionista";
		}

		return modelViewAdapter.generateModelView(pagina, listMap);
	}
	
	@RequestMapping("/excluir/{ids}")
	public ModelAndView excluirRecepcionista(@PathVariable String ids) {
		
		String[] id = ids.split("-");
		
		service.deleteById(Long.parseLong(id[0]));

		Map<String, Object> listMap = Map.of(
				"listaRecepcionistas", service.getAll(),
				"administrador", aService.findById(Long.parseLong(id[1]))
		);
		return modelViewAdapter.generateModelView("PaginaAdministrador", listMap);
	}
	
	@RequestMapping("/registrar")
	public String loginUsuario(String entidade) {

		switch(entidade) {
		
		case "medico":
			return "CadastroMedico";
			
		case "paciente":
			return "CadastroPaciente";
			
		case "consulta":
			return "CadastroConsulta";
			
		default:
			System.out.println("Error");
			break;
		
		}
		
		return "OlaMundo";
	}



}

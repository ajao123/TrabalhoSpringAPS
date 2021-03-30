package com.allissonjardel.projeto.webcontroller;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

import com.allissonjardel.projeto.utils.ModelAndViewPages;
import com.allissonjardel.projeto.utils.ModelViewAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.allissonjardel.projeto.entities.enums.StatusConsulta;
import com.allissonjardel.projeto.entities.enums.TipoSanguineo;
import com.allissonjardel.projeto.model.Consulta;
import com.allissonjardel.projeto.model.Paciente;
import com.allissonjardel.projeto.model.Recepcionista;
import com.allissonjardel.projeto.service.ConsultaService;
import com.allissonjardel.projeto.service.DoencaService;
import com.allissonjardel.projeto.service.MedicoService;
import com.allissonjardel.projeto.service.PacienteService;
import com.allissonjardel.projeto.service.RecepcionistaService;


@Controller
@RequestMapping("/paciente")
public class PacienteWebController {

	@Autowired
	PacienteService service;
	
	@Autowired
	MedicoService mService;
	
	@Autowired
	ConsultaService cService;
	
	@Autowired
	RecepcionistaService rService;
	
	@Autowired
	DoencaService dService;

	@Autowired
	ModelViewAdapter modelViewAdapter;

	@Autowired
	ModelAndViewPages modelAndViewPages;

	@RequestMapping("/cadastro/{id}")
	public ModelAndView cadastroPaciente(@PathVariable Long id) {

		Map<String, Object> listMap = Map.of(
				"dataR", rService.findById(id).getDataNascimento().format(modelViewAdapter.formatter),
				"recepcionista", rService.findById(id)
		);
		return modelViewAdapter.generateModelView("CadastroPaciente", listMap);
		
	}
	
	@RequestMapping("/paginainicial")
	public String paginaPaciente() {
		return "PaginaPaciente";
	}
	

	public ModelAndView paginaPacienteModel(Long id) {
		
		Paciente paciente = service.findById(id);

		Map<String, Object> listMap = Map.of(
				"paciente", paciente,
				"dataR", paciente.getDate().format(modelViewAdapter.formatter),
				"listaConsultasMarcadas", cService.getByPacienteMarcada(id),
				"listaConsultasRealizadas", cService.getByPacienteRealizada(id),
				"listaConsultasIndeferidas", cService.getByPacienteIndeferida(id),
				"listaDoencas", dService.getDoencasByPaciente(id)
		);
		return modelViewAdapter.generateModelView("PaginaPaciente", listMap);
	}
	
	@RequestMapping("/salvar")
	public ModelAndView salvarPaciente(
		String login, String senha, String nome, String telefone, String date, String endereco, 
		TipoSanguineo tipoSanguineo, String observacao, Long idr) {
		Recepcionista recepcionista = rService.findById(idr);
		String pagina;
		Map<String, Object> listMap = null;

		try {
			
			Paciente paciente = new Paciente(nome, endereco, telefone, tipoSanguineo, LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
					new ArrayList<>(), new ArrayList<>(), login, senha);
			
			service.insert(paciente);

			pagina = "PaginaRecepcionista";

			listMap = modelAndViewPages.mapPaginaRecepcionista(recepcionista);
		}catch(Exception e) {
			pagina = "CadastroPaciente";
			listMap = Map.of(
					"recepcionista", recepcionista,
					"dataR", recepcionista.getDataNascimento().format(modelViewAdapter.formatter)
			);
		}


		return modelViewAdapter.generateModelView(pagina, listMap);
	}
	
	@RequestMapping("/marcar/{idp}")
	public ModelAndView MarcarConsulta(@PathVariable Long idp) {

		Map<String, Object> listMap = Map.of(
				"paciente", service.findById(idp),
				"listaMedicos", mService.getAtivos()
		);
		return modelViewAdapter.generateModelView("CadastrarPedidoConsulta", listMap);
	}
	
	@RequestMapping("/voltar/{idp}")
	public ModelAndView voltar(@PathVariable Long idp) {

		Paciente paciente = service.findById(idp);

		Map<String, Object> listMap = modelAndViewPages.mapPaginaPaciente(idp);

		return modelViewAdapter.generateModelView("PaginaPaciente", listMap);
	}

	@RequestMapping("/atualizar")
	public ModelAndView atualizarPaciente(Long id, String nome, String endereco, String telefone, String dataNascimento, TipoSanguineo tipoSanguineo) {
		Paciente paciente;

		try {
			paciente = new Paciente(nome, endereco, telefone, tipoSanguineo,
					LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
					new ArrayList<>(), new ArrayList<>(), "", "");

			paciente = service.update(id, paciente);

		}catch(Exception e) {
			paciente = service.findById(id);
		}

		Map<String, Object> listMap = modelAndViewPages.mapPaginaPaciente(id);
		return modelViewAdapter.generateModelView("PaginaPaciente", listMap);
	}
	
	@RequestMapping("/excluir/{ids}")
	public ModelAndView excluirPaciente(@PathVariable String ids) {
		
		String[] id = ids.split("-");
		service.deleteById(Long.parseLong(id[0]));
		
		Recepcionista recepcionista = rService.findById(Long.parseLong(id[1]));

		Map<String, Object> listMap = modelAndViewPages.mapPaginaRecepcionista(recepcionista);
		return modelViewAdapter.generateModelView("PaginaRecepcionista", listMap);
	}
	
	@RequestMapping("/desativar/{ids}")
	public ModelAndView desativarPaciente(@PathVariable String ids) {
		
		String[] id = ids.split("-");
		Paciente paciente = service.findById(Long.parseLong(id[0]));
		paciente.setStatus(false);
		service.insert(paciente);

		Recepcionista recepcionista = rService.findById(Long.parseLong(id[1]));

		Map<String, Object> listMap = modelAndViewPages.mapPaginaRecepcionista(recepcionista);
		return modelViewAdapter.generateModelView("PaginaRecepcionista", listMap);
	}
	
	@RequestMapping("/cancelar/{ids}")
	public ModelAndView cancelarConsulta(@PathVariable String ids) {
		
		String[] id = ids.split("-");
		Consulta consulta = cService.findById(Long.parseLong(id[0]));
		consulta.setStatusConsulta(StatusConsulta.CANCELADA);
		cService.insert(consulta);

		Map<String, Object> listMap = modelAndViewPages.mapPaginaPaciente(Long.parseLong(id[1]));

		return modelViewAdapter.generateModelView("PaginaPaciente", listMap);
	}
	
	@RequestMapping("/ativar/{ids}")
	public ModelAndView ativarPaciente(@PathVariable String ids) {
		String[] id = ids.split("-");
		Paciente paciente = service.findById(Long.parseLong(id[0]));
		paciente.setStatus(true);
		service.insert(paciente);
		
		Recepcionista recepcionista = rService.findById(Long.parseLong(id[1]));

		Map<String, Object> listMap = modelAndViewPages.mapPaginaRecepcionista(recepcionista);
		return modelViewAdapter.generateModelView("PaginaRecepcionista", listMap);
	}
}

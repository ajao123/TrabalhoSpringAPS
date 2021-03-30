package com.allissonjardel.projeto.webcontroller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.allissonjardel.projeto.utils.ModelAndViewPages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.allissonjardel.projeto.entities.enums.StatusConsulta;
import com.allissonjardel.projeto.model.Consulta;
import com.allissonjardel.projeto.model.Medico;
import com.allissonjardel.projeto.model.Paciente;
import com.allissonjardel.projeto.model.Recepcionista;
import com.allissonjardel.projeto.service.ConsultaService;
import com.allissonjardel.projeto.service.DoencaService;
import com.allissonjardel.projeto.service.MedicoService;
import com.allissonjardel.projeto.service.PacienteService;
import com.allissonjardel.projeto.service.RecepcionistaService;
import com.allissonjardel.projeto.utils.ModelViewAdapter;



@Controller
@RequestMapping("/consulta")
public class ConsultaWebController {

	@Autowired
	ConsultaService service;
	
	@Autowired
	MedicoService mService;
	
	@Autowired
	PacienteService pService;
	
	@Autowired
	RecepcionistaService rService;
	
	@Autowired
	DoencaService dService;

	@Autowired
	ModelAndViewPages modelAndViewPages;

	@Autowired
	ModelViewAdapter modelViewAdapter;

	@RequestMapping("/cadastro/{id}")
	public ModelAndView cadastroConsulta(@PathVariable Long id) {
		
		Recepcionista recepcionista = rService.findById(id);
		
		Map<String, Object> listMap = Map.of("listPacientes", pService.getAtivos(), 
												"listaMedicos", mService.getAtivos(), 
												"recepcionista", recepcionista, 
												"dataR", recepcionista.getDataNascimento().format(modelViewAdapter.formatter));

		return modelViewAdapter.generateModelView("CadastroConsulta", listMap);
		
	}
	
	@RequestMapping("/cancelarConsulta/{idc}")
	public ModelAndView cancelarConsulta(@PathVariable Long idc) {
		
		Consulta consulta = service.findById(idc);
		consulta.getObservacoes().clear();
		service.insert(consulta);

		Map<String, Object> listMap = Map.of(
				"medico", consulta.getMedico(),
				"dataR", consulta.getMedico().getDataNascimento().format(modelViewAdapter.formatter),
				"listaConsultasMarcadas",  service.getByMedicoMarcada(consulta.getMedico().getId()),
				"listaConsultasRealizadas", service.getByMedicoRealizada(consulta.getMedico().getId())
		);

		return modelViewAdapter.generateModelView("PaginaMedico", listMap);

	}
	
	@RequestMapping("/salvar")
	public ModelAndView salvarConsulta(String dataC, Long idPaciente, Long idMedico, Long idr) {

		String pagina;
		Map<String, Object> listMap = Map.of();

		Recepcionista recepcionista  = rService.findById(idr);

		try {
			
			Consulta consulta = new Consulta((LocalDateTime.parse(dataC, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))), StatusConsulta.MARCADA, 
					new Paciente(idPaciente), new Medico(idMedico));
			
			
			service.insert(consulta);

			listMap = modelAndViewPages.mapPaginaRecepcionista(recepcionista);
			pagina = "PaginaRecepcionista";

		}catch(Exception e) {

			listMap = Map.of(
					"recepcionista", recepcionista,
					"listaPacientes", pService.getAtivos(),
					"listaMedicos",   mService.getAtivos(),
					"dataR", recepcionista.getDataNascimento().format(modelViewAdapter.formatter)

			);
			pagina = "CadastroConsulta";
		}

		return modelViewAdapter.generateModelView(pagina, listMap);

	}
	
	
	@RequestMapping("/desejaMarcar")
	public ModelAndView desejaMarcar(String data, Long idPaciente, Long idMedico) {

		String pagina;
		Map<String, Object> listMap = null;
		Paciente paciente = pService.findById(idPaciente);
		try {		

			service.insert(new Consulta(
					(LocalDateTime.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))),
					StatusConsulta.INDEFERIDA,
					new Paciente(idPaciente),
					new Medico(idMedico)));

			listMap = Map.of(
					"paciente", paciente,
					"listaConsultasMarcadas", service.getByPacienteMarcada(idPaciente),
					"listaConsultasRealizadas", service.getByPacienteRealizada(idPaciente),
					"listaConsultasIndeferidas", service.getByPacienteIndeferida(idPaciente),
					"dataR", paciente.getDate().format(modelViewAdapter.formatter)
			);
			pagina = "PaginaPaciente";

		}catch(Exception e) {

			listMap = Map.of(
					"paciente", paciente,
					"listaMedicos",   mService.getAtivos(),
					"dataR", paciente.getDate().format(modelViewAdapter.formatter)
			);
			pagina = "CadastrarPedidoConsulta";

		}

		return modelViewAdapter.generateModelView(pagina, listMap);

	}
	
	@RequestMapping("/excluir/{ids}")
	public ModelAndView excluirConsulta(@PathVariable String ids) {
		
		String[] id = ids.split("-");
		service.deleteById(Long.parseLong(id[0]));

		Recepcionista recepcionista  = rService.findById(Long.parseLong(id[1]));

		Map<String, Object> listMap = modelAndViewPages.mapPaginaRecepcionista(recepcionista);

		return modelViewAdapter.generateModelView("PaginaRecepcionista", listMap);
	}

	@RequestMapping("/finalizar/{id}")
	public ModelAndView finalizarConsulta(@PathVariable Long id) {
		
		Consulta consulta = service.findById(id);
		consulta.setStatusConsulta(StatusConsulta.REALIZADA);
		service.insert(consulta);

		Map<String, Object> listMap = Map.of(
				"medico", consulta.getMedico(),
				"dataR",  service.getByMedicoMarcada(consulta.getMedico().getId()),
				"listaConsultasMarcadas",  mService.getAll(),
				"listaConsultasRealizadas",  service.getByMedicoRealizada(consulta.getMedico().getId())
		);

		return modelViewAdapter.generateModelView("PaginaMedico", listMap);
	}
	
	@RequestMapping("/voltar/{idc}")
	public ModelAndView voltar(@PathVariable Long idc) {

		Map<String, Object> listMap = Map.of(
				"consulta", service.findById(idc),
				"listaDoencas",  dService.getDoencasByPaciente(service.findById(idc).getPaciente().getId())
		);

		return modelViewAdapter.generateModelView("RealizarConsulta", listMap);
	}

}

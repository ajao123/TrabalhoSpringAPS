package com.allissonjardel.projeto.webcontroller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.allissonjardel.projeto.model.*;
import com.allissonjardel.projeto.utils.ModelAndViewPages;
import com.allissonjardel.projeto.utils.ModelViewAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.allissonjardel.projeto.entities.enums.StatusConsulta;
import com.allissonjardel.projeto.service.ConsultaService;
import com.allissonjardel.projeto.service.DoencaService;
import com.allissonjardel.projeto.service.MedicoService;
import com.allissonjardel.projeto.service.PacienteService;
import com.allissonjardel.projeto.service.RecepcionistaService;


@Controller
@RequestMapping("/medico")
public class MedicoWebController {

	@Autowired
	MedicoService service;

	@Autowired
	PacienteService pService;

	@Autowired
	ConsultaService cService;

	@Autowired
	RecepcionistaService rService;

	@Autowired
	ModelViewAdapter modelViewAdapter;

	@Autowired
	ModelAndViewPages modelAndViewPages;


	@RequestMapping("/cadastro/{id}")
	public ModelAndView cadastroMedico(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("CadastroMedico");

		Recepcionista recepcionista = rService.findById(id);

		Map<String, Object> listMap = Map.of(
				"dataR", recepcionista.getDataNascimento().format(modelViewAdapter.formatter),
				"recepcionista",  recepcionista
		);

		return modelViewAdapter.generateModelView("CadastroMedico", listMap);

	}

	@RequestMapping("/paginainicial")
	public String paginaMedico() {
		return "PaginaMedico";
	}

	public ModelAndView paginaMedicoModel(Long id) {

		Medico medico = service.findById(id);

		Map<String, Object> listMap =  modelAndViewPages.mapPaginaMedico(medico, id);

		return modelViewAdapter.generateModelView("PaginaMedico", listMap);
	}

	@RequestMapping("/atualizar")
	public ModelAndView atualizarMedico( Long id, String nome, String endereco, String telefone, String dataNascimento,
										 String especialidade, Integer crm) {

		Medico medico;
		try {
			medico =  service.update(id, new Medico(nome, crm, endereco, telefone, especialidade,
					LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy")), "", ""));
		}catch(Exception e) {
			medico = service.findById(id);
		}

		Map<String, Object> listMap =  modelAndViewPages.mapPaginaMedico(medico, id);

		return modelViewAdapter.generateModelView("PaginaMedico", listMap);

	}

	@RequestMapping("/salvar")
	public ModelAndView salvarMedico(String login, String senha, String nome, Integer crm, String telefone, String date,
			String endereco, String especialidade, Long idr) {


		Recepcionista recepcionista = rService.findById(idr);
		Map<String, Object> listMap = null;
		String pagina;

		try {
			pagina = "PaginaRecepcionista";
			Medico medico = new Medico(nome, crm, endereco, telefone, especialidade,
					(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))), login, senha);

			service.insert(medico);

			listMap = modelAndViewPages.mapPaginaRecepcionista(recepcionista);

		} catch (Exception e) {
			pagina = "CadastroMedico";
			listMap = Map.of("recepcionista", recepcionista);
		}



		return modelViewAdapter.generateModelView(pagina, listMap);
	}

	@RequestMapping("/excluir/{ids}")
	public ModelAndView excluirMedico(@PathVariable String ids) {

		String[] id = ids.split("-");
		service.deleteById(Long.parseLong(id[0]));

		Recepcionista recepcionista = rService.findById(Long.parseLong(id[1]));

		Map<String, Object> listMap = modelAndViewPages.mapPaginaRecepcionista(recepcionista);
		return modelViewAdapter.generateModelView("PaginaRecepcionista", listMap);
	}

	@RequestMapping("/desativar/{ids}")
	public ModelAndView desativarMedico(@PathVariable String ids) {

		String[] id = ids.split("-");

		Medico medico = service.findById(Long.parseLong(id[0]));
		medico.setStatus(false);
		service.insert(medico);

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
		
		Medico medico = service.findById(Long.parseLong(id[1]));

		Map<String, Object> listMap = modelAndViewPages.mapPaginaMedico(medico, Long.parseLong(id[1]));

		return modelViewAdapter.generateModelView("PaginaMedico", listMap);
	}

	@RequestMapping("/realizar/{id}")
	public ModelAndView realizarConsulta(@PathVariable Long id) {

		Map<String, Object> listMap = modelAndViewPages.mapRealizarConsulta(cService.findById(id));

		return modelViewAdapter.generateModelView("RealizarConsulta", listMap);
	}

	@RequestMapping("/adicionarObs")
	public ModelAndView adicionarObservacao(Long idc, String observacao) {

		Consulta consulta = cService.findById(idc);
		consulta.getObservacoes().add(observacao);
		consulta = cService.insert(consulta);

		Map<String, Object> listMap = modelAndViewPages.mapRealizarConsulta(consulta);

		return modelViewAdapter.generateModelView("RealizarConsulta", listMap);
	}

	@RequestMapping("/removerObs/{obs}")
	public ModelAndView removerObservacao(@PathVariable String obs) {

		String[] observacoes = obs.split("_");

		Consulta consulta = cService.findById(Long.parseLong(observacoes[1]));
		consulta.getObservacoes().remove(observacoes[0]);
		consulta = cService.insert(consulta);

		Map<String, Object> listMap = modelAndViewPages.mapRealizarConsulta(consulta);

		return modelViewAdapter.generateModelView("RealizarConsulta", listMap);
	}

	@RequestMapping("/adicionarObsPaciente")
	public ModelAndView adicionarObservacaoPaciente(Long idc, String observacaop) {

		Consulta consulta = cService.findById(idc);

		Paciente paciente = pService.findById(consulta.getPaciente().getId());
		paciente.getObservacoes().add(observacaop);
		pService.insert(paciente);

		Map<String, Object> listMap = modelAndViewPages.mapRealizarConsulta(consulta);

		return modelViewAdapter.generateModelView("RealizarConsulta", listMap);
	}

	@RequestMapping("/removerObsPaciente/{obs}")
	public ModelAndView removerObservacaoPaciente(@PathVariable String obs) {

		String[] observacoes = obs.split("_");

		Consulta consulta = cService.findById(Long.parseLong(observacoes[1]));
		Paciente paciente = pService.findById(consulta.getPaciente().getId());
		paciente.getObservacoes().remove(observacoes[0]);
		pService.insert(paciente);

		Map<String, Object> listMap = modelAndViewPages.mapRealizarConsulta(consulta);

		return modelViewAdapter.generateModelView("RealizarConsulta", listMap);
	}

	@RequestMapping("/adicionarIHPaciente")
	public ModelAndView adicionarIHPaciente(Long idc, String ihPaciente) {

		Consulta consulta = cService.findById(idc);

		Paciente paciente = pService.findById(consulta.getPaciente().getId());
		paciente.getHistoricoPaciente().add(ihPaciente);
		pService.insert(paciente);

		Map<String, Object> listMap = modelAndViewPages.mapRealizarConsulta(consulta);

		return modelViewAdapter.generateModelView("RealizarConsulta", listMap);
	}

	@RequestMapping("/removerIHPaciente/{obs}")
	public ModelAndView removerIHPaciente(@PathVariable String obs) {

		String[] observacoes = obs.split("_");

		Consulta consulta = cService.findById(Long.parseLong(observacoes[1]));
		Paciente paciente = pService.findById(consulta.getPaciente().getId());
		paciente.getHistoricoPaciente().remove(observacoes[0]);
		pService.insert(paciente);

		Map<String, Object> listMap = modelAndViewPages.mapRealizarConsulta(consulta);


		return modelViewAdapter.generateModelView("RealizarConsulta", listMap);
	}

	@RequestMapping("/adicionarDoenca/{idConsulta}")
	public ModelAndView adicionarDoenca(@PathVariable Long idConsulta) {

		Consulta consulta = cService.findById(idConsulta);

		ModelAndView mv = new ModelAndView("CadastroDoenca");
		mv.addObject("consulta", consulta);

		Map<String, Object> listMap = Map.of(
				"consulta", consulta
		);
		return modelViewAdapter.generateModelView("CadastroDoenca", listMap);
	}

	@RequestMapping("/ativar/{ids}")
	public ModelAndView ativarMedico(@PathVariable String ids) {

		String[] id = ids.split("-");

		Medico medico = service.findById(Long.parseLong(id[0]));
		medico.setStatus(true);
		service.insert(medico);
		
		Recepcionista recepcionista = rService.findById(Long.parseLong(id[1]));

		Map<String, Object> listMap = modelAndViewPages.mapPaginaRecepcionista(recepcionista);
		return modelViewAdapter.generateModelView("PaginaRecepcionista", listMap);

	}




}

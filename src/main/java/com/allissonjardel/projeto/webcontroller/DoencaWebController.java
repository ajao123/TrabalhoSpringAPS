package com.allissonjardel.projeto.webcontroller;

import com.allissonjardel.projeto.model.Recepcionista;
import com.allissonjardel.projeto.utils.ModelAndViewPages;
import com.allissonjardel.projeto.utils.ModelViewAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.allissonjardel.projeto.model.Doenca;
import com.allissonjardel.projeto.model.Paciente;
import com.allissonjardel.projeto.service.ConsultaService;
import com.allissonjardel.projeto.service.DoencaService;
import com.allissonjardel.projeto.service.MedicoService;
import com.allissonjardel.projeto.service.PacienteService;
import com.allissonjardel.projeto.service.RecepcionistaService;

import java.util.Map;


@Controller
@RequestMapping("/doenca")
public class DoencaWebController {

	@Autowired
	ConsultaService cService;
	
	@Autowired
	MedicoService mService;
	
	@Autowired
	PacienteService pService;
	
	@Autowired
	RecepcionistaService rService;
	
	@Autowired
	DoencaService dService;

	@Autowired
	ModelViewAdapter modelViewAdapter;

	@Autowired
	ModelAndViewPages modelAndViewPages;

	@RequestMapping("/salvar")
	public ModelAndView SalvarDoenca(Long idc, String nomeDoenca, Long idPaciente, String estagio) {
		
		
		Paciente p = pService.findById(idPaciente);
		
		Doenca doenca = new Doenca(null, nomeDoenca, null, null, estagio, p);
		dService.insert(doenca);

		Map<String, Object> listMap = Map.of(
				"consulta", cService.findById(idc),
				"listaDoencas",  dService.getDoencasByPaciente(idPaciente)
		);

		return modelViewAdapter.generateModelView("RealizarConsulta", listMap);
	}
	
	
	@RequestMapping("/adicionarSintoma/{ids}")
	public ModelAndView adicionarSintomaDoenca(@PathVariable String ids) {
		
		String[] id = ids.split("_");

		Map<String, Object> listMap =modelAndViewPages. mapAdicionar(Long.parseLong(id[0]),  dService.findById(Long.parseLong(id[1])));

		return modelViewAdapter.generateModelView("AdicionarSintoma", listMap);
	}
	
	@RequestMapping("/adicionarObservacao/{ids}")
	public ModelAndView adicionarObservacaoDoenca(@PathVariable String ids) {
		
		String[] id = ids.split("_");
		Map<String, Object> listMap = modelAndViewPages.mapAdicionar(Long.parseLong(id[0]), dService.findById(Long.parseLong(id[1])));

		return modelViewAdapter.generateModelView("AdicionarObservacao", listMap);
	}
	
	@RequestMapping("/adicionarSint")
	public ModelAndView adicionarSintoma(Long idc, Long idd, String sintoma) {
		
		Doenca doenca = dService.findById(idd);
		doenca.getSintomas().add(sintoma);
		
		doenca = dService.insert(doenca);

		Map<String, Object> listMap = modelAndViewPages.mapAdicionar(idc, doenca);

		return modelViewAdapter.generateModelView("AdicionarSintoma", listMap);
	}
	
	@RequestMapping("/adicionarObs")
	public ModelAndView adicionarObservacao(Long idc, Long idd, String observacao) {
		
		Doenca doenca = dService.findById(idd);
		doenca.getObservacoes().add(observacao);
		
		doenca = dService.insert(doenca);

		Map<String, Object> listMap = modelAndViewPages.mapAdicionar(idc, doenca);

		return modelViewAdapter.generateModelView("AdicionarObservacao", listMap);
	}





}

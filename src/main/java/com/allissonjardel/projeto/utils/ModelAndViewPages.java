package com.allissonjardel.projeto.utils;

import com.allissonjardel.projeto.model.Consulta;
import com.allissonjardel.projeto.model.Doenca;
import com.allissonjardel.projeto.model.Medico;
import com.allissonjardel.projeto.model.Recepcionista;
import com.allissonjardel.projeto.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class ModelAndViewPages {

    @Autowired
    AdministradorService administradorService;

    @Autowired
    RecepcionistaService recepcionistaService;

    @Autowired
    MedicoService medicoService;

    @Autowired
    ConsultaService consultaService;

    @Autowired
    DoencaService doencaService;

    @Autowired
    PacienteService pacienteService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Map<String, Object> mapPaginaRecepcionista(Recepcionista recepcionista){

        return Map.of(
                "recepcionista", recepcionista,
                "listaPacientes",  pacienteService.getAll(),
                "listaMedicos",  medicoService.getAll(),
                "dataR",  recepcionista.getDataNascimento().format(formatter),
                "listaConsultasMarcadas",  consultaService.getByMarcada(),
                "listaConsultasRealizadas",  consultaService.getByRealizada(),
                "listaConsultasCanceladas",  consultaService.getByCancelada(),
                "listaConsultasIndeferidas",  consultaService.getByIndeferida()
        );
    }

    public Map<String, Object> mapAdicionar(Long idConsulta, Doenca doenca){
        return Map.of(
                "consulta", consultaService.findById(idConsulta),
                "doenca",  doenca
        );
    }

    public Map<String, Object> mapPaginaMedico(Medico medico, Long id){
        return Map.of(
                "medico", medico,
                "dataR",  medico.getDataNascimento().format(formatter),
                "listaConsultasMarcadas",  consultaService.getByMedicoMarcada(id),
                "listaConsultasRealizadas",  consultaService.getByMedicoRealizada(id)
        );
    }

    public Map<String, Object> mapRealizarConsulta(Consulta consulta){
        return Map.of(
                "consulta", consulta,
                "listaDoencas",  doencaService.getDoencasByPaciente(consulta.getPaciente().getId()));
    }


    public Map<String, Object> mapPaginaPaciente(Long id){
        return Map.of(
                "paciente", pacienteService.findById(id),
                "dataR", pacienteService.findById(id).getDate().format(formatter),
                "listaConsultasMarcadas", consultaService.getByPacienteMarcada(id),
                "listaConsultasRealizadas", consultaService.getByPacienteRealizada(id),
                "listaDoencas", doencaService.getDoencasByPaciente(id)
        );
    }

    public ModelAndView paginaAdministrador(Long ida){
        ModelAndView mv = new ModelAndView("PaginaAdministrador");
        mv.addObject("administrador", administradorService.findById(ida));
        mv.addObject("listaRecepcionistas", recepcionistaService.getAll());
        return mv;
    }

}

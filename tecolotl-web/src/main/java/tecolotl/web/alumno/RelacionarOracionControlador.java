package tecolotl.web.alumno;

import tecolotl.alumno.modelo.relacionar_oraciones.TareaRelacionarOracionModelo;
import tecolotl.alumno.sesion.RelacionarOracionSesionBean;
import tecolotl.alumno.sesion.RelacionarSesionBean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.logging.Logger;

@RequestScoped
@Named
public class RelacionarOracionControlador {

    @Inject
    private Logger logger;

    @Inject
    private RelacionarOracionSesionBean relacionarOracionSesionBean;

    @Inject
    private AlumnoControlador alumnoControlador;

    private String idRespuesta;



    private List<TareaRelacionarOracionModelo> tareaRelacionarOracionModeloLista;

    @PostConstruct
    public void init() {
        tareaRelacionarOracionModeloLista = relacionarOracionSesionBean.busca(alumnoControlador.getTareaModelo().getId());
    }

    public List<TareaRelacionarOracionModelo> getTareaRelacionarOracionModeloLista() {
        return tareaRelacionarOracionModeloLista;
    }

    public void setTareaRelacionarOracionModeloLista(List<TareaRelacionarOracionModelo> tareaRelacionarOracionModeloLista) {
        this.tareaRelacionarOracionModeloLista = tareaRelacionarOracionModeloLista;
    }

    public String getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(String idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public void llenarTareaModelo(String idRespuesta){
        logger.info(idRespuesta.toString());
        //alumnoControlador.
        //alumnoControlador.setTareaModeloLista();
    }
}


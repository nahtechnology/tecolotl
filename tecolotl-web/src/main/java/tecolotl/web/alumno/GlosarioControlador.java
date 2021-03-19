package tecolotl.web.alumno;

import tecolotl.alumno.modelo.TareaActividadModelo;
import tecolotl.alumno.modelo.glosario.GlosarioModelo;
import tecolotl.alumno.sesion.GlosarioSesionBean;
import tecolotl.alumno.sesion.TareaSesionBean;
import tecolotl.nucleo.herramienta.AlmacenamientoEnum;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RequestScoped
@Named
public class GlosarioControlador {

    @Inject
    private GlosarioSesionBean glosarioSesionBean;

    @Inject
    private AlumnoControlador alumnoControlador;

    @Inject
    private TareaSesionBean tareaSesionBean;

    @Inject
    private Logger logger;


    private List<GlosarioModelo> glosarioModeloLista;
    private String carpeta;
    private String idTarea;
    private TareaActividadModelo tareaActividadModelo;

    public String seleccion(){
        logger.info("Id Tarea: ".concat(idTarea));
        tareaActividadModelo = tareaSesionBean.buscaActividad(UUID.fromString(idTarea), alumnoControlador.getAlumnoModelo().getId());
        glosarioModeloLista = glosarioSesionBean.busca(tareaActividadModelo.getIdActividad());
        carpeta = AlmacenamientoEnum.IMAGENES_GLOSARIO.name().toLowerCase();
        return "glosario";
    }

    public void validarParametro(){
        try{
            FacesContext facesContext = FacesContext.getCurrentInstance();
            if (!facesContext.isPostback() && facesContext.isValidationFailed()) {
                facesContext.getExternalContext().redirect("activities.xhtml");
            }
        }catch(IOException ioe){
            logger.severe("Ocurrio un error: ".concat(ioe.getMessage()));
        }
    }

    public List<GlosarioModelo> getGlosarioModeloLista() {
        return glosarioModeloLista;
    }

    public void setGlosarioModeloLista(List<GlosarioModelo> glosarioModeloLista) {
        this.glosarioModeloLista = glosarioModeloLista;
    }

    public String getCarpeta() {
        return carpeta;
    }

    public void setCarpeta(String carpeta) {
        this.carpeta = carpeta;
    }

    public String getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(String idTarea) {
        this.idTarea = idTarea;
    }

    public void setTareaActividadModelo(TareaActividadModelo tareaActividadModelo) {
        this.tareaActividadModelo = tareaActividadModelo;
    }
}

package tecolotl.alumno.sesion;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import tecolotl.alumno.Modelo.AlumnoModelo;
import tecolotl.alumno.Modelo.TareaModelo;
import tecolotl.alumno.entidad.*;
import tecolotl.nucleo.modelo.PersonaModelo;
import tecolotl.nucleo.persistencia.entidad.CatalagoEntidad;
import tecolotl.nucleo.persistencia.entidad.PersonaEntidad;
import tecolotl.nucleo.sesion.PersonaSesionBean;

import javax.inject.Inject;
import java.util.List;

@RunWith(Arquillian.class)
public class TareaSesionBeanTest {

    @Deployment
    public static Archive<?> createDeployment(){
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(PersonaModelo.class.getPackage())
                .addPackage(AlumnoModelo.class.getPackage())
                .addPackage(TareaModelo.class.getPackage())
                .addPackage(PersonaEntidad.class.getPackage())
                .addClasses(PersonaEntidad.class, CatalagoEntidad.class, GradoEscolarEntidad.class,
                        AlumnoEntidad.class, TareaEntidad.class, TipoEstudianteEntidad.class,
                        NivelLenguajeEntidad.class, PersonaSesionBean.class, AlumnoSesionBean.class,
                        TareaSesionBean.class, GradoEscolarSesionBean.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    @Inject
    private TareaSesionBean tareaSesionBean;

    @Test
    public void busca(){
        List<TareaModelo> tareaModeloLista = tareaSesionBean.busca();
        Assert.assertNotNull(tareaModeloLista);
        Assert.assertFalse(tareaModeloLista.isEmpty());
        for (TareaModelo tareaModelo : tareaModeloLista){
            Assert.assertNotNull(tareaModelo);
            Assert.assertNotNull(tareaModelo.getId());
            Assert.assertNotNull(tareaModelo.getAlumnoEntidad());
            Assert.assertNotNull(tareaModelo.getAsignacion());
        }

    }
}

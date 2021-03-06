package tecolotl.alumno.sesion;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import tecolotl.alumno.entidad.AlumnoEntidad;
import tecolotl.alumno.entidad.ControlSesionEntidad;
import tecolotl.alumno.entidad.glosario.TareaGlosarioActividadEntidad;
import tecolotl.alumno.entidad.mapamental.TareaMapaMentalActividadEntidad;
import tecolotl.alumno.entidad.relacionar.TareaRelacionarActividadEntidad;
import tecolotl.nucleo.persistencia.entidad.CatalagoEntidad;
import tecolotl.nucleo.persistencia.entidad.PersonaEntidad;
import tecolotl.nucleo.persistencia.entidad.TipoRegistroEntidad;

import javax.inject.Inject;

import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class ControlSesionSesionBeanTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addAsResource("META-INF/persistence.xml")
                .addClass(ControlSesionSesionBean.class).addClass(ControlSesionEntidad.class)
                .addClass(tecolotl.nucleo.persistencia.entidad.ControlSesionEntidad.class)
                .addClass(TipoRegistroEntidad.class).addClass(CatalagoEntidad.class)
                .addPackage(AlumnoEntidad.class.getPackage()).addPackage(PersonaEntidad.class.getPackage())
                .addPackage(TareaGlosarioActividadEntidad.class.getPackage())
                .addPackage(TareaMapaMentalActividadEntidad.class.getPackage())
                .addPackage(TareaRelacionarActividadEntidad.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private ControlSesionSesionBean controlSesionSesionBean;

    @Test
    public void inicioSesion() {
        controlSesionSesionBean.inserta(UUID.fromString("0cbaa96c-ba77-408d-b046-56e0fd1ffe56"), (short)1);
    }

    @Test
    public void cierreSesion() {
        controlSesionSesionBean.inserta(UUID.fromString("0cbaa96c-ba77-408d-b046-56e0fd1ffe56"), (short)2);
    }

}

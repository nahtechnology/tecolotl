package tecolotl.administracion.persistencia.entidad;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.List;

@RunWith(Arquillian.class)
public class ColoniaEntidadTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClass(EstadoEntidad.class)
                .addPackage(ColoniaEntidad.class.getPackage())
                .addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void buscaCodigoPostal() {
        TypedQuery<ColoniaEntidad> typedQuery = entityManager.createNamedQuery("ColoniaEntidad.buscaCodigoPostal", ColoniaEntidad.class);
        typedQuery.setParameter("codigoPostal", "72470");
        List<ColoniaEntidad> colonialista = typedQuery.getResultList();
        Assert.assertNotNull(colonialista);
        Assert.assertFalse(colonialista.isEmpty());
        for (ColoniaEntidad coloniaEntidad :
                colonialista) {
            Assert.assertNotNull(coloniaEntidad.getId());
            Assert.assertNotNull(coloniaEntidad.getNombre());
            Assert.assertNotNull(coloniaEntidad.getCodigoPostal());
            MunicipioEntidad municipioEntidad = coloniaEntidad.getMunicipio();
            Assert.assertNotNull(municipioEntidad);
            Assert.assertNotNull(municipioEntidad.getId());
            Assert.assertNotNull(municipioEntidad.getNombre());
        }
    }

}

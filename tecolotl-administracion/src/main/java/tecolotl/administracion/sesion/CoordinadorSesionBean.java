package tecolotl.administracion.sesion;

import tecolotl.administracion.modelo.coordinador.CoordinadorModelo;
import tecolotl.administracion.modelo.coordinador.CoordinadorMotivoBloqueoModelo;
import tecolotl.administracion.persistencia.entidad.CoordinadorEntidad;
import tecolotl.administracion.persistencia.entidad.CoordinadorEntidadPK;
import tecolotl.administracion.persistencia.entidad.CoordinadorMotivoBloqueoEntidad;
import tecolotl.administracion.persistencia.entidad.EscuelaEntidad;
import tecolotl.administracion.validacion.escuela.CoordinadorLlavePrimaria;
import tecolotl.administracion.validacion.escuela.CoordinadorNuevoValidacion;
import tecolotl.nucleo.herramienta.ValidadorSessionBean;
import tecolotl.nucleo.validacion.PersonaNuevaValidacion;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Coordinador de una escuela, es el encargado de administrar los alumnos y profesores de una escuela.
 * @since 0.1
 * @author Antonio Francisco Alonso Valerdi
 */
@Stateless
public class CoordinadorSesionBean {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Logger logger;

    @Inject
    private ValidadorSessionBean validadorSessionBean;

    /**
     * Busca todos los coordinadores de una escuela, sin el motivo de bloqueo de dicho coordinador.
     * @param claveCentroTrabajo Clave centro de trabajo de la escuela.
     * @return Colección de {@link CoordinadorModelo}, lista vacia en caso de no tener alumnos.
     */
    public List<CoordinadorModelo> busca(@NotNull @Size(min = 10, max = 14) String claveCentroTrabajo) {
        return entityManager.createNamedQuery("CoordinadorEntidad.buscaEscuela", CoordinadorEntidad.class)
                .setParameter("claveCentroTrabajo",claveCentroTrabajo).getResultList().stream()
                .map(CoordinadorModelo::new).collect(Collectors.toList());
    }

    public CoordinadorModelo busca(@NotNull @Size(min = 10, max = 14) String claveCentroTrabajo,
                                   @NotNull Short contador) {
        CoordinadorEntidad coordinadorEntidad = entityManager.createNamedQuery(
                "CoordinadorEntidad.buscaDetalle", CoordinadorEntidad.class)
                .setParameter("coordinadorEntidadPK", new CoordinadorEntidadPK(claveCentroTrabajo, contador))
                .getSingleResult();
        CoordinadorModelo coordinadorModelo = new CoordinadorModelo(coordinadorEntidad);
        coordinadorModelo.getCoordinadorMotivoBloqueoModelo().setValor(
                coordinadorEntidad.getCoordinadorMotivoBloqueoEntidad().getValor());
        return coordinadorModelo;
    }

    /**
     * Cambia es estatus de una coordinador.
     * @param coordinadorModelo Datos del coordinador a cambiar el estatus.
     * @param motivoBloqueo Estatus del coordinador.
     */
    public void estatus(@NotNull CoordinadorModelo coordinadorModelo,
                        @NotNull Short motivoBloqueo) {
        logger.fine(coordinadorModelo.toString());
        logger.fine(motivoBloqueo.toString());
        validadorSessionBean.validacion(coordinadorModelo, CoordinadorLlavePrimaria.class);
        CoordinadorEntidad coordinadorEntidad = entityManager.find(CoordinadorEntidad.class, generaLlavePrimaria(coordinadorModelo));
        CoordinadorMotivoBloqueoEntidad coordinadorMotivoBloqueoEntidad =
                new CoordinadorMotivoBloqueoEntidad(motivoBloqueo);
        coordinadorEntidad.setCoordinadorMotivoBloqueoEntidad(coordinadorMotivoBloqueoEntidad);
    }

    /**
     * Agrega un coordinador a una escuela, por defecto todos los nuevos coordinadores se encuentran actuvos.
     * @param coordinadorModelo Datos del coordinador a ser insertados en la base de datos.
     */
    public void agreaga(@NotNull CoordinadorModelo coordinadorModelo) {
        validadorSessionBean.validacion(coordinadorModelo, CoordinadorNuevoValidacion.class, PersonaNuevaValidacion.class);
        CoordinadorEntidad coordinadorEntidad = new CoordinadorEntidad(generaLlavePrimaria(coordinadorModelo));
        coordinadorEntidad.setCorreoEletronico(coordinadorModelo.getCorreoEletronico());
        coordinadorEntidad.setApodo(coordinadorModelo.getApodo());
        coordinadorEntidad.setNombre(coordinadorModelo.getNombre());
        coordinadorEntidad.setApellidoPaterno(coordinadorModelo.getApellidoPaterno());
        coordinadorEntidad.setApellidoMaterno(coordinadorModelo.getApellidoMaterno());
        coordinadorEntidad.setContrasenia(coordinadorModelo.getContrasenia());
        entityManager.persist(coordinadorEntidad);
    }

    /**
     * Actualiza los datos de un coordinador
     * @param coordinadorModelo
     */
    public void actualiza(@NotNull @Valid CoordinadorModelo coordinadorModelo) {
        logger.fine(coordinadorModelo.toString());
        /*CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<CoordinadorEntidad> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(CoordinadorEntidad.class);
        Root<CoordinadorEntidad> root = criteriaUpdate.from(CoordinadorEntidad.class);
        Predicate predicate = criteriaBuilder.equal(root.get("coordinadorEntidadPK"), generaLlavePrimaria(coordinadorModelo));
        criteriaUpdate.set(root.get("correoEletronico"), coordinadorModelo.getCorreoEletronico())
                .set(root.get("nombre"), coordinadorModelo.getNombre())
                .set(root.get("apellidoPaterno"), coordinadorModelo.getApellidoPaterno())
                .set(root.get("apellidoMaterno"), coordinadorModelo.getApellidoMaterno())
                .set(root.get("apodo"), coordinadorModelo.getApodo())
                .set(root.get("contrasenia"), coordinadorModelo.getContrasenia())
                .where(predicate);
        return entityManager.createQuery(criteriaUpdate).executeUpdate();*/
        CoordinadorEntidad coordinadorEntidad = entityManager.find(CoordinadorEntidad.class, generaLlavePrimaria(coordinadorModelo));
        coordinadorEntidad.setCorreoEletronico(coordinadorModelo.getCorreoEletronico());
        coordinadorEntidad.setApodo(coordinadorModelo.getApodo());
        coordinadorEntidad.setNombre(coordinadorModelo.getNombre());
        coordinadorEntidad.setApellidoPaterno(coordinadorModelo.getApellidoPaterno());
        coordinadorEntidad.setApellidoMaterno(coordinadorModelo.getApellidoMaterno());
        coordinadorEntidad.setContrasenia(coordinadorModelo.getContrasenia());
    }

    public void elimina(@NotNull CoordinadorModelo coordinadorModelo) {
        logger.fine(coordinadorModelo.toString());
        entityManager.remove(entityManager.find(CoordinadorEntidad.class, generaLlavePrimaria(coordinadorModelo)));
    }

    private CoordinadorEntidadPK generaLlavePrimaria(CoordinadorModelo coordinadorModelo) {
        CoordinadorEntidadPK coordinadorEntidadPK = new CoordinadorEntidadPK();
        coordinadorEntidadPK.setEscuelaEntidad(new EscuelaEntidad(coordinadorModelo.getClaveCentroTrabajo()));
        coordinadorEntidadPK.setContador(coordinadorModelo.getContador());
        return coordinadorEntidadPK;
    }

}

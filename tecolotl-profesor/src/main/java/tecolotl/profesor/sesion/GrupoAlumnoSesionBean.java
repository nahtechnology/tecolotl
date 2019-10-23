package tecolotl.profesor.sesion;

import tecolotl.alumno.entidad.AlumnoEntidad;
import tecolotl.alumno.entidad.NivelLenguajeEntidad;
import tecolotl.alumno.modelo.AlumnoModelo;
import tecolotl.profesor.entidad.*;
import tecolotl.profesor.modelo.AlumnoTareasNivelModelo;
import tecolotl.profesor.modelo.GrupoAlumnoModelo;
import tecolotl.profesor.modelo.TareaAlumnoGrupoModelo;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Stateless
public class GrupoAlumnoSesionBean {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Logger logger;

    /**
     * Inserta un nuevo GrupoAlumno.
     * @param grupoAlumnoModelo dato para poder insertar un nuecvo GrupoAlumno.
     */
    public void inserta(@NotNull GrupoAlumnoModelo grupoAlumnoModelo){
        logger.fine(grupoAlumnoModelo.toString());
        GrupoAlumnoEntidadPK grupoAlumnoEntidadPK = new GrupoAlumnoEntidadPK();
        grupoAlumnoEntidadPK.setAlumnoEntidad(new AlumnoEntidad(grupoAlumnoModelo.getIdAlumno()));

        GrupoAlumnoEntidad grupoAlumnoEntidad = new GrupoAlumnoEntidad();
        grupoAlumnoEntidad.setGrupoAlumnoEntidadPK(grupoAlumnoEntidadPK);
        entityManager.persist(grupoAlumnoEntidad);
    }

    /**
     * Busca un GrupoAlumno.
     * @return una Lista de GrupoAlumno.
     */
    public List<GrupoAlumnoModelo> busca(){
        TypedQuery<GrupoAlumnoEntidad> typedQuery = entityManager.createNamedQuery("GrupoAlumnoEntidad.busca", GrupoAlumnoEntidad.class);
        List<GrupoAlumnoEntidad> grupoAlumnoEntidadLista = typedQuery.getResultList();
        logger.finer("Numero de profesores encontrados: ".concat(String.valueOf(grupoAlumnoEntidadLista.size())));
        return grupoAlumnoEntidadLista.stream().map(GrupoAlumnoModelo::new).collect(Collectors.toList());
    }

    /**
     * Busca todos las tareas asigandas y realizadas de los alumnos asignados de un grupo
     * @param idGrupo Identificador del grupo
     * @return Colección de {@link TareaAlumnoGrupoModelo}
     */
    public List<TareaAlumnoGrupoModelo> busca(@NotNull Integer idGrupo) {
        Query query = entityManager.createNativeQuery("SELECT * FROM profesor.tareas_grupo(?)", TareaAlumnoGrupoEntidad.class);
        query.setParameter(1, idGrupo);
        return ((List<TareaAlumnoGrupoEntidad>)query.getResultList()).stream().map(TareaAlumnoGrupoModelo::new).collect(Collectors.toList());
    }

    /**
     * Busca todos los alumnos de un grupo y muestra el avance
     * @param idGrupoLista catalogo de grupos
     * @return Colección de alumno con sus niveles
     */
    public List<AlumnoTareasNivelModelo> buscaAlumnoNivel(@NotNull @Size(min = 1) List<Integer> idGrupoLista) {
        logger.fine(idGrupoLista.toString());
        Query query = entityManager.createNativeQuery("SELECT * FROM profesor.tarea_nivel_lenguaje(CAST (? AS INTEGER[]))", AlumnoTareasNivelEntidad.class);
        final StringJoiner stringJoiner = new StringJoiner(",", "{", "}");
        idGrupoLista.forEach(grupo -> stringJoiner.add(String.valueOf(grupo.intValue())));
        query.setParameter(1,stringJoiner.toString());
        List<AlumnoTareasNivelModelo> alumnoTareasNivelModeloLista = new ArrayList<>();
        for (AlumnoTareasNivelEntidad alumnoTareasNivelEntidad : (List<AlumnoTareasNivelEntidad>)query.getResultList()) {
            AlumnoTareasNivelModelo alumnoTareasNivelModelo = new AlumnoTareasNivelModelo(alumnoTareasNivelEntidad);
            alumnoTareasNivelModeloLista.add(alumnoTareasNivelModelo);
        }
        return alumnoTareasNivelModeloLista;
    }

    public List<AlumnoModelo> buscaAlumno(@NotNull Integer idGrupo) {
        TypedQuery<AlumnoEntidad> typedQuery = entityManager.createNamedQuery("GrupoAlumnoEntidad.buscaAlumnosPorGrupo", AlumnoEntidad.class);
        typedQuery.setParameter("idGrupo", idGrupo);
        List<AlumnoEntidad> alumnoEntidadLista = typedQuery.getResultList();
        List<AlumnoModelo> alumnoModeloLista = new ArrayList<>(alumnoEntidadLista.size());
        for (AlumnoEntidad alumnoEntidad : alumnoEntidadLista) {
            AlumnoModelo alumnoModelo = new AlumnoModelo();
            alumnoModelo.setId(alumnoEntidad.getId());
            alumnoModelo.setNombre(alumnoEntidad.getNombre());
            alumnoModelo.setApellidoPaterno(alumnoEntidad.getApellidoPaterno());
            alumnoModelo.setApellidoMaterno(alumnoEntidad.getApellidoMaterno());
            alumnoModeloLista.add(alumnoModelo);
        }
        return alumnoModeloLista;
    }

    /**
     * Elimina un GrupoAlumno.
     * @param grupoAlumnoModelo dato para eliminar el GrupoAlumno.
     * @return numero de elementos modificados, 0 en caso de no existir.
     */
    public Integer elimina(@NotNull GrupoAlumnoModelo grupoAlumnoModelo){
        logger.fine(grupoAlumnoModelo.toString());
        GrupoAlumnoEntidadPK grupoAlumnoEntidadPK = new GrupoAlumnoEntidadPK();
        grupoAlumnoEntidadPK.setAlumnoEntidad(new AlumnoEntidad(grupoAlumnoModelo.getIdAlumno()));
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete criteriaDelete = criteriaBuilder.createCriteriaDelete(GrupoAlumnoEntidad.class);
        Root<GrupoAlumnoEntidad> root = criteriaDelete.from(GrupoAlumnoEntidad.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get("grupoAlumnoEntidadPK"), grupoAlumnoEntidadPK));
        return entityManager.createQuery(criteriaDelete).executeUpdate();
    }

}

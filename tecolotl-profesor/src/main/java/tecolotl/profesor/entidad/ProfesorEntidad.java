package tecolotl.profesor.entidad;

import tecolotl.administracion.persistencia.entidad.EscuelaEntidad;
import tecolotl.nucleo.persistencia.entidad.PersonaEntidad;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.StringJoiner;

@Entity
@Table(schema = "profesor", name = "profesor", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"apodo"})
})
@SequenceGenerator(name = "secuencia", schema = "profesor", sequenceName = "profesor_seq")
@NamedQueries({
        @NamedQuery(name = "ProfesorEntidad.busca", query = "SELECT p FROM ProfesorEntidad p"),
        @NamedQuery(
            name = "ProfesorEntidad.buscaIdEscuela",
            query = "SELECT p FROM ProfesorEntidad p WHERE p.escuelaEntidad.claveCentroTrabajo = :claveCentroTrabajo"
        ),
        @NamedQuery(
            name = "ProfesorEntidad.buscaTotalGrupos" ,
            query = "SELECT COUNT(g.profesorEntidad.id), p.id FROM ProfesorEntidad p LEFT JOIN p.grupoEntidadLista g " +
                    "WHERE p.escuelaEntidad.claveCentroTrabajo = :claveCentroTrabajo GROUP BY g.profesorEntidad.id, p.id"
        ),
        @NamedQuery(
                name = "ProfesorEntidad.totalAlumnosPorEscuela",
                query = "SELECT COUNT(g.profesorEntidad.id) FROM ProfesorEntidad p LEFT JOIN p.grupoEntidadLista g " +
                    "LEFT JOIN g.grupoAlumnoEntidadLista ga WHERE p.escuelaEntidad.claveCentroTrabajo = :claveCentroTrabajo GROUP BY g.profesorEntidad.id"
        ),
        @NamedQuery(
                name = "ProfesorEntidad.totalProfesores",
                query = "SELECT COUNT(p.id) FROM ProfesorEntidad p WHERE p.escuelaEntidad.claveCentroTrabajo = :claveCentroTrabajo"
        )
})
public class ProfesorEntidad extends PersonaEntidad {

    private Integer id;
    private String correoEletronico;
    private EscuelaEntidad escuelaEntidad;
    private List<GrupoEntidad> grupoEntidadLista;

    public ProfesorEntidad() {
    }

    public ProfesorEntidad(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(generator = "secuencia", strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_escuela")
    public EscuelaEntidad getEscuelaEntidad() {
        return escuelaEntidad;
    }

    public void setEscuelaEntidad(EscuelaEntidad escuelaEntidad) {
        this.escuelaEntidad = escuelaEntidad;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profesorEntidad")
    public List<GrupoEntidad> getGrupoEntidadLista() {
        return grupoEntidadLista;
    }

    public void setGrupoEntidadLista(List<GrupoEntidad> grupoEntidadLista) {
        this.grupoEntidadLista = grupoEntidadLista;
    }

    @Basic
    @Column(name = "correo_electronico")
    @NotNull
    @Size(max = 100)
    public String getCorreoEletronico() {
        return correoEletronico;
    }

    public void setCorreoEletronico(String correoEletronico) {
        this.correoEletronico = correoEletronico;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ProfesorEntidad.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("correoEletronico='" + correoEletronico + "'")
                .add("escuelaEntidad=" + escuelaEntidad)
                .add("grupoEntidadLista=" + grupoEntidadLista)
                .toString();
    }
}

package tecolotl.profesor.entidad;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "grupo_alumno_tarea", schema = "profesor")
@NamedQueries({
        @NamedQuery(name = "GrupoAlumnoTareaEntidad.busca", query = "SELECT gat FROM GrupoAlumnoTareaEntidad gat")
})
public class GrupoAlumnoTareaEntidad {
    private GrupoAlumnoTareaEntidadPK grupoAlumnoTareaEntidadPK;
    private Timestamp asignacion;

    @EmbeddedId
    public GrupoAlumnoTareaEntidadPK getGrupoAlumnoTareaEntidadPK() {
        return grupoAlumnoTareaEntidadPK;
    }

    public void setGrupoAlumnoTareaEntidadPK(GrupoAlumnoTareaEntidadPK grupoAlumnoTareaEntidadPK) {
        this.grupoAlumnoTareaEntidadPK = grupoAlumnoTareaEntidadPK;
    }

    @Basic
    @Column(name = "asignacion" , insertable = false)
    public Timestamp getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(Timestamp asignacion) {
        this.asignacion = asignacion;
    }
}
package tecolotl.profesor.entidad;

import tecolotl.alumno.entidad.AlumnoEntidad;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "grupo", schema = "profesor")
@NamedQueries({
    @NamedQuery(name ="GrupoEntidad.busca",query = "SELECT g FROM GrupoEntidad g")
})
public class GrupoEntidad {

    private Integer id;
    private Short grado;
    private char grupo;
    private ProfesorEntidad profesorEntidad;

    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    public Short getGrado() {
        return grado;
    }

    public void setGrado(Short grado) {
        this.grado = grado;
    }

    @NotNull
    public char getGrupo() {
        return grupo;
    }

    public void setGrupo(char grupo) {
        this.grupo = grupo;
    }

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_profesor")
    public ProfesorEntidad getProfesorEntidad() {
        return profesorEntidad;
    }

    public void setProfesorEntidad(ProfesorEntidad profesorEntidad) {
        this.profesorEntidad = profesorEntidad;
    }
}

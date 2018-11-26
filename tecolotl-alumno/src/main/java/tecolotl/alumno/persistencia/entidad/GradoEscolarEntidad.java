package tecolotl.alumno.persistencia.entidad;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;


/**
 * The persistent class for the grado_escolar database table.
 * 
 */
@Entity
@Table(name="grado_escolar", schema="alumno")
@NamedQuery(name="GradoEscolarEntidad.findAll", query="SELECT g FROM GradoEscolarEntidad g")
public class GradoEscolarEntidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@NotNull
	@Max(6)
	@Min(1)
	private Integer grado;
	@NotNull
	@Size(min= 1, max = 1)
	private String grupo;

	//bi-directional many-to-one association to AlumnoEntidad
	@OneToMany(mappedBy="gradoEscolarBean")
	private List<AlumnoEntidad> alumnos;

	public GradoEscolarEntidad() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGrado() {
		return this.grado;
	}

	public void setGrado(Integer grado) {
		this.grado = grado;
	}

	public String getGrupo() {
		return this.grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public List<AlumnoEntidad> getAlumnos() {
		return this.alumnos;
	}

	public void setAlumnos(List<AlumnoEntidad> alumnos) {
		this.alumnos = alumnos;
	}

	public AlumnoEntidad addAlumno(AlumnoEntidad alumno) {
		getAlumnos().add(alumno);
		alumno.setGradoEscolarBean(this);

		return alumno;
	}

	public AlumnoEntidad removeAlumno(AlumnoEntidad alumno) {
		getAlumnos().remove(alumno);
		alumno.setGradoEscolarBean(null);

		return alumno;
	}

}
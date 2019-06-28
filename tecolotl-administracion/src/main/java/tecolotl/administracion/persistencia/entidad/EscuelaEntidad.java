package tecolotl.administracion.persistencia.entidad;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "escuela", schema = "administracion")
@NamedQueries({
	@NamedQuery(
			name = "EscuelaEntidad.busca",
			query = "SELECT e from EscuelaEntidad e ORDER BY e.nombre"
	),
	@NamedQuery(
			name = "EscuelaEntidad.detalle",
			query = "SELECT e FROM EscuelaEntidad e LEFT JOIN FETCH e.coloniaEntidad " +
					"LEFT JOIN FETCH e.motivoBloqueoEntidad WHERE e.claveCentroTrabajo=:claveCentroTrabajo"
	),
	@NamedQuery(
			name = "EscuelaEntidad.existe",
			query = "SELECT e.nombre FROM EscuelaEntidad e WHERE e.claveCentroTrabajo=:claveCentroTrabajo"
	),//TODO querys de TotalAlumnos y TotalProfesores por escuela
    @NamedQuery(
            name = "EscuelaEntidad.totalAlumnos",
            query = "SELECT COUNT(pga.id), p.nombre FROM ProfesorEntidad p "+
                "LEFT JOIN p.GrupoEntidad pg " +
                "LEFT JOIN pg.GrupoAlumnoEntidad pga " +
                "WHERE p.escuelaEntidad.claveCentroTrabajo =: claveCentroTrabajo GROUP BY pga.id, p.nombre"
    ),
    @NamedQuery(
            name = "EscuelaEntidad.totalProfesores",
            query = "SELECT COUNT(p.id), e.escuelaEntidad.nombre FROM ProfesorEntidad p " +
                "LEFT JOIN p.EscuelaEntidad e " +
                "WHERE p.escuelaEntidad.claveCentroTrabajo =: claveCentroTrabajo GROUP BY p.id, e.escuelaEntidad.nombre"
    )

})
public class EscuelaEntidad {

    private String claveCentroTrabajo;
    private ColoniaEntidad coloniaEntidad;
    private MotivoBloqueoEntidad motivoBloqueoEntidad;
    private String nombre;
	private String domicilio;
	private String numeroInterior;
	private String numeroExterior;
	private List<LicenciaEntidad> licenciaEntidadLista;
	private List<ContactoEntidad> contactoEntidadLista;

	public EscuelaEntidad() {
	}

	public EscuelaEntidad(String claveCentroTrabajo) {
		this.claveCentroTrabajo = claveCentroTrabajo;
	}

	@Id
	@Column(name = "clave_centro_trabajo")
	@Size(min = 10, max = 14)
	public String getClaveCentroTrabajo() {
		return claveCentroTrabajo;
	}

	public void setClaveCentroTrabajo(String claveCentroTrabajo) {
		this.claveCentroTrabajo = claveCentroTrabajo;
	}

	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_colonia", referencedColumnName = "id")
	public ColoniaEntidad getColoniaEntidad() {
		return coloniaEntidad;
	}

	public void setColoniaEntidad(ColoniaEntidad coloniaEntidad) {
		this.coloniaEntidad = coloniaEntidad;
	}

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_motivo_bloqueo", referencedColumnName = "clave")
	public MotivoBloqueoEntidad getMotivoBloqueoEntidad() {
		return motivoBloqueoEntidad;
	}

	public void setMotivoBloqueoEntidad(MotivoBloqueoEntidad motivoBloqueoEntidad) {
		this.motivoBloqueoEntidad = motivoBloqueoEntidad;
	}

	@Basic
	@Column(name = "nombre")
	@NotNull
	@Size(min = 4, max = 70)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Basic
	@Column(name = "domicilio")
	@NotNull
	@Size(min = 11, max = 60)
	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	@Basic
	@Column(name = "numero_interior")
	@Size(max = 15, min = 1)
	public String getNumeroInterior() {
		return numeroInterior;
	}

	public void setNumeroInterior(String numeroInterior) {
		this.numeroInterior = numeroInterior;
	}

	@Basic
	@Column(name = "numero_exterior")
	@NotNull
	@Size(max = 15, min = 1)
	public String getNumeroExterior() {
		return numeroExterior;
	}

	public void setNumeroExterior(String numeroExterior) {
		this.numeroExterior = numeroExterior;
	}

	@PrePersist
	public void motivoPorDefecto() {
		if (motivoBloqueoEntidad == null) {
			motivoBloqueoEntidad = new MotivoBloqueoEntidad((short)1);
		}
	}

	@OneToMany(mappedBy = "contactoEntidadPK.escuelaEntidad", fetch = FetchType.LAZY)
	public List<ContactoEntidad> getContactoEntidadLista() {
		return contactoEntidadLista;
	}

	public void setContactoEntidadLista(List<ContactoEntidad> contactoEntidadLista) {
		this.contactoEntidadLista = contactoEntidadLista;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EscuelaEntidad that = (EscuelaEntidad) o;
		return claveCentroTrabajo.equals(that.claveCentroTrabajo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(claveCentroTrabajo);
	}
}

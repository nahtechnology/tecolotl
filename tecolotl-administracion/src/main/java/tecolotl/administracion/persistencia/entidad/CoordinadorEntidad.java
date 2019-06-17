package tecolotl.administracion.persistencia.entidad;

import tecolotl.nucleo.persistencia.entidad.PersonaEntidad;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "coordinador", schema = "administracion")
@NamedQueries({
        @NamedQuery(
                name = "CoordinadorEntidad.busca",
                query = "SELECT c FROM CoordinadorEntidad c "
        ),
        @NamedQuery(
                name = "CoordinadorEntidad.buscaEscuela",
                query = "SELECT c FROM CoordinadorEntidad c LEFT JOIN FETCH c.coordinadorMotivoBloqueoEntidad cmb " +
                        "WHERE c.coordinadorEntidadPK.escuelaEntidad.claveCentroTrabajo = :claveCentroTrabajo ORDER BY c.apellidoPaterno"
        )
})
public class CoordinadorEntidad extends PersonaEntidad {

    private CoordinadorEntidadPK coordinadorEntidadPK;
    private String correoEletronico;
    private CoordinadorMotivoBloqueoEntidad coordinadorMotivoBloqueoEntidad;

    @EmbeddedId
    public CoordinadorEntidadPK getCoordinadorEntidadPK() {
        return coordinadorEntidadPK;
    }

    public void setCoordinadorEntidadPK(CoordinadorEntidadPK coordinadorEntidadPK) {
        this.coordinadorEntidadPK = coordinadorEntidadPK;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_motivo_bloqueo")
    public CoordinadorMotivoBloqueoEntidad getCoordinadorMotivoBloqueoEntidad() {
        return coordinadorMotivoBloqueoEntidad;
    }

    public void setCoordinadorMotivoBloqueoEntidad(CoordinadorMotivoBloqueoEntidad coordinadorMotivoBloqueoEntidad) {
        this.coordinadorMotivoBloqueoEntidad = coordinadorMotivoBloqueoEntidad;
    }
}

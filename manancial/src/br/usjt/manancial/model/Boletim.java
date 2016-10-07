package br.usjt.manancial.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="boletim")
@NamedQueries({ 
	@NamedQuery(name = "Boletim.listar", query = "select b from Boletim b join b.represa r join r.sistema s order by b.dataBoletim,s.nome,r.nome"), 
	@NamedQuery(name = "Boletim.listarSistemaOrRepresa", query = "select b from Boletim b join b.represa r join r.sistema s where b.represa = :represa or (r.sistema = :sistema and :represa is null) order by s.nome,r.nome,b.dataBoletim") 
})
public class Boletim implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@NotNull(message="O campo Represa é obrigatório.")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "represa_id")
	@JsonIgnore
	private Represa represa;
	
	@NotNull(message="O campo volume é obrigatório")
	@Column(name="volumedia")	
	private Double volumeDia;
	
	@NotNull(message="O campo Data do boletim é obrigatório")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="databoletim")
	private Date dataBoletim;
	
	@Column(name="pluviometria")	
	private Double pluviometria;

	transient Double variacao;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Represa getRepresa() {
		return represa;
	}

	public void setRepresa(Represa represa) {
		this.represa = represa;
	}

	public Double getVolumeDia() {
		return volumeDia;
	}

	public void setVolumeDia(Double volumeDia) {
		this.volumeDia = volumeDia;
	}

	public Date getDataBoletim() {
		return dataBoletim;
	}

	public void setDataBoletim(Date dataBoletim) {
		this.dataBoletim = dataBoletim;
	}

	public Double getPluviometria() {
		return pluviometria;
	}

	public void setPluviometria(Double pluviometria) {
		this.pluviometria = pluviometria;
	}

	public Double getVariacao() {
		return variacao;
	}
	public void setVariacao(Double variacao) {
		this.variacao = variacao;
	}
	
	@Override
	public String toString() {
		return "Boletim [id=" + id + ", volumeDia=" + volumeDia + ", dataBoletim=" + dataBoletim + ", pluviometria="
				+ pluviometria + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Boletim other = (Boletim) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}

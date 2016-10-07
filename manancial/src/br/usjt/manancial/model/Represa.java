package br.usjt.manancial.model;

import java.io.Serializable;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="represa")
@NamedQueries({ 
	@NamedQuery(name = "Represa.listar", query = "select r from Represa r join r.sistema s order by s.nome,r.nome"),
	@NamedQuery(name = "Represa.listarPorSistema", query = "select r from Represa r where r.sistema=:sistema order by r.nome") 
})
public class Represa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@NotEmpty(message="O campo Nome é obrigatório.")
	@Size(max=100,message="Tamanho máximo 100 caracteres.")
	@Column(name="nome",length=100,nullable=false)
	private String nome;
	
	@NotNull(message="O campo Sistema é obrigatório.")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "sistema_id")
	@JsonIgnore
	private Sistema sistema;
	
	@NotNull(message="O campo volume util é obrigatório")
	@Column(name="volumeutil")	
	private Double volumeUtil;
	
	@Column(name="volumereserva")
	private Double volumeReserva;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Sistema getSistema() {
		return sistema;
	}
	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}
	public Double getVolumeUtil() {
		return volumeUtil;
	}
	public void setVolumeUtil(Double volumeUtil) {
		this.volumeUtil = volumeUtil;
	}
	public Double getVolumeReserva() {
		return volumeReserva;
	}
	public void setVolumeReserva(Double volumeReserva) {
		this.volumeReserva = volumeReserva;
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
		Represa other = (Represa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Represa [id=" + id + ", nome=" + nome + ", sistema=" + sistema + ", volumeUtil=" + volumeUtil
				+ ", volumeReserva=" + volumeReserva + "]";
	}
	
	
}

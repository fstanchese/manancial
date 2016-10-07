package br.usjt.manancial.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="sistema")
@NamedQueries({ 
	@NamedQuery(name = "Sistema.listar", query = "select s from Sistema s order by s.nome")
})
public class Sistema implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@NotEmpty(message="O campo Nome é obrigatório.")
	@Size(max=100,message="Tamanho máximo 100 caracteres.")
	@Column(name="nome",length=100,nullable=false)
	private String nome;

	transient List<Municipio> municipios;
	
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
	
	public void setMunicipios(List<Municipio> municipios) {
		this.municipios = municipios;
	}
	
	public List<Municipio> getMunicipios() {
		return municipios;
	}
	

	@Override
	public String toString() {
		return "Sistema [id=" + id + ", nome=" + nome + ", municipios=" + municipios + "]";
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
		Sistema other = (Sistema) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

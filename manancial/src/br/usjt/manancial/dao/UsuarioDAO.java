package br.usjt.manancial.dao;

import javax.persistence.EntityManager;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.usjt.manancial.model.Usuario;

@Transactional
@Repository
public class UsuarioDAO {

	@PersistenceContext
	EntityManager manager;

	public Usuario buscaUsuario(Usuario usuario) {
		System.out.println("usuario "+usuario.toString());
		try {
			return (Usuario) manager.createNamedQuery("Usuario.buscaUsuario").
					setParameter("login", usuario.getLogin()).
					setParameter("senha", usuario.getSenha()).getSingleResult();
		} catch (NoResultException nre) {
			return new Usuario();
		}
	}
}

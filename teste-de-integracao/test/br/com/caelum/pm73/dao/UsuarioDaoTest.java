package br.com.caelum.pm73.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.hibernate.Session;
import org.junit.Test;

import br.com.caelum.pm73.dominio.Usuario;

public class UsuarioDaoTest {

	@Test
	public void deveEncontrarPeloNomeEEmailMockado() {

		Session session = new CriadorDeSessao().getSession();
		UsuarioDao usuarioDao = new UsuarioDao(session);

		Usuario novoUsuario = new Usuario("Joao da Silva", "joao@dasilva.com.br");
		usuarioDao.salvar(novoUsuario);

		Usuario usuarioDoBanco = usuarioDao.porNomeEEmail("Joao da Silva", "joao@dasilva.com.br");

		assertEquals("Joao da Silva", usuarioDoBanco.getNome());
		assertEquals("joao@dasilva.com.br", usuarioDoBanco.getEmail());

		session.close();

	}

	@Test
	public void deveRetornarNuloCasoNaoEncontrarUsuario() {

		Session session = new CriadorDeSessao().getSession();
		UsuarioDao usuarioDao = new UsuarioDao(session);

		Usuario usuarioDoBanco = usuarioDao.porNomeEEmail("Naruto Uzumaki", "naruto@gmail.com.br");

		assertNull(usuarioDoBanco);

		session.close();

	}

}

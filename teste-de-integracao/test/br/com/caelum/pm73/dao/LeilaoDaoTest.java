package br.com.caelum.pm73.dao;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.pm73.dominio.Leilao;
import br.com.caelum.pm73.dominio.Usuario;

public class LeilaoDaoTest {
	private Session session;
	private LeilaoDao leilaoDao;
	private UsuarioDao usuarioDao;

	@Before
	public void antes() {

		session = new CriadorDeSessao().getSession();
		leilaoDao = new LeilaoDao(session);
		usuarioDao = new UsuarioDao(session);

		session.beginTransaction();

	}

	@After
	public void depois() {
		session.getTransaction().rollback();
		session.close();
	}

	@Test
	public void deveContarLeiloesNaoEncerrados() {

		Usuario naruto = new Usuario("Naruto Uzumaki", "naruto@gmail.com.br");

		Leilao ativo = new Leilao("Geladeira", 1500.0, naruto, false);
		Leilao encerrado = new Leilao("XBox", 700.0, naruto, false);
		encerrado.encerra();

		usuarioDao.salvar(naruto);
		leilaoDao.salvar(ativo);
		leilaoDao.salvar(encerrado);

		long total = leilaoDao.total();

		assertEquals(1L, total);

	}

	@Test
	public void deveRetornarZeroSeNaoTemLeiloes() {
		Usuario naruto = new Usuario("Naruto Uzumaki", "naruto@gmail.com.br");
		Leilao encerrado = new Leilao("Geladeira", 1500.0, naruto, false);
		Leilao tambemEncerrado = new Leilao("XBox", 700.0, naruto, false);
		encerrado.encerra();

		encerrado.encerra();
		tambemEncerrado.encerra();

		usuarioDao.salvar(naruto);
		leilaoDao.salvar(tambemEncerrado);
		leilaoDao.salvar(encerrado);

		long total = leilaoDao.total();

		assertEquals(0L, total);

	}

	@Test
	public void deveRetornarLeiloesDeProdutosNovos() {

		Usuario naruto = new Usuario("Naruto Uzumaki", "naruto@gmail.com.br");

		Leilao produtoNovo = new Leilao("XBox", 700.0, naruto, false);
		Leilao produtoUsado = new Leilao("Geladeira", 1500.0, naruto, true);

		usuarioDao.salvar(naruto);
		leilaoDao.salvar(produtoNovo);
		leilaoDao.salvar(produtoUsado);

		List<Leilao> novos = leilaoDao.novos();

		assertEquals(1, novos.size());
		assertEquals("XBox", novos.get(0).getNome());

	}

	@Test
	public void deveRetornarLeiloesDeProdutosAntigos() {

		Calendar dataAntiga = Calendar.getInstance();
		dataAntiga.add(Calendar.DAY_OF_WEEK, -15);
		Calendar dataAtual = Calendar.getInstance();

		Usuario naruto = new Usuario("Naruto Uzumaki", "naruto@gmail.com.br");

		Leilao produtoAntigo = new Leilao("Kunai", 2300.0, naruto, false);

		Leilao produtoNovo = new Leilao("Shuriken", 1500.0, naruto, true);

		produtoAntigo.setDataAbertura(dataAntiga);
		produtoNovo.setDataAbertura(dataAtual);

		usuarioDao.salvar(naruto);
		leilaoDao.salvar(produtoAntigo);
		leilaoDao.salvar(produtoNovo);

		List<Leilao> antigos = leilaoDao.antigos();

		assertEquals(1, antigos.size());
		assertEquals("Kunai", antigos.get(0).getNome());

	}
	
	@Test
	public void deveTrazerSomenteLeiloesAntigosApartirDe7Dias() {

		Calendar dataAntiga = Calendar.getInstance();
		dataAntiga.add(Calendar.DAY_OF_WEEK, -7);
		Calendar dataAtual = Calendar.getInstance();
		dataAtual.add(Calendar.DAY_OF_WEEK, -6);

		Usuario naruto = new Usuario("Naruto Uzumaki", "naruto@gmail.com.br");

		Leilao produtoAntigo = new Leilao("Kunai", 2300.0, naruto, false);

		Leilao produtoNovo = new Leilao("Shuriken", 1500.0, naruto, true);

		produtoAntigo.setDataAbertura(dataAntiga);
		produtoNovo.setDataAbertura(dataAtual);

		usuarioDao.salvar(naruto);
		leilaoDao.salvar(produtoAntigo);
		leilaoDao.salvar(produtoNovo);

		List<Leilao> antigos = leilaoDao.antigos();

		assertEquals(1, antigos.size());	
		assertEquals("Kunai", antigos.get(0).getNome());

	}

	
	@Test
	public void deveTrazerLeiloesNaoEncerradosNoPeriodo() {

		Calendar comecoIntervalo = Calendar.getInstance();
		comecoIntervalo.add(Calendar.DAY_OF_WEEK, -10);
		
		Calendar finalIntervalo = Calendar.getInstance();		

		Usuario naruto = new Usuario("Naruto Uzumaki", "naruto@gmail.com.br");

		Leilao leilao1 = new Leilao("Kunai", 2300.0, naruto, false);
		Leilao leilao2 = new Leilao("Shuriken", 1500.0, naruto, true);

		Calendar dataleilao1 = Calendar.getInstance();
		dataleilao1.add(Calendar.DAY_OF_MONTH, -2);
		leilao1.setDataAbertura(dataleilao1);
		
		Calendar dataleilao2 = Calendar.getInstance();
		dataleilao2.add(Calendar.DAY_OF_MONTH, -20);
		leilao1.setDataAbertura(dataleilao2);
		

		usuarioDao.salvar(naruto);
		leilaoDao.salvar(leilao1);
		leilaoDao.salvar(leilao2);
		
		
		List<Leilao> leiloes = leilaoDao.porPeriodo(comecoIntervalo, finalIntervalo);
		
		assertEquals(1, leiloes.size());	
		assertEquals("Shuriken", leiloes.get(0).getNome());

	}
	
	@Test
	public void NaoDeveTrazerLeiloesEncerradosNoPeriodo() {

		Calendar comecoIntervalo = Calendar.getInstance();
		comecoIntervalo.add(Calendar.DAY_OF_WEEK, -10);
		
		Calendar finalIntervalo = Calendar.getInstance();		

		Usuario naruto = new Usuario("Naruto Uzumaki", "naruto@gmail.com.br");
			
		Calendar dataleilao1 = Calendar.getInstance();
		dataleilao1.add(Calendar.DAY_OF_MONTH, -2);
		
		Leilao leilao1 = new Leilao("Kunai", 2300.0, naruto, false);
		leilao1.setDataAbertura(dataleilao1);
		leilao1.encerra();
					
		usuarioDao.salvar(naruto);
		leilaoDao.salvar(leilao1);		
		
		
		List<Leilao> leiloes = leilaoDao.porPeriodo(comecoIntervalo, finalIntervalo);
		
		assertEquals(0, leiloes.size());			

	}
	
	
}

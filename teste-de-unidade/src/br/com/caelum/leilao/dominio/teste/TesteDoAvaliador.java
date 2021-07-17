package br.com.caelum.leilao.dominio.teste;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import br.com.caelum.leilao.dominio.Avaliador;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matcher.*;

public class TesteDoAvaliador {

	private Avaliador leiloeiro;	
	private Usuario joao;
	private Usuario jose;
	private Usuario maria;
	private Usuario pedro;
	private Usuario mario;
	private Leilao leilao;
	private Avaliador avaliador;
	
	@Before
	public void setUp() {
		avaliador = new Avaliador();
		leiloeiro = new Avaliador();
		joao = new Usuario("João");
		jose = new Usuario("José");
		maria = new Usuario("Maria");
		pedro = new Usuario("pedro");
		mario = new Usuario("mario");
		leilao = new Leilao("Fusca");
	}
	
	
	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
					
		leilao.propoe(new Lance(joao, 300.0));
		leilao.propoe(new Lance(jose, 400.0));
		leilao.propoe(new Lance(maria, 250.0));
		
		Leilao leilao = new CriadorDeLeilao().para("Fusca")
				.lance(joao, 300.0)
				.lance(jose, 400.0)
				.lance(maria, 250.0)				
				.constroi();

		// ação		
		leiloeiro.avalia(leilao);

		// validacao
		double maiorEsperado = 400.0;
		double menorEsperado = 250.0;

		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
	}

	@Test
	public void deveCalcularAMediaDosLances() {
							
		Leilao leilao = new CriadorDeLeilao().para("Fusca")
				.lance(joao, 100.0)
				.lance(jose, 200.0)
				.lance(maria, 300.0)
				
				.constroi();

		// ação		
		leiloeiro.avalia(leilao);

		// validacao
		double media = 200.0;

		assertEquals(media, leiloeiro.getMedia(), 0.00001);

	}

	@Test(expected=RuntimeException.class)
	public void testaMediaDeZeroLance() {

		Leilao leilao = new CriadorDeLeilao().para("Fusca").constroi();

		// acao		
		avaliador.avalia(leilao);
		
		// validacao
		Assert.fail();    	

	}

	@Test
	public void deveEntenderLeilaoComApenasUmLance() {

		// cenario
		leilao.propoe(new Lance(joao, 1000.0));

		// ação		
		leiloeiro.avalia(leilao);

		// validacao
		double maiorEsperado = 1000.0;
		double menorEsperado = 1000.0;

		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);

	}

	@Test
	public void deveEncontrarOsTresMaioresLances() {

		// cenario				
		Leilao leilao = new CriadorDeLeilao().para("Fusca")
				.lance(joao, 1000.0)
				.lance(maria, 2000.0)
				.lance(pedro, 500.0)
				.lance(mario, 5000.0)
				.lance(jose, 1000.0)
				.constroi();
					

		// ação		
		leiloeiro.avalia(leilao);

		// validacao
		List<Lance> maiores = leiloeiro.getMaiores();

		assertEquals(3, maiores.size());
		assertEquals(5000, maiores.get(0).getValor(), 0.00001);
		assertEquals(2000, maiores.get(1).getValor(), 0.00001);
		assertEquals(1000, maiores.get(2).getValor(), 0.00001);

	}

	@Test
	public void deveEncontrarOsDoisLances() {

		// cenario									
		Leilao leilao = new CriadorDeLeilao().para("Fusca")				
				.lance(pedro, 500.0)
				.lance(mario, 5000.0)				
				.constroi();						

		// ação		
		leiloeiro.avalia(leilao);

		// validacao
		List<Lance> maiores = leiloeiro.getMaiores();

		assertEquals(2, maiores.size());
		assertEquals(5000, maiores.get(0).getValor(), 0.00001);
		assertEquals(500, maiores.get(1).getValor(), 0.00001);

	}

	@Test(expected=RuntimeException.class)
	public void deveReceberListaVaziaDeLances() {

		Leilao leilao = new Leilao("Fusca");

		// ação		
		leiloeiro.avalia(leilao);
		
	}

	@Test
	public void deveEncontrarOMaiorEOMenorLance() {

		// cenario						
		Leilao leilao = new CriadorDeLeilao().para("Fusca")
				.lance(joao, 200.0)
				.lance(maria, 450.0)
				.lance(pedro, 120.0)
				.lance(mario, 700.0)
				.lance(jose, 630.0)
				.constroi();
					

		// ação		
		leiloeiro.avalia(leilao);

		// validacao
		double maior = 700.0;
		double menor = 120;

		assertEquals(menor, leiloeiro.getMenorLance(), 0.00001);
		assertEquals(maior, leiloeiro.getMaiorLance(), 0.00001);

	}		 
	
	
	@Test(expected=RuntimeException.class)
    public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {    	    	    
		Leilao leilao = new CriadorDeLeilao().para("Fusca").constroi();
		leiloeiro.avalia(leilao);			    		  
    }
	
	
	@Test(expected = IllegalArgumentException.class)
	public void deveLancarExcecaoSeOlanceForInferiorA1() {

		Leilao leilao = new CriadorDeLeilao()
				.lance(maria, 0)
				.constroi();

		// ação		
		leiloeiro.avalia(leilao);		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void deveLancarExcecaoSeOlanceForNegativo() {

		Leilao leilao = new CriadorDeLeilao()
				.lance(maria, -50.0)
				.constroi();

		// ação		
		leiloeiro.avalia(leilao);		
	}

}

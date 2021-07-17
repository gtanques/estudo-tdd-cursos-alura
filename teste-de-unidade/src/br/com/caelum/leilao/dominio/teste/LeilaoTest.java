package br.com.caelum.leilao.dominio.teste;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class LeilaoTest {
	
	Usuario naruto;
	Usuario sasuke;
	Usuario sakura;
	
	@Before
	public void setUp() {
		naruto = new Usuario("Naruto");
		sasuke = new Usuario("Sasuke");
		sakura = new Usuario("Sakura");
	}

	@Test
	public void deveReceberUmLance() {
				
		Leilao leilao = new CriadorDeLeilao().para("Bicicleta")
				.lance(naruto, 800)
				.constroi();
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(800, leilao.getLances().get(0).getValor(), 0.00001);
	}
	
	@Test
	public void deveReceberVariosLances() {
						
		Leilao leilao = new CriadorDeLeilao().para("Bicicleta")
				.lance(naruto, 700)
				.lance(sasuke, 800)				
				.lance(sakura, 900)
				.constroi();

		assertEquals(3, leilao.getLances().size());
		assertEquals(700, leilao.getLances().get(0).getValor(), 0.00001);
		assertEquals(800, leilao.getLances().get(1).getValor(), 0.00001);
		assertEquals(900, leilao.getLances().get(2).getValor(), 0.00001);
		
	}
	
	@Test
	public void naoDeveReceberDoisLancesSeguidosDoMesmoUsuario() {
		
		Leilao leilao = new Leilao("Bicicleta");
		Usuario naruto = new Usuario("Naruto");
		
		leilao.propoe(new Lance(naruto, 800));
		leilao.propoe(new Lance(naruto, 3000));
		
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(800, leilao.getLances().get(0).getValor(), 0.00001);			
		
	}
	
	@Test
	public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {
		
		Leilao leilao = new CriadorDeLeilao().para("Kunai")
				.lance(sasuke, 1000)
				.lance(naruto, 1001)
				.lance(sasuke, 2000)				
				.lance(naruto, 3000)
				.lance(sasuke, 4000)				
				.lance(naruto, 5000)
				.lance(sasuke, 6000)				
				.lance(naruto, 7000)
				.lance(sasuke, 8000)				
				.lance(naruto, 9000)
				.lance(sasuke, 10000)				
				.lance(naruto, 11000)				
				.constroi();
		
		assertEquals(10, leilao.getLances().size());
		assertEquals(9000, leilao.getLances().get(9).getValor(), 0.00001);			
		
	}
	
	
	@Test
	public void DeveDobrarOLanceDoUsuario() {
					
		Leilao leilao = new CriadorDeLeilao().para("Bicicleta")
				.lance(naruto, 400)
				.lance(sasuke, 800)
				.constroi();
		leilao.dobraLance(naruto);
		
		
		assertEquals(3, leilao.getLances().size());		
		assertEquals(800, leilao.getLances().get(2).getValor(), 0.00001);			
		
	}
	
    @Test
    public void naoDeveDobrarCasoNaoHajaLanceAnterior() {               
        Leilao leilao = new CriadorDeLeilao().para("Bicicleta")
				.lance(naruto, 400)				
				.constroi();

        leilao.dobraLance(naruto);

        assertEquals(1, leilao.getLances().size());
    }	               
	
}

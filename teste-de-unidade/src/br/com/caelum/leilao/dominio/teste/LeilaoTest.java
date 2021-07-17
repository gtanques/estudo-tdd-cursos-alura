package br.com.caelum.leilao.dominio.teste;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class LeilaoTest {

	@Test
	public void deveReceberUmLance() {
		Leilao leilao = new Leilao("Bicicleta");
		assertEquals(0, leilao.getLances().size());
		
		leilao.propoe(new Lance(new Usuario("Naruto"), 800));
		assertEquals(1, leilao.getLances().size());
		assertEquals(800, leilao.getLances().get(0).getValor(), 0.00001);
	}
	
	@Test
	public void deveReceberVariosLances() {
		
		Leilao leilao = new Leilao("Bicicleta");
		assertEquals(0, leilao.getLances().size());
		
		leilao.propoe(new Lance(new Usuario("Naruto"), 800));
		leilao.propoe(new Lance(new Usuario("Sasuke"), 700));
		leilao.propoe(new Lance(new Usuario("Sakura"), 900));
		
		
		assertEquals(3, leilao.getLances().size());
		assertEquals(800, leilao.getLances().get(0).getValor(), 0.00001);
		assertEquals(700, leilao.getLances().get(1).getValor(), 0.00001);
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
		
		Leilao leilao = new Leilao("Kunai");
		Usuario naruto = new Usuario("Naruto");
		Usuario sasuke = new Usuario("Sasuke");
		
		leilao.propoe(new Lance(naruto, 1000));
		leilao.propoe(new Lance(sasuke, 2000));
		
		leilao.propoe(new Lance(naruto, 3000));
		leilao.propoe(new Lance(sasuke, 4000));
		
		leilao.propoe(new Lance(naruto, 5000));
		leilao.propoe(new Lance(sasuke, 6000));
		
		leilao.propoe(new Lance(naruto, 7000));
		leilao.propoe(new Lance(sasuke, 8000));
		
		leilao.propoe(new Lance(naruto, 9000));
		leilao.propoe(new Lance(sasuke, 10000));
		
		leilao.propoe(new Lance(naruto, 11000));
		
		assertEquals(10, leilao.getLances().size());
		assertEquals(10000, leilao.getLances().get(9).getValor(), 0.00001);			
		
	}
	
	
	@Test
	public void DeveDobrarOLanceDoUsuario() {
		
		Leilao leilao = new Leilao("Bicicleta");
		Usuario naruto = new Usuario("Naruto");
		Usuario sasuke = new Usuario("Sasuke");
		
		assertEquals(0, leilao.getLances().size());
		
		leilao.propoe(new Lance(naruto, 400));
		leilao.propoe(new Lance(sasuke, 800));	
		leilao.dobraLance(naruto);
		
		assertEquals(3, leilao.getLances().size());		
		assertEquals(800, leilao.getLances().get(2).getValor(), 0.00001);			
		
	}
	
    @Test
    public void naoDeveDobrarCasoNaoHajaLanceAnterior() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        Usuario steveJobs = new Usuario("Steve Jobs");

        leilao.dobraLance(steveJobs);

        assertEquals(0, leilao.getLances().size());
    }
	
	
}

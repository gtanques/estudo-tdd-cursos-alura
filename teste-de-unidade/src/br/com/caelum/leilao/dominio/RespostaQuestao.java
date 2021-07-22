package br.com.caelum.leilao.dominio;

class RespostaQuestao {

	private Avaliacao avaliacao;

	private Aluno aluno;

	private int nota;

	// construtor

	public RespostaQuestao(Avaliacao avaliacao, Aluno aluno, int nota) {

		if (avaliacao == null) {

			throw new IllegalArgumentoException("A avaliação não pode ser nula");

		}

		if (aluno == null) {

			throw new IllegalArgumentoException("O aluno não pode ser nula");

		}

		if (nota < 0) {

			throw new IllegalArgumentoException("A nota não pode ser menor que zero");

		}

		if (nota > 10) {

			throw new IllegalArgumentoException("A nota não pode ser maior que10");

		}

		// resto do código de atribuição aqui

	}

}
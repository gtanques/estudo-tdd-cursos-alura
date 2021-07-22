package br.com.caelum.leilao.dominio;

class RespostaQuestao {

	private Avaliacao avaliacao;

	private Aluno aluno;

	private int nota;

	// construtor

	public RespostaQuestao(Avaliacao avaliacao, Aluno aluno, int nota) {

		if (avaliacao == null) {

			throw new IllegalArgumentoException("A avalia��o n�o pode ser nula");

		}

		if (aluno == null) {

			throw new IllegalArgumentoException("O aluno n�o pode ser nula");

		}

		if (nota < 0) {

			throw new IllegalArgumentoException("A nota n�o pode ser menor que zero");

		}

		if (nota > 10) {

			throw new IllegalArgumentoException("A nota n�o pode ser maior que10");

		}

		// resto do c�digo de atribui��o aqui

	}

}
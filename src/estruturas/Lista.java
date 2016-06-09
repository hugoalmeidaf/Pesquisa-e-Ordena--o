package estruturas;

public class Lista {
	private No prim;
	private long nElem;

	public Lista() {
		this.prim = null;
		this.nElem = 0;
	}

	public void insere(Promissoria elem) {
		No novoNo = new No(elem);
		novoNo.setProx(this.prim);
		this.prim = novoNo;
		this.nElem++;
	}

	public long getnElem() {
		return this.nElem;
	}

	public Promissoria getElemVetor(long pos) {
		No no = this.prim;
		for (int i = 0; i < pos; i++) {
			no = no.getProx();
		}
		return no.getPromissoria();
	}

	public Lista pesquisaPagas() {
		Lista pagas = new Lista();
		No atual = this.prim;
		do {
			if (atual.getPromissoria().Pago)
				pagas.insere(atual.getPromissoria());

			atual = atual.getProx();
		} while (atual != null);

		return pagas;
	}

	public Lista pesquisaNaoPagas() {
		Lista pagas = new Lista();
		No atual = this.prim;
		do {
			if (!atual.getPromissoria().Pago)
				pagas.insere(atual.getPromissoria());

			atual = atual.getProx();
		} while (atual != null);

		return pagas;
	}

	public No getPrimeiro() {
		return this.prim;
	}

	public void Resumo() {
		No atual = this.prim;
		do {
			System.out.println(atual.getPromissoria().resumo());
			atual = atual.getProx();
		} while (atual != null);
	}
}

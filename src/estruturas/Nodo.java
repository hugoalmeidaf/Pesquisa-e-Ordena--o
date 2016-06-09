package estruturas;

public class Nodo {
	private Lista promissorias;
	private Nodo esq, dir;
	private byte fatorBalanceamento;

	public Nodo(Promissoria _info) {
		this.promissorias = new Lista();
		this.promissorias.insere(_info);
		this.fatorBalanceamento = 0;
	}

	public Nodo getDir() {
		return this.dir;
	}

	public void setDir(Nodo dir) {
		this.dir = dir;
	}

	public Nodo getEsq() {
		return this.esq;
	}

	public void setEsq(Nodo esq) {
		this.esq = esq;
	}

	public byte getFatorBalanceamento() {
		return this.fatorBalanceamento;
	}

	public void setFatorBalanceamento(byte fatorBalanceamento) {
		this.fatorBalanceamento = fatorBalanceamento;
	}

	public Promissoria getPromissoria() {
		if (this.promissorias != null) {
			return this.promissorias.getElemVetor(0);
		} else {
			return null;
		}
	}

	public Lista getLista() {
		return promissorias;
	}

	public void insere(Promissoria novo) {
		this.promissorias.insere(novo);
	}
}
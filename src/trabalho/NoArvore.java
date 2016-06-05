package trabalho;

public class NoArvore {
	private Lista promissorias;
	private NoArvore dir, esq;

	public NoArvore(Promissoria _info) {
		this.promissorias = new Lista();
		this.promissorias.insere(_info);
	}

	public NoArvore getDir() {
		return dir;
	}

	public void setDir(NoArvore dir) {
		this.dir = dir;
	}

	public NoArvore getEsq() {
		return esq;
	}

	public void setEsq(NoArvore esq) {
		this.esq = esq;
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
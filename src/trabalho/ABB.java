package trabalho;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import estruturas.Lista;
import estruturas.No;
import estruturas.NoArvore;
import estruturas.Promissoria;
import utilitarios.Arquivo;
import utilitarios.Data;

public class ABB {
	private NoArvore raiz;
	private int nElem;

	public ABB() {
		this.raiz = null;
		this.nElem = 0;
	}

	public Lista pesquisa(LocalDate data) {
		NoArvore no;

		no = this.pesquisa(data, this.raiz);
		if (no != null)
			return no.getLista();
		else
			return null;
	}

	private NoArvore pesquisa(LocalDate data, NoArvore no) {
		NoArvore temp;
		temp = no;

		if (temp != null) {
			if (Data.Comparar(data, temp.getPromissoria().getChave()) < 0)
				temp = this.pesquisa(data, temp.getEsq());
			else {
				if (Data.Comparar(data, temp.getPromissoria().getChave()) > 0)
					temp = this.pesquisa(data, temp.getDir());
			}
		}

		return temp;
	}

	public void insere(Promissoria elem) {
		this.raiz = this.insere(elem, this.raiz);
		this.nElem++;
	}

	private NoArvore insere(Promissoria elem, NoArvore no) {
		NoArvore novo;

		if (no == null) {
			novo = new NoArvore(elem);
			return novo;
		} else {
			if (Data.Comparar(elem.getChave(), no.getPromissoria().getChave()) < 0) {
				no.setEsq(this.insere(elem, no.getEsq()));
				return no;
			} else if (Data.Comparar(elem.getChave(), no.getPromissoria().getChave()) > 0) {
				no.setDir(this.insere(elem, no.getDir()));
				return no;
			} else {
				no.insere(elem);
				return no;
			}
		}
	}

	public void Mostra() {
		Mostra(this.raiz);
	}

	public void Mostra(NoArvore no) {
		if (no != null) {
			System.out.println(no.getPromissoria().resumo());
			System.out.println("ESQ");
			Mostra(no.getEsq());
			System.out.println("DIR");
			Mostra(no.getDir());
		}
	}

	public Tabela CamCentral() {
		return (this.FazCamCentral(this.raiz, new Tabela(nElem)));
	}

	private Tabela FazCamCentral(NoArvore arv, Tabela vetOrdenado) {
		if (arv != null && vetOrdenado != null) {
			vetOrdenado = this.FazCamCentral(arv.getEsq(), vetOrdenado);

			No no = arv.getLista().getPrimeiro();
			do {
				vetOrdenado.inserir(no.getPromissoria());
				no = no.getProx();
			} while (no != null);

			vetOrdenado = this.FazCamCentral(arv.getDir(), vetOrdenado);
		}
		return vetOrdenado;
	}

	public ABB ArvoreBalanceada(Tabela vetOrdenado) {
		ABB temp = new ABB();
		this.Balancear(vetOrdenado, temp, 0, vetOrdenado.getnElem() - 1);
		return temp;
	}

	private void Balancear(Tabela vet, ABB temp, int inic, int fim) {
		int meio;
		if (fim >= inic) {
			meio = (inic + fim) / 2;
			temp.insere(vet.getElemVetor(meio));
			this.Balancear(vet, temp, inic, meio - 1);
			this.Balancear(vet, temp, meio + 1, fim);
		}
	}

	public void PesquisarDatas(Boolean mostrarResultado) {
		try {
			File arquivo = Arquivo.Novo("resultado.txt");
			FileWriter writer = new FileWriter(arquivo);
			String[] linhas = Arquivo.LerLinhas("data.txt");

			LocalDate data;
			for (int i = 0; i < linhas.length; i++) {
				data = Data.Converter(linhas[i]);
				Lista resultado = this.pesquisa(data);
				if (resultado != null) {
					writer.write("---------");
					writer.write(System.lineSeparator());

					Lista pagas = resultado.pesquisaPagas();
					Lista naoPagas = resultado.pesquisaNaoPagas();
					double total = 0;

					if (pagas.getPrimeiro() != null) {
						No no = pagas.getPrimeiro();
						writer.write("Paga");
						writer.write(System.lineSeparator());
						do {
							writer.write(no.getPromissoria().resumo());
							writer.write(System.lineSeparator());
							no = no.getProx();
						} while (no != null);
					}

					if (naoPagas.getPrimeiro() != null) {
						No no = naoPagas.getPrimeiro();
						writer.write("N�o paga");
						writer.write(System.lineSeparator());
						do {
							total += no.getPromissoria().Valor;
							writer.write(no.getPromissoria().resumo());
							writer.write(System.lineSeparator());
							no = no.getProx();
						} while (no != null);
					}

					writer.write("Total n�o paga: ");
					writer.write(String.valueOf(total));
					writer.write(System.lineSeparator());
					writer.write("---------");
					writer.write(System.lineSeparator());
				} else {
					writer.write(data.toString());
					writer.write(": N�O H� PROMISS�RIA NA DATA MENCIONADA");
					writer.write(System.lineSeparator());
				}
			}

			writer.close();

			if (mostrarResultado) {
				Arquivo.Abrir(arquivo);
			}

		} catch (

		IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

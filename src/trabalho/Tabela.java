package trabalho;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import estruturas.Lista;
import estruturas.No;
import estruturas.Promissoria;
import utilitarios.Arquivo;
import utilitarios.Data;

public class Tabela {

	private Promissoria[] vetor;
	private int nElem;

	public Tabela() {
		this.vetor = null;
		this.nElem = 0;
	}

	public Tabela(int tamanho) {
		this.vetor = new Promissoria[tamanho];
		this.nElem = 0;
	}

	public boolean inserir(Promissoria elemento) {
		if (this.nElem == this.vetor.length)
			return false;
		else {
			this.vetor[this.nElem++] = elemento;
			return true;
		}
	}

	public int getnElem() {
		return this.nElem;
	}

	public Promissoria getElemVetor(int pos) {
		return this.vetor[pos];
	}

	public void QuickSort() {
		Ordena(0, this.nElem - 1);
	}

	private void Ordena(int esq, int dir) {
		int i = esq, j = dir;
		LocalDate pivo;
		Promissoria temp;
		pivo = this.vetor[(i + j) / 2].getChave();
		do {
			while (Data.Comparar(this.vetor[i].getChave(), pivo) < 0)
				i++;
			while (Data.Comparar(this.vetor[j].getChave(), pivo) > 0)
				j--;
			if (i <= j) {
				temp = this.vetor[i];
				this.vetor[i] = this.vetor[j];
				this.vetor[j] = temp;
				i++;
				j--;
			}
		} while (i <= j);
		if (esq < j)
			Ordena(esq, j);
		if (dir > i)
			Ordena(i, dir);
	}

	public Lista pesqBinaria(LocalDate chave) {
		int meio, esq = 0, dir = this.nElem - 1;

		LocalDate dataTemp;
		while (esq <= dir) {
			meio = (esq + dir) / 2;
			dataTemp = this.vetor[meio].getChave();
			if (Data.Comparar(chave, dataTemp) == 0) {
				Lista resultado = new Lista();
				resultado.insere(this.vetor[meio]);

				// VERIFICANDO SE NA ESQUERDA N�O H� UMA CHAVE IGUAL
				for (int i = meio - 1; i > esq; i--) {
					dataTemp = this.vetor[i].getChave();

					if (Data.Comparar(chave, dataTemp) == 0) {
						resultado.insere(this.vetor[i]);
					} else {
						break;
					}
				}

				// VERIFICANDO SE NA DIREITA N�O H� UMA CHAVE IGUAL
				for (int i = meio + 1; i < dir; i++) {
					dataTemp = this.vetor[i].getChave();

					if (Data.Comparar(chave, dataTemp) == 0) {
						resultado.insere(this.vetor[i]);
					} else {
						break;
					}
				}

				return resultado;

			} else {
				if (Data.Comparar(chave, dataTemp) < 0)
					dir = meio - 1;
				else
					esq = meio + 1;
			}
		}
		return null;
	}

	public void Salvar(String nomeArquivo) {
		File arquivo = Arquivo.Novo(nomeArquivo);
		try {
			FileWriter writer = new FileWriter(arquivo);
			for (int i = 0; i < this.nElem; i++) {
				writer.write(vetor[i].toString());
				writer.write(System.lineSeparator());
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Carregar(String nomeArquivo) {
		try {
			String[] linhas = Arquivo.LerLinhas(nomeArquivo);
			
			this.vetor = new Promissoria[linhas.length];
			this.nElem = 0;

			for (int i = 0; i < linhas.length; i++) {
				String[] valores = linhas[i].split(";");
				Promissoria p = new Promissoria();

				p.Vencimento = Data.Converter(valores[0]);
				p.Nome = valores[1];
				p.CPF = valores[2];
				p.Valor = Double.parseDouble(valores[3]);
				p.Pago = Boolean.parseBoolean(valores[4]);

				this.inserir(p);
			}

		} catch (Exception e) {
			e.printStackTrace();
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
				Lista resultado =  this.pesqBinaria(data);
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

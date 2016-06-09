package trabalho;

import java.util.stream.LongStream;

import estruturas.Promissoria;
import utilitarios.Arquivo;
import utilitarios.Data;

public class Trabalho {

	public static void main(String[] args) {
		System.out.println("---- TABELA USANDO QUICKSORT ----");
		Lista();
		System.out.println("---- ABB ----");
		ABB();
		System.out.println("---- AVL ----");
		AVL();
		System.out.println("---- TABELA USANDO HASHING----");
		Hashing();
	}

	public static void Lista() {

		int qtdVezes = 5;
		long[] tempos = new long[qtdVezes];

		for (String arquivo : Arquivo.arquivos) {
			System.out.println("Arquivo: " + arquivo + " ------------");

			for (int i = 0; i < qtdVezes; i++) {
				long tempoInicio = System.currentTimeMillis();

				Tabela lista = new Tabela();
				lista.Carregar(arquivo);
				lista.QuickSort();
				lista.Salvar("quicksort.txt");
				lista.PesquisarDatas(false);

				tempos[i] = System.currentTimeMillis() - tempoInicio;
			}

			long soma = LongStream.of(tempos).sum();

			System.out.println("Media tempo: " + soma / qtdVezes + " ms");
			System.out.println("-------------------");
		}
	}

	public static void ABB() {
		int qtdVezes = 5;
		long[] tempos = new long[qtdVezes];

		for (String arquivo : Arquivo.arquivos) {
			System.out.println("Arquivo: " + arquivo + " ------------");

			for (int i = 0; i < qtdVezes; i++) {
				long tempoInicio = System.currentTimeMillis();

				ABB arvore = new ABB();
				try {
					String[] linhas = Arquivo.LerLinhas(arquivo);

					for (int j = 0; j < linhas.length; j++) {
						String[] valores = linhas[j].split(";");

						Promissoria p = new Promissoria();
						p.Vencimento = Data.Converter(valores[0]);
						p.Nome = valores[1];
						p.CPF = valores[2];
						p.Valor = Double.parseDouble(valores[3]);
						p.Pago = Boolean.parseBoolean(valores[4]);

						arvore.insere(p);

						if (j % 10000 == 0) {
							arvore = arvore.ArvoreBalanceada(arvore.CamCentral());
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				arvore.PesquisarDatas(false);
				// arvore.Mostra();

				tempos[i] = System.currentTimeMillis() - tempoInicio;
			}

			long soma = LongStream.of(tempos).sum();

			System.out.println("Media tempo: " + soma / qtdVezes + " ms");
			System.out.println("-------------------");
		}
	}

	public static void AVL() {
		int qtdVezes = 5;
		long[] tempos = new long[qtdVezes];

		for (String arquivo : Arquivo.arquivos) {
			System.out.println("Arquivo: " + arquivo + " ------------");

			for (int i = 0; i < qtdVezes; i++) {
				long tempoInicio = System.currentTimeMillis();

				AVL arvore = new AVL();
				arvore.Carregar(arquivo);
				arvore = arvore.ArvoreBalanceada(arvore.CamCentral());
				arvore.PesquisarDatas(false);
				tempos[i] = System.currentTimeMillis() - tempoInicio;
			}

			long soma = LongStream.of(tempos).sum();

			System.out.println("Media tempo: " + soma / qtdVezes + " ms");
			System.out.println("-------------------");
		}
	}

	public static void Hashing() {

		int qtdVezes = 5;
		long[] tempos = new long[qtdVezes];

		for (String arquivo : Arquivo.arquivos) {
			System.out.println("Arquivo: " + arquivo + " ------------");

			for (int i = 0; i < qtdVezes; i++) {
				long tempoInicio = System.currentTimeMillis();

				TabelaHashing hashing = new TabelaHashing();
				hashing.Carregar(arquivo);
				hashing.PesquisarDatas(false);

				tempos[i] = System.currentTimeMillis() - tempoInicio;
			}

			long soma = LongStream.of(tempos).sum();

			System.out.println("Media tempo: " + soma / qtdVezes + " ms");
			System.out.println("-------------------");
		}
	}
}

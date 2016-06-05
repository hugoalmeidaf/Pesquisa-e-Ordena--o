package trabalho;

import java.util.stream.LongStream;

public class Trabalho {

	public static void main(String[] args) {
		// Lista();
		Arvore();
	}

	public static void Lista() {

		int qtdVezes = 5;
		long[] tempos = new long[qtdVezes];
		String[] arquivos = { "aquecimento.txt", "promissoria500alea.txt", "promissoria500inv.txt",
				"promissoria500ord.txt", "promissoria1000alea.txt", "promissoria1000inv.txt", "promissoria1000ord.txt",
				"promissoria5000alea.txt", "promissoria5000inv.txt", "promissoria5000ord.txt",
				"promissoria10000alea.txt", "promissoria10000inv.txt", "promissoria10000ord.txt",
				"promissoria50000alea.txt", "promissoria50000inv.txt", "promissoria50000ord.txt" };

		for (String arquivo : arquivos) {
			System.out.println("Arquivo: " + arquivo + " ------------");

			for (int i = 0; i < qtdVezes; i++) {
				long tempoInicio = System.currentTimeMillis();

				PromissoriaLista lista = new PromissoriaLista();
				lista.Carregar(arquivo);
				lista.tabela.QuickSort();
				lista.tabela.Salvar("quicksort.txt");
				lista.PesquisarDatas();

				tempos[i] = System.currentTimeMillis() - tempoInicio;
			}

			long soma = LongStream.of(tempos).sum();

			System.out.println("Media tempo: " + soma / qtdVezes + " ms");
			System.out.println("-------------------");
		}
	}

	public static void Arvore() {
		int qtdVezes = 1;
		long[] tempos = new long[qtdVezes];
		String[] arquivos = { "promissoria50000inv.txt" };

		for (String arquivo : arquivos) {
			System.out.println("Arquivo: " + arquivo + " ------------");

			for (int i = 0; i < qtdVezes; i++) {
				long tempoInicio = System.currentTimeMillis();

				PromissoriaArvore arvore = new PromissoriaArvore();
				arvore.Carregar(arquivo);
				arvore = arvore.ArvoreBalanceada(arvore.CamCentral());
			    arvore.PesquisarDatas();
				//arvore.Mostra();

				// lista.QuickSort();
				// lista.Salvar("quicksort.txt");
				// lista.PesquisarDatas();
				//
				tempos[i] = System.currentTimeMillis() - tempoInicio;
			}

			long soma = LongStream.of(tempos).sum();

			System.out.println("Media tempo: " + soma / qtdVezes + " ms");
			System.out.println("-------------------");
		}
	}
}

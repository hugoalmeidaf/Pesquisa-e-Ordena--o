package trabalho;

public class Trabalho {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int qtdVezes = 5;
		long[] tempos = new long[qtdVezes];
		String[] arquivos = { "aquecimento.txt", "promissoria500alea.txt", "promissoria500inv.txt", "promissoria500ord.txt",
				"promissoria1000alea.txt", "promissoria1000inv.txt", "promissoria1000ord.txt",
				"promissoria5000alea.txt", "promissoria5000inv.txt", "promissoria5000ord.txt",
				"promissoria10000alea.txt", "promissoria10000inv.txt", "promissoria10000ord.txt",
				"promissoria50000alea.txt", "promissoria50000inv.txt", "promissoria50000ord.txt" };

		for (String arquivo : arquivos) {
			System.out.println("Arquivo: " + arquivo + " ------------");

			for (int i = 0; i < qtdVezes; i++) {
				long tempoInicio = System.currentTimeMillis();

				PromissoriaLista lista = new PromissoriaLista();
				lista.Carregar(arquivo);
				lista.QuickSort();
				lista.Salvar("quicksort.txt");
				lista.PesquisarDatas();

				tempos[i] = System.currentTimeMillis() - tempoInicio;
			}

			long soma = 0;
			for (long l : tempos) {
				soma += l;
			}

			System.out.println("Media tempo: " + soma / qtdVezes + " ms");
			System.out.println("-------------------");
		}
	}

}

package trabalho;

public class Trabalho {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PromissoriaLista lista = new PromissoriaLista();
		
		long tempoInicio = System.currentTimeMillis();
		
		lista.Carregar("promissoria500alea.txt");
		lista.QuickSort();
		lista.Salvar("quicksort.txt");
		
		System.out.println("Tempo Total: "+(System.currentTimeMillis()-tempoInicio) + " ms");
	}

}

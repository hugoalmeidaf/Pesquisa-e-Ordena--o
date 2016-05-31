package trabalho;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PromissoriaLista {
	private Promissoria[] promissoria;
	private String localArquivos = "arquivos/";

	public void Carregar(String nomeArquivo) {
		try {
			Path path = Paths.get(localArquivos + nomeArquivo);
			int qtd = (int) Files.lines(path).count();

			promissoria = new Promissoria[qtd];
			String[] linhas = Files.readAllLines(path).toArray(new String[0]);
			DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			for (int i = 0; i < linhas.length; i++) {
				String[] valores = linhas[i].split(";");
				promissoria[i] = new Promissoria();

				promissoria[i].Vencimento = LocalDate.parse(valores[0], formatoData);
				promissoria[i].Nome = valores[1];
				promissoria[i].CPF = valores[2];
				promissoria[i].Valor = Double.parseDouble(valores[3]);
				promissoria[i].Pago = Boolean.parseBoolean(valores[4]);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Salvar(String nomeArquivo){
		File arquivo = new File(localArquivos + nomeArquivo);
		try {
			FileWriter writer = new FileWriter(arquivo);
			for (int i = 0; i < promissoria.length; i++) {
				writer.write(promissoria[i].toString());
				writer.write(System.lineSeparator());
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Mostrar() {
		for (int i = 0; i < promissoria.length; i++) {
			System.out.println(promissoria[i].toString());
		}
	}

	public void QuickSort() {
		Ordena(0, this.promissoria.length - 1);
	}

	private void Ordena(int esq, int dir) {
		int i = esq, j = dir;
		LocalDate pivo;
		Promissoria temp;
		pivo = this.promissoria[(i + j) / 2].getChave();
		do {
			while (this.promissoria[i].getChave().compareTo(pivo) < 0)
				i++;
			while (this.promissoria[j].getChave().compareTo(pivo) > 0)
				j--;
			if (i <= j) {
				temp = this.promissoria[i];
				this.promissoria[i] = this.promissoria[j];
				this.promissoria[j] = temp;
				i++;
				j--;
			}
		} while (i <= j);
		if (esq < j)
			Ordena(esq, j);
		if (dir > i)
			Ordena(i, dir);
	}
}

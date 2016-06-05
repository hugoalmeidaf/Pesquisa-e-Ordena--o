package trabalho;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PromissoriaLista {
	public Tabela tabela;
	private String localArquivos = "arquivos/";

	public void Carregar(String nomeArquivo) {
		try {
			Path path = Paths.get(localArquivos + nomeArquivo);
			int qtd = (int) Files.lines(path).count();

			tabela = new Tabela(qtd);
			String[] linhas = Files.readAllLines(path).toArray(new String[0]);
			DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			for (int i = 0; i < linhas.length; i++) {
				String[] valores = linhas[i].split(";");
				Promissoria p = new Promissoria();

				p.Vencimento = LocalDate.parse(valores[0], formatoData);
				p.Nome = valores[1];
				p.CPF = valores[2];
				p.Valor = Double.parseDouble(valores[3]);
				p.Pago = Boolean.parseBoolean(valores[4]);

				tabela.inserir(p);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void AbrirArquivo(File arquivo) {
		try {
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().edit(arquivo);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// public void Mostrar() {
	// for (int i = 0; i < promissoria.length; i++) {
	// System.out.println(promissoria[i].toString());
	// }
	// }

	public void PesquisarDatas() {
		try {
			Path path = Paths.get(localArquivos + "data.txt");
			File arquivo = new File(localArquivos + "resultado.txt");
			FileWriter writer = new FileWriter(arquivo);

			String[] linhas = Files.readAllLines(path).toArray(new String[0]);
			DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate data;
			for (int i = 0; i < linhas.length; i++) {
				data = LocalDate.parse(linhas[i], formatoData);
				Lista resultado = tabela.pesqBinaria(data);
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

			// AbrirArquivo(arquivo);

		} catch (

		IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

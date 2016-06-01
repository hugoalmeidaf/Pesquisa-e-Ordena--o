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

	public void Salvar(String nomeArquivo) {
		File arquivo = new File(localArquivos + nomeArquivo);
		try {
			FileWriter writer = new FileWriter(arquivo);
			for (int i = 0; i < promissoria.length; i++) {
				writer.write(promissoria[i].toString());
				writer.write(System.lineSeparator());
			}
			writer.close();

			//AbrirArquivo(arquivo);

		} catch (IOException e) {
			// TODO Auto-generated catch block
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
				List<Promissoria> resultado = pesqBinaria(data);
				if (resultado != null) {
					writer.write("---------");
					// writer.write(System.lineSeparator());
					// writer.write(data.toString());
					// writer.write(":");
					writer.write(System.lineSeparator());

					List<Promissoria> pagas = new ArrayList<Promissoria>();
					List<Promissoria> naoPagas = new ArrayList<Promissoria>();
					double total = 0;

					for (Promissoria promissoria : resultado) {
						if (promissoria.Pago) {
							pagas.add(promissoria);
						} else {
							naoPagas.add(promissoria);
						}
					}

					if (pagas.size() > 0) {
						writer.write("Paga");
						writer.write(System.lineSeparator());
						for (Promissoria promissoria : pagas) {
							writer.write(promissoria.resumo());
							writer.write(System.lineSeparator());
						}
					}

					if (naoPagas.size() > 0) {
						writer.write("Não paga");
						writer.write(System.lineSeparator());
						for (Promissoria promissoria : naoPagas) {
							total += promissoria.Valor;
							writer.write(promissoria.resumo());
							writer.write(System.lineSeparator());
						}
					}
					
					writer.write("Total não paga: ");
					writer.write(String.valueOf(total));
					writer.write(System.lineSeparator());
					writer.write("---------");
					writer.write(System.lineSeparator());
				} else {
					writer.write(data.toString());
					writer.write(": NÃO HÁ PROMISSÓRIA NA DATA MENCIONADA");
					writer.write(System.lineSeparator());
				}
			}

			writer.close();

			//AbrirArquivo(arquivo);

		} catch (

		IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Promissoria> pesqBinaria(LocalDate chave) {
		int meio, esq, dir;
		esq = 0;
		dir = this.promissoria.length - 1;
		LocalDate dataTemp;
		while (esq <= dir) {
			meio = (esq + dir) / 2;
			dataTemp = this.promissoria[meio].getChave();
			if (chave.compareTo(dataTemp) == 0) {
				List<Promissoria> resultado = new ArrayList<Promissoria>();
				resultado.add(this.promissoria[meio]);

				// VERIFICANDO SE NA ESQUERDA NÃO HÁ UMA CHAVE IGUAL
				for (int i = meio - 1; i > esq; i--) {
					dataTemp = this.promissoria[i].getChave();

					if (chave.compareTo(dataTemp) == 0) {
						resultado.add(this.promissoria[i]);
					} else {
						break;
					}
				}

				// VERIFICANDO SE NA DIREITA NÃO HÁ UMA CHAVE IGUAL
				for (int i = meio + 1; i < dir; i++) {
					dataTemp = this.promissoria[i].getChave();

					if (chave.compareTo(dataTemp) == 0) {
						resultado.add(this.promissoria[i]);
					} else {
						break;
					}
				}

				return resultado;

			} else {
				if (chave.compareTo(dataTemp) < 0)
					dir = meio - 1;
				else
					esq = meio + 1;
			}
		}
		return null;
	}
}

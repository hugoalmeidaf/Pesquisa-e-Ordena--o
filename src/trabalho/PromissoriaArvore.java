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

public class PromissoriaArvore {
	private String localArquivos = "arquivos/";

	private NoArvore raiz;

	public PromissoriaArvore() {
		this.raiz = null;
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
			if (data.compareTo(temp.getPromissoria().getChave()) < 0)
				temp = this.pesquisa(data, temp.getEsq());
			else {
				if (data.compareTo(temp.getPromissoria().getChave()) > 0)
					temp = this.pesquisa(data, temp.getDir());
			}
		}

		return temp;
	}

	public void insere(Promissoria elem) {
		this.raiz = this.insere(elem, this.raiz);
	}

	private NoArvore insere(Promissoria elem, NoArvore no) {
		NoArvore novo;

		if (no == null) {
			novo = new NoArvore(elem);
			return novo;
		} else {
			if (elem.getChave().compareTo(no.getPromissoria().getChave()) < 0) {
				no.setEsq(this.insere(elem, no.getEsq()));
				return no;
			} else if (elem.getChave().compareTo(no.getPromissoria().getChave()) > 0) {
				no.setDir(this.insere(elem, no.getDir()));
				return no;
			} else {
				no.insere(elem);
				return no;
			}
		}
	}

	public void Carregar(String nomeArquivo) {
		try {
			Path path = Paths.get(localArquivos + nomeArquivo);

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

				this.insere(p);
			}

		} catch (Exception e) {
			e.printStackTrace();
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
		return (this.FazCamCentral(this.raiz, new Tabela(500000)));
	}

	private Tabela FazCamCentral(NoArvore arv, Tabela vetOrdenado) {
		if (arv != null && vetOrdenado != null) {
			vetOrdenado = this.FazCamCentral(arv.getEsq(), vetOrdenado);
			Lista promissorias = arv.getLista();
			No no = promissorias.getPrimeiro();
			do {
				vetOrdenado.inserir(no.getPromissoria());
				no = no.getProx();
			} while (no != null);
			vetOrdenado = this.FazCamCentral(arv.getDir(), vetOrdenado);
		}
		return vetOrdenado;
	}

	public PromissoriaArvore ArvoreBalanceada(Tabela vetOrdenado) {
		PromissoriaArvore temp = new PromissoriaArvore();
		this.Balancear(vetOrdenado, temp, 0, vetOrdenado.getnElem() - 1);
		return temp;
	}

	private void Balancear(Tabela vet, PromissoriaArvore temp, int inic, int fim) {
		int meio;
		if (fim >= inic) {
			meio = (inic + fim) / 2;
			temp.insere(vet.getElemVetor(meio));
			this.Balancear(vet, temp, inic, meio - 1);
			this.Balancear(vet, temp, meio + 1, fim);
		}
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

			AbrirArquivo(arquivo);

		} catch (

		IOException e) {
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
}
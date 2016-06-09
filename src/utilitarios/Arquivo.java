package utilitarios;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Arquivo {
	private static String localArquivos = "arquivos/";
	public static String[] arquivos = { "aquecimento.txt", "promissoria500alea.txt", "promissoria500inv.txt",
			"promissoria500ord.txt", "promissoria1000alea.txt", "promissoria1000inv.txt", "promissoria1000ord.txt",
			"promissoria5000alea.txt", "promissoria5000inv.txt", "promissoria5000ord.txt",
			"promissoria10000alea.txt", "promissoria10000inv.txt", "promissoria10000ord.txt",
			"promissoria50000alea.txt", "promissoria50000inv.txt", "promissoria50000ord.txt" };
	
	public static void Abrir(File arquivo) {
		try {
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().edit(arquivo);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static File Novo(String nomeArquivo) {
		return new File(localArquivos + nomeArquivo);
	}

	public static String[] LerLinhas(String nomeArquivo) throws IOException {
		Path path = Paths.get(localArquivos + nomeArquivo);
		return Files.readAllLines(path).toArray(new String[0]);
	}
}
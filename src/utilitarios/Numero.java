package utilitarios;

public class Numero {
	public static Boolean ÈPrimo(int numero) {		
		if (numero % 2 == 0) {
			return false;
		}
		
		for (long i = 3; i * i <= numero; i += 2) {
			if (numero % i == 0) {
				return false;
			}
		}
		return true;
	}
}

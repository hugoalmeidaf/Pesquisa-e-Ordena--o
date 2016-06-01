package trabalho;

import java.time.LocalDate;

public class Promissoria {
	public LocalDate Vencimento;
	public String Nome;
	public String CPF;
	public Double Valor;
	public Boolean Pago;

	@Override
	public String toString() {
		return this.Vencimento + " / " + 
			   this.Nome + " / " + 
			   this.CPF+ " / " + 
			   this.Valor + " / " + 
			   this.Pago;
	}
	
	public String resumo(){
		return     this.Vencimento + ";" + 
				   this.Valor + ";" + 
				   this.Nome;
	}
	public LocalDate getChave(){
		return this.Vencimento;
	}
}

package br.com.contabil.rest.dto;

import javax.validation.constraints.NotNull;

public class LancamentoDTO {
	
	@NotNull(message = "contaContabil nao pode ser nulo")
	private String contaContabil;
	@NotNull(message = "data nao pode ser nulo")
	private String data;
	@NotNull(message = "valor nao pode ser nulo")
	private String valor;
	
	public String getContaContabil() {
		return contaContabil;
	}
	public void setContaContabil(String contaContabil) {
		this.contaContabil = contaContabil;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}

}

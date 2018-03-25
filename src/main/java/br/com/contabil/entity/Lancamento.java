package br.com.contabil.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Lancamento {
	
	private String id = UUID.randomUUID().toString();
	private Long contaContabil;
	private Date data;
	private BigDecimal valor;
	
	public Long getContaContabil() {
		return contaContabil;
	}
	public void setContaContabil(Long contaContabil) {
		this.contaContabil = contaContabil;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getId() {
		return id;
	}

}

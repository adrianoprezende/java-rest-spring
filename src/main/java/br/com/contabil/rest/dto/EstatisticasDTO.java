package br.com.contabil.rest.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import br.com.contabil.entity.Lancamento;

public class EstatisticasDTO {
	
	private BigDecimal soma = BigDecimal.ZERO;
	private BigDecimal min;
	private BigDecimal max;
	private BigDecimal media;
	private Long qtde = 0l;
			
	public EstatisticasDTO(final List<Lancamento> lancamentos) {
		for(Lancamento lancamento : lancamentos) {
			min = calculaMinimo(lancamento);
			max = calculaValorMaximo(lancamento);
			soma = soma.add(lancamento.getValor());
			qtde++;
		}
		
		media = calculaValorMedio(qtde, soma);
	}
	
	private BigDecimal calculaMinimo(final Lancamento lancamento) {
		if(min == null) {
			return lancamento.getValor();
		}
		return min.min(lancamento.getValor());
	}
	
	private BigDecimal calculaValorMedio(final Long qtd, final BigDecimal soma) {
		return soma.divide(new BigDecimal(qtd), RoundingMode.HALF_UP);
	}
	
	private BigDecimal calculaValorMaximo(final Lancamento lancamento) {
		if(max == null) {
			return lancamento.getValor();
		}
		return max.max(lancamento.getValor());
	}

	public BigDecimal getSoma() {
		return soma;
	}

	public void setSoma(BigDecimal soma) {
		this.soma = soma;
	}

	public BigDecimal getMin() {
		return min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
	}

	public BigDecimal getMax() {
		return max;
	}

	public void setMax(BigDecimal max) {
		this.max = max;
	}

	public BigDecimal getMedia() {
		return media;
	}

	public void setMedia(BigDecimal media) {
		this.media = media;
	}

	public Long getQtde() {
		return qtde;
	}

	public void setQtde(Long qtde) {
		this.qtde = qtde;
	}
}

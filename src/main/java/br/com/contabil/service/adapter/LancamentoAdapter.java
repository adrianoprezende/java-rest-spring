package br.com.contabil.service.adapter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.contabil.entity.Lancamento;
import br.com.contabil.rest.dto.LancamentoDTO;

public class LancamentoAdapter {
	
	public static final Logger logger = LoggerFactory.getLogger(LancamentoAdapter.class);
	
	private static final String FORMATO_DATA = "yyyyMMdd";
	private SimpleDateFormat dateFormat = new SimpleDateFormat();
	private DecimalFormat decimalFormat = new DecimalFormat();
	
	public LancamentoAdapter() {
		super();
		dateFormat = new SimpleDateFormat(FORMATO_DATA);
		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setMinimumFractionDigits(0);
		decimalFormat.setGroupingUsed(false);
	}

	public Lancamento toEntity(LancamentoDTO dto) throws Exception {
		if(dto == null) {
			return null;
		}
		
		Lancamento lancamento = new Lancamento();
		lancamento.setContaContabil(Long.valueOf(dto.getContaContabil()));
		lancamento.setValor(new BigDecimal(dto.getValor()));
		
		try {
			lancamento.setData(dateFormat.parse(dto.getData()));
		} catch (ParseException e) {
			logger.error("Erro ao realizar parse de data");
			throw new Exception(e);
		}
		
		return lancamento;
	}
	
	public LancamentoDTO toDto(Lancamento lancamento) throws Exception {
		if(lancamento == null) {
			return null;
		}
		
		LancamentoDTO dto = new LancamentoDTO();
		dto.setContaContabil(String.valueOf(lancamento.getContaContabil()));
		dto.setValor(decimalFormat.format(lancamento.getValor()));
		dto.setData(dateFormat.format(lancamento.getData()));
		return dto;
	}
	
	public List<LancamentoDTO> listEntityToDto(List<Lancamento> listEntity) throws Exception {
		List<LancamentoDTO> lisDto = new ArrayList<>();
		for(Lancamento entity : listEntity) {
			lisDto.add(toDto(entity));
		}
		return lisDto;
	}

}

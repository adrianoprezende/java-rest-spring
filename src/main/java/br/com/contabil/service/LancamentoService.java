package br.com.contabil.service;

import java.util.List;
import java.util.Optional;

import br.com.contabil.rest.dto.EstatisticasDTO;
import br.com.contabil.rest.dto.LancamentoDTO;

public interface LancamentoService {
	
	String salvar(LancamentoDTO lancamento) throws Exception;
	
	LancamentoDTO buscarPorId(String id) throws Exception;
	
	List<LancamentoDTO> buscarPorContaContabil(Optional<Long> conta) throws Exception;
	
	EstatisticasDTO buscarEstatisticas(Optional<Long> conta) throws Exception;

}

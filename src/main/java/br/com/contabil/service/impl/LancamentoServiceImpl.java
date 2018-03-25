package br.com.contabil.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.contabil.dao.LancamentoDAO;
import br.com.contabil.entity.Lancamento;
import br.com.contabil.rest.dto.EstatisticasDTO;
import br.com.contabil.rest.dto.LancamentoDTO;
import br.com.contabil.service.LancamentoService;
import br.com.contabil.service.adapter.LancamentoAdapter;

@Component
public class LancamentoServiceImpl implements LancamentoService {
	
	public static final Logger logger = LoggerFactory.getLogger(LancamentoServiceImpl.class);
	
	LancamentoAdapter adapter = new LancamentoAdapter();

	public String salvar(LancamentoDTO lancamentoDto) throws Exception {
		
		Lancamento lancamentoEntity;
		try {
			lancamentoEntity = adapter.toEntity(lancamentoDto);
			LancamentoDAO.getInstance().salvarEmMemoria(lancamentoEntity);
		} catch (Exception e) {
			logger.error("Erro ao salvar lancamento", e);
			throw e;
		}
		return lancamentoEntity.getId().toString();
	}

	public LancamentoDTO buscarPorId(String id) throws Exception {
		LancamentoDTO dto;

		try {
			Lancamento entity = LancamentoDAO.getInstance().buscarPorId(id);
			dto = adapter.toDto(entity);
		} catch (Exception e) {
			logger.error("Erro ao buscar lancamento por id", e);
			throw e;
		}
		return dto;
	}

	public List<LancamentoDTO> buscarPorContaContabil(Optional<Long> conta) throws Exception {
		List<LancamentoDTO> listDto = new ArrayList<>();

		try {
			List<Lancamento> listEntity = LancamentoDAO.getInstance().buscarPorContaContabil(conta);
			listDto = adapter.listEntityToDto(listEntity);
		} catch (Exception e) {
			logger.error("Erro ao buscar lancamento por conta contabil", e);
			throw e;
		}
		return listDto;
	}
	
	public EstatisticasDTO buscarEstatisticas(Optional<Long> conta) throws Exception {
		EstatisticasDTO estatistica = null;

		try {
			List<Lancamento> listEntity = LancamentoDAO.getInstance().buscarPorContaContabil(conta);
			if(listEntity != null && !listEntity.isEmpty()) {
				estatistica = new EstatisticasDTO(listEntity);
			}
		} catch (Exception e) {
			logger.error("Erro ao calcular Estatisticas.", e);
			throw e;
		}
		return estatistica;
	}

}

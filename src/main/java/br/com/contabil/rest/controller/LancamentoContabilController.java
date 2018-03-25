package br.com.contabil.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.contabil.rest.dto.EstatisticasDTO;
import br.com.contabil.rest.dto.LancamentoDTO;
import br.com.contabil.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos-contabeis")
public class LancamentoContabilController {
	
	public static final Logger logger = LoggerFactory.getLogger(LancamentoContabilController.class);
	
	@Autowired
	private LancamentoService service;
	
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> cadastrarLancamento(@RequestBody LancamentoDTO entrada, UriComponentsBuilder ucBuilder) {
        logger.info("Registrando Lancamento Contabil : {}", entrada);
		Map<String, String> mapRetorno = new HashMap<>();
        
        try {
        	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        	Validator validator = factory.getValidator();
        	
        	Set<ConstraintViolation<LancamentoDTO>> violations = validator.validate(entrada);
        	
        	for (ConstraintViolation<LancamentoDTO> violation : violations) {
        		logger.error("Erro de validacao de dados de entrada.", violation.getMessage());
        		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        	}
        	
			String id = service.salvar(entrada);
			mapRetorno.put("ID", id);
		} catch (Exception e) {
			logger.error("Erro ao salvar lancamento contabil.", e);
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
        return new ResponseEntity<>(mapRetorno, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> buscarLancamento(@PathVariable("id") String id) {
        logger.info("Consultando Lancamento Contabil : {}", id);
		
    	LancamentoDTO lancamentoDto = null;
		try {
			lancamentoDto = service.buscarPorId(id);
	    	if(lancamentoDto == null) {
	        	return ResponseEntity.notFound().build();
	        } 
        	
		} catch (Exception e) {
			logger.error("Erro ao buscar lancamento contabil. ID"+id, e);
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
        return new ResponseEntity<>(lancamentoDto, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> buscarLancamentoPorContaContabil(@RequestParam("contaContabil") Optional<Long> contaContabil) {
        logger.info("Consultando Lancamento Contabil por ContaContabil: {}", contaContabil);
		
    	List<LancamentoDTO> listLancamentoDto = new ArrayList<>();
		try {
			listLancamentoDto = service.buscarPorContaContabil(contaContabil);
	    	if(listLancamentoDto.isEmpty()) {
	        	return ResponseEntity.notFound().build();
	        } 
        	
		} catch (Exception e) {
			logger.error("Erro ao buscar lancamento contabil por contaContabil. ID"+contaContabil, e);
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
        return new ResponseEntity<>(listLancamentoDto, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/_stats", method = RequestMethod.GET)
    public ResponseEntity<?> buscarEstatisticas(@RequestParam("contaContabil") Optional<Long> contaContabil) {
        logger.info("Consultando Estatisticas: {}", contaContabil);
		
    	EstatisticasDTO estatistica = null;
		try {
			estatistica = service.buscarEstatisticas(contaContabil);
	    	if(estatistica == null) {
	        	return ResponseEntity.notFound().build();
	        } 
        	
		} catch (Exception e) {
			logger.error("Erro ao calcular Estatisticas.", e);
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
        return new ResponseEntity<>(estatistica, HttpStatus.OK);
    }

}

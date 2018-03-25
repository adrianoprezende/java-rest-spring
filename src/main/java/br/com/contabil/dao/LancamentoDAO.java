package br.com.contabil.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import br.com.contabil.entity.Lancamento;

public class LancamentoDAO {
	
private static LancamentoDAO instance;

private Map<String, Lancamento> lancamentos = new ConcurrentHashMap<String, Lancamento>();
    
    private LancamentoDAO(){}
    
    public static LancamentoDAO getInstance(){
        if(instance == null){
            instance = new LancamentoDAO();
        }
        return instance;
    }

	public Map<String, Lancamento> getLancamentos() {
		return lancamentos;
	}
	
	public Lancamento buscarPorId(String id) {
		if(lancamentos.containsKey(id)) {
			return lancamentos.get(id);
		}
		return null;
	}

	public void salvarEmMemoria(Lancamento lancamento) {
		this.lancamentos.put(lancamento.getId(), lancamento);
	}
	
	public List<Lancamento> buscarPorContaContabil(Optional<Long> contaContabil) {
		if(!contaContabil.isPresent()) {
			return new ArrayList<Lancamento>(this.lancamentos.values());
		}
		
		List<Lancamento> listaLancamento = new ArrayList<>();
		for(Lancamento lancamento: this.lancamentos.values()) {
			if(contaContabil.get().equals(lancamento.getContaContabil())) {
				listaLancamento.add(lancamento);
			}
		}
		
		return listaLancamento;
	}

}

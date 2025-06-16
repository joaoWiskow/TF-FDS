package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDeVendas;


@Component
public class BuscaOrcamentoUC {
        
    private ServicoDeVendas servicoDeVendas;
        
    @Autowired
    public BuscaOrcamentoUC(ServicoDeVendas servicoDeVendas){
            this.servicoDeVendas = servicoDeVendas;
        }
    
    public OrcamentoModel run(long idOrcamento){
        return servicoDeVendas.buscaOrcamento(idOrcamento);
        }
    }

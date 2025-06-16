package com.bcopstein.sistvendas.persistencia;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface EstoqueJPA_ItfRep extends CrudRepository<ItemDeEstoque,Long>{
    @SuppressWarnings("null")
    List<ItemDeEstoque> findAll();
    ItemDeEstoque findById(long id);
}

package com.mak.baca.boot.dao;

import org.springframework.data.repository.CrudRepository;

import com.mak.baca.boot.model.Shoe;

public interface ShoeRepo extends CrudRepository<Shoe, Integer> {

}

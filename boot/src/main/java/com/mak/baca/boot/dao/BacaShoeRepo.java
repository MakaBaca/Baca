package com.mak.baca.boot.dao;

import org.springframework.data.repository.CrudRepository;

import com.mak.baca.boot.model.BacaShoe;
import com.mak.baca.boot.model.BacaShoeAndHandPK;

public interface BacaShoeRepo extends CrudRepository<BacaShoe, BacaShoeAndHandPK>{

}

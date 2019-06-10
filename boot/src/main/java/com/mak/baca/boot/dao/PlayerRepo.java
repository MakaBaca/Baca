package com.mak.baca.boot.dao;

import org.springframework.data.repository.CrudRepository;

import com.mak.baca.boot.model.Player;

public interface PlayerRepo extends CrudRepository<Player, Integer>{

}
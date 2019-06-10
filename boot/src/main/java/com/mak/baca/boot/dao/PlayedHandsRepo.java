package com.mak.baca.boot.dao;

import org.springframework.data.repository.CrudRepository;

import com.mak.baca.boot.model.PlayedHandPK;
import com.mak.baca.boot.model.PlayedHands;

public interface PlayedHandsRepo extends CrudRepository<PlayedHands, PlayedHandPK>{

}

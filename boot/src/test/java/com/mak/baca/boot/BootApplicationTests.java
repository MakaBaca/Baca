package com.mak.baca.boot;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mak.baca.boot.dao.PPSCustomRepo;
import com.mak.baca.boot.model.PlayerPlayedShoes;
import com.mak.baca.boot.model.PlayerScore;
import com.mak.baca.boot.model.PlayerShoe;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootApplicationTests {
	
	@Autowired
	PPSCustomRepo repo;
	
	@Test
	public void contextLoads() {
	}
	
	//@Test
	public void checkDistinctShoe(){
		List<PlayerShoe> list = repo.findDistictPlayerShoe(100);
		list.forEach(ps -> {
			System.out.println(ps.toString());
		});
	}
	
	//@Test
	public void checkPlayerPlayedShoes(){
		List<PlayerPlayedShoes> list = repo.findSpecificPlayedShoes(1055, 100);
		list.forEach(pps -> {
			System.out.println(pps.toString());
		});
	}
	
	//@Test
	public void checkPlayerPlayedShoesScore(){
		List<PlayerShoe> list = repo.findDistictPlayerShoeAndScore(100);
		list.forEach(ps -> {
			System.out.println(ps.toString());
		});
	}
	
	@Test
	public void checkRefreshScore(){
		PlayerScore ps = repo.refreshPlayerScore(100);
		System.out.println(ps);
	}

}

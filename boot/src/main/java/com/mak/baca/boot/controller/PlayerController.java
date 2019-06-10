package com.mak.baca.boot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mak.baca.boot.dao.PPSCustomRepo;
import com.mak.baca.boot.dao.PlayerPlayedShoeRepo;
import com.mak.baca.boot.dao.PlayerRepo;
import com.mak.baca.boot.model.Player;
import com.mak.baca.boot.model.PlayerPlayedShoes;
import com.mak.baca.boot.model.PlayerScore;
import com.mak.baca.boot.model.PlayerShoe;
import com.mak.casino.Baccarat.Outcome;

import application.mak.btc.BtcScoreCalc;
import application.mak.btc.FiveDScoreCard;

@Controller
public class PlayerController {
	
	@Autowired
	PlayerRepo repo;
	
	/*@Autowired
	PlayerPlayedShoeRepo ppsRepo;*/
	
	@Autowired
	PPSCustomRepo customRepo;
	
	@RequestMapping("/createPlayer")
	public ModelAndView createPlayer(String name){
		Player p = new Player();
		p.setName(name);
		p.setBalance(30);
		p.setInvested(30);
		repo.save(p);
		Iterable<Player> players = repo.findAll();
		return new ModelAndView("player.jsp","players",players);
	}
	
	@RequestMapping("/refreshScore/{playerId}")
	public ModelAndView refreshPlayerScore(@PathVariable("playerId")int playerId){
		PlayerScore ps = customRepo.refreshPlayerScore(playerId);
		Player p = repo.findById(playerId).get();
		p.setWinings(ps.getAmountWon());
		p.setInvested(ps.getAmountInvested());
		repo.save(p);
		Iterable<Player> players = repo.findAll();
		return new ModelAndView("/player.jsp","players",players);
	}
	
	@RequestMapping("/")
	public ModelAndView listPlayers(){
		Iterable<Player> players = repo.findAll();
		return new ModelAndView("player.jsp","players",players);
	}
	
	@RequestMapping("/playedShoes/{playerId}")
	public ModelAndView listPlayedShoes(@PathVariable("playerId")int playerId){
		Iterable<PlayerShoe> result = customRepo.findDistictPlayerShoeAndScore(playerId);
		return new ModelAndView("/playesShoes.jsp","playedShoes",result);
	}
	
	@RequestMapping("/showPlayedShoes/{shoeNumber}/{playerId}")
	public ModelAndView showSpecificShoe(@PathVariable("shoeNumber") int shoeNumber, @PathVariable("playerId")int playerId){
		List<PlayerPlayedShoes> result = customRepo.findSpecificPlayedShoes(shoeNumber, playerId);
		return new ModelAndView("/showShoe.jsp","show",result);
	}
	
	@RequestMapping("/showInBtcMode/{shoeNumber}/{playerId}")
	public ModelAndView showInBtcMode(@PathVariable("shoeNumber") int shoeNumber, @PathVariable("playerId")int playerId){
		List<PlayerPlayedShoes> result = customRepo.findSpecificPlayedShoes(shoeNumber, playerId);
		List<FiveDScoreCard> btcList = new ArrayList<>();
		BtcScoreCalc calc = new BtcScoreCalc(false);
		result.forEach(pps -> {
			Outcome o = pps.getOutcome();
			Long betAmount = pps.getBetAmount();
			Boolean didWin = pps.getDidWin();
			FiveDScoreCard card = null;
			if(o != Outcome.tie){
				if(betAmount == null){
					calc.recordOutcome(o, 0, false);
					card = calc.get5DScoreCard();
				}else if(betAmount != null && didWin){
					if(o == Outcome.player)
						calc.recordOutcome(o, betAmount.intValue(), true);
					else 
						calc.recordOutcome(o, betAmount.intValue(), false);
					card = calc.get5DScoreCard();
					if(betAmount == 0){
						if(o == Outcome.player)
							card.setPlayer(card.getPlayer()+"  0");
						else
							card.setBanker(card.getBanker()+"  0") ;
					}
				}else if(betAmount != null && !didWin){
					if(o == Outcome.player)
						calc.recordOutcome(o, betAmount.intValue(), false);
					else 
						calc.recordOutcome(o, betAmount.intValue(), true);
					card = calc.get5DScoreCard();
					if(betAmount == 0){
						if(o == Outcome.player)
							card.setBanker("0");
						else
							card.setPlayer("0") ;
					}
				}
				
				btcList.add(card);
			}
		});
		return new ModelAndView("/showInBtcMode.jsp","btcList",btcList);
	}
	
	@RequestMapping("/showResult/{playerId}")
	public ModelAndView displayPlayerPerfomace(@PathVariable("playerId") int playerId){
		return new ModelAndView("/result.html","resultMap",customRepo.findPlayerPerfomance(playerId));
	}
}

package com.aworks.atm.webservices.restfulwebservices.controller;

import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aworks.atm.webservices.restfulwebservices.dao.AtmDaoService;

@RestController
public class AtmController {

	@Autowired
	AtmDaoService atmService;
	
	@PostMapping("/get-denominations")
	public TreeMap<Integer, Integer> getDenominations(@RequestBody Integer atmId) {
		TreeMap<Integer, Integer> denoMap =  atmService.getDenominations(atmId);
		return denoMap;
	}
	
	@PostMapping("/get-atm-balance")
	public Integer getAtmBalance(@RequestBody Integer atmId) {
		return atmService.getBalance(atmId);
	}
}

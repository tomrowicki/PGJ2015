package pl.pgj2015.controller;

import java.util.Map;

public class ControllerConfig  {

	private Map<PlayerNumber, Map<Integer, ActionKey>> config;  
	
	public Map<Integer, ActionKey> getConfigForPlayer(PlayerNumber player){
	
		return config.get(player);
	}
	
	public void setConfigurationForPlayer(PlayerNumber player){

		}
	}	

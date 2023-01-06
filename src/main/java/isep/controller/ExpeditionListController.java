package isep.controller;

import isep.model.DistributionNetwork;
import isep.model.ExpeditionList;
import isep.shared.exceptions.InvalidHubException;
import isep.shared.exceptions.InvalidOrderException;
import isep.shared.exceptions.UndefinedHubsException;

public class ExpeditionListController {

    private DistributionNetwork network;

    public ExpeditionListController(){
        network = App.getInstance().getCompany().getDistributionNetwork();
    }

    public ExpeditionList getExpeditionList(Integer day) throws InvalidOrderException, InvalidHubException, UndefinedHubsException{
        if(!network.hasHub())
            return null;
        
        return network.getExpeditionList(day); 
    }
    
}

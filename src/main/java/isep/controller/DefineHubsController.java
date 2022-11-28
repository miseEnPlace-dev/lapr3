package isep.controller;

import java.util.List;

import isep.model.DistributionNetwork;
import isep.model.Enterprise;
import isep.shared.exceptions.InvalidNumberOfHubsException;

public class DefineHubsController {

    private DistributionNetwork network;
    private int NumberOfHubsToDefine;

    public DefineHubsController(DistributionNetwork network, int numberOfHubs){
        this.network = network;
        this.NumberOfHubsToDefine = numberOfHubs;
    }

    public List<Enterprise> defineHubs() throws InvalidNumberOfHubsException{
        return network.defineHubs(NumberOfHubsToDefine);
    }
    
}

package isep.model;

public class Hubs {
    // Number of Hubs to find
    int N;
    DistributionNetwork distN;

    public Hubs(int n, DistributionNetwork dn){
        if(n <= 0) throw(new NumberFormatException("N cannot be lower than 1!"));
        if(dn == null) throw(new NullPointerException("Distribution Network cannot be null!"));

        this.N = n;
        this.distN = dn;
    }

}

package isep.model;

public class Enterprise extends Entity {

    private boolean isHub;

    public Enterprise(String id, double latitude, double longitude, String localizationId) {
        super(id, latitude, longitude, localizationId);
        this.isHub = false;
    }
    
    public void makeHub(){
        this.isHub = true;
    }

    public void unMakeHub(){
        this.isHub = false;
    }

    public boolean isHub(){
        return this.isHub;
    }

}

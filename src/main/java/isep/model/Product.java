package isep.model;

import isep.shared.exceptions.InvalidProductNameException;

public class Product {

    private String name;

    public Product(String name) throws InvalidProductNameException{
        setName(name);
    }
    
    public String getName(){
        return this.name;
    }

    private void setName(String name) throws InvalidProductNameException{
        if(name == "" || name == null){
            throw new InvalidProductNameException();
        }   
        this.name = name;
    }
    
}

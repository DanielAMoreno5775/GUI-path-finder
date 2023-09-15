/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Daniel Moreno
 */

public class Government{
    protected String govName;
    
    //constructor
    public Government (String name) {
        govName = name;
    }
    
    public String getName(){
        return govName;
    }
    
    //toString that overrides default version
    @Override
    public String toString () {
        return String.format("%s",govName);
    }
}

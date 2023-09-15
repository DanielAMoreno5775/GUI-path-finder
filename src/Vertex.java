/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Daniel Moreno
 * @param <E> for data within the vertex
 */
public class Vertex <E extends Comparable & DistanceCalculation> implements Comparable<Vertex>{
    private E data;
   
    public Vertex(E vertexData) {
        data = vertexData;
    }
    
    public E getData () {
        return data;
    }
    
    public double distanceCalc(Vertex other){
        return Math.abs(data.compareDistance(other.data));
    }

    @Override
    public int compareTo(Vertex other) {
        return this.data.compareTo(other.data);
    }
    
    @Override
    public String toString () {
        return String.format("%s",data);
    }
}

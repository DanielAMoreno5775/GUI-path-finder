/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Daniel Moreno
 * @param <N> for vertices
 */
public class Edge <N> {
    private N source;
    private N destination;
    private double weight;
    
    public Edge(N source, N destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public N getDestination() {
        return destination;
    }

    public N getSource() {
        return source;
    }
    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "From " + source + " to " + destination + " (Weight: " + weight + ")";
    }
}

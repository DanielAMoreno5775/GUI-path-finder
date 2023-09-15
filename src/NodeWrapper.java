
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Daniel Moreno
 * @param <N> for vertices
 */
class NodeWrapper<N extends Comparable> implements Comparable<NodeWrapper> {
    private N node;
    private double totalDistance;
    private NodeWrapper<N> predecessor;

    public NodeWrapper(N node, double totalDistance, NodeWrapper<N> predecessor) {
        this.node = node;
        this.totalDistance = totalDistance;
        this.predecessor = predecessor;
    }

    public N getNode() {
        return node;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setPredecessor(NodeWrapper<N> predecessor) {
        this.predecessor = predecessor;
    }

    public NodeWrapper<N> getPredecessor() {
        return predecessor;
    }

    @Override
    public int compareTo(NodeWrapper other) {
        if (this.node == null && other.node == null) {
            return 0;
        } else if (this.node == null && other.node != null) {
            return -1;
        } else if (this.node != null && other.node == null) {
            return 1;
        }
        
        Double thisDistance = this.totalDistance;
        Double otherDistance = other.totalDistance;
        Integer comparison = thisDistance.compareTo(otherDistance);
        return comparison;
    }
}
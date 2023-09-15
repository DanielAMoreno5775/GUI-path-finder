import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Daniel Moreno
 * @param <N> for vertices
 * @param <D> for data within vertices
 */

public class DijkstraWithPriorityQueue <N extends Comparable, D extends Comparable<? super D>>{
    private int numOfPaths; //tracks the number of vertices that were checked
    private List<N> pathsConsidered;
    
    public DijkstraWithPriorityQueue(){
        numOfPaths = 0;
        pathsConsidered = null;
    }
    
    //Finds the shortest path from source to target
    public List<N> findShortestPath(Graph<N, D> graph, N source, N target) {
        numOfPaths = 0;
        Map<N, NodeWrapper<N>> nodeWrappers = new HashMap<>();
        PriorityQueue<NodeWrapper<N>> queue = new PriorityQueue<>(59);
        Set<N> shortestPathFound = new HashSet<>();
        pathsConsidered = new ArrayList<>();

        // Add source to queue
        NodeWrapper<N> sourceWrapper = new NodeWrapper<>(source, 0, null);
        nodeWrappers.put(source, sourceWrapper);
        queue.enqueue(sourceWrapper);

        while (!queue.isEmpty()) {
            NodeWrapper<N> nodeWrapper = queue.dequeue();
            N node = nodeWrapper.getNode();
            shortestPathFound.add(node);
            
            //only add a node to the list of paths considered if it is not the source node
            if (node.compareTo(source) != 0) {
                pathsConsidered.add(node);
                numOfPaths++;
            }
            
            // Have we reached the target? --> Build and return the path
            if (node.compareTo(target) == 0) {
                return buildPath(nodeWrapper);
            }

            // Iterate over all neighbors
            Set<Edge> neighbors = new HashSet<> (graph.getEdgesFrom(node));
            for (Edge edge : neighbors) {
                N neighbor = (N) edge.getDestination();
                // Ignore neighbor if already in path
                if (shortestPathFound.contains(neighbor)) {
                    continue;
                }

                // Calculate total distance from start to neighbor via current node
                double distance = edge.getWeight();
                if (distance > 300) {
                    continue;
                }
                double totalDistance = nodeWrapper.getTotalDistance() + distance;

                // check whether neighbor not yet discovered
                NodeWrapper<N> neighborWrapper = nodeWrappers.get(neighbor);
                if (neighborWrapper == null) {
                    neighborWrapper = new NodeWrapper<>(neighbor, totalDistance, nodeWrapper);
                    nodeWrappers.put(neighbor, neighborWrapper);
                    queue.enqueue(neighborWrapper);
                }

                // neighbor discovered, but total distance via current node is shorter? --> Update total distance and predecessor
                else if (totalDistance < neighborWrapper.getTotalDistance()) {
                    neighborWrapper.setTotalDistance(totalDistance);
                    neighborWrapper.setPredecessor(nodeWrapper);

                    // The position in the PriorityQueue won't change automatically; remove and reinsert the node
                    queue.removeValue(neighborWrapper);
                    queue.enqueue(neighborWrapper);
                }
            }
        }
        // All reachable nodes were visited but the target was not found
        System.out.println("target not found");
        return null;
    }

    //follows the found path backward from target -> start, write them into a list, and reverse them
    private List<N> buildPath(NodeWrapper<N> nodeWrapper) {
        List<N> path = new ArrayList<>();
        while (nodeWrapper != null) {
            path.add(nodeWrapper.getNode());
            nodeWrapper = nodeWrapper.getPredecessor();
        }
        Collections.reverse(path);
        return path;
    }
    
    public int getNumOfPathsConsidered(){
        return numOfPaths;
    }
    
    public List<N> getPathsConsidered () {
        return pathsConsidered;
    }
}


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Daniel Moreno
 * @param <N> for vertices
 * @param <E> for data within vertices
 */
public class Graph <N, E extends Comparable<? super E>> {
    private int numOfVertices;
    
    // Maps a vertex to an ArrayList of all edges that start from that vertex
    private HashMap<N, ArrayList<Edge>> fromEdges;
   
    // Maps a vertex to an ArrayList of all edges that go to that vertex
    private HashMap<N, ArrayList<Edge>> toEdges;
    
    public Graph() {
        numOfVertices = 0;
        fromEdges = new HashMap<>();
        toEdges = new HashMap<>();
    }
   
    public N addVertex(N newVertex) {    
        // Every vertex must exist as a key in both maps
        fromEdges.put(newVertex, new ArrayList<>());
        toEdges.put(newVertex, new ArrayList<>());
        numOfVertices++;
      
        return newVertex;
    }
   
    public Edge addDirectedEdge(N fromVertex, N toVertex) {
        // Use 1.0 as the default edge weight
        return addDirectedEdge(fromVertex, toVertex, 1.0);
    }
   
    public Edge addDirectedEdge(N fromVertex, N toVertex, double weight) {
        // Don't add the same edge twice
        if (hasEdge(fromVertex, toVertex)) {
            return null;
        }
      
        // Create the Edge object
        Edge newEdge = new Edge(fromVertex, toVertex, weight);
      
        // Add the edge to the appropriate list in both maps
        fromEdges.get(fromVertex).add(newEdge);
        toEdges.get(toVertex).add(newEdge);
      
        return newEdge;
    }
   
    public Edge[] addUndirectedEdge(N vertexA, N vertexB) {
        // Use 1.0 as the default edge weight
        return addUndirectedEdge(vertexA, vertexB, 1.0);
    }
   
    public Edge[] addUndirectedEdge(N vertexA, N vertexB, double weight) {
        Edge edge1 = addDirectedEdge(vertexA, vertexB, weight);
        Edge edge2 = addDirectedEdge(vertexB, vertexA, weight);
        Edge[] result = { edge1, edge2 };
        return result;
    }
   
    // Returns the collection of edges with the specified fromVertex
    public Collection<Edge> getEdgesFrom(N fromVertex) {
        return fromEdges.get(fromVertex);
    }
   
    // Returns the collection of edges with the specified toVertex
    public Collection<Edge> getEdgesTo(N toVertex) {
        return toEdges.get(toVertex);
    }
   
    // Returns the collection of all of this graph's vertices
    public Collection<N> getVertices() {
        return fromEdges.keySet();
    }

    // Returns true if this graph has an edge from fromVertex to toVertex
    public boolean hasEdge(N fromVertex, N toVertex) {
        // fromVertex is not in this graph
        if (!fromEdges.containsKey(fromVertex)) {
            return false;
        }
      
        // Search the list of edges for an edge that goes to toVertex
        ArrayList<Edge> edges = fromEdges.get(fromVertex);
            for (Edge edge : edges) {
            if (edge.getDestination() == toVertex) {
                return true;
            }
        }
      
        return false;
    }
    
    @Override
    public String toString () {
        String result = "";
        for (N vertex : this.getVertices()) {
            result += vertex;
            result += ", ";
        }
        return result;
    }
    
    public void printGraph () {
        Collection<N> vertexList = this.getVertices();
        for (N vertex : vertexList) {
            Collection<Edge> edgeList = this.getEdgesFrom(vertex);
            for (Edge edge : edgeList) {
                System.out.println(edge);
            }
        }
    }
}
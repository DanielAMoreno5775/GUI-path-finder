
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Daniel Moreno
 * @param <E> for data within the priority queue
 */
public class PriorityQueue <E extends Comparable<? super E>> {
    private MinHeap<E> list;
    public ArrayList<E> visited = new ArrayList<>();
    
    public PriorityQueue(){
        list = new MinHeap<>(10);
    }
    
    public PriorityQueue(int capacity){
        list = new MinHeap<>(capacity);
    }
    
    public void enqueue(E value) {
        list.add(value); 
        visited.add(value);
    }
    
    public E dequeue() {
        return list.remove();
    }
    
    public E removeValue(E value){
        return list.removeValue(value);
    }
    
    public E peek(){
        return list.viewMin();
    }
    
    public boolean isEmpty(){
        return list.isEmpty();
    }
    
    public int getLength(){
        return list.size();
    }
}

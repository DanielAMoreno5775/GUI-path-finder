/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Daniel Moreno
 * @param <E> for data within the heap
 */


public class MinHeap <E extends Comparable<? super E>> {
    private int capacity;
    private E[] heapArray;
    private int size;
 
    public MinHeap () {
        capacity = 10;
        heapArray = (E[]) new Comparable[capacity];
        size = 0;
    }
 
    public MinHeap (int cap) {
        this.capacity = cap;
        heapArray = (E[]) new Comparable[capacity];
        size = 0;
    }
 
    // add new element into heap
    public void add(E value) {
        if (size >= heapArray.length - 1) {
            heapArray = this.resize();
        }
 
        // place element into heap at bottom (right most)
        heapArray[size] = value;
        size++;
 
        bubbleUp();
    }
 
    // bubble up the last node with its parent until they are in the order of max heap
    private void bubbleUp() {
        int index = this.size - 1;  // last node (right most)
 
        while (hasParent(index) && (parent(index).compareTo(heapArray[index]) > 0)) {
            // parent and child are out of order; swap them
            swap(index, parentIndex(index));
            index = parentIndex(index);
        }
    }
 
    // remove and return the maximum element in the heap
    public E remove() {
        if (this.isEmpty()) {
            return null;
        }
        // get the root, which is the maximum value
        E result = viewMin();
 
        // move the last leaf to root
        heapArray[0] = heapArray[size - 1];
        heapArray[size - 1] = null;
        size--;
 
        bubbleDown();
 
        return result;
    }
    
    public E removeValue (E value) {
        if (this.isEmpty() || value == null) {
            return null;
        }
        
        //perform linear search to find the value
        for (int i = 0; i < size; i += 1) {
            if (heapArray[i].compareTo(value) == 0) {
                if (size == 1) { //removed the last element
                    heapArray[i] = null;
                    size -= 1;
                } else {
                    //transfer last element to current location and shrink the array's current size
                    E moved  = heapArray[size - 1];
                    heapArray[size - 1] = null;
                    size -= 1;
                    
                    siftDown(i, moved, heapArray, size);
                    if (heapArray[i] == moved) {
                        siftUp(i, moved, heapArray);
                        if (heapArray[i] != moved) {
                            return moved;
                        }
                    }
                }
            }
        }
        
        return null;
    }
 
    // bubble down the new root to proper position to maintain the order of max heap
    private void bubbleDown() {
        // root
        int index = 0;
 
        // heap is complete tree, so it's safe to check left child first
        while (hasLeftChild(index)) {
            int biggerChild = leftIndex(index);
 
            // find the smaller child
            if (hasRightChild(index) && heapArray[leftIndex(index)].compareTo((heapArray[rightIndex(index)])) > 0) {
                biggerChild = rightIndex(index);
            }
 
            if (heapArray[index].compareTo(heapArray[biggerChild]) < 0) {
                break;
            } else {
                // parent and child are out of order; swap them
                swap(index, biggerChild);
                index = biggerChild;
            }
        }
    }
    
    //based on the code from the default PriorityQueue in Java
    private static <T> void siftDown(int k, T x, Object[] es, int n) {
        // assert n > 0;
        Comparable<? super T> key = (Comparable<? super T>)x;
        int half = n >>> 1;           // loop while a non-leaf
        while (k < half) {
            int child = (k << 1) + 1; // assume left child is least
            Object c = es[child];
            int right = child + 1;
            if (right < n &&
                ((Comparable<? super T>) c).compareTo((T) es[right]) > 0)
                c = es[child = right];
            if (key.compareTo((T) c) <= 0)
                break;
            es[k] = c;
            k = child;
        }
        es[k] = key;
    }
    
    //based on the code from the default PriorityQueue in Java
    private static <T> void siftUp(int k, T x, Object[] es) {
        Comparable<? super T> key = (Comparable<? super T>) x;
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            Object e = es[parent];
            if (key.compareTo((T) e) >= 0)
                break;
            es[k] = e;
            k = parent;
        }
        es[k] = key;
    }
 
    // get the root without removing it from heap
    public E viewMin() {
        if (this.isEmpty()) {
            return null;
        }
 
        return heapArray[0];
    }
    
    // get the current size
    public int size() {
        return size;
    }
    
    //calculate the left index
    private int leftIndex(int i) {
        return 2 * i + 1;
    }
 
    //calculate the right index
    private int rightIndex(int i) {
        return 2 * i + 2;
    }
 
    //calculate and get the parent
    private E parent(int i) {
        return heapArray[parentIndex(i)];
    }
 
    //calculate the parent's index
    private int parentIndex(int i) {
        return (i - 1) / 2;
    }
    
    //verification methods 
    public boolean isEmpty() {
        return size == 0;
    }
    
    private boolean hasParent(int i) {
        return i > 0;
    }
    
    private boolean hasLeftChild(int i) {
        return leftIndex(i) <= size - 1;
    }
 
    private boolean hasRightChild(int i) {
        return rightIndex(i) <= size - 1;
    }    
 
    //resize the array
    private E[] resize() {
        // Allocate new array and copy existing items
        int newSize = capacity * 2;
        E[] newArray = (E[]) new Comparable[newSize];
        for (int i = 0; i < size; i++) {
            newArray[i] = heapArray[i];
        }
         
        // Assign new array and allocation size
        capacity = newSize;
        return newArray;
    }
 
    //swap two elements in an array
    private void swap(int index1, int index2) {
        E tmp = heapArray[index1];
        heapArray[index1] = heapArray[index2];
        heapArray[index2] = tmp;
    }
}
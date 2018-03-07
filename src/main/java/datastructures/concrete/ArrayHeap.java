package datastructures.concrete;


import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;

/**
 * See IPriorityQueue for details on what each method must do.
 */
public class ArrayHeap<T extends Comparable<T>> implements IPriorityQueue<T> {
    // See spec: you must implement a implement a 4-heap.
    private static final int NUM_CHILDREN = 4;
    private static final int CAPACITY = 10;

    // You MUST use this field to store the contents of your heap.
    // You may NOT rename this field: we will be inspecting it within
    // our private tests.
    private T[] heap;

    // Feel free to add more fields and constants.
    private int num;

    public ArrayHeap() {
        num = 0;
        heap = makeArrayOfT(CAPACITY);
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * elements of type T.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private T[] makeArrayOfT(int size) {
        // This helper method is basically the same one we gave you
        // in ArrayDictionary and ChainedHashDictionary.
        //
        // As before, you do not need to understand how this method
        // works, and should not modify it in any way.
        return (T[]) (new Comparable[size]);
    }

    @Override
    public T removeMin() {
        if (num == 0) {
            throw new EmptyContainerException();
        }
        T value = heap[0];
        heap[0] = heap[num - 1];
        int parent = 0;
        int child;
        int min = parent;
        num--;
        do {
            swap(parent, min);
            parent = min;
            child = parent * NUM_CHILDREN + 1;
            for (int i = child; i < child + NUM_CHILDREN; i++) {
                if (i < num && heap[min].compareTo(heap[i]) > 0) {
                    min = i;
                }
            }
        } while (heap[parent].compareTo(heap[min]) > 0);
        return value;
    }

    private void swap(int parent, int min) {
        T temp = heap[parent];
        heap[parent] = heap[min];
        heap[min] = temp;
    }

    @Override
    public T peekMin() {
        if (num == 0) {
            throw new EmptyContainerException();
        }
        return heap[0];
    }

    @Override
    public void insert(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (num == heap.length) {
            // resize
            T[] newHeap = makeArrayOfT(heap.length * 2);
            for (int i = 0; i < num; i++) {
                newHeap[i] = heap[i];
            }
            heap = newHeap;
        }
        heap[num] = item;
        int parent = (num - 1) / NUM_CHILDREN;
        int child = num;
        if (num != 0) {
            while (heap[child].compareTo(heap[parent]) < 0) {
                swap(parent, child);
                child = parent;
                parent = (child - 1) / NUM_CHILDREN;
            }
        }
        num++;
    }

    @Override
    public int size() {
        return num;
    }
}

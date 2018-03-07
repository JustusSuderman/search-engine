package datastructures.sorting;

import misc.BaseTest;
import misc.Searcher;
import misc.exceptions.EmptyContainerException;

import static org.junit.Assert.fail;
import org.junit.Test;

import datastructures.concrete.ArrayHeap;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import datastructures.interfaces.IPriorityQueue;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestSortingStress extends BaseTest {
    private static final int MAX = 100000;

    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }

    @Test(timeout = 10 * SECOND)
    public void testStressUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < MAX; i++) {
            list.add(i);
        }
        IList<Integer> top = Searcher.topKSort(10000, list);
        assertEquals(10000, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(MAX - 10000 + i, top.get(i));
        }
    }

    @Test(timeout = 10 * SECOND)
    public void testManyInsert() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = MAX; i > 0; i--) {
            heap.insert(i);
        }
        assertEquals(MAX, heap.size());
        assertEquals(1, heap.peekMin());
    }

    @Test(timeout = 10 * SECOND)
    public void testManyRemove() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < MAX; i++) {
            heap.insert(i);
        }
        for (int i = 0; i < MAX; i++) {
            heap.removeMin();
        }
        try {
            heap.removeMin();
            fail("Expected EmptyContainerException");
        } catch (EmptyContainerException ex) {
            // Do nothing: this is OK
        }
    }

    @Test(timeout = 10 * SECOND)
    public void testManyPeek() {
        // large amount of data
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < MAX; i++) {
            heap.insert(i);
        }
        for (int i = 0; i < MAX; i++) {
            heap.removeMin();
        }
        try {
            heap.peekMin();
            fail("Expected EmptyContainerException");
        } catch (EmptyContainerException ex) {
            // Do nothing: this is OK
        }
    }
}

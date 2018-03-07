package datastructures.sorting;

import misc.BaseTest;
import datastructures.concrete.ArrayHeap;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import datastructures.interfaces.IPriorityQueue;
import misc.Searcher;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */

public class TestTopKSortFunctionality extends BaseTest {

    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }

    @Test(timeout = SECOND)
    public void testSimpleUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        IList<Integer> top = Searcher.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(15 + i, top.get(i));
        }
    }
    
    @Test(timeout = SECOND)
    public void testSortingReversed() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 10; i > 0; i--) {
            list.add(i);
        }
        IList<Integer> top = Searcher.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(6 + i, top.get(i));
        }
    }


    @Test(timeout = SECOND)
    public void testRandom() {
        IList<Integer> list = new DoubleLinkedList<>();
        list.add(2);
        list.add(1);
        list.add(90);
        list.add(56);
        list.add(44);
        IList<Integer> top = Searcher.topKSort(3, list);
        assertEquals(3, top.size());
        assertEquals(44, top.get(0));
        assertEquals(56, top.get(1));
        assertEquals(90, top.get(2));
    }
    
    @Test(timeout = SECOND)
    public void testSortingOneValue() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        IList<Integer> top = Searcher.topKSort(1, list);
        assertEquals(1, top.size());
        assertEquals(9, top.get(0));
    }

    @Test(timeout = SECOND)
    public void negativeK() {
        IList<Integer> list = new DoubleLinkedList<>();
        try {
            Searcher.topKSort(-10, list);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // Do nothing: this is OK
        }
    }

    @Test(timeout = SECOND)
    public void largeK() {
        IList<Integer> list = new DoubleLinkedList<>();
        list.add(2);
        list.add(1);
        list.add(90);
        IList<Integer> top = Searcher.topKSort(10, list);
        assertEquals(3, top.size());
        assertEquals(1, top.get(0));
        assertEquals(2, top.get(1));
        assertEquals(90, top.get(2));
    }

    @Test(timeout = SECOND)
    public void testDuplicates() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 50; i++) {
            list.add(2);
        }
        IList<Integer> sorted = Searcher.topKSort(20, list);
        assertEquals(20, sorted.size());
        for (int i = 0; i < sorted.size(); i++) {
            assertEquals(2, sorted.get(i));
        }
    }

    @Test(timeout = SECOND)
    public void testZeroK() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }
        IList<Integer> top = Searcher.topKSort(0, list);
        assertTrue(top.isEmpty());
    }
}

package datastructures.sorting;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import misc.BaseTest;
import misc.exceptions.EmptyContainerException;
import datastructures.concrete.ArrayHeap;
import datastructures.interfaces.IPriorityQueue;
import org.junit.Test;

//import java.util.*;
/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestArrayHeapFunctionality extends BaseTest {
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }

    @Test(timeout = SECOND)
    public void testBasicSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        assertEquals(1, heap.size());
        assertTrue(!heap.isEmpty());
    }

    @Test(timeout = SECOND)
    public void testBasicInsertAndPeek() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 5; i > 0; i--) {
            heap.insert(i);
        }
        assertEquals(5, heap.size());
        assertEquals(1, heap.peekMin());
    }
    
    @Test(timeout = SECOND)
    public void testInsertAndRemoveMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 10; i++) {
            heap.insert(i);
        }

        for (int i = 0; i < 10; i++) {
            assertEquals(i, heap.removeMin());
        }
        assertTrue(heap.isEmpty());
    }
    
    @Test(timeout = SECOND)
    public void testInsertAndRemoveMinReversely() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 10; i > 0; i--) {
            heap.insert(i);
        }
        assertEquals(1, heap.peekMin());
        assertEquals(10, heap.size());
        int i = 1;
        while (!heap.isEmpty()) {
            assertEquals(i, heap.removeMin());
            i++;
        }
    }

    @Test(timeout = SECOND)
    public void testPeekEmpty() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
            heap.peekMin();
            fail("Expected EmptyContainerException");
        } catch (EmptyContainerException ex) {
            // Do nothing: this is OK
        }
    }

    @Test(timeout = SECOND)
    public void testType() {
        IPriorityQueue<Character> heap = this.makeInstance();
        for (char i = 'a'; i <= 'z'; i++) {
            heap.insert(i);
        }
        assertEquals('a', heap.peekMin());
        assertEquals(26, heap.size());
    }

    @Test(timeout = SECOND)
    public void testRemoveEmpty() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
            heap.removeMin();
            fail("Expected EmptyContainerException");
        } catch (EmptyContainerException ex) {
            // Do nothing: this is OK
        }
        heap.insert(1);
        heap.removeMin();
        try {
            heap.removeMin();
            fail("Expected EmptyContainerException");
        } catch (EmptyContainerException ex) {
            // This is ok: do nothing
        }
        assertTrue(heap.isEmpty());
    }

    
    @Test(timeout = SECOND)
    public void testInsertNull() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
            heap.insert(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // This is ok: do nothing
        }
        for (int i = 1; i < 5; i++) {
            heap.insert(i);
        }
        try {
            heap.insert(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // This is ok: do nothing
        }
    }
    
    @Test(timeout = SECOND)
    public void testpeekMinAndInsert() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = -1; i < 2; i++) {
            heap.insert(i);
        }
        assertEquals(-1, heap.peekMin());
        assertEquals(3, heap.size());
        assertTrue(!heap.isEmpty());
    }
    

    @Test(timeout = SECOND)
    public void testInsertRandomly() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(1);
        heap.insert(10);
        heap.insert(30);
        heap.insert(-2);
        heap.insert(0);

        assertEquals(-2, heap.peekMin());
        assertEquals(5, heap.size());
    }

}

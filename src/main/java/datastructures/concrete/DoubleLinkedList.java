package datastructures.concrete;

import datastructures.interfaces.IList;
import misc.exceptions.EmptyContainerException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Note: For more info on the expected behavior of your methods, see the source
 * code for IList.
 */
public class DoubleLinkedList<T> implements IList<T> {
    // You may not rename these fields or change their types.
    // We will be inspecting these in our private tests.
    // You also may not add any additional fields.
    private Node<T> front;
    private Node<T> back;
    private int size;

    public DoubleLinkedList() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    @Override
    public void add(T item) {
        if (size == 0) {
            front = new Node<T>(item);
            back = front;
            front.prev = null;
            back.next = null;
        } else {
            Node<T> cur = new Node<T>(item);
            back.next = cur;
            cur.prev = back;
            back = cur;
        }
        size++;
    }

    @Override
    public T remove() {
        if (size == 0) {
            throw new EmptyContainerException();
        }
        T lastData = back.data;
        if (size == 1) {
            front = null;
            back = null;
        } else {
            Node<T> cur = back.prev;
            back.prev = null;
            back = cur;
            cur.next = null;
        }
        size--;
        return lastData;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> cur = front;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.data;
    }

    @Override
    public void set(int index, T item) {
        delete(index);
        insert(index, item);
    }

    @Override
    public void insert(int index, T item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> temp = new Node<T>(item);

        if (index <= size / 2) {
            if (index == 0) {
                if (front != null) {
                    temp.next = front;
                    front.prev = temp;
                    front = front.prev;
                    front.prev = null;
                } else {
                    front = temp;
                    back = front;
                    front.prev = null;
                    back.next = null;
                }
            } else {
                Node<T> cur = front;
                for (int i = 0; i < index - 1; i++) {
                    cur = cur.next;
                }
                insertHelper(cur, temp);
            }
        } else {
            if (index == size) {
                back.next = temp;
                temp.prev = back;
                back = back.next;
                back.next = null;
            } else {
                Node<T> cur = back;
                for (int i = 0; i < size - index; i++) {
                    cur = cur.prev;
                }
                insertHelper(cur, temp);
            }
        }
        size++;
    }
    
    private void insertHelper(Node<T> cur, Node<T> temp) {
        temp.prev = cur;
        temp.next = cur.next;
        cur.next = temp;
        temp = temp.next;
        temp.prev = cur.next;
    }

    @Override
    public T delete(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 1)  {
            return remove();
        }
        if (size == 2) {
            if (index == 0) {
                Node<T> cur = front;
                front = front.next;
                cur.next = null;
                front.prev = null;
                size--;
                return cur.data;
            } else {    // index == 1
                return remove();
            }
        }
        
        if (index <= size / 2) {
            Node<T> cur = front;
            if (index == 0) {
                front = front.next;
                cur.next = null;
                front.prev = null;
                size--;
                return cur.data;
            } else {
                for (int i = 0; i < index - 1; i++) {
                    cur = cur.next;
                }
                return helper(cur);
            }
        } else {
            if (index == size - 1) {
                return remove();
            } else {
                Node<T> cur = back;
                for (int i = 0; i < size - index; i++) {
                    cur = cur.prev;
                }
                return helper(cur);
            }
        }
    }
    
    private T helper(Node<T> cur) {
        Node<T> temp = cur.next;
        cur.next = cur.next.next;
        temp.next = null;
        cur = cur.next;
        cur.prev = temp.prev;
        temp.prev = null;
        size--;
        return temp.data;
    }

    @Override
    public int indexOf(T item) {
        // if (item == null) {
        // return -1;
        // }
        Node<T> cur = front;
        for (int i = 0; i < size; i++) {
            if (cur.data == item || cur.data.equals(item)) {
                return i;
            }
            cur = cur.next;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(T other) {
        return indexOf(other) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        // Note: we have provided a part of the implementation of
        // an iterator for you. You should complete the methods stubs
        // in the DoubleLinkedListIterator inner class at the bottom
        // of this file. You do not need to change this method.
        return new DoubleLinkedListIterator<>(this.front);
    }

    private static class Node<E> {
        // You may not change the fields in this node or add any new fields.
        public final E data;
        public Node<E> prev;
        public Node<E> next;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        public Node(E data) {
            this(null, data, null);
        }

        // Feel free to add additional constructors or methods to this class.
    }

    private static class DoubleLinkedListIterator<T> implements Iterator<T> {
        // You should not need to change this field, or add any new fields.
        private Node<T> current;

        public DoubleLinkedListIterator(Node<T> current) {
            // You do not need to make any changes to this constructor.
            this.current = current;
        }

        /**
         * Returns 'true' if the iterator still has elements to look at; returns 'false'
         * otherwise.
         */
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Returns the next item in the iteration and internally updates the iterator to
         * advance one element forward.
         *
         * @throws NoSuchElementException
         *             if we have reached the end of the iteration and there are no more
         *             elements to look at.
         */
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T data = current.data;
            current = current.next;
            return data;
        }
    }
}

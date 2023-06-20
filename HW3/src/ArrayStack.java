import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

/**
 * A data structure that implements a generic stack using an array.
 * The stack is a LIFO data structure, where the head points at the last element.
 * This stack has a set capacity.
 * This stack is iterable.
 *
 * @param <E> A cloneable data type
 */
public class ArrayStack<E extends Cloneable> implements Stack<E> {
    private E[] data;
    private int head;

    /**
     * Constructor for ArrayStack with a capacity parameter.
     *
     * @param capacity the capacity of the stack
     * @throws NegativeCapacityException if given capacity is negative
     */
    public ArrayStack(int capacity) {
        if (capacity < 0) {
            throw new NegativeCapacityException();
        }
        data = (E[]) new Cloneable[capacity];
        head = 0;
    }

    /**
     * Adds an element to the head of the stack.
     *
     * @param element the element to add
     * @throws StackOverflowException if the stack is full
     */
    @Override
    public void push(E element) {
        if (head == data.length) {
            throw new StackOverflowException();
        }
        data[head++] = element;
    }

    /**
     * Removes an element from the head of the stack.
     *
     * @return the removed element
     * @throws EmptyStackException if trying to pop from an empty stack
     */
    @Override
    public E pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return data[--head];
    }

    /**
     * Returns the element at the head of the stack.
     *
     * @return the element at the head
     * @throws EmptyStackException if the stack is empty
     */
    @Override
    public E peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return data[head - 1];
    }

    /**
     * Returns the number of elements in the stack.
     *
     * @return the number of elements
     */
    @Override
    public int size() {
        return head;
    }

    /**
     * Checks if the stack is empty.
     *
     * @return true if the stack has no elements
     */
    @Override
    public boolean isEmpty() {
        return head == 0;
    }

    /**
     * Performs a deep copy on the stack.
     *
     * @return a clone of the stack
     */
    @Override
    public ArrayStack<E> clone() {
        try {
            ArrayStack<E> stackClone = (ArrayStack<E>) super.clone();
            stackClone.data = data.clone();
            for (int i = 0; i < head; i++) {
                stackClone.data[i] = (E) data[i].getClass().getMethod("clone").invoke(data[i]);
            }
            return stackClone;
        } catch (CloneNotSupportedException | NoSuchMethodException
                 | InvocationTargetException | IllegalAccessException e) {
            return null;
        }
    }


    /**
     * Returns an iterator over elements of type E.
     *
     * @return an iterator.
     */
    @Override
    public StackIterator iterator() {
        return new StackIterator();
    }

    /** Iterator for ArrayStack */
    public class StackIterator implements Iterator<E> {
        private int index;

        /** Constructs a StackIterator */
        private StackIterator() {
            index = head - 1;
        }

        @Override
        public boolean hasNext() {
            return index >= 0;
        }

        @Override
        public E next() {
            return data[index--];
        }
    }
}

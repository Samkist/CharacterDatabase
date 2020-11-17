package me.Samkist.CharacterDatabase;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class DoubleLinkedList<T> implements Iterable<T> {

    private Node<T> head, tail;

    @Override
    public SamIterator iterator() {
        return new SamIterator();
    }

    public DoubleLinkedList() {

    }

    public void add(T data) {
        Node<T> node = new Node<>(data, tail,null);
        if(head == null) {
            tail = head = node;
        } else {
            tail.setNext(node);
            tail = node;
        }
    }

    public class SamIterator implements Iterator {

        private Node<T> cursor = null;
        private Node<T> next = head;
        private Node<T> previous = null;

        @Override
        public boolean hasNext() {
            return !Objects.isNull(next);
        }

        public boolean hasPrevious() {
            return !Objects.isNull(previous);
        }

        @Override
        public T next() {
            if(!hasNext()) throw new NoSuchElementException();
            T data = (T) next.getData();
            if(!Objects.isNull(cursor))
                previous = cursor;
            cursor = next;
            next = next.getNext();
            return data;
        }

        public T previous() {
            if(!hasPrevious()) throw new NoSuchElementException();
            T data = (T) previous.getData();


            return data;
        }

        @Override
        public void remove() {
            if(Objects.isNull(cursor)) throw new NoSuchElementException();
            if(head.equals(tail)) {
                head = null;
                tail = null;
                return;
            }
            if(Objects.isNull(previous)) {
                head = cursor.getNext();
                next = head.getNext();
                return;
            }
            previous.setNext(cursor.getNext());
            cursor.getNext().setPrevious(previous);
        }

        @Override
        public void forEachRemaining(Consumer action) {
            if(!Objects.isNull(cursor.getNext())) {
                action.accept((T) cursor.getNext().getData());
            }
            while(hasNext()) {
                action.accept(next());
            }
        }
    }

    public int size() {
        AtomicInteger i = new AtomicInteger();
        forEach(s -> i.getAndIncrement());
        return i.get();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        Iterator<T> it = iterator();
        while(it.hasNext()) {
            action.accept(it.next());
        }
    }

    public boolean contains(T data) {
        Iterator<T> it = iterator();
        while(it.hasNext()) {
            if(it.next().equals(data))
                return true;
        }
        return false;
    }

    public void remove(T data) {
        Iterator<T> it = iterator();
        while(it.hasNext()) {
            if(it.next().equals(data)) {
                it.remove();
                return;
            }
        }
    }


    private class Node<T> {

        private T data;
        private Node<T> previous;
        private Node<T> next;

        public Node(T data, Node<T> previous, Node<T> next) {
            this.data = data;
            this.previous = previous;
            this.next = next;
        }

        public T getData() {
            return data;
        }

        public Node<T> getNext() {
            return next;
        }

        public Node<T> getPrevious() {
            return previous;
        }

        public boolean hasNext() {
            return next != null;
        }

        public boolean hasPrevious() {
            return previous != null;
        }

        public void setPrevious(Node<T> previous) {
            this.previous = previous;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

    }

}
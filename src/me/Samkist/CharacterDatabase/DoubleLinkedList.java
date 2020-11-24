package me.Samkist.CharacterDatabase;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class DoubleLinkedList<T> implements Iterable<T>, Collection<T> {

    private Node<T> head, tail;

    @Override
    public SamIterator iterator() {
        return new SamIterator();
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        SamIterator it = iterator();
        int x = 0;
        while(it.hasNext()) {
            a[x++] = (T1) it.next;
        }
        return a;
    }

    public SamIterator reverseIterator() {
        return new SamIterator().fromEnd();
    }

    public DoubleLinkedList() {
        LinkedList<String> linked = new LinkedList<>();
    }

    public boolean add(T data) {
        try {
            Node<T> node = new Node<>(data, tail, null);
            if (head == null) {
                tail = head = node;
            } else {
                tail.setNext(node);
                tail = node;
            }
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

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

        private SamIterator fromEnd() {
            cursor = null;
            next = null;
            previous = tail;
            return this;
        }

        @Override
        public T next() {
            if(!hasNext()) throw new NoSuchElementException();
            T data = next.getData();
            if(!Objects.isNull(cursor))
                previous = cursor;
            cursor = next;
            next = next.getNext();
            return data;
        }

        public T previous() {
            if(!hasPrevious()) throw new NoSuchElementException();
            T data = previous.getData();
            if(Objects.nonNull(previous)) {
                cursor = previous;
            }
            previous = previous.getPrevious();
            next = cursor;
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
            cursor = previous;
            previous.setNext(next.getNext());
            next.setPrevious(previous.getPrevious());
        }

        @Override
        public void forEachRemaining(Consumer action) {
            if(!Objects.isNull(cursor.getNext())) {
                action.accept((cursor.getNext().getData()));

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
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        Iterator<T> it = iterator();
        while(it.hasNext()) {
            action.accept(it.next());
        }
    }

    public boolean contains(Object data) {
        Iterator<T> it = iterator();
        while(it.hasNext()) {
            if(it.next().equals(data))
                return true;
        }
        return false;
    }

    public boolean remove(Object data) {
        Iterator<T> it = iterator();
        while(it.hasNext()) {
            if(it.next().equals(data)) {
                it.remove();
                return true;
            }
        }
        return false;
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
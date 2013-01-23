package concurrency.ch13_explicit_locks.exercise_13_1;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

/**
 * TODO: Fix the WalkingCollection to allow multiple threads to call the
 * TODO: iterate() method concurrently and to still be threadsafe
 */
public class WalkingCollection<E>
        extends AbstractCollection<E> {
    private final Collection<E> wrappedCollection;

    public WalkingCollection(Collection<E> wrappedCollection) {
        this.wrappedCollection = wrappedCollection;
    }

    public synchronized void iterate(Processor<? super E> processor) {
        // TODO: lock using a ReadLock, then iterate through collection calling
        // TODO: processor.process(e) on each element
        for (E e : wrappedCollection) {
            if (!processor.process(e)) return;
        }
    }

    @Override
    public synchronized Iterator<E> iterator() {
        // TODO: this method should not really be called by users anymore, instead
        // TODO: they should call the iterate(Processor) method

        // TODO: return an iterator that locks a ReadLock on hasNext() and next()
        // TODO: and a WriteLock on remove().

        // TODO: Should throw IllegalStateException if a thread tries to call
        // TODO: remove() during iteration.
        final Iterator<E> wrappedIterator = wrappedCollection.iterator();
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                synchronized (WalkingCollection.this) {
                    return wrappedIterator.hasNext();
                }
            }

            @Override
            public E next() {
                synchronized (WalkingCollection.this) {
                    return wrappedIterator.next();
                }
            }

            @Override
            public void remove() {
                synchronized (WalkingCollection.this) {
                    wrappedIterator.remove();
                }
            }
        };
    }

    @Override
    public synchronized int size() {
        // TODO: the size of the wrappedCollection, but wrapped with a ReadLock
        return wrappedCollection.size();
    }

    @Override
    public synchronized boolean add(E e) {
        // TODO: adds the element to the collection, throws IllegalStateException
        // TODO: if that thread is busy iterating
        return wrappedCollection.add(e);
    }
}

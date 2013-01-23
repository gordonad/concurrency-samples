package concurrency.ch13_explicit_locks.solution_13_1;

import concurrency.ch13_explicit_locks.exercise_13_1.Processor;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * In the solution, we use a ReadWriteLock to allow concurrent iteration but
 * only exclusive modification.  One thing to watch out for - you cannot
 * upgrade a read lock to a write lock in the current ReeentrantReadWriteLock.
 * Thus, every time we want to acquire a write lock, we first need to ensure
 * that we are not currently holding a read lock, otherwise we will cause a
 * deadlock.
 */
public class WalkingCollection<E>
        extends AbstractCollection<E> {
    private final Collection<E> wrappedCollection;
    private final ReentrantReadWriteLock rwlock =
            new ReentrantReadWriteLock();

    public WalkingCollection(Collection<E> wrappedCollection) {
        this.wrappedCollection = wrappedCollection;
    }

    public void iterate(Processor<? super E> processor) {
        // lock using a ReadLock, then iterate through collection calling
        // processor.process(e) on each element
        rwlock.readLock().lock();
        try {
            for (E e : wrappedCollection) {
                if (!processor.process(e)) return;
            }
        } finally {
            rwlock.readLock().unlock();
        }
    }

    @Override
    public Iterator<E> iterator() {
        // this method should not really be called by users anymore, instead
        // they should call the iterate(Processor) method

        // return an iterator that locks a ReadLock on hasNext() and next()
        // and a WriteLock on remove().

        // Should throw IllegalStateException if a thread tries to call
        // remove() during iteration.

        rwlock.readLock().lock();
        try {
            final Iterator<E> wrappedIterator = wrappedCollection.iterator();
            return new Iterator<E>() {
                @Override
                public boolean hasNext() {
                    rwlock.readLock().lock();
                    try {
                        return wrappedIterator.hasNext();
                    } finally {
                        rwlock.readLock().unlock();
                    }

                }

                @Override
                public E next() {
                    rwlock.readLock().lock();
                    try {
                        return wrappedIterator.next();
                    } finally {
                        rwlock.readLock().unlock();
                    }

                }

                @Override
                public void remove() {
                    checkThatWeDoNotOwnReadLocks();
                    rwlock.writeLock().lock();
                    try {
                        wrappedIterator.remove();
                    } finally {
                        rwlock.writeLock().unlock();
                    }

                }
            };
        } finally {
            rwlock.readLock().unlock();
        }

    }

    private void checkThatWeDoNotOwnReadLocks() {
        if (rwlock.getReadHoldCount() > 0)
            throw new IllegalStateException(
                    "thread already holds read lock - cannot upgrade locks!");
    }

    @Override
    public int size() {
        // the size of the wrappedCollection, but wrapped with a ReadLock
        rwlock.readLock().lock();
        try {
            return wrappedCollection.size();
        } finally {
            rwlock.readLock().unlock();
        }

    }

    @Override
    public boolean add(E e) {
        // adds the element to the collection, throws IllegalStateException
        // if that thread is busy iterating
        checkThatWeDoNotOwnReadLocks();
        rwlock.writeLock().lock();
        try {
            return wrappedCollection.add(e);
        } finally {
            rwlock.writeLock().unlock();
        }

    }
}

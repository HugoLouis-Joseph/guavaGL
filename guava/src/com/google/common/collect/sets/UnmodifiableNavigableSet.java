package com.google.common.collect.sets;
import java.util.*;

public final class UnmodifiableNavigableSet<E extends @Nullable Object>
      extends ForwardingSortedSet<E> implements NavigableSet<E>, Serializable {
    private final NavigableSet<E> delegate;
    private final SortedSet<E> unmodifiableDelegate;

    UnmodifiableNavigableSet(NavigableSet<E> delegate) {
      this.delegate = checkNotNull(delegate);
      this.unmodifiableDelegate = Collections.unmodifiableSortedSet(delegate);
    }

    @Override
    protected SortedSet<E> delegate() {
      return unmodifiableDelegate;
    }

    // default methods not forwarded by ForwardingSortedSet

    @Override
    public boolean removeIf(java.util.function.Predicate<? super E> filter) {
      throw new UnsupportedOperationException();
    }

    @Override
    public Stream<E> stream() {
      return delegate.stream();
    }

    @Override
    public Stream<E> parallelStream() {
      return delegate.parallelStream();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
      delegate.forEach(action);
    }

    @Override
    public @Nullable E lower(@ParametricNullness E e) {
      return delegate.lower(e);
    }

    @Override
    public @Nullable E floor(@ParametricNullness E e) {
      return delegate.floor(e);
    }

    @Override
    public @Nullable E ceiling(@ParametricNullness E e) {
      return delegate.ceiling(e);
    }

    @Override
    public @Nullable E higher(@ParametricNullness E e) {
      return delegate.higher(e);
    }

    @Override
    public @Nullable E pollFirst() {
      throw new UnsupportedOperationException();
    }

    @Override
    public @Nullable E pollLast() {
      throw new UnsupportedOperationException();
    }

    @LazyInit private transient @Nullable UnmodifiableNavigableSet<E> descendingSet;

    @Override
    public NavigableSet<E> descendingSet() {
      UnmodifiableNavigableSet<E> result = descendingSet;
      if (result == null) {
        result = descendingSet = new UnmodifiableNavigableSet<>(delegate.descendingSet());
        result.descendingSet = this;
      }
      return result;
    }

    @Override
    public Iterator<E> descendingIterator() {
      return Iterators.unmodifiableIterator(delegate.descendingIterator());
    }

    @Override
    public NavigableSet<E> subSet(
        @ParametricNullness E fromElement,
        boolean fromInclusive,
        @ParametricNullness E toElement,
        boolean toInclusive) {
      return unmodifiableNavigableSet(
          delegate.subSet(fromElement, fromInclusive, toElement, toInclusive));
    }

    @Override
    public NavigableSet<E> headSet(@ParametricNullness E toElement, boolean inclusive) {
      return Sets.unmodifiableNavigableSet(delegate.headSet(toElement, inclusive));
    }

    @Override
    public NavigableSet<E> tailSet(@ParametricNullness E fromElement, boolean inclusive) {
      return Sets.unmodifiableNavigableSet(delegate.tailSet(fromElement, inclusive));
    }

    @GwtIncompatible @J2ktIncompatible private static final long serialVersionUID = 0;
  }
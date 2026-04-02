package com.google.common.collect.sets;
import com.google.common.collect.sets.*;
import java.util.*;

@GwtIncompatible // NavigableSet
  public final class FilteredNavigableSet<E extends @Nullable Object>
      extends FilteredSortedSet<E> implements NavigableSet<E> {
    FilteredNavigableSet(NavigableSet<E> unfiltered, Predicate<? super E> predicate) {
      super(unfiltered, predicate);
    }

    NavigableSet<E> unfiltered() {
      return (NavigableSet<E>) unfiltered;
    }

    @Override
    public @Nullable E lower(@ParametricNullness E e) {
      return Iterators.find(unfiltered().headSet(e, false).descendingIterator(), predicate, null);
    }

    @Override
    public @Nullable E floor(@ParametricNullness E e) {
      return Iterators.find(unfiltered().headSet(e, true).descendingIterator(), predicate, null);
    }

    @Override
    public @Nullable E ceiling(@ParametricNullness E e) {
      return Iterables.find(unfiltered().tailSet(e, true), predicate, null);
    }

    @Override
    public @Nullable E higher(@ParametricNullness E e) {
      return Iterables.find(unfiltered().tailSet(e, false), predicate, null);
    }

    @Override
    public @Nullable E pollFirst() {
      return Iterables.removeFirstMatching(unfiltered(), predicate);
    }

    @Override
    public @Nullable E pollLast() {
      return Iterables.removeFirstMatching(unfiltered().descendingSet(), predicate);
    }

    @Override
    public NavigableSet<E> descendingSet() {
      return Sets.filter(unfiltered().descendingSet(), predicate);
    }

    @Override
    public Iterator<E> descendingIterator() {
      return Iterators.filter(unfiltered().descendingIterator(), predicate);
    }

    @Override
    @ParametricNullness
    public E last() {
      return Iterators.find(unfiltered().descendingIterator(), predicate);
    }

    @Override
    public NavigableSet<E> subSet(
        @ParametricNullness E fromElement,
        boolean fromInclusive,
        @ParametricNullness E toElement,
        boolean toInclusive) {
      return filter(
          unfiltered().subSet(fromElement, fromInclusive, toElement, toInclusive), predicate);
    }

    @Override
    public NavigableSet<E> headSet(@ParametricNullness E toElement, boolean inclusive) {
      return filter(unfiltered().headSet(toElement, inclusive), predicate);
    }

    @Override
    public NavigableSet<E> tailSet(@ParametricNullness E fromElement, boolean inclusive) {
      return filter(unfiltered().tailSet(fromElement, inclusive), predicate);
    }
  }
package com.google.common.collect.sets;
import com.google.common.collect.Sets;
import java.util.SortedSet;
import java.util.Comparator;

private static class FilteredSortedSet<E extends @Nullable Object> extends FilteredSet<E>
      implements SortedSet<E> {

    FilteredSortedSet(SortedSet<E> unfiltered, Predicate<? super E> predicate) {
      super(unfiltered, predicate);
    }

    @Override
    public @Nullable Comparator<? super E> comparator() {
      return ((SortedSet<E>) unfiltered).comparator();
    }

    @Override
    public SortedSet<E> subSet(@ParametricNullness E fromElement, @ParametricNullness E toElement) {
      return new FilteredSortedSet<>(
          ((SortedSet<E>) unfiltered).subSet(fromElement, toElement), predicate);
    }

    @Override
    public SortedSet<E> headSet(@ParametricNullness E toElement) {
      return new FilteredSortedSet<>(((SortedSet<E>) unfiltered).headSet(toElement), predicate);
    }

    @Override
    public SortedSet<E> tailSet(@ParametricNullness E fromElement) {
      return new FilteredSortedSet<>(((SortedSet<E>) unfiltered).tailSet(fromElement), predicate);
    }

    @Override
    @ParametricNullness
    public E first() {
      return Iterators.find(unfiltered.iterator(), predicate);
    }

    @Override
    @ParametricNullness
    public E last() {
      SortedSet<E> sortedUnfiltered = (SortedSet<E>) unfiltered;
      while (true) {
        E element = sortedUnfiltered.last();
        if (predicate.apply(element)) {
          return element;
        }
        sortedUnfiltered = sortedUnfiltered.headSet(element);
      }
    }
  }
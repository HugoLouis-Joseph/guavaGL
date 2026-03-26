package com.google.common.collect.sets;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.Comparator;

@GwtIncompatible // NavigableSet
  static class DescendingSet<E extends @Nullable Object> extends ForwardingNavigableSet<E> {
    private final NavigableSet<E> forward;

    DescendingSet(NavigableSet<E> forward) {
      this.forward = forward;
    }

    @Override
    protected NavigableSet<E> delegate() {
      return forward;
    }

    @Override
    public @Nullable E lower(@ParametricNullness E e) {
      return forward.higher(e);
    }

    @Override
    public @Nullable E floor(@ParametricNullness E e) {
      return forward.ceiling(e);
    }

    @Override
    public @Nullable E ceiling(@ParametricNullness E e) {
      return forward.floor(e);
    }

    @Override
    public @Nullable E higher(@ParametricNullness E e) {
      return forward.lower(e);
    }

    @Override
    public @Nullable E pollFirst() {
      return forward.pollLast();
    }

    @Override
    public @Nullable E pollLast() {
      return forward.pollFirst();
    }

    @Override
    public NavigableSet<E> descendingSet() {
      return forward;
    }

    @Override
    public Iterator<E> descendingIterator() {
      return forward.iterator();
    }

    @Override
    public NavigableSet<E> subSet(
        @ParametricNullness E fromElement,
        boolean fromInclusive,
        @ParametricNullness E toElement,
        boolean toInclusive) {
      return forward.subSet(toElement, toInclusive, fromElement, fromInclusive).descendingSet();
    }

    @Override
    public SortedSet<E> subSet(@ParametricNullness E fromElement, @ParametricNullness E toElement) {
      return standardSubSet(fromElement, toElement);
    }

    @Override
    public NavigableSet<E> headSet(@ParametricNullness E toElement, boolean inclusive) {
      return forward.tailSet(toElement, inclusive).descendingSet();
    }

    @Override
    public SortedSet<E> headSet(@ParametricNullness E toElement) {
      return standardHeadSet(toElement);
    }

    @Override
    public NavigableSet<E> tailSet(@ParametricNullness E fromElement, boolean inclusive) {
      return forward.headSet(fromElement, inclusive).descendingSet();
    }

    @Override
    public SortedSet<E> tailSet(@ParametricNullness E fromElement) {
      return standardTailSet(fromElement);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Comparator<? super E> comparator() {
      Comparator<? super E> forwardComparator = forward.comparator();
      if (forwardComparator == null) {
        return (Comparator) Ordering.natural().reverse();
      } else {
        return reverse(forwardComparator);
      }
    }

    // If we inline this, we get a javac error.
    private static <T extends @Nullable Object> Ordering<T> reverse(Comparator<T> forward) {
      return Ordering.from(forward).reverse();
    }

    @Override
    @ParametricNullness
    public E first() {
      return forward.last();
    }

    @Override
    @ParametricNullness
    public E last() {
      return forward.first();
    }

    @Override
    public Iterator<E> iterator() {
      return forward.descendingIterator();
    }

    @Override
    public @Nullable Object[] toArray() {
      return standardToArray();
    }

    @Override
    @SuppressWarnings("nullness") // b/192354773 in our checker affects toArray declarations
    public <T extends @Nullable Object> T[] toArray(T[] array) {
      return standardToArray(array);
    }

    @Override
    public String toString() {
      return standardToString();
    }
  }
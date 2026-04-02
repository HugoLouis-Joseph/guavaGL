package com.google.common.collect.sets;
import com.google.common.collect.sets.*;

public static class FilteredSet<E extends @Nullable Object> extends FilteredCollection<E>
      implements Set<E> {
    FilteredSet(Set<E> unfiltered, Predicate<? super E> predicate) {
      super(unfiltered, predicate);
    }

    @Override
    public boolean equals(@Nullable Object object) {
      return Sets.equalsImpl(this, object);
    }

    @Override
    public int hashCode() {
      return Sets.hashCodeImpl(this);
    }
  }
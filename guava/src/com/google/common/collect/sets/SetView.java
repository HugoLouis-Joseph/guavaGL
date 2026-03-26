package com.google.common.collect.sets;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.AbstractSet;
import com.google.common.collect.Sets;
import java.util.Set;

/**
   * An unmodifiable view of a set which may be backed by other sets; this view will change as the
   * backing sets do. Contains methods to copy the data into a new set which will then remain
   * stable. There is usually no reason to retain a reference of type {@code SetView}; typically,
   * you either use it as a plain {@link Set}, or immediately invoke {@link #immutableCopy} or
   * {@link #copyInto} and forget the {@code SetView} itself.
   *
   * @since 2.0
   */
  public abstract class SetView<E extends @Nullable Object> extends AbstractSet<E> {
    private SetView() {} // no subclasses but our own

    /**
     * Returns an immutable copy of the current contents of this set view. Does not support null
     * elements.
     *
     * <p><b>Warning:</b> this may have unexpected results if a backing set of this view uses a
     * nonstandard notion of equivalence, for example if it is a {@link TreeSet} using a comparator
     * that is inconsistent with {@link Object#equals(Object)}.
     */
    public ImmutableSet<@NonNull E> immutableCopy() {
      // Not using ImmutableSet.copyOf() to avoid iterating thrice (isEmpty, size, iterator).
      int maxSize = maxSize();
      if (maxSize == 0) {
        return ImmutableSet.of();
      }
      ImmutableSet.Builder<@NonNull E> builder = ImmutableSet.builderWithExpectedSize(maxSize);
      for (E element : this) {
        builder.add(checkNotNull(element));
      }
      return builder.build();
    }

    /**
     * Copies the current contents of this set view into an existing set. This method has equivalent
     * behavior to {@code set.addAll(this)}, assuming that all the sets involved are based on the
     * same notion of equivalence.
     *
     * @return a reference to {@code set}, for convenience
     */
    // Note: S should logically extend Set<? super E> but can't due to either
    // some javac bug or some weirdness in the spec, not sure which.
    @CanIgnoreReturnValue
    public <S extends Set<E>> S copyInto(S set) {
      set.addAll(this);
      return set;
    }

    /**
     * Guaranteed to throw an exception and leave the collection unmodified.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @CanIgnoreReturnValue
    @Deprecated
    @Override
    @DoNotCall("Always throws UnsupportedOperationException")
    public final boolean add(@ParametricNullness E e) {
      throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave the collection unmodified.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @CanIgnoreReturnValue
    @Deprecated
    @Override
    @DoNotCall("Always throws UnsupportedOperationException")
    public final boolean remove(@Nullable Object object) {
      throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave the collection unmodified.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @CanIgnoreReturnValue
    @Deprecated
    @Override
    @DoNotCall("Always throws UnsupportedOperationException")
    public final boolean addAll(Collection<? extends E> newElements) {
      throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave the collection unmodified.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @CanIgnoreReturnValue
    @Deprecated
    @Override
    @DoNotCall("Always throws UnsupportedOperationException")
    public final boolean removeAll(Collection<?> oldElements) {
      throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave the collection unmodified.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @CanIgnoreReturnValue
    @Deprecated
    @Override
    @DoNotCall("Always throws UnsupportedOperationException")
    public final boolean removeIf(java.util.function.Predicate<? super E> filter) {
      throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave the collection unmodified.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @CanIgnoreReturnValue
    @Deprecated
    @Override
    @DoNotCall("Always throws UnsupportedOperationException")
    public final boolean retainAll(Collection<?> elementsToKeep) {
      throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave the collection unmodified.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @Deprecated
    @Override
    @DoNotCall("Always throws UnsupportedOperationException")
    public final void clear() {
      throw new UnsupportedOperationException();
    }

    /**
     * Scope the return type to {@link UnmodifiableIterator} to ensure this is an unmodifiable view.
     *
     * @since 20.0 (present with return type {@link Iterator} since 2.0)
     */
    @Override
    public abstract UnmodifiableIterator<E> iterator();

    @Override
    @SuppressWarnings("EqualsHashCode") // same semantics
    public boolean equals(@Nullable Object object) {
      if (object == this) {
        return true;
      }
      if (!(object instanceof Set)) {
        return false;
      }
      Set<?> that = (Set<?>) object;

      int thatMaxSize = maxSize(that);
      if (minSize() > thatMaxSize) {
        return false; // this.size() > that.size()
      }
      int thatMinSize = minSize(that);
      if (maxSize() < thatMinSize) {
        return false; // this.size() < that.size()
      }

      // the base implementation from AbstractSet uses size() and containsAll()
      // both require iterating over the entire SetView
      // we avoid iterating twice by doing the equivalent of both in one iteration
      int thisSize = 0;
      for (E e : this) {
        try {
          if (!that.contains(e)) {
            return false;
          }
        } catch (NullPointerException | ClassCastException ignored) {
          return false;
        }
        thisSize++;
      } // that.containsAll(this) so that.size() >= this.size()

      if (thisSize == thatMaxSize) {
        // this.size() == maxSize(that) >= that.size() >= this.size()
        return true; // this.size() == that.size()
      } else if (thisSize < thatMinSize) {
        // this.size() < minSize(that) <= that.size()
        return false; // this.size() < that.size()
      } else { // that can only be a SetView at this point
        int thatSize = 0;
        for (Object unused : that) {
          if (++thatSize > thisSize) {
            return false;
          }
        }
        return true; // that.size() == this.size()
      }
    }

    /**
     * Returns a lower bound for {@link #size()} based on the sizes of the backing sets.
     *
     * <p>This is more efficient than {@link #size()}, which iterates over the entire {@link
     * SetView}.
     */
    abstract int minSize();

    /**
     * Returns the {@link #minSize()} of {@code set} if it is a {@link SetView}, or the exact {@link
     * #size()} of {@code set} otherwise.
     */
    static int minSize(Set<?> set) {
      return set instanceof SetView ? ((SetView<?>) set).minSize() : set.size();
    }

    /**
     * Returns an upper bound for {@link #size()} based on the sizes of the backing sets.
     *
     * <p>This is more efficient than {@link #size()}, which iterates over the entire {@link
     * SetView}.
     */
    abstract int maxSize();

    /**
     * Returns the {@link #maxSize()} of {@code set} if it is a {@link SetView}, or the exact {@link
     * #size()} of {@code set} otherwise.
     */
    static int maxSize(Set<?> set) {
      return set instanceof SetView ? ((SetView<?>) set).maxSize() : set.size();
    }
  }
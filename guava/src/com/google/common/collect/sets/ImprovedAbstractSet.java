package com.google.common.collect.sets;

import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.Sets;

/**
* {@link AbstractSet} substitute without the potentially-quadratic {@code removeAll}
* implementation.
*/
abstract class ImprovedAbstractSet<E extends @Nullable Object> extends AbstractSet<E> {
    @Override
    public boolean removeAll(Collection<?> c) {
      return Sets.removeAllImpl(this, c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
      return super.retainAll(checkNotNull(c)); // GWT compatibility
    }
}
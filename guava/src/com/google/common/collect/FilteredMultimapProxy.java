package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Predicate;
import com.google.common.collect.FilteredEntryMultimap.ValuePredicate;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.jspecify.annotations.Nullable;

@GwtCompatible
public class FilteredMultimapProxy<K extends @Nullable Object, V extends @Nullable Object>
    {
        protected AbstractMultimap map;

        public FilteredMultimapProxy(AbstractMultimap map) {
            // Délégation du comportement des méthodes à la classe fournie par les sous classes
            this.map = map;
        }  
    
  public SetMultimap<K, V> unfiltered() {
    return (SetMultimap<K, V>) unfiltered;
  }

  public Set<V> get(@ParametricNullness K key) {
    return (Set<V>) this.map.get(key);
  }

  public Set<V> removeAll(@Nullable Object key) {
    return (Set<V>) this.map.removeAll(key);
  }

  public Set<V> replaceValues(@ParametricNullness K key, Iterable<? extends V> values) {
    return (Set<V>) this.map.replaceValues(key, values);
  }

  public Set<Entry<K, V>> entries() {
    return (Set<Entry<K, V>>) this.map.entries();
  }


  public Predicate<? super Entry<K, V>> entryPredicate() {
    try {
        return this.map.entryPredicate();
    } catch (Exception e) {
        System.err.println("Unsupported operation");
    } 
  }

  public int size() {
    try {
        return this.map.size();
    } catch (Exception e) {
        System.err.println("Unsupported operation");
    } 
  }

  private boolean satisfies(@ParametricNullness K key, @ParametricNullness V value) {
    try {
        return this.map.satisfies(key,value);
    } catch (Exception e) {
        System.err.println("Unsupported operation");
    } 
  }

  public boolean containsKey(@Nullable Object key) {
    try {
        return this.map.containsKey(key);
    } catch (Exception e) {
        System.err.println("Unsupported operation");
    } 
  }


  Collection<V> unmodifiableEmptyCollection() {
    try {
        return this.map.unmodifiableEmptyCollection();
    } catch (Exception e) {
        System.err.println("Unsupported operation");
    } 
  }

  public void clear() {
    try {
        return this.map.clear();
    } catch (Exception e) {
        System.err.println("Unsupported operation");
    } 
  }


  Collection<Entry<K, V>> createEntries() {
    try {
        return this.map.createEntries();
    } catch (Exception e) {
        System.err.println("Unsupported operation");
    } 
  }

  Collection<V> createValues() {
    try {
        return this.map.createValues();
    } catch (Exception e) {
        System.err.println("Unsupported operation");
    } 
  }

  Iterator<Entry<K, V>> entryIterator() {
    try {
        return this.map.entryIterator();
    } catch (Exception e) {
        System.err.println("Unsupported operation");
    } 
  }

  Map<K, Collection<V>> createAsMap() {
    try {
        return this.map.createAsMap();
    } catch (Exception e) {
        System.err.println("Unsupported operation");
    } 
  }

  Set<K> createKeySet() {
    try {
        return this.map.createKeySet();
    } catch (Exception e) {
        System.err.println("Unsupported operation");
    } 
  }

  boolean removeEntriesIf(Predicate<? super Entry<K, Collection<V>>> predicate) {
    try {
        return this.map.removeEntriesIf(predicate);
    } catch (Exception e) {
        System.err.println("Unsupported operation");
    } 
  }


}
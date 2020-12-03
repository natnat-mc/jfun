package jfun;

import java.util.*;
import static java.util.Map.Entry;

public final class Maps {
	private Maps() {} // no instances

	// newMap :: map 'k 'v -> map 'k 'v
	private static <K, V, L, W> Map<L, W> newMap(Map<K, V> type) {
		if(type instanceof TreeMap) return new TreeMap<L, W>();
		else return new HashMap<L, W>();
	}

	// mapk :: map 'k 'v -> ('k -> 'l) -> map 'l 'v
	public static <K, L, V> Map<L, V> mapk(Map<K, V> m, Func.Function1<? super K, ? extends L> f) {
		Map<L, V> r = newMap(m);
		for(Entry<K, V> e: m.entrySet()) r.put(f.fn(e.getKey()), e.getValue());
		return r;
	}

	// mapv :: map 'k 'v -> ('v -> 'w) -> map 'k 'w
	public static <K, V, W> Map<K, W> mapv(Map<K, V> m, Func.Function1<? super V, ? extends W> f) {
		Map<K, W> r = newMap(m);
		for(Entry<K, V> e: m.entrySet()) r.put(e.getKey(), f.fn(e.getValue()));
		return r;
	}

	// mapkv :: map 'k 'v -> ('k -> 'l) -> ('v -> 'w) -> map 'l 'w
	public static <K, L, V, W> Map<L, W> mapkv(Map<K, V> m, Func.Function1<? super K, ? extends L> k, Func.Function1<? super V, ? extends W> v) {
		Map<L, W> r = newMap(m);
		for(Entry<K, V> e: m.entrySet()) r.put(k.fn(e.getKey()), v.fn(e.getValue()));
		return r;
	}
	// mapkv :: map 'k 'v -> ('k * 'v -> 'l * 'w) -> map 'l 'w
	public static <K, L, V, W> Map<L, W> mapkv(Map<K, V> m, Func.Function1<Pair<? super K, ? super V>, Pair<? extends L, ? extends W>> f) {
		Map<L, W> r = newMap(m);
		for(Entry<K, V> e: m.entrySet()) {
			Pair<? extends L, ? extends W> p = f.fn(new Pair<K, V>(e.getKey(), e.getValue()));
			r.put(p.a, p.b);
		}
		return r;
	}

	// filterk :: map 'k 'v -> ('k -> bool) -> map 'k 'v
	public static <K, V> Map<K, V> filterk(Map<K, V> m, Func.Predicate1<? super K> p) {
		Map<K, V> r = newMap(m);
		for(Entry<K, V> e: m.entrySet()) if(p.fn(e.getKey())) r.put(e.getKey(), e.getValue());
		return r;
	}

	// filterv :: map 'k 'v -> ('v -> bool) -> map 'k 'v
	public static <K, V> Map<K, V> filterv(Map<K, V> m, Func.Predicate1<? super V> p) {
		Map<K, V> r = newMap(m);
		for(Entry<K, V> e: m.entrySet()) if(p.fn(e.getValue())) r.put(e.getKey(), e.getValue());
		return r;
	}

	// filterkv :: map 'k 'v -> ('k -> 'v -> bool) -> map 'k 'v
	public static <K, V> Map<K, V> filterkv(Map<K, V> m, Func.Function2<? super K, ? super V, Boolean> p) {
		Map<K, V> r = newMap(m);
		for(Entry<K, V> e: m.entrySet()) if(p.fn(e.getKey(), e.getValue())) r.put(e.getKey(), e.getValue());
		return r;
	}
	public static <K, V> Map<K, V> filterkv(Map<K, V> m, Func.Function1<K, Func.Function1<V, Boolean>> p) {
		return filterkv(m, Func.uncurrify2(p));
	}
	// filterkv :: map 'k 'v -> ('k * 'v -> bool) -> map 'k 'v
	public static <K, V> Map<K, V> filterkv(Map<K, V> m, Func.Predicate1<Pair<? super K, ? super V>> p) {
		return filterkv(m, (a, b) -> p.fn(new Pair<K, V>(a, b)));
	}

	// keys :: map 'k 'v -> list 'k
	public static <K> List<K> keys(Map<K, ?> m) {
		List<K> r = new ArrayList<K>(m.size());
		r.addAll(m.keySet());
		return r;
	}

	// values :: map 'k 'v -> list 'v
	public static <V> List<V> values(Map<?, V> m) {
		List<V> r = new ArrayList<V>(m.size());
		r.addAll(m.values());
		return r;
	}

	// pairs :: map 'k 'v -> list ('k * 'v)
	public static <K, V> List<Pair<K, V>> pairs(Map<K, V> m) {
		List<Pair<K, V>> r = new ArrayList<Pair<K, V>>(m.size());
		for(Entry<K, V> e: m.entrySet()) r.add(new Pair<K, V>(e.getKey(), e.getValue()));
		return r;
	}

	// pair :: list ('k * 'v) -> map 'k 'v
	public static <K, V> Map<K, V> pair(List<Pair<K, V>> l) {
		Map<K, V> r = new HashMap<K, V>();
		l.forEach(p -> r.put(p.a, p.b));
		return r;
	}

	// containsk :: map 'k 'v -> 'k -> bool
	public static <K> boolean containsk(Map<K, ?> m, K e) {
		return m.containsKey(e);
	}

	// containsv :: map 'k 'v -> 'v -> bool
	public static <V> boolean containsv(Map<?, V> m, V e) {
		return m.containsValue(e);
	}

	// containskv :: map 'k 'v -> 'k -> 'v -> bool
	public static <K, V> boolean containskv(Map<K, V> m, K k, V v) {
		if(!m.containsKey(k)) return false;
		V e = m.get(k);
		return (e != null && v != null && e.equals(v)) || (e == null && v == null);
	}
	// containskv :: map 'k 'v -> 'k * 'v -> bool
	public static <K, V> boolean containskv(Map<K, V> m, Pair<K, V> p) {
		return containskv(m, p.a, p.b);
	}
}

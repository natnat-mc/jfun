package jfun;

import java.util.*;
import java.util.concurrent.LinkedTransferQueue;

@FunctionalInterface
public interface Iter<T> extends Iterable<T> {
	public static class StopIteration extends Throwable {
		private static final long serialVersionUID = 1L;
	}

	public static class IterImp<T> implements Iterator<T>, Iter<T> {

		protected IterImp(Iter<T> fn) {
			this.fn = fn;
		}
		protected Iter<T> fn;

		private T next;
		private boolean hasNext;
		private boolean calledHasNext;

		@Override
		public IterImp<T> iterator() {
			return this;
		}

		@Override
		public boolean hasNext() {
			if(calledHasNext) return hasNext;
			try {
				next = fn.get();
				hasNext = true;
			} catch(StopIteration e) {
				hasNext = false;
			}
			calledHasNext = true;
			return hasNext;
		}

		@Override
		public T next() {
			if(calledHasNext) {
				calledHasNext = false;
				return next;
			}
			try {
				return fn.get();
			} catch(StopIteration e) {
				throw new NoSuchElementException();
			}
		}

		@Override
		public T get() throws StopIteration {
			return fn.get();
		}
	}

	T get() throws StopIteration;

	@Override
	default IterImp<T> iterator() {
		return new IterImp<T>(this);
	}

	// tofn :: iter 't -> (nil -> 't)
	default Func.Function0<T> tofn() {
		return () -> {
			try {
				return get();
			} catch(StopIteration e) {
				return null;
			}
		};
	}
	public static <T> Func.Function0<T> tofn(Iter<T> i) {
		return i.tofn();
	}

	// map :: iter 't -> ('t -> 'u) -> 'u
	default <U> Iter<U> map(Func.Function1<? super T, ? extends U> f) {
		return () -> f.fn(get());
	}
	public static <T, U> Iter<U> map(Iter<T> i, Func.Function1<? super T, ? extends U> f) {
		return i.map(f);
	}

	// filter :: iter 't -> ('t -> bool) -> iter 't
	default Iter<T> filter(Func.Predicate1<? super T> p) {
		return () -> {
			while(true) {
				T e = get();
				if(p.fn(e)) return e;
			}
		};
	}
	public static <T> Iter<T> filter(Iter<T> i, Func.Predicate1<? super T> p) {
		return i.filter(p);
	}

	// fold :: iter 't -> ('t -> 't -> 't) -> 't -> 't
	default T fold(Func.Function2<? super T, ? super T, ? extends T> f, T i) {
		try {
			while(true) i = f.fn(i, get());
		} catch(StopIteration e) {
			return i;
		}
	}
	default T fold(Func.Function1<T, Func.Function1<T, T>> f, T i) {
		return fold(Func.uncurrify2(f), i);
	}
	public static <T> T fold(Iter<T> i, Func.Function2<?super T, ? super T, ? extends T> f, T j) {
		return i.fold(f, j);
	}
	public static <T> T fold(Iter<T> i, Func.Function1<T, Func.Function1<T, T>> f, T j) {
		return i.fold(f, j);
	}

	// first :: iter 't -> option 't
	default T first() {
		try {
			return get();
		} catch(StopIteration e) {
			return null;
		}
	}
	public static <T> T first(Iter<T> i) {
		return i.first();
	}

	// skip :: iter 't -> int -> iter 't
	default Iter<T> skip(int n) {
		try {
			for(int i=0; i<n; i++) get();
		} catch(StopIteration e) {}
		return this;
	}
	public static <T> Iter<T> skip(Iter<T> i, int n) {
		return i.skip(n);
	}

	// tap :: iter 't -> ('t -> nil) -> iter 't
	default Iter<T> tap(Func.Function1<? super T, ?> f) {
		return () -> {
			T e = get();
			f.fn(e);
			return e;
		};
	}
	public static <T> Iter<T> tap(Iter<T> i, Func.Function1<? super T, ?> f) {
		return i.tap(f);
	}

	// collect :: iter 't -> list 't
	default List<T> collect() {
		List<T> l = new ArrayList<T>();
		try {
			while(true) l.add(get());
		} catch(StopIteration e) {
			return l;
		}
	}
	public static <T> List<T> collect(Iter<T> i) {
		return i.collect();
	}

	// tee :: iter 't -> (iter 't) * (iter 't)
	default Pair<Iter<T>, Iter<T>> tee() {
		Queue<T> qa = new LinkedTransferQueue<T>();
		Queue<T> qb = new LinkedTransferQueue<T>();

		return new Pair<Iter<T>, Iter<T>>(
			() -> {
				if(!qa.isEmpty()) return qa.element();
				T e = get();
				qb.add(e);
				return e;
			},
			() -> {
				if(!qb.isEmpty()) return qb.element();
				T e = get();
				qa.add(e);
				return e;
			}
		);
	}
	public static <T> Pair<Iter<T>, Iter<T>> tee(Iter<T> i) {
		return i.tee();
	}

	// cycle :: iter 't -> iter 't
	default Iter<T> cycle() {
		List<T> l = new ArrayList<T>();
		boolean[] f = {false};
		int[] p = {0};

		return () -> {
			if(f[0]) {
				int i = p[0];
				p[0] = (i+1) % l.size();
				return l.get(i);
			}
			try {
				return get();
			} catch(StopIteration e) {
				return l.get(p[0]++);
			}
		};
	}
	public static <T> Iter<T> cycle(Iter<T> i) {
		return i.cycle();
	}

	// limit :: iter 't -> int -> iter 't
	default Iter<T> limit(int l) {
		int[] n = {0};

		return () -> {
			if(n[0] >= l) throw new StopIteration();
			n[0]++;
			return get();
		};
	}
	public static <T> Iter<T> limit(Iter<T> i, int l) {
		return i.limit(l);
	}

	// zip :: iter 't -> iter 'u -> iter ('t * 'u)
	default <U> Iter<Pair<T, U>> zip(Iter<U> o) {
		return () -> new Pair<T, U>(get(), o.get());
	}
	public static <T, U> Iter<Pair<T, U>> zip(Iter<T> a, Iter<U> b) {
		return a.zip(b);
	}

	// zipwith :: iter 't -> iter 'u -> ('t -> 'u -> 'v) -> iter 'v
	default <U, V> Iter<V> zipwith(Iter<U> o, Func.Function2<? super T, ? super U, ? extends V> f) {
		return zip(o).map(p -> f.fn(p.a, p.b));
	}
	public static <T, U, V> Iter<V> zipwith(Iter<T> t, Iter<U> u, Func.Function2<? super T, ? super U, ? extends V> f) {
		return t.zipwith(u, f);
	}

	// until :: iter 't -> ('t -> bool) -> iter 't
	default Iter<T> until(Func.Predicate1<? super T> p) {
		boolean[] f = {false};
		return () -> {
			if(f[0]) throw new StopIteration();
			T e = get();
			if(p.fn(e)) {
				f[0] = true;
				throw new StopIteration();
			}
			return e;
		};
	}
	public static <T> Iter<T> until(Iter<T> i, Func.Predicate1<? super T> p) {
		return i.until(p);
	}

	// chain :: iter 't -> iter 't -> iter 't
	default Iter<T> chain(Iter<T> o) {
		boolean[] f = {false};
		return () -> {
			if(f[0]) return o.get();
			try {
				return get();
			} catch(StopIteration e) {
				f[0] = true;
				return o.get();
			}
		};
	}
	public static <T> Iter<T> chain(Iter<T> a, Iter<T> b) {
		return a.chain(b);
	}

	// count :: iter 't -> int
	default int count() {
		int c = 0;
		try {
			while(true) {
				get();
				c++;
			}
		} catch(StopIteration e) {
			return c;
		}
	}
	public static <T> int count(Iter<T> i) {
		return i.count();
	}

	// iter :: iterator 't -> iter 't
	public static <T> Iter<T> iter(Iterator<T> i) {
		return () -> {
			try {
				return i.next();
			} catch(NoSuchElementException e) {
				throw new StopIteration();
			}
		};
	}

	// iter :: iterable 't -> iter 't
	public static <T> Iter<T> iter(Iterable<T> i) {
		return iter(i.iterator());
	}
	public static <T> Iter<T> iter(Iter<T> i) {
		return i;
	}

	// iter :: 't[] -> iter 't
	public static <T> Iter<T> iter(T[] a) {
		int[] i = {0};
		int l = a.length;
		return () -> {
			if(i[0] < l) return a[i[0]++];
			throw new StopIteration();
		};
	}

	// keys :: map 'k 'v -> iter 'k
	public static <K> Iter<K> keys(Map<K, ?> m) {
		return iter(m.keySet());
	}

	// values :: map 'k 'v -> iter 'v
	public static <V> Iter<V> values(Map<?, V> m) {
		return iter(m.values());
	}

	// pairs :: map 'k 'v -> iter ('k * 'v)
	public static <K, V> Iter<Pair<K, V>> pairs(Map<K, V> m) {
		return iter(m.entrySet()).map(e -> new Pair<K, V>(e.getKey(), e.getValue()));
	}

	// unzip :: iter ('a * 'b) -> (iter 'a) * (iter 'b)
	public static <A, B> Pair<Iter<A>, Iter<B>> unzip(Iter<Pair<A, B>> i) {
		return i.tee().map(a -> a.map(Pair::a), b -> b.map(Pair::b));
	}

	// collectmap :: iter ('k * 'v) -> map 'k 'v
	public static <K, V> Map<K, V> collectmap(Iter<Pair<K, V>> i) {
		return Maps.pair(i.collect());
	}
}

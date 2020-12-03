package jfun;

import java.util.*;

public final class Lists {
	private Lists() {} // no instances

	// newList :: list 'a -> list 'b
	private static <A, B> List<B> newList(List<A> type) {
		if(type instanceof LinkedList) return new LinkedList<B>();
		else return new ArrayList<B>();
	}

	// map :: list 'a -> ('a -> 'b) -> list 'b
	public static <A, B> List<B> map(List<A> l, Func.Function1<? super A, ? extends B> f) {
		List<B> r = newList(l);
		l.forEach(e -> r.add(f.fn(e)));
		return r;
	}

	// filter :: list 't -> ('t -> bool) -> list 't
	public static <T> List<T> filter(List<T> l, Func.Function1<? super T, Boolean> p) {
		List<T> r = newList(l);
		l.forEach(e -> { if(p.fn(e)) r.add(e); });
		return r;
	}
	public static <T> List<T> filter(List<T> l, Func.Predicate1<? super T> p) {
		return filter(l, p.toFunction1());
	}

	// fold :: list 't -> ('t -> 't -> 't) -> 't -> 't
	public static <T> T fold(List<T> l, Func.Function2<? super T, ? super T, ? extends T> f, T i) {
		for(T e: l) i = f.fn(i, e);
		return i;
	}
	public static <T> T fold(List<T> l, Func.Function1<T, Func.Function1<T, T>> f, T i) {
		return fold(l, Func.uncurrify2(f), i);
	}

	// find :: list 't -> ('t -> bool) -> option 't
	public static <T> T find(List<T> l, Func.Function1<? super T, Boolean> p) {
		for(T e: l) if(p.fn(e)) return e;
		return null;
	}
	public static <T> T find(List<T> l, Func.Predicate1<? super T> p) {
		return find(l, p.toFunction1());
	}

	// findi :: list 't -> ('t -> bool) -> int
	public static <T> int findi(List<T> l, Func.Function1<? super T, Boolean> p) {
		Iterator<T> t = l.iterator();
		for(int i=0; i<l.size(); i++) {
			T e = t.next();
			if(p.fn(e)) return i;
		}
		return -1;
	}
	public static <T> int findi(List<T> l, Func.Predicate1<? super T> p) {
		return findi(l, p.toFunction1());
	}

	// zip :: list 'a -> list 'b -> list ('a * 'b)
	public static <A, B> List<Pair<A, B>> zip(List<A> a, List<B> b) {
		if(a.size() != b.size()) throw new IllegalArgumentException("a and b must be of the same size (" + a.size() + ", " + b.size() + ")");
		List<Pair<A, B>> r = newList(a);
		Iterator<B> bi = b.iterator();
		for(A ae: a) r.add(new Pair<A, B>(ae, bi.next()));
		return r;
	}

	// zipwith :: list 'a -> list 'b -> ('a -> 'b -> 'c) -> list 'c
	public static <A, B, C> List<C> zipwith(List<A> a, List<B> b, Func.Function2<? super A, ? super B, ? extends C> f) {
		if(a.size() != b.size()) throw new IllegalArgumentException("a and b must be of the same size (" + a.size() + ", " + b.size() + ")");
		List<C> r = newList(a);
		Iterator<B> bi = b.iterator();
		for(A ae: a) r.add(f.fn(ae, bi.next()));
		return r;
	}

	// unzip :: list ('a * 'b) -> (list 'a) * (list 'b)
	public static <A, B> Pair<List<A>, List<B>> unzip(List<Pair<A, B>> l) {
		List<A> a = newList(l);
		List<B> b = newList(l);
		for(Pair<A, B> p: l) {
			a.add(p.a);
			b.add(p.b);
		}
		return new Pair<List<A>, List<B>>(a, b);
	}

	// sorted :: list 't -> ('t -> 't -> int) -> list 't
	public static <T> List<T> sorted(List<T> l, Comparator<? super T> c) {
		List<T> r = newList(l);
		r.addAll(l);
		Collections.sort(r, c);
		return r;
	}
	public static <T> List<T> sorted(List<T> l, Func.Function2<T, T, Integer> c) {
		return sorted(l, (Comparator<T>) (a, b) -> c.fn(a, b));
	}
	public static <T> List<T> sorted(List<T> l, Func.Function1<T, Func.Function1<T, Integer>> c) {
		return sorted(l, (Comparator<T>) (a, b) -> c.fn(a).fn(b));
	}
	public static <T extends Comparable<? super T>> List<T> sorted(List<T> l) {
		return sorted(l, Comparator.naturalOrder());
	}

	// contains :: list 't -> 't -> bool
	public static <T> boolean contains(List<T> l, T e) {
		return l.contains(e);
	}

	// some :: list 't -> ('t -> bool) -> bool
	public static <T> boolean some(List<T> l, Func.Predicate1<? super T> p) {
		for(T e: l) if(p.fn(e)) return true;
		return false;
	}

	// all :: list 't -> ('t -> bool) -> bool
	public static <T> boolean all(List<T> l, Func.Predicate1<? super T> p) {
		for(T e: l) if(!p.fn(e)) return false;
		return true;
	}
}

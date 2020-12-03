package jfun;

public final class Pair<A, B> {
	public final A a;
	public final B b;

	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}

	public A a() { return a; }
	public B b() { return b; }

	@Override
	public boolean equals(Object o) {
		if(this==o) return true;
		if(!(o instanceof Pair)) return false;
		Pair<?, ?> p = (Pair<?, ?>) o;
		if((a == null && p.a == null) || (a != null && p.a!= null && a.equals(p.a))) {
			if((b == null && p.b == null) || (b != null && p.b != null && b.equals(p.b))) return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (a == null ? 0 : a.hashCode()) * 7 + (b == null ? 0 : b.hashCode());
	}

	@Override
	public String toString() {
		return "(" + a + ", " + b + ")";
	}

	// map :: 'a * 'b -> ('a -> 'c) -> ('b -> 'd) -> 'c * 'd
	public <C, D> Pair<C, D> map(Func.Function1<? super A, ? extends C> fa, Func.Function1<? super B, ? extends D> fb) {
		return new Pair<>(fa.fn(a), fb.fn(b));
	}
	public static <A, B, C, D> Pair<C, D> map(Pair<A, B> p, Func.Function1<? super A, ? extends C> a, Func.Function1<? super B, ? extends D> b) {
		return p.map(a, b);
	}
}

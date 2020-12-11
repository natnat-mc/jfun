package jfun;

import jfun.Func.*;

public final class Match {
	private Match() {} // no instances

	@SuppressWarnings("unchecked")
	public static <S, R> R match(S obj, When<S, ?, R>... cases) {
		Class<?> c = obj.getClass();
		for(When<S, ?, R> w: cases) {
			if(w.s.isAssignableFrom(c)) return w.apply(obj);
		}
		throw new NoMatchException();
	}

	@SuppressWarnings("unchecked")
	public static <S, R> R match(S obj, Class<R> r, When<S, ?, R>... cases) {
		return match(obj, cases);
	}

	@SuppressWarnings("unchecked")
	public static <S, R> R matchV(S obj, Pair<S, R>... cases) {
		for(Pair<S, R> c: cases) {
			if(Op.eq(c.a, obj)) return c.b;
		}
		throw new NoMatchException();
	}

	@SuppressWarnings("unchecked")
	public static <S, R> R matchV(S obj, Case<S, R>... cases) {
		for(Case<S, R> c: cases) {
			if(Op.eq(c.k, obj)) return c.v.fn();
		}
		throw new NoMatchException();
	}

	public static final class When<S, T, R> {
		public final Class<T> s;
		public final Function1<T, R> f;
		public When(Class<T> s, Function1<T, R> f) {
			this.s = s;
			this.f = f;
		}
		public R apply(S v) {
			return this.f.fn(this.s.cast(v));
		}
	}
	public static final <S, T, R> When<S, T, R> when(Class<T> s, Function1<T, R> f) {
		return new When<S, T, R>(s, f);
	}

	public static final class Case<S, R> {
		public final S k;
		public final Function0<R> v;
		public Case(S k, Function0<R> v) { this.k = k; this.v = v; }
	}
	public static final <S, R> Case<S, R> whenV(S k, Function0<R> v) {
		return new Case<S, R>(k, v);
	}
	public static final <S, R> Case<S, R> whenV(S k, Function1<S, R> v) {
		return new Case<S, R>(k, v.curry(k));
	}

	@SuppressWarnings("serial")
	public static class NoMatchException extends RuntimeException {}

	@SuppressWarnings("unchecked")
	public static <S, D> D magicCast(S s) {
		return (D) s;
	}
}

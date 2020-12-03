package jfun;

// maybe = None | Some 't
public class Maybe<T> {
	private Maybe(T e) { this.e = e; }
	public final T e;

	// None :: maybe 't
	public static <T> Maybe<T> None() { return new Maybe<T>(null); }
	// Some :: 't -> maybe 't
	public static <T> Maybe<T> Some(T e) { return new Maybe<T>(e); }

	// isnone :: maybe 't -> bool
	public boolean isnone() { return e == null; }
	public static <T> boolean isnone(Maybe<T> m) { return m.e == null; }
	// issome :: maybe 't -> bool
	public boolean issome() { return e != null; }
	public static <T> boolean issome(Maybe<T> m) { return m.e != null; }

	// some :: maybe 't -> option 't
	public T some() { return e; }
	public static <T> T some(Maybe<T> m) { return m.e; }

	// map :: maybe 't -> ('t -> 'u) -> maybe 'u
	public <U> Maybe<U> map(Func.Function1<? super T, ? extends U> f) { return e == null ? None() : Some(f.fn(e)); }
	public static <T, U> Maybe<U> map(Maybe<T> m, Func.Function1<? super T, ? extends U> f) { return m.e == null ? None() : Some(f.fn(m.e)); }

	// or :: maybe 't -> 't -> 't
	public T or(T v) { return e == null ? v : e; }
	public static <T> T or(Maybe<T> m, T v) { return m.e == null ? v : m.e; }

	// unify :: maybe 't -> (nil -> 't) -> 't
	public T unify(Func.Function0<? extends T> f) { return e == null ? f.fn() : e; }
	public static <T> T unify(Maybe<T> m, Func.Function0<? extends T> f) { return m.e == null ? f.fn() : m.e; }
}

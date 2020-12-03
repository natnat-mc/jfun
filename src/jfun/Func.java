package jfun;

import java.util.function.*;

public final class Func {
	private Func() {} // no instances

	@FunctionalInterface
	public static interface Predicate1<A> {
		boolean fn(A a);
		default Predicate<A> toPredicate() { return a -> fn(a); }
		default Function1<A, Boolean> toFunction1() { return a -> fn(a); }

		default Predicate1<A> negate() { return a -> !fn(a); }
	}

	@FunctionalInterface
	public static interface Function0<R> {
		R fn();
		default Supplier<R> toProducer() { return () -> fn(); }
	}

	@FunctionalInterface
	public static interface Function1<A, R> {
		R fn(A a);
		default Function<A, R> toFunction() { return a -> fn(a); }

		default Function0<R> curry(A a) { return () -> fn(a); }

		default <S> Function1<A, S> andThen(Function1<R, S> f) { return a -> f.fn(fn(a)); }
	}

	@FunctionalInterface
	public static interface Function2<A, B, R> {
		R fn(A a, B b);
		default BiFunction<A, B, R> toBiFunction() { return (a, b) -> fn(a, b); }
		default Function1<Pair<A, B>, R> toPairFunction() { return p -> fn(p.a, p.b); }

		default Function1<B, R> curry(A a) { return (b) -> fn(a, b); }
		default Function0<R> curry(A a, B b) { return () -> fn(a, b); }

		default Function1<A, Function1<B, R>> curryfy() { return a -> b -> fn(a, b); }
	}

	@FunctionalInterface
	public static interface Function3<A, B, C, R> {
		R fn(A a, B b, C c);

		default Function2<B, C, R> curry(A a) { return (b, c) -> fn(a, b, c); }
		default Function1<C, R> curry(A a, B b) { return (c) -> fn(a, b, c); }
		default Function0<R> curry(A a, B b, C c) { return () -> fn(a, b, c); }

		default Function1<A, Function1<B, Function1<C, R>>> curryfy() { return a -> b -> c -> fn(a, b, c); }
	}

	@FunctionalInterface
	public static interface Function4<A, B, C, D, R> {
		R fn(A a, B b, C c, D d);

		default Function3<B, C, D, R> curry(A a) { return (b, c, d) -> fn(a, b, c, d); }
		default Function2<C, D, R> curry(A a, B b) { return (c, d) -> fn(a, b, c, d); }
		default Function1<D, R> curry(A a, B b, C c) { return (d) -> fn(a, b, c, d); }
		default Function0<R> curry(A a, B b, C c, D d) { return () -> fn(a, b, c, d); }

		default Function1<A, Function1<B, Function1<C, Function1<D, R>>>> curryfy() { return a -> b -> c -> d -> fn(a, b, c, d); }
	}

	// produce :: 't -> nil -> 't
	public static <T> Function0<T> produce(T e) { return () -> e; }

	// _if :: ('a -> bool) -> ('a -> 'r) -> ('a -> 'r) -> ('a -> 'r)
	public static <A, R> Function1<A, R> _if(Predicate1<A> p, Function1<A, R> t, Function1<A, R> f) { return a -> p.fn(a) ? t.fn(a) : f.fn(a); }
	// _if :: ('a -> bool) -> (nil -> 'r) -> (nil -> 'r) -> ('a -> 'r)
	public static <A, R> Function1<A, R> _if(Predicate1<A> p, Function0<R> t, Function0<R> f) { return a -> p.fn(a) ? t.fn() : f.fn(); }
	// _ifv :: ('a -> bool) -> 'r -> 'r -> ('a -> 'r')
	public static <A, R> Function1<A, R> _ifv(Predicate1<A> p, R t, R f) { return a -> p.fn(a) ? t : f; }

	// compose :: ('a -> 'b) -> ('b -> 'c) -> ('a -> 'c)
	public static <A, B, C> Function1<A, C> compose(Function1<A, B> a, Function1<B, C> b) { return a.andThen(b); }

	// negate :: ('a -> bool) -> ('a -> bool)
	public static <A> Predicate1<A> negate(Predicate1<A> p) { return p.negate(); }
	public static <A> Predicate1<A> negate(Function1<A, Boolean> p) { return a -> !p.fn(a); }

	public static <A, B, R> Function2<A, B, R> uncurrify2(Function1<A, Function1<B, R>> f) { return (a, b) -> f.fn(a).fn(b); }
	public static <A, B, C, R> Function3<A, B, C, R> uncurrify3(Function1<A, Function1<B, Function1<C, R>>> f) { return (a, b, c) -> f.fn(a).fn(b).fn(c); }
	public static <A, B, C, D, R> Function4<A, B, C, D, R> uncurrify4(Function1<A, Function1<B, Function1<C, Function1<D, R>>>> f) { return (a, b, c, d) -> f.fn(a).fn(b).fn(c).fn(d); }

	public static <A> Predicate1<A> convertP1(Predicate<A> p) { return p::test; }
	public static Predicate<Integer> convertP1(IntPredicate p) { return p::test; }
	public static Predicate<Double> convertP1(DoublePredicate p) { return p::test; }
	public static Predicate<Long> convertP1(LongPredicate p) { return p::test; }
	public static <A> Predicate1<A> convertP1(Function<A, Boolean> p) { return p::apply; }
	public static <A> Predicate1<A> convertP1(Function1<A, Boolean> p) { return p::fn; }

	public static <R> Function0<R> convertF0(Supplier<R> s) { return s::get; }
	public static Function0<Boolean> convertF0(BooleanSupplier s) { return s::getAsBoolean; }
	public static Function0<Integer> convertF0(IntSupplier s) { return s::getAsInt; }
	public static Function0<Double> convertF0(DoubleSupplier s) { return s::getAsDouble; }
	public static Function0<Long> convertF0(LongSupplier s) { return s::getAsLong; }

	public static <A, R> Function1<A, R> convertF1(Function<A, R> f) { return f::apply; }
	public static <A> Function1<A, Boolean> convertF1(Predicate<A> p) { return p::test; }
	public static <A> Function1<A, Boolean> convertF1(Predicate1<A> p) { return p::fn; }
	public static Function1<Integer, Boolean> convertF1(IntPredicate p) { return p::test; }
	public static Function1<Double, Boolean> convertF1(DoublePredicate p) { return p::test; }
	public static Function1<Long, Boolean> convertF1(LongPredicate p) { return p::test; }
	public static <R> Function1<Integer, R> convertF1(IntFunction<R> f) { return f::apply; }
	public static <R> Function1<Double, R> convertF1(DoubleFunction<R> f) { return f::apply; }
	public static <R> Function1<Long, R> convertF1(LongFunction<R> f) { return f::apply; }
	public static Function1<Integer, Double> convertF1(IntToDoubleFunction f) { return f::applyAsDouble; }
	public static Function1<Integer, Long> convertF1(IntToLongFunction f) { return f::applyAsLong; }
	public static Function1<Double, Integer> convertF1(DoubleToIntFunction f) { return f::applyAsInt; }
	public static Function1<Double, Long> convertF1(DoubleToLongFunction f) { return f::applyAsLong; }
	public static Function1<Long, Double> convertF1(LongToDoubleFunction f) { return f::applyAsDouble; }
	public static Function1<Long, Integer> convertF1(LongToIntFunction f) { return f::applyAsInt; }
	public static Function1<Integer, Integer> convertF1(IntUnaryOperator o) { return o::applyAsInt; }
	public static Function1<Double, Double> convertF1(DoubleUnaryOperator o) { return o::applyAsDouble; }
	public static Function1<Long, Long> convertF1(LongUnaryOperator o) { return o::applyAsLong; }

	public static <A, B, R> Function2<A, B, R> convertF(BiFunction<A, B, R> f) { return (a, b) -> f.apply(a, b); }
	//TODO the rest of the java.util.function package
}

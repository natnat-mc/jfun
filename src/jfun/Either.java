package jfun;

// either = Left 'a | Right 'b
public class Either<A, B> {

	public static enum Side { LEFT, RIGHT }

	private Either(A a, B b, Side side) { this.a = a; this.b = b; this.side = side; }

	public final A a;
	public final B b;
	public final Side side;

	// Left :: 'a -> either 'a 'b
	public static <A, B> Either<A, B> Left(A a) { return new Either<>(a, null, Side.LEFT); }
	// Right :: 'b -> either 'a 'b
	public static <A, B> Either<A, B> Right(B b) { return new Either<>(null, b, Side.RIGHT); }

	// left :: either 'a 'b -> option 'a
	public static <A> A left(Either<A, ?> e) { if(e.side == Side.LEFT) return e.a; return null; }
	// right :: either 'a 'b -> option 'b
	public static <B> B right(Either<?, B> e) { if(e.side == Side.RIGHT) return e.b; return null; }
	// side :: either 'a 'b -> LEFT | RIGHT
	public static Side side(Either<?, ?> e) { return e.side; }

	// map :: either 'a 'b -> ('a -> 'c) -> ('b -> 'd) -> either 'c 'd
	public static <A, B, C, D> Either<C, D> map(Either<A, B> e, Func.Function1<? super A, ? extends C> a, Func.Function1<? super B, ? extends D> b) {
		return e.side == Side.LEFT ? Left(a.fn(e.a)) : Right(b.fn(e.b));
	}
	// mapleft :: either 'a 'b -> ('a -> 'c) -> either 'c 'b
	public static <A, B, C> Either<C, B> mapleft(Either<A, B> e, Func.Function1<? super A, ? extends C> f) {
		return e.side == Side.LEFT ? Left(f.fn(e.a)) : Right(e.b);
	}
	// mapright :: either 'a 'b -> ('b -> 'c) -> either 'a 'c
	public static <A, B, C> Either<A, C> mapright(Either<A, B> e, Func.Function1<? super B, ? extends C> f) {
		return e.side == Side.LEFT ? Left(e.a) : Right(f.fn(e.b));
	}
	// unify :: either 'a 'b -> ('a -> 'c) -> ('b -> 'c) -> 'c
	public static <A, B, C> C unify(Either<A, B> e, Func.Function1<? super A, ? extends  C> a, Func.Function1<? super B, ? extends C> b) {
		return e.side == Side.LEFT ? a.fn(e.a) : b.fn(e.b);
	}
}

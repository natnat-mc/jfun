package jfun;

import java.util.*;

public final class Op {
	private Op() {} // no instances

	// ident :: 'a -> 'a
	public static <A> A ident(A a) { return a; }

	// eq :: 'a -> 'b -> bool
	public static <A, B> boolean eq(A a, B b) { return (a == null && b == null) || (a != null && b != null && a.equals(b)); }

	// neq :: 'a -> 'b -> bool
	public static <A, B> boolean neq(A a, B b) { return !eq(a, b); }

	// stricteq :: 'a -> 'b -> bool
	public static <A, B> boolean stricteq(A a, B b) { return a == b; }

	// strictneq :: 'a -> 'b -> bool
	public static <A, B> boolean strictneq(A a, B b) { return a != b; }

	// isnull :: 't -> bool
	public static <T> boolean isnull(T o) { return o == null; }

	// notnull :: 't -> bool
	public static <T> boolean notnull(T o) { return o != null; }

	// cmp :: 't -> 't -> int
	public static <T extends Comparable<? super T>> int cmp(T a, T b) { return a.compareTo(b); }

	// lt :: 't -> 't -> bool
	public static <T extends Comparable<? super T>> boolean lt(T a, T b) { return cmp(a, b) < 0; }

	// ge :: 't -> 't -> bool
	public static <T extends Comparable<? super T>> boolean le(T a, T b) { return cmp(a, b) <= 0; }

	// gt :: 't -> 't -> bool
	public static <T extends Comparable<? super T>> boolean gt(T a, T b) { return cmp(a, b) > 0; }

	// ge :: 't -> 't -> bool
	public static <T extends Comparable<? super T>> boolean ge(T a, T b) { return cmp(a, b) >= 0; }

	// add :: int -> int -> int
	public static int add(int a, int b) { return a + b; }
	// add :: double -> double -> double
	public static double add(double a, double b) { return a + b; }
	// add :: long -> long -> long
	public static long add(long a, long b) { return a + b; }

	// sub :: int -> int -> int
	public static int sub(int a, int b) { return a - b; }
	// sub :: double -> double -> double
	public static double sub(double a, double b) { return a - b; }
	// sub :: long -> long -> long
	public static long sub(long a, long b) { return a - b; }

	// neg :: int -> int
	public static int neg(int a) { return -a; }
	// neg :: double -> double
	public static double neg(double a) { return -a; }
	// neg :: long -> double
	public static long neg(long a) { return -a; }
	// neg :: bool -> bool
	public static boolean neg(boolean a) { return !a; }

	// mul :: int -> int -> int
	public static int mul(int a, int b) { return a * b; }
	// mul :: double -> double -> double
	public static double mul(double a, double b) { return a * b; }
	// mul :: long -> long -> long
	public static long mul(long a, long b) { return a * b; }

	// div :: int -> int -> int
	public static int div(int a, int b) { return a / b; }
	// div :: double -> double -> double
	public static double div(double a, double b) { return a / b; }
	// div :: long -> long -> long
	public static long div(long a, long b) { return a / b; }

	// mod :: int -> int -> int
	public static int mod(int a, int b) { return a % b; }
	// mod :: double -> double -> double
	public static double mod(double a, double b) { return a % b; }
	// mod :: long -> long -> long
	public static long mod(long a, long b) { return a % b; }

	// concat :: str -> str -> str
	public static String concat(String a, String b) { return a + b; }
	// concat :: str -> int -> str
	public static String concat(String a, int b) { return a + b; }
	// concat :: str -> double -> str
	public static String concat(String a, double b) { return a + b; }
	// concat :: str -> long -> str
	public static String concat(String a, long b) { return a + b; }
	// concat :: str -> bool -> str
	public static String concat(String a, boolean b) { return a + b; }

	// _ifv :: bool -> 't -> 't -> 't
	public static <T> T _ifv(boolean b, T t, T f) { return b ? t : f; }
}

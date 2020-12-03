# Available functions:
```ocaml
Left :: 'a -> either 'a 'b
Right :: 'b -> either 'a 'b
_if :: ('a -> bool) -> ('a -> 'r) -> ('a -> 'r) -> ('a -> 'r)
_if :: ('a -> bool) -> (nil -> 'r) -> (nil -> 'r) -> ('a -> 'r)
_ifv :: ('a -> bool) -> 'r -> 'r -> ('a -> 'r')
_ifv :: bool -> 't -> 't -> 't
add :: double -> double -> double
add :: int -> int -> int
add :: long -> long -> long
chain :: iter 't -> iter 't -> iter 't
cmp :: 't -> 't -> int
collect :: iter 't -> list 't
collectmap :: iter ('k * 'v) -> map 'k 'v
compose :: ('a -> 'b) -> ('c -> 'd) -> ('a -> 'd)
concat :: str -> bool -> str
concat :: str -> double -> str
concat :: str -> int -> str
concat :: str -> long -> str
concat :: str -> str -> str
contains :: list 't -> 't -> bool
containsk :: map 'k 'v -> 'k -> bool
containskv :: map 'k 'v -> 'k * 'v -> bool
containskv :: map 'k 'v -> 'k -> 'v -> bool
containsv :: map 'k 'v -> 'v -> bool
count :: iter 't -> int
cycle :: iter 't -> iter 't
div :: double -> double -> double
div :: int -> int -> int
div :: long -> long -> long
eq :: 'a -> 'b -> bool
filter :: iter 't -> ('t -> bool) -> iter 't
filter :: list 't -> ('t -> bool) -> list 't
filterk :: map 'k 'v -> ('k -> bool) -> map 'k 'v
filterkv :: map 'k 'v -> ('k * 'v -> bool) -> map 'k 'v
filterkv :: map 'k 'v -> ('k -> 'v -> bool) -> map 'k 'v
filterv :: map 'k 'v -> ('v -> bool) -> map 'k 'v
find :: list 't -> ('t -> bool) -> option 't
findi :: list 't -> ('t -> bool) -> int
first :: iter 't -> option 't
fold :: iter 't -> ('t -> 't -> 't) -> 't -> 't
fold :: list 't -> ('t -> 't -> 't) -> 't -> 't
ge :: 't -> 't -> bool
gt :: 't -> 't -> bool
ident :: 'a -> 'a
isnull :: 't -> bool
iter :: 't[] -> iter 't
iter :: iterable 't -> iter 't
iter :: iterator 't -> iter 't
keys :: map 'k 'v -> iter 'k
keys :: map 'k 'v -> list 'k
left :: either 'a 'b -> option 'a
limit :: iter 't -> int -> iter 't
lt :: 't -> 't -> bool
map :: 'a * 'b -> ('a -> 'c) -> ('b -> 'd) -> 'c * 'd
map :: either 'a 'b -> ('a -> 'c) -> ('b -> 'd) -> either 'c 'd
map :: iter 't -> ('t -> 'u) -> 'u
map :: list 'a -> ('a -> 'b) -> list 'b
mapk :: map 'k 'v -> ('k -> 'l) -> map 'l 'v
mapkv :: map 'k 'v -> ('k * 'v -> 'l * 'w) -> map 'l 'w
mapkv :: map 'k 'v -> ('k -> 'l) -> ('v -> 'w) -> map 'l 'w
mapleft :: either 'a 'b -> ('a -> 'c) -> either 'c 'b
mapright :: either 'a 'b -> ('b -> 'c) -> either 'a 'c
mapv :: map 'k 'v -> ('v -> 'w) -> map 'k 'w
mod :: double -> double -> double
mod :: int -> int -> int
mod :: long -> long -> long
mul :: double -> double -> double
mul :: int -> int -> int
mul :: long -> long -> long
neg :: bool -> bool
neg :: double -> double
neg :: int -> int
neg :: long -> double
negate :: ('a -> bool) -> ('a -> bool)
neq :: 'a -> 'b -> bool
newList :: list 'a -> list 'b
newMap :: map 'k 'v -> map 'k 'v
notnull :: 't -> bool
pair :: list ('k * 'v) -> map 'k 'v
pairs :: map 'k 'v -> iter ('k * 'v)
pairs :: map 'k 'v -> list ('k * 'v)
peek :: iter 't -> option 't * iter 't
produce :: 't -> nil -> 't
right :: either 'a 'b -> option 'b
side :: either 'a 'b -> LEFT | RIGHT
skip :: iter 't -> int -> iter 't
sorted :: list 't -> ('t -> 't -> int) -> list 't
stricteq :: 'a -> 'b -> bool
strictneq :: 'a -> 'b -> bool
sub :: double -> double -> double
sub :: int -> int -> int
sub :: long -> long -> long
tap :: iter 't -> ('t -> nil) -> iter 't
tee :: iter 't -> (iter 't) * (iter 't)
tofn :: iter 't -> (nil -> 't)
unify :: either 'a 'b -> ('a -> 'c) -> ('b -> 'c) -> 'c
until :: iter 't -> ('t -> bool) -> iter 't
unzip :: iter ('a * 'b) -> (iter 'a) * (iter 'b)
unzip :: list ('a * 'b) -> (list 'a) * (list 'b)
values :: map 'k 'v -> iter 'v
values :: map 'k 'v -> list 'v
zip :: iter 't -> iter 'u -> iter ('t * 'u)
zip :: list 'a -> list 'b -> list ('a * 'b)
zipwith :: iter 't -> iter 'u -> ('t -> 'u -> 'v) -> iter 'v
zipwith :: list 'a -> list 'b -> ('a -> 'b -> 'c) -> list 'c
```

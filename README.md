# Shopping Summit

Start by [installing Clojure][clojure]
and [downloading Leiningen][lein].
Both should work just fine with Java 8 or 9.
Clone this repository or download a [zip archive][zip] with its contents.
In the root directory of the project invoke the following command.

    lein test

It will download 20ish MB worth of dependencies and run some tests.

Open `project.clj` in your favourite text editor or IDE
to check if syntax highlighting works as expected.

Start the Clojure read-eval-print loop (also known as a REPL):

    lein repl

and experiment with some Clojure expressions:

```clojure
user=> (+ 0.1 0.2)
0.30000000000000004
user=> (for [x [2/10 0.2 4]] (+ x 1/10))
(3/10 0.30000000000000004 41/10)
user=> (.println System/out "Servus Weλt!")
Servus Weλt!
nil
user=> (sort (keys (System/getProperties)))
("awt.toolkit" "clojure.compile.path" ...)
```

You're good to go.

## Tests

In the `test` directory you can find a lot of tests covering
functionality we'll implement over the course of the workshop.
Most of them are commented out; they will be ignored until the
time we need them.

Take a look at `test/shopping_summit/entities/cart_test.clj`.
Code preceded with a `#_` is commented out.
Remove a `#_` sign to uncomment the expression follownig it.

Tests can be ran either in your terminal using

    lein test

or in a REPL. To do it, start by reloading the test namespace of
interest along with all namespaces it depends on.

```clojure
(require 'shopping-summit.entities.cart-test :reload-all)
(clojure.test/test-ns 'shopping-summit.entities.cart-test)
```

Notice that testing in the REPL is much faster.
We don't have to restart the JVM; we just dynamically
recompile the code in a running Clojure environment.

## Anything else

Just ask me; I'm happy to help.

— Jan

[clojure]: https://clojure.org/guides/getting_started
[lein]: https://leiningen.org
[zip]: https://github.com/jstepien/shopping/archive/master.zip

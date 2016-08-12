This is a simple and fast implementation of a ring buffer, written in Scala.

##For the impatient

    $ ./sbt clean test


## Documentation

There are only 3 functions: ``put``, ``take`` and ``size``.

The snippet of code below is all you need to know:

    val capacity =  4
    val sentinel = -1
    val ring = new RingBuffer(capacity, sentinel)
    assert(ring.put(11) == 11)       // succeeded
    assert(ring.put(12) == sentinel) // failed
    assert(ring.take    == 6)        // succeeded
    assert(ring.size    == 3)        // number of elements stored


## Design Principles

. O(1) cost in regime operation;
. Never allocates objects in regime operation;
. Never provokes garbage collections, not even indirectly, in regime operation;
. Never boxes/unboxes input/output values;
. Never converts values from one type to another;
. Returns a sentinel value when the ring buffer is empty or full;
. Not thread-safe, lock-free implementation: leave this concern to the caller;
. Fail-fast when internal buffer is full or empty: leave this concern to the caller.

In order to achieve this, there's a small price to pay in regards to clarity: it's necessary
to define a *sentinel* value which means that the ring buffer was empty and nothing was taken
from it; or the ring buffer was full and it was not possible to put the passed value into
the ring buffer.

Suppose, for example, that you are going to store positive integers into the ring buffer; any
negative value would be a good sentinel. Or suppose you are going to store case classes into
the ring buffer; in this case, pass a ``case object`` with special meaning in your application.

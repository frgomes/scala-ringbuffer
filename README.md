This is a simple, no-frills implementation of a ring buffer, written in Scala.

When an element in put into the ring buffer, you will get an ``Option[T]`` for it, otherwise you will get None.
When you take an element from the ring buffer, you will get an ``Option[T]`` for it, otherwise you will get None.
In other words, in case of buffer full or buffer empty, it simply returns None. It's your responsibility to handle
these situations.

Multiple insertions and/or multiple removals are not supported.

This implementation is *not* thread safe.


##For the impatient

    $ ./sbt clean test


## Documentation

There are only 3 funtions: ``put``, ``take`` and ``size``.

The snippet of code below is all you need to know:

    val ring = new RingBuffer[Int](4)
    assert(ring.put(11).isDefined)
    assert(ring.put(12).isEmpty)
    assert(ring.take == Some(6))
    assert(ring.size == 3)


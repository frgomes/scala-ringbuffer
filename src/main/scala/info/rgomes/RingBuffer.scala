package info.rgomes

import net.jcip.annotations.NotThreadSafe

import scala.reflect.ClassTag


// @formatter:off


trait RingLike[@scala.specialized T] {
  def size: Int
  /** Put one element onto the tail position of the ring buffer. Returns None if failed. */
  def put(o: T): T
  /** Gets one element from the head position of the ring buffer. Returns None if failed. */
  def take: T
}

/** A very simple, naive and not thread safe ring buffer.
  * <p/>
  * Design Principles
  *<ul>
  * <li>O(1) cost in regime operation;</li>
  * <li>Never allocates objects in regime operation;</li>
  * <li>Never provokes garbage collections, not even indirectly, in regime operation;</li>
  * <li>Not thread-safe, lock-free implementation: leave this concern to the caller;</li>
  * <li>Fail-fast when internal buffer is full or empty: leave this concern to the caller.</li>
  *</ul>
  * In order to achieve this, there's a small price to pay in regards to clarity: it's necessary
  * to define a sentinel value which means that the ring buffer was empty and nothing was taken
  * from it; or the ring buffer was full and it was not possible to put the passed value into
  * the ring buffer.
  *<p/>
  * Suppose, for example, that you are going to store positive integers into the ring buffer; any
  * negative value would be a good sentinel. Or suppose you are going to store case classes into
  * the ring buffer; in this case, pass a ``case object`` with special meaning in your application.
  */
@NotThreadSafe
class RingBuffer[T:ClassTag](val capacity: Int, val sentinel: T)
  extends RingLike[T] {
  //XXX assert(capacity>0 && capacity < Int.MaxValue)

  // invariants:
  // * head     is equal to tail -> the buffer is empty
  // * (head+1) is equal to tail -> the buffer is full
  // * tail always points to a sentinel, which is necessarily free

  private var head: Int = 0
  private var tail: Int = 0

  private val len  = capacity+1
  private val ring = new Array[T](len)

  override def size: Int = if(head>=tail) head-tail else len-tail+head

  override def put(o: T): T = {
    var next = head+1
    next = if(next>=len) 0 else next
    if(next==tail)
      sentinel
    else {
      ring(head) = o
      head = next
      o
    }
  }

  override def take: T = {
    if(head==tail)
      sentinel
    else {
      val o = ring(tail)
      var next = tail+1
      tail = if(next>=len) 0 else next
      o
    }
  }
}

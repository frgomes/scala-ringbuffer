package info.rgomes

import scala.reflect.ClassTag
import net.jcip.annotations.NotThreadSafe


// @formatter:off


trait RingLike[T] {
  def size: Int
  /** Put one element onto the tail position of the ring buffer. Returns None if failed. */
  def put(o: T): Option[T]
  /** Gets one element from the head position of the ring buffer. Returns None if failed. */
  def take: Option[T]
}

//** A very simple, naive and not thread safe ring buffer. */
@NotThreadSafe
class RingBuffer[T:ClassTag](val capacity: Int) extends RingLike[T] {
  assert(capacity>0 && capacity < Int.MaxValue)

  // invariants:
  // * head     is equal to tail -> the buffer is empty
  // * (head+1) is equal to tail -> the buffer is full
  // * tail always points to a sentinel, which is necessarily free

  private var head: Int = 0
  private var tail: Int = 0

  private val len  = capacity+1
  private val ring = new Array[T](len)

  override def size: Int = if(head>=tail) head-tail else len-tail+head

  override def put(o: T): Option[T] = {
    var next = head+1
    next = if(next>=len) 0 else next
    if(next==tail)
      None
    else {
      ring(head) = o
      head = next
      Some(o)
    }
  }

  override def take: Option[T] = {
    if(head==tail)
      None
    else {
      val o = ring(tail)
      var next = tail+1
      tail = if(next>=len) 0 else next
      Some(o)
    }
  }
}

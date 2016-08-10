package info.rgomes

import utest._

// @formatter:off


object RingBufferSpec extends TestSuite {
  val tests = this {
    val ring = new RingBuffer[Int](4)

    "Tests its initial size"-{
      assert(ring.size == 0)
    }

    "Try to get an element from it"-{
      assert(ring.take == None)
    }

    "Ability to put elements until it is full"-{
      assert(ring.put(1) == Some(1))
      assert(ring.size   == 1)
      assert(ring.put(2) == Some(2))
      assert(ring.size   == 2)
      assert(ring.put(3) == Some(3))
      assert(ring.size   == 3)
      assert(ring.put(4) == Some(4))
      assert(ring.size   == 4)
      assert(ring.put(5) == None)
      assert(ring.size   == 4)
      assert(ring.put(6) == None)
      assert(ring.size   == 4)
    }

    "Ability to take elements until it is empty"-{
      assert(ring.put(1) == Some(1))
      assert(ring.put(2) == Some(2))
      assert(ring.put(3) == Some(3))
      assert(ring.put(4) == Some(4))
      assert(ring.take   == Some(1))
      assert(ring.take   == Some(2))
      assert(ring.take   == Some(3))
      assert(ring.take   == Some(4))
      assert(ring.take   == None)
      assert(ring.take   == None)
    }

    "Ability to roll over the internal boundaries."-{
      // i.e: ability to manage ``head`` and ``tail`` properly.
      "First we put two elements and take one in each test."-{
        assert(ring.put(1).isDefined)
        assert(ring.put(2).isDefined)
        assert(ring.take == Some(1))
        assert(ring.size == 1)

        assert(ring.put(3).isDefined)
        assert(ring.put(4).isDefined)
        assert(ring.take == Some(2))
        assert(ring.size == 2)

        assert(ring.put(5).isDefined)
        assert(ring.put(6).isDefined)
        assert(ring.take == Some(3))
        assert(ring.size == 3)

        assert(ring.put(7).isDefined)
        assert(ring.put(8).isEmpty)
        assert(ring.take == Some(4))
        assert(ring.size == 3)


        assert(ring.put(11).isDefined)
        assert(ring.put(12).isEmpty)
        assert(ring.take == Some(6))
        assert(ring.size == 3)

        assert(ring.put(13).isDefined)
        assert(ring.put(14).isEmpty)
        assert(ring.take == Some(7))
        assert(ring.size == 3)

        assert(ring.put(15).isDefined)
        assert(ring.put(16).isEmpty)
        assert(ring.take == Some(9))
        assert(ring.size == 3)

        assert(ring.put(17).isDefined)
        assert(ring.put(18).isEmpty)
        assert(ring.take == Some(11))
        assert(ring.size == 3)

        assert(ring.put(19).isDefined)
        assert(ring.put(20).isEmpty)
        assert(ring.take == Some(13))
        assert(ring.size == 3)
      }
    }
  }
}

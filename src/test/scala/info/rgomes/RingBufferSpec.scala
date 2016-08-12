package info.rgomes

import utest._

// @formatter:off


object RingBufferSpec extends TestSuite {
  val tests = this {
    val sentinel = -1
    val ring = new RingBuffer(4, sentinel)

    "Tests its initial size"-{
      assert(ring.size == 0)
    }

    "Try to get an element from it"-{
      assert(ring.take == sentinel)
    }

    "Ability to put elements until it is full"-{
      assert(ring.put(1) == 1)
      assert(ring.size   == 1)
      assert(ring.put(2) == 2)
      assert(ring.size   == 2)
      assert(ring.put(3) == 3)
      assert(ring.size   == 3)
      assert(ring.put(4) == 4)
      assert(ring.size   == 4)
      assert(ring.put(5) == sentinel)
      assert(ring.size   == 4)
      assert(ring.put(6) == sentinel)
      assert(ring.size   == 4)
    }

    "Ability to take elements until it is empty"-{
      assert(ring.put(1) == 1)
      assert(ring.put(2) == 2)
      assert(ring.put(3) == 3)
      assert(ring.put(4) == 4)
      assert(ring.take   == 1)
      assert(ring.take   == 2)
      assert(ring.take   == 3)
      assert(ring.take   == 4)
      assert(ring.take   == sentinel)
      assert(ring.take   == sentinel)
    }

    "Ability to roll over the internal boundaries."-{
      // i.e: ability to manage ``head`` and ``tail`` properly.
      "First we put two elements and take one in each test."-{
        assert(ring.put(1) == 1)
        assert(ring.put(2) == 2)
        assert(ring.take   == 1)
        assert(ring.size   == 1)

        assert(ring.put(3) == 3)
        assert(ring.put(4) == 4)
        assert(ring.take   == 2)
        assert(ring.size   == 2)

        assert(ring.put(5) == 5)
        assert(ring.put(6) == 6)
        assert(ring.take   == 3)
        assert(ring.size   == 3)

        assert(ring.put(7) == 7)
        assert(ring.put(8) == sentinel)
        assert(ring.take   == 4)
        assert(ring.size   == 3)


        assert(ring.put(11) == 11)
        assert(ring.put(12) == sentinel)
        assert(ring.take    == 5)
        assert(ring.size    == 3)

        assert(ring.put(13) == 13)
        assert(ring.put(14) == sentinel)
        assert(ring.take    == 6)
        assert(ring.size    == 3)

        assert(ring.put(15) == 15)
        assert(ring.put(16) == sentinel)
        assert(ring.take    == 7)
        assert(ring.size    == 3)

        assert(ring.put(17) == 17)
        assert(ring.put(18) == sentinel)
        assert(ring.take    == 11)
        assert(ring.size    == 3)

        assert(ring.put(19) == 19)
        assert(ring.put(20) == sentinel)
        assert(ring.take    == 13)
        assert(ring.size    == 3)
      }
    }
  }
}

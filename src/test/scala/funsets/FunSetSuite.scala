package funsets

import org.junit._

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite {

  import FunSets._

  @Test def `contains is implemented`: Unit = {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   * val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remvoe the
   *
   * @Ignore annotation.
   */
  @Test def `singleton set one contains one`: Unit = {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  @Test def `union contains all elements of each set`: Unit = {
    new TestSets {
      val s: _root_.funsets.FunSets.FunSet = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")

      val unionOfAll: _root_.funsets.FunSets.FunSet = union(s, s3)
      assert(contains(unionOfAll, 1), "Union 1")
      assert(contains(unionOfAll, 2), "Union 2")
      assert(contains(unionOfAll, 3), "Union 3")
    }
  }

  @Test def `intersect contains no elements of each set`: Unit = {
    new TestSets {
      val s: _root_.funsets.FunSets.FunSet = intersect(s1, s2)
      assert(!contains(s, 1), "intersect 1")
      assert(!contains(s, 2), "intersect 2")
      assert(!contains(s, 3), "intersect 3")

      val intersectOfAll: _root_.funsets.FunSets.FunSet = intersect(s, s3)
      assert(!contains(intersectOfAll, 1), "intersect 1")
      assert(!contains(intersectOfAll, 2), "intersect 2")
      assert(!contains(intersectOfAll, 3), "intersect 3")
    }
  }

  @Test def `diff contains no elements of each set`: Unit = {
    new TestSets {
      val s: _root_.funsets.FunSets.FunSet = diff(s1, s2)
      assert(contains(s, 1), "diff 1")
      assert(!contains(s, 2), "diff 2")
      assert(!contains(s, 3), "diff 3")

      val diffAllSets: _root_.funsets.FunSets.FunSet = diff(s, s3)
      assert(contains(diffAllSets, 1), "diff 1")
      assert(!contains(diffAllSets, 2), "diff 2")
      assert(!contains(diffAllSets, 3), "diff 3")
    }
  }

  @Test def `filter elements of each set`: Unit = {
    new TestSets {
      assert(contains(filter(s1, x => x == 1), 1), "not found 1")
      assert(!contains(filter(s1, x => x > 1), 1), "found > 1")

      assert(contains(filter(s2, x => x == 2), 2), "not found 2")
      assert(!contains(filter(s2, x => x > 2), 2), "found > 2")

      assert(contains(filter(s3, x => x == 3), 3), "not found 3")
      assert(!contains(filter(s3, x => x > 3), 3), "found > 3")
    }
  }

  @Test def `set has any element`: Unit = {
    new TestSets {
      val unionOfAll: _root_.funsets.FunSets.FunSet = union(union(s1, s2), s3)
      assert(forall(unionOfAll, x => x >= 1), "some element is not greater than or equal to 1")
      assert(!forall(unionOfAll, x => x == 2), "all elements are equal to 2")
    }
  }

  @Test def `add 2 to all elements in the set`: Unit = {
    new TestSets {
      val unionOfAll: _root_.funsets.FunSets.FunSet = union(union(s1, s2), s3)
      val addTwo: _root_.funsets.FunSets.FunSet = map(unionOfAll, x => x + 2)
      assert(contains(addTwo, 3), "add 2 to one is failed")
      assert(contains(addTwo, 4), "add 2 to 2 is failed")
      assert(contains(addTwo, 5), "add 2 to 3 is failed")

      //
      printSet(unionOfAll)
    }
  }


  @Rule def individualTestTimeout = new org.junit.rules.Timeout(10 * 1000)
}

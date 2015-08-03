import net.ettinsmoor.zipped.{ZippedTextFileIterable, ZippedTextFileIterator}
import org.scalatest._

class BasicTests extends FlatSpec with Matchers {
  val zip_name = getClass.getResource("/jabberwocky.zip").getFile
  val stream = getClass.getResourceAsStream("/jabberwocky.txt")
  val txt = scala.io.Source.fromInputStream(stream).getLines()

  "When iterating over a zipped text file, every line" should "be exactly the same as the unzipped text file" in {
    val zip_iter = new ZippedTextFileIterator(zip_name, "jabberwocky.txt")

    val zarray = zip_iter.toArray
    val tarray = txt.toArray

    zarray.length should be (tarray.length)

    (zarray zip tarray).foreach { case (a,b) => a should be (b)}
  }

  "An iterator" should "only be iterable once" in {
    val zip_iter = new ZippedTextFileIterator(zip_name, "jabberwocky.txt")
    val zarray1 = zip_iter.toArray

    intercept[NoSuchElementException] { zip_iter.next() }
  }

  "An iterable" should "be iterable more than once" in {
    val zip_iter = new ZippedTextFileIterable(zip_name, "jabberwocky.txt")
    val zarray1 = zip_iter.toArray
    val zarray2 = zip_iter.toArray

    (zarray1 zip zarray2).foreach { case (a,b) => a should be (b)}
  }
}

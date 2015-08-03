import org.scalatest._

class BasicTests extends FlatSpec with Matchers {
  val zip_name: String = getClass.getResource("/jabberwocky.zip").getFile
  val stream = getClass.getResourceAsStream("/jabberwocky.txt")
  val txt = scala.io.Source.fromInputStream(stream).getLines()

  val zip_iter = new ZippedTextFileIterator(zip_name, "jabberwocky.txt")

  val zarray = zip_iter.toArray
  val tarray = txt.toArray

  zarray.length should be (tarray.length)

  (zarray zip tarray).foreach { case (a,b) => a should be (b)}
}

package net.ettinsmoor.zipped

/**
 * Creates an iterable capable of iterating over a text file
 * stored within a ZIP file.
 * @param zipfilename The name of the ZIP file.
 * @param filename The name of the file ("entity") stored inside the ZIP file.
 */
class ZippedTextFileIterable(zipfilename: String, filename: String) extends Iterable[String] {
  def iterator: Iterator[String] = new ZippedTextFileIterator(zipfilename, filename).toIterator
}

package net.ettinsmoor.zipped

import java.io.InputStream
import java.util.zip.ZipFile

private sealed abstract class NextIs
private case class NextIsChar(c: Char) extends NextIs
private case class NextIsString(s: String) extends NextIs
private case class NextIsNewline() extends NextIs
private case class NextIsEOF() extends NextIs

/**
 * Creates an iterator capable of iterating over a text file
 * stored within a ZIP file.
 * @param zipfilename The name of the ZIP file.
 * @param filename The name of the file ("entity") stored inside the ZIP file.
 */
class ZippedTextFileIterator(zipfilename: String, filename: String) extends Iterator[String] {
  val NL = System.lineSeparator()
  val zf: ZipFile = new ZipFile(zipfilename)
  val zfs: InputStream = zf.getInputStream(zf.getEntry(filename))
  var nb_opt: Option[Int] = None

  def hasNext: Boolean = {
    nb_opt match {
      case Some(nb) => true
      case None =>
        val byte = zfs.read()
        if (byte != -1) {
          nb_opt = Some(byte)
          true
        } else {
          nb_opt = None
          false
        }
    }
  }

  def next(): String = {
    var done = false

    val sb = new StringBuilder
    var token = nextToken()
    while (!done) {
      val sofar = sb.toString()

      token match {
        case NextIsChar(c) => sb.append(c); token = nextToken()
        case NextIsString(s) => sb.append(s); token = nextToken()
        case _ => done = true
      }
    }

    // return string
    sb.toString()
  }

  private def nextToken() : NextIs = {
    // read next byte
    val byte = nextByte()

    if (byte == -1) {
      NextIsEOF()
    } else {
      // cast to Char and decide what to do next
      byte.asInstanceOf[Char] match {
        // handle Windows newlines
        case '\r' => {
          // read the next char
          val nc = zfs.read()
          if (nc != -1) {
            if (nc.asInstanceOf[Char] != '\n') {
              // add \r + whatever to and return
              NextIsString("\r" + nc.asInstanceOf[Char])
            } else {
              NextIsNewline()
            }
          } else {
            NextIsChar('\r')
          }
        }
        // handle UNIX newlines
        case '\n' => NextIsNewline() // NOP
        // ordinary char
        case c: Char => NextIsChar(c)
      }
    }
  }

  private def nextByte() : Int = {
    nb_opt match {
      case Some(nb) => nb_opt = None; nb
      case None => zfs.read()
    }
  }
}

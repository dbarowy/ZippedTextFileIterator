# ZippedTextFileIterator
A simple (simplistic?) library for iterating over ZIP-compressed text files.

## Installation ##

For now, you need to checkout from GitHub and then publish locally:

```
$ git clone https://github.com/dbarowy/ZippedTextFileIterator.git
[...]
$ cd ZippedTextFileIterator
$ sbt publish-local

```

Then add the following to your `build.sbt`:

```
libraryDependencies += "net.ettinsmoor" %% "zippedtextfileiterator" % "1.0-SNAPSHOT"
```

## How to use ##

The following prints each line of the file `foo.txt` which is located inside the `file.zip` archive.


```
import net.ettinsmoor.zipped._

val z = new ZippedTextFileIterator("file.zip", "foo.txt")
z.foreach(println)

```

# ZippedTextFileIterator
A simple (simplistic?) library for iterating over ZIP-compressed text files one line at a time.

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

Right now, only Scala 2.10 and 2.11 are supported.

## How to use ##

First, import the library:

```
import net.ettinsmoor.zipped._
```

The following prints each line of the file `foo.txt` which is located inside the `file.zip` archive:

```
val z = new ZippedTextFileIterator("file.zip", "foo.txt")
z.foreach(println)

```

You can even use this to iterate over zipped text files stored as a Java resource:

```
val zip_name = getClass.getResource("/file.zip").getFile
val z = new ZippedTextFileIterator(zip_name, "foo.txt")
z.foreach(println)
```

If you're a functional programming purist, there's also an `Iterable` version:

```
val z = new ZippedTextFileIterator("file.zip", "foo.txt")
z.foreach(println)  // iterate first time
z.foreach(println)  // iterate second time

```
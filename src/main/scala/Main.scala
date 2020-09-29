import java.io.{File, FileWriter}

import com.typesafe.config.{Config, ConfigFactory}

object Main extends App {

  val path: Config = ConfigFactory.parseFile(new File("./application.conf"))

  val bufferedSource = scala.io.Source.fromFile(s"./${path.getString("filename")}")
  val input = bufferedSource.getLines().toList.map(_.split(",").toList)

  def writeDataToCsv(lines: List[String], header: String): Unit = {
    val writer = new FileWriter(s"./lines.csv")
    writer.write(header + "\n")
    lines.foreach(l => writer.write(l + "\n"))
    writer.close()
  }

  def crossJoin[T](list: Iterable[Iterable[T]]): Iterable[Iterable[T]] =
    list match {
      case xs :: Nil => xs map (Iterable(_))
      case x :: xs => for {
        i <- x
        j <- crossJoin(xs)
      } yield Iterable(i) ++ j
    }


  val header = input.map(_.head)
  val noHeaders = input.map(_.tail)

  private val lines: List[List[String]] = crossJoin(noHeaders).toList.map(_.toList)

  writeDataToCsv(lines.toList.map(x => x.mkString(",")).toList,
    header.mkString(","))
}


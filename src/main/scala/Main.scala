import java.io.{File, FileWriter}
import java.util

import Main.Line
import cats.effect.IO
import com.typesafe.config.{Config, ConfigFactory, ConfigList}
import io.chrisdavenport.cormorant.Printer
import io.chrisdavenport.log4cats.Logger

object Main extends App {

  import io.chrisdavenport.cormorant._
  import io.chrisdavenport.cormorant.generic.semiauto._
  import io.chrisdavenport.cormorant.implicits._
  import scala.jdk.CollectionConverters._

  val conf: Config = ConfigFactory.parseFile(new File("./application.conf"))

  case class Line(jahr: String, branche: String, firmengroesse: String, quali: String, geschlecht: String, alter: String, selbstbestimmt: String)

  implicit val sopr: Read[Line] = deriveRead
  implicit val vwr: Write[Line] = deriveWrite

  def writeDataToCsv(lines: List[Line]): Unit = {
    val writer = new FileWriter(s"./lines.csv")
    writer.write(header.mkString(",") + "\n")
    writer.append(lines.writeRows.print(Printer.default))
    writer.close()
  }

  val header = conf.getStringList("header").asScala.toList
  val jahre = conf.getStringList("jahre").asScala.toList
  val branchen = conf.getStringList("branchen").asScala.toList
  val groessen = conf.getStringList("groessen").asScala.toList
  val qualis = conf.getStringList("qualis").asScala.toList
  val geschlechter = conf.getStringList("geschlechter").asScala.toList
  val altersGruppen = conf.getStringList("altersGruppen").asScala.toList
  val sebstbestimmung = conf.getStringList("sebstbestimmung").asScala.toList


  val lines = jahre.flatMap(j =>
    branchen.flatMap(b =>
      groessen.flatMap(gr =>
        qualis.flatMap(q =>
          geschlechter.flatMap(ge =>
            altersGruppen.flatMap(a =>
              sebstbestimmung.map(s => Line(j, b, gr, q, ge, a, s)))
          )
        )
      )
    )
  )

  writeDataToCsv(lines)
}


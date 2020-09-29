import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec

class MainTest extends AnyFlatSpec {

  it should "combine entries" in {



    val bufferedSource = scala.io.Source.fromFile("./test3.csv")
    val input = bufferedSource.getLines().toList.map(_.split(",").toList)

//    val buchstaben = List("A", "B", "C")
//    val zahlen = List("1", "2", "3", "4")
//    val zeichen = List(".",",","|")
//    val input = List(buchstaben, zahlen, zeichen)


    val output = Main.crossJoin(input)

    print(output)
  }


}

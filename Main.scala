//> using scala "3.1.2"
//> using lib "io.circe::circe-core::0.14.1"
//> using lib "io.circe::circe-generic::0.14.1"
//> using lib "io.circe::circe-parser::0.14.1"
//> using options "-Xmax-inlines", "64"

package zetashift.ldtk4s

import io.circe.*
import io.circe.generic.semiauto.*
import io.circe.generic.auto.*
import io.circe.parser.*
import scala.io.Source

@main def main() =
  val file = Source.fromFile("./sample_platformer.json")
  val json = file.getLines.mkString
  val out = decode[LdtkJsonRoot](json)
  println(out)


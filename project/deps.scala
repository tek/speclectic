package tryp

import sbt._
import Keys._

object SDeps
extends tryp.Deps
{
  override def deps = super.deps ++ Map(
    "root" → root
  )

  def root = ids(
    "org.specs2" %% "specs2-core" % "3.7" % "provided",
    "org.specs2" %% "specs2-core" % "3.7" % "test",
    "com.geteit" %% "robotest" % "+"
  )
}

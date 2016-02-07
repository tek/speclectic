package tryp

import sbt._, Keys._

object Build
extends LibsBuild("speclectic", deps = SDeps)
{
  override lazy val root = (
    "root" ~ "." settingsV(
      fork := true,
      parallelExecution := false, 
      javaOptions in Test ++= Seq("-XX:+CMSClassUnloadingEnabled")
    ) !
  )
}

package tryp

import sbt._, Keys._

import bintray.BintrayKeys._

object Build
extends LibsBuild("speclectic", deps = SDeps)
{
  override lazy val root = (
    ("root" ~ ".")
      .bintray
      .settingsV(
        name := "speclectic",
        fork := true,
        licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
        bintrayRepository := "releases",
        publishArtifact in (Compile, packageDoc) := false,
        publishArtifact in (Compile, packageSrc) := false
      )
      .!
  )
}

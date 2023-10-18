// https://typelevel.org/sbt-typelevel/faq.html#what-is-a-base-version-anyway
ThisBuild / tlBaseVersion := "0.0" // your current series x.y

ThisBuild / organization := "zetashift"
ThisBuild / organizationName := "Example"
ThisBuild / startYear := Some(2023)
ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("zetashift", "Rishad Sewnarain")
)

// publish to s01.oss.sonatype.org (set to true to publish to oss.sonatype.org instead)
ThisBuild / tlSonatypeUseLegacyHost := false

// publish website from this branch
// ThisBuild / tlSitePublishBranch := Some("main")

val Scala3 = "3.3.1"
ThisBuild / crossScalaVersions := Seq(Scala3)
ThisBuild / scalaVersion := Scala3 // the default Scala

lazy val root = tlCrossRootProject.aggregate(core)

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "ldtk4s",
    scalacOptions ++= Seq("-Xmax-inlines", "50"),
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core" % "2.10.0",
      "io.circe" %%% "circe-core" % "0.14.6",
      "io.circe" %%% "circe-generic" % "0.14.6"
    )
  )

lazy val indigo = crossProject(JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("indigo"))
  .settings(
    name := "ldtk4s-indigo",
    libraryDependencies ++= Seq(
      "io.indigoengine" %%% "indigo" % "0.15.0"
    )
  )
// lazy val docs = project.in(file("site")).enablePlugins(TypelevelSitePlugin)

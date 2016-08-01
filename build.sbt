name := "ScalaSmplMsgQ"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-simple" % "1.7.5",
  "org.scalactic" %% "scalactic" % "2.2.6",
  "org.scalatest" %% "scalatest" % "2.2.6"

)

lazy val root = (project in file(".")).enablePlugins(PlayScala)


mainClass in assembly := Some("play.core.server.NettyServer")

fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value)   
assemblyMergeStrategy in assembly := {
	case PathList(ps @ _*) if ps.last endsWith ".class" => MergeStrategy.first
  	case "application.conf"                            => MergeStrategy.concat
	case PathList(ps @ _*) if ps.last endsWith "netty.versions.properties" => MergeStrategy.first
	case x =>
    		val oldStrategy = (assemblyMergeStrategy in assembly).value
    		oldStrategy(x)
}

 

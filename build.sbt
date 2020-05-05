name := "HL7v2Parser"

version := "0.1"

scalaVersion := "2.13.1"
libraryDependencies ++= Seq(
  "ca.uhn.hapi.fhir" % "hapi-fhir-base" % "4.2.0",
  "ca.uhn.hapi" % "hapi-base" % "2.2",
  "ca.uhn.hapi" % "hapi-structures-v25" % "2.3",
  "ca.uhn.hapi" % "hapi-structures-v23" % "2.3",
  "ca.uhn.hapi" % "hapi-structures-v22" % "2.3",
  "ca.uhn.hapi" % "hapi-structures-v24" % "2.3",
  "net.liftweb" %% "lift-json" % "3.4.1"
)
 
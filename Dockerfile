FROM anapsix/alpine-java
MAINTAINER rewatiraman
CMD ["java","-jar","./target/scala-2.11/ScalaSmplMsgQ-assembly-1.0.jar"]


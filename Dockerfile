FROM anapsix/alpine-java
MAINTAINER rewati.raman@gmail.com
RUN ["sbt", "compile"]
COPY ./target/scala-2.11/ScalaSmplMsgQ-assembly-1.0.jar ./ScalaSmplMsgQ-assembly-1.0.jar
CMD ["java","-jar","./ScalaSmplMsgQ-assembly-1.0.jar"]
#FROM anapsix/alpine-java
#MAINTAINER rewati.raman@gmail.com
#RUN ["sbt", "compile"]
#COPY ./target/scala-2.11/ScalaSmplMsgQ-assembly-1.0.jar ./ScalaSmplMsgQ-assembly-1.0.jar
#CMD ["java","-jar","./ScalaSmplMsgQ-assembly-1.0.jar"]

FROM ubuntu:14.04
RUN rm /bin/sh && ln -s /bin/bash /bin/sh

MAINTAINER Rewati Raman "rewati.raman@gmail.com"

# Update aptitude with new repo
RUN apt-get update

# Install software
RUN apt-get install -y git
# Make ssh dir
#RUN mkdir /root/.ssh/
#
## Copy over private key, and set permissions
#ADD id_rsa /root/.ssh/id_rsa
#
## Create known_hosts
#RUN touch /root/.ssh/known_hosts
## Add bitbuckets key
#RUN ssh-keyscan bitbucket.org >> /root/.ssh/known_hosts

# Clone the conf files into the docker container
RUN git clone https://github.com/rewati/ScalaSmplMsgQ.git
RUN wget https://repo.typesafe.com/typesafe/ivy-releases/org.scala-sbt/sbt-launch/0.13.12/sbt-launch.jar
RUN mv sbt-launch.jar /bin
RUN touch /bin/sbt
RUN echo #!/bin/bash > /bin/sbt
RUN echo SBT_OPTS="-Xms512M -Xmx1536M -Xss1M -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=256M" > /bin/sbt 
RUN echo java $SBT_OPTS -jar `dirname $0`/sbt-launch.jar "$@" > /bin/sbt
RUN chmod u+x ~/bin/sbt
RUN cd ScalaSmplMsgQ
RUN sbt compile
COPY ./target/scala-2.11/ScalaSmplMsgQ-assembly-1.0.jar ./ScalaSmplMsgQ-assembly-1.0.jar
CMD ["java","-jar","./ScalaSmplMsgQ-assembly-1.0.jar"]

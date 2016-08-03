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

RUN apt-get install -y wget
RUN cd /opt
RUN wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u91-b14/jdk-8u91-linux-x64.tar.gz"
RUN tar xzf jdk-8u91-linux-x64.tar.gz
RUN export PATH=$PATH:/opt/jdk1.8.0_91/bin/
RUN cd /
RUN git clone https://github.com/rewati/ScalaSmplMsgQ.git
RUN wget https://repo.typesafe.com/typesafe/ivy-releases/org.scala-sbt/sbt-launch/0.13.12/sbt-launch.jar
RUN mv sbt-launch.jar /bin
RUN touch /bin/sbt
RUN echo "#!/bin/bash" > /bin/sbt
RUN echo SBT_OPTS="-Xms512M -Xmx1536M -Xss1M -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=256M" > /bin/sbt 
RUN echo /opt/jdk1.8.0_91/bin/java $SBT_OPTS -jar `dirname $0`/sbt-launch.jar "$@" > /bin/sbt
RUN chmod u+x /bin/sbt
RUN cd /ScalaSmplMsgQ
RUN sbt run

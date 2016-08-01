#FROM anapsix/alpine-java
#MAINTAINER rewati.raman@gmail.com
#RUN ["sbt", "compile"]
#COPY ./target/scala-2.11/ScalaSmplMsgQ-assembly-1.0.jar ./ScalaSmplMsgQ-assembly-1.0.jar
#CMD ["java","-jar","./ScalaSmplMsgQ-assembly-1.0.jar"]

FROM ubuntu

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
FROM anapsix/alpine-java
RUN apt-get install -y sbt
RUN cd ScalaSmplMsgQ
RUN sbt compile
COPY ./target/scala-2.11/ScalaSmplMsgQ-assembly-1.0.jar ./ScalaSmplMsgQ-assembly-1.0.jar
CMD ["java","-jar","./ScalaSmplMsgQ-assembly-1.0.jar"]

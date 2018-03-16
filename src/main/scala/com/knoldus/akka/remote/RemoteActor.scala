package com.knoldus.akka.remote

import java.io.File

import akka.actor._
import com.typesafe.config.ConfigFactory

class RemoteActor extends Actor {
  override def receive: Receive = {
    case "START" =>
      println(s"Start Remote Actor...!!!")

    case _: String =>
      println(s"Get message at RemoteActor")
      sender ! "Message from the RemoteActor"

    case _ => println("Get Unhandled message at RemoteActor")
  }
}

object RemoteActor extends App {
  val configFile = getClass.getClassLoader.getResource("remote_application.conf").getFile
  val config = ConfigFactory.parseFile(new File(configFile))
  val system = ActorSystem("RemoteSystem", config)
  val remote = system.actorOf(Props[RemoteActor], name = "remote")
  remote ! "START"
}



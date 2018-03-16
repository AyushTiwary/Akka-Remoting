package com.knoldus.akka.local

import java.io.File

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

class LocalActor extends Actor {

  var counter = 0

  override def preStart(): Unit = {
    val remote = context.actorSelection("akka.tcp://RemoteSystem@127.0.0.1:5150/user/remote")
    remote ! "Message from the LocalActor"
  }

  def receive = {
    case "START" =>
      println(s"Start Local Actor...!!!")
    case msg: String =>
      println(s"LocalActor received message: '$msg'")
      if (counter < 5) {
        sender ! "Hello back to you"
        counter += 1
      }
  }
}

object LocalActor extends App {
  val configFile = getClass.getClassLoader.getResource("local_application.conf").getFile
  val config = ConfigFactory.parseFile(new File(configFile))
  val system = ActorSystem("ClientSystem", config)
  val localActor = system.actorOf(Props[LocalActor], name = "local")
}

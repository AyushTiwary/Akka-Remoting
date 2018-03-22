package com.knoldus.akka.remote

import akka.actor._
import com.knoldus.akka.CreateRemoteConfig

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
  val createRemoteConfig = new CreateRemoteConfig
  val config = createRemoteConfig.remoteConfig("127.0.0.1", 5152)
  val system = ActorSystem("RemoteSystem", config)
  val remote = system.actorOf(Props[RemoteActor], name = "remote")
  remote ! "START"
}

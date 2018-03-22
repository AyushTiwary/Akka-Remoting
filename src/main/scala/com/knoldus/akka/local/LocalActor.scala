package com.knoldus.akka.local

import akka.actor.{Actor, ActorSystem, Props}
import com.knoldus.akka.CreateRemoteConfig

class LocalActor extends Actor {

  var counter = 0

  override def preStart(): Unit = {
    val remote = context.actorSelection("akka.tcp://RemoteSystem@127.0.0.1:5152/user/remote")
    println("remote ::: " + remote)
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
  val createRemoteConfig = new CreateRemoteConfig
  val config = createRemoteConfig.remoteConfig("127.0.0.1", 5151)
  val system = ActorSystem("ClientSystem", config)
  val localActor = system.actorOf(Props[LocalActor], name = "local")
}

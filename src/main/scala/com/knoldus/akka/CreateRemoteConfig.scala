package com.knoldus.akka

import com.typesafe.config.{Config, ConfigFactory}

class CreateRemoteConfig {

  def remoteConfig(hostname: String, port: Int): Config = {
    val configStr =  s"""
                       |akka {
                       |  actor {
                       |    provider = "akka.remote.RemoteActorRefProvider"
                       |  }
                       |  remote {
                       |    enabled-transports = ["akka.remote.netty.tcp"]
                       |    netty.tcp {
                       |      hostname = $hostname
                       |      port = $port
                       |    }
                       |    log-sent-messages = on
                       |    log-received-messages = on
                       |  }
                       |}
                     """.stripMargin

    ConfigFactory.parseString(configStr)
  }
}

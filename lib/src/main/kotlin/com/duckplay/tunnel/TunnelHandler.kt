package com.duckplay.tunnel

import java.io.IOException
import java.net.ServerSocket
import java.net.Socket

class TunnelHandler(val tunnel: TunnelParam) {

    fun start() {
        try {
            val serverSocket = ServerSocket(tunnel.tunnelPort)
            while (true) {
                val clientSocket: Socket = serverSocket.accept()
                val tunnel = TCPTunnel(params = tunnel, clientTunnel = clientSocket)
                tunnel.start()
            }
        } catch (e: IOException) {
            throw RuntimeException("Error while trying to forward TCP with params:$tunnel", e)
        }
    }
}
package com.duckplay.tunnel

import java.io.IOException
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*

class TCPTunnel(
    private val params: TunnelParam, val clientTunnel: Socket,
    private var serverSocket: Socket? = null,
    private var active: Boolean = false
) : Thread() {
    private val sdf = SimpleDateFormat("yyyy.MMM.dd HH:mm:ss")

    override fun run() {
        val dateStr = sdf.format(Date())
        try {
            serverSocket = Socket(params.remoteHost, params.remotePort)

            serverSocket!!.setKeepAlive(true)
            clientTunnel.setKeepAlive(true)

            val clientIn = clientTunnel.getInputStream()
            val clientOut = clientTunnel.getOutputStream()

            val serverIn = serverSocket!!.getInputStream()
            val serverOut = serverSocket!!.getOutputStream()

            active = true
            val clientAddr = toStr(clientTunnel)
            val serverAddr = toStr(serverSocket!!)
            println("$dateStr: TCP Forwarding $clientAddr <--> $serverAddr")

            val clientForward = TCPForwarder(this, clientIn, serverOut, params)
            clientForward.start()
            val serverForward = TCPForwarder(this, serverIn, clientOut, params)
            serverForward.start()
        } catch (_: IOException) {
            val remoteAddr: String = params.remoteHost + ":" + params.remotePort
            System.err.println("$dateStr: Failed to connect to remote host ($remoteAddr)")
            connectionBroken()
        }
    }

    private fun toStr(socket: Socket): String {
        val host = socket.getInetAddress().hostAddress
        val port = socket.getPort()
        return "$host:$port"
    }

    @Synchronized
    fun connectionBroken() {
        try {
            serverSocket!!.close()
            clientTunnel.close()
        } catch (_: Exception) {
            val dateStr = sdf.format(Date())
            println(
                dateStr + ": TCP Forwarding " + toStr(clientTunnel) + " <--> " + toStr(
                    serverSocket!!
                ) + " stopped."
            )
        }

        active = false
    }
}
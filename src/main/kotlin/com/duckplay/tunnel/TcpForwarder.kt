package com.duckplay.tunnel

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class TCPForwarder(
    private val parent: TCPTunnel,
    private val `is`: InputStream,
    private val os: OutputStream,
    private val params: TunnelParam
) : Thread() {

    override fun run() {
        val buffer = ByteArray(params.bufferSize)
        try {
            while (true) {
                val bytesRead = `is`.read(buffer)
                if (bytesRead == -1) break

                os.write(buffer, 0, bytesRead)
                os.flush()
            }
        } catch (e: IOException) {
            parent.connectionBroken()
        }
    }
}
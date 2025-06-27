package com.duckplay.tunnel

data class TunnelParam(
    val remoteHost: String = "localhost",
    val remotePort: Int = 7777,
    val tunnelPort: Int = 10030,
    val tunnelHost: String = "localhost",
    val bufferSize: Int = 8192
)

import com.duckplay.tunnel.TCPTunnel
import com.duckplay.tunnel.TunnelHandler
import com.duckplay.tunnel.TunnelParam
import java.io.IOException
import java.net.ServerSocket
import java.net.Socket

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main(args: Array<String>) {
    val arguments = args.toList()
    val localPort = arguments.getOrNull(arguments.indexOf("--local") + 1)?.toIntOrNull() ?: 7777
    val host = arguments.getOrNull(arguments.indexOf("--to") + 1) ?: "localhost:10000"
    val hostData = host.split(":")

    val tunnel = TunnelParam(remotePort = localPort, tunnelHost = hostData[0], tunnelPort = hostData[1].toInt())

    println(
        """
         ____          _      _____                 _ 
        |    \ _ _ ___| |_   |_   _|_ _ ___ ___ ___| |
        |  |  | | |  _| '_|    | | | | |   |   | -_| |
        |____/|___|___|_,_|    |_| |___|_|_|_|_|___|_|
        Local server - localhost:${tunnel.remotePort}
        Tunnel server - ${tunnel.tunnelHost}:${tunnel.tunnelPort}
        
        """.trimIndent()
    )

    TunnelHandler(tunnel).start()
}
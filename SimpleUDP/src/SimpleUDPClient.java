import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SimpleUDPClient {
	public static void main(String args[]) {
		// Declara o socket UDP
		DatagramSocket clientSocket = null;
		try {
			// Mensagem que vou enviar
			String mes = "Felipe";
			// Instancia o socker UDP (define que ele deve usar a porta 10.000)
			clientSocket = new DatagramSocket();
			// Cria array de bytes que será enviado para o servidor
			byte[] sendArray = mes.getBytes();
			// Ip e porta do servidor
			InetAddress IpServidor = InetAddress.getByName("192.168.56.1");
			int port = 6789;
			// Cria um pacote UDP (array, tamanho do array, ip, porta)
			//DatagramPacket sendPacket = new DatagramPacket(sendArray, sendArray.length, IpServidor, port);
			DatagramPacket sendPacket = new DatagramPacket(sendArray, sendArray.length, IpServidor, port);
			// Envia o pacote UDP
			clientSocket.send(sendPacket);
			System.out.println("Pacote enviado!");

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Fecha o socket 
			if (clientSocket != null) clientSocket.close();
		}
	}
}
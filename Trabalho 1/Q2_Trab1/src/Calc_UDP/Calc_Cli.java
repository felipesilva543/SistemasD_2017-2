package Calc_UDP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Calc_Cli {
	public static void main(String[] args) {
		//Declara o socket UDP
		DatagramSocket clientSocket = null;
		
		try {
			System.out.print("Operação (Ex: 2 + 2): ");
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			String resposta = inFromUser.readLine();
			
			// Instancia o socker UDP
			clientSocket = new DatagramSocket();

			// Cria array de bytes que será enviado para o servidor
			byte[] sendArray = resposta.getBytes();
			byte[] receiveData = new byte[1024];
			
			// Ip e porta do servidor
			InetAddress IpServidor = InetAddress.getByName("localhost");
			
			// Cria um pacote UDP (array, tamanho do array, ip, porta)
			DatagramPacket sendPacket = new DatagramPacket(sendArray, sendArray.length, IpServidor, 6789);
			
			//Enviando pacote
			clientSocket.send(sendPacket);
			
			//Criando pacote de recebimento
			DatagramPacket request = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(request);
			
			//Convertendo o que chegou do servidor em String
			resposta = new String(request.getData(), 0, request.getLength());
			System.out.println(resposta);
			clientSocket.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

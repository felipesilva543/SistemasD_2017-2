package Calc_UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Calc_Serv {
	public static void main(String[] args) {
		DatagramSocket serverSocket = null;
		
		try {
			// Instancia socker UDP (define que ele deve usar a porta 6789)
			serverSocket = new DatagramSocket(6789);
			System.out.println("Servidor em execucão!");
			
			// Cria array de bytes recebe o pacote do cliente
			byte[] receiveData = new byte[1024];
			
			while (true) {
				// Cria pacote para receber a mensagem UDP
				DatagramPacket request = new DatagramPacket(receiveData, receiveData.length);
				
				// Espera a chegada de uma msg (bloqueante)
				serverSocket.receive(request);
				// Armazena a mensagem que chegou no formato String
				String operacao = new String(request.getData(), 0, request.getLength());
				
				//Separando a operação em varios pedaços
				String ope[] = operacao.split(" ");
				
				if(ope[1].equals("+")) {
					int res = Integer.parseInt(ope[0]) + Integer.parseInt(ope[2]);
					String env = "Resultado: " + Integer.toString(res);
					sendAgrVai(request, serverSocket, env);
				}
				if(ope[1].equals("-")) {
					int res = Integer.parseInt(ope[0]) - Integer.parseInt(ope[2]);
					String env = "Resultado: " + Integer.toString(res);
					sendAgrVai(request, serverSocket, env);
				}
				if(ope[1].equals("*")) {
					int res = Integer.parseInt(ope[0]) * Integer.parseInt(ope[2]);
					String env = "Resultado: " + Integer.toString(res);
					sendAgrVai(request, serverSocket, env);
				}
				if(ope[1].equals("/")) {
					if(ope[2].equals("0")) {
						String env = "Não é possível realizar divisão por zero!\n";
						sendAgrVai(request, serverSocket, env);
					}
					int res = Integer.parseInt(ope[0]) / Integer.parseInt(ope[2]);
					String env = "Resultado: " + Integer.toString(res);
					sendAgrVai(request, serverSocket, env);
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void sendAgrVai(DatagramPacket data, DatagramSocket socServ, String msg) {
		InetAddress ip;
		ip = data.getAddress();
		int porta = data.getPort();
		byte[] send = msg.getBytes();
		
		DatagramPacket sendPacket = new DatagramPacket(send, send.length, ip, porta);
		try {
			socServ.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Enviado!");
	}
}

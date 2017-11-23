package Clie_Serv;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class BroadcastServer {
	public static void main(String args[]) {
		System.out.println("Servidor ON!");
		DatagramSocket socket = null;
		int portServ = 5555;
		try {
			socket = new DatagramSocket(portServ, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);
			while (true) {
				byte[] receiveArray = new byte[1000];
				DatagramPacket receivePacket = new DatagramPacket(receiveArray, receiveArray.length);
				socket.receive(receivePacket);
				String msg = new String(receivePacket.getData(), 0, receivePacket.getLength());
				System.out.println("Mensagem recebida: " + msg);
				if(msg.equals("calc")) {
					String retorno = "Olá, eu tenho esse serviço!";
					sendAgrVai(receivePacket, socket, retorno);
					//IP
					InetAddress servidorAddr = receivePacket.getAddress();
					String ipServ = servidorAddr.getHostAddress();
					sendAgrVai(receivePacket, socket, ipServ);
					//Porta
					sendAgrVai(receivePacket, socket, ""+portServ);
					
					// Espera a chegada de uma msg (bloqueante)
					socket.receive(receivePacket);
					// Armazena a mensagem que chegou no formato String
					String operacao = new String(receivePacket.getData(), 0, receivePacket.getLength());
					
					//Separando a operação em varios pedaços
					String ope[] = operacao.split(" ");
					
					if(ope[1].equals("+")) {
						int res = Integer.parseInt(ope[0]) + Integer.parseInt(ope[2]);
						String env = "Resultado: " + Integer.toString(res);
						sendAgrVai(receivePacket, socket, env);
					}
					if(ope[1].equals("-")) {
						int res = Integer.parseInt(ope[0]) - Integer.parseInt(ope[2]);
						String env = "Resultado: " + Integer.toString(res);
						sendAgrVai(receivePacket, socket, env);
					}
					if(ope[1].equals("*")) {
						int res = Integer.parseInt(ope[0]) * Integer.parseInt(ope[2]);
						String env = "Resultado: " + Integer.toString(res);
						sendAgrVai(receivePacket, socket, env);
					}
					if(ope[1].equals("/")) {
						if(ope[2].equals("0")) {
							String env = "Não é possível realizar divisão por zero!\n";
							sendAgrVai(receivePacket, socket, env);
						}else {
							int res = Integer.parseInt(ope[0]) / Integer.parseInt(ope[2]);
							String env = "Resultado: " + Integer.toString(res);
							sendAgrVai(receivePacket, socket, env);
						}
					}
					
				}
//				InetAddress servidorAddr = receivePacket.getAddress();
//				msg = servidorAddr.getHostAddress();
//				sendAgrVai(receivePacket, socket, msg);
				
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (socket != null)
				socket.close();
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
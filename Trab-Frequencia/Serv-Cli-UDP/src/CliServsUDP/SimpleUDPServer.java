package CliServsUDP;

import java.io.*;
import java.net.*;

import javax.swing.SingleSelectionModel;

public class SimpleUDPServer {

	public static void main(String[] args) {
				// Declara socket UDP
				DatagramSocket serverSocket = null;
				String msgResposta;
				int requi = 0;
				long start = System.currentTimeMillis();
				int portaSer = 6789;
				try {
					// Instancia socker UDP (define que ele deve usar a porta 6789)
					serverSocket = new DatagramSocket(portaSer);
					System.out.println("Servidor em execucão!");
					// Cria array de bytes que será enviado para o cliente
					byte[] receiveData = new byte[1024];
					int id = 0;
					// Cria loop para receber mais de uma msg
					while (true) {
						id++;
						System.out.println("Esperando Msg " + id + " ...");
						// Cria pacote para receber a mensagem UDP
						DatagramPacket request = new DatagramPacket(receiveData, receiveData.length);
						InetAddress IPAddressCli = request.getAddress();
						
						
						// Espera a chegada de uma msg (bloqueante)
						serverSocket.receive(request);
						// Armazena a mensagem que chegou no formato String
						String sentence = new String(request.getData(), 0, request.getLength());
						
						
						if(sentence.equals("\\CLOSE")) {
							msgResposta = "Tchau!";
							sendAgrVai(request, serverSocket, msgResposta);
						}
						if(sentence.equals("\\UPTIME")) {
							++requi;
							msgResposta = "Tempo executando: " + ((System.currentTimeMillis() - start)/1000) + "s";
							sendAgrVai(request, serverSocket, msgResposta);
						}
						if(sentence.equals("\\REQNUM")) {
							++requi;
							msgResposta = "Nume. Requisições: " + requi;
							sendAgrVai(request, serverSocket, msgResposta);
						}
					}
				} catch (SocketException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					// Fecha o socket 
					if (serverSocket != null) serverSocket.close();
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

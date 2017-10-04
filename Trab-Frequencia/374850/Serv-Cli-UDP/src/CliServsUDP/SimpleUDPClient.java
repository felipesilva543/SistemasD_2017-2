package CliServsUDP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SimpleUDPClient {
	private static Scanner s;

	public static void main(String[] args) {
		// Declara as mensagens da aplicação
		//String requisicao;
		String resposta;
		int portaServ = 0;
		
		s = new Scanner( System.in );
		
		//--------------------------------------------------------
		//System.out.print("Informe o IP do servidor: ");
		//ip = inFromUser.readLine();
		System.out.print("Informe a porta do servidor: ");
		portaServ = s.nextInt();
		//--------------------------------------------------------
		
		// Declara o socket UDP
				DatagramSocket clientSocket = null;
				try {
					System.out.print("Cliente.\nDigite\n" +
							 "\\UPTIME: Tempo de execução do Server.\n" + 
							 "\\REQNUM: Número de requisições do Server.\n" +
							 "\\CLOSE: Fechar cliente.");
					System.out.print("\nEscreva MSG a ser enviada: ");
					BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
					String argument = inFromUser.readLine();
					
					// Instancia o socker UDP (define que ele deve usar a porta 10.000)
					clientSocket = new DatagramSocket();
					// Cria array de bytes que ser� enviado para o servidor
					byte[] sendArray = argument.getBytes();
					byte[] receiveData = new byte[1024];
					// Ip e porta do servidor
					InetAddress IpServidor = InetAddress.getByName("localhost");
					// Cria um pacote UDP (array, tamanho do array, ip, porta)
					DatagramPacket sendPacket = new DatagramPacket(sendArray, sendArray.length, IpServidor, portaServ);
					
					if(argument.equals("\\Close")) {
						// Enviar mensagem digitada para o servidor
						clientSocket.send(sendPacket);
						DatagramPacket request = new DatagramPacket(receiveData, receiveData.length);
						// Esperar resposta do servidor
						clientSocket.receive(request);
						resposta = new String(request.getData(), 0, request.getLength());
						System.out.println("\n" + resposta + "\n\n");
						clientSocket.close();
						//break;
					}else {
						clientSocket.send(sendPacket);
						DatagramPacket request = new DatagramPacket(receiveData, receiveData.length);
						// Esperar resposta do servidor
						clientSocket.receive(request);
						resposta = new String(request.getData(), 0, request.getLength());
						System.out.println("\n" + resposta + "\n\n");
					}
					

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
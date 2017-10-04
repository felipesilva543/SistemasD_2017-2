package CliServsTCP;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SimpleTCPClient {

	public static void main(String[] args) {
		// Declara as mensagens da aplicação
				String requisicao;
				String resposta;
				//String ip;
				int porta;
				
				Scanner s = new Scanner( System.in );
				BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
				
				//--------------------------------------------------------
				//System.out.print("Informe o IP do servidor: ");
				//ip = inFromUser.readLine();
				System.out.print("Informe a porta do servidor: ");
				porta = s.nextInt();
				//--------------------------------------------------------
				
				
				// Declara o socket
				Socket clientSocket;
				try {
					
					while(true) {
					// Instancia o socket
					clientSocket = new Socket("localhost", porta);
					// Instancia objeto que escreve no buffer de saída
					DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
					// Instancia objeto que lê buffer de entrada
					DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
					
					
						//-------------------------------------------------
						System.out.print("Cliente.\nDigite\n" +
								 "\\UPTIME: Tempo de execução do Server.\n" + 
								 "\\REQNUM: Número de requisições do Server.\n" +
								 "\\Close: Fechar cliente. ");
						System.out.print("\nEscreva MSG a ser enviada: ");
						//-------------------------------------------------
						// Ler entrada do teclado
						requisicao = inFromUser.readLine();
						if(requisicao.equals("\\Close")) {
							// Enviar mensagem digitada para o servidor
							outToServer.writeUTF(requisicao);
							// Esperar resposta do servidor
							resposta = inFromServer.readUTF();
							System.out.println("\n" + resposta + "\n\n");
							clientSocket.close();
							break;
						}else {
							// Enviar mensagem digitada para o servidor
							outToServer.writeUTF(requisicao);
							// Esperar resposta do servidor
							resposta = inFromServer.readUTF();
							System.out.println("\n" + resposta + "\n\n");
						}
						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
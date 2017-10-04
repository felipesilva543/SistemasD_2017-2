package CliServsTCP;

import java.io.*;
import java.net.*;


public class SimpleTCPServer2 {

	public static void main(String[] args) {
		// Declara o socket do servidor
				ServerSocket listenSocket;
				// Declara objetos necessários às regras de negócio da aplicação
				String msgRequisicao;
				String msgResposta;
				int requisicoes = 0;
				
				//----------------------------------------------------------------------
				// Pegar a data e hr do sistema
				//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
				// Mostra data e hr
				//System.out.println(sdf.format(new Date()));
				// Pega a hora corrente do sistema
				long start = System.currentTimeMillis();
				//----------------------------------------------------------------------
				
				try {
					// Instancia o socket que vai escutar na porta 6789
					listenSocket = new ServerSocket(6790);
					System.out.println("Server 2: \nEsperando conexões");
					// Faz um loop para lidar com as requisições
					while (true) {
						// Instancia socket que lida com as requisições de UM cliente
						Socket connectionSocket = listenSocket.accept();
						// Instancia objeto que lê buffer de entrada
						DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
						// Instancia objeto que escreve no buffer de saída
						DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
						// Lê msg enviada pelo cliente
						msgRequisicao = inFromClient.readUTF();
						
						//---------------------------------------------------------------------------------------------
						if(msgRequisicao.equals("\\Close")) {
							System.out.println("Messagem recebida: " + msgRequisicao);
							//Mostra tempo de execução em segundos
							msgResposta = "Tchau!";
							// Escreve msg de resposta para o cliente
							outToClient.writeUTF(msgResposta);
						}else
						if(msgRequisicao.equals("\\UPTIME")) {
							++requisicoes;
							// Mostra a mensagem do cliente
							System.out.println("Messagem recebida: " + msgRequisicao);
							//Mostra tempo de execução em segundos
							msgResposta = "Tempo executando: " + ((System.currentTimeMillis() - start)/1000) + "s";
							// Escreve msg de resposta para o cliente
							outToClient.writeUTF(msgResposta);
						}else if(msgRequisicao.equals("\\REQNUM")) {
							++requisicoes;
							System.out.println("Messagem recebida: " + msgRequisicao);
							// Transforma a msg recebida
							msgResposta = "Número de requisições: " + requisicoes;
							outToClient.writeUTF(msgResposta);
						}
						//---------------------------------------------------------------------------------------------

						// Fecha o socket
						connectionSocket.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

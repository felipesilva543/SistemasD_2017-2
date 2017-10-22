package Chat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Cliente {
	public static void main(String[] args) {
		int porta = 6789;
		
		//Cria um buffer para armazenar txt do teclado
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

		try {			
			//Criando Socket do cliente
			final Socket clientSocket = new Socket("localhost", porta);
			
			//Lendo mensagens
			new Thread() {
				/**
				 * 
				 */
				@Override
				public void run() {
					try {
						// Instancia objeto que lê buffer de entrada
						DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
						
						while(true) {
							String mensagem = inFromServer.readUTF();
							System.out.println(mensagem);
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}.start();;
			
			//Escrevendo mensagem para o servidor
			try {
				// Instancia objeto que escreve no buffer de saída
				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
				while (true) {
					String entrdaCli = inFromUser.readLine();
					outToServer.writeUTF(entrdaCli);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

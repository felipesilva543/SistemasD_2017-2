package Chat_NB;

import java.net.*;
import java.io.*;
public class Cliente {
	public static void main (String args[]) {
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		
		try{
			int serverPort = 7896;
			Socket s  = new Socket("localhost", serverPort);    
			/**
			 * Lendo as mensagens
			 */
			new Thread() {
				/**
				 * 
				 */
				@Override
				public void run() {
					try {
						// Instancia objeto que lê buffer de entrada
						DataInputStream in = new DataInputStream(s.getInputStream());
						while(true) {
							String mensagem = in.readUTF();
							System.out.println(mensagem);
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}.start();;
			
			/**
			 * Escrevendo mensagem para o servidor
			 */
			try {
				// Instancia objeto que escreve no buffer de saída
				DataOutputStream out = new DataOutputStream(s.getOutputStream());
				while (true) {
					String entrdaCli = inFromUser.readLine();
					out.writeUTF(entrdaCli);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			 
		}catch (UnknownHostException e){System.out.println("Socket:"+e.getMessage());
		}catch (EOFException e){System.out.println("EOF:"+e.getMessage());
		}catch (IOException e){System.out.println("readline:"+e.getMessage());
		}
     }
}
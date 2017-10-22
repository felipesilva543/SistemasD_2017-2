package Chat;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Chat_Serv {
	public static void main(String[] args){
		ServerSocket servidor = null;
		System.out.println("Server ON!");
		try {
			servidor = new ServerSocket(6789);
			while (true) {	
				Socket s = servidor.accept();
				System.out.println(s.getInetAddress() + " entrou");

				Mensagem msg = new Mensagem(s);
			}
		} catch (Exception e) {
			try {
				if(servidor != null)
					servidor.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.err.println("Porta não disponível.");
		}
		
	}
	
}

	

	
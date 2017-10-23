package Chat_NB;


import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.*;
public class Serv {
	public static void main (String args[]) {
		try{
			int serverPort = 7896; // the server port
			ServerSocket listenSocket = new ServerSocket(serverPort);
			System.out.println("Server ON!");
			while(true) {
				Socket clientSocket = listenSocket.accept();
				System.out.println(listenSocket.getInetAddress() + " entrou.");
				Connection c = new Connection(clientSocket);
			}
		} catch(IOException e) {System.out.println("Listen socket:"+e.getMessage());}
	}
}
class Connection extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
	private BufferedReader inFromUser;
	private String nomeCliente;
	private static final ArrayList<Connection> clis = new ArrayList<Connection>();
	public Connection (Socket aClientSocket) {
		inFromUser = new BufferedReader(new InputStreamReader(System.in));
		try {
			clientSocket = aClientSocket;
			in = new DataInputStream( clientSocket.getInputStream());
			out =new DataOutputStream( clientSocket.getOutputStream());
			this.start();
		} catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
	}
	public void run(){
		try {
			out.writeUTF("Digite seu nome: ");
			// Lê msg enviada pelo cliente
			String msgRequisicao = in.readUTF();
			this.nomeCliente = msgRequisicao;
			out.writeUTF("Bem vindo " + this.nomeCliente + ", digite :DATA para transmitir informações de data e horário.");
			clis.add(this);
			
			
			while(true) {
				msgRequisicao = in.readUTF();
				if(msgRequisicao.equalsIgnoreCase(":DATA")) {
					//Pegar data e hr do sistema
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
					// Mostra data e hr
					msgRequisicao = sdf.format(new Date());
					for(Connection elemento : clis) {
						elemento.getOut().writeUTF(nomeCliente + ": " + msgRequisicao);
					}
					msgRequisicao = null;
				}else {
					for(Connection elemento : clis) {
						elemento.getOut().writeUTF(nomeCliente + ": " + msgRequisicao);
					}
					msgRequisicao = null;
				}
			}

			
		}catch (EOFException e){System.out.println("EOF:"+e.getMessage());
		} catch(IOException e) {System.out.println("readline:"+e.getMessage());
		} finally{ try {clientSocket.close();}catch (IOException e){/*close failed*/}}

	}
	public DataOutputStream getOut() {
		return out;
	}
}

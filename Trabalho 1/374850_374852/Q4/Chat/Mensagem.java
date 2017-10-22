package Chat;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mensagem extends Thread{
	private Socket cliente;
	private String nomeCliente;
	private DataOutputStream outToClient;
	private static final ArrayList<Mensagem> clis = new ArrayList<Mensagem>();
	
	//Construtor
	public Mensagem(Socket _cliente) {
		this.cliente = _cliente;
		start();
	}
	
	/**
	 * 
	 */
	@Override
	public void run() {
		try {
			//Instancia objeto que lê buffer de entrada
			DataInputStream inFromClient = new DataInputStream(cliente.getInputStream());
			
			// Instancia objeto que escreve no buffer de saída
			outToClient = new DataOutputStream(cliente.getOutputStream());
			
			outToClient.writeUTF("Digite seu nome: ");
			// Lê msg enviada pelo cliente
			String msgRequisicao = inFromClient.readUTF();
			this.nomeCliente = msgRequisicao;
			outToClient.writeUTF("Bem vindo " + this.nomeCliente + ".");
			clis.add(this);
			
			
			while(true) {
				msgRequisicao = inFromClient.readUTF();
				
					for(Mensagem elemento : clis) {
						elemento.getOutToClient().writeUTF(nomeCliente + ": " + msgRequisicao);
					}
					msgRequisicao = null;
				
			}
			
			
		} catch (Exception e) {
			System.err.println("O cliente desconectou.\n");
		}
		
	}
	public DataOutputStream getOutToClient() {
		return outToClient;
	}
}
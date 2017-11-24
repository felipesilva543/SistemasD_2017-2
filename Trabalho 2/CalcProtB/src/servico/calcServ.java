package servico;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import br.ufc.quixada.calc.Calculadora.Reply;
import br.ufc.quixada.calc.Calculadora.Request;

public class calcServ {
	public static Reply protRes(Request req) {
		double respOpe = 0;
		if(req.getOp().equals(Request.Operacao.SOM)) {
			respOpe = req.getN1() + req.getN2();
		}
		if(req.getOp().equals(Request.Operacao.SUB)) {
			respOpe = req.getN1() - req.getN2();
		}
		if(req.getOp().equals(Request.Operacao.MUL)) {
			respOpe = req.getN1() * req.getN2();
		}
		if(req.getOp().equals(Request.Operacao.DIV)) {
			respOpe = req.getN1() / req.getN2();
		}
		Reply.Builder build = Reply.newBuilder();
		build.setId(req.getId());
		build.setRes(respOpe);
		return build.build();
	}
	
	public static void main(String[] args) {
		DatagramSocket socketServ = null;
		System.out.println("Servidor ON!");
		try {
			socketServ = new DatagramSocket(6789);
			byte[] reciveCli = new byte[1024];
			
			while(true) {	
			DatagramPacket receivePacket = new DatagramPacket(reciveCli, reciveCli.length);
			socketServ.receive(receivePacket);
			InetAddress ipCli = receivePacket.getAddress();
			int portCli = receivePacket.getPort();
			
			ByteArrayInputStream inCli = new ByteArrayInputStream(reciveCli);
			ByteArrayOutputStream outCli = new ByteArrayOutputStream();
			
			//TRABALHANDO COM O Protocol Buffer
			Request request = Request.parseDelimitedFrom(inCli);
			Reply replyCli = protRes(request);
			replyCli.writeDelimitedTo(outCli);
			byte sendData[] = outCli.toByteArray();
			DatagramPacket aSendPacket = new DatagramPacket(sendData, sendData.length,ipCli,portCli);
			socketServ.send(aSendPacket);
			}
		} catch (Exception e) {
				
		}
	}
}

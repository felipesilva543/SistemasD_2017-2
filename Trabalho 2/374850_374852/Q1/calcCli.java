package servico;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import br.ufc.quixada.calc.Calculadora.Reply;
import br.ufc.quixada.calc.Calculadora.Reply.Builder;
import br.ufc.quixada.calc.Calculadora.Request;

public class calcCli {
	private static Scanner s;

	public static Request protCalc(){
		Request.Builder cal = Request.newBuilder();
		
		s = new Scanner(System.in);
		
		System.out.print("Enter Calc ID: ");
	    cal.setId(Integer.valueOf(s.next()));
	    System.out.print("Enter N1: ");
	    cal.setN1(Integer.valueOf(s.next()));
	    System.out.print("Enter N2: ");
	    cal.setN2(Integer.valueOf(s.next()));
	    System.out.print("Enter OP \n [1 - Soma]\n [2 - Sub]\n [3 - Mul]\n [4 - Div]\n: ");
	    int c = s.nextInt();
	    
	    switch (c) {
		case 1:
			cal.setOp(Request.Operacao.SOM);
			break;
		case 2:
			cal.setOp(Request.Operacao.SUB);
			break;
		case 3:
			cal.setOp(Request.Operacao.MUL);
			break;
		case 4:
			double n2 = cal.getN2(); 
			if(n2 == 0) {
				System.out.println("Não é possível divisão por zero!");
				System.exit(0);
			}
			cal.setOp(Request.Operacao.DIV);
			break;
		default:
			break;
		}
	    
	    return cal.build();
		
	}
	
	public static void main(String[] args) throws Exception {
		DatagramSocket socketCli = null;
		Request reqCal = null;
		try {
			reqCal = protCalc();
			socketCli = new DatagramSocket();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			reqCal.writeDelimitedTo(output);
			byte sendOperacao[] = output.toByteArray();
			InetAddress ipCliente = InetAddress.getByName("localhost");
			int port = 6789;
			DatagramPacket sendPacket = new DatagramPacket(sendOperacao, sendOperacao.length, ipCliente, port);
			socketCli.send(sendPacket);
		
			
			byte[] receiveOperacao = new byte[1024];
			DatagramPacket reciveSer = new DatagramPacket(receiveOperacao, receiveOperacao.length);
			socketCli.receive(reciveSer);
			ByteArrayInputStream in = new ByteArrayInputStream(receiveOperacao);
			Reply repCal = Reply.parseDelimitedFrom(in);
			System.out.println("Resultado: " + repCal.getRes());
			System.out.println();
		} catch (Exception e) {
		}
	}

}



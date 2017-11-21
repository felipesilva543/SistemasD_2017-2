package exemplo.ufc.br;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

	private Client() {
	}

	public static void main(String[] args) {
		try {
			Scanner s = new Scanner( System.in );
			Registry registry = LocateRegistry.getRegistry("localhost");
			Calc proxy = (Calc) registry.lookup("Calc");
			String response = proxy.sayHello();
			System.out.println("Resposta: " + response);
			
			System.out.print("Digite:\n 1 - SOMA\n 2 - SUBTRAÇÃO\n 3 - MULTIPLICAÇÃO\n 4 - DIVISÃO\n: ");
			int c = s.nextInt();
			System.out.print("Digite o primeiro valor: ");
			double n1 = s.nextDouble();
			System.out.print("Digite o segundo valor: ");
			double n2 = s.nextDouble();
			double resultado;
			
			switch (c) {
			case 1:
				resultado = proxy.som(n1, n2);
				System.out.println("Soma " + n1 + " + " + n2 + " = " + resultado);
				break;
			case 2:
				resultado = proxy.sub(n1, n2);
				System.out.println("Sub " + n1 + " - " + n2 + " = " + resultado);
				break;
			case 3:
				resultado = proxy.mul(n1, n2);
				System.out.println("Mult " + n1 + " * " + n2 + " = " + resultado);
				break;
			case 4:
				if(n2 != 0) {
					resultado = proxy.div(n1, n2);
					System.out.println("Div " + n1 + " / " + n2 + " = " + resultado);
				}else {
					System.out.println("Divisor não pode ser igual a zero!");
				}
				break;
			default:
				break;
			}
			
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}

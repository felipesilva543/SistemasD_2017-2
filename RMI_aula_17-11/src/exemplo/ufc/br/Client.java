package exemplo.ufc.br;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

	private Client() {
	}

	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry("localhost");
			Calc proxy = (Calc) registry.lookup("Calc");
			String response = proxy.sayHello();
			System.out.println("Resposta: " + response);
			double resultado = proxy.mul(20, 35);
			System.out.println("Mul 20 * 35 = " + resultado);
			resultado = proxy.som(500, 500);
			System.out.println("Som 500 + 500 = " + resultado);
			resultado = proxy.sub(500, 250);
			System.out.println("Sub 500 - 250 = " + resultado);
			String resultadoDiv = proxy.div(30, 5);
			System.out.println("Div 30 / 5 = " + resultadoDiv);

		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}

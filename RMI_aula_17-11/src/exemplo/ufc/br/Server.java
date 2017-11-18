package exemplo.ufc.br;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Calc {
	
	public Server() {
	}

	@Override
	public String sayHello() {
		return "Hello, world!";
	}

	@Override
	public double mul(double n1, double n2) throws RemoteException {
		return n1 * n2;
	}

	public double som(double n1, double n2) throws RemoteException {
		return n1 + n2;
	}
	public double sub(double n1, double n2) throws RemoteException {
		return n1 - n2;
	}
	public String div(double n1, double n2) throws RemoteException {
		if(n2 == 0)
			return "Impossivel dividir por 0!\n";
		double res = n1/n2;
		return res+"";
	}

	public static void main(String args[]) {
		try {
			Server obj = new Server();
			Calc stub = (Calc) UnicastRemoteObject.exportObject(obj, 0);

			Registry registry = LocateRegistry.getRegistry();
			registry.bind("Calc", stub);

			System.err.println("Servidor pronto");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
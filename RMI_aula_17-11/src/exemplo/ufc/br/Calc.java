package exemplo.ufc.br;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calc extends Remote {
	String sayHello() throws RemoteException;

	double mul(double n1, double n2) throws RemoteException;
	double som(double n1, double n2) throws RemoteException;
	double sub(double n1, double n2) throws RemoteException;
	double div(double n1, double n2) throws RemoteException;
}

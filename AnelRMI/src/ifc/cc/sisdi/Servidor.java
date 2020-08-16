package ifc.cc.sisdi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Servidor {
    public static void main(String[] args) {
        try {
            No no1 = new No();
            No no2 = new No();
            No no3 = new No();
            No no4 = new No();
            No no5 = new No();
            No no6 = new No();
            No no7 = new No();
            No no8 = new No();

            System.setProperty("java.rmi.server.hostname","localhost");
            Registry registro = LocateRegistry.createRegistry(12345);

            RMIInterface stub1 = (RMIInterface) UnicastRemoteObject.exportObject(no1, 0);
            RMIInterface stub2 = (RMIInterface) UnicastRemoteObject.exportObject(no2, 0);
            RMIInterface stub3 = (RMIInterface) UnicastRemoteObject.exportObject(no3, 0);
            RMIInterface stub4 = (RMIInterface) UnicastRemoteObject.exportObject(no4, 0);
            RMIInterface stub5 = (RMIInterface) UnicastRemoteObject.exportObject(no5, 0);
            RMIInterface stub6 = (RMIInterface) UnicastRemoteObject.exportObject(no6, 0);
            RMIInterface stub7 = (RMIInterface) UnicastRemoteObject.exportObject(no7, 0);
            RMIInterface stub8 = (RMIInterface) UnicastRemoteObject.exportObject(no8, 0);

            registro.bind("No1", stub1);
            registro.bind("No2", stub2);
            registro.bind("No3", stub3);
            registro.bind("No4", stub4);
            registro.bind("No5", stub5);
            registro.bind("No6", stub6);
            registro.bind("No7", stub7);
            registro.bind("No8", stub8);

            System.out.println("Servidor pronto!");
        } catch (Exception e) {
            System.err.println("erro: " + e.toString());
        }
    }
}

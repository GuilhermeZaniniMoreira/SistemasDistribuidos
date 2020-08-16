package ifc.cc.sisdi;
import java.rmi.RemoteException;

public class No implements RMIInterface {

    Integer identificador; // identifacdor do Nó
    Boolean disponibilidade; // disponibilidade do nó
    Boolean coordenador; // nó é coordenador
    RMIInterface sucessor; // RMIInterface do nó sucessor

    public void setIdentificador(Integer identificador) throws RemoteException {
        this.identificador = identificador;
    }

    public Boolean getDisponibilidade() throws RemoteException {
        return this.disponibilidade;
    }

    public void setDisponibilidade(Boolean disponibilidade) throws RemoteException {
        this.disponibilidade = disponibilidade;
    }

    public Integer getIdentificador() throws RemoteException {
        return this.identificador;
    }

    public void setCoordenador(Boolean coordenador) throws RemoteException {
        this.coordenador = coordenador;
    }

    public Boolean getCoordenador() throws RemoteException {
        return this.coordenador;
    }

    public void setSucessor(RMIInterface sucessor) throws RemoteException {
        this.sucessor = sucessor;
    }

    public RMIInterface getSucessor() throws RemoteException{
        return this.sucessor;
    }

}

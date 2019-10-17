import java.util.ArrayList;

public class TablaMicro {
    private int ID_Micro;
    private ArrayList<Character> process;
    private ArrayList<Integer> tCambioContexto, tEjecucion, tVencimientoQ, tBloqueo, tTotal, tInicial, tFinal;
    private int tiempoI = 0;
    boolean microVacio = true;

    public TablaMicro(int ID_Micro){
        this.ID_Micro = ID_Micro;
        process = new ArrayList<>();
        tCambioContexto = new ArrayList<>();
        tEjecucion = new ArrayList<>();
        tVencimientoQ = new ArrayList<>();
        tBloqueo = new ArrayList<>();
        tTotal = new ArrayList<>();
        tInicial = new ArrayList<>();
        tFinal = new ArrayList<>();
    }

    public int getID_Micro() {
        return ID_Micro;
    }

    public void setID_Micro(int ID_Micro) {
        this.ID_Micro = ID_Micro;
    }

    public ArrayList<Character> getProcess() {
        return process;
    }

    public void setProcess(ArrayList<Character> process) {
        this.process = process;
    }

    public ArrayList<Integer> gettCambioContexto() {
        return tCambioContexto;
    }

    public void settCambioContexto(ArrayList<Integer> tCambioContexto) {
        this.tCambioContexto = tCambioContexto;
    }

    public ArrayList<Integer> gettEjecucion() {
        return tEjecucion;
    }

    public void settEjecucion(ArrayList<Integer> tEjecucion) {
        this.tEjecucion = tEjecucion;
    }

    public ArrayList<Integer> gettVencimientoQ() {
        return tVencimientoQ;
    }

    public void settVencimientoQ(ArrayList<Integer> tVencimientoQ) {
        this.tVencimientoQ = tVencimientoQ;
    }

    public ArrayList<Integer> gettBloqueo() {
        return tBloqueo;
    }

    public void settBloqueo(ArrayList<Integer> tBloqueo) {
        this.tBloqueo = tBloqueo;
    }

    public ArrayList<Integer> gettTotal() {
        return tTotal;
    }

    public void settTotal(ArrayList<Integer> tTotal) {
        this.tTotal = tTotal;
    }

    public ArrayList<Integer> gettInicial() {
        return tInicial;
    }

    public void settInicial(ArrayList<Integer> tInicial) {
        this.tInicial = tInicial;
    }

    public ArrayList<Integer> gettFinal() {
        return tFinal;
    }

    public void settFinal(ArrayList<Integer> tFinal) {
        this.tFinal = tFinal;
    }

    public int getTiempoI() {
        return tiempoI;
    }

    public void setTiempoI(int tiempoI) {
        this.tiempoI = tiempoI;
    }

    public boolean isMicroVacio() {
        return microVacio;
    }

    public void setMicroVacio(boolean microVacio) {
        this.microVacio = microVacio;
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class GUI extends JFrame {
    static public int micros, quantum, tcc, tb, counter;
    static public Stack<Character> proInitial = new Stack<>();
    static public Stack<Character> proSecond = new Stack<>(); // 1500
    static public Stack<Character> proThird = new Stack<>();  // 3000
    static public Stack<Character> proFourth = new Stack<>(); // 4000
    static public Stack<Character> proFifth = new Stack<>();  // 8000
    static public HashMap<Character, Integer> process = new HashMap<>();
    static public TablaMicro[] mic;
    static public TablaMicro m;
    static private Stack<Character>[] procesos = new Stack[5];
    static private int[] tiempos = {0, 1500, 3000, 4000, 8000};
    static public int idMicro = 0;

    public GUI(){
        super("Despachador");
        generateDataStructures();
        questionPanel();
        setLayout(new GridLayout(1, 1));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setVisible(true);
    }

    public void questionPanel(){
        JPanel qPan = new JPanel();
        JTextField microTField, quantumTField, tccTField, tbTField;
        microTField = new JTextField();
        quantumTField = new JTextField();
        tccTField = new JTextField();
        tbTField = new JTextField();
        JButton bDatos = new JButton("Generar Tabla");
        bDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                micros = Integer.parseInt(microTField.getText());
                quantum = Integer.parseInt(quantumTField.getText());
                tcc = Integer.parseInt(tccTField.getText());
                tb = Integer.parseInt(tbTField.getText());
                despachador();
                remove(qPan);
                showTables();
                revalidate();
                repaint();
            }
        });

        JLabel tittle = new JLabel("Despachador");
        tittle.setHorizontalAlignment(JLabel.CENTER);
        tittle.setFont(new Font("C", Font.BOLD, 20));

        qPan.setLayout(new GridLayout(8, 5));

        qPan.add(new JLabel(""));
        qPan.add(new JLabel(""));
        qPan.add(tittle);
        qPan.add(new JLabel(""));
        qPan.add(new JLabel(""));

        qPan.add(new JLabel(""));
        qPan.add(new JLabel(""));
        qPan.add(new JLabel(""));
        qPan.add(new JLabel(""));
        qPan.add(new JLabel(""));

        qPan.add(new JLabel(""));
        qPan.add(new JLabel("Numero de micros"));
        qPan.add(new JLabel(""));
        qPan.add(microTField);
        qPan.add(new JLabel(""));

        qPan.add(new JLabel(""));
        qPan.add(new JLabel("Tiempo Quantum"));
        qPan.add(new JLabel(""));
        qPan.add(quantumTField);
        qPan.add(new JLabel(""));

        qPan.add(new JLabel(""));
        qPan.add(new JLabel("Cambio de Contexto"));
        qPan.add(new JLabel(""));
        qPan.add(tccTField);
        qPan.add(new JLabel(""));

        qPan.add(new JLabel(""));
        qPan.add(new JLabel("Tiempo de Bloqueo"));
        qPan.add(new JLabel(""));
        qPan.add(tbTField);
        qPan.add(new JLabel(""));

        qPan.add(new JLabel(""));
        qPan.add(new JLabel(""));
        qPan.add(bDatos);
        qPan.add(new JLabel(""));
        qPan.add(new JLabel(""));

        qPan.add(new JLabel(""));
        qPan.add(new JLabel(""));
        qPan.add(new JLabel(""));
        qPan.add(new JLabel(""));
        qPan.add(new JLabel(""));

        add(qPan);
    }

    public void showTables(){
        JPanel panTablas = new JPanel();
        JButton next = new JButton(">");
        JButton before = new JButton("<");

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int nextID = idMicro + 1;
                if(nextID < mic.length){
                    remove(panTablas);
                    idMicro++;
                    showTables();
                    revalidate();
                    repaint();
                }else{
                    JOptionPane.showMessageDialog(panTablas, "no hay mas micros");
                }
            }
        });

        before.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int beforeID = idMicro - 1;
                if(beforeID > -1){
                    remove(panTablas);
                    idMicro--;
                    showTables();
                    revalidate();
                    repaint();
                }else{
                    JOptionPane.showMessageDialog(panTablas, "no hay mas micros");
                }
            }
        });

        TablaMicro tabla = mic[idMicro];
        int number_of_rows = tabla.getProcess().size();

        Iterator<Character> pro = tabla.getProcess().iterator();
        Iterator<Integer> tCamb = tabla.gettCambioContexto().iterator();
        Iterator<Integer> tEje = tabla.gettEjecucion().iterator();
        Iterator<Integer> tven = tabla.gettVencimientoQ().iterator();
        Iterator<Integer> tBloq = tabla.gettBloqueo().iterator();
        Iterator<Integer> tTot = tabla.gettTotal().iterator();
        Iterator<Integer> tIni = tabla.gettInicial().iterator();
        Iterator<Integer> tFin = tabla.gettFinal().iterator();

        panTablas.setLayout(new GridLayout( number_of_rows + 2,8));
        panTablas.add(before);
        panTablas.add(new JLabel("id micro " + (idMicro + 1), JLabel.CENTER));
        panTablas.add(next);
        for(int i=0; i<5; i++){ panTablas.add(new JLabel(""));}

        panTablas.add(new JLabel("Proceso", JLabel.CENTER));
        panTablas.add(new JLabel("TCC", JLabel.CENTER));
        panTablas.add(new JLabel("TE", JLabel.CENTER));
        panTablas.add(new JLabel("TVC", JLabel.CENTER));
        panTablas.add(new JLabel("TB", JLabel.CENTER));
        panTablas.add(new JLabel("TT", JLabel.CENTER));
        panTablas.add(new JLabel("TI", JLabel.CENTER));
        panTablas.add(new JLabel("TF", JLabel.CENTER));

        while(pro.hasNext()){
            panTablas.add(new JLabel(String.valueOf(pro.next()), JLabel.CENTER));
            panTablas.add(new JLabel(String.valueOf(tCamb.next()), JLabel.CENTER));
            panTablas.add(new JLabel(String.valueOf(tEje.next()), JLabel.CENTER));
            panTablas.add(new JLabel(String.valueOf(tven.next()), JLabel.CENTER));
            panTablas.add(new JLabel(String.valueOf(tBloq.next()), JLabel.CENTER));
            panTablas.add(new JLabel(String.valueOf(tTot.next()), JLabel.CENTER));
            panTablas.add(new JLabel(String.valueOf(tIni.next()), JLabel.CENTER));
            panTablas.add(new JLabel(String.valueOf(tFin.next()), JLabel.CENTER));
        }

        add(panTablas);
    }

    public static void generateDataStructures(){
        process.put('A', 400);
        process.put('B', 300);
        process.put('C', 50);
        process.put('D', 100);
        process.put('E', 1000);
        process.put('F', 500);
        process.put('G', 10);
        process.put('H', 700);
        process.put('I', 450);
        process.put('J', 300);
        process.put('K', 100);
        process.put('L', 3000);
        process.put('M', 80);
        process.put('N', 50);
        process.put('Q', 500);
        process.put('O', 600);
        process.put('P', 800);

        proInitial.add('H');
        proInitial.add('F');
        proInitial.add('D');
        proInitial.add('B');

        proSecond.add('O');
        proSecond.add('N');
        proSecond.add('L');
        proSecond.add('J');

        proThird.add('I');
        proThird.add('G');
        proThird.add('E');
        proThird.add('C');
        proThird.add('A');

        proFourth.add('P');
        proFourth.add('M');
        proFourth.add('K');

        proFifth.add('Q');


        procesos[0] = proInitial;
        procesos[1] = proSecond;
        procesos[2] = proThird;
        procesos[3] = proFourth;
        procesos[4] = proFifth;
    }

    public static void despachador(){
        mic = new TablaMicro[micros];
        for(int i=0; i<micros; i++){
            mic[i] = new TablaMicro(i + 1);
        }
        counter = 0;
        m = mic[0];

        for(int i=0; i<5; i++){
            processStacks(procesos[i], tiempos[i]);
        }
    }

    public static void processStacks(Stack<Character> proStack, int llegadaPro){
        igualarTiempoLLegada(llegadaPro);
        float tiempoVenciminetoCuantum = 0;
        int tiempoEjecucion = 0;
        int tVencimiento = 0;
        int tiempoBloqueo = 0;
        int tiempoTotal = 0;
        int tiempoFinal = 0;

        while(!proStack.isEmpty()){
            chooseMicroprocessor(llegadaPro);

            if(m.getTiempoI() < llegadaPro) m.setTiempoI(llegadaPro);

            tiempoEjecucion = process.get(proStack.peek());
            if(tiempoEjecucion > 1 && tiempoEjecucion < 401){
                tiempoBloqueo = tb * 2;
            }
            if(tiempoEjecucion > 400 && tiempoEjecucion < 601){
                tiempoBloqueo = tb * 3;
            }
            if(tiempoEjecucion > 600 && tiempoEjecucion < 801){
                tiempoBloqueo = tb * 4;
            }
            if(tiempoEjecucion > 800 && tiempoEjecucion < 10001){
                tiempoBloqueo = tb * 5;
            }
            tiempoVenciminetoCuantum = tiempoEjecucion / quantum;
            tVencimiento = (int)tiempoVenciminetoCuantum;

            if(tVencimiento < tiempoVenciminetoCuantum){
                tVencimiento = tVencimiento * tcc;
            }else if(tVencimiento == tiempoVenciminetoCuantum || tVencimiento > tiempoVenciminetoCuantum){
                tVencimiento = (tVencimiento - 1) * tcc;
            }

            if(tVencimiento < 0) tVencimiento = 0;

            if(m.isMicroVacio()){
                if(llegadaPro != 0){
                    m.getProcess().add('-');
                    m.gettCambioContexto().add(0);
                    m.gettEjecucion().add(0);
                    m.gettVencimientoQ().add(0);
                    m.gettBloqueo().add(0);
                    m.gettTotal().add(0);
                    m.gettInicial().add(0);
                    m.gettFinal().add(llegadaPro);
                }
                m.gettCambioContexto().add(0);
                m.setMicroVacio(false);
                tiempoTotal = 0;
            }
            else{
                m.gettCambioContexto().add(tcc);
                tiempoTotal = tcc;
            }

            tiempoTotal = tiempoTotal + tiempoEjecucion + tVencimiento + tiempoBloqueo;
            int tiempoInicial = m.getTiempoI();
            tiempoFinal = tiempoTotal + tiempoInicial;

            m.getProcess().add(proStack.pop());
            m.gettEjecucion().add(tiempoEjecucion);
            m.gettVencimientoQ().add(tVencimiento);
            m.gettBloqueo().add(tiempoBloqueo);
            m.gettTotal().add(tiempoTotal);
            m.gettInicial().add(tiempoInicial);
            m.gettFinal().add(tiempoFinal);

            m.setTiempoI(tiempoFinal);
        }
    }

    public static void chooseMicroprocessor(int llegadaPro){
        TablaMicro micro = mic[0];
        for(int i=0; i<mic.length; i++){
            if(micro.getTiempoI() > mic[i].getTiempoI()){
                micro = mic[i];
            }
            if(micro.getTiempoI() == 0){
                m = micro;
                return;
            }
        }
        m = micro;
    }

    public static void igualarTiempoLLegada(int tiempoLlegada){
        if(tiempoLlegada == 0) return;

        for(int i=0; i<mic.length; i++){
            if(mic[i].getTiempoI() < tiempoLlegada){
                mic[i].setTiempoI(tiempoLlegada);
                mic[i].setMicroVacio(true);
            }
        }
    }

}

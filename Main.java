import java.util.*;

public class Main{
	static public int micros, quantum, tcc, tb, counter;
	static public Stack<Character> proInitial = new Stack<>();
	static public Stack<Character> proSecond = new Stack<>(); // 1500
	static public Stack<Character> proThird = new Stack<>();  // 3000
	static public Stack<Character> proFourth = new Stack<>(); // 4000
	static public Stack<Character> proFifth = new Stack<>();  // 8000
	static public HashMap<Character, Integer> process = new HashMap<>();
	static public TablaMicro[] mic;
	static public TablaMicro m;

	public static void main(String[] args) {
		questions();
		generateMap();
		generateStacks();
		despachador();
	}

	public static void questions(){
		Scanner in = new Scanner(System.in);
		System.out.println("Dame los Microprosesadores que deseas: ");
		micros = in.nextInt();
		System.out.println("Dame el tiempo del Quantum: ");
		quantum = in.nextInt();
		System.out.println("Dame el tiempo para el cambio de contexto: ");
		tcc = in.nextInt();
		System.out.println("Dame el tiempo de bloqueo: ");
		tb = in.nextInt();
	}

	public static void generateMap(){
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
	}

	public static void generateStacks(){
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
	}

	public static void despachador(){
		mic = new TablaMicro[micros];
		for(int i=0; i<micros; i++){
			mic[i] = new TablaMicro(i + 1);
		}
		counter = 0;
		m = mic[0];
		processStacks(proInitial, 0);
		processStacks(proSecond, 1500);
		processStacks(proThird, 3000);
		processStacks(proFourth, 4000);
		processStacks(proFifth, 8000);
		System.out.println();
		printMicros();
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
			chooseMicroprocessor();
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

	public static void chooseMicroprocessor(){
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

	public static void printMicros(){
		for(TablaMicro t : mic){
			Iterator<Character> pro = t.getProcess().iterator();
			Iterator<Integer> tCamb = t.gettCambioContexto().iterator();
			Iterator<Integer> tEje = t.gettEjecucion().iterator();
			Iterator<Integer> tven = t.gettVencimientoQ().iterator();
			Iterator<Integer> tBloq = t.gettBloqueo().iterator();
			Iterator<Integer> tTot = t.gettTotal().iterator();
			Iterator<Integer> tIni = t.gettInicial().iterator();
			Iterator<Integer> tFin = t.gettFinal().iterator();

			System.out.println("ID " + t.getID_Micro());
			while(pro.hasNext()){
				System.out.println("Proceso: " + pro.next() + " TCC: " + tCamb.next() + " tEje: " + tEje.next() +
						" TVC " + tven.next() + " TB " + tBloq.next() + " TT " + tTot.next() + " TI " + tIni.next()
						+ " TF: " + tFin.next());
			}
			System.out.println("");
		}
	}
}
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Main{
	static public int micros, quantum, tcc, tb;
	static public Stack<Character> proInitial = new Stack<>();
	static public Stack<Character> proSecond = new Stack<>(); // 1500
	static public Stack<Character> proThird = new Stack<>();  // 3000
	static public Stack<Character> proFourth = new Stack<>(); // 4000
	static public Stack<Character> proFifth = new Stack<>();  // 8000
	static public HashMap<Character, Integer> process = new HashMap<>();

	public static void main(String[] args) {
		questions();
		generateMap();
		generateStacks();
	}

	public static void questions(){
		Scanner in = new Scanner(System.in);
		System.out.println("Dame los Microprosesadores que desas: ");
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
		TablaMicro[] mic = new TablaMicro[micros];
		for(int i=1; i<=micros; i++){
			mic[i-1] = new TablaMicro(i);
		}

		int idMicro = 0;
		int tiempoEjecucion = 0;
		float tiempoVenciminetoCuantum = 0;
		int tVencimiento = 0;
		int tiempoBloqueo = 0;
		int tiempoTotal = 0;
		int tiempoFinal = 0;

		while(!proInitial.isEmpty()){
			tiempoEjecucion = process.get(proInitial.peek());
			if(tiempoEjecucion > 1 && tiempoEjecucion < 401){
				tiempoBloqueo = tb * 2;
			}
			if(tiempoEjecucion > 400 && tiempoEjecucion < 601){
				tiempoBloqueo = tb * 3;
			}
			if(tiempoEjecucion > 600 && tiempoEjecucion < 801){
				tiempoBloqueo = tb * 4;
			}
			if(tiempoEjecucion > 801 && tiempoEjecucion < 10001){
				tiempoBloqueo = tb * 5;
			}
			tiempoVenciminetoCuantum = tiempoEjecucion % quantum;
			if(tiempoVenciminetoCuantum > 1){
				tVencimiento = (int)tiempoVenciminetoCuantum;
			}else{
				tVencimiento = 0;
			}

			mic[idMicro].getProcess().add(proInitial.pop());
			if(mic[idMicro].isMicroVacio()){
				mic[idMicro].gettCambioContexto().add(0);
				mic[idMicro].setMicroVacio(false);
			}
			else{
				mic[idMicro].gettCambioContexto().add(tcc);
			}

			mic[idMicro].gettEjecucion().add(tiempoEjecucion);
			mic[idMicro].gettVencimientoQ().add(tVencimiento);
			mic[idMicro].gettBloqueo().add(tiempoBloqueo);
			mic[idMicro].gettTotal().add()
		}

	}
}
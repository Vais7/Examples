package picoPlaca;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Placa {

	static class Matricula //Creacion de la clase Publica Matricula//
	{
		public String cadena = "";
		public Matricula (String a) {
			cadena= a;

		}
	}
	static class Fecha   //Definicion de la clase Publica Fecha//
	{
		public String fecha1 = "";
		public Fecha (String a) {
			fecha1= a;

		}
	}

	static String [][] semana = {{"lu","1","2"},{"ma","3","4"},{"mi","5","6"},{"ju","7","8"},{"vi","9","0"},};
/* Para optimizar la busqueda del dia y la hora implementé una matriz*/
	public static void main(String[] args) {
		boolean controladorPrincipal = true;//Boolean de control
		while (controladorPrincipal)
		{
			System.out.println("Introduzca la placa");//Ingreso de la placa//
			Matricula placaIngresada = ingresoPlaca();//LLamada a la funcion del ingreso de placa//
			System.out.println("La placa ingresada es "+placaIngresada.cadena);//Impresion de la placa Ingresada//
			String placaValida = validacionPlaca(placaIngresada);//llamada a la funcion de la Validacion de placa//
			System.out.println(placaValida);//Impresion de la placa Valida//
			if (placaValida!=""&&placaValida.substring(0,2).matches("Su")){ //Si el string retornado no es vacio y el mensaje comienza en "Su" , abstraido de un substring del mensaje post confirmacion de la placa//
			    boolean controladorsec1=true;
			    while(controladorsec1) {
                    System.out.println("Ingrese la fecha en formato dia/mes/anio");//Ingreso del dia del anio//
                    String dia = ingresoDia();
                    System.out.println("Ingrese la hora en formato 00:00 (24horas)");//Ingreso de la hora//
                    String hora = ingresoHora();
                    Fecha fecha = creacionFecha(dia, hora);//Se crea el objeto fecha a base de los 2 strings ingresados,que ta tambien es un tipo string//
                    String fechaValida = validacionFecha(fecha);//Se llama a la funcion de formato para la fecha y hora//
                    System.out.println(fechaValida);//Impresion con formato de la fecha ya validada
                    if(fechaValida.matches("")){
                        controladorsec1=false;
                        System.out.println("Desea ejecutar el programa de nuevo? Presione S, cualquier otra tecla terminara el programa");
                        Scanner reader = new Scanner(System.in);
                        char c = reader.next().charAt(0);
                        if (c=='S'||c=='s'){//Ingreso de caracter para la reejecucion del programa//
                            controladorPrincipal=true;

                        }
                        else{ }


                    }

                    if (fechaValida != "") {//Si la fecha ya validada no es un campo vacio llame a la funcion con las reglas para el pico y placa//
                        String circulacion = "";
                        circulacion = determinarCirculacion(placaIngresada, fechaValida);
                        System.out.println(circulacion);
                        controladorPrincipal = false;//Control de los booleans utilizados para evitar bucle//
                        controladorsec1=false;


                    }
                }

			}
            else{
				controladorPrincipal=false;//Boolean para el control de la placa en caso de no ser correcta
                System.out.println("Desea ejecutar el programa de nuevo? Presione S, cualquier otra tecla terminara el programa");
                Scanner reader = new Scanner(System.in);
                char c = reader.next().charAt(0);
                if (c=='S'||c=='s'){
                    controladorPrincipal=true;

                }
                else{ }
			}
		}
	}
	static Matricula ingresoPlaca()//Funcion para el ingreso de la placa//
	{
		Matricula placa1 = new Matricula("");
		Scanner lectura = new Scanner(System.in);
		placa1.cadena = lectura.nextLine();
		String placaIngresada = "Placa ingresada "+placa1.cadena.substring(0,3)+"-"+placa1.cadena.substring(3,placa1.cadena.length());
		return placa1;
	}

	static String validacionPlaca(Matricula placa)//Funcion para la verificacion de la placa//
	{
		String alfabetico=placa.cadena.substring(0,3);
		String numerico=placa.cadena.substring(3,placa.cadena.length());
		Pattern pat1 = Pattern.compile("[a-zA-Z]{3}");//Que contenga 3 letras inicialmente//
		Matcher mat1 = pat1.matcher(alfabetico);
		Pattern pat2 = Pattern.compile("[0-9]{4}||[0-9]{3}");//Que contenga al menos 3 numeros, hay carros que tienen 3 numeros aunque en el registro viene implicito un cero//
		Matcher mat2 = pat2.matcher(numerico);

		int long2=numerico.length();

		if (mat2.matches()) {
			if(long2==3){
				numerico="0"+numerico;//En caso de tener 3 digitos se añde un cero//

			}
		}
		String placaCorrecta = "";
		if (mat1.matches()&&mat2.matches()){
			placaCorrecta = "Su placa " + alfabetico+"-"+numerico + " es valida";//Si ambas condiciones como la parte alfabetica y la parte numerica son correctas , la placa tambien lo es.
		}
		else
		{
			placaCorrecta ="La placa es invalida";//Mensaje para el caso contrario//
		}

		return placaCorrecta;
	}

	static String ingresoDia()//Funcion para el ingreso del dia//
	{
		Scanner lectura2 = new Scanner(System.in);
		String diaEnString = lectura2.nextLine();
		return diaEnString;
	}

	static String ingresoHora()//Funcion para el ingreso del hora//
	{
		Scanner lectura2 = new Scanner(System.in);
		String horaEnString = lectura2.nextLine();
		return horaEnString;
	}

	static Fecha creacionFecha(String dia, String hora)//Funcion de la creacion del string en el formato necesario para convertir en objeto tipo date//
	{
		Fecha fecha = new Fecha("");
		fecha.fecha1 = dia + " " + hora;
		return fecha;
	}

	static String validacionFecha(Fecha fecha)//Funcion de conversion String a Date//
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");//Creacion del formato en el que se va a recibir el string de la fecha//
		SimpleDateFormat formatter2 = new SimpleDateFormat("E dd/MM/yyyy HH:mm");//Creacion del formato en el que se va a comparar la fecha//
		String fecha2 = "";
		try {
			Date date = formatter.parse(fecha.fecha1);
			fecha2=formatter2.format(date);

		} catch (ParseException e) {
			System.out.println("Fecha incorrecta");

		}

		return fecha2;
	}

	static String determinarCirculacion(Matricula placa, String fecha)//Funcion para determinar si pude circular el vehiculo
	{
		String horaEnString = fecha.substring(15,20);//Delimitacion del substring correspondiente a la hora
		Pattern pat3 = Pattern.compile("[0][7-8]:[0-5][0-9]|[0][9]:([0-2][0-9]|30)");//Regla de expresion regular para el pico y placa (Horario matutino)//
		Matcher horadia = pat3.matcher(horaEnString);//Verificar si esta en horario matutino
		Pattern pat4 = Pattern.compile("[1][6-8]:[0-5][0-9]|[1][9]:([0-2][0-9]|30)");//Regla de expresion regular para el pico y placa (Horario matutino)//
		Matcher horanoche = pat4.matcher(horaEnString);//erificar si esta en horario vespertino
		String circulacion ="";
		String dia=fecha.substring(0,2);//Delimitacion del sub string correspondiente al dia de la semana
		String digito=placa.cadena.substring(5,6);//Delimitacion del sub string correspondiente al ultimo digito de la placa
		for(int i=0;i<5;i++)
		{
			if(dia.matches(semana[i][0]))//Busqueda en la matriz para el dia
			{
				if(digito.matches(semana[i][1])||digito.matches(semana[i][2]))//Busqueda para el digito
				{
					if(horadia.matches()||horanoche.matches())
					{
						circulacion = "El carro no puede circular";//Comparacion de horarios, entra en el horario pico y placa//
					}
					else
					{
						circulacion = "El vehiculo esta autorizado para circular";//Puede circular en el horario asignado
					}
				}
				else
				{
					circulacion = "El vehiculo esta autorizado para circular ";//Vehiculos correspodientes a los que no tienen restriccion de ningun tipo debido al dia//
				}
			}

		}

		return circulacion;//Regreso del mensaje circulacion//
	}

}

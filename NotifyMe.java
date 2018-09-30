import java.util.*;
import java.io.*;
import java.net.*;

public class NotifyMe{
	Vector<Notificacion> notificaciones;
	
	public void actualizar(int pagina){
		Vector<Notificacion> temporal = new Vector<Notificacion>();
		URL urlObject;
		String codigo;
		File code;
		
		String url = "https://www.itescam.edu.mx/portal/avisos.php?pag="+pagina;
		//String url = "http://localhost:8080/notifyme/avisos.php";
		try{
			code = new File("notificaciones.txt");
			urlObject = new URL(url);
			InputStreamReader isr = new InputStreamReader(urlObject.openStream());
			BufferedReader br = new BufferedReader(isr);
			
			String linea = "";
			String htmlNotificacion = "";
			int contador = 0;
			while(linea!=null){
				try{
					linea = br.readLine();

					if(linea.equals("									<div class='panel panel-warning'>")){
						contador = contador + 1;
						String temp = "";
						String urlTemp = "";
						String tituloTemp = "";
						String opTemp = "";
						String areaTemp = "";
						String fechaTemp = "";
						String vistasTemp = "";
						for(int a = 0;a<3;a++){
							try{
								String [] ln = br.readLine().split("");
								for(int v=0;v<ln.length;v++){
									if(ln[v].equals("%")){
										temp = temp + ln[v] + ln[v];
									}else{
										temp = temp + ln[v] + "";
									}
								}
								//temp = br.readLine();
							}catch(Exception c){
								temp = "";
							}
							
							switch(a){
								case 0:
								break;
								case 1:
									urlTemp = "http://www.itescam.edu.mx/portal/avisos.php"+localizar("<h5><a href='avisos.php","'><font color='#000000'>",temp);
									tituloTemp = localizar("<font color='#000000'>","</font>",temp);
									if(empiezaCon("<strong>",tituloTemp)){
										tituloTemp = localizar("<font color='#000000'><strong>","</strong></font>",temp);
									}
								break;
								case 2:
									opTemp = localizar("	<br><small><i class='fa fa-user'></i> ","||  <i class='fa fa-bullhorn'>",temp);
									areaTemp = localizar("<i class='fa fa-bullhorn'></i>","|| <i class='fa fa-calendar'>",temp);
									fechaTemp = localizar("<i class='fa fa-calendar'></i>  ","||  <i class='fa fa-",temp);
									vistasTemp = localizar("<i class='fa fa-eye'></i> ","</small>",temp);
								break;
							}
						}
						Notificacion noti = new Notificacion(urlTemp, tituloTemp, opTemp, areaTemp, fechaTemp, vistasTemp);
						temporal.addElement(noti);
					}
				}catch(Exception u){
					
				}
			}
		}catch(Exception e){

		}

		notificaciones = temporal;
	}
	
	public boolean empiezaCon(String a, String b){
		boolean estado = false;
		if(a.charAt(0)==b.charAt(0)){
			int contador = 0;
			for(int i = 0;i<a.length();i++){
				if(!(a.charAt(i)==b.charAt(i))){
					i=a.length();
				}else{
					contador = contador + 1;
				}
			}
			if(contador==a.length()){
				estado = true;
			}
		}
		return estado;
	}
	
	public String localizar(String a, String b, String c){
		String resultado = "";
		String restante = "";
		int contador = 0;
		
		for(int i = 0 ;i<c.length();i++){
			if(c.charAt(i)==a.charAt(0)){
				boolean condicion = true;
				contador = 0;
				while(condicion){
					contador = contador + 1;
					if(contador<a.length()){
						if(c.charAt(i+contador)==a.charAt(contador)){
							condicion = true;
						}else{
							condicion = false;
						}
					}else{
						condicion = false;
						for(int j = i+contador;j<c.length();j++){
							restante = restante + c.charAt(j)+"";
						}
					}
				}
			}
		}
		
		int largo = 0;
		
		for(int i = 0 ;i<restante.length();i++){
			if(restante.charAt(i)==b.charAt(0)){
				boolean condicion = true;
				contador = 0;
				while(condicion){
					contador = contador + 1;		
					try{
						if(restante.charAt(i+contador)==b.charAt(contador)){
							condicion = true;
						}else{
							condicion = false;
						}
					}catch(Exception w){
						condicion = false;
						largo = i;
					}
				}
			}
		}
		
		
		for(int i=0;i<largo;i++){
			resultado = resultado + restante.charAt(i)+"";
		}
		
		return resultado + "\n";
	}
	
	public void pausa(){
		Scanner leer = new Scanner(System.in);
		String x = leer.next();
	}
	
	public Vector<Notificacion> getNotificaciones(){
		return notificaciones;
	}
	
	public static void main (String[] args){
		NotifyMe itescam = new NotifyMe();
		Scanner leer = new Scanner (System.in);
		System.out.printf(" _   _       _   _  __         __  __ _____ _ \n");
		System.out.printf("| \\ | | ___ | |_(_)/ _|_   _  |  \\/  | ____| |\n");
		System.out.printf("|  \\| |/ _ \\| __| | |_| | | | | |\\/| |  _| | |\n");
		System.out.printf("| |\\  | (_) | |_| |  _| |_| | | |  | | |___|_|\n");
		System.out.printf("|_| \\_|\\___/ \\__|_|_|  \\__, | |_|  |_|_____(_)\n");
		System.out.printf("                       |___/                  \n");
		System.out.printf("By NOOBS.INC 2018, v1.2\n");
		System.out.printf("----------------------------------------------\n");
		System.out.printf("\nPagina: ");
		int pag = leer.nextInt();
		System.out.printf("Conectando a itescam.edu.mx... \n");
		itescam.actualizar(pag);
		
		for(int i = 0;i<itescam.getNotificaciones().size();i++){
			Notificacion noti = itescam.getNotificaciones().elementAt(i);
			System.out.printf("--["+(i+1)+"]-------------------------------------------------------\n\n");
			System.out.printf("URL: " + noti.getUrl());
			System.out.printf("Titulo: " + noti.getTitulo());
			System.out.printf("Op: " + noti.getOp());
			System.out.printf("Area: " + noti.getArea());
			System.out.printf("Fecha de publicación: " + noti.getFecha());
			System.out.printf("Numero de vistas: " + noti.getVistas()+"\n");
		}
		
	}

}




class Notificacion{
	String url;
	String titulo;
	String op;
	String area;
	String fecha;
	String vistas;
	
	public Notificacion(String url, String titulo, String op, String area, String fecha, String vistas){
		this.url = url;
		this.titulo = titulo;
		this.op = op;
		this.area = area;
		this.fecha = fecha;
		this.vistas = vistas;
	}
	
	public Notificacion(){
		titulo = "";
		op = "";
		fecha = "";
		vistas = "0";
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public void setTitulo(String titulo){
		this.titulo = titulo;
	}
	
	public void setOp(String op){
		this.op = op;
	}
	
	public void setArea(String area){
		this.area = area;
	}
	
	public void setFecha(String fecha){
		this.fecha = fecha;
	}
	
	public void setVistas(String vistas){
		this.vistas = vistas;
	}
	
	public String getUrl(){
		return url;
	}
	
	public String getTitulo(){
		return titulo;
	}
	
	public String getOp(){
		return op;
	}
	
	public String getArea(){
		return area;
	}
	
	public String getFecha(){
		return fecha;
	}
	
	public String getVistas(){
		return vistas;
	}
}

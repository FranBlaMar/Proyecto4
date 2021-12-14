package com.example.demo.utiles;

public class Messages {

	private static final String ERRORLOGIN = "Nombre de usuario o contraseña erroneos";
	private static final String ERRORCATALOGO = "No puedes realizar un pedido sin añadir productos al carrito";

	
	
	
	public Messages() {
		super();
	}
	
	public static String getErrorLogin() {
		return ERRORLOGIN;
	}
	public static String getErrorCatalogo() {
		return ERRORCATALOGO;
	}

}


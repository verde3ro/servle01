package com.appstracta.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appstracta.utils.Conexion;
import com.google.gson.Gson;


/**
 * Servlet implementation class CiudadesServlet
 */
@WebServlet("/ciudades")
public class CiudadesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		Conexion conexion = null;
		Map <String, Object> resultado = new HashMap<>();
		List<Map<String, Object>> ciudades = new ArrayList<>();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try (PrintWriter out = response.getWriter()) {
			try {
				conexion = new Conexion();
				conexion.connectar();

				try (PreparedStatement ps = conexion.getConnection().prepareStatement("SELECT city_id, city FROM city ORDER BY city_id ASCs")) {
					try(ResultSet rs = ps.executeQuery()) {
						while(rs.next()) {
							Map <String, Object> ciudad = new HashMap<>();

							ciudad.put("id", rs.getInt("city_id"));
							ciudad.put("city", rs.getString("city"));

							ciudades.add(ciudad);
						}
					}
				}

				conexion.cerrar();
				resultado.put("estatus", "success");
				resultado.put("datos", ciudades);

				out.print(gson.toJson(resultado));
			} catch (Exception ex) {
				Map<String, String> error = new HashMap<>();

				error.put("estatus", "error");
				error.put("mensaje", ex.getMessage());

				out.print(gson.toJson(error));
			}
		}

	}

}

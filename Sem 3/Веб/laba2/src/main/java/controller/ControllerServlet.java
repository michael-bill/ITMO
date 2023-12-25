package controller;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.PointResult;

public class ControllerServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Content-Type", "application/json");
		if (req.getParameter("clear") != null && req.getParameter("clear").equals("true")) {
			getServletContext().setAttribute("pointStorage", null);
			resp.getWriter().print("{\"values\": []}");
			return;
		}
		if (req.getParameter("history") != null && req.getParameter("history").equals("true")) {
			if (getServletContext().getAttribute("pointStorage") != null) {
				@SuppressWarnings("unchecked")
				ArrayList<PointResult> history = (ArrayList<PointResult>)getServletContext().getAttribute("pointStorage");
				resp.getWriter().print(Utils.pointsToJsonArray(history));
			} else {
				resp.getWriter().print("{\"values\": []}");
			}
			return;
		}
		double x = 0, y = 0, r = 0;
		try {
			x = getDouble(req, "x");
			if (!(-4 <= x && x <= 4)) throw new Exception();
		} catch (Exception e) {
			notValid("X", resp);
			return;
		}
		try {
			y = getDouble(req, "y");
			if (!(-3 <= y && y <= 3)) throw new Exception();
		} catch (Exception e) {
			notValid("Y", resp);
			return;
		}
		try {
			r = getDouble(req, "r");
			if (!(1 <= r && r <= 5)) throw new Exception();
		} catch (Exception e) {
			notValid("R", resp);
			return;
		}
		getServletContext().getRequestDispatcher("/check").forward(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}
	
	private double getDouble(HttpServletRequest req, String param) {
		return Double.parseDouble(req.getParameter(param));
	}
	
	private void notValid(String valueName, HttpServletResponse resp) throws IOException {
		resp.setStatus(400);
		resp.getWriter().print("{\"message\":\"" + valueName + " not valid.\"}");
	}
}

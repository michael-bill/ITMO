package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.PointResult;

public class AreaCheckServlet extends HttpServlet {
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Content-Type", "application/json");
		double x = 0, y = 0, r = 0;
		try {
			x = getDouble(req, "x");
			y = getDouble(req, "y");
			r = getDouble(req, "r");
		} catch (Exception e) {
			return;
		}

		boolean validation = false;
		if ((y <= 0 && y >= -r / 2 && x >= 0 && x <= r / 2 && y * y + x * x <= (r / 2) * (r / 2)) ||
			    (y <= 0 && y >= -r && x <= 0 && x >= -r / 2) ||
			    (y >= 0 && x <= 0 && y <= x / 2 + r / 2)) {
			    validation = true;
		}
		long time = new Date().getTime();
		PointResult result = new PointResult(x, y, r, validation, time);
		ServletContext context = getServletContext();
		ArrayList<PointResult> history;
		if (context.getAttribute("pointStorage") == null) {
			history = new ArrayList<PointResult>();
			history.add(result);
			context.setAttribute("pointStorage", history);
		} else {
			history = (ArrayList<PointResult>) context.getAttribute("pointStorage");
			history.add(result);
			context.setAttribute("pointStorage", history);
		}
        resp.getWriter().print(Utils.pointsToJsonArray(history));
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	private double getDouble(HttpServletRequest req, String param) {
		return Double.parseDouble(req.getParameter(param));
	}
}

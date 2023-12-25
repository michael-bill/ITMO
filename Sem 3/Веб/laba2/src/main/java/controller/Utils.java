package controller;

import java.util.ArrayList;

import model.PointResult;

public class Utils {
	public static String pointsToJsonArray(ArrayList<PointResult> points) {
		String result = "{\"values\": [";
		for (int i = 0; i < points.size(); i++) {
			if (i != points.size() - 1)
				result += points.get(i).toString() + ",";
			else
				result += points.get(i).toString();
		}
		result += "]}";
		return result;
	}
}

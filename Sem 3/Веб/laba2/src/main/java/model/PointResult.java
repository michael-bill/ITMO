package model;


public class PointResult {
	
		private double x;
		private double y;
		private double r;
		private boolean validation;
		private long currentTime;
		
		public PointResult(double x, double y, double r, boolean validation, long currentTime) {
			this.x = x;
			this.y = y;
			this.r = r;
			this.validation = validation;
			this.currentTime = currentTime;
		}

		public double getX() {
			return x;
		}

		public void setX(long x) {
			this.x = x;
		}

		public double getY() {
			return y;
		}

		public void setY(long y) {
			this.y = y;
		}

		public double getR() {
			return r;
		}

		public void setR(long r) {
			this.r = r;
		}

		public boolean isValidation() {
			return validation;
		}

		public void setValidation(boolean validation) {
			this.validation = validation;
		}

		public long getCurrentTime() {
			return currentTime;
		}

		public void setCurrentTime(long currentTime) {
			this.currentTime = currentTime;
		}
		
		@Override
	    public String toString() {
	        return "{" +
	                "\"x\":" + x + "," +
	                "\"y\":" + y + "," +
	                "\"r\":" + r + "," +
	                "\"validation\":" + validation + "," +
	                "\"currentTime\":" + currentTime +
	                "}";
	    }
}

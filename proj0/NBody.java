public class NBody {
	public static double readRadius(String s) {
		In in = new In(s);
		in.readInt();
		return in.readDouble();
	}

	public static Planet[] readPlanets(String s) {
		In in = new In(s);
		int count = in.readInt();
		Planet[] p = new Planet[count];
		in.readDouble();
		for(int i = 0; i < count; i++) {
			double xP = in.readDouble();
			double yP = in.readDouble();
			double xV = in.readDouble();
			double yV = in.readDouble();
			double m = in.readDouble();
			String img = in.readString();
			p[i] = new Planet(xP, yP, xV, yV, m, img);
			System.out.println(xP + " " + yP);
		}
		return p;
	}

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename);
		Planet[] p = readPlanets(filename);

		StdDraw.enableDoubleBuffering();

		double time = 0;
		while(time <= T) {
			double[] xForces = new double[p.length];
			double[] yForces = new double[p.length];
			for(int i = 0; i < p.length; i++) {
				xForces[i] = p[i].calcNetForceExertedByX(p);
				yForces[i] = p[i].calcNetForceExertedByY(p);
			}
			for(int i = 0; i < p.length; i++) {
				p[i].update(dt, xForces[i], yForces[i]);
			}

			String imageToDraw = "images/starfield.jpg";
			StdDraw.setScale(radius * (-1), radius);
			StdDraw.clear();
			StdDraw.picture(0, 0, imageToDraw);

			for(Planet star: p) {
				star.draw();
			}

			StdDraw.show();
			StdDraw.pause(10);

			time += dt;
		}
	}
}












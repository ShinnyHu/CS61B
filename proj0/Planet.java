class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	static final double graConstant = 6.67e-11;

	public Planet(double xP, double yP, double xV,
		double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet p) {
		double xDis = p.xxPos - this.xxPos;
		double yDis = p.yyPos - this.yyPos;
		return Math.sqrt((xDis * xDis) + (yDis * yDis));
	}

	public double calcForceExertedBy(Planet p) {
		return graConstant * p.mass * this.mass 
		/ (calcDistance(p) * calcDistance(p));
	}

	public double calcForceExertedByX(Planet p) {
		double xDis = p.xxPos - xxPos;
		return calcForceExertedBy(p) 
						* xDis / calcDistance(p);
	}

	public double calcForceExertedByY(Planet p) {
		double yDis = p.yyPos - yyPos;
		return calcForceExertedBy(p) 
						* yDis / calcDistance(p);
	}

	public double calcNetForceExertedByX(Planet[] p) {
		double netForce = 0;
		for(int i = 0; i < p.length; i++) {
			if(p[i].equals(this)) {
				continue;
			}
			netForce += calcForceExertedByX(p[i]);
		}
		return netForce;
	}

	public double calcNetForceExertedByY(Planet[] p) {
		double netForce = 0;
		for(int i = 0; i < p.length; i++) {
			if(p[i].equals(this)) {
				continue;
			}
			netForce += calcForceExertedByY(p[i]);
		}
		return netForce;
	}

	public void update(double dt, double fX, double fY) {
		double accX = fX / this.mass;
		double accY = fY / this.mass;
		this.xxVel += accX * dt;
		this.yyVel += accY * dt;
		this.xxPos += xxVel * dt;
		this.yyPos += yyVel * dt;
	}

	public void draw() {
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}















}
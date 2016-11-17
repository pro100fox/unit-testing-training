package com.becomejavasenior.applecar;

public class Camaro extends Chevrolet {

	public enum GearEnum {
		GEAR1(1),		GEAR2(2), GEAR3(3);
		
		int type;
		

		private GearEnum(int type) {
			this.type = type;
		}


		public int getGear() {
			return type;
		}

	}

}

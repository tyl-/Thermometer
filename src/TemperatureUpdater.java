
public class TemperatureUpdater {
	public float Convert(float temp, String unitOne, String unitTwo){
		switch (unitOne){
			case "C":
				switch(unitTwo){
					case "K":
						return (float) (temp + (float)273.15);
					case "F":
						return (float) (((temp * (float)9.00)/ (float)5.00) + (float) 32.00);
					default:
						return (float) temp;
				}
			case "F":
				switch(unitTwo){
				case "K":
					return (float) (((temp + (float)459.67) * (float)5.00) / (float)9.00);
				case "C":
					return (float) (((temp - (float) 32.00) * (float)5.00) / (float)9.00);
				default:
					return (float) temp;
				}
			case "K":
				switch(unitTwo){
				case "C":
					return (float) (temp - (float)273.15);
				case "F":
					return (float) (((temp * (float)9.00)/ (float)5.00) - (float) 459.67);
				default:
					return (float) temp;
				}
			default:
				return (float) temp;
		}			
	}
	
	public float UpdateTemp(float origTemp, float newTemp){
		float difference = origTemp - newTemp;
		float changeTemp;
		if (Math.abs(difference) > 20){
			if(origTemp > newTemp){
				changeTemp = (float)-0.50;
			}else{
				changeTemp = (float)0.50;
			}
		}else if(Math.abs(difference) > 10){
			if(origTemp > newTemp){
				changeTemp = (float)-0.25;
			}else{
				changeTemp = (float)0.25;
			}
		}else if(Math.abs(difference) > 5){
			if(origTemp > newTemp){
				changeTemp = (float)-0.15;
			}else{
				changeTemp = (float)0.15;
			}
		}else{
			if(origTemp > newTemp){
				changeTemp = (float)-0.10;
			}else{
				changeTemp = (float)0.10;
			}
		}
		return origTemp + changeTemp;
	}
}

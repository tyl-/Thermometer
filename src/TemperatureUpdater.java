import java.util.ArrayList;

public class TemperatureUpdater {
	//Converts from one unit to the other
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
	
	//Updates temperature based on difference
	public float UpdateTemp(float origTemp, float newTemp){
		float difference = origTemp - newTemp;
		float changeTemp;
		if (Math.abs(difference) < 0.10){
			return newTemp;
		}else{
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
	
	//Returns the status of temperature changes
	public ArrayList<String> StatusChange(float insideTemp, float outsideTemp){
		float difference = insideTemp - outsideTemp;
		ArrayList<String> returnString = new ArrayList<String>();
		String changeTemp = "";
		if (Math.abs(difference) > 20){
			changeTemp = "0.50 C per second";
		}else if(Math.abs(difference) > 10){
			changeTemp = "0.25 C per second";
		}else if(Math.abs(difference) > 5){
			changeTemp = "0.15 C per second";
		}else if(Math.abs(difference) > 0){
			changeTemp = "0.10 C per second";
		}
				
		if(insideTemp > outsideTemp){
			returnString.add("Decreasing by ");
		}else if(insideTemp < outsideTemp){
			returnString.add("Increasing by ");
		}else{
			returnString.add("Stable");
		}
		returnString.add(changeTemp);
		return returnString;
	}
	
	//Increase or decrease by 1 degree celcius
	public float UpdateByCelcius(float outsideTemp, String currentUnit, String method){
		float tempCelcius = Convert(outsideTemp, currentUnit, "C");
		if (method.equals("INC")){
			tempCelcius++;
		}else if(method.equals("DEC")){
			tempCelcius--;
		}
		return Convert(tempCelcius, "C", currentUnit);
	}
	
}

// runs well on http://repl.it/gtl/2
function DPGPS(){
    entiteID = 100;
    while(entiteID <= 118){
        var DD, MM, YY, HH, mmm, SS, lat, long, entiteID;
        
        var isArtifciel = false;
        var isVivant = false;
        
        //set initial values
        long = Math.floor(Math.random()*180) + 1; // this will get a number between 1 and 99
        var PorN = Math.floor(Math.random()*2) == 1 ? 1 : -1; // this will add minus sign in 50% of cases
        
        long *= PorN;
        
        lat = Math.floor(Math.random()*90) + 1; // this will get a number between 1 and 99
        PorN = Math.floor(Math.random()*2) == 1 ? 1 : -1; // this will add minus sign in 50% of cases
        lat *= PorN;


        // long = 58.68; 
        // lat = 27.08;
        //entiteID =  100;
        // DD = 1;
        // MM = 4
        // YY = 11
        
        HH = 23;
        mmm = 1;
        SS = 5;
		
	switch(entiteID){
	    case 100:
	        isArtifciel = true;
	        DD=7;
	        MM=3;
	        YY=11;
	        break;
	        
	    case 101:
	        isArtifciel = true;
	        DD=1;
	        MM=6;
	        YY=11;
	        break;
	        
	    case 102:
	        isVivant = true;
	        DD=6;
	        MM=3;
	        YY=12;
	        break;
	        
	    case 103:
	        isVivant = true;
	        DD=7;
	        MM=3;
	        YY=13;
	        break;
	        
	    case 104:
	        isVivant = true;
	        DD=6;
	        MM=3;
	        YY=12;
	        break;
	        
	    case 105:
	        isVivant = true;
	        DD=26;
	        MM=3;
	        YY=10;
	        break;
	        
	    case 106:
	        isArtifciel = true;
	        DD=2;
	        MM=1;
	        YY=9;
	        break;
	        
	    case 107:
	        isVivant = true;
	        DD=4;
	        MM=7;
	        YY=12;
	        break;
	        
	    case 108:
	        isArtifciel = true;
	        DD=11;
	        MM=12;
	        YY=10;
	        break;
	        
	    case 109:
	        isVivant = true;
	        DD=22;
	        MM=6;
	        YY=11;
	        break;
	        
	    case 110:
	        isVivant = true;
	        DD=15;
	        MM=11;
	        YY=13;
	        break;
	        
	    case 111:
	        isVivant = true;
	        DD=9;
	        MM=3;
	        YY=14;
	        break;
	        
	    case 112:
	        isArtifciel = true;
	        DD=2;
	        MM=12;
	        YY=13;
	        break;
	        
	    case 113:
	        isArtifciel = true;
	        DD=26;
	        MM=3;
	        YY=14;
	        break;
	        
	    case 114:
	        DD=20;
	        MM=5;
	        YY=14;
	        break;
	        
	    case 115:
	        DD=1;
	        MM=1;
	        YY=11;
	        break;
	        
	    case 116:
	        DD=12;
	        MM=2;
	        YY=15;
	        break;
	        
	    case 117:
	        isArtifciel = true;
	        DD=5;
	        MM=6;
	        YY=8;
	        break;
	        
	    case 118:
	        isArtifciel = true;
	        DD=16;
	        MM=1;
	        YY=15;
	        break;
	        
	    
	}
		//generates 5*5*5 values (125)
        for(k = 0; k<5; k++){
            MM++;
        
            for(j = 0; j<5; j++){
                DD++;
        
     
        
    	        for(i =0; i<5; i++){
    	        result = "Insert into DB_GPS values(";
    
    	        result += entiteID;
    	        result += ",";
    	        var time = " to_date(\'";

                //Sets Day
    	        var d = Math.floor(DD);
    	        d %= 28;
    	        d+=1;
    	        if(d<10) d = "0" + d;
    
                //Sets Month
    	        var m = Math.floor(MM);
    	        m%= 12;
    	        m+=1;
    	        if(m<10) m = "0" + m;
    
                //Sets Year (always same year)
    	        var y = Math.floor(YY);
    	        y += 2000;
    
                //Sets Hour
    	        HH += Math.random();
    	        var h = Math.round(HH);
    	        h %=24;
    	        if(h<10) h = "0" + h;
    
                //Sets Min
                mmm += Math.random();
                mmm += .5;
             	    
    	        var min = Math.round(mmm);
    	        min %= 59;   
    	        if(min<10) min = "0" + min;

                //Sets Seconds
                SS += Math.random() +1;
    	        var s = Math.round(SS);
    	        s %= 59; 
    	        if(s<10) s = "0" + s;
    
                //Builds Time
    	        time+= d + "\/" + m + "\/" + y + " " + h + "\/" + min + "\/" + s + "\', \'DD\/MM\/YYYY HH24\/MI\/SS\'), ";
    
    	        result += time;
    
                //Sets latitude and longitude
                long += Math.random();
    	        long =  +long.toFixed(6);
    	        long %=150;
    	        
    
                lat += Math.random();
    	        lat = +lat.toFixed(6);
    	        lat %=80;
    
    	
    
    	        result += lat + ", " +  long+ ");\n";
    	        console.log(result);
    	        
    	        if(isArtifciel || isVivant){
    	            var artificiel = "Insert into Infos_Scientifique values(";
    
    	        artificiel += entiteID;
    	        artificiel += ",";
    	        artificiel +=time;
    	       // artificiel += ",";
    	        var tempEnviro = Math.round((Math.random()*5) + 10);
    	        var vent = Math.round(Math.random()*10);
    	        var dirVentRan = Math.round((Math.random()*4) + 1);
    	        var directionVent = "Nord";
    	        if(dirVentRan == 1)
    	            directionVent = "Sud";
    	            
    	        if(dirVentRan == 2)
    	            directionVent = "Est";
    	            
    	        if(dirVentRan == 3)
    	            directionVent = "Ouest";

    	        var pressionAtm =Math.round((Math.random() * 3) + 30);
    	        var houle = Math.round(Math.random()*10);
    	        var altitude = Math.round(Math.random()*25);
    	        var vitesse = Math.round(Math.random()*8);
    	        
    	        artificiel += tempEnviro + ", ";
    	        artificiel += vent + ", ";
    	        artificiel += "\'" + directionVent + "\'" + ", ";
    	        artificiel += pressionAtm + ", ";
    	        artificiel += houle + ", ";
    	        artificiel += altitude + ", ";
    	        artificiel += vitesse + ");\n ";
    	        console.log(artificiel);

    	        }
    	        
    	        if(isVivant){
    	            var vivant = "Insert into Infos_Scientifique_Vivant values(";
    	            vivant += entiteID;
    	            vivant += ",";
    	            vivant +=time;
    	       
    	            var pouls = Math.round((Math.random()*10) + 80);
    	            var pressionArterielle = Math.round((Math.random()*10) + 110);
    	            var pourcentageGras = (Math.random()*3) + 7;
    	            pourcentageGras =   +pourcentageGras.toFixed(2);
    	            var temperatureCorps = Math.round((Math.random()*2) + 97);

                    vivant += pouls + ", ";
                    vivant += pressionArterielle + ", ";
                    vivant += pourcentageGras + ", ";
                    vivant += temperatureCorps + ");\n";
                    console.log(vivant);

    	        }

            }
        }
    }
    entiteID++;
    }
}
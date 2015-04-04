// runs well on http://repl.it/gtl/2
function DPGPS(){
    
        var DD, MM, YY, HH, mmm, SS, lat, long, entiteID;
        //set initial values
        long = 58.68; 
        lat = 27.08;
        entiteID =  100;
        DD = 1;
        MM = 4
        YY = 11
        
        HH = 23;
        mmm = 1;
        SS = 5;
		
		//generates 5*5*5 values (125)
        for(k = 0; k<5; k++){
            MM++
        
            for(j = 0; j<5; j++){
                DD++
        
     
        
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
    
                lat += Math.random();
    	        lat = +lat.toFixed(6);
    	 
    
    	
    
    	        result += lat + ", " +  long+ ");\n";
    	        console.log(result);
            }
        }
    }
    
    
}
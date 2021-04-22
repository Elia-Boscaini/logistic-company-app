package application.model;

import java.util.Map.Entry;

public class ClientInfoChecker {
	
	LogisticCompany lc;
	
	public ClientInfoChecker(LogisticCompany logisticCompany) {
		this.lc = logisticCompany;
	}
	
	public boolean checkAllInfo(Client c) {
		return this.checkNameValid(c.getName()) &&
			   this.checkEmailValid(c.getEmail()) &&
			   this.checkAddressValid(c.getAddress()) &&
			   this.checkReferencePersonValid(c.getReferencePerson());
	}

	public boolean checkReferencePersonValid(String rp) {
        // further implementation can be done
        if (!(rp == null) && rp.matches("[a-zA-Z ]+")) {
        	
        	return true;
        	
        }
        return false;
    }
	public boolean checkLocation(String l) {
		if (!(l == null) && l.matches("[a-zA-Z ]+")) {
        	
        	return true;
        	
        }
        return false;
	}

    public boolean checkEmailValid(String email) {
        // Check whether a @ is there and if it is not already taken
        if (!(email == null) && email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$") && checkEmailTaken(email)) {
        return true;
        }
        return false;
    }

    public boolean checkAddressValid(String address) {
        String[] parts = address.split(" ");
        if ( parts.length==3 ) {
        	if (parts[1].matches("[0-9]+")) {
        		return true;
        	}
        }
        return false;
    }

    public boolean checkNameValid(String name) {
        if (!(name == null) && name.matches("[a-zA-Z 0-9]+") && checkNameTaken(name)) {
        	return true;
        }
        return false;
        
    }
    
    private boolean checkEmailTaken(String email) {
		for (Entry<Integer, Client> entry : lc.getDatabase().entrySet()) {
			if (entry.getValue().getEmail().equals(email)) {
				return false;
			}
		}return true;
    }
    
    
    
    private boolean checkNameTaken(String name) {
		for (Entry<Integer, Client> entry : lc.getDatabase().entrySet()) {
			if (entry.getValue().getName().equals(name)) {
				return false;
			}
		}return true;
    	
    }
    
    
    //Implement!
    public boolean checkJourneyDetails(String origin, String destination, String content) {
    	if(checkOrigin(origin)&& checkDestination(destination)) {
    		return true;
    	}
    	return false;
    } 
    
    private boolean checkOrigin(String origin) {
    	 // further implementation can be done
        if (!(origin == null) && origin.matches("[a-zA-Z ]+")) {
        	
        	return true;
        	
        }
        return false;
    }
    public boolean checkDestination(String destination) {
   	 // further implementation can be done
       if (!(destination == null) && destination.matches("[a-zA-Z ]+")) {
       	
       	return true;
       	
       }
       return false;
   }
//Implement more
	public boolean checkDate(long date) {
		if (date>0) {
			return true;
		}
		return false;
	}
	
	public boolean checkPassword(String p) {
		if (p.length()<3) {
			return false;
		}return true;
	}
    
}

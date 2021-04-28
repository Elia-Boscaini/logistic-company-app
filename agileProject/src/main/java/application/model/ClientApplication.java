package application.model;

import java.util.ArrayList;
import java.util.List;

public class ClientApplication{
	
	
	private int clientID;
	
	public int getClientID() {
		return clientID;
	}


	private LogisticCompany lc;
	public ClientApplication(int clientID,LogisticCompany lc) {
		this.clientID = clientID;
		this.lc=lc;
		
	}	
	
	public String updateName(String name) {
		
		 if (lc.getCic().checkNameValid(name)) {
			 lc.getClientDatabase().changeName(clientID, name);
			 return "Successful Update";
	     } else {
			return "Unsuccessful Update";
		 }
	}
	
	public String updateEmail(String email) {
		
		if (lc.getCic().checkEmailValid(email)) {
			 lc.getClientDatabase().changeEmail(clientID, email);
			
			return "Successful Update";
		} else {
			return "Unsuccessful Update";
		} 
	}
	
	
	public String updateAdress(String address) {
		
		if (lc.getCic().checkAddressValid(address)) {
			 lc.getClientDatabase().changeAddress(clientID, address);
			 return "Successful Update";
	    } else {
			return "Unsuccessful Update";
		}
        
	}
	
	
	public String updatePassword(String password) {

		// maybe have minimum size, required sign or Capital letter
		if (lc.getCic().checkPassword(password)) {
			 lc.getClientDatabase().changePassword(clientID, password);
			return "Successful Update";
		} else {
			return "Unsuccessful Update";
		}
	}
 	
	public String updateContactPerson(String rp) {

		if (lc.getCic().checkReferencePersonValid(rp)) {
			 lc.getClientDatabase().changeContactPerson(clientID, rp);
			 
			return "Successful Update";
		} else {
			return "Unsuccessful Update";
		}
	}
	

	public List<Journey> filterJourneysbyContent(String content) {

		List<Journey> results = new ArrayList<Journey>();
		for (int i = 0;i<lc.getJourneys().size();i++) {
			
			if (lc.getJourneys().getValueFromID(i).getClientid()==clientID && 
					lc.getJourneys().getValueFromID(i).getContent().equals(content)) {
				results.add(lc.getJourneys().getValueFromID(i));
			}
		}return results;
	}
	public List<Journey> filterJourneysbyDestination(String destination) {

		List<Journey> results = new ArrayList<Journey>();
		for (int i = 0;i<lc.getJourneys().size();i++) {
			
			if (lc.getJourneys().getValueFromID(i).getClientid()==clientID && 
					lc.getJourneys().getValueFromID(i).getDestination().equals(destination)) {
				results.add(lc.getJourneys().getValueFromID(i));
			}
		}return results;
	}
	
	public List<Journey> filterJourneysbyOrigin(String origin) {

		List<Journey> results = new ArrayList<Journey>();
		for (int i = 0;i<lc.getJourneys().size();i++) {
			
			if (lc.getJourneys().getValueFromID(i).getClientid()==clientID && 
					lc.getJourneys().getValueFromID(i).getOrigin().equals(origin)) {
				results.add(lc.getJourneys().getValueFromID(i));
			}
		}return results;
	}
	
	
	//TODO not enough container message is not displayed
	public String registerJourney(Journey j) {

		if (lc.getCic().checkJourneyDetails(j.getOrigin(),j.getDestination(),j.getContent())) {
			ArrayList<Container> containerList = new ArrayList<Container>();
			boolean enoughContainers = true;
			int jid = lc.getJourneys().size();
			for (int i = 0; i < j.getNOfContainers(); i++) {
				int Cid = lc.getContainers().getIDfromEmptyContainerLocation(j.getOrigin());
				if (Cid == -1) {
					enoughContainers = false;
					
				}else {
					lc.getContainers().startJourney(Cid, jid, j.getContent());
					containerList.add(lc.getContainers().getValueFromID(Cid));
					
				}
				
			}
			if (!enoughContainers) {
				for (Container c : containerList) {
					lc.getContainers().finishJourney(c.getID());
				
				}
				return "Not enough containers";
			}else {
				for (Container c : containerList) {
					lc.getContainersHistory().registerValue(new ContainerStatus(0, 0, 0, 0, jid, c.getID()));
				
				}
				lc.getJourneys().registerValue(j);
				return "Successful Registration";
			}
			
			
			
		}
		return "Invalid Info";
		
	}
	
	
	public List<ContainerStatus> getLatestStatus(int journeyid) {
		List<ContainerStatus> results = new ArrayList<ContainerStatus>();
		if (lc.getJourneys().containsKey(journeyid)) {

		
			if (lc.getJourneys().getValueFromID(journeyid).getClientid()==clientID) {
				
				for (Container c : lc.getContainers().containerOnJourney(journeyid)) {
					
					List<ContainerStatus> lcs = lc.getContainersHistory().getContainerStatusfromJourney(journeyid, c.getID());
					// if we have time we can check the most recent one, using date
					if(lcs.size() > 0) {
						System.out.print("container status");
						results.add(lcs.get(lcs.size() - 1));
					}
					
					
				}
			}
			return results;
		}return results;
		
	} 
	
	
	public List<ContainerStatus> getclosestStatus(int journeyid,long date){

		List<ContainerStatus> results = new ArrayList<ContainerStatus>();
		if (lc.getJourneys().containsKey(journeyid)&&lc.getCic().checkDate(date)) {
			int count = 0;
			int index = 0;
			if (lc.getJourneys().getValueFromID(journeyid).getClientid()==clientID) {

				
				for (Container c : lc.getContainers().containerOnJourney(journeyid)) {
					List<ContainerStatus> lcs = lc.getContainersHistory().getContainerStatusfromJourney(journeyid, c.getID());
					if(lcs.size() > 0) {
						long diff = lcs.get(0).getDifference(date);
						count = 0;
						index = 0;
						for (ContainerStatus cs : lcs) {
							
							if (diff>cs.getDifference(date)) {
								diff = cs.getDifference(date);
					 			index = count;
							}
							count++;
						}

						results.add(lcs.get(index));
					}
				}
			}
			return results;
		}return results;
		
	}
}

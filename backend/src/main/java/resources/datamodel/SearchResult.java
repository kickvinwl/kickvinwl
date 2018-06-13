package resources.datamodel;

import java.util.ArrayList;

import entities.Group;
import entities.User;

public class SearchResult {
	
	private ArrayList<User> users;
	private ArrayList<Group> groups;
	
	public SearchResult() {
		users = new ArrayList<User>();
		groups = new ArrayList<Group>();
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public ArrayList<Group> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}

}

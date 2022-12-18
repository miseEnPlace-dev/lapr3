package isep.model;

import java.util.HashMap;

import isep.shared.exceptions.InvalidHubException;
import isep.shared.exceptions.InvalidOrderException;

public class Basket {
	private HashMap<Product, Integer> ordered;
	private HashMap<Producer, HashMap<Product, Integer>> received;
	private Enterprise hub;
	private Client client;

	public Basket(HashMap<Product, Integer> ordered, HashMap<Producer, HashMap<Product, Integer>> received,
			Enterprise hub, Client client) throws InvalidOrderException, InvalidHubException {
		setOrdered(ordered);
		setReceived(received);
		setHub(hub);
		setClient(client);
	}

	private void setOrdered(HashMap<Product, Integer> ordered) throws InvalidOrderException {
		if (ordered == null)
			throw new NullPointerException("Null ordered map is Invalid!");
		Integer sum = 0;
		for (Integer qnt : ordered.values()) {
			sum += qnt;
		}
		if (sum == 0)
			throw new InvalidOrderException();
		this.ordered = ordered;
	}

	private void setReceived(HashMap<Producer, HashMap<Product, Integer>> received) {
		if (received == null)
			throw new NullPointerException("Null received map is Invalid!");
		this.received = received;
	}

	public void setHub(Enterprise enterprise) throws InvalidHubException {
		if (enterprise == null)
			throw new NullPointerException("Null hub is Invalid!");
		if (!enterprise.isHub())
			throw new InvalidHubException();
		this.hub = enterprise;
	}

	public void setClient(Client client) {
		if (client == null)
			throw new NullPointerException("Null client is Invalid!");
		this.client = client;
	}

}

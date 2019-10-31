package de.quandoo.recruitment.registry;

import de.quandoo.recruitment.registry.api.CuisinesRegistry;
import de.quandoo.recruitment.registry.model.Cuisine;
import de.quandoo.recruitment.registry.model.Customer;

import java.util.*;

public class InMemoryCuisinesRegistry implements CuisinesRegistry {

    private Map<Customer, Set<Cuisine>> cuisinesByCustomers = new HashMap<>();
    private Map<Cuisine, Set<Customer>> customersByCuisines = new HashMap<>();

    @Override
    public void register(final Customer customer, final Cuisine cuisine) {
        if(customer == null || cuisine == null) {

        }

        Set<Cuisine> cuisines;
        if(cuisinesByCustomers.containsKey(customer)) {
            cuisines = cuisinesByCustomers.get(customer);
            cuisines.add(cuisine);
        } else {
            cuisines = new HashSet<>();
            cuisines.add(cuisine);
            cuisinesByCustomers.put(customer, cuisines);
        }

        Set<Customer> customers;
        if(customersByCuisines.containsKey(cuisine)) {
            customers = customersByCuisines.get(cuisine);
            customers.add(customer);
        } else {
            customers = new HashSet<>();
            customers.add(customer);
            customersByCuisines.put(cuisine, customers);
        }
    }

    @Override
    public List<Customer> cuisineCustomers(final Cuisine cuisine) {
        if(cuisine == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(customersByCuisines.get(cuisine));
    }

    @Override
    public List<Cuisine> customerCuisines(final Customer customer) {
        if(customer == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(cuisinesByCustomers.get(customer));
    }

    @Override
    public List<Cuisine> topCuisines(final int n) {
        PriorityQueue<Map.Entry<Cuisine, Set<Customer>>> queue = new PriorityQueue<>(new TopCuisineComparator());
        customersByCuisines.entrySet().forEach((entry) -> queue.add(entry));

        List<Cuisine> topCuisines = new ArrayList<>(n);
        for(int i = 0; i < n && !queue.isEmpty(); i++) {
            topCuisines.add(queue.poll().getKey());
        }
        return topCuisines;
    }

    class TopCuisineComparator implements Comparator<Map.Entry<Cuisine, Set<Customer>>> {

        @Override
        public int compare(Map.Entry<Cuisine, Set<Customer>> left, Map.Entry<Cuisine, Set<Customer>> right) {
            return Integer.compare(right.getValue().size(), left.getValue().size());
        }
    }
}

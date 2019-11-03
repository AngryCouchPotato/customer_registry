package de.quandoo.recruitment.registry;

import de.quandoo.recruitment.registry.api.CuisinesRegistry;
import de.quandoo.recruitment.registry.model.Cuisine;
import de.quandoo.recruitment.registry.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemoryCuisinesRegistry implements CuisinesRegistry {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryCuisinesRegistry.class);

    private ConcurrentMap<Customer, Set<Cuisine>> cuisinesByCustomers = new ConcurrentHashMap<>();
    private ConcurrentMap<Cuisine, Set<Customer>> customersByCuisines = new ConcurrentHashMap<>();

    @Override
    public void register(final Customer customer, final Cuisine cuisine) {
        if(customer == null) {
            logger.error(String.format("Customer can not be null. Cuisine = %s", cuisine));
            return;
        }
        if(cuisine == null) {
            logger.error(String.format("Cuisine can not be null. Customer = %s", customer));
            return;
        }
        registerCuisineByCustomer(cuisine, customer);
        registerCustomerByCuisine(customer, cuisine);
    }

    private void registerCuisineByCustomer(Cuisine cuisine, Customer customer) {
        cuisinesByCustomers.computeIfAbsent(customer, key -> new HashSet<>());
        cuisinesByCustomers.computeIfPresent(customer, (key, cuisines) -> {
            cuisines.add(cuisine);
            return cuisines;
        });
    }

    private void registerCustomerByCuisine(Customer customer, Cuisine cuisine) {
        customersByCuisines.computeIfAbsent(cuisine, key -> new HashSet<>());
        customersByCuisines.computeIfPresent(cuisine, (key, customers) -> {
            customers.add(customer);
            return customers;
        });
    }

    @Override
    public List<Customer> cuisineCustomers(final Cuisine cuisine) {
        if(cuisine == null || !customersByCuisines.containsKey(cuisine)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(customersByCuisines.get(cuisine));
    }

    @Override
    public List<Cuisine> customerCuisines(final Customer customer) {
        if(customer == null || !cuisinesByCustomers.containsKey(customer)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(cuisinesByCustomers.get(customer));
    }

    @Override
    public List<Cuisine> topCuisines(final int n) {
        PriorityQueue<Map.Entry<Cuisine, Set<Customer>>> queue = new PriorityQueue<>(new TopCuisineComparator());
        queue.addAll(customersByCuisines.entrySet());

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

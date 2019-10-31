package de.quandoo.recruitment.registry;

import de.quandoo.recruitment.registry.model.Cuisine;
import de.quandoo.recruitment.registry.model.Customer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

public class InMemoryCuisinesRegistryTest {

    private InMemoryCuisinesRegistry cuisinesRegistry = new InMemoryCuisinesRegistry();

    @Before
    public void init() {
        cuisinesRegistry.register(new Customer("1"), new Cuisine("French"));
        cuisinesRegistry.register(new Customer("2"), new Cuisine("Russian"));
        cuisinesRegistry.register(new Customer("3"), new Cuisine("German"));
        cuisinesRegistry.register(new Customer("4"), new Cuisine("German"));
        cuisinesRegistry.register(new Customer("5"), new Cuisine("Italian"));
        cuisinesRegistry.register(new Customer("6"), new Cuisine("Italian"));
        cuisinesRegistry.register(new Customer("7"), new Cuisine("Italian"));
        cuisinesRegistry.register(new Customer("7"), new Cuisine("French"));
        cuisinesRegistry.register(new Customer("7"), new Cuisine("Russian"));
        cuisinesRegistry.register(new Customer("7"), new Cuisine("German"));
        cuisinesRegistry.register(new Customer("8"), new Cuisine("Italian"));
    }

    @Test
    public void cuisineCustomers() {
        // Given
        Cuisine cuisine = new Cuisine("French");
        List<Customer> expectedCustomers = new ArrayList<>(2);
        expectedCustomers.add(new Customer("1"));
        expectedCustomers.add(new Customer("7"));

        // When
        List<Customer> customers = cuisinesRegistry.cuisineCustomers(cuisine);

        // Then
        assertNotNull(customers);
        assertEquals(2, customers.size());
        assertThat("List equality without order",
            expectedCustomers, containsInAnyOrder(customers.toArray()));
    }

    @Test
    public void cuisineCustomersWithNullableCuisine() {
        // Given
        Cuisine cuisine = null;

        // When
        List<Customer> customers = cuisinesRegistry.cuisineCustomers(cuisine);

        // Then
        assertNotNull(customers);
        assertEquals(0, customers.size());
    }

    @Test
    public void customerCuisines() {
        // Given
        Customer customer = new Customer("7");
        List<Cuisine> expectedCuisines = new ArrayList<>(4);
        expectedCuisines.add(new Cuisine("Italian"));
        expectedCuisines.add(new Cuisine("French"));
        expectedCuisines.add(new Cuisine("Russian"));
        expectedCuisines.add(new Cuisine("German"));

        // When
        List<Cuisine> cuisines = cuisinesRegistry.customerCuisines(customer);

        // Then
        assertNotNull(cuisines);
        assertEquals(4, cuisines.size());
        assertThat("List equality without order",
            expectedCuisines, containsInAnyOrder(cuisines.toArray()));
    }

    @Test
    public void customerCuisinesWithNullableCustomer() {
        // Given
        Customer customer = null;

        // When
        List<Cuisine> cuisines = cuisinesRegistry.customerCuisines(customer);

        // Then
        assertNotNull(cuisines);
        assertEquals(0, cuisines.size());
    }

    @Test
    public void topCuisinesOnlyOne() {
        // Given
        Cuisine expectedCuisine = new Cuisine("Italian");
        int count = 1;

        // When
        List<Cuisine> topCuisines = cuisinesRegistry.topCuisines(count);

        // Then
        assertNotNull(topCuisines);
        assertEquals(1, topCuisines.size());
        assertEquals(expectedCuisine, topCuisines.get(0));
    }

  @Test
  public void topCuisinesMultiple() {
    // Given
    List<Cuisine> expectedCuisines = new ArrayList<>(4);
    expectedCuisines.add(new Cuisine("Italian"));
    expectedCuisines.add(new Cuisine("German"));
    int count = 2;

    // When
    List<Cuisine> topCuisines = cuisinesRegistry.topCuisines(count);

    // Then
    assertNotNull(topCuisines);
    assertEquals(2, topCuisines.size());
    assertThat("List equality without order",
        expectedCuisines, containsInAnyOrder(topCuisines.toArray()));
  }


}
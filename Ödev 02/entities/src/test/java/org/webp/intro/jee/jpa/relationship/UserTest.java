package org.webp.intro.jee.jpa.relationship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


public class UserTest {

    private EntityManagerFactory factory;
    private EntityManager em;

    @BeforeEach
    public void init() {
        factory = Persistence.createEntityManagerFactory("DB");
        em = factory.createEntityManager();
    }

    @AfterEach
    public void tearDown() {
        em.close();
        factory.close();
    }

    private boolean persistInATransaction(Object... obj) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            for(Object o : obj) {
                em.persist(o);
            }
            tx.commit();
        } catch (Exception e) {
            System.out.println("FAILED TRANSACTION: " + e.toString());
            tx.rollback();
            return false;
        }

        return true;
    }



    @Test
    public void testEmptyUser(){

        User user = new User();
        assertTrue(persistInATransaction(user));
    }


    @Test
    public void testUserWithAddress(){

        Address address = new Address();
        AddressWithUserLink addressWithUserLink = new AddressWithUserLink();

        //Bir entity dogrudan veritabanina kaydedilebilir
        assertTrue(persistInATransaction(address));
        assertTrue(persistInATransaction(addressWithUserLink));

        //henuz user tanimlanmadigi icin null olmalidir
        assertNull(addressWithUserLink.getUser());

        User user = new User();
        user.setAddress(address);
        user.setAddressWithUserLink(addressWithUserLink);
        addressWithUserLink.setUser(user);

        assertTrue(persistInATransaction(user));
    }




    @Test
    public void testElementCollection(){

        User u = new User();
        u.setRoles(new ArrayList<>());

        u.getRoles().add("Admin");

        assertTrue(persistInATransaction(u));
    }
}
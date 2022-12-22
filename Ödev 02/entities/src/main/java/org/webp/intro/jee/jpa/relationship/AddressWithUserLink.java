package org.webp.intro.jee.jpa.relationship;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class AddressWithUserLink extends Address {


    @OneToOne(mappedBy = "addressWithUserLink")

    private User user;

    public AddressWithUserLink(){}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

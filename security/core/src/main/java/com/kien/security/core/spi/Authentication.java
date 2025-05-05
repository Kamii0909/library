package com.kien.security.core.spi;

import java.security.Principal;

/**
 * Represent the information known about an entity privileges. For example, the
 * most common {@code Authentication} is an {@code User}.
 * <p>
 * Each {@code Authentication} is a 1-1 mapping to the privilege granted to an
 * entity. Each entity could have many privilege (or many sets of privileges).
 * <p>
 * For example, a real-life entity such as an user could have many ways to login
 * a service, using a secured physical device, an API key or a the traditional
 * username-password combination. Each authentication returned by any of these
 * method should have the same privilege.
 * <p>
 * On the other hand, consider the following scenario. An user provides 3rd
 * party applications (or even just themself) with API keys, each having a
 * different set of authorities. While each of these keys should be understood
 * as the same original user, each of them should be mapped to a different "set"
 * of priviledge.
 */
public interface Authentication extends Principal {
    Privilege getPrivilege();
    
    Object getDetails();
}

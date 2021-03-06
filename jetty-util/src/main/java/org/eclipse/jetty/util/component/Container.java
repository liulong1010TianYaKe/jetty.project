//
//  ========================================================================
//  Copyright (c) 1995-2017 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package org.eclipse.jetty.util.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * A Container
 */
public interface Container
{
    /* ------------------------------------------------------------ */
    /**
     * Add a bean.  If the bean is-a {@link Listener}, then also do an implicit {@link #addEventListener(Listener)}.
     * @param o the bean object to add
     * @return true if the bean was added, false if it was already present
     */
    public boolean addBean(Object o);

    /**
     * @return the list of beans known to this aggregate
     * @see #getBean(Class)
     */
    public Collection<Object> getBeans();

    /**
     * @param clazz the class of the beans
     * @return the list of beans of the given class (or subclass)
     * @param <T> the Bean type
     * @see #getBeans()
     * @see #getContainedBeans(Class)
     */
    public <T> Collection<T> getBeans(Class<T> clazz);

    /**
     * @param clazz the class of the bean
     * @return the first bean of a specific class (or subclass), or null if no such bean exist
     * @param <T> the Bean type 
     */
    public <T> T getBean(Class<T> clazz);

    /**
     * Removes the given bean.
     * If the bean is-a {@link Listener}, then also do an implicit {@link #removeEventListener(Listener)}.
     * @param o the bean to remove
     * @return whether the bean was removed
     */
    public boolean removeBean(Object o);
    
    /**
     * Add an event listener. 
     * @see Container#addBean(Object)
     * @param listener the listener to add
     */
    public void addEventListener(Listener listener);
    
    /**
     * Remove an event listener. 
     * @see Container#removeBean(Object)
     * @param listener the listener to remove
     */
    public void removeEventListener(Listener listener);

    /**
     * Unmanages a bean already contained by this aggregate, so that it is not started/stopped/destroyed with this
     * aggregate.
     *
     * @param bean The bean to unmanage (must already have been added).
     */
    void unmanage(Object bean);

    /**
     * Manages a bean already contained by this aggregate, so that it is started/stopped/destroyed with this
     * aggregate.
     *
     * @param bean The bean to manage (must already have been added).
     */
    void manage(Object bean);


    /**
     * Test if this container manages a bean
     * @param bean the bean to test
     * @return whether this aggregate contains and manages the bean
     */
    boolean isManaged(Object bean);

    /**
     * Adds the given bean, explicitly managing it or not.
     *
     * @param o       The bean object to add
     * @param managed whether to managed the lifecycle of the bean
     * @return true if the bean was added, false if it was already present
     */
    boolean addBean(Object o, boolean managed);

    /**
     * A listener for Container events.
     * If an added bean implements this interface it will receive the events
     * for this container.
     */
    public interface Listener
    {
        void beanAdded(Container parent,Object child);
        void beanRemoved(Container parent,Object child);
    }
    
    /**
     * Inherited Listener.
     * If an added bean implements this interface, then it will 
     * be added to all contained beans that are themselves Containers
     */
    public interface InheritedListener extends Listener
    {
    }

    /**
     * @param clazz the class of the beans
     * @return the list of beans of the given class from the entire managed hierarchy
     * @param <T> the Bean type
     */
    public <T> Collection<T> getContainedBeans(Class<T> clazz);
}

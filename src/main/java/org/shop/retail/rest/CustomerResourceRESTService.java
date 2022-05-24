/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.shop.retail.rest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.shop.retail.model.Customer;

@Path("/customers")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
public class CustomerResourceRESTService {

    private static Map<Long, Customer> customersRepository = new HashMap<Long, Customer>();
    @POST
    public Response createcustomer(Customer customer) {

        Response.ResponseBuilder builder = null;
        Long nextId = customersRepository.keySet().size() + 1L;
        try {
            // Store the customer
            customer.setId(nextId);
            customersRepository.put(nextId, customer);

            // Create an "ok" response with the persisted customer
            builder = Response.ok(customer);
        } catch (Exception e) {
            // Handle generic exceptions
            builder = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage());
        }

        return builder.build();
    }

    // edit customer
    @PUT
    @Path("/{id}")
    public void updateContact (final @PathParam("id") Long id, Customer update) {
       Customer current = customersRepository.get(id);
       if (current == null) throw new WebApplicationException(Response.Status.NOT_FOUND);

       current.setName(update.getName());
       current.setPhoneNumber(update.getPhoneNumber());
       current.setRegister(update.getRegister());
    }
    
    // delete all customers
    @DELETE
    public Response removeAllcustomers() {
        customersRepository.clear();
        return Response.ok().build();
    }

    // delete a specific customer
    @DELETE
    @Path("/{id}")
    public Response removecustomer(final @PathParam("id") Long id) {
        customersRepository.remove(id);
        return Response.ok().build();
    }

    // Fetch all customers
    @GET
    public Response getAll() {
        Collection<Customer> allcustomers = customersRepository.values();
        return Response.ok(allcustomers).build();
    }

    // Fetch a specific customer
    @GET
    @Path("/{id}")
    public Response getById(final @PathParam("id") Long id) {
        Customer customer = customersRepository.get(id);
        return Response.ok(customer).build();
    }
    
    
    // add to register
    @PUT
    @Path("{id}/register/add/{item_id}")
    public void addToRegister (final @PathParam("id") Long id, final @PathParam("item_id") Long item_id) {
       Customer current = customersRepository.get(id);
       if (current == null) throw new WebApplicationException(Response.Status.NOT_FOUND);

       current.addToRegister(item_id);
    }
    
    // remove from register
    @PUT
    @Path("{id}/register/remove/{item_id}")
    public void removeFromRegister (final @PathParam("id") Long id, final @PathParam("item_id") Long item_id) {
       Customer current = customersRepository.get(id);
       if (current == null) throw new WebApplicationException(Response.Status.NOT_FOUND);

       current.removeFromRegister(item_id);
    }
}

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

import org.shop.retail.model.Item;

@Path("/items")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
public class ItemResourceRESTService {

    private static Map<Long, Item> itemsRepository = new HashMap<Long, Item>();

    /**
     * Creates a new item from the values provided and will return a JAX-RS response with either 200 ok, or 400 (BAD REQUEST)
     * in case of errors.
     */
    @POST
    public Response createitem(Item item) {

        Response.ResponseBuilder builder = null;
        Long nextId = itemsRepository.keySet().size() + 1L;
        try {
            // Store the item
            item.setId(nextId);
            itemsRepository.put(nextId, item);

            // Create an "ok" response with the persisted item
            builder = Response.ok(item);
        } catch (Exception e) {
            // Handle generic exceptions
            builder = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage());
        }

        return builder.build();
    }

    // edit item
    @PUT
    @Path("/{id}")
    public void updateContact (final @PathParam("id") Long id, Item update) {
       Item current = itemsRepository.get(id);
       if (current == null) throw new WebApplicationException(Response.Status.NOT_FOUND);

       current.setName(update.getName());
       current.setPrice(update.getPrice());
    }
    
    // delete all items
    @DELETE
    public Response removeAllitems() {
        itemsRepository.clear();
        return Response.ok().build();
    }

    // delete a specific item
    @DELETE
    @Path("/{id}")
    public Response removeitem(final @PathParam("id") Long id) {
        itemsRepository.remove(id);
        return Response.ok().build();
    }

    // Fetch all items
    @GET
    public Response getAll() {
        Collection<Item> allitems = itemsRepository.values();
        return Response.ok(allitems).build();
    }

    // Fetch a specific item
    @GET
    @Path("/{id}")
    public Response getById(final @PathParam("id") Long id) {
        Item item = itemsRepository.get(id);
        return Response.ok(item).build();
    }
}

package com.endpoints;

import java.util.List;
import java.util.stream.Collectors;

import com.entities.Component;
import com.services.GenericEntityService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/components")
public class ComponentEndpoint {

    @Inject
    private GenericEntityService<Component> componentService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComponents() {
        List<Component> componentList = componentService.findAll(Component.class);

        if (componentList.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        List<Component> copyList = componentList.stream()
                .map(e -> new Component(e.getName(), e.getPrice(), e.getDescription(), e.getTransactionParty()))
                .collect(Collectors.toList());

        return Response.ok(copyList).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postComponent(Component component, @Context UriInfo uriInfo) {
        componentService.save(component);

        return Response.ok(component).build();
    }
    @POST
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response postComponents(List<Component> componentList, @Context UriInfo uriInfo) {
		componentList.forEach(e -> componentService.save(e));

		return Response.ok(componentList).build();
	}
}

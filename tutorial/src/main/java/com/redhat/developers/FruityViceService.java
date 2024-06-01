package com.redhat.developers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/fruit")
@RegisterRestClient
public interface FruityViceService {

  @GET
  @Path("/{name}")
  @Produces(MediaType.APPLICATION_JSON)
  @Retry(maxRetries = 3, delay = 2000)
  FruityVice getFruitByName(@PathParam("name") String name);

}
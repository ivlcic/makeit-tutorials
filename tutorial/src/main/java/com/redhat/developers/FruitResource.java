package com.redhat.developers;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/fruit")
public class FruitResource {

  @RestClient
  @Inject
  FruityViceService fruityViceService;

  @Transactional
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response newFruit(Fruit fruit) {
    fruit.id = null;
    fruit.persist();
    return Response.status(Status.CREATED).entity(fruit).build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<FruitDTO> fruits(@QueryParam("season") String season) {
    if (season != null) {
      return Fruit.findBySeason(season).stream()
          .map(fruit -> FruitDTO.of(fruit, fruityViceService.getFruitByName(fruit.name)))
          .collect(Collectors.toList());
    }
    return Fruit.<Fruit>listAll().stream()
        .map(fruit -> FruitDTO.of(fruit, fruityViceService.getFruitByName(fruit.name)))
        .collect(Collectors.toList());
  }
}

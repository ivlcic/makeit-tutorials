package com.redhat.developers;

import java.util.List;

import io.quarkus.logging.Log;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/fruit-local")
public class FruitResourceLocal {

  FruitRepository fruitRepository;

  public FruitResourceLocal(FruitRepository fruitRepository) {
    this.fruitRepository = fruitRepository;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Fruit> fruits(@QueryParam("season") String season) {
    if (season != null) {
      Log.infof("Searching for %s fruits", season);
      return fruitRepository.findBySeason(season);
    }
    return Fruit.listAll();
  }

  @Transactional
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response create(Fruit fruit) {
    fruit.id = null;
    fruit.persist();
    return Response.status(Status.CREATED).entity(fruit).build();
  }

  @GET
  @Path("/{name}")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Fruit> getFruitByName(@PathParam("name") String name) {
    if (name != null) {
      Log.infof("Searching for %s fruits", name);
      return fruitRepository.findByName(name);
    }
    return Fruit.listAll();
  }

}

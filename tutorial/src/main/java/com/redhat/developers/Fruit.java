package com.redhat.developers;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Fruit extends PanacheEntity {
  public String name;

  public String season;

  public static List<Fruit> findBySeason(String season) {
    return find("season", season).list();
  }
}

package fr.fms.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity
public class Article implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String brand;
  private String description;
  private float price;

  public Article() {}

  public Article(Long id, String brand, String description, float price) {
    this.id = id;
    this.brand = brand;
    this.description = description;
    this.price = price;
  }
}

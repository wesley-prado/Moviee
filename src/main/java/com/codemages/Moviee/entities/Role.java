package com.codemages.moviee.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "role_tb")
@Getter
public class Role implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  @JsonIgnore
  @OneToMany(mappedBy = "role")
  private Set<User> users = new HashSet<>();

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private RoleStatus status;

  public Role() {
    // Default constructor
  }

  public Role(String name) {
    this.id = null;
    this.name = name;
    this.status = RoleStatus.ACTIVE;
  }

  public Role(Long id, String name, RoleStatus status) {
    this.id = id;
    this.name = name;
    this.status = status;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setStatus(RoleStatus status) {
    this.status = status;
  }

  public void addUser(User user) {
    this.users.add(user);
  }

  public void removeUser(User user) {
    this.users.remove(user);
  }
}

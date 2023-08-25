package com.example.prog4.repository.cnaps.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"cnaps_position\"")
@EqualsAndHashCode
@ToString
public class CnapsEmployeePosition {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private String id;
  private String name;
}

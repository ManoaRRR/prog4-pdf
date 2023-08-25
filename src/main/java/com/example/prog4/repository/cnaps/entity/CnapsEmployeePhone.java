package com.example.prog4.repository.cnaps.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "\"phone_cnaps\"")
@EqualsAndHashCode
@ToString
public class CnapsEmployeePhone {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private String id;
  private String value;
  @ManyToOne
  @JoinColumn(name = "employee_id", referencedColumnName = "id")
  @JsonIgnore
  private EmployeeCnaps employee;
}

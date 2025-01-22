package com.ademarporto.ba.repository.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class AddressEntity {
  private String streetName;
  private String houseNumber;
  private String postCode;
  private String city;
}

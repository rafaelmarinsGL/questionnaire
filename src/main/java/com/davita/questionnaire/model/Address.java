package com.davita.questionnaire.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ApiModel( value = "Address", description = "Address description")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "Autogenerated id", dataType = "Integer")
    private Integer id;
    @ApiModelProperty(notes = "Street name", dataType = "String")
    private String street;
    @ApiModelProperty(notes = "City name", dataType = "String")
    private String city;
    @ApiModelProperty(notes = "State name", dataType = "String")
    private String state;
    @ApiModelProperty(notes = "Zip code", dataType = "String")
    private String zip;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "address")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Person person;

}

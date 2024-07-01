package com.icwd.serviceregistry.userservicemwfdec.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Role extends BaseModel
{
    private String name;
}

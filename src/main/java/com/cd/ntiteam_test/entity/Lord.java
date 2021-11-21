package com.cd.ntiteam_test.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "lord")
public class Lord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotNull
    @Size(min = 2 , max = 255)
    @JsonProperty("name")
    private String name;

    @Column(name = "age", nullable = false)
    @NotNull
    @Min(0)
    @JsonProperty("age")
    private Integer age;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Planet> planet;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Planet> getPlanets() {
        return planet;
    }

    public void setPlanets(List<Planet> planets) {
        this.planet = planets;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

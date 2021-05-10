package io.welldev.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "should not be empty")
    private String description;

    @NotNull
    @JsonSerialize(using = ToStringSerializer.class)
    private final LocalDate creationDate;

    @NotNull
    @JsonSerialize(using = ToStringSerializer.class)
    private final LocalTime creationTime;


    public CustomerOrder() {
        this.creationDate = LocalDate.now();
        this.creationTime = LocalTime.now().withNano(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", creationTime=" + creationTime +
                '}';
    }
}

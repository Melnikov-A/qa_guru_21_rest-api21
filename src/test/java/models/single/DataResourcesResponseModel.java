package models.single;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DataResourcesResponseModel {

    private int id;
    private String name;
    private int year;
    private String color;

    @JsonProperty("pantone_value")
    private String pantoneValue;
}

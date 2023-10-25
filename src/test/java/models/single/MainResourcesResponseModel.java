package models.single;

import lombok.Data;

@Data
public class MainResourcesResponseModel {

    private DataResourcesResponseModel data;
    private SupportResourcesResponseModel support;

}

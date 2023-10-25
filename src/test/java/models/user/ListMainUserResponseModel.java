package models.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ListMainUserResponseModel {

    int page;

    @JsonProperty("per_page")
    int perPage;
    int total;

    @JsonProperty("total_pages")
    int totalPages;

    List<ListDataUserResponseModel> data;
    ListSupportResponseModel support;

}

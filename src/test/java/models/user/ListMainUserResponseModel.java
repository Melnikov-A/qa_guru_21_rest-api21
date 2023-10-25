package models.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ListMainUserResponseModel {

    private int page;

    @JsonProperty("per_page")
    private int perPage;
    private int total;

    @JsonProperty("total_pages")
    private int totalPages;

    List<ListDataUserResponseModel> data;
    ListSupportResponseModel support;

}

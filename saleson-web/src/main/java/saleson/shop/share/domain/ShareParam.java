package saleson.shop.share.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareParam {
    private String u; // url
    private String t; // title
    private String d; // description
    private String i; // image
}


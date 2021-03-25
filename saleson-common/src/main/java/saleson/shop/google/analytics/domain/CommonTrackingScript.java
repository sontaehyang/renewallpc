package saleson.shop.google.analytics.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonTrackingScript {

    private String src;
    private String run;

    public String getFullScript () {
        StringBuffer sb = new StringBuffer();

        sb.append("<script async src=\""+getSrc()+"\"></script>");

        sb.append("<script>");
        sb.append(getRun());
        sb.append("</script>");

        return sb.toString();
    }
}

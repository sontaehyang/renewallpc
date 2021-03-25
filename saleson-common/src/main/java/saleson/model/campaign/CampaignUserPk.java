package saleson.model.campaign;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class CampaignUserPk implements Serializable{

    private static final long serialVersionUID = 350837388L;

    @Column
    private Long userId;

    @Column
    private Long campaignId;

    public CampaignUserPk(Long userId, Long campaignId) {
        super();
        this.userId = userId;
        this.campaignId = campaignId;
    }
}

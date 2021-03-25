package saleson.shop.campaign;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CampaignMapper {

    void deleteCampaignBatch();

    void insertCampaignBatch();

}

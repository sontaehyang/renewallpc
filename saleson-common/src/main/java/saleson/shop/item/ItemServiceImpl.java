package saleson.shop.item;

import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.CommonUtils;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.apache.ibatis.binding.BindingException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.Const;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.*;
import saleson.model.review.ItemReviewFilter;
import saleson.model.review.ItemReviewLike;
import saleson.seller.main.SellerMapper;
import saleson.seller.main.domain.Seller;
import saleson.shop.brand.BrandService;
import saleson.shop.brand.domain.Brand;
import saleson.shop.cardbenefits.CardBenefitsService;
import saleson.shop.categories.CategoriesMapper;
import saleson.shop.categories.CategoriesService;
import saleson.shop.categories.domain.Breadcrumb;
import saleson.shop.categories.domain.BreadcrumbCategory;
import saleson.shop.config.ConfigService;
import saleson.shop.config.domain.Config;
import saleson.shop.coupon.CouponService;
import saleson.shop.coupon.domain.ChosenItem;
import saleson.shop.coupon.support.UserCouponParam;
import saleson.shop.deliverycompany.DeliveryCompanyService;
import saleson.shop.deliverycompany.domain.DeliveryCompany;
import saleson.shop.giftitem.GiftItemService;
import saleson.shop.item.domain.*;
import saleson.shop.item.support.*;
import saleson.shop.label.LabelService;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.point.PointMapper;
import saleson.shop.point.PointService;
import saleson.shop.point.domain.Point;
import saleson.shop.point.domain.PointConfig;
import saleson.shop.point.domain.PointPolicy;
import saleson.shop.point.support.OrderPointParam;
import saleson.shop.qna.QnaService;
import saleson.shop.qna.domain.Qna;
import saleson.shop.qna.support.QnaParam;
import saleson.shop.reviewfilter.ItemReviewFilterRepository;
import saleson.shop.shipment.ShipmentService;
import saleson.shop.shipment.domain.Shipment;
import saleson.shop.shipment.support.ShipmentParam;
import saleson.shop.shipmentreturn.ShipmentReturnService;
import saleson.shop.shipmentreturn.domain.ShipmentReturn;
import saleson.shop.shipmentreturn.support.ShipmentReturnParam;
import saleson.shop.user.UserMapper;
import saleson.shop.wishlist.domain.WishlistGroup;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service("itemService")
public abstract class ItemServiceImpl implements ItemService {
    private static final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SellerMapper sellerMapper;

    @Autowired
    private ItemMapperBatch itemMapperBatch;

    @Autowired
    private PointMapper pointMapper;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private FileService fileService;

    @Autowired
    private CategoriesMapper categoriesMapper;

    @Autowired
    private PointService pointService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private ShipmentReturnService shipmentReturnService;

    @Autowired
    private DeliveryCompanyService deliveryCompanyService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GiftItemService giftItemService;

    @Autowired
    private LabelService labelService;

	@Autowired
	Environment environment;

	@Autowired
    CardBenefitsService cardBenefitsService;

    @Autowired
    QnaService qnaService;

    @Autowired
    CouponService couponService;

    @Autowired
    ItemReviewLikeRepository itemReviewLikeRepository;

    @Autowired
    ItemReviewFilterRepository itemReviewFilterRepository;

    /**
     * ????????? ???????????? ?????? ?????? ??????
     */
    @Override
    public List<Item> getItemLogListById(ItemParam itemParam) {
        return itemMapper.getItemLogListById(itemParam);
    }

    /**
     * ????????? ???????????? ??? ?????? ??????
     */
    @Override
    public int getItemLogCountById(ItemParam itemParam) {
        return itemMapper.getItemLogCountById(itemParam);
    }

    @Override
    public void makeShoppingHowFile(String fileName) {

        ItemParam itemParam = new ItemParam();

        // ??????????????? CategoryId??? ?????? ???????????? ??????.
        if (itemParam.getOrderBy() != null && "ORDERING".equals(itemParam.getOrderBy())
            && (itemParam.getCategoryId() == null || "".equals(itemParam.getCategoryId()))) {
            itemParam.setOrderBy("");
            itemParam.setSort("DESC");
        }

        itemParam.setDataStatusCode("1");
        itemParam.setDisplayFlag("Y");
        itemParam.setConditionType("SHOPPING_HOW");
        List<Item> list = itemMapper.getItemList(itemParam);

        if (list == null) {
            return;
        }

        StringBuffer tsb = new StringBuffer();
        for(Item item : list) {
            if (item.getStockQuantity() != 0) {
                StringBuffer sb = new StringBuffer();
                this.shoppingHowTextTag(sb, "begin");
                this.shoppingHowTextTag(sb, "pid", item.getItemUserCode());
                this.shoppingHowTextTag(sb, "price", item.getPresentPrice());
                this.shoppingHowTextTag(sb, "pname", item.getItemName());

                String itemUrl = environment.getProperty("saleson.url.shoppingmall") + "/products/view/" + item.getItemUserCode();

                this.shoppingHowTextTag(sb, "pgurl", itemUrl);
                this.shoppingHowTextTag(sb, "igurl", environment.getProperty("saleson.url.shoppingmall") + item.getImageSrc());

                String imageUpdateDate = "";
                if (item.getItemImages() != null) {
                    for(ItemImage itemImage : item.getItemImages()) {
                        if (itemImage.getOrdering() == 1) {
                            imageUpdateDate = itemImage.getCreatedDate();
                            break;
                        }
                    }
                }

                this.shoppingHowTextTag(sb, "updateimg", imageUpdateDate);
                if (item.getItemCategories().isEmpty()) {
                    List<ItemCategory> categories = itemMapper.getItemCategoryListByItemId(item.getItemId());
                    item.setItemCategories(categories);
                }

                List<Breadcrumb> breadcrumbs = new ArrayList<>();
                if (!item.getItemCategories().isEmpty()) {
                    breadcrumbs = categoriesMapper.getBreadcrumbListByCollection(item.getItemCategories());
                }
                item.setBreadcrumbs(breadcrumbs);

                if (item.getBreadcrumbs().isEmpty()) {
                    continue;
                }

                Breadcrumb breadcrumb = item.getBreadcrumbs().get(0);
                if (breadcrumb.getBreadcrumbCategories() != null) {

                    String[] categoryElement = new String[]{"cate1", "cate2", "cate3", "cate4"};
                    int categoryIndex = 0;
                    String elementName = "";

                    elementName = categoryElement[categoryIndex];
                    this.shoppingHowTextTag(sb, elementName, breadcrumb.getTeamUrl());
                    categoryIndex++;

                    elementName = categoryElement[categoryIndex];
                    this.shoppingHowTextTag(sb, elementName, breadcrumb.getGroupUrl());
                    categoryIndex++;

                    for(BreadcrumbCategory category : breadcrumb.getBreadcrumbCategories()) {
                        if (categoryElement.length <= categoryIndex) {
                            break;
                        }

                        elementName = categoryElement[categoryIndex];
                        this.shoppingHowTextTag(sb, elementName, category.getCategoryUrl());

                        categoryIndex++;
                    }

                    String[] categoryNameElement = new String[]{"catename1", "catename2", "catename3", "catename4"};
                    int categoryNameIndex = 0;


                    elementName = categoryNameElement[categoryNameIndex];
                    this.shoppingHowTextTag(sb, elementName, breadcrumb.getTeamName());
                    categoryNameIndex++;

                    elementName = categoryNameElement[categoryNameIndex];
                    this.shoppingHowTextTag(sb, elementName, breadcrumb.getGroupName());
                    categoryNameIndex++;

                    for(BreadcrumbCategory category : breadcrumb.getBreadcrumbCategories()) {
                        if (categoryElement.length <= categoryNameIndex) {
                            break;
                        }

                        elementName = categoryNameElement[categoryNameIndex];
                        this.shoppingHowTextTag(sb, elementName, category.getCategoryName());

                        categoryNameIndex++;
                    }
                }

                this.shoppingHowTextTag(sb, "brand", item.getBrand());
                this.shoppingHowTextTag(sb, "maker"); // ?????????
                this.shoppingHowTextTag(sb, "pdate"); // ?????????

                this.shoppingHowTextTag(sb, "weight"); // ????????? - ????????????
                this.shoppingHowTextTag(sb, "sales"); // ?????? ?????????
                this.shoppingHowTextTag(sb, "coupon"); // ??????
                this.shoppingHowTextTag(sb, "pcard"); // ?????????/??????

                PointPolicy pointPolicy = pointService.getPointPolicyByItemId(item.getItemId());
                int point = 0;
                if (ValidationUtils.isNotNull(pointPolicy)) {

                    try {
                        point = ShopUtils.getEarnPoint(item.getPresentPrice(), pointPolicy);
                    } catch (Exception e) {
                        log.error("[Exception] ShopUtils.getEarnPoint(item.getPresentPrice(), pointPolicy) : {}", e.getMessage());
                        point = 0;
                    }

                }

                this.shoppingHowTextTag(sb, "point", point); // ?????????/?????????

                // [SKC??????]
				/*
				//DeliveryCharge deliveryCharge = deliveryMapper.getDeliveryChargeById(item.getDeliveryChargeId());
				// DeliveryCharge deliveryCharge = new DeliveryCharge();

				String deliveryType = "0";
				String deliveryPrice = "";
				if ("2".equals(deliveryCharge.getDeliveryChargeType())) {
					deliveryType = "1";
					deliveryPrice = StringUtils.numberFormat(deliveryCharge.getDeliveryCharge())+"???";
				} else if ("3".equals(deliveryCharge.getDeliveryChargeType())) {
					deliveryType = "2";
					deliveryPrice = StringUtils.numberFormat(deliveryCharge.getDeliveryFreeAmount())+"??? ??????" + StringUtils.numberFormat(deliveryCharge.getDeliveryCharge())+"???";
				}
				*/
                String deliveryType = "0";
                String deliveryPrice = "";

                this.shoppingHowTextTag(sb, "deliv", deliveryType);
                this.shoppingHowTextTag(sb, "deliv2", deliveryPrice);

                this.shoppingHowTextTag(sb, "review", item.getReviewCount());
                this.shoppingHowTextTag(sb, "event");
                this.shoppingHowTextTag(sb, "eventurl");
                this.shoppingHowTextTag(sb, "sellername", "????????????");
                this.shoppingHowTextTag(sb, "sellershop", environment.getProperty("saleson.url.shoppingmall"));
                this.shoppingHowTextTag(sb, "sellergrade");		// ????????? ??????

                this.shoppingHowTextTag(sb, "end");

                tsb.append(sb);
            }
        }

        BufferedWriter bufferedWriter = null;
        try {

            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
            bufferedWriter.write(tsb.toString());


        } catch (IOException e) {
            log.error("BufferedWriter Exception : {}", e.getMessage(), e);
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    log.error("BufferedWriter Exception : {}", e.getMessage(), e);
                }

            }
        }
    }

    private void shoppingHowTextTag(StringBuffer sb, String tagName) {
        this.shoppingHowTextTag(sb, tagName, "");
    }

    private void shoppingHowTextTag(StringBuffer sb, String tagName, int value) {
        this.shoppingHowTextTag(sb, tagName, Integer.toString(value));
    }

    private void shoppingHowTextTag(StringBuffer sb, String tagName, String value) {
        sb.append("<<<"+tagName+">>>"+value + "\n");
    }

    @Override
    public Item getItemBy(int itemId) {
        return itemMapper.getItemById(itemId);
    }


    @Override
    public Item getItemBy(String itemUserCode) {
        return itemMapper.getItemByItemUserCode(itemUserCode);
    }



    @Override
    public Item getItemById(int itemId) {

        Item item = itemMapper.getItemById(itemId);

        bindItemAdditionInfo(item, false);
        return item;
    }


    @Override
    public Item getItemByIdForManager(int itemId) {
        Item item = itemMapper.getItemById(itemId);
        bindItemAdditionInfo(item, true);

        // ?????? ???????????? (???????????? ????????? ?????????????????? ??? ????????????.)
        item.setItemOptionGroups(itemMapper.getItemOptionGroupListForManager(item.getItemId()));

        return item;
    }

    @Override
    public Item getItemByItemUserCodeForPreview(String itemUserCode) {
        Item item = itemMapper.getItemByItemUserCodeForPreview(itemUserCode);

        bindItemAdditionInfo(item, false);

        return item;
    }

    @Override
    public Item getItemByItemUserCode(String itemUserCode) {

        ItemParam itemParam = new ItemParam();
        if (UserUtils.isUserLogin()) {
            itemParam.setUserId(UserUtils.getUserId());
        }

        if (!UserUtils.isManagerLogin()) {
            // ???????????? ??????
            itemParam.setPrivateTypes(ItemUtils.getPrivateTypes());
        }

        itemParam.setItemUserCode(itemUserCode);

        /**
         * CJH 2016.08.02 ITEM ????????? ????????? ???????????? ???????????? ?????? ItemParam?????? ?????? ??????
         * Item item = itemMapper.getItemByItemUserCode(itemUserCode);
         */

        Item item = itemMapper.getItemByParam(itemParam);

        bindItemAdditionInfo(item, false);

        return item;
    }

    // ?????? ?????? ?????? ??????
    private void bindItemAdditionInfo(Item item, boolean isManager) {
        if (item == null) {
            throw new UserException(MessageUtils.getMessage("M00378"));	// ??????????????? ????????????.
        }

        // Breadcrumbs
        if (item.getItemCategories() != null) {
            List<Breadcrumb> breadcrumbs = categoriesMapper.getBreadcrumbListByCollection(item.getItemCategories());
            item.setBreadcrumbs(breadcrumbs);
        }

        // ?????? ?????? ??????
        item.setItemInfos(itemMapper.getItemInfoListByItemId(item.getItemId()));

        // ?????? ?????? ??????
        item.setItemInfoMobiles(itemMapper.getItemInfoMobileListByItemId(item.getItemId()));

        // ?????? ??????
        item.setItemOptions(getItemOptionList(item, isManager));

        // ?????? ?????? ?????????
        //item.setItemOptionImages(itemMapper.getItemOptionImageList(item.getItemId()));

        // ????????????
        // ????????? ?????? FRONT ??? ????????? ??????
        if (UserUtils.isManagerLogin() || UserUtils.isSellerLogin()) {
            item.setItemRelations(getItemRelationsByItemId(item.getRelationItemDisplayType(), item.getItemId()));
        }

        // ??????????????????
        item.setItemAdditions(getItemAdditionsByItemId(item.getItemId()));

        // ????????? ??????
        if ("Y".equals(item.getFreeGiftFlag())) {
            try {
                item.setFreeGiftItemList(giftItemService.getGiftItemListForFront(item.getItemId()));
            } catch (Exception e) {
                log.error("????????? ????????? ?????? :  {}", item.getItemUserCode());
            }
        }
    }

    @Override
    public int getItemCountByItemUserCode(String itemUserCode) {
        return itemMapper.getItemCountByItemUserCode(itemUserCode);
    }

    @Override
    public int getItemCount(ItemParam itemParam) {

        // categoryGroupId??? ?????? ?????? ?????? ?????? ??????.
        if (itemParam.getCategoryGroupId() > 0 && "".equals(itemParam.getCategoryClass())) {
            itemParam.setGroupCategoryClassCodes(categoriesMapper.getCategoryClassCodesByCategoryGroupId(itemParam.getCategoryGroupId()));
        }
        return itemMapper.getItemCount(itemParam);
    }

    @Override
    public List<Item> getItemList(ItemParam itemParam) {
        // categoryGroupId??? ?????? ?????? ?????? ?????? ??????.
        if (itemParam.getCategoryGroupId() > 0 && "".equals(itemParam.getCategoryClass())) {
            itemParam.setGroupCategoryClassCodes(categoriesMapper.getCategoryClassCodesByCategoryGroupId(itemParam.getCategoryGroupId()));
        }

        return itemMapper.getItemList(itemParam);
    }

    @Override
    public List<Item> getMainDisplayItemList(ItemParam itemParam) {

        int totalCount = itemMapper.getMainDisplayItemCountByParam(itemParam);

        if (itemParam.getItemsPerPage() == 10) {
            itemParam.setItemsPerPage(50);
        }

        Pagination pagination = Pagination.getInstance(totalCount, itemParam.getItemsPerPage());
        itemParam.setPagination(pagination);
        itemParam.setLanguage(CommonUtils.getLanguage());

        return itemMapper.getMainDisplayItemListByParam(itemParam);
    }

    @Override
    public List<Item> getMainDisplayItemListForMain(String templateId, String team, int limit) {

        ItemParam itemParam = new ItemParam();
        itemParam.setTemplateId(templateId);
        itemParam.setTeam(team);

        if (limit > 0) {
            itemParam.setLimit(limit);
        }

        return itemMapper.getMainDisplayItemListByParam(itemParam);
    }

    @Override
    public void insertItem(Item item) {
        int itemId = sequenceService.getId("OP_ITEM");
        String itemCode = "G2" + StringUtils.lPad(Integer.toString(itemId), 9, '0');

        item.setItemId(itemId);
        item.setItemCode(itemCode);

        if (ShopUtils.isMallInMall()) {
            item.setItemUserCode(itemCode);
        }

        Seller seller = sellerMapper.getSellerById(item.getSellerId());

        String itemApprovalType = seller.getItemApprovalType();



        if (ShopUtils.isOpmanagerPage() && UserUtils.isManagerLogin()) {
            item.setDataStatusCode("1");
            item.setDataStatusMessage(ShopUtils.getItemStatusMessage("1", "????????? ?????? (????????????)"));
            item.setProcessPage("manager");

        } else if (ShopUtils.isSellerPage() && SellerUtils.isSellerLogin()) {
            if("2".equals(itemApprovalType)){
                item.setDataStatusCode("1");
                item.setDataStatusMessage(ShopUtils.getItemStatusMessage("1", "????????? ?????? ?????? ??????. (????????????)"));
            }else{
                item.setDataStatusCode("20");
                item.setDataStatusMessage(ShopUtils.getItemStatusMessage("20", "????????? ?????? ?????? ??????. (????????????)"));
            }

            item.setProcessPage("seller");
        }

        item.setHits(0);
        item.setCreatedUserId(UserUtils.getManagerId());

        // ????????? ???????????? (??????????????? ????????? ???????????? ??????????????? ?????????. - ?????? 1???)
        if ("2".equals(item.getItemLabel()) && "Y".equalsIgnoreCase(item.getDisplayFlag())) {
            item.setOpentime(DateUtils.getToday(Const.DATETIME_FORMAT));
        }

        // ????????? ?????????
        item.setItemNewFlag("N");
        if ("2".equals(item.getItemLabel())) {
            item.setItemNewFlag("Y");
        }

        // ?????? ?????? ?????? ?????? ??????
        if (StringUtils.isEmpty(item.getItemReturnFlag())) {
            item.setItemReturnFlag("Y");
        }

        itemMapper.insertItem(item);

        item.setActionType("insert");
        saveItem(item);
    }

    @Override
    public void updateItem(Item item) {

        // ????????? ???????????? (??????????????? ????????? ???????????? ??????????????? ?????????. - ?????? 1???)
        if (ValidationUtils.isEmpty(item.getOpentime())
            && "2".equals(item.getItemLabel()) && "".equalsIgnoreCase(item.getDisplayFlag())) {
            item.setOpentime(DateUtils.getToday(Const.DATETIME_FORMAT));
        }

        // ????????? ?????????
        item.setItemNewFlag("N");
        if ("2".equals(item.getItemLabel())) {
            item.setItemNewFlag("Y");
        }

        Seller seller = sellerMapper.getSellerById(item.getSellerId());
        String itemApprovalType = seller.getItemApprovalType();

        if (ShopUtils.isOpmanagerPage() && UserUtils.isManagerLogin()) {
            item.setDataStatusMessage(item.getDataStatusMessage() + ShopUtils.getItemStatusMessage("1000", "????????? ??????"));
            item.setProcessPage("manager");
        } else if (ShopUtils.isSellerPage() && SellerUtils.isSellerLogin()) {
            // ?????????????????? ?????? ???????????? ????????? ??????????????? ????????? ??????(itemApprovalType)
            if ("2".equals(itemApprovalType)) {
                item.setDataStatusCode("1");
                item.setDataStatusMessage(ShopUtils.getItemStatusMessage("1", "????????? ?????? ?????? ??????. (????????????)"));
            } else{
                if (!"20".equals(item.getDataStatusCode())) {
                    item.setDataStatusCode("30");
                    item.setDataStatusMessage(item.getDataStatusMessage() + ShopUtils.getItemStatusMessage("30", "????????? ?????? ?????? ??????."));
                }
            }

            item.setProcessPage("seller");
        }

        item.setActionType("update");
        saveItem(item);
    }

    /**
     * ?????? ?????? ??? ?????? ????????? ??????.
     * ??????, ????????????, ???????????? ?????? ??????
     * @param item
     */
    private void saveItem(Item item) {

        // ????????? ??????.
        item.setCreatedManagerId(UserUtils.getManagerId());
        item.setCreatedSellerId(SellerUtils.getSellerId());

        // 0. ?????? - ?????????
        if ("2".equals(item.getShippingType())) {
            item.setShippingGroupCode("SELLER-" + item.getSellerId());
        } else if ("3".equals(item.getShippingType())) {
            item.setShippingGroupCode("SHIPMENT-" + item.getShipmentId());
        } else {
            item.setShippingGroupCode("");
        }

        // ?????????(??????) ???????????? itemUserCode??? ???????????? ?????? ????????? ??? ??????
        if (ShopUtils.isMallInMall()) {
            item.setItemUserCode(item.getItemCode());
        }

        // ?????? ?????? ?????? ?????? ??????
        if (StringUtils.isEmpty(item.getItemReturnFlag())) {
            item.setItemReturnFlag("Y");
        }

        // 1. ?????? ????????????
        if (item.getCategoryIds() != null) {
            // 1.1 ?????? ?????? ???????????? ??????
            itemMapper.deleteItemCategoryByItemId(item.getItemId());

            // 1.2 ?????? ???????????? ??????
            for (int i = 0; i < item.getCategoryIds().length; i++) {
                ItemCategory itemCategory = new ItemCategory();

                itemCategory.setItemCategoryId(sequenceService.getId("OP_ITEM_CATEGORY"));
                itemCategory.setItemId(item.getItemId());
                itemCategory.setCategoryId(item.getCategoryIds()[i]);
                itemCategory.setOrdering(i + 1);

                itemMapper.insertItemCategory(itemCategory);
            }
        }

		/*
		// 2.??????????????? (?????? ????????? ?????? ??????)
		if (item.getItemImageFile() != null) {
			if (item.getItemImageFile().getSize() > 0) {
				MultipartFile multipartFile = item.getItemImageFile();

				String[] ITEM_DEFAULT_IMAGE_SAVE_FOLDER = new String[] {"list"};	// , "catalog"
				String[] ITEM_DEFAULT_IMAGE_SAVE_SIZE = new String[] {"200x200"};	// , "250x250"


				String defaultFileName = item.getItemId() + "." + FileUtils.getExtension(multipartFile.getOriginalFilename());
				defaultFileName = item.getItemUserCode() + "." + FileUtils.getExtension(multipartFile.getOriginalFilename()); // 7esthe

				for (int i = 0; i < ITEM_DEFAULT_IMAGE_SAVE_FOLDER.length; i++) {
					String saveFolderName = ITEM_DEFAULT_IMAGE_SAVE_FOLDER[i];

					// 1. ????????? ????????????
					String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + "item" + File.separator + item.getItemUserCode() + File.separator + saveFolderName;
					fileService.makeUploadPath(uploadPath);


					// 2. ????????? ???????????? ??????
					try {
						FileUtils.delete(uploadPath, defaultFileName);
					} catch (IOException e1) {
						log.warn(e1.getMessage());
					}

					// 2-1. ????????? ?????????.
					defaultFileName = FileUtils.getNewFileName(uploadPath, defaultFileName);



					// 3. ????????? ??????
					File saveFile = new File(uploadPath + File.separator + defaultFileName);

					// 4. ????????? ?????????
					String[] thumbnailSize = StringUtils.delimitedListToStringArray(ITEM_DEFAULT_IMAGE_SAVE_SIZE[i], "x");


					// ??????.
					try {

						ThumbnailUtils.create(multipartFile, saveFile, Integer.parseInt(thumbnailSize[0]), Integer.parseInt(thumbnailSize[1]));
					} catch (IOException e) {
						log.error("????????? ?????? ?????? ", e);
					}


				}

				// ??????????????? ?????????
				item.setItemImage(defaultFileName);

			}

		}
		*/

        // 3.????????????????????? ????????????.
        boolean hasItemImage = false;
        if (item.getItemImageIds() != null) {
            int i = 1;
            for (int itemImageId : item.getItemImageIds()) {
                if (i == 1) {
                    // ??? ?????? ???????????? ????????? ??????
                    ItemImage firstItemImage = itemMapper.getItemImageById(itemImageId);
                    item.setItemImage(firstItemImage.getImageName());
                }
                ItemImage itemImage = new ItemImage(itemImageId, i);
                itemMapper.updateOrderingOfItemImage(itemImage);
                i++;
            }
            hasItemImage = true;
        }

        // 3.?????????????????????
        if (item.getItemDetailImageFiles() != null) {

            // ordering??????.
            int ordering = itemMapper.getMaxOrderingOfItemImageByItemId(item.getItemId());

            int uploadFileCount = 0;
            for (MultipartFile multipartFile : item.getItemDetailImageFiles()) {
                if (multipartFile.getSize() > 0) {

                    String newFileName = saveItemImage(multipartFile, item.getItemUserCode());

                    ItemImage itemImage = new ItemImage();

                    itemImage.setItemImageId(sequenceService.getId("OP_ITEM_IMAGE"));
                    itemImage.setItemId(item.getItemId());
                    itemImage.setImageName(newFileName);
                    itemImage.setOrdering(ordering);

                    itemMapper.insertItemImage(itemImage);

                    // ??? ?????? ???????????? ????????? ??????
                    if (!hasItemImage && uploadFileCount == 0) {
                        item.setItemImage(newFileName);
                    }
;
                    uploadFileCount++;
                    ordering++;
                }
            }
        }

        // 4. ????????? ??????
        if ("Y".equals(item.getSellerPointFlag())) {
            pointMapper.deletePointConfigByItemId(item.getItemId());

            if (item.getPointType() != null) {
                for (int i = 0; i < item.getPointType().length; i++) {
                    String periodType = ArrayUtils.get(item.getPointRepeatDay(), i);
                    if (periodType == null || "".equals(periodType.trim())) {
                        periodType = "1";
                    } else {
                        periodType = "2";
                    }

                    PointConfig pointConfig = new PointConfig();

                    pointConfig.setPointConfigId(sequenceService.getId("OP_POINT_CONFIG"));

                    pointConfig.setConfigType("2");
                    pointConfig.setPeriodType(periodType);
                    pointConfig.setPointType(ArrayUtils.get(item.getPointType(), i));
                    pointConfig.setPoint(item.getPoint()[i]);
                    pointConfig.setStartDate(ArrayUtils.get(item.getPointStartDate(), i));
                    pointConfig.setStartTime(ArrayUtils.get(item.getPointStartTime(), i));
                    pointConfig.setEndDate(ArrayUtils.get(item.getPointEndDate(), i));
                    pointConfig.setEndTime(ArrayUtils.get(item.getPointEndTime(),i));
                    pointConfig.setRepeatDay(ArrayUtils.get(item.getPointRepeatDay(), i));
                    pointConfig.setItemId(item.getItemId());
                    pointConfig.setStatusCode("1");
                    pointConfig.setCreatedUserId(UserUtils.getManagerId());

                    pointMapper.insertPointConfig(pointConfig);
                }
            }
        } else {
            pointMapper.deletePointConfigByItemId(item.getItemId());
        }

        // 5.????????????
		/*if ("N".equalsIgnoreCase(item.getItemOptionFlag())) {
			// ???????????? ???????????? ????????? ?????????..
			itemMapper.deleteItemOptionByItemId(item.getItemId());

		}*/
        // 5.1 ???????????? ??????
        itemMapper.deleteItemOptionByItemId(item.getItemId());

        if (item.getItemOptionFlag() != null
            && "Y".equals(item.getItemOptionFlag().toUpperCase())
            && item.getOptionName1() != null) {

            List<ItemOption> itemOptions = new ArrayList<>();

            // 5.2 ???????????? ??????
            for (int i = 0; i < item.getOptionName1().length; i++) {
                String optionType = ArrayUtils.get(item.getOptionType(), i);
                String optionName1 = ArrayUtils.get(item.getOptionName1(), i);
                String erpItemCode = ArrayUtils.get(item.getErpItemCode(), i);

                if (!StringUtils.isEmpty(optionType) && !StringUtils.isEmpty(optionName1.trim())) {
                    String optionId = ArrayUtils.get(item.getOptionId(), i);
                    int itemOptionId = "".equals(optionId) || "0".equals(optionId) ? sequenceService.getId("OP_ITEM_OPTION") : Integer.parseInt(optionId);

                    ItemOption itemOption = new ItemOption();
                    itemOption.setItemOptionId(itemOptionId);
                    itemOption.setItemId(item.getItemId());
                    itemOption.setOptionType(optionType);

                    if (StringUtils.isEmpty(ArrayUtils.get(item.getOptionDisplayType(), i))) {
                        itemOption.setOptionDisplayType("select");
                    } else {
                        itemOption.setOptionDisplayType(ArrayUtils.get(item.getOptionDisplayType(), i));
                    }

                    if (StringUtils.isEmpty(ArrayUtils.get(item.getOptionOrdering(), i))) {
                        itemOption.setOptionOrdering(0);
                    } else {
                        itemOption.setOptionOrdering(Integer.parseInt(ArrayUtils.get(item.getOptionOrdering(), i)));
                    }

                    // ??????????????? + ??????????????? ???????????? ????????? ??????, ?????? ?????? ??????????????? ?????? (?????? ?????? ???????????? ????????? ??????)
                    // ?????? ?????? - ????????????, ??????, ????????????, ????????????, ????????????
                    if ("C".equals(optionType) && !StringUtils.isEmpty(erpItemCode)) {
                        itemOption.setErpItemCode(erpItemCode);
                        itemOption.setOptionPrice(0);
                        itemOption.setOptionCostPrice(0);
                        itemOption.setOptionStockFlag("N");
                        itemOption.setOptionStockQuantity(-1);
                        itemOption.setOptionSoldOutFlag("N");
                    } else {
                        if (StringUtils.isEmpty(ArrayUtils.get(item.getOptionPrice(), i))) {
                            itemOption.setOptionPrice(0);
                        } else {
                            itemOption.setOptionPrice(Integer.parseInt(ArrayUtils.get(item.getOptionPrice(), i)));
                        }

                        if (StringUtils.isEmpty(ArrayUtils.get(item.getOptionCostPrice(), i))) {
                            itemOption.setOptionCostPrice(0);
                        } else {
                            itemOption.setOptionCostPrice(Integer.parseInt(ArrayUtils.get(item.getOptionCostPrice(), i)));
                        }

                        String optionStockFlag = ArrayUtils.get(item.getOptionStockFlag(), i);
                        itemOption.setOptionStockFlag(optionStockFlag);

                        if ("Y".equals(optionStockFlag)) {
                            itemOption.setOptionStockQuantity(Integer.parseInt(ArrayUtils.get(item.getOptionStockQuantity(), i)));
                        } else {
                            itemOption.setOptionStockQuantity(-1);
                        }

                        itemOption.setOptionSoldOutFlag(ArrayUtils.get(item.getOptionSoldOutFlag(), i));
                    }

                    itemOption.setOptionName1(optionName1);
                    itemOption.setOptionName2(ArrayUtils.get(item.getOptionName2(), i));
                    itemOption.setOptionName3(ArrayUtils.get(item.getOptionName3(), i));
                    itemOption.setOptionStockCode(ArrayUtils.get(item.getOptionStockCode(), i));

                    String optionQuantity = ArrayUtils.get(item.getOptionQuantity(), i);
                    if (StringUtils.isEmpty(optionQuantity) || Integer.parseInt(optionQuantity) < 1) {
                        itemOption.setOptionQuantity(1);
                    } else {
                        itemOption.setOptionQuantity(Integer.parseInt(optionQuantity));
                    }

                    itemOption.setOptionDisplayFlag(ArrayUtils.get(item.getOptionDisplayFlag(), i));

                    itemOption.setCreatedUserId(UserUtils.getManagerId());

                    // ????????????.
                    itemOption.setOptionPriceNonmember(0);
                    itemOption.setOptionHideFlag("N");
                    itemOption.setOptionStockScheduleDate("");
                    itemOption.setOptionStockScheduleText("");

                    itemMapper.insertItemOption(itemOption);
                }
            }
        }

        // 6. ??????????????????
        // 6.1. ?????????????????? ??????
        itemMapper.deleteItemAdditionByItemId(item.getItemId());

        if ("Y".equalsIgnoreCase(item.getItemAdditionFlag()) && item.getAdditionItemIds() != null) {
            // 6.2 ?????? ?????? ?????? ??????
            int ordering = 1;
            for (int additionItemId : item.getAdditionItemIds()) {
                ItemAddition itemAddition = new ItemAddition();

                itemAddition.setItemId(item.getItemId());
                itemAddition.setAdditionItemId(additionItemId);
                itemAddition.setOrdering(ordering);

                itemMapper.insertItemAddition(itemAddition);

                ordering++;
            }
        }

        // 7. ???????????? ??????
        // 7.1. ???????????? ??????
        itemMapper.deleteItemRelationByItemId(item.getItemId());

        if ("2".equals(item.getRelationItemDisplayType()) && item.getRelatedItemIds() != null) {
            // 7.2 ???????????? ??????
            int ordering = 1;
            for (int relatedItemId : item.getRelatedItemIds()) {
                int itemRelationId = sequenceService.getId("OP_ITEM_RELATION");

                ItemRelation itemRelation = new ItemRelation();

                itemRelation.setItemId(item.getItemId());
                itemRelation.setItemRelationId(itemRelationId);
                itemRelation.setRelatedItemId(relatedItemId);
                itemRelation.setOrdering(ordering);

                itemMapper.insertItemRelation(itemRelation);

                ordering++;
            }
        }

        // 8. ?????? ?????? ??????
        if (item.getItemInfoTitles() != null) {
            // 8.1 ?????? ?????? ?????? ??????
            itemMapperBatch.deleteItemInfoByItemId(item.getItemId());

            // 8.2 ???????????? ??????
            List<ItemInfo> itemInfos = new ArrayList<>();

            for (int i = 0; i < item.getItemInfoTitles().length; i++) {
                String title = ArrayUtils.get(item.getItemInfoTitles(), i);
                String description = ArrayUtils.get(item.getItemInfoDescriptions(), i);

                if ("".equals(title)) {
                    continue;
                }

                ItemInfo itemInfo = new ItemInfo();
                itemInfo.setItemId(item.getItemId());
                itemInfo.setItemInfoId(sequenceService.getId("OP_ITEM_INFO"));
                itemInfo.setInfoCode("ITEM_0001");
                itemInfo.setTitle(title);
                itemInfo.setDescription(description);

                itemInfos.add(itemInfo);
            }

            if (!itemInfos.isEmpty()) {
                //itemMapperBatch.insertItemInfoListForExcel(itemInfos);
                for (ItemInfo itemInfo : itemInfos) {
                    itemMapperBatch.insertItemInfo(itemInfo);
                }
            }
        }

        // 9. ????????? ??????
        try {

            giftItemService.deleteGiftItemRelation(item.getItemId());

            if ("Y".equals(item.getFreeGiftFlag())) {

                List<Long> freeGiftItemIds = ModelUtils.getIds(item.getFreeGiftItemIds());

                if (freeGiftItemIds != null && !freeGiftItemIds.isEmpty()) {
                    giftItemService.insertGiftItemRelation(item.getItemId(), freeGiftItemIds);
                }
            }
        } catch (Exception e) {
            log.error("????????? ?????? ????????? ?????? :  {}",item.getItemUserCode());
        }

        // 10. ???????????? ??????
        try {
            // ?????? ???????????? JSON ??????
            if (item.getItemLabelValues().length > 0) {
                item.setItemLabelValue(labelService.getJsonValue(item.getItemLabelValues()));
            }
        } catch (Exception e) {
            log.error("???????????? ?????? ????????? ?????? :  {}", item.getItemUserCode());
        }

        itemMapper.updateItem(item);

        // ??????????????? ????????????. (??????????????? ??? ???????????? ????????? ?????? ???????????? ???)
        itemMapper.updateItemImage(item.getItemId());

        if ("insert".equals(item.getActionType())) {
            itemMapper.insertItemLog(item);
        } else if ("update".equals(item.getActionType())) {
            itemMapper.insertItemLogForApproval(item);
        }
    }

    private String saveItemImage(MultipartFile multipartFile, String itemUserCode) {
        // ????????? ????????????
        String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + "item" + File.separator + itemUserCode;

        fileService.makeUploadPath(uploadPath);
        String newFileName = "";
        BufferedImage bufferedImage = null;

        // ???????????? ??????????????? ??????[2017-05-31]minae.yun
        try {
            String date = DateUtils.getToday(Const.DATETIME_FORMAT);
            for (String sizeName : ShopUtils.getThumbnailType()) {

                try {
                    bufferedImage = ImageIO.read(multipartFile.getInputStream());
                } catch (IOException e1) {
                    throw new UserException("????????? ???????????? ?????? - ERROR");
                }

                if (bufferedImage == null) {
                    throw new UserException("????????? ???????????? ?????? - ERROR");
                }

                // 2. ?????????
                String defaultFileName = date + sizeName + "." + FileUtils.getExtension(multipartFile.getOriginalFilename());		// 7esthe
                newFileName = FileUtils.getNewFileName(uploadPath, defaultFileName);

                // 3. ????????? ??????
                File saveFile = new File(uploadPath + File.separator + newFileName);

                int imageSize = ShopUtils.getImageSize(sizeName);
							/*switch (size) {
								case "XS" :  imageSize = ShopUtils.XS; break;
								case "S" :  imageSize = ShopUtils.S; break;
								case "M" :  imageSize = ShopUtils.M; break;
								case "L" :  imageSize = ShopUtils.L; break;
							}*/

                //????????? [2017-03-31 ??????] ???????????? ?????? ?????? ??? ?????? ?????? imageSize?????? ?????? true, imageSize+10?????? ????????? ????????? ?????? ??????

                if (ShopUtils.checkImageSize2(bufferedImage.getWidth(), bufferedImage.getHeight(), imageSize)) {
                    try {
                        bufferedImage = ShopUtils.getThumbnailImage(bufferedImage, imageSize);
                    } catch (Exception e) {
                        throw new UserException("????????? ????????? ?????? - ERROR");
                    }
                }

                ByteArrayOutputStream output = new ByteArrayOutputStream();
                output.flush();
                ImageIO.write(bufferedImage, FileUtils.getExtension(multipartFile.getOriginalFilename()), output);
                output.close();
                FileCopyUtils.copy(output.toByteArray(), saveFile);

            }
        } catch (IOException e) {
            log.debug("????????? ?????? ?????? :  {}", e.getMessage());
            throw new UserException("????????? ????????? ?????? - ERROR");
        }

        return newFileName;
    }

    @Override
    public void insertItemOption(ItemOption itemOption) {
        itemMapper.insertItemOption(itemOption);

    }

    @Override
    public void updateItemOrdering(ItemListParam itemListParam) {



        if (itemListParam.getId() != null && itemListParam.getCategoryId() > 0) {

            itemMapperBatch.deleteItemOrderingByCategoryId(itemListParam.getCategoryId());
            itemMapperBatch.insertItemOrderingByListParam(itemListParam);

			/*
			int i = 1;
			for (String itemId : itemListParam.getId()) {

				ItemOrdering itemOrdering = new ItemOrdering();

				itemOrdering.setItemOrderingId(sequenceService.getId("OP_ITEM_ORDERING"));
				itemOrdering.setItemId(Integer.parseInt(itemId));
				itemOrdering.setCategoryId(itemListParam.getCategoryId());
				itemOrdering.setOrdering(i);


				itemMapperBatch.insertItemOrdering(itemOrdering);
				i++;
			}
			*/
        }
    }

    @Override
    public void deleteListData(ItemListParam itemListParam) {

        if (itemListParam.getId() != null) {

            for (String itemId : itemListParam.getId()) {
                // DATA_STATUS_CODE = '2'??? ???????????? ??????.
                Item item = new Item();
                item.setItemId(Integer.parseInt(itemId));
                item.setDataStatusCode("2");
                item.setUpdatedUserId(UserUtils.getManagerId());

                itemMapper.deleteItem(item);
            }

        }
    }

    @Override
    public void updateItemApproval(Item item) {
        item.setUpdatedUserId(UserUtils.getManagerId());
        item.setDataStatusCode("1");
        item.setDataStatusMessage(ShopUtils.getItemStatusMessage("1", "????????? ????????????."));

        itemMapper.updateItemApprovalByListParam(item);

        // 2. ?????? ??????
        item.setCreatedManagerId(UserUtils.getManagerId());
        item.setCreatedSellerId(item.getSellerId());
        item.setProcessPage("manager");
        item.setActionType("approval");

        itemMapper.insertItemLogForApproval(item);
    }

    @Override
    public void updateListData(ItemListParam itemListParam) {

        if (itemListParam.getId() != null) {

            // ????????????
            if ("approval".equals(itemListParam.getProcessType())) {
                for (int i = 0; i < itemListParam.getId().length; i++) {
                    Item item = new Item();
                    item.setItemId(Integer.parseInt(ArrayUtils.get(itemListParam.getId(), i)));

                    this.updateItemApproval(item);
                }
            } else if ("reject".equals(itemListParam.getProcessType())) {	// ?????? ??????
                for (int i = 0; i < itemListParam.getId().length; i++) {
                    Item item = new Item();
                    item.setItemId(Integer.parseInt(ArrayUtils.get(itemListParam.getId(), i)));
                    item.setUpdatedUserId(UserUtils.getManagerId());
                    item.setDataStatusCode("21");
                    item.setDataStatusMessage(ShopUtils.getItemStatusMessage("21", itemListParam.getApprovalMessage()));

                    itemMapper.updateItemByListParam(item);

                    // 2. ?????? ??????
                    item.setCreatedManagerId(UserUtils.getManagerId());
                    item.setCreatedSellerId(item.getSellerId());
                    item.setProcessPage("manager");
                    item.setActionType("reject");

                    itemMapper.insertItemLogForApproval(item);
                }

            } else if ("update-item-simple".equals(itemListParam.getProcessType())) {	// ?????? ?????? ??????
                for (int i = 0; i < itemListParam.getId().length; i++) {
                    Item item = itemListParam.getItemSimple(i);

                    if (item != null) {
                        item.setUpdatedUserId(UserUtils.getManagerId());
                        itemMapper.updateItemForSimple(item);

                        // 2. ?????? ??????
                        item.setCreatedManagerId(UserUtils.getManagerId());
                        item.setCreatedSellerId(item.getSellerId());
                        item.setProcessPage("manager");
                        item.setActionType("update-item-simple");

                        itemMapper.insertItemLogForApproval(item);


                    }

                }

            } else {
                for (int i = 0; i < itemListParam.getId().length; i++) {


                    // DATA_STATUS_CODE = '2'??? ???????????? ??????.
                    Item item = new Item();
                    item.setItemId(Integer.parseInt(ArrayUtils.get(itemListParam.getId(), i)));
                    item.setUpdatedUserId(UserUtils.getManagerId());

                    item.setItemPrice(ArrayUtils.get(itemListParam.getItemPrice(), i));
                    item.setSalePrice(ShopUtils.emptyToNegativeNumber(ArrayUtils.get(itemListParam.getSalePrice(), i)));
                    //item.setSalePriceNonmember(ShopUtils.emptyToNegativeNumber(ArrayUtils.get(itemListParam.getSalePriceNonmember(), i)));

                    itemMapper.updateItemByListParam(item);
                }
            }



        }
    }

    @Override
    public void updateListDataByDisplay(ItemListParam itemListParam) {

        if (itemListParam.getId() != null) {
            for (int i = 0; i < itemListParam.getId().length; i++) {

                // DATA_STATUS_CODE = '2'??? ???????????? ??????.
                Item item = new Item();
                item.setItemId(Integer.parseInt(ArrayUtils.get(itemListParam.getId(), i)));
                item.setUpdatedUserId(UserUtils.getManagerId());
                item.setDisplayFlag(itemListParam.getDisplayFlag());

                itemMapper.updateItemDisplayByListParam(item);
            }
        }
    }

    @Override
    public void updateListDataByLabel(ItemListParam itemListParam) {

        if (itemListParam.getId() != null) {
            for (int i = 0; i < itemListParam.getId().length; i++) {

                // DATA_STATUS_CODE = '2'??? ???????????? ??????.
                Item item = new Item();
                item.setItemId(Integer.parseInt(ArrayUtils.get(itemListParam.getId(), i)));
                item.setUpdatedUserId(UserUtils.getManagerId());

                item.setSoldOut(itemListParam.getSoldOut());

                if ("90".equals(itemListParam.getSoldOut())) {
                    item.setDataStatusMessage(ShopUtils.getItemStatusMessage("90", (ShopUtils.isSellerPage() ? "?????????" : "?????????") + "?????? ?????? ??????"));
                }
                //item.setStockQuantity(itemListParam.getStockQuantity());

                itemMapper.updateItemLabelByListParam(item);
            }
        }
    }

    @Override
    public void insertItemCategoryByItemListParam(ItemListParam itemListParam) {
        if (itemListParam.getId() != null && itemListParam.getCategoryId() > 0) {
            itemMapper.deleteItemCategoryByListParam(itemListParam);
            itemMapper.insertItemCategoryByListParam(itemListParam);

        }

    }


    @Override
    public void deleteItemImageByItemId(int itemId) {
        Item item = itemMapper.getItemById(itemId);

        // 1. ????????? ?????? ??????
        if (item.getItemImage().indexOf("/") > -1) {
            FileUtils.delete(new File((FileUtils.getWebRootPath() + ShopUtils.unescapeHtml(item.getItemImage())).replaceAll("/",  File.separator)));
        } else {
            String detailsUploadBase = "/item/" + item.getItemUserCode();

            //????????? ???????????? ????????? ?????? [2017-06-01] minae.yun
            for (String size : ShopUtils.getThumbnailType()) {

                String imageName = item.getItemImage().substring(0, 14);
                String detailsImage = "";

                //??????????????? ??????()??? ?????? ??????
                if (item.getItemImage().contains("(") && item.getItemImage().contains(")")) {
                    int startIndex = item.getItemImage().lastIndexOf("(");
                    int endIndex = item.getItemImage().lastIndexOf(")");
                    String subName = item.getItemImage().substring(startIndex, endIndex+1);
                    detailsImage = detailsUploadBase + "/" + ShopUtils.unescapeHtml(imageName) + size + subName + "." + FileUtils.getExtension(item.getItemImage());
                } else {
                    detailsImage = detailsUploadBase + "/" + ShopUtils.unescapeHtml(imageName) + size + "." + FileUtils.getExtension(item.getItemImage());
                }

                FileUtils.delete(detailsImage);
            }
        }

        // 2. ?????? ????????? ?????? ????????????.
        itemMapper.updateItemImageOfItemByItemId(itemId);

        // 3. ????????? ?????? ?????? ??????.
        itemMapper.deleteItemImageByItemId(itemId);

    }


    @Override
    public void deleteItemImageById(int itemImageId) {
        ItemImage itemImage = itemMapper.getItemImageById(itemImageId);

        if (itemImage != null) {

            // 1. ????????? ?????? ??????.
            if (itemImage.getImageName().indexOf("/") > -1) {
                FileUtils.delete(new File((FileUtils.getWebRootPath() + ShopUtils.unescapeHtml(itemImage.getImageName())).replaceAll("/",  File.separator)));
            } else {
                String detailsUploadBase = "/item/" + itemImage.getItemUserCode();

                //????????? ???????????? ????????? ?????? [2017-06-01] minae.yun
                for (String size : ShopUtils.getThumbnailType()) {

                    String imageName = itemImage.getImageName().substring(0, 14);
                    String detailsImage = "";

                    //??????????????? ??????()??? ?????? ??????
                    if (itemImage.getImageName().contains("(") && itemImage.getImageName().contains(")")) {
                        int startIndex = itemImage.getImageName().lastIndexOf("(");
                        int endIndex = itemImage.getImageName().lastIndexOf(")");
                        String subName = itemImage.getImageName().substring(startIndex, endIndex+1);
                        detailsImage = detailsUploadBase + "/" + ShopUtils.unescapeHtml(imageName) + size + subName + "." + FileUtils.getExtension(itemImage.getImageName());
                    } else {
                        detailsImage = detailsUploadBase + "/" + ShopUtils.unescapeHtml(imageName) + size + "." + FileUtils.getExtension(itemImage.getImageName());
                    }

                    FileUtils.delete(detailsImage);
                }

				/*
				String detailsUploadBase = "/item/" + itemImage.getItemUserCode() + "/details";
				String detailsImage = detailsUploadBase + "/" + itemImage.getImageName();
				String detailsBigImage = detailsUploadBase + "/big/" + itemImage.getImageName();
				String detailsThumbImage = detailsUploadBase + "/thumb/" + itemImage.getImageName();

				FileUtils.delete(detailsImage);
				FileUtils.delete(detailsBigImage);
				FileUtils.delete(detailsThumbImage);
				*/

            }

            // 2. ????????? ?????? ?????? ??????.
            itemMapper.deleteItemImageById(itemImageId);

            // 3. ?????? ????????? ??????.
            List<ItemImage> itemImages = itemMapper.getItemImageListByItemId(itemImage.getItemId());

            Item item = new Item();
            item.setItemId(itemImage.getItemId());

            if (itemImages.isEmpty()) {
                item.setItemImage("");
            } else {
                item.setItemImage(itemImages.get(0).getImageName());
            }
            itemMapper.updateItemImageName(item);

        }

    }

    @Override
    public int insertItemReview(ItemReview itemReview) {
        // 1. ????????? sellerId ????????????..
        Item item = itemMapper.getItemById(itemReview.getItemId());

        int itemReviewId = sequenceService.getId("OP_ITEM_REVIEW");

        itemReview.setSubject(StringUtils.isEmpty(itemReview.getSubject()) ? "-" : itemReview.getSubject());
        itemReview.setItemReviewId(itemReviewId);
        itemReview.setRecommendFlag("N");
        itemReview.setUserId(UserUtils.getUserId());
        itemReview.setUserName(UserUtils.getUser().getUserName());
        itemReview.setSellerId(item.getSellerId());
        itemReview.setItem(item);

        // 2. ???????????? ?????????
        if (itemReview.getItemReviewImageFiles() != null && !itemReview.getItemReviewImageFiles().isEmpty()) {
            List<ItemReviewImage> itemReviewImages = new ArrayList<>();

            int index = 0;
            for (MultipartFile multipartFile : itemReview.getItemReviewImageFiles()) {
                if (multipartFile.getSize() > 0) {
                    String[] ITEM_DEFAULT_IMAGE_SAVE_PREFIX = new String[]{"", "thumb_"};
                    String[] ITEM_DEFAULT_IMAGE_SAVE_SIZE = new String[]{"500x-1", "150x-1"};

                    String fileExtension = FileUtils.getExtension(multipartFile.getOriginalFilename());
                    String defaultFileName = itemReview.getItemReviewId() + "." + fileExtension;

                    for (int i = 0; i < ITEM_DEFAULT_IMAGE_SAVE_PREFIX.length; i++) {
                        defaultFileName = ITEM_DEFAULT_IMAGE_SAVE_PREFIX[i] + defaultFileName;

                        // 1. ????????? ????????????
                        String uploadPath = SalesonProperty.getUploadSaveFolder() + itemReview.getImageUploadPath();
                        fileService.makeUploadPath(uploadPath);

                        // 2. ????????? ???????????? ??????
                        try {
                            FileUtils.delete(uploadPath, ShopUtils.unescapeHtml(defaultFileName));
                        } catch (IOException e1) {
                            log.warn(e1.getMessage());
                        }

                        // 2-1. ????????? ?????????.
                        defaultFileName = FileUtils.getNewFileName(uploadPath, defaultFileName);

                        // 3. ????????? ??????
                        File saveFile = new File(uploadPath + File.separator + defaultFileName);

                        // 4. ????????? ?????????
                        String[] thumbnailSize = StringUtils.delimitedListToStringArray(ITEM_DEFAULT_IMAGE_SAVE_SIZE[i], "x");

                        // 5. ????????? ??????
                        try {
                            ThumbnailUtils.create(multipartFile, saveFile, Integer.parseInt(thumbnailSize[0]), Integer.parseInt(thumbnailSize[1]));
                        } catch (IOException e) {
                            log.error("ThumbnailUtils.create(... Exception : {}", e.getMessage(), e);
                        }
                        //fileService.createThumbnail(saveFile, uploadPath, newFileName, thumbnailSize, "0");
                    }

                    ItemReviewImage image = new ItemReviewImage();
                    image.setReviewImage(defaultFileName.replace("thumb_", ""));
                    image.setOrdering(index);

                    itemReviewImages.add(image);
                    index++;
                }
            }

            itemReview.setItemReviewImages(itemReviewImages);
        }

        // 3. ?????? ?????? ?????? (1: ?????? ??????, 2: ????????? ?????? ??? ??????)
        Config config = configService.getShopConfig(Config.SHOP_CONFIG_ID);
        if ("1".equals(config.getReviewDisplayType())) {
            itemReview.setDisplayFlag("Y");
            itemReview.setLoginId(UserUtils.getLoginId());
            itemReview = earnItemReviewPoint(itemReview, config);
        }

        itemMapper.insertItemReview(itemReview);

        List<ItemReviewFilter> itemReviewFilters = itemReview.getItemReviewFilters();

        if (itemReviewFilters != null && !itemReviewFilters.isEmpty()) {
            itemReviewFilters.forEach(f->{
                f.setItemReviewId(itemReviewId);
            });

            itemReviewFilterRepository.saveAll(itemReviewFilters);
        }


        if (itemReview.getItemReviewImages() != null && !itemReview.getItemReviewImages().isEmpty()) {
            itemMapper.insertItemReviewImage(itemReview);
        }

        return itemReviewId;

    }


    @Override
    public List<ItemRelation> getItemRelationRandomList(ItemParam itemParam) {
        return itemMapper.getItemRelationRandomList(itemParam);
    }


    @Override
    public int getItemReviewCountByParam(ItemParam itemParam) {
        return itemMapper.getItemReviewCountByParam(itemParam);
    }

    @Override
    public List<ItemReview> getItemReviewListByParam(ItemParam itemParam) {

        List<ItemReview> list = itemMapper.getItemReviewListByParam(itemParam);

        if (list != null) {
            for (ItemReview review : list) {
                String starScore = "";
                for (int i = 0; i < review.getScore(); i++) {
                    starScore += "<span class='on'></span>";
                }

                review.setStarScore(starScore);
            }
        }
        return list;
    }


    @Override
    public List<ItemOther> getItemOtherList(int itemId) {
        return itemMapper.getItemOtherList(itemId);
    }


    @Override
    public ItemOption getItemOptionById(int itemOptionId) {
        return itemMapper.getItemOptionById(itemOptionId);
    }

    @Override
    public void updateItemStockQuantityByItemUserCodeNoTx(String stockCode, int quantity, String sign) {
        if ("1".equals(sign) || "2".equals(sign)) {
            ItemStockQuantityParam itemStockQuantityParam = new ItemStockQuantityParam();

            itemStockQuantityParam.setStockCode(stockCode);
            itemStockQuantityParam.setQuantity(quantity);
            itemStockQuantityParam.setSign(sign);

            itemMapper.updateItemStockQuantityByItemStockQuantityParam(itemStockQuantityParam);
        }
    }


    @Override
    public void updateItemOptionStockQuantityByOptionCodeNoTx(String stockCode, int quantity, String sign) {
        if ("1".equals(sign) || "2".equals(sign)) {
            ItemStockQuantityParam itemStockQuantityParam = new ItemStockQuantityParam();

            itemStockQuantityParam.setStockCode(stockCode);
            itemStockQuantityParam.setQuantity(quantity);
            itemStockQuantityParam.setSign(sign);

            itemMapper.updateItemOptionStockQuantityByItemStockQuantityParam(itemStockQuantityParam);
        }
    }


    @Override
    public void updateItemStockQuantityByItemStockQuantityParamNoTx(int itemId, int quantity, String sign) {
        if (itemId > 0) {
            if ("1".equals(sign) || "2".equals(sign)) {
                ItemStockQuantityParam itemStockQuantityParam = new ItemStockQuantityParam();

                List<Integer> keys = new ArrayList<>();
                keys.add(itemId);

                itemStockQuantityParam.setKeys(keys);
                itemStockQuantityParam.setQuantity(quantity);
                itemStockQuantityParam.setSign(sign);

                itemMapper.updateItemStockQuantityByItemStockQuantityParam(itemStockQuantityParam);
            }
        }
    }

    @Override
    public void updateItemOptionStockQuantityByItemStockQuantityParamNoTx(List<Integer> optionIds, int quantity, String sign) {
        if (!optionIds.isEmpty()) {
            if ("1".equals(sign) || "2".equals(sign)) {
                ItemStockQuantityParam itemStockQuantityParam = new ItemStockQuantityParam();

                itemStockQuantityParam.setKeys(optionIds);
                itemStockQuantityParam.setQuantity(quantity);
                itemStockQuantityParam.setSign(sign);

                itemMapper.updateItemOptionStockQuantityByItemStockQuantityParam(itemStockQuantityParam);
            }
        }
    }

    @Override
    public List<Item> getNewArrivalItemList(ItemParam itemParam) {
        return itemMapper.getNewArrivalItemList(itemParam);
    }


    @Override
    public List<Item> getNewArrivalItemListForMain(ItemParam itemParam) {
        return itemMapper.getNewArrivalItemListForMain(itemParam);
    }


    @Override
    public List<Item> getWishlistItemList(WishlistGroup wishlistGroup) {
        return itemMapper.getWishlistItemList(wishlistGroup);
    }



    @Override
    public int getSearchNewArrivalItemCount(ItemParam itemParam) {
        return itemMapper.getSearchNewArrivalItemCount(itemParam);
    }


    @Override
    public List<Item> getSearchNewArrivalItemList(ItemParam itemParam) {
        return itemMapper.getSearchNewArrivalItemList(itemParam);
    }

    @Override
    public ItemReview getItemReviewById(int itemReviewId) {
        return itemMapper.getItemReviewById(itemReviewId);
    }

    @Override
    public void updateItemReview(ItemReview itemReview) {

        Config config = configService.getShopConfig(Config.SHOP_CONFIG_ID);
        itemReview = earnItemReviewPoint(itemReview, config);

        if (StringUtils.isEmpty(itemReview.getContent())) {
            itemReview.setContent("");
        }

        itemMapper.updateItemReview(itemReview);
    }

    private ItemReview earnItemReviewPoint(ItemReview itemReview, Config config) {
        if ("N".equals(itemReview.getPointPayment()) && "Y".equals(itemReview.getDisplayFlag())) {

            User user  = userMapper.getUserByLoginId(itemReview.getLoginId());

            Point point = new Point();
            String message = "";

            int imageCount = itemReview.getItemReviewImages() != null && !itemReview.getItemReviewImages().isEmpty() ?
                    itemReview.getItemReviewImages().size() : itemMapper.getItemReviewImageCount(itemReview.getItemReviewId());

            if (imageCount == 0) {
                point.setPoint(config.getPointReview());                    // ???????????? ?????? ?????? ????????????
                message = "???" + itemReview.getItem().getItemName() + "?????? ?????? ???????????? " + MessageUtils.getMessage("M00246");
            } else {
                point.setPoint(config.getPhotoPointReview());				// ???????????? ?????? ?????? ????????????
                message = "???" + itemReview.getItem().getItemName() + "?????? ?????? ?????????????????? " + MessageUtils.getMessage("M00246");
            }

            point.setReason(message);

            if (user != null) {
                point.setUserId(user.getUserId());
                point.setPointType(PointUtils.DEFAULT_POINT_CODE);
                pointService.earnPoint("review", point);
                itemReview.setPointPayment("Y");
                itemReview.setPoint(point.getPoint());
            }
        }

        return itemReview;
    }

    @Override
    public void deleteItemReview(int itemReviewId) {
        itemMapper.deleteItemReview(itemReviewId);
    }

    @Override
    public void deleteItemReviewImage(ItemReview itemReview, ItemReviewImage itemReviewImage) {

        try {
            // ????????? unescapeHtml ??????
            FileUtils.delete(itemReview.getImageUploadPath(), ShopUtils.unescapeHtml(itemReviewImage.getReviewImage()));
        } catch (IOException e1) {
            log.warn(e1.getMessage());
        }

        itemMapper.deleteItemReviewImageById(itemReviewImage.getItemReviewImageId());
    }

    @Async
    @Override
    public Future<AsyncReport> uploadCsv(MultipartFile[] multipartFiles) {
        AsyncReport report = new AsyncReport("ITEM");
        try {
            Thread.sleep(30000);

            String message = "";
            for (MultipartFile multipartFile : multipartFiles) {
                if (multipartFile.getSize() > 0) {

                    message += multipartFile.getOriginalFilename() + " " + multipartFile.getSize() + "\n";
                }
            }

            report.setMessage(message);

            //            if (1 == 1) {
            //            	throw new RuntimeException("?????? ????????? ??????????????????.");
            //            }
        } catch (Exception e) {
            report.setMessage(e.getMessage());
            log.error(e.getMessage(), e);
        }



        return new AsyncResult<AsyncReport>(report);
    }

    @Override
    public List<ItemInfo> getItemInfoListForExcel(ItemParam itemParam) {
        return itemMapper.getItemInfoListForExcel(itemParam);
    }


    @Override
    public List<ItemInfo> getItemInfoMobileListForExcel(ItemParam itemParam) {
        return itemMapper.getItemInfoMobileListForExcel(itemParam);
    }

    @Override
    public List<ExcelItemCategory> getItemCategoryListForExcel(ItemParam itemParam) {
        return itemMapper.getItemCategoryListForExcel(itemParam);
    }

    @Override
    public List<ExcelItemAddition> getItemAdditionListForExcel(ItemParam itemParam) {
        return itemMapper.getItemAdditionListForExcel(itemParam);
    }

    @Override
    public List<ExcelItemRelation> getItemRelationListForExcel(ItemParam itemParam) {
        return itemMapper.getItemRelationListForExcel(itemParam);
    }

    @Override
    public List<ExcelItemPointConfig> getItemPointListForExcel(ItemParam itemParam) {
        return itemMapper.getItemPointListForExcel(itemParam);
    }

    @Override
    public List<Item> getItemKeywordListForExcel(ItemParam itemParam) {
        return itemMapper.getItemKeywordListForExcel(itemParam);
    }

    @Override
    public String insertExcelData(MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new UserException(MessageUtils.getMessage("M01532")); // ????????? ????????? ?????????.
        }

        String fileName = multipartFile.getOriginalFilename();
        String fileExtension = FileUtils.getExtension(fileName);

        // ????????? ??????
        if (!(fileExtension.equalsIgnoreCase("xlsx"))) {
            throw new UserException(MessageUtils.getMessage("M01533"));	// ?????? ??????(.xlsx)??? ???????????? ???????????????.
        }

        // ????????????
        String maxUploadFileSize = "20";
        Long maxUploadSize = Long.parseLong(maxUploadFileSize) * 1000 * 1000;

        if (multipartFile.getSize() > maxUploadSize) {
            throw new UserException("Maximum upload file Size : " + maxUploadFileSize + "MB");
        }

        // ?????? ??? ?????? : http://poi.apache.org/spreadsheet/quick-guide.html#CellContents ????????? ??? ????????? ?????? - skc
        XSSFWorkbook wb = null;

        String excelUploadReport = "";
        try {
            wb = new XSSFWorkbook(multipartFile.getInputStream());

            excelUploadReport += "<p class=\"upload_file\">" + multipartFile.getOriginalFilename() + "</p>\n";
            ArrayList<Item> oldItems = new ArrayList<>();

            // ?????? Main SHEET (??????, ??????)
            excelUploadReport += processItemMainExcelSheet(wb.getSheet("item_main"), false, oldItems);

            // ?????? Seo SHEET
            excelUploadReport += processItemSubExcelSheet(wb.getSheet("item_seo"), oldItems);

            // ?????? ???????????? (?????????) SHEET
            excelUploadReport += processItemInfoExcelSheet(wb.getSheet("item_table"), oldItems);

            // ?????? ???????????? (?????????) SHEET
            // excelUploadReport += processItemInfoMobileExcelSheet(wb.getSheet("item_table_mobile"));

            // ?????? ?????? SHEET
            excelUploadReport += processItemOptionExcelSheet(wb.getSheet("item_option"), oldItems);

            // ?????? ????????? SHEET
            excelUploadReport += processItemImageExcelSheet(wb.getSheet("item_image"), oldItems);

            // ?????? ???????????? SHEET
            excelUploadReport += processItemCategoryExcelSheet(wb.getSheet("item_category"), oldItems);

            // ?????? ???????????? ?????? SHEET
            excelUploadReport += processItemAdditionExcelSheet(wb.getSheet("item_addition"), oldItems);

            // ?????? ?????? ?????? SHEET
            excelUploadReport += processItemRelationExcelSheet(wb.getSheet("item_relation"), oldItems);

            // ?????? ??????????????? SHEET
            // excelUploadReport += processItemPointConfigExcelSheet(wb.getSheet("item_point"));

            // ?????? ?????? ?????? SHEET
            excelUploadReport += processItemCheckExcelSheet(wb.getSheet("item_check"));

            // ?????? ???????????? ????????? SHEET
            excelUploadReport += processItemKeywordExcelSheet(wb.getSheet("item_keyword"));


            // ?????? Main SHEET (?????????)
            // ?????? Main SHEET (?????? ?????? ?????? ????????? ????????? ??????. -  'd' ??? ?????? ?????? ??? ?????? ????????? ??????????????? ?????? ?????????.
            excelUploadReport += processItemMainExcelSheet(wb.getSheet("item_main"), true, oldItems);


            return excelUploadReport;

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new UserException(MessageUtils.getMessage("M01534") + "(" + e.getMessage() + ")"); // ?????? ?????? ?????? ??? ????????? ?????????????????????.
        } catch (Exception e) {
			log.error(e.getMessage(), e);
            throw new UserException(MessageUtils.getMessage("M01534") + "(" + e.getMessage() + ")"); // ?????? ?????? ?????? ??? ????????? ?????????????????????.
        }


    }

    /**
     * ?????? ?????? (ITEM_MAIN)
     * @param sheet
     * @param isDelete (true: ?????????, false: ??????, ??????) ?????????.
     * @return
     */
    @SuppressWarnings("static-access")
    private String processItemMainExcelSheet(XSSFSheet sheet, boolean isDelete, ArrayList<Item> oldItems) {
        String result = "";
        if (sheet == null) {
            return result;
        }

        StringBuffer executionLog = new StringBuffer();

        // ????????? ?????? INSER VALUE (), () ????????? ?????? ??????.
        // ??????, ?????? ??? ?????? Batch??? ??????
        List<Item> insertItems = new ArrayList<>();
        List<PointConfig> pointConfigures = new ArrayList<>();
        List<HashMap<String, String>> cellReferences = new ArrayList<>();

        int rowDataCount = 0;			// ????????? ??? (?????????, ?????? ??????)
        int rowErrorCount = 0;			// ?????? ???
        for (Row row : sheet) {
            int rowIndex = row.getRowNum() + 1;

            if (row.getRowNum() < 1) {
                continue;
            }

            // ?????? - ????????? ????????????
            if (row.getRowNum() == 1) {
                for (Cell cell : row) {
                    CellReference cellReference = new CellReference(cell);

                    HashMap<String, String> cellInfo = new HashMap<>();
                    cellInfo.put("title", ShopUtils.getString(cell).replace("(*)", ""));
                    cellInfo.put("colString", cellReference.convertNumToColString(cell.getColumnIndex()));

                    cellReferences.add(cellInfo);
                }
                continue;
            }


            // ?????? ????????? ??? ?????? ?????? ???????????? ????????? SKIP
            if (PoiUtils.isEmptyAllCell(row)) {
                continue;
            }


            String control = ShopUtils.getString(row.getCell(0)).toUpperCase();
            String itemUserCode = ShopUtils.getString(row.getCell(1));

            // ??????????????? ???????????? ?????? ??????
            if (!"N".equals(control) && StringUtils.isEmpty(itemUserCode)) {
                HashMap<String, String> cellReference = cellReferences.get(1);
                cellReference.put("rowIndex", Integer.toString(rowIndex));

                executionLog.append(PoiUtils.log(cellReference, "??????????????? ???????????? ???????????????."));

                rowErrorCount++;
                continue;
            }

            Item item = null;
            PointConfig pointConfig = new PointConfig();
            Shipment shipment = null;
            ShipmentReturn shipmentReturn = null;
            DeliveryCompany deliveryCompany = null;

            int shipping = 0;
            int shippingFreeAmount = 0;
            int shippingExtraCharge1 = 0;
            int shippingExtraCharge2 = 0;

            // ????????? vs ??????, ?????? ??????
            if (isDelete) {
                if ("N".equals(control) || "U".equals(control)) {
                    continue;
                }
            } else {
                if ("D".equals(control)) {
                    continue;
                }
            }

            pointConfig.setPointConfigId(sequenceService.getId("OP_POINT_CONFIG"));
            pointConfig.setConfigType("2");		// 2: ???????????????
            pointConfig.setStatusCode("1");
            pointConfig.setPeriodType("1");		// 1: ???????????? (??????)
            pointConfig.setStartDate("");
            pointConfig.setStartTime("");
            pointConfig.setEndDate("");
            pointConfig.setEndTime("");
            pointConfig.setRepeatDay("");		// ?????????
            pointConfig.setCreatedUserId(UserUtils.getUserId());

            // ??????????????? ?????? ?????? ?????? ??????
            if ("N".equals(control)) {				// N: ??????
                item = new Item();
                int itemId = sequenceService.getId("OP_ITEM");
                String itemCode = "G2" + StringUtils.lPad(Integer.toString(itemId), 9, '0');

                pointConfig.setItemId(itemId);

                item.setItemId(itemId);
                item.setItemUserCode(itemCode);
                item.setTempId(ShopUtils.getString(row.getCell(1)));
                item.setTempControl("N");

                oldItems.add(item);

                item.setItemId(itemId);
                item.setItemCode(itemCode);
                item.setItemUserCode(itemCode);
                item.setItemType("1");
                item.setItemType1("0");
                item.setItemType2("0");
                item.setItemType3("0");
                item.setItemType4("0");
                item.setItemType5("0");
                item.setPrivateType("000");
                item.setItemLabel("0");
                item.setNonmemberOrderType("1");
                item.setSellerDiscountType("1");
                item.setShippingGroupCode("1000");
                item.setDataStatusCode("1");
                item.setUpdatedUserId(UserUtils.getUserId());
                item.setCreatedUserId(UserUtils.getUserId());
                item.setItemOptionTitle1("?????????");
                item.setItemOptionTitle2("?????????");

            } else if ("U".equals(control)) {		// U: ??????
                // ???????????? ????????????
                int itemCount = itemMapper.getItemCountByItemUserCode(itemUserCode);

	    		/*
	    		if (itemCount > 0) {
	    			HashMap<String, String> cellReference = cellReferences.get(1);
	    			cellReference.put("rowIndex", Integer.toString(rowIndex));

	    			executionLog.append(PoiUtils.log(cellReference, "??????????????? ?????????????????????. (" + itemUserCode + ")"));

	    			rowErrorCount++;
	    			continue;
	    		}
	    		*/

                item = new Item();
                item.setItemUserCode(itemUserCode);
                item.setTempId(itemUserCode);
                item.setTempControl("U");

                oldItems.add(item);

                int itemId = itemMapper.getItemIdByItemUserCode(itemUserCode);

                item.setItemId(itemId);
                pointConfig.setItemId(itemId);
            } else if ("D".equals(control)) {		// D: ??????
                item = new Item();
                item.setItemUserCode(itemUserCode);
                item.setTempId(itemUserCode);
                item.setTempControl("D");
                item.setSellerId(UserUtils.getManagerId());

                oldItems.add(item);


                item.setTempId(itemUserCode);
                item.setTempControl("D");

                oldItems.add(item);

                itemMapperBatch.deleteItemByItemUserCode(item);

                rowDataCount++;
                continue;

            } else {
                HashMap<String, String> cellReference = cellReferences.get(0);
                cellReference.put("rowIndex", Integer.toString(rowIndex));
                executionLog.append(PoiUtils.log(cellReference, "Control value is empty. (use 'I', 'U', 'D')"));

                rowErrorCount++;
                continue;

            }


            // ??????, ????????? ??????
            int cellErrorCount = 0;
            for (Cell cell : row) {
                HashMap<String, String> cellReference = null;
                try {
                    cellReference = cellReferences.get(cell.getColumnIndex());
                } catch (Exception e) {
                    log.warn(" cellReferences.get(cell.getColumnIndex()) : {}", e.getMessage());
                }
                if (cellReference == null) {
                    continue;
                }
                cellReference.put("rowIndex", Integer.toString(rowIndex));

                switch (cell.getColumnIndex()) {

                    case 2: 	// ?????????
                        item.setItemName(ShopUtils.getString(cell));
                        break;

                    case 3: 	// ?????????
                        int cellValue = ShopUtils.getInt(cell);
                        if (ShopUtils.isSellerPage()) {
                            if(SellerUtils.getSellerId() != cellValue) {
                                executionLog.append(PoiUtils.log(cellReference, "????????? ???????????? ?????????????????????."));
                                cellErrorCount++;
                                break;
                            } else {
                                item.setSellerId(cellValue);
                            }
                        } else {
                            if(cellValue <= 0) {
                                item.setSellerId(90000000);
                            } else {
                                item.setSellerId(cellValue);
                            }
                        }
                        break;

                    case 4: 	// ?????????
                        item.setManufacturer(ShopUtils.getString(cell));
                        break;

                    case 5:		// ?????????
                        Brand brand = brandService.getBrandById(ShopUtils.getInt(cell));
                        if(brand != null) {
                            item.setBrandId(ShopUtils.getInt(cell));
                            item.setBrand(brand.getBrandName());
                        } else if (brand == null && ShopUtils.getInt(cell)  != 0) {
                            executionLog.append(PoiUtils.log(cellReference, "????????? ???????????? ?????????????????????."));
                            cellErrorCount++;
                            break;
                        }
                        break;

                    case 6: 	// ?????????
                        item.setOriginCountry(ShopUtils.getString(cell));
                        break;

                    case 7: 	// ??????
                        item.setWeight(ShopUtils.getString(cell));
                        break;

                    case 8: 	// ??????
                        item.setDisplayQuantity(ShopUtils.getString(cell));
                        break;

                    case 9: 	// ????????????
                        item.setTaxType("2".equals(ShopUtils.getString(cell)) ? "2" : "1");
                        break;

                    case 10: 	// ????????? ????????????
                        item.setFreeGiftFlag("Y".equalsIgnoreCase(ShopUtils.getString(cell)) ? "Y" : "N");
                        break;

                    case 11: 	// ????????? ??????
                        if ("Y".equals(item.getFreeGiftFlag().toUpperCase())) {
                            item.setFreeGiftName(ShopUtils.getString(cell));
                        }
                        break;

                    case 12: 	// ??????????????????
                        item.setItemSummary(ShopUtils.getString(cell));
                        break;

                    case 13: 	// ?????????
                        item.setItemKeyword(ShopUtils.getString(cell));
                        break;

                    case 14:	// ????????????
                        item.setSalePrice(ShopUtils.getInt(cell));
                        break;

                    case 15:	// ????????? ??????
                        item.setCommissionType("2".equals(ShopUtils.getString(cell)) ? "2" : "1");
                        break;

                    case 16:	// ????????????
                        if ("2".equals(item.getCommissionType())) {
                            item.setCommissionRate(ShopUtils.getInt(cell));
                        } else {
                            if(UserUtils.isManagerLogin()) {
                                Seller seller = sellerMapper.getSellerById(item.getSellerId());
                                item.setCommissionRate(seller.getCommissionRate());
                            } else {
                                item.setCommissionRate(SellerUtils.getSeller().getCommissionRate());
                            }
                        }
                        break;

                    case 17:	// ??????
                        item.setItemPrice(ShopUtils.getString(cell));
                        break;

                    case 18:	// ????????????
                        item.setStockFlag("Y".equalsIgnoreCase(ShopUtils.getString(cell)) ? "Y" : "N");
                        break;

                    case 19:	// ????????????
                        if (StringUtils.isEmpty(ShopUtils.getString(cell))) {
                            item.setStockFlag("N");
                        }

                        if ("Y".equals(item.getStockFlag().toUpperCase())) {
                            item.setStockQuantity(ShopUtils.getInt(cell));
                        } else {
                            item.setStockQuantity(-1);
                        }
                        break;

                    case 20:	// ????????????
                        item.setStockCode(ShopUtils.getString(cell));
                        break;

                    case 21:	// ????????????
                        item.setSoldOut("1".equals(ShopUtils.getString(cell)) ? "1" : "0");
                        break;

                    case 22:	// ?????? ?????? ??????
                        String orderMinQuantity = ShopUtils.getString(cell);
                        item.setOrderMinQuantity("".equals(orderMinQuantity) ? -1 : Integer.parseInt(orderMinQuantity));
                        break;

                    case 23:	// ?????? ?????? ??????
                        String orderMaxQuantity = ShopUtils.getString(cell);
                        item.setOrderMaxQuantity("".equals(orderMaxQuantity) ? -1 : Integer.parseInt(orderMaxQuantity));
                        break;

                    case 24:	// ?????? ?????? ?????? ??????
                        item.setCouponUseFlag("Y".equalsIgnoreCase(ShopUtils.getString(cell)) ? "Y" : "N");
                        break;

                    case 25:	// ????????????
                        item.setSellerDiscountFlag("Y".equalsIgnoreCase(ShopUtils.getString(cell)) ? "Y" : "N");
                        break;

                    case 26:	// ???????????? ??????
                        if ("Y".equals(item.getSellerDiscountFlag().toUpperCase())) {
                            item.setSellerDiscountAmount(ShopUtils.getInt(cell));
                        }
                        break;

                    case 27:	// ????????? ??????
                        item.setSellerPointFlag("Y".equalsIgnoreCase(ShopUtils.getString(cell)) ? "Y" : "N");
                        break;

                    case 28:	// ?????????
                        if ("Y".equals(item.getSellerPointFlag().toUpperCase())) {
                            pointConfig.setPoint(ShopUtils.getInt(cell));
                        }
                        break;

                    case 29:	// ????????????
                        pointConfig.setPointType("1".equals(ShopUtils.getString(cell)) ? "1" : "2");
                        break;

                    case 30: 	// ??????????????????
                        item.setDisplayFlag("Y".equalsIgnoreCase(ShopUtils.getString(cell)) ? "Y" : "N");
                        break;

                    case 31:	// ????????????
                        item.setDeliveryType("2".equals(ShopUtils.getString(cell)) ? "2" : "1");
                        break;

                    case 32:	// ?????????
                        deliveryCompany = deliveryCompanyService.getDeliveryCompanyById(ShopUtils.getInt(cell));
                        if (deliveryCompany != null) {
                            item.setDeliveryCompanyId(ShopUtils.getInt(cell));
                            item.setDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());
                        } else {
                            executionLog.append(PoiUtils.log(cellReference, "????????? ???????????? ?????????????????????."));
                            cellErrorCount++;
                            break;
                        }
                        break;

                    case 33:	// ????????? ??????
                        ShipmentParam shipmentParam = new ShipmentParam();
                        shipmentParam.setShipmentId(ShopUtils.getInt(cell));
                        shipmentParam.setSellerId("1".equals(item.getDeliveryType()) ? 90000000 : item.getSellerId());

                        shipment = shipmentService.getShipmentByParam(shipmentParam);

                        if (shipment != null) {
                            item.setShipmentId(ShopUtils.getInt(cell));
                            shipping = shipment.getShipping();
                            shippingFreeAmount = shipment.getShippingFreeAmount();
                            shippingExtraCharge1 = shipment.getShippingExtraCharge1();
                            shippingExtraCharge2 = shipment.getShippingExtraCharge2();
                        } else {
                            executionLog.append(PoiUtils.log(cellReference, "????????? ????????? ????????? ?????????????????????."));
                            cellErrorCount++;
                            break;
                        }
                        break;

                    case 34:	// ????????? ??????
                        item.setShippingType(ShopUtils.getString(cell));
                        break;

                    case 35:	// ?????????
                        if ("3".equals(item.getShippingType())) {
                            item.setShipping(shipping);
                        }
                        if ("4".equals(item.getShippingType()) || "5".equals(item.getShippingType()) || "6".equals(item.getShippingType())) {
                            item.setShipping(ShopUtils.getInt(cell));
                        }
                        break;

                    case 36:	// ????????? ?????? ????????????
                        if ("3".equals(item.getShippingType())) {
                            item.setShippingFreeAmount(shippingFreeAmount);
                        }
                        if ("4".equals(item.getShippingType())) {
                            item.setShippingFreeAmount(ShopUtils.getInt(cell));
                        }
                        break;

                    case 37:	// ????????? ?????? ????????????
                        if ("5".equals(item.getShippingType())) {
                            item.setShippingItemCount(ShopUtils.getInt(cell));
                        }
                        break;

                    case 38:	// ?????? ???????????????
                        if ("3".equals(item.getShippingType())) {
                            item.setShippingExtraCharge1(shippingExtraCharge1);
                        }
                        if (!"2".equals(item.getShippingType()) && !"3".equals(item.getShippingType())) {
                            item.setShippingExtraCharge1(ShopUtils.getInt(cell));
                        }
                        break;

                    case 39:	// ???????????? ???????????????
                        if ("3".equals(item.getShippingType())) {
                            item.setShippingExtraCharge2(shippingExtraCharge2);
                        }
                        if (!"2".equals(item.getShippingType()) && !"3".equals(item.getShippingType())) {
                            item.setShippingExtraCharge2(ShopUtils.getInt(cell));
                        }
                        break;

                    case 40:	// ????????? ???????????? ??????
                        item.setQuickDeliveryExtraChargeFlag("Y".equalsIgnoreCase(ShopUtils.getString(cell)) ? "Y" : "N");
                        break;

                    case 41:	// ??????/?????? ?????? ?????? ??????
                        item.setItemReturnFlag("N".equalsIgnoreCase(ShopUtils.getString(cell)) ? "N" : "Y");
                        break;

                    case 42:	// ??????/?????? ??????
                        item.setShipmentReturnType("2".equals(ShopUtils.getString(cell)) ? "2" : "1");
                        break;

                    case 43:	// ??????/?????? ??????
                        ShipmentReturnParam shipmentReturnParam = new ShipmentReturnParam();
                        shipmentReturnParam.setShipmentReturnId(ShopUtils.getInt(cell));
                        shipmentReturnParam.setSellerId("1".equals(item.getShipmentReturnType()) ? 90000000 : item.getSellerId());

                        shipmentReturn = shipmentReturnService.getShipmentReturnByParam(shipmentReturnParam);
                        if (shipmentReturn != null) {
                            item.setShipmentReturnId(ShopUtils.getInt(cell));
                        } else {
                            executionLog.append(PoiUtils.log(cellReference, "????????? ??????/?????? ????????? ?????????????????????."));
                            cellErrorCount++;
                            break;
                        }
                        break;

                    case 44:	// ??????/?????? ?????????
                        item.setShippingReturn(ShopUtils.getInt(cell));
                        break;

                    case 45:	// ?????? ?????? ?????? ????????????
                        item.setHeaderContentFlag("Y".equalsIgnoreCase(ShopUtils.getString(cell)) ? "Y" : "N");
                        break;

                    case 46:	// ?????? ?????? ?????? ????????????
                        item.setFooterContentFlag("Y".equalsIgnoreCase(ShopUtils.getString(cell)) ? "Y" : "N");
                        break;

                    case 47:	// ?????? ?????? ??????
                        item.setDetailContent(ShopUtils.getString(cell));
                        break;

                    case 48:	// ?????? ?????? ??????(?????????)
                        item.setDetailContentMobile(ShopUtils.getString(cell));
                        break;

                    case 49:	// ?????????
                        break;

                    case 50:	// ?????????
                        break;

                    default:
                        break;
                }

            } // cell

            // ?????? ?????? ????????? Cell??? ????????? ???????????? ????????? ?????? ?????? ???????????? ???????????? ??????.
            if (cellErrorCount > 0) {
                rowErrorCount++;
                continue;
            }


            // ?????? / ?????? ??????
            String message = "";			// dataStatusMessage
            String actionMessage = "";		// itemLog actionType

            // itemLog ?????? data setting
            if(!ShopUtils.isSellerPage()) {
                item.setCreatedManagerId(UserUtils.getManagerId());
                item.setCreatedSellerId(90000000);					// ????????? ????????? ????????????
                item.setProcessPage("manager");
            } else {
                item.setCreatedManagerId(SellerUtils.getSellerId());
                item.setCreatedSellerId(SellerUtils.getSellerId());
                item.setProcessPage("seller");
            }

            if ("N".equals(control)) {			// N: ??????
                actionMessage = "insert-by-excel";
                if(!ShopUtils.isSellerPage()) {								// ????????? ????????? ??? ???
                    item.setDataStatusCode("1");
                    message =  "????????? ??????????????? ?????? ??????.";
                    item.setCreatedUserId(UserUtils.getManagerId());
                } else {
                    Seller seller = sellerMapper.getSellerById(SellerUtils.getSellerId());

                    if ("1".equals(seller.getItemApprovalType())) {				// ?????????????????? ????????????????????? ????????? ????????? ???
                        item.setDataStatusCode("20");	//  ????????????
                        message =  "????????? ??????????????? ?????? ?????? ??????.";
                        actionMessage = "apply-reg-by-excel";
                    } else {													// ?????????????????? ????????????????????? ??????????????? ???
                        item.setDataStatusCode("1");
                        message =  "????????? ??????????????? ?????? ??????(????????????).";
                    }
                    item.setCreatedUserId(seller.getSellerId());
                }

                item.setDataStatusMessage(ShopUtils.getItemStatusMessage(item.getDataStatusCode(), message));
                insertItems.add(item);

                item.setPriceCriteria("2");
                item.setActionType(actionMessage);
                itemMapper.insertItemLog(item);

                pointConfigures.add(pointConfig);

            } else if ("U".equals(control)) {	// U: ??????
                Item itemOrgData = itemMapper.getItemByItemUserCodeForPreview(item.getItemUserCode());
                actionMessage =  "update-by-excel";
                if(!ShopUtils.isSellerPage()) {
                    message =  "????????? ??????????????? ?????? ??????.";
                    item.setUpdatedUserId(UserUtils.getManagerId());
                    item.setDataStatusCode(itemOrgData.getDataStatusCode());
                } else {
                    Seller seller = sellerMapper.getSellerById(SellerUtils.getSellerId());
                    if("1".equals(seller.getItemApprovalType())) {
                        item.setDataStatusCode("30");	// ???????????????
                        message =  "????????? ??????????????? ?????? ?????? ??????.";
                        actionMessage = "apply-mod-by-excel";
                    } else {
                        item.setDataStatusCode("1");
                        message =  "????????? ??????????????? ?????? ??????(????????????).";

                    }
                    item.setUpdatedUserId(seller.getSellerId());
                }
                item.setDataStatusMessage(itemOrgData.getDataStatusMessage() + ShopUtils.getItemStatusMessage(item.getDataStatusCode(), message));
                itemMapperBatch.updateItemForExcel(item);
                itemMapperBatch.deleteItemPointConfigByItemId(item.getItemId());

                item.setPriceCriteria("2");
                item.setActionType(actionMessage);
                itemMapper.insertItemLog(item);

                pointConfigures.add(pointConfig);
            }

            rowDataCount++;

        } // row


        // ?????? ?????? ??????
        if (!insertItems.isEmpty()) {
            for (int i=0; i<insertItems.size(); i++) {
                itemMapper.insertItem(insertItems.get(i));
            }
        }

        // ????????? ??????
        if (!pointConfigures.isEmpty()) {
            itemMapperBatch.insertItemPointConfigListForExcel(pointConfigures);
        }


        // ????????????
        result = "\n<p class=\"sheet\"><span>[" + sheet.getSheetName() + "]</span> Total:" + rowDataCount
            + ", Process:" + (rowDataCount - rowErrorCount)
            + ", Error:" + rowErrorCount + "</p>\n";

        if (rowErrorCount > 0) {
            result += "<p class=\"line\">----------------------------------------------------------------------------</p>\n";
            result += executionLog.toString();
            result += "\n";
        }
        return result;
    }

    /**
     * ?????? SUB
     * @param sheet
     * @return
     */
    @SuppressWarnings("static-access")
    private String processItemSubExcelSheet(XSSFSheet sheet, ArrayList<Item> oldItems) {
        String result = "";
        if (sheet == null) {
            return result;
        }

        StringBuffer executionLog = new StringBuffer();



        // ????????? ?????? INSER VALUE (), () ????????? ?????? ??????.
        // ??????, ?????? ??? ?????? Batch??? ??????
        List<HashMap<String, String>> cellReferences = new ArrayList<>();

        int rowDataCount = 0;			// ????????? ??? (?????????, ?????? ??????)
        int rowErrorCount = 0;			// ?????? ???
        for (Row row : sheet) {
            int rowIndex = row.getRowNum() + 1;

            if (row.getRowNum() < 1) {
                continue;
            }

            // ?????? - ????????? ????????????
            if (row.getRowNum() == 1) {
                for (Cell cell : row) {
                    CellReference cellReference = new CellReference(cell);

                    HashMap<String, String> cellInfo = new HashMap<>();
                    cellInfo.put("title", ShopUtils.getString(cell).replace("(*)", ""));
                    cellInfo.put("colString", cellReference.convertNumToColString(cell.getColumnIndex()));

                    cellReferences.add(cellInfo);
                }
                continue;
            }


            // ?????? ????????? ??? ?????? ?????? ???????????? ????????? SKIP
            if (PoiUtils.isEmptyAllCell(row)) {
                continue;
            }

            String itemUserCode = ShopUtils.getString(row.getCell(0));

            // ??????No.??? ???????????? ?????? ??????
            if (StringUtils.isEmpty(ShopUtils.getString(row.getCell(0)))) {
                HashMap<String, String> cellReference = cellReferences.get(1);
                cellReference.put("rowIndex", Integer.toString(rowIndex));

                executionLog.append(PoiUtils.log(cellReference, "??????No.??? ???????????? ???????????????."));

                rowErrorCount++;
                continue;
            }

            // ???????????? ?????? ??????
            try {
                int itemId = 0;
                String control = "N";

                for (int i=0; i<oldItems.size(); i++) {
                    if ("N".equals(oldItems.get(i).getTempControl()) && ShopUtils.getString(row.getCell(0)).equals(oldItems.get(i).getTempId())) {
                        itemId = oldItems.get(i).getItemId();
                        itemUserCode = oldItems.get(i).getItemUserCode();
                        control = "N";
                    } else if ("U".equals(oldItems.get(i).getTempControl()) && ShopUtils.getString(row.getCell(0)).equals(oldItems.get(i).getTempId())) {
                        control = "U";
                    }
                }

                if ("U".equals(control)) {
                    itemId = itemMapper.getItemIdByItemUserCode(ShopUtils.getString(row.getCell(0)));
                }

            } catch(BindingException e) {
                HashMap<String, String> cellReference = cellReferences.get(1);
                cellReference.put("rowIndex", Integer.toString(rowIndex));
                executionLog.append(PoiUtils.log(cellReference, "??????????????? ???????????? ????????????. (" + itemUserCode + ")"));

                rowErrorCount++;
                continue;
            }


            ExcelItemSub item = new ExcelItemSub();

            item.setItemUserCode(itemUserCode);



            // ??????, ????????? ??????
            int cellErrorCount = 0;
            for (Cell cell : row) {
                HashMap<String, String> cellReference = null;
                try {
                    cellReference = cellReferences.get(cell.getColumnIndex());
                } catch (Exception e) {
                    log.warn(" cellReferences.get(cell.getColumnIndex()) : {}", e.getMessage());
                }
                if (cellReference == null) {
                    continue;
                }
                cellReference.put("rowIndex", Integer.toString(rowIndex));

                switch (cell.getColumnIndex()) {

                    case 1: 	// ?????????
                        item.setItemName(ShopUtils.getString(cell));
                        break;

                    case 2:	// SEO > INDEX
                        item.getSeo().setIndexFlag("Y".equalsIgnoreCase(ShopUtils.getString(cell)) ? "N" : "Y");
                        break;

                    case 3:	// SEO > ???????????? ?????????
                        item.setSeoTitle(ShopUtils.getString(cell));
                        break;

                    case 4:	// SEO > Meta ?????????
                        item.setSeoKeywords(ShopUtils.getString(cell));
                        break;

                    case 5:	// SEO > Meta Description
                        item.setSeoDescription(ShopUtils.getString(cell));
                        break;

                    case 6:	// SEO > H1
                        item.setSeoHeaderContents1(ShopUtils.getString(cell));
                        break;

                    default:
                        break;
                }

            } // cell

            // ?????? ?????? ????????? Cell??? ????????? ???????????? ????????? ?????? ?????? ???????????? ???????????? ??????.
            if (cellErrorCount > 0) {
                rowErrorCount++;
                continue;
            }


            // ??????.
            itemMapperBatch.updateItemSubForExcel(item);


            rowDataCount++;

        } // row



        // ????????????
        result = "\n<p class=\"sheet\"><span>[" + sheet.getSheetName() + "]</span> Total:" + rowDataCount
            + ", Process:" + (rowDataCount - rowErrorCount)
            + ", Error:" + rowErrorCount + "</p>\n";

        if (rowErrorCount > 0) {
            result += "<p class=\"line\">----------------------------------------------------------------------------</p>\n";
            result += executionLog.toString();
            result += "\n";
        }

        return result;

    }


    /**
     * ?????? ????????????(?????????) ?????? ?????? ?????? (ITEM_TABLE)
     * @param sheet
     */
    @SuppressWarnings("static-access")
    private String processItemInfoExcelSheet(XSSFSheet sheet, ArrayList<Item> oldItems) {
        String result = "";
        if (sheet == null) {
            return result;
        }

        StringBuffer executionLog = new StringBuffer();

        // ????????? ?????? INSER VALUE (), () ????????? ?????? ??????.

        List<HashMap<String, String>> cellReferences = new ArrayList<>();

        int rowDataCount = 0;			// ????????? ??? (?????????, ?????? ??????)
        int rowErrorCount = 0;			// ?????? ???

        for (Row row : sheet) {

            int rowIndex = row.getRowNum() + 1;

            if (row.getRowNum() < 1) {
                continue;
            }

            // ?????? - ????????? ????????????
            if (row.getRowNum() == 1) {
                for (Cell cell : row) {
                    CellReference cellReference = new CellReference(cell);

                    HashMap<String, String> cellInfo = new HashMap<>();
                    cellInfo.put("title", ShopUtils.getString(cell).replace("(*)", ""));
                    cellInfo.put("colString", cellReference.convertNumToColString(cell.getColumnIndex()));

                    cellReferences.add(cellInfo);
                }
                continue;
            }


            // ?????? ????????? ??? ?????? ?????? ???????????? ????????? SKIP
            if (PoiUtils.isEmptyAllCell(row)) {
                continue;
            }

            // ??????, ????????? ??????
            int cellErrorCount = 0;
            boolean isDelete = false;		// ?????? ?????? ??? ????????? ???????????????.. ???????????? ?????? ??????.
            String control = "D";

            Item item = new Item();
            ItemInfo itemInfo = new ItemInfo();
            List<ItemInfo> itemInfos = new ArrayList<>();

            for (Cell cell : row) {
                HashMap<String, String> cellReference = null;
                try {
                    cellReference = cellReferences.get(cell.getColumnIndex());
                } catch (Exception e) {
                    log.warn(" cellReferences.get(cell.getColumnIndex()) : {}", e.getMessage());
                }
                if (cellReference == null) {
                    continue;
                }
                cellReference.put("rowIndex", Integer.toString(rowIndex));


                switch (cell.getColumnIndex()) {
                    case 0: 	// ????????????

                        String itemUserCode = ShopUtils.getString(cell);

                        // ??????????????? ???????????? ?????? ??????
                        if (StringUtils.isEmpty(itemUserCode)) {
                            executionLog.append(PoiUtils.log(cellReference, "Product code is empty!"));

                            cellErrorCount++;
                            continue;
                        } else {

                            // ???????????? ????????????
                            try {

                                int itemId = 0;

                                for (int i=0; i<oldItems.size(); i++) {
                                    if ("N".equals(oldItems.get(i).getTempControl()) && ShopUtils.getString(row.getCell(0)).equals(oldItems.get(i).getTempId())) {
                                        itemId = oldItems.get(i).getItemId();
                                        itemUserCode = oldItems.get(i).getItemUserCode();
                                        control = "N";
                                    } else if ("U".equals(oldItems.get(i).getTempControl()) && ShopUtils.getString(row.getCell(0)).equals(oldItems.get(i).getTempId())) {
                                        control = "U";
                                    }
                                }

                                if ("U".equals(control)) {
                                    itemId = itemMapper.getItemIdByItemUserCode(itemUserCode);
                                }

                                item.setItemId(itemId);

                            } catch(BindingException e) {
                                executionLog.append(PoiUtils.log(cellReference, "Product is not exist. (" + itemUserCode + ")"));
                                cellErrorCount++;
                                continue;
                            }


                        }

                        break;

                    case 1: 	// ?????????
                        break;

                    case 2:		// ????????? ?????????
                        item.setItemNoticeCode(ShopUtils.getString(cell));

                        if ("N".equals(control)) {
                            List<ItemNotice> itemNoticeList = this.getItemNoticeListByCode(item.getItemNoticeCode());
                            itemInfo = new ItemInfo();

                            for (int i=0; i<itemNoticeList.size(); i++) {
                                itemInfo.setItemId(item.getItemId());
                                itemInfo.setItemInfoId(sequenceService.getId("OP_ITEM_INFO"));
                                itemInfo.setTitle(itemNoticeList.get(i).getNoticeTitle());
                                itemInfo.setDescription("???????????? ????????????");
                                itemInfo.setInfoCode("IMSI0001");

                                itemMapperBatch.insertItemInfo(itemInfo);
                            }
                        }
                        break;

                    default:
                        if ("U".equals(control)) {
                            if (cell.getColumnIndex() >= 3 && cell.getColumnIndex() <= 42) {
                                if (cell.getColumnIndex() % 2 == 1) {
                                    itemInfo = null;
                                    if (!"".equals(ShopUtils.getString(cell))) {
                                        itemInfo = new ItemInfo();
                                        itemInfo.setTitle(ShopUtils.getString(cell));
                                    }
                                }

                                if (cell.getColumnIndex() % 2 == 0) {
                                    if (itemInfo != null) {
                                        itemInfo.setItemId(item.getItemId());
                                        itemInfo.setItemInfoId(sequenceService.getId("OP_ITEM_INFO"));
                                        itemInfo.setInfoCode("IMSI0001");
                                        itemInfo.setDescription(ShopUtils.getString(cell));

                                        itemInfos.add(itemInfo);
                                    }
                                }
                            }
                        }

                        break;
                }

            } // cell

            // ?????? ?????? ????????? Cell??? ????????? ???????????? ????????? ?????? ?????? ???????????? ???????????? ??????.
            if (cellErrorCount > 0) {
                rowErrorCount++;
                continue;
            }

            if (!itemInfos.isEmpty()) {
                // ?????? ????????? ????????????(?????????) ?????? ??????.
                itemMapperBatch.deleteItemInfoByItemId(item.getItemId());
                itemMapperBatch.insertItemInfoListForExcel(itemInfos);
            }

            itemMapperBatch.updateItemContentByItemIdForExcel(item);
            rowDataCount++;

        } // row

        // ????????????
        result = "\n<p class=\"sheet\"><span>[" + sheet.getSheetName() + "]</span> Total:" + rowDataCount
            + ", Process:" + (rowDataCount - rowErrorCount)
            + ", Error:" + rowErrorCount + "</p>\n";

        if (rowErrorCount > 0) {
            result += "<p class=\"line\">----------------------------------------------------------------------------</p>\n";
            result += executionLog.toString();
            result += "\n";
        }
        return result;
    }



    /**
     * ?????? ????????????(?????????) ?????? ?????? ?????? (ITEM_INFO)
     * @param sheet
     */
    @SuppressWarnings("static-access")
    private String processItemInfoMobileExcelSheet(XSSFSheet sheet) {
        String result = "";
        if (sheet == null) {
            return result;
        }

        StringBuffer executionLog = new StringBuffer();

        // ????????? ?????? INSER VALUE (), () ????????? ?????? ??????.

        List<HashMap<String, String>> cellReferences = new ArrayList<>();

        int rowDataCount = 0;			// ????????? ??? (?????????, ?????? ??????)
        int rowErrorCount = 0;			// ?????? ???


        int oldItemId = 0;

        for (Row row : sheet) {
            int rowIndex = row.getRowNum() + 1;

            if (row.getRowNum() < 1) {
                continue;
            }

            // ?????? - ????????? ????????????
            if (row.getRowNum() == 1) {
                for (Cell cell : row) {
                    CellReference cellReference = new CellReference(cell);

                    HashMap<String, String> cellInfo = new HashMap<>();
                    cellInfo.put("title", ShopUtils.getString(cell).replace("(*)", ""));
                    cellInfo.put("colString", cellReference.convertNumToColString(cell.getColumnIndex()));

                    cellReferences.add(cellInfo);
                }
                continue;
            }


            // ?????? ????????? ??? ?????? ?????? ???????????? ????????? SKIP
            if (PoiUtils.isEmptyAllCell(row)) {
                continue;
            }


            // ??????, ????????? ??????
            int cellErrorCount = 0;


            Item item = new Item();
            ItemInfo itemInfo = new ItemInfo();
            List<ItemInfo> itemInfos = new ArrayList<>();


            for (Cell cell : row) {
                HashMap<String, String> cellReference = null;
                try {
                    cellReference = cellReferences.get(cell.getColumnIndex());
                } catch (Exception e) {
                    log.warn(" cellReferences.get(cell.getColumnIndex()) : {}", e.getMessage());
                }
                if (cellReference == null) {
                    continue;
                }
                cellReference.put("rowIndex", Integer.toString(rowIndex));


                switch (cell.getColumnIndex()) {
                    case 0: 	// ????????????

                        String itemUserCode = ShopUtils.getString(cell);

                        // ??????????????? ???????????? ?????? ??????
                        if (itemUserCode == null || "".equals(itemUserCode)) {
                            if (oldItemId == 0) {
                                executionLog.append(PoiUtils.log(cellReference, "Product code is empty!"));

                                cellErrorCount++;
                                continue;
                            }
                        } else {

                            // ???????????? ????????????
                            int itemId = 0;

                            try {
                                itemId = itemMapper.getItemIdByItemUserCode(itemUserCode);
                                item.setItemId(itemId);

                            } catch(BindingException e) {
                                executionLog.append(PoiUtils.log(cellReference, "Product is not exist. (" + itemUserCode + ")"));
                                cellErrorCount++;
                                continue;
                            }


                        }

                        break;

                    case 1: 	// ?????????
                        break;

                    case 2:	// ?????????????????? (?????????)
                        item.setDetailContentMobile(ShopUtils.getString(cell));
                        break;

                    default:
						/*
						if (cell.getColumnIndex() >= 4 && cell.getColumnIndex() <= 43) {
							if (cell.getColumnIndex() % 2 == 0) {
								itemInfo = new ItemInfo();
								if (!"".equals(ShopUtils.getString()(cell))) {
					    			itemInfo.setTitle(ShopUtils.getString()(cell));
								}
							}

							if (cell.getColumnIndex() % 2 == 1) {
								if (!"".equals(itemInfo.getTitle())) {
									itemInfo.setItemId(item.getItemId());
									itemInfo.setItemInfoId(sequenceService.getId("OP_ITEM_INFO_MOBILE"));
					    			itemInfo.setInfoCode("IMSI0002");
					    			itemInfo.setDescription(ShopUtils.getString()(cell));

					    			itemInfos.add(itemInfo);
								}
							}
						}
						*/
                        break;
                }





            } // cell

            // ?????? ?????? ????????? Cell??? ????????? ???????????? ????????? ?????? ?????? ???????????? ???????????? ??????.
            if (cellErrorCount > 0) {
                rowErrorCount++;
                continue;
            }

            // ?????? ????????? ?????? ??????
            if (!itemInfos.isEmpty()) {

                // ?????? ????????? ????????????(?????????) ?????? ??????.
                itemMapperBatch.deleteItemInfoMobileByItemId(item.getItemId());

                itemMapperBatch.insertItemInfoMobileListForExcel(itemInfos);
            }




            itemMapperBatch.updateItemContentMobileByItemIdForExcel(item);
            rowDataCount++;

        } // row




        // ????????????
        result = "\n<p class=\"sheet\"><span>[" + sheet.getSheetName() + "]</span> Total:" + rowDataCount
            + ", Process:" + (rowDataCount - rowErrorCount)
            + ", Error:" + rowErrorCount + "</p>\n";

        if (rowErrorCount > 0) {
            result += "<p class=\"line\">----------------------------------------------------------------------------</p>\n";
            result += executionLog.toString();
            result += "\n";
        }
        return result;
    }


    /**
     * ?????? ?????? ?????? ?????? ?????? (ITEM_OPTION)
     * @param sheet
     */
    @SuppressWarnings("static-access")
    private String processItemOptionExcelSheet(XSSFSheet sheet, ArrayList<Item> oldItems) {
        String result = "";
        if (sheet == null) {
            return result;
        }

        StringBuffer executionLog = new StringBuffer();

        // ????????? ?????? INSER VALUE (), () ????????? ?????? ??????.
        // ??????, ?????? ??? ?????? Batch??? ??????
        List<ItemOption> itemOptions = new ArrayList<>();
        List<HashMap<String, String>> cellReferences = new ArrayList<>();

        int rowDataCount = 0;			// ????????? ??? (?????????, ?????? ??????)
        int rowErrorCount = 0;			// ?????? ???

        List<Integer> deleteItemIds = new ArrayList<>();

        // String oldOptionName1 = "";
        String oldOptionDisplayType = "";
        String oldOptionHideFlag = "";

        for (Row row : sheet) {
            int rowIndex = row.getRowNum() + 1;

            if (row.getRowNum() < 1) {
                continue;
            }

            // ?????? - ????????? ????????????
            if (row.getRowNum() == 1) {
                for (Cell cell : row) {
                    CellReference cellReference = new CellReference(cell);

                    HashMap<String, String> cellInfo = new HashMap<>();
                    cellInfo.put("title", ShopUtils.getString(cell).replace("(*)", ""));
                    cellInfo.put("colString", cellReference.convertNumToColString(cell.getColumnIndex()));

                    cellReferences.add(cellInfo);
                }
                continue;
            }


            // ?????? ????????? ??? ?????? ?????? ???????????? ????????? SKIP
            if (PoiUtils.isEmptyAllCell(row)) {
                continue;
            }


            // ??????, ????????? ??????
            int cellErrorCount = 0;
            boolean optionDeleteFlag = false;		// ?????? ?????? ??? ????????? ???????????????.. ???????????? ?????? ??????.

            String itemUserCode = ShopUtils.getString(row.getCell(0));

            ItemOption itemOption = new ItemOption();
            itemOption.setOptionDisplayType("select");
            itemOption.setOptionHideFlag("N");
            itemOption.setCreatedUserId(UserUtils.getUserId());

            for (Cell cell : row) {
                HashMap<String, String> cellReference = null;
                try {
                    cellReference = cellReferences.get(cell.getColumnIndex());
                } catch (Exception e) {
                    log.warn(" cellReferences.get(cell.getColumnIndex()) : {}", e.getMessage());
                }
                if (cellReference == null) {
                    continue;
                }
                cellReference.put("rowIndex", Integer.toString(rowIndex));

                switch (cell.getColumnIndex()) {
                    case 0: 	// ????????????

                        // ??????????????? ???????????? ?????? ??????
                        if (StringUtils.isEmpty(itemUserCode)) {
                            executionLog.append(PoiUtils.log(cellReference, "Product code is empty!"));

                            cellErrorCount++;
                            continue;
                        } else {

                            // ???????????? ????????????
                            int itemId = 0;
                            String control = "N";

                            try {
                                for (int i=0; i<oldItems.size(); i++) {
                                    if ("N".equals(oldItems.get(i).getTempControl()) && ShopUtils.getString(row.getCell(0)).equals(oldItems.get(i).getTempId())) {
                                        itemId = oldItems.get(i).getItemId();
                                        itemUserCode = oldItems.get(i).getItemUserCode();
                                        control = "N";
                                    } else if ("U".equals(oldItems.get(i).getTempControl()) && ShopUtils.getString(row.getCell(0)).equals(oldItems.get(i).getTempId())) {
                                        control = "U";
                                    }
                                }

                                if ("U".equals(control)) {
                                    itemId = itemMapper.getItemIdByItemUserCode(itemUserCode);
                                }
                            } catch(BindingException e) {
                                log.error("itemMapper.getItemIdByItemUserCode( Exception : {}", e.getMessage(), e);
                                // itemUserCode??? ????????? ???????????? Null??? ?????? int??? ????????? ??? ??? ??????.
                                // ?????? ????????? ????????? ???????????? ?????? ????????? ????????????.!
                            }

                            if (itemId == 0) {
                                executionLog.append(PoiUtils.log(cellReference, "Product is not exist. (" + itemUserCode + ")"));

                                cellErrorCount++;
                                continue;
                            }

                            // ?????? ?????? ?????? ??????..
                            optionDeleteFlag = true;		// ?????????????????? ?????? ????????? ??????.

                            itemOption.setItemId(itemId);
                            deleteItemIds.add(itemId);
                        }

                        break;

                    case 1: 	// ?????????
                        break;

                    case 2: 	// ???????????? ??????
                        if ("S".equals(ShopUtils.getString(cell)) || "S2".equals(ShopUtils.getString(cell))
                            || "S3".equals(ShopUtils.getString(cell)) || "T".equals(ShopUtils.getString(cell)) || "C".equals(ShopUtils.getString(cell))) {
                            itemOption.setOptionType(ShopUtils.getString(cell));
                        } else {
                            executionLog.append(PoiUtils.log(cellReference, "????????? ???????????? ???????????????."));

                            cellErrorCount++;
                            continue;
                        }
                        break;

                    case 3:	// ???????????? (???????????????)
                        if ("C".equals(itemOption.getOptionType())) {
                            itemOption.setOptionStockCode(ShopUtils.getString(cell));
                        }
                        break;

                    case 4:	// ERP ???????????? (???????????????)
                        if ("C".equals(itemOption.getOptionType())) {
                            itemOption.setErpItemCode(ShopUtils.getString(cell));
                        }
                        break;

                    case 5:		// ?????????1
                        String optionName1 = ShopUtils.getString(cell);

                        if (StringUtils.isEmpty(optionName1)) {
                            executionLog.append(PoiUtils.log(cellReference, "?????????1??? ???????????? ???????????????."));

                            cellErrorCount++;
                            continue;
                        }

                        if (!StringUtils.isEmpty(optionName1)) {
                            itemOption.setOptionName1(optionName1);
                            // oldOptionName1 = optionName1;
                        }
                        break;

                    case 6:		// ?????????2
                        String optionName2 = ShopUtils.getString(cell);

                        // ???????????? ???????????? ?????? - ????????? ?????? ?????? (????????? ?????? ?????? - ?????? ????????? ????????? ????????? ????????? ?????? ?????? ?????? ???.)
                        if (StringUtils.isEmpty(optionName2)) {

                            // ?????? ??????????
                            boolean isDeleteOption = true;
                            for (int i = 2; i <= 11; i++) {
                                if (!StringUtils.isEmpty(ShopUtils.getString(row.getCell(i)))) {
                                    isDeleteOption = false;
                                    break;
                                }
                            }

                            // ?????????????????? ?????? ????????? ??????.
                            if (!(!StringUtils.isEmpty(optionName2) && isDeleteOption) && !"T".equals(itemOption.getOptionType())) {
                                executionLog.append(PoiUtils.log(cellReference, "?????????2??? ???????????? ???????????????."));

                                cellErrorCount++;
                                continue;
                            }
                        }

                        itemOption.setOptionName2(ShopUtils.getString(cell));
                        break;

                    case 7:		// ?????????3
                        if (StringUtils.isEmpty(ShopUtils.getString(cell)) && "S3".equals(itemOption.getOptionType().toUpperCase())) {
                            executionLog.append(PoiUtils.log(cellReference, "?????????3??? ???????????? ???????????????."));

                            cellErrorCount++;
                            continue;
                        } else {
                            itemOption.setOptionName3(ShopUtils.getString(cell));
                        }
                        break;

                    case 8:		// ????????????
                        itemOption.setOptionPrice(ShopUtils.getInt(cell));
                        break;

                    case 9:		// ??????
                        itemOption.setOptionCostPrice(ShopUtils.getInt(cell));
                        break;

                    case 10:	// ???????????? (???????????????)
                        if ("C".equals(itemOption.getOptionType())) {
                            itemOption.setOptionQuantity(ShopUtils.getInt(cell));
                        } else {
                            itemOption.setOptionQuantity(1);
                        }
                        break;

                    case 11:	// ????????????
                        itemOption.setOptionStockFlag("N".equalsIgnoreCase(ShopUtils.getString(cell)) ? "N" : "Y");
                        break;

                    case 12:	// ????????????
                        if (itemOption != null && itemOption.getOptionStockFlag() != null && "Y".equals(itemOption.getOptionStockFlag().toUpperCase())) {
                            itemOption.setOptionStockQuantity(ShopUtils.getInt(cell));
                        }
                        break;

                    case 13:	// ????????????
                        itemOption.setOptionSoldOutFlag("Y".equalsIgnoreCase(ShopUtils.getString(cell)) ? "Y" : "N");
                        break;

                    case 14:    // ????????????
                        itemOption.setOptionDisplayFlag("N".equalsIgnoreCase(ShopUtils.getString(cell)) ? "N" : "Y");
                        break;

                    default:
                        break;
                }

            } // cell

            // ??????????????? + ??????????????? ???????????? ????????? ??????, ?????? ?????? ??????????????? ?????? (?????? ?????? ???????????? ????????? ??????)
            // ?????? ?????? - ????????????, ??????, ????????????, ????????????, ????????????
            if ("C".equals(itemOption.getOptionType()) && !StringUtils.isEmpty(itemOption.getErpItemCode())) {
                itemOption.setOptionPrice(0);
                itemOption.setOptionCostPrice(0);
                itemOption.setOptionStockFlag("N");
                itemOption.setOptionStockQuantity(-1);
                itemOption.setOptionSoldOutFlag("N");
            }

            // ?????? ?????? ????????? Cell??? ????????? ???????????? ????????? ?????? ?????? ???????????? ???????????? ??????.
            if (cellErrorCount > 0) {
                rowErrorCount++;
                continue;
            }

            // ?????? ?????? ?????? ??????.
            if (optionDeleteFlag) {
                itemMapperBatch.deleteItemOptionByItemId(itemOption.getItemId());
            }

            if ("T".equals(itemOption.getOptionType().toUpperCase()) && itemOption.getItemId() != 0 && !"".equals(itemOption.getOptionName1())) {
                itemOption.setItemOptionId(sequenceService.getId("OP_SHOP_ITEM_OPTION"));
                itemOption.setCreatedUserId(UserUtils.getUserId());
                itemOption.setOptionDisplayType("text");

                itemOptions.add(itemOption);
            }

            // ?????? ?????? ??????. (??????????????? ?????? ?????? ???????????? ?????? ????????? ???????????? ?????? ????????????)
            if (itemOption.getItemId() != 0 && !"".equals(itemOption.getOptionName2())) {
                itemOption.setItemOptionId(sequenceService.getId("OP_ITEM_OPTION"));
                itemOption.setCreatedUserId(UserUtils.getUserId());

                itemOptions.add(itemOption);
                //temMapperBatch.insertItemOption(itemOption);

            }

            rowDataCount++;

        } // row


        // ?????? ?????? ?????? ??????
        if (!itemOptions.isEmpty()) {

            Map<Integer, List<ItemOption>> itemOptionMap =
                itemOptions.stream()
                    .sorted(Comparator.comparing(ItemOption::getItemId).thenComparing(ItemOption::getOptionName1))
                    .collect(Collectors.groupingBy(ItemOption::getItemId, LinkedHashMap::new, Collectors.toList()));

            // ??????????????? ?????? ?????? ?????? (??????ID -> ????????? -> ???????????????)
            int ordering = 0;
            String oldOptionName1 = "";

            for (Integer ItemId : itemOptionMap.keySet()) {
                for (ItemOption itemOption : itemOptionMap.get(ItemId)) {
                    if ("C".equals(itemOption.getOptionType())) {
                        if (!oldOptionName1.equals(itemOption.getOptionName1())) {
                            ordering = 0;
                        }

                        itemOption.setOptionOrdering(ordering);
                        oldOptionName1 = itemOption.getOptionName1();

                        ordering++;
                    }
                }
            }

            itemOptions = itemOptionMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());

            itemMapperBatch.insertItemOptionListForExcel(itemOptions);
        }

        // ?????? ITEM_OPTION_FLAG ?????? ????????????.
        for (Integer itemId : deleteItemIds) {
            int matchCount = 0;
            for (ItemOption option : itemOptions) {
                if (option.getItemId() == itemId) {
                    matchCount++;
                    break;
                }
            }

            Item item = new Item();
            item.setItemId(itemId);

            if (matchCount == 0) {		// ??????
                item.setItemOptionFlag("N");
            } else {
                item.setItemOptionFlag("Y");
            }

            itemMapperBatch.updateItemOptionFlag(item);

            // OP_SHOP_ITEM ???????????? OPTION ?????? ????????? UPDATE
            for (ItemOption option : itemOptions) {
                if (option.getItemId() == itemId) {
                    item.setItemOptionType(option.getOptionType());
                }

				/*item.setItemOptionTitle1(option.getOptionName1());
                item.setItemOptionTitle2(option.getOptionName2());
                item.setItemOptionTitle3(option.getOptionName3());*/

                // ?????? ?????? ????????? ?????? ?????? ?????? ??????
                if ("Y".equals(option.getOptionStockFlag())) {
                    item.setStockFlag("N");
                }

                itemMapperBatch.updateItemOptionFlag(item);
            }
        }


        // ????????????
        result = "\n<p class=\"sheet\"><span>[" + sheet.getSheetName() + "]</span> Total:" + rowDataCount
            + ", Process:" + (rowDataCount - rowErrorCount)
            + ", Error:" + rowErrorCount + "</p>\n";

        if (rowErrorCount > 0) {
            result += "<p class=\"line\">----------------------------------------------------------------------------</p>\n";
            result += executionLog.toString();
            result += "\n";
        }
        return result;
    }


    /**
     * ?????? ????????? ?????? ?????? ?????? (ITEM_IMAGE)
     * @param sheet
     */
    @SuppressWarnings("static-access")
    private String processItemImageExcelSheet(XSSFSheet sheet, ArrayList<Item> oldItems) {
        String result = "";
        if (sheet == null) {
            return result;
        }

        StringBuffer executionLog = new StringBuffer();

        // ????????? ?????? INSER VALUE (), () ????????? ?????? ??????.
        List<ItemImage> itemImages = new ArrayList<>();
        List<HashMap<String, String>> cellReferences = new ArrayList<>();

        int rowDataCount = 0;			// ????????? ??? (?????????, ?????? ??????)
        int rowErrorCount = 0;			// ?????? ???

        List<Integer> deleteItemIds = new ArrayList<>();

        for (Row row : sheet) {
            int rowIndex = row.getRowNum() + 1;

            if (row.getRowNum() < 1) {
                continue;
            }

            // ?????? - ????????? ????????????
            if (row.getRowNum() == 1) {
                for (Cell cell : row) {
                    CellReference cellReference = new CellReference(cell);

                    HashMap<String, String> cellInfo = new HashMap<>();
                    cellInfo.put("title", ShopUtils.getString(cell).replace("(*)", ""));
                    cellInfo.put("colString", cellReference.convertNumToColString(cell.getColumnIndex()));

                    cellReferences.add(cellInfo);
                }
                continue;
            }


            // ?????? ????????? ??? ?????? ?????? ???????????? ????????? SKIP
            if (PoiUtils.isEmptyAllCell(row)) {
                continue;
            }


            // ??????, ????????? ??????
            int cellErrorCount = 0;
            boolean isDelete = false;		// ?????? ?????? ??? ????????? ???????????????.. ???????????? ?????? ??????.

            ItemImage itemImage = new ItemImage();

            for (Cell cell : row) {
                HashMap<String, String> cellReference = null;
                try {
                    cellReference = cellReferences.get(cell.getColumnIndex());
                } catch (Exception e) {
                    log.warn(" cellReferences.get(cell.getColumnIndex()) : {}", e.getMessage());
                }
                if (cellReference == null) {
                    continue;
                }
                cellReference.put("rowIndex", Integer.toString(rowIndex));

                switch (cell.getColumnIndex()) {
                    case 0: 	// ????????????

                        String itemUserCode = ShopUtils.getString(cell);

                        // ??????????????? ???????????? ?????? ??????
                        if (itemUserCode == null || "".equals(itemUserCode)) {
                            executionLog.append(PoiUtils.log(cellReference, "Product code is empty!"));

                            cellErrorCount++;
                            continue;
                        } else {

                            // ???????????? ????????????
                            int itemId = 0;
                            String control = "N";

                            try {

                                for (int i=0; i<oldItems.size(); i++) {
                                    if ("N".equals(oldItems.get(i).getTempControl()) && ShopUtils.getString(row.getCell(0)).equals(oldItems.get(i).getTempId())) {
                                        itemId = oldItems.get(i).getItemId();
                                        itemUserCode = oldItems.get(i).getItemUserCode();
                                        control = "N";
                                    } else if ("U".equals(oldItems.get(i).getTempControl()) && ShopUtils.getString(row.getCell(0)).equals(oldItems.get(i).getTempId())) {
                                        control = "U";
                                    }
                                }

                                if ("U".equals(control)) {
                                    itemId = itemMapper.getItemIdByItemUserCode(itemUserCode);
                                }

                                itemImage.setItemId(itemId);
                            } catch(BindingException e) {
                                executionLog.append(PoiUtils.log(cellReference, "Product is not exist. (" + itemUserCode + ")"));
                                cellErrorCount++;
                                continue;
                            }


                            // ???????????? ???????????? (?????? ?????????) - ?????? ????????? ?????????????????? ????????? ?????? ?????? ?????? ????????? ?????? ?????? ?????? ??????.
                            boolean isDuplicationItemId = false;
                            for (ItemImage data : itemImages) {
                                if (data.getItemId() == itemId) {
                                    isDuplicationItemId = true;
                                    break;
                                }
                            }

                            // ?????????????????? ?????? ????????? ??????.
                            if (!isDuplicationItemId) {
                                isDelete = true;			// ?????? ?????? ?????? ??????..
                            }

                            itemImage.setItemId(itemId);
                            deleteItemIds.add(itemId);
                        }

                        break;

                    case 1: 	// ?????????
                        break;

                    case 2: 	// ????????????

                        String imageName = ShopUtils.getString(cell);

                        if(StringUtils.isEmpty(imageName)) {
                            imageName = cell.getCellFormula();
                        }

                        if(StringUtils.isEmpty(imageName)) {
                            imageName = cell.getHyperlink().getAddress();
                        }

                        itemImage.setImageName(imageName);


                        if ("".equals(itemImage.getImageName())) {
                            executionLog.append(PoiUtils.log(cellReference, "Image name is empty!"));
                            cellErrorCount++;
                            continue;
                        }

                        if(itemImage.getImageName().contains("HYPERLINK")) {
                            int start = imageName.indexOf('"');
                            int end = imageName.indexOf(",");
                            String linkedImageName = imageName.substring(start + 1, end - 1);
                            itemImage.setImageName(linkedImageName);
                        }

                        break;

                    case 3: 	// ?????? ??????
                        String ordering = Integer.toString(ShopUtils.getInt(cell));
                        itemImage.setOrdering("".equals(ordering) ? 0 : Integer.parseInt(ordering));

                        break;
                    default:
                        break;
                }

            } // cell

            // ?????? ?????? ????????? Cell??? ????????? ???????????? ????????? ?????? ?????? ???????????? ???????????? ??????.
            if (cellErrorCount > 0) {
                rowErrorCount++;
                continue;
            }


            // ?????? ????????? ?????? ??????.
            if (isDelete) {
                itemMapperBatch.deleteItemImageByItemId(itemImage.getItemId());
            }


            // ?????? ????????? ?????? ??????. (??????????????? ?????? ?????? ???????????? ?????? ????????? ???????????? ?????? ????????????)
            if (itemImage != null && itemImage.getItemId() != 0
                && !"".equals(itemImage.getImageName())) {

                itemImage.setItemImageId(sequenceService.getId("OP_ITEM_IMAGE"));
                itemImages.add(itemImage);
                //temMapperBatch.insertItemOption(itemOption);

            }

            rowDataCount++;

        } // row


        // ?????? ????????? ?????? ??????
        if (!itemImages.isEmpty()) {
            itemMapperBatch.insertItemImageListForExcel(itemImages);

            // ????????? ?????? ??? ???????????? ?????? ???????????? ??????.
            int previousItemId = 0;
            for (ItemImage itemImage : itemImages) {
                if (itemImage.getItemId() != previousItemId && !"".equals(itemImage.getImageName())) {
                    itemMapperBatch.updateItemImageInfoByItemImage(itemImage);
                    previousItemId = itemImage.getItemId();
                }
            }
        }

        // ????????????
        result = "\n<p class=\"sheet\"><span>[" + sheet.getSheetName() + "]</span> Total:" + rowDataCount
            + ", Process:" + (rowDataCount - rowErrorCount)
            + ", Error:" + rowErrorCount + "</p>\n";

        if (rowErrorCount > 0) {
            result += "<p class=\"line\">----------------------------------------------------------------------------</p>\n";
            result += executionLog.toString();
            result += "\n";
        }
        return result;
    }

    /**
     * ?????? ???????????? ?????? ?????? ?????? (ITEM_CATEGORY)
     * @param sheet
     */
    @SuppressWarnings("static-access")
    private String processItemCategoryExcelSheet(XSSFSheet sheet, ArrayList<Item> oldItems) {
        String result = "";
        if (sheet == null) {
            return result;
        }

        StringBuffer executionLog = new StringBuffer();

        // ????????? ?????? INSER VALUE (), () ????????? ?????? ??????.
        List<ItemCategory> itemCategories = new ArrayList<>();
        List<HashMap<String, String>> cellReferences = new ArrayList<>();

        int rowDataCount = 0;			// ????????? ??? (?????????, ?????? ??????)
        int rowErrorCount = 0;			// ?????? ???

        List<Integer> deleteItemIds = new ArrayList<>();

        for (Row row : sheet) {
            int rowIndex = row.getRowNum() + 1;

            if (row.getRowNum() < 1) {
                continue;
            }

            // ?????? - ????????? ????????????
            if (row.getRowNum() == 1) {
                for (Cell cell : row) {
                    CellReference cellReference = new CellReference(cell);

                    HashMap<String, String> cellInfo = new HashMap<>();
                    cellInfo.put("title", ShopUtils.getString(cell).replace("(*)", ""));
                    cellInfo.put("colString", cellReference.convertNumToColString(cell.getColumnIndex()));

                    cellReferences.add(cellInfo);
                }
                continue;
            }


            // ?????? ????????? ??? ?????? ?????? ???????????? ????????? SKIP
            if (PoiUtils.isEmptyAllCell(row)) {
                continue;
            }


            // ??????, ????????? ??????
            int cellErrorCount = 0;
            boolean isDelete = false;		// ?????? ?????? ??? ????????? ???????????????.. ???????????? ?????? ??????.

            ItemCategory itemCategory = new ItemCategory();

            for (Cell cell : row) {
                HashMap<String, String> cellReference = null;
                try {
                    cellReference = cellReferences.get(cell.getColumnIndex());
                } catch (Exception e) {
                    log.warn(" cellReferences.get(cell.getColumnIndex()) : {}", e.getMessage());
                }
                if (cellReference == null) {
                    continue;
                }
                cellReference.put("rowIndex", Integer.toString(rowIndex));

                switch (cell.getColumnIndex()) {
                    case 0: 	// ????????????

                        String itemUserCode = ShopUtils.getString(cell);

                        // ??????????????? ???????????? ?????? ??????
                        if (itemUserCode == null || "".equals(itemUserCode)) {
                            executionLog.append(PoiUtils.log(cellReference, "Product code is empty!"));

                            cellErrorCount++;
                            continue;
                        } else {

                            // ???????????? ????????????
                            int itemId = 0;
                            String control = "N";

                            try {

                                for (int i=0; i<oldItems.size(); i++) {
                                    if ("N".equals(oldItems.get(i).getTempControl()) && ShopUtils.getString(row.getCell(0)).equals(oldItems.get(i).getTempId())) {
                                        itemId = oldItems.get(i).getItemId();
                                        itemUserCode = oldItems.get(i).getItemUserCode();
                                        control = "N";
                                    } else if ("U".equals(oldItems.get(i).getTempControl()) && ShopUtils.getString(row.getCell(0)).equals(oldItems.get(i).getTempId())) {
                                        control = "U";
                                    }
                                }

                                if ("U".equals(control)) {
                                    itemId = itemMapper.getItemIdByItemUserCode(itemUserCode);
                                }

                            } catch(BindingException e) {
                                executionLog.append(PoiUtils.log(cellReference, "Product is not exist. (" + itemUserCode + ")"));
                                cellErrorCount++;
                                continue;
                            }

                            // ???????????? ???????????? (?????? ?????????) - ?????? ????????? ?????????????????? ????????? ?????? ?????? ?????? ????????? ?????? ?????? ?????? ??????.
                            boolean isDuplicationItemId = false;
                            for (ItemCategory data : itemCategories) {
                                if (data.getItemId() == itemId) {
                                    isDuplicationItemId = true;
                                    break;
                                }
                            }

                            // ?????????????????? ?????? ????????? ??????.
                            if (!isDuplicationItemId) {
                                isDelete = true;			// ?????? ?????? ???????????? ??????..
                            }

                            itemCategory.setItemId(itemId);
                            deleteItemIds.add(itemId);
                        }

                        break;

                    case 1: 	// ?????????
                        break;

                    case 2: 	// ???????????? ??????
                        String categoryUrl = ShopUtils.getString(cell);

                        if ("".equals(categoryUrl)) {
                            executionLog.append(PoiUtils.log(cellReference, "Category code is empty!"));
                            cellErrorCount++;
                            continue;
                        }

                        // CategoryId ??????
                        int categoryId = 0;
                        try {
                            categoryId = categoriesMapper.getCategoryIdByCategoryUrl(categoryUrl);

                        } catch (BindingException e) {
                            executionLog.append(PoiUtils.log(cellReference, "Category is not exist. (" + categoryUrl + ")"));
                            cellErrorCount++;
                            continue;

                        }

                        // ?????? ????????? ?????? ??????????????? ????????? ??????.
                        boolean isDuplcateCategory = false;
                        for(ItemCategory itemCate : itemCategories) {
                            if (itemCate.getItemId() == itemCategory.getItemId()
                                && itemCate.getCategoryId() == categoryId) {
                                isDuplcateCategory = true;
                                break;
                            }
                        }

                        if (isDuplcateCategory) {
                            executionLog.append(PoiUtils.log(cellReference, "There is the same category. (" + categoryUrl + ")"));
                            cellErrorCount++;
                            continue;
                        }

                        itemCategory.setCategoryId(categoryId);
                        break;

                    case 3: 	// ???????????????
                        break;

                    case 4: 	// ?????? ??????
                        String ordering = Integer.toString(ShopUtils.getInt(cell));
                        itemCategory.setOrdering(StringUtils.isEmpty(ordering) ? 0 : Integer.parseInt(ordering));

                        break;
                    default:
                        break;
                }

            } // cell

            // ?????? ?????? ????????? Cell??? ????????? ???????????? ????????? ?????? ?????? ???????????? ???????????? ??????.
            if (cellErrorCount > 0) {
                rowErrorCount++;
                continue;
            }


            // ?????? ????????? ?????? ??????.
            if (isDelete) {
                itemMapperBatch.deleteItemCategoryByItemId(itemCategory.getItemId());
            }


            // ?????? ????????? ?????? ??????. (??????????????? ?????? ?????? ???????????? ?????? ????????? ???????????? ?????? ????????????)
            if (itemCategory.getItemId() != 0 && itemCategory.getCategoryId() != 0) {
                itemCategory.setItemCategoryId(sequenceService.getId("OP_ITEM_CATEGORY"));
                itemCategories.add(itemCategory);
            }

            rowDataCount++;

        } // row


        // ?????? ???????????? ?????? ??????
        if (!itemCategories.isEmpty()) {
            itemMapperBatch.insertItemCategoryListForExcel(itemCategories);
        }

        // ????????????
        result = "\n<p class=\"sheet\"><span>[" + sheet.getSheetName() + "]</span> Total:" + rowDataCount
            + ", Process:" + (rowDataCount - rowErrorCount)
            + ", Error:" + rowErrorCount + "</p>\n";

        if (rowErrorCount > 0) {
            result += "<p class=\"line\">----------------------------------------------------------------------------</p>\n";
            result += executionLog.toString();
            result += "\n";
        }

        return result;
    }

    /**
     * ?????? ?????????????????? ?????? ?????? ?????? (ITEM_ADDITION)
     * @param sheet
     */
    @SuppressWarnings("static-access")
    private String processItemAdditionExcelSheet(XSSFSheet sheet, ArrayList<Item> oldItems) {
        String result = "";
        if (sheet == null) {
            return result;
        }

        StringBuffer executionLog = new StringBuffer();

        // ????????? ?????? INSER VALUE (), () ????????? ?????? ??????.
        List<ItemAddition> itemAdditions = new ArrayList<>();
        List<HashMap<String, String>> cellReferences = new ArrayList<>();

        int rowDataCount = 0;			// ????????? ??? (?????????, ?????? ??????)
        int rowErrorCount = 0;			// ?????? ???

        List<Integer> deleteItemIds = new ArrayList<>();

        for (Row row : sheet) {
            int rowIndex = row.getRowNum() + 1;

            if (row.getRowNum() < 1) {
                continue;
            }

            // ?????? - ????????? ????????????
            if (row.getRowNum() == 1) {
                for (Cell cell : row) {
                    CellReference cellReference = new CellReference(cell);

                    HashMap<String, String> cellInfo = new HashMap<>();
                    cellInfo.put("title", ShopUtils.getString(cell).replace("(*)", ""));
                    cellInfo.put("colString", cellReference.convertNumToColString(cell.getColumnIndex()));

                    cellReferences.add(cellInfo);
                }
                continue;
            }

            // ?????? ????????? ??? ?????? ?????? ???????????? ????????? SKIP
            if (PoiUtils.isEmptyAllCell(row)) {
                continue;
            }

            // ??????, ????????? ??????
            int cellErrorCount = 0;
            boolean isDelete = false;		// ?????? ?????? ????????? ?????????.

            ItemAddition itemAddition = new ItemAddition();

            for (Cell cell : row) {

                HashMap<String, String> cellReference = null;
                try {
                    cellReference = cellReferences.get(cell.getColumnIndex());
                } catch (Exception e) {
                    log.warn(" cellReferences.get(cell.getColumnIndex()) : {}", e.getMessage());
                }
                if (cellReference == null) {
                    continue;
                }
                cellReference.put("rowIndex", Integer.toString(rowIndex));

                switch (cell.getColumnIndex()) {
                    case 0: 	// ????????????

                        String itemUserCode = ShopUtils.getString(cell);

                        // ??????????????? ???????????? ?????? ??????
                        if (StringUtils.isEmpty(itemUserCode)) {
                            executionLog.append(PoiUtils.log(cellReference, "Product code is empty!"));
                            cellErrorCount++;
                            continue;
                        } else {

                            // ???????????? ???????????? (DB)
                            int itemId = 0;
                            String control = "N";

                            try {

                                for (int i=0; i<oldItems.size(); i++) {
                                    if ("N".equals(oldItems.get(i).getTempControl()) && ShopUtils.getString(row.getCell(0)).equals(oldItems.get(i).getTempId())) {
                                        itemId = oldItems.get(i).getItemId();
                                        itemUserCode = oldItems.get(i).getItemUserCode();
                                        control = "N";
                                    } else if ("U".equals(oldItems.get(i).getTempControl()) && ShopUtils.getString(row.getCell(0)).equals(oldItems.get(i).getTempId())) {
                                        control = "U";
                                    }
                                }

                                if ("U".equals(control)) {
                                    itemId = itemMapper.getItemIdByItemUserCode(itemUserCode);
                                }

                            } catch(BindingException e) {
                                executionLog.append(PoiUtils.log(cellReference, "Product is not exist. (" + itemUserCode + ")"));
                                cellErrorCount++;
                                continue;
                            }

                            // ???????????? ???????????? (?????? ?????????) - ?????? ????????? ?????????????????? ????????? ?????? ?????? ?????? ????????? ?????? ?????? ?????? ??????.
                            boolean isDuplicationItemId = false;
                            for (ItemAddition data : itemAdditions) {
                                if (data.getItemId() == itemId) {
                                    isDuplicationItemId = true;
                                    break;
                                }
                            }

                            // ?????????????????? ?????? ????????? ??????.
                            if (!isDuplicationItemId) {
                                isDelete = true;			// ?????? ?????? ?????? ??????..
                            }

                            itemAddition.setItemId(itemId);
                            deleteItemIds.add(itemId);
                        }

                        break;

                    case 1: 	// ?????????
                        break;

                    case 2: 	// ?????? ????????????
                        String additionItemUserCode = ShopUtils.getString(cell);

                        if ("".equals(additionItemUserCode)) {
                            executionLog.append(PoiUtils.log(cellReference, "Addition product code is empty!"));
                            cellErrorCount++;
                            continue;
                        }

                        // ?????? ?????? ID??????
                        int additionItemId = 0;
                        try {
                            additionItemId = itemMapper.getItemIdByItemUserCode(additionItemUserCode);

                        } catch (BindingException e) {
                            executionLog.append(PoiUtils.log(cellReference, "Product is not exist. (" + additionItemUserCode + ")"));
                            cellErrorCount++;
                            continue;
                        }

                        // ?????? ????????? ????????? ??????????????? ????????? ??????.
                        boolean isDuplicateCategory = false;
                        for (ItemAddition itemAdd : itemAdditions) {
                            if (itemAdd.getItemId() == itemAddition.getItemId()
                                    && itemAdd.getAdditionItemId() == additionItemId) {
                                isDuplicateCategory = true;
                                break;
                            }
                        }

                        if (isDuplicateCategory) {
                            executionLog.append(PoiUtils.log(cellReference, "Product code is already in use. (" + additionItemUserCode + ")"));
                            cellErrorCount++;
                            continue;
                        }

                        itemAddition.setAdditionItemId(additionItemId);
                        break;

                    case 3: 	// ???????????????
                        break;

                    case 4: 	// ?????? ??????
                        String ordering = Integer.toString(ShopUtils.getInt(cell));
                        itemAddition.setOrdering("".equals(ordering) ? 0 : ShopUtils.getInt(cell));

                        break;
                    default:
                        break;
                }

            } // cell

            // ?????? ?????? ????????? Cell??? ????????? ???????????? ????????? ?????? ?????? ???????????? ???????????? ??????.
            if (cellErrorCount > 0) {
                rowErrorCount++;
                continue;
            }


            // ?????? ?????? ?????? ??????.
            if (isDelete) {
                itemMapperBatch.deleteItemAdditionByItemId(itemAddition.getItemId());
            }


            // ?????? ?????? ?????? ??????. (??????????????? ?????? ?????? ???????????? ?????? ????????? ???????????? ?????? ????????????)
            if (itemAddition.getItemId() != 0 && itemAddition.getAdditionItemId() != 0) {
                itemAdditions.add(itemAddition);
            }

            rowDataCount++;

        } // row


        // ?????? ?????? ?????? ??????
        if (!itemAdditions.isEmpty()) {
            itemMapperBatch.insertItemAdditionListForExcel(itemAdditions);
        }


        // ?????? ITEM_OPTION_FLAG ?????? ????????????.
        for (Integer itemId : deleteItemIds) {
            int matchCount = 0;
            for (ItemAddition itemAddition : itemAdditions) {
                if (itemAddition.getItemId() == itemId) {
                    matchCount++;
                    break;
                }
            }

            Item item = new Item();
            item.setItemId(itemId);

            if (matchCount == 0) {		// ??????
                item.setItemAdditionFlag("N");		// ????????????
            } else {
                item.setItemAdditionFlag("Y");		// ??????
            }
            itemMapperBatch.updateAdditionItemFlag(item);
        }


        // ????????????
        result = "\n<p class=\"sheet\"><span>[" + sheet.getSheetName() + "]</span> Total:" + rowDataCount
                + ", Process:" + (rowDataCount - rowErrorCount)
                + ", Error:" + rowErrorCount + "</p>\n";

        if (rowErrorCount > 0) {
            result += "<p class=\"line\">----------------------------------------------------------------------------</p>\n";
            result += executionLog.toString();
            result += "\n";
        }

        return result;
    }

    /**
     * ?????? ???????????? ?????? ?????? ?????? (ITEM_RELATION)
     * @param sheet
     */
    @SuppressWarnings("static-access")
    private String processItemRelationExcelSheet(XSSFSheet sheet, ArrayList<Item> oldItems) {
        String result = "";
        if (sheet == null) {
            return result;
        }

        StringBuffer executionLog = new StringBuffer();

        // ????????? ?????? INSER VALUE (), () ????????? ?????? ??????.
        List<ItemRelation> itemRelations = new ArrayList<>();
        List<HashMap<String, String>> cellReferences = new ArrayList<>();

        int rowDataCount = 0;			// ????????? ??? (?????????, ?????? ??????)
        int rowErrorCount = 0;			// ?????? ???

        List<Integer> deleteItemIds = new ArrayList<>();

        for (Row row : sheet) {
            int rowIndex = row.getRowNum() + 1;

            if (row.getRowNum() < 1) {
                continue;
            }

            // ?????? - ????????? ????????????
            if (row.getRowNum() == 1) {
                for (Cell cell : row) {
                    CellReference cellReference = new CellReference(cell);

                    HashMap<String, String> cellInfo = new HashMap<>();
                    cellInfo.put("title", ShopUtils.getString(cell).replace("(*)", ""));
                    cellInfo.put("colString", cellReference.convertNumToColString(cell.getColumnIndex()));

                    cellReferences.add(cellInfo);
                }
                continue;
            }

            // ?????? ????????? ??? ?????? ?????? ???????????? ????????? SKIP
            if (PoiUtils.isEmptyAllCell(row)) {
                continue;
            }


            // ??????, ????????? ??????
            int cellErrorCount = 0;
            boolean isDelete = false;		// ?????? ?????? ????????? ?????????.

            ItemRelation itemRelation = new ItemRelation();

            for (Cell cell : row) {

                HashMap<String, String> cellReference = null;
                try {
                    cellReference = cellReferences.get(cell.getColumnIndex());
                } catch (Exception e) {
                    log.warn(" cellReferences.get(cell.getColumnIndex()) : {}", e.getMessage());
                }
                if (cellReference == null) {
                    continue;
                }
                cellReference.put("rowIndex", Integer.toString(rowIndex));

                switch (cell.getColumnIndex()) {
                    case 0: 	// ????????????

                        String itemUserCode = ShopUtils.getString(cell);

                        // ??????????????? ???????????? ?????? ??????
                        if (StringUtils.isEmpty(itemUserCode)) {
                            executionLog.append(PoiUtils.log(cellReference, "Product code is empty!"));
                            cellErrorCount++;
                            continue;
                        } else {

                            // ???????????? ???????????? (DB)
                            int itemId = 0;
                            String control = "N";

                            try {

                                for (int i=0; i<oldItems.size(); i++) {
                                    if ("N".equals(oldItems.get(i).getTempControl()) && ShopUtils.getString(row.getCell(0)).equals(oldItems.get(i).getTempId())) {
                                        itemId = oldItems.get(i).getItemId();
                                        itemUserCode = oldItems.get(i).getItemUserCode();
                                        control = "N";
                                    } else if ("U".equals(oldItems.get(i).getTempControl()) && ShopUtils.getString(row.getCell(0)).equals(oldItems.get(i).getTempId())) {
                                        control = "U";
                                    }
                                }

                                if ("U".equals(control)) {
                                    itemId = itemMapper.getItemIdByItemUserCode(itemUserCode);
                                }

                            } catch(BindingException e) {
                                executionLog.append(PoiUtils.log(cellReference, "Product is not exist. (" + itemUserCode + ")"));
                                cellErrorCount++;
                                continue;
                            }

                            // ???????????? ???????????? (?????? ?????????) - ?????? ????????? ?????????????????? ????????? ?????? ?????? ?????? ????????? ?????? ?????? ?????? ??????.
                            boolean isDuplicationItemId = false;
                            for (ItemRelation data : itemRelations) {
                                if (data.getItemId() == itemId) {
                                    isDuplicationItemId = true;
                                    break;
                                }
                            }

                            // ?????????????????? ?????? ????????? ??????.
                            if (!isDuplicationItemId) {
                                isDelete = true;			// ?????? ?????? ?????? ??????..
                            }

                            itemRelation.setItemId(itemId);
                            deleteItemIds.add(itemId);
                        }

                        break;

                    case 1: 	// ?????????
                        break;

                    case 2: 	// ?????? ????????????
                        String relationItemUserCode = ShopUtils.getString(cell);

                        if ("".equals(relationItemUserCode)) {
                            executionLog.append(PoiUtils.log(cellReference, "Relation product code is empty!"));
                            cellErrorCount++;
                            continue;
                        }

                        // ?????? ?????? ID??????
                        int relationItemId = 0;
                        try {
                            relationItemId = itemMapper.getItemIdByItemUserCode(relationItemUserCode);

                        } catch (BindingException e) {
                            executionLog.append(PoiUtils.log(cellReference, "Product is not exist. (" + relationItemUserCode + ")"));
                            cellErrorCount++;
                            continue;
                        }

                        // ?????? ????????? ????????? ??????????????? ????????? ??????.
                        boolean isDuplcateCategory = false;
                        for(ItemRelation itemRelate : itemRelations) {
                            if (itemRelate.getItemId() == itemRelation.getItemId()
                                && itemRelate.getRelatedItemId() == relationItemId) {
                                isDuplcateCategory = true;
                                break;
                            }
                        }

                        if (isDuplcateCategory) {
                            executionLog.append(PoiUtils.log(cellReference, "Product code is already in use. (" + relationItemUserCode + ")"));
                            cellErrorCount++;
                            continue;
                        }

                        itemRelation.setRelatedItemId(relationItemId);
                        break;

                    case 3: 	// ???????????????
                        break;

                    case 4: 	// ?????? ??????
                        String ordering = Integer.toString(ShopUtils.getInt(cell));
                        itemRelation.setOrdering("".equals(ordering) ? 0 : ShopUtils.getInt(cell));

                        break;
                    default:
                        break;
                }

            } // cell

            // ?????? ?????? ????????? Cell??? ????????? ???????????? ????????? ?????? ?????? ???????????? ???????????? ??????.
            if (cellErrorCount > 0) {
                rowErrorCount++;
                continue;
            }


            // ?????? ?????? ?????? ??????.
            if (isDelete) {
                itemMapperBatch.deleteItemRelationByItemId(itemRelation.getItemId());
            }


            // ?????? ?????? ?????? ??????. (??????????????? ?????? ?????? ???????????? ?????? ????????? ???????????? ?????? ????????????)
            if (itemRelation.getItemId() != 0 && itemRelation.getRelatedItemId() != 0) {
                itemRelation.setItemRelationId(sequenceService.getId("OP_ITEM_RELATION"));
                itemRelations.add(itemRelation);
            }

            rowDataCount++;

        } // row


        // ?????? ?????? ?????? ??????
        if (!itemRelations.isEmpty()) {
            itemMapperBatch.insertItemRelationListForExcel(itemRelations);
        }


        // ?????? ITEM_OPTION_FLAG ?????? ????????????.
        for (Integer itemId : deleteItemIds) {
            int matchCount = 0;
            for (ItemRelation itemRelation : itemRelations) {
                if (itemRelation.getItemId() == itemId) {
                    matchCount++;
                    break;
                }
            }


            Item item = new Item();
            item.setItemId(itemId);

            if (matchCount == 0) {		// ??????
                item.setRelationItemDisplayType("1");		// ?????? ??????
            } else {
                item.setRelationItemDisplayType("2");		// ???????????? ??????
            }
            itemMapperBatch.updateRelationItemDisplayType(item);
        }


        // ????????????
        result = "\n<p class=\"sheet\"><span>[" + sheet.getSheetName() + "]</span> Total:" + rowDataCount
            + ", Process:" + (rowDataCount - rowErrorCount)
            + ", Error:" + rowErrorCount + "</p>\n";

        if (rowErrorCount > 0) {
            result += "<p class=\"line\">----------------------------------------------------------------------------</p>\n";
            result += executionLog.toString();
            result += "\n";
        }

        return result;
    }

    /**
     * ?????? ????????? ?????? ?????? ?????? ?????? (SHOP_POINT_CONFIG)
     * @param sheet
     */
    @SuppressWarnings("static-access")
    private String processItemPointConfigExcelSheet(XSSFSheet sheet) {
        String result = "";
        if (sheet == null) {
            return result;
        }

        StringBuffer executionLog = new StringBuffer();

        // ????????? ?????? INSER VALUE (), () ????????? ?????? ??????.
        List<PointConfig> pointConfigures = new ArrayList<>();
        List<HashMap<String, String>> cellReferences = new ArrayList<>();

        int rowDataCount = 0;			// ????????? ??? (?????????, ?????? ??????)
        int rowErrorCount = 0;			// ?????? ???

        List<Integer> deleteItemIds = new ArrayList<>();

        int oldItemId = 0;

        for (Row row : sheet) {
            int rowIndex = row.getRowNum() + 1;

            if (row.getRowNum() < 1) {
                continue;
            }

            // ?????? - ????????? ????????????
            if (row.getRowNum() == 1) {
                for (Cell cell : row) {
                    CellReference cellReference = new CellReference(cell);

                    HashMap<String, String> cellInfo = new HashMap<>();
                    cellInfo.put("title", ShopUtils.getString(cell).replace("(*)", ""));
                    cellInfo.put("colString", cellReference.convertNumToColString(cell.getColumnIndex()));

                    cellReferences.add(cellInfo);
                }
                continue;
            }

            // ?????? ????????? ??? ?????? ?????? ???????????? ????????? SKIP
            if (PoiUtils.isEmptyAllCell(row)) {
                continue;
            }


            // ??????, ????????? ??????
            int cellErrorCount = 0;
            boolean isDelete = false;		// ?????? ?????? ??? ????????? ???????????????.. ???????????? ?????? ??????.


            PointConfig pointConfig = new PointConfig();
            pointConfig.setItemId(oldItemId);


            for (Cell cell : row) {
                HashMap<String, String> cellReference = null;
                try {
                    cellReference = cellReferences.get(cell.getColumnIndex());
                } catch (Exception e) {
                    log.warn(" cellReferences.get(cell.getColumnIndex()) : {}", e.getMessage());
                }
                if (cellReference == null) {
                    continue;
                }
                cellReference.put("rowIndex", Integer.toString(rowIndex));

                switch (cell.getColumnIndex()) {
                    case 0: 	// ????????????

                        String itemUserCode = ShopUtils.getString(cell);

                        // ??????????????? ???????????? ?????? ??????
                        if (itemUserCode == null || "".equals(itemUserCode)) {
                            if (oldItemId == 0) {
                                executionLog.append(PoiUtils.log(cellReference, "Product code is empty!"));

                                cellErrorCount++;
                                continue;
                            }
                        } else {

                            // ???????????? ????????????
                            int itemId = 0;

                            try {
                                itemId = itemMapper.getItemIdByItemUserCode(itemUserCode);
                            } catch(BindingException e) {
                                executionLog.append(PoiUtils.log(cellReference, "Product is not exist. (" + itemUserCode + ")"));
                                cellErrorCount++;
                                continue;
                            }

                            // ???????????? ???????????? (?????? ?????????) - ?????? ????????? ?????????????????? ????????? ?????? ?????? ?????? ????????? ?????? ?????? ?????? ??????.
                            boolean isDuplicationItemId = false;
                            for (PointConfig data : pointConfigures) {
                                if (data.getItemId() == itemId) {
                                    isDuplicationItemId = true;
                                    break;
                                }
                            }

                            // ?????????????????? ?????? ????????? ??????.
                            if (!isDuplicationItemId) {
                                isDelete = true;			// ?????? ?????? ????????? ?????? ??????..
                            }

                            oldItemId = itemId;
                            pointConfig.setItemId(itemId);
                            deleteItemIds.add(itemId);
                        }

                        break;

                    case 1: 	// ?????????
                        break;

                    case 2: 	// ???????????????
                        pointConfig.setPoint("".equals(ShopUtils.getString(cell)) ? 0 : ShopUtils.getInt(cell));
                        break;

                    case 3: 	// ???????????? (1:??????, 2:??????)
                        pointConfig.setPointType("2".equals(ShopUtils.getString(cell)) ? "2" : "1");
                        break;

                    case 4: 	// ???????????? - ?????????
                        String startDate = ShopUtils.getString(cell);
                        if (!DateUtils.checkDate(startDate)) {
                            executionLog.append(PoiUtils.log(cellReference, "Date format is invalid. (input:'" + startDate + "', format:yyyymmdd(ex.20140706))"));
                            cellErrorCount++;
                            continue;
                        }
                        pointConfig.setStartDate(startDate);
                        break;

                    case 5: 	// ???????????? - ????????????
                        String startTime = ShopUtils.getString(cell);

                        boolean hasValidationError = false;

                        if ("".equals(startTime)) {
                            hasValidationError = true;
                        }

                        boolean isMatchTime = false;
                        for (int i = 0; i <= 23; i++) {
                            String checkString = "" + i;
                            if (i < 10) {
                                checkString = "0" + i;
                            }

                            if (checkString.equals(startTime) || Integer.toString(i).equals(startTime)) {
                                isMatchTime = true;
                                break;
                            }
                        }

                        if (!isMatchTime) {
                            hasValidationError = true;
                        }

                        if (hasValidationError) {
                            executionLog.append(PoiUtils.log(cellReference, "Time format is invalid. (input:'" + startTime + "', format:00 ~ 23)"));
                            cellErrorCount++;
                            continue;
                        }
                        pointConfig.setStartTime(startTime.length() == 1 ? "0" + startTime : startTime);
                        break;

                    case 6: 	// ???????????? - ?????????
                        String endDate = ShopUtils.getString(cell);
                        if (!DateUtils.checkDate(endDate)) {
                            executionLog.append(PoiUtils.log(cellReference, "Date format is invalid. (input:'" + endDate + "', format:yyyymmdd(ex.20140706))"));
                            cellErrorCount++;
                            continue;
                        }
                        pointConfig.setEndDate(endDate);
                        break;

                    case 7: 	// ???????????? - ????????????
                        String endTime = ShopUtils.getString(cell);

                        boolean hasEndTimeError = false;

                        if ("".equals(endTime)) {
                            hasEndTimeError = true;
                        }

                        boolean isMatchEndTime = false;
                        for (int i = 0; i <= 23; i++) {
                            String checkString = "" + i;
                            if (i < 10) {
                                checkString = "0" + i;
                            }

                            if (checkString.equals(endTime) || Integer.toString(i).equals(endTime)) {
                                isMatchEndTime = true;
                                break;
                            }
                        }

                        if (!isMatchEndTime) {
                            hasEndTimeError = true;
                        }

                        if (hasEndTimeError) {
                            executionLog.append(PoiUtils.log(cellReference, "Time format is invalid. (input:'" + endTime + "', format:00 ~ 23)"));
                            cellErrorCount++;
                            continue;
                        }
                        pointConfig.setEndTime(endTime.length() == 1 ? "0" + endTime : endTime);
                        break;
					/*
					case 8: 	// ?????????
						String repeatDay = ShopUtils.getString()(cell);

						if (!"".equals(repeatDay)) {
							boolean isMatchDay = false;
							for (int i = 1; i <= 31; i++) {
								String checkString = "" + i;

								if (checkString.equals(repeatDay)) {
									isMatchDay = true;
									break;
								}
							}

							if (!isMatchDay) {
								executionLog.append(PoiUtils.log(cellReference, "Day format is invalid. (input:'" + repeatDay + "', format:1 ~ 31)"));
								cellErrorCount++;
				    			continue;
							}
						}

						pointConfig.setRepeatDay(repeatDay);

						// ???????????? (1:??????, 2:?????????)
						pointConfig.setPeriodType("".equals(repeatDay) ? "1" : "2");
					*/
                    default:
                        break;
                }

            } // cell

            // ?????? ?????? ????????? Cell??? ????????? ???????????? ????????? ?????? ?????? ???????????? ???????????? ??????.
            if (cellErrorCount > 0) {
                rowErrorCount++;
                continue;
            }


            // ?????? ????????? ?????? ?????? ?????? ??????.
            if (isDelete) {
                itemMapperBatch.deleteItemPointConfigByItemId(pointConfig.getItemId());
            }


            // ?????? ????????? ?????? ?????? ?????? ??????.
            if (pointConfig.getItemId() != 0) {
                pointConfig.setPointConfigId(sequenceService.getId("OP_POINT_CONFIG"));
                pointConfig.setConfigType("2");		// 2: ???????????????
                pointConfig.setStatusCode("1");
                pointConfig.setPeriodType("1");		// 1: ???????????? (??????)
                pointConfig.setRepeatDay("");		// ?????????
                pointConfig.setCreatedUserId(UserUtils.getUserId());
                pointConfigures.add(pointConfig);
            }

            rowDataCount++;
        } // row


        // ?????? ????????? ?????? ?????? ?????? ??????
        if (!pointConfigures.isEmpty()) {
            itemMapperBatch.insertItemPointConfigListForExcel(pointConfigures);
        }

        // ????????????
        result = "\n<p class=\"sheet\"><span>[" + sheet.getSheetName() + "]</span> Total:" + rowDataCount
            + ", Process:" + (rowDataCount - rowErrorCount)
            + ", Error:" + rowErrorCount + "</p>\n";

        if (rowErrorCount > 0) {
            result += "<p class=\"line\">----------------------------------------------------------------------------</p>\n";
            result += executionLog.toString();
            result += "\n";
        }

        return result;
    }


    /**
     * ?????? ITEM_CHECK
     *
     * ????????????, ????????? ?????? ????????? ????????? ????????? ????????? ?????? ??? ??????.
     * ????????? ?????? ???????????? ?????? ??????.
     * @param sheet
     * @return
     */
    @SuppressWarnings("static-access")
    private String processItemCheckExcelSheet(XSSFSheet sheet) {
        String result = "";
        if (sheet == null) {
            return result;
        }

        StringBuffer executionLog = new StringBuffer();



        // ????????? ?????? INSER VALUE (), () ????????? ?????? ??????.
        // ??????, ?????? ??? ?????? Batch??? ??????
        List<HashMap<String, String>> cellReferences = new ArrayList<>();

        int rowDataCount = 0;			// ????????? ??? (?????????, ?????? ??????)
        int rowErrorCount = 0;			// ?????? ???
        for (Row row : sheet) {
            int rowIndex = row.getRowNum() + 1;

            if (row.getRowNum() < 1) {
                continue;
            }

            // ?????? - ????????? ????????????
            if (row.getRowNum() == 1) {
                for (Cell cell : row) {
                    CellReference cellReference = new CellReference(cell);

                    HashMap<String, String> cellInfo = new HashMap<>();
                    cellInfo.put("title", ShopUtils.getString(cell).replace("(*)", ""));
                    cellInfo.put("colString", cellReference.convertNumToColString(cell.getColumnIndex()));

                    cellReferences.add(cellInfo);
                }
                continue;
            }


            // ?????? ????????? ??? ?????? ?????? ???????????? ????????? SKIP
            if (PoiUtils.isEmptyAllCell(row)) {
                continue;
            }


            String itemUserCode = ShopUtils.getString(row.getCell(0));

            // ??????????????? ???????????? ?????? ??????
            if (itemUserCode == null || "".equals(itemUserCode)) {
                HashMap<String, String> cellReference = cellReferences.get(1);
                cellReference.put("rowIndex", Integer.toString(rowIndex));

                executionLog.append(PoiUtils.log(cellReference, "Product code is empty!"));

                rowErrorCount++;
                continue;
            }

            ExcelItemCheck item = new ExcelItemCheck();

            item.setItemUserCode(itemUserCode);



            // ??????, ????????? ??????
            int cellErrorCount = 0;
            for (Cell cell : row) {
                HashMap<String, String> cellReference = null;
                try {
                    cellReference = cellReferences.get(cell.getColumnIndex());
                } catch (Exception e) {
                    log.warn(" cellReferences.get(cell.getColumnIndex()) : {}", e.getMessage());
                }
                if (cellReference == null) {
                    continue;
                }
                cellReference.put("rowIndex", Integer.toString(rowIndex));

                switch (cell.getColumnIndex()) {

                    case 1: 	// ?????????
                        item.setItemName(ShopUtils.getString(cell));
                        break;

                    case 2: 	// ?????????
                        String teamNumber = ShopUtils.getString(cell);
                        if ("1".equals(teamNumber)) {			// 1. ?????????
                            item.setTeam("esthetic");

                        } else if ("2".equals(teamNumber)) {	// 2. ??????
                            item.setTeam("nail");

                        } else if ("3".equals(teamNumber)) {	// 3. ?????????
                            item.setTeam("matsuge_extension");

                        } else if ("4".equals(teamNumber)) {	// 4. ??????
                            item.setTeam("hair");

                        } else if ("5".equals(teamNumber)) {	// 5. ??????
                            item.setTeam("sale_outlets");

                        } else if ("6".equals(teamNumber)) {	// 6. ?????????
                            item.setTeam("-");
                        }

                        break;

                    case 3: 	// ??????/?????????
                        String displayFlag = ShopUtils.getString(cell).toUpperCase();
                        if ("Y".equals(displayFlag) || "N".equals(displayFlag)) {
                            item.setDisplayFlag(displayFlag);
                        }
                        break;

                    case 4:	// SEO > NO INDEX
                        String noIndexDisplayFlag = ShopUtils.getString(cell).toUpperCase();
                        if ("Y".equals(noIndexDisplayFlag) || "N".equals(noIndexDisplayFlag)) {
                            item.setSeoNoIndexDisplayFlag(noIndexDisplayFlag);
                        }
                        break;

                    case 5:		// ???????????? (0:??????, 2:NEW, 3:SALE, 4:??????)

                        String itemLabel = ShopUtils.getString(cell);
                        if ("0".equals(itemLabel) || "2".equals(itemLabel) || "3".equals(itemLabel) || "5".equals(itemLabel)) {
                            item.setItemLabel(itemLabel);

                            item.setItemNewFlag("N");		// ????????? ?????? (????????? ?????? ??????)
                            if ("2".equals(itemLabel)) {
                                item.setItemNewFlag("Y");
                            }
                        }


                        break;

                    case 6:	// ???????????? (1:????????????, 2:???????????????, 3:?????????)
                        if ("1".equals(ShopUtils.getString(cell)) || "2".equals(ShopUtils.getString(cell)) || "3".equals(ShopUtils.getString(cell))) {
                            item.setItemType(ShopUtils.getString(cell));
                        }
                        break;

                    case 7:	// ????????????
                        String stockQuantity = ShopUtils.getString(cell);
                        item.setStockQuantity("".equals(stockQuantity) ? -1 : Integer.parseInt(stockQuantity));
                        break;

                    case 8:	// ????????????-??????0?????? (1:????????????, 2:????????????)
                        if ("1".equals(ShopUtils.getString(cell)) || "2".equals(ShopUtils.getString(cell))) {
                            item.setItemLabelSoldOut(ShopUtils.getString(cell));
                        }
                        break;

                    case 9:	// ????????? ???????????? - (1:'', 2:date, 3:text)
                        item.setStockScheduleType("");
                        if ("2".equals(ShopUtils.getString(cell))) {
                            item.setStockScheduleType("data");

                        } else if ("3".equals(ShopUtils.getString(cell))) {
                            item.setStockScheduleType("text");

                        }
                        break;

                    case 10:	// ???????????????
                        item.setStockScheduleDate(ShopUtils.getString(cell));
                        break;

                    case 11:	// ???????????????
                        item.setStockScheduleText(ShopUtils.getString(cell));
                        break;

                    default:
                        break;
                }

            } // cell

            // ?????? ?????? ????????? Cell??? ????????? ???????????? ????????? ?????? ?????? ???????????? ???????????? ??????.
            if (cellErrorCount > 0) {
                rowErrorCount++;
                continue;
            }


            // ??????.
            itemMapperBatch.updateItemCheckForExcel(item);


            rowDataCount++;

        } // row



        // ????????????
        result = "\n<p class=\"sheet\"><span>[" + sheet.getSheetName() + "]</span> Total:" + rowDataCount
            + ", Process:" + (rowDataCount - rowErrorCount)
            + ", Error:" + rowErrorCount + "</p>\n";

        if (rowErrorCount > 0) {
            result += "<p class=\"line\">----------------------------------------------------------------------------</p>\n";
            result += executionLog.toString();
            result += "\n";
        }

        return result;

    }


    /**
     * ?????? ITEM_KEYWORD  ???????????? ????????? ????????????..
     *
     * @param sheet
     * @return
     */
    @SuppressWarnings("static-access")
    private String processItemKeywordExcelSheet(XSSFSheet sheet) {
        String result = "";
        if (sheet == null) {
            return result;
        }

        StringBuffer executionLog = new StringBuffer();



        // ????????? ?????? INSER VALUE (), () ????????? ?????? ??????.
        // ??????, ?????? ??? ?????? Batch??? ??????
        List<HashMap<String, String>> cellReferences = new ArrayList<>();

        int rowDataCount = 0;			// ????????? ??? (?????????, ?????? ??????)
        int rowErrorCount = 0;			// ?????? ???
        for (Row row : sheet) {
            int rowIndex = row.getRowNum() + 1;

            if (row.getRowNum() < 1) {
                continue;
            }

            // ?????? - ????????? ????????????
            if (row.getRowNum() == 1) {
                for (Cell cell : row) {
                    CellReference cellReference = new CellReference(cell);

                    HashMap<String, String> cellInfo = new HashMap<>();
                    cellInfo.put("title", ShopUtils.getString(cell).replace("(*)", ""));
                    cellInfo.put("colString", cellReference.convertNumToColString(cell.getColumnIndex()));

                    cellReferences.add(cellInfo);
                }
                continue;
            }


            // ?????? ????????? ??? ?????? ?????? ???????????? ????????? SKIP
            if (PoiUtils.isEmptyAllCell(row)) {
                continue;
            }


            String itemUserCode = ShopUtils.getString(row.getCell(0));

            // ??????????????? ???????????? ?????? ??????
            if (itemUserCode == null || "".equals(itemUserCode)) {
                HashMap<String, String> cellReference = cellReferences.get(1);
                cellReference.put("rowIndex", Integer.toString(rowIndex));

                executionLog.append(PoiUtils.log(cellReference, "Product code is empty!"));

                rowErrorCount++;
                continue;
            }

            ExcelItemKeyword item = new ExcelItemKeyword();

            item.setItemUserCode(itemUserCode);



            // ??????, ????????? ??????
            int cellErrorCount = 0;
            for (Cell cell : row) {
                HashMap<String, String> cellReference = null;
                try {
                    cellReference = cellReferences.get(cell.getColumnIndex());
                } catch (Exception e) {
                    log.warn(" cellReferences.get(cell.getColumnIndex()) : {}", e.getMessage());
                }
                if (cellReference == null) {
                    continue;
                }
                cellReference.put("rowIndex", Integer.toString(rowIndex));

                switch (cell.getColumnIndex()) {

                    case 1: 	// ???????????? ?????????.
                        item.setItemKeyword(ShopUtils.getString(cell));
                        break;


                    default:
                        break;
                }

            } // cell

            // ?????? ?????? ????????? Cell??? ????????? ???????????? ????????? ?????? ?????? ???????????? ???????????? ??????.
            if (cellErrorCount > 0) {
                rowErrorCount++;
                continue;
            }


            // ??????.
            itemMapperBatch.updateItemKeywordForExcel(item);


            rowDataCount++;

        } // row



        // ????????????
        result = "\n<p class=\"sheet\"><span>[" + sheet.getSheetName() + "]</span> Total:" +  (rowDataCount + rowErrorCount)
            + ", Process:" + rowDataCount
            + ", Error:" + rowErrorCount + "</p>\n";

        if (rowErrorCount > 0) {
            result += "<p class=\"line\">----------------------------------------------------------------------------</p>\n";
            result += executionLog.toString();
            result += "\n";
        }

        return result;

    }

    @Override
    public List<HashMap<String, Object>> getIndexList(
        SearchIndexParam searchIndexParam) {
        return itemMapper.getIndexList(searchIndexParam);
    }


    @Override
    public List<HashMap<String, Object>> getSubIndexList(SearchIndexParam searchIndexParam) {
        if ("".equals(searchIndexParam.getStartIndex())) {
            return searchIndexParam.getSubIndexList();
        }

        return itemMapper.getSubIndexList(searchIndexParam);
    }


    @Override
    public List<ItemIndex> getItemIndexList(SearchIndexParam searchIndexParam) {
        return itemMapper.getItemIndexList(searchIndexParam);
    }

    @Override
    public List<Item> getTodayItemList(ItemParam itemParam) {
        return itemMapper.getTodayItemList(itemParam);
    }


    @Override
    public void updateItemHitsByItemId(int itemId) {
        itemMapper.updateItemHitsByItemId(itemId);
    }

    @Override
    public List<Item> getItemListForGroupBanner(String value) {

        if (StringUtils.isEmpty(value)) {
            return new ArrayList<Item>();
        }

        HashMap<String, String[]> ids = new HashMap<String, String[]>();
        String[] idArray = value.split(",");

        ids.put("ids", idArray);

        return itemMapper.getItemListForGroupBanner(ids);
    }

    @Override
    public List<saleson.shop.categories.domain.Group> getGroupItemsForGroupBanner(
        List<saleson.shop.categories.domain.Group> shopCategoryGroups) {

        List<saleson.shop.categories.domain.Group> groupParam = new ArrayList<>();

        // ????????? ?????? ??????.
        for (saleson.shop.categories.domain.Group group : shopCategoryGroups) {
            if (!StringUtils.isEmpty(group.getItemList())) {
                groupParam.add(group);
            }
        }
        return itemMapper.getGroupItemsForGroupBanner(groupParam);
    }


    @Override
    public List<ItemNotice> getItemNoticeCodes() {
        return itemMapper.getItemNoticeCodes();
    }

    @Override
    public List<ItemNotice> getItemNoticeListByCode(String itemNoticeCode) {
        return itemMapper.getItemNoticeListByCode(itemNoticeCode);
    }

    @Override
    public void updateShipmentPrice(Shipment shipment) {
        itemMapper.updateShipmentPrice(shipment);
    }

    @Override
    public void updateShipment(Shipment shipment) {
        itemMapper.updateShipment(shipment);
    }

    @Override
    public void updateShipmentReturn(ShipmentReturn shipmentReturn) {
        itemMapper.updateShipmentReturn(shipmentReturn);
    }

    @Override
    public List<Item> getItemCountForMain(long sellerId){
        return itemMapper.getItemCountForMain(sellerId);
    }

    @Override
    public void insertItemSaleEdit(ItemSaleEdit itemSaleEdit) {
        itemMapper.insertItemSaleEdit(itemSaleEdit);
    };

    @Override
    public List<ItemSaleEdit> getItemSaleEdit(ItemSaleEditParam itemSaleEditParam) {
        return itemMapper.getItemSaleEdit(itemSaleEditParam);
    };

    @Override
    public int getItemSaleEditCountByParam(ItemSaleEditParam itemSaleEditParam){
        return itemMapper.getItemSaleEditCountByParam(itemSaleEditParam);
    };

    @Override
    public void deleteItemSaleEdit(ItemSaleEdit itemSaleEdit){
        itemMapper.deleteItemSaleEdit(itemSaleEdit);
    };

    @Override
    public void updateSaleEdit(ItemSaleEdit itemSaleEdit){
        itemMapper.updateSaleEdit(itemSaleEdit);
    };

    @Override
    public void updateSaleEditStatus(ItemSaleEdit itemSaleEdit){
        itemMapper.updateSaleEditStatus(itemSaleEdit);
    };

    @Override
    public ItemSaleEdit getItemSaleEditByParam(ItemSaleEditParam itemSaleEditParam){
        return itemMapper.getItemSaleEditByParam(itemSaleEditParam);
    };

    @Override
    public void updateItemPrice(ItemSaleEdit itemSaleEdit){
        itemMapper.updateItemPrice(itemSaleEdit);
    };

    @Override
    public Item getItemByItemSaleEdit(ItemSaleEdit itemSaleEdit){
        return itemMapper.getItemByItemSaleEdit(itemSaleEdit);
    };

    public List<ChosenItem> getChosenItemList(List<String> list){
        return itemMapper.getChosenItemList(list);
    }

    @Override
    public List<ChosenItem> getSearchItemList(ChosenItem chosenItem) {

        return itemMapper.getSearchItemList(chosenItem);
    }

    @Override
    public Integer getItemIdByItemUserCode(String itemUserCode){
        return itemMapper.getItemIdByItemUserCode(itemUserCode);
    };

    //kye ??????
    @Override
    public int getItemNonregisteredReviewCount(ItemParam itemParam) {
        return itemMapper.getItemNonregisteredReviewCount(itemParam);
    }

    //kye ??????
    @Override
    public List<OrderItem> getItemNonregisteredReviewList(ItemParam itemParam) {
        return itemMapper.getItemNonregisteredReviewList(itemParam);
    }

    @Override
    public void updateItemOptionSoldout() {
        // ?????? ?????? ???????????? ??????.
        itemMapper.deleteItemOptionSoldout();

        // ?????? ?????? ???????????? ??????
        itemMapper.insertItemOptionSoldout();
    }

    @Override
    public List<ItemRelation> getItemRelationsByItemId(String relationItemDisplayType, int itemId) {

        if (StringUtils.isEmpty(relationItemDisplayType)) {
            relationItemDisplayType = "1";
        }

        ItemParam itemParam = ItemUtils.bindItemParam(new ItemParam());
        itemParam.setItemId(itemId);
        List<ItemRelation> itemRelations = itemMapper.getItemRelationList(itemParam);

        // ???????????? ?????? ????????? ?????? ?????? ???????????? ?????? 5??? ??????.
        if ("1".equals(relationItemDisplayType) && itemRelations.size() == 0) {
            itemRelations = itemMapper.getItemRelationRandomList(itemParam);
        }

        return itemRelations;
    }

    @Override
    public List<ItemAddition> getItemAdditionsByItemId(int itemId) {
        ItemParam itemParam = ItemUtils.bindItemParam(new ItemParam());
        itemParam.setItemId(itemId);
        return itemMapper.getItemAdditionList(itemParam);
    }

    @Override
    public BenefitInfo getBenefitInfoByItemId(int itemId) throws Exception{

        BenefitInfo benefitInfo = new BenefitInfo();

        Config shopConfig = ShopUtils.getConfig();

        // ?????? ????????? ??????
        OrderPointParam orderPointParam = new OrderPointParam();
        orderPointParam.setItemId(itemId);
        orderPointParam.setRepeatDayEndTime(shopConfig.getRepeatDayEndTime());
        orderPointParam.setRepeatDayStartTime(shopConfig.getRepeatDayStartTime());

        PointPolicy pointPolicy =  pointService.getPointPolicyByOrderPointParam(orderPointParam);

        benefitInfo.setPointPolicy(pointPolicy);
        benefitInfo.setCardBenefits(cardBenefitsService.getTodayCardBenefits(DateUtils.getToday()));

        benefitInfo.setItemEarnPoint(new ItemEarnPoint(pointPolicy, getItemBy(itemId)));

        return benefitInfo;
    }

    @Override
    public CustomerInfo getCustomerInfoByItemId(int itemId) throws Exception{

        // ????????????
        ItemParam itemReviewParam = new ItemParam();
        itemReviewParam.setItemId(itemId);
        itemReviewParam.setConditionType("FRONT_ITEM_DETAIL");

        int reviewCount = getItemReviewCountByParam(itemReviewParam);

        // QNA ?????? ????????????
        QnaParam qnaParam = new QnaParam();
        qnaParam.setQnaType(Qna.QNA_GROUP_TYPE_ITEM);
        qnaParam.setItemId(itemId);
        int qnaCount = qnaService.getQnaListCountByParam(qnaParam);

        CustomerInfo customerInfo = new CustomerInfo();

        customerInfo.setQnaCount(qnaCount);
        customerInfo.setReviewCount(reviewCount);

        return customerInfo;
    }

    @Override
    public void setDownloadableCouponListPagination(UserCouponParam userCouponParam) {
        // LIMIT ?????? ???????????? ????????? ?????? ??????
        if (userCouponParam.getLimit() <= 0) {

            int count  = couponService.getUserDownloadableCouponListCountByParam(userCouponParam);

            Pagination pagination = Pagination.getInstance(count, userCouponParam.getItemsPerPage());

            ShopUtils.setPaginationInfo(pagination, userCouponParam.getConditionType(), userCouponParam.getPage());

            userCouponParam.setPagination(pagination);

        }
    }

    @Override
    public boolean saveItemReviewLike(HttpServletRequest request, int itemReviewId) {

        boolean saveFlag = false;
        String ip = saleson.common.utils.CommonUtils.getClientIp(request);
        long userId = UserUtils.getUserId();

        ItemReviewLikeDto dto = new ItemReviewLikeDto();
        dto.setItemReviewId(itemReviewId);

        if (UserUtils.isUserLogin()) {
            dto.setUserId(userId);
        } else {
            dto.setIp(ip);
        }

        saveFlag = itemReviewLikeRepository.count(dto.getPredicate()) == 0;

        if (saveFlag) {
            ItemReviewLike like = new ItemReviewLike();

            like.setItemReviewId(itemReviewId);
            like.setIp(ip);
            like.setUserId(userId);

            itemReviewLikeRepository.save(like);
            itemMapper.updateItemReviewLikeCount(itemReviewId);

            return true;
        }

        return false;
    }

    @Override
    public void updateItemLabelValue(ItemParam itemParam) {
        itemMapper.updateItemLabelValue(itemParam);
    }

    @Override
    public void deleteItemLabelValue(ItemParam itemParam) {
        itemMapper.deleteItemLabelValue(itemParam);
    }

    @Override
    public List<ItemOption> getItemOptionList(Item item, boolean isManager) {
        List<ItemOption> itemOptionList = item.getItemOptions();

        if (itemOptionList == null || itemOptionList.isEmpty()) {
            if (isManager) {
                itemOptionList = itemMapper.getItemOptionListForManager(item.getItemId());
            } else {
                itemOptionList = itemMapper.getItemOptionList(item.getItemId());
            }
        }

        // ??????????????? :: ERP ??????????????? ?????? ?????? ?????? START!!
        if ("C".equals(item.getItemOptionType()) && itemOptionList != null && !itemOptionList.isEmpty()) {

            // 1. ?????? ??????????????? ???????????? ?????? - ERP ??????????????? ???????????? ??????, ?????? ????????? ???????????? ??????
            List<String> erpItemUserCodes =
                itemOptionList.stream()
                    .filter(itemOption -> !StringUtils.isEmpty(itemOption.getErpItemCode()))
                    .map(itemOption -> itemOption.getErpItemCode())
                    .collect(Collectors.toList());

            // 2. ?????? ?????????(query asc ??????), ?????? ????????????(java lambda ??????)?????? ???????????? ????????? ?????? (???????????? ?????? + ???????????? ????????? ?????? ??????)
            Map<String, List<ItemOption>> itemOptionMap =
                itemOptionList.stream()
                    .sorted(Comparator.comparing(ItemOption::getOptionOrdering))
                    .collect(Collectors.groupingBy(ItemOption::getOptionName1, LinkedHashMap::new, Collectors.toList()));

            // 3. ??????????????? ????????? ?????? ?????? ????????? ??????
            if (erpItemUserCodes != null && !erpItemUserCodes.isEmpty()) {
                ItemParam itemParam = new ItemParam();
                itemParam.setItemUserCodes(erpItemUserCodes);

                List<Item> itemList = itemMapper.getItemList(itemParam);

                // 4. ?????? ?????? ????????? <-> ?????? ????????? ?????? ?????? (????????????, ??????, ????????????, ????????????, ????????????)
                if (itemList != null) {
                    for (String optionName1 : itemOptionMap.keySet()) {
                        int index = 0;

                        int firstCostPrice = 0;
                        int firstQuantity = 1;

                        // 4-1. ?????? ????????? ???????????? & ??????????????? ???????????? ????????? ???????????? ?????? ??? ?????? ????????? ??????
                        for (ItemOption itemOption : itemOptionMap.get(optionName1)) {
                            if (!StringUtils.isEmpty(itemOption.getOptionStockCode())) {
                                for (Item itemInfo : itemList) {
                                    if (!StringUtils.isEmpty(itemInfo.getStockCode())
                                            && itemInfo.getItemUserCode().equals(itemOption.getErpItemCode()) && itemInfo.getStockCode().equals(itemOption.getOptionStockCode())) {

                                        itemOption.setOptionCostPrice(itemInfo.getSalePrice());                 // ?????? ??????
                                        itemOption.setOptionStockFlag(itemInfo.getStockFlag());                 // ?????? ????????????
                                        itemOption.setOptionStockQuantity(itemInfo.getStockQuantity());         // ?????? ????????????
                                        itemOption.setOptionSoldOutFlag(itemInfo.isItemSoldOut() ? "Y" : "N");  // ?????? ????????????

                                        break;
                                    }
                                }
                            }

                            // 4-2. ?????? ???????????? ??????: {(???????????? ?????? * ????????????) - (1????????? ?????? * 1????????? ????????????)}
                            if (index == 0) {
                                itemOption.setOptionPrice(0);

                                firstCostPrice = itemOption.getOptionCostPrice();
                                firstQuantity = itemOption.getOptionQuantity();
                            } else {
                                itemOption.setOptionPrice((itemOption.getOptionCostPrice() * itemOption.getOptionQuantity()) - (firstCostPrice * firstQuantity));
                            }

                            index++;
                        }
                    }
                }
            }

            // 5. ???????????? ???????????? ???????????? ?????? ?????? ???????????? ??????
            itemOptionList = itemOptionMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        }

        return itemOptionList;
    }
}

package saleson.shop.item.domain;

public class ExcelItemAddition {
    private String itemUserCode;
    private String itemName;
    private String additionItemUserCode;
    private String additionItemName;
    private int ordering;

    public String getItemUserCode() {
        return itemUserCode;
    }

    public void setItemUserCode(String itemUserCode) {
        this.itemUserCode = itemUserCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAdditionItemUserCode() {
        return additionItemUserCode;
    }

    public void setAdditionItemUserCode(String additionItemUserCode) {
        this.additionItemUserCode = additionItemUserCode;
    }

    public String getAdditionItemName() {
        return additionItemName;
    }

    public void setAdditionItemName(String additionItemName) {
        this.additionItemName = additionItemName;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }
}

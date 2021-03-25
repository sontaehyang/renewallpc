package saleson.common.web;

public class Paging {
	private String dbVendor = "mysql";
	private int totalItems;
	private int itemsPerPage = 10;
	private int currentPage = 1;
	private boolean autoHide = false;
	private int totalPages;
	private int startRownum;
	private int endRownum;
	private int startLimit;
	private int endLimit;

	public Paging(int totalItems) {
		this.totalItems = totalItems;
		this.calculateRownum();
	}
	public Paging(int totalItems, int itemPerPage) {
		this(totalItems);
		this.itemsPerPage = itemPerPage;
		this.calculateRownum();
	}
	public Paging(int totalItems, int itemPerPage, int currentPage) {
		this(totalItems, itemPerPage);
		this.currentPage = currentPage;
		this.calculateRownum();
	}

	private void calculateRownum() {
		String db = this.dbVendor == null ? "oracle" : this.dbVendor;
		if (db.equals("oracle")) {
			this.startRownum = (this.currentPage - 1) * this.itemsPerPage + 1;
			this.endRownum = this.currentPage * this.itemsPerPage;
		} else if (db.equals("mysql")) {
			this.startRownum = (this.currentPage - 1) * this.itemsPerPage;
			this.endRownum = this.itemsPerPage;
		} else if (db.equals("mssql")) {
			this.startRownum = (this.currentPage - 1) * this.itemsPerPage + 1;
			this.endRownum = this.currentPage * this.itemsPerPage;
		}
	}

	public int getTotalItems() {
		return this.totalItems;
	}

	public int getItemsPerPage() {
		return this.itemsPerPage;
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
		this.calculateRownum();
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		this.calculateRownum();
	}

	public int getTotalPages() {
		return this.totalPages = (int) Math.ceil((double)this.totalItems / (double)this.itemsPerPage);
	}

	public int getFirstPage() {
		return this.currentPage == 1 ? 0 : 1;
	}

	public int getLastPage() {
		return this.currentPage >= this.totalPages ? 0 : this.totalPages;
	}

	public int getPreviousPage() {
		return this.currentPage > 1 ? this.currentPage - 1 : 0;
	}

	public int getNextPage() {
		return this.currentPage < this.totalPages ? this.currentPage + 1 : 0;
	}

	public int getItemNumber() {
		return this.getTotalItems() - this.getItemsPerPage() * (this.getCurrentPage() - 1) + 1;
	}

	public int getNumber() {
		return this.getItemsPerPage() * (this.getCurrentPage() - 1);
	}

	public int getStartRownum() {
		return this.startRownum;
	}

	public int getEndRownum() {
		return this.endRownum;
	}

	public void setStartRownum(int startRownum) {
		this.startRownum = startRownum;
	}

	public void setEndRownum(int endRownum) {
		this.endRownum = endRownum;
	}

}

package customView;

public class LayoutFilterModel {
    private String placeHolderText;
    private int searchImage;
    private int filterImage;
    private int sortImage;

    public LayoutFilterModel(String placeHolderText, int searchImage, int filterImage
            , int sortImage) {
        this.placeHolderText = placeHolderText;
        this.searchImage = searchImage;
        this.filterImage = filterImage;
        this.sortImage = sortImage;
    }

    public String getPlaceHolderText() {
        return placeHolderText;
    }

    public void setPlaceHolderText(String placeHolderText) {
        this.placeHolderText = placeHolderText;
    }

    public int getSearchImage() {
        return searchImage;
    }

    public void setSearchImage(int searchImage) {
        this.searchImage = searchImage;
    }

    public int getFilterImage() {
        return filterImage;
    }

    public void setFilterImage(int filterImage) {
        this.filterImage = filterImage;
    }

    public int getSortImage() {
        return sortImage;
    }

    public void setSortImage(int sortImage) {
        this.sortImage = sortImage;
    }
}

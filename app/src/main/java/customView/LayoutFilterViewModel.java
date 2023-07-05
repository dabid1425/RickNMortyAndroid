package customView;

public class LayoutFilterViewModel {
    private LayoutFilterModel filterModel;
    public LayoutFilterViewModel(LayoutFilterModel filterModel){
        this.filterModel = filterModel;
    }

    public LayoutFilterModel getFilterModel() {
        return filterModel;
    }
    public String getPlaceHolderText() {
        return filterModel.getPlaceHolderText();
    }

    public void setPlaceHolderText(String placeHolderText) {
        this.filterModel.setPlaceHolderText(placeHolderText);
    }

    public int getSearchImage() {
        return this.filterModel.getSearchImage();
    }

    public void setSearchImage(int searchImage) {
        this.filterModel.setSearchImage(searchImage);
    }

    public int getFilterImage() {
        return this.filterModel.getFilterImage();
    }

    public void setFilterImage(int filterImage) {
        this.filterModel.setFilterImage(filterImage);
    }

    public int getSortImage() {
        return this.filterModel.getSortImage();
    }

    public void setSortImage(int sortImage) {
        this.filterModel.setSortImage(sortImage);
    }
}

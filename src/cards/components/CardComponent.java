package cards.components;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import model.Model;

/*
 * Represents a component on a card template
 */
public abstract class CardComponent extends Model implements Comparable<CardComponent>, Serializable {
	
	private static final long serialVersionUID = 1L;

	// Identifier
	protected String name;
	
	protected Integer x;
	
	protected Integer y;

	protected Integer z;
	
	protected Integer width;
	
	protected Integer height;
	
	protected boolean selected;
	
	// Is this the card name?
	protected Boolean isCardName;
	
	protected long createTime;
	
	protected Integer lines;
	
	protected transient BufferedImage img;
	
	protected Boolean editable;
	
	public CardComponent(){
		this("", 0, 0, 0, 1, 1);
	}
	
	public CardComponent(String iName){
		this(iName, 0, 0, 0, 1, 1);
	}
	
	public CardComponent(String iName, int iX, int iY, int iZ, int iWidth, int iHeight){
		name = iName;
		x = iX;
		y = iY;
		z = iZ;
		width = iWidth < 1 ? 1 : iWidth;
		height = iHeight < 1 ? 1 : iHeight;
		isCardName = false;
		selected = false;
		editable = true;
		lines = 0;
		createTime = System.currentTimeMillis();
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String newName){
		if(newName == name) return;
		name = newName;
		updateImage();
	}
	
	public Integer getX(){
		return x;
	}
	
	public Integer setX(Integer newX){
		if(newX == x) return x;
		x = newX;
		return x;
	}
	
	public Integer getY(){
		return y;
	}
	
	public Integer setY(Integer newY){
		if(newY == y) return y;
		y = newY;
		return y;
	}
	
	public Integer getZ(){
		return z;
	}
	
	public Integer setZ(Integer newZ){
		if(newZ == z) return z;
		z = newZ;
		return z;
	}

	public Integer getWidth(){
		return width;
	}
	
	public Integer setWidth(Integer newWidth){
		if(newWidth == width || newWidth < 1) return width;
		width = newWidth;
		updateImage();
		return width;
	}
	
	public Integer getHeight(){
		return height;
	}
	
	public Integer setHeight(Integer newHeight){
		if(newHeight == height || newHeight < 1) return height;
		height = newHeight;
		updateImage();
		return height;
	}
	
	public boolean getSelected(){
		return selected;
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public void setSelected(boolean isSelected){
		selected = isSelected;
	}
	
	public BufferedImage getImage(){
		return img;
	}
	
	public abstract BufferedImage getImage(Object data);
	
	public abstract void updateImage();
	
	public boolean setEditable(Boolean iEditable){
		return editable = iEditable;
	}
	
	public boolean isEditable(){
		return editable;
	}
	
	public boolean getEditable(){
		return editable;
	}
	
	public Boolean isCardName(){
		return isCardName;
	}
	
	public Boolean getIsCardName(){
		return isCardName;
	}

	public void setIsCardName(Boolean isItCardName){
		isCardName = isItCardName;
	}

	public int getLines(){
		return 0;
	}
	
	public int setLines(Integer newLines){
		return -1;
	}
	
	public long getCreateTime(){
		return createTime;
	}
	
	public boolean containsPoint(int xx, int yy){
		return xx >= x && yy >= y && xx <= x + width && yy <= y + height;
	}
	
	/*
	 * Return < 0 if this is less than other, > 0 if this is greater than other, 0 is they are equal
	 * Sort by Z descending, Y ascending, X ascending, timestamp descending
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
    public int compareTo(CardComponent other){
        if(z > other.z) return 1;
        if(z < other.z) return -1;
        if(y > other.y) return -1;
        if(y < other.y) return 1;
        if(x > other.x) return -1;
        if(x < other.x) return 1;
        if(createTime > other.createTime) return 1;
        if(createTime < other.createTime) return -1;
        return 0;
    }
	
	public abstract String getTypeString();
}
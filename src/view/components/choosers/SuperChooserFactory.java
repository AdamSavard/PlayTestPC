package view.components.choosers;

import constants.Constants;

public class SuperChooserFactory {

	public static JAbstractSuperChooser getChooser(String type,  int ... options){
		if(Constants.TEXT_TYPE.equals(type)){
			if(options != null && options.length < 2) return null;
			return new JSuperTextChooser(options[0], options[1]);
		}
		if(Constants.LINE_TYPE.equals(type)){
			return new JSuperColorChooser();
		}
		if(Constants.RECT_TYPE.equals(type)){
			JSuperMultiChooser twoColors = new JSuperMultiChooser();
			twoColors.addChooser(new JSuperColorChooser());
			twoColors.addChooser(new JSuperColorChooser());
			return twoColors;
		}
		if(Constants.IMAGE_TYPE.equals(type)){
			return new JSuperImageChooser();
		}
		return null;
	}
}

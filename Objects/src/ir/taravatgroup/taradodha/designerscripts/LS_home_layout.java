package ir.taravatgroup.taradodha.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_home_layout{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 3;BA.debugLine="ImageView1.HorizontalCenter=50%x"[home_layout/General script]
views.get("imageview1").vw.setLeft((int)((50d / 100 * width) - (views.get("imageview1").vw.getWidth() / 2)));
//BA.debugLineNum = 4;BA.debugLine="ImageView1.VerticalCenter=35%y"[home_layout/General script]
views.get("imageview1").vw.setTop((int)((35d / 100 * height) - (views.get("imageview1").vw.getHeight() / 2)));

}
}
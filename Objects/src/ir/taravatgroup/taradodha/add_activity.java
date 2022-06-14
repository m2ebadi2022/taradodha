package ir.taravatgroup.taradodha;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class add_activity extends Activity implements B4AActivity{
	public static add_activity mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "ir.taravatgroup.taradodha", "ir.taravatgroup.taradodha.add_activity");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (add_activity).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "ir.taravatgroup.taradodha", "ir.taravatgroup.taradodha.add_activity");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ir.taravatgroup.taradodha.add_activity", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (add_activity) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (add_activity) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return add_activity.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        if (!dontPause)
            BA.LogInfo("** Activity (add_activity) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (add_activity) Pause event (activity is not paused). **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        if (!dontPause) {
            processBA.setActivityPaused(true);
            mostCurrent = null;
        }

        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            add_activity mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (add_activity) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_tozih = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_date = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_khoroj_time = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_vorod_time = null;
public b4a.example3.customlistview _cuslistv_data = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_show_alltime = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_date_item = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_vorod_item = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_khoroj_item = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_tozih_item = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_edit_item = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_show_time = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_all_edit = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_date_edit = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_tozih_edit = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_khoroj_time_edit = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_vorod_time_edit = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_show_time_edit = null;
public static int _current_id = 0;
public anywheresoftware.b4a.agraham.dialogs.InputDialog.TimeDialog _dialog_tim = null;
public b4a.example.dateutils _dateutils = null;
public ir.taravatgroup.taradodha.main _main = null;
public ir.taravatgroup.taradodha.starter _starter = null;
public ir.taravatgroup.taradodha.home_activity _home_activity = null;
public ir.taravatgroup.taradodha.list_activity _list_activity = null;
public ir.taravatgroup.taradodha.myfunc _myfunc = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 48;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 50;BA.debugLine="Activity.LoadLayout(\"add_layout\")";
mostCurrent._activity.LoadLayout("add_layout",mostCurrent.activityBA);
 //BA.debugLineNum = 51;BA.debugLine="et_date.Text=Main.prsianDate.PersianShortDate";
mostCurrent._et_date.setText(BA.ObjectToCharSequence(mostCurrent._main._prsiandate /*com.b4a.manamsoftware.PersianDate.ManamPersianDate*/ .getPersianShortDate()));
 //BA.debugLineNum = 53;BA.debugLine="fill_list(\"1401\",\"03\")";
_fill_list("1401","03");
 //BA.debugLineNum = 54;BA.debugLine="dialog_tim.Is24Hours=True";
mostCurrent._dialog_tim.setIs24Hours(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 104;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 106;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 100;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 102;BA.debugLine="End Sub";
return "";
}
public static String  _fill_list(String _sal,String _mah) throws Exception{
anywheresoftware.b4a.objects.collections.List _list1 = null;
int _all_tim_h = 0;
int _all_tim_m = 0;
int _i = 0;
String[] _list2 = null;
 //BA.debugLineNum = 57;BA.debugLine="Sub fill_list(sal As String , mah As String)";
 //BA.debugLineNum = 58;BA.debugLine="Dim list1 As List";
_list1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 59;BA.debugLine="list1.Initialize";
_list1.Initialize();
 //BA.debugLineNum = 60;BA.debugLine="list1=myFunc.get_data(sal,mah)";
_list1 = mostCurrent._myfunc._get_data /*anywheresoftware.b4a.objects.collections.List*/ (mostCurrent.activityBA,_sal,_mah);
 //BA.debugLineNum = 67;BA.debugLine="cusListV_data.Clear";
mostCurrent._cuslistv_data._clear();
 //BA.debugLineNum = 69;BA.debugLine="Dim all_tim_h As Int=0";
_all_tim_h = (int) (0);
 //BA.debugLineNum = 70;BA.debugLine="Dim all_tim_m As Int=0";
_all_tim_m = (int) (0);
 //BA.debugLineNum = 74;BA.debugLine="For i=0 To list1.Size-1";
{
final int step7 = 1;
final int limit7 = (int) (_list1.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit7 ;_i = _i + step7 ) {
 //BA.debugLineNum = 75;BA.debugLine="p = xui.CreatePanel(\"p\")";
mostCurrent._p = mostCurrent._xui.CreatePanel(processBA,"p");
 //BA.debugLineNum = 76;BA.debugLine="p.SetLayoutAnimated(0, 0, 0, 95%x, 120dip)";
mostCurrent._p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (120)));
 //BA.debugLineNum = 77;BA.debugLine="p.LoadLayout(\"irem_data\")";
mostCurrent._p.LoadLayout("irem_data",mostCurrent.activityBA);
 //BA.debugLineNum = 78;BA.debugLine="Dim list2() As String=list1.Get(i)";
_list2 = (String[])(_list1.Get(_i));
 //BA.debugLineNum = 80;BA.debugLine="cusListV_data.Add(p,list2(0))";
mostCurrent._cuslistv_data._add(mostCurrent._p,(Object)(_list2[(int) (0)]));
 //BA.debugLineNum = 81;BA.debugLine="lbl_edit_item.Tag=list2(0)";
mostCurrent._lbl_edit_item.setTag((Object)(_list2[(int) (0)]));
 //BA.debugLineNum = 83;BA.debugLine="lbl_date_item.Text=list2(1)";
mostCurrent._lbl_date_item.setText(BA.ObjectToCharSequence(_list2[(int) (1)]));
 //BA.debugLineNum = 84;BA.debugLine="lbl_vorod_item.Text=list2(2)";
mostCurrent._lbl_vorod_item.setText(BA.ObjectToCharSequence(_list2[(int) (2)]));
 //BA.debugLineNum = 85;BA.debugLine="lbl_khoroj_item.Text=list2(3)";
mostCurrent._lbl_khoroj_item.setText(BA.ObjectToCharSequence(_list2[(int) (3)]));
 //BA.debugLineNum = 86;BA.debugLine="lbl_tozih_item.Text=list2(6)";
mostCurrent._lbl_tozih_item.setText(BA.ObjectToCharSequence(_list2[(int) (6)]));
 //BA.debugLineNum = 88;BA.debugLine="all_tim_h=all_tim_h+list2(4)";
_all_tim_h = (int) (_all_tim_h+(double)(Double.parseDouble(_list2[(int) (4)])));
 //BA.debugLineNum = 89;BA.debugLine="all_tim_m=all_tim_m+list2(5)";
_all_tim_m = (int) (_all_tim_m+(double)(Double.parseDouble(_list2[(int) (5)])));
 }
};
 //BA.debugLineNum = 93;BA.debugLine="If(all_tim_m>59)Then";
if ((_all_tim_m>59)) { 
 //BA.debugLineNum = 94;BA.debugLine="all_tim_h=all_tim_h+(all_tim_m / 60)";
_all_tim_h = (int) (_all_tim_h+(_all_tim_m/(double)60));
 //BA.debugLineNum = 95;BA.debugLine="all_tim_m=all_tim_m Mod 60";
_all_tim_m = (int) (_all_tim_m%60);
 };
 //BA.debugLineNum = 98;BA.debugLine="lbl_show_allTime.Text=all_tim_h &\"ساعت و \"&all_ti";
mostCurrent._lbl_show_alltime.setText(BA.ObjectToCharSequence(BA.NumberToString(_all_tim_h)+"ساعت و "+BA.NumberToString(_all_tim_m)+"دقیقه "));
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private et_tozih As EditText";
mostCurrent._et_tozih = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private et_date As EditText";
mostCurrent._et_date = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private lbl_khoroj_time As Label";
mostCurrent._lbl_khoroj_time = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private lbl_vorod_time As Label";
mostCurrent._lbl_vorod_time = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private cusListV_data As CustomListView";
mostCurrent._cuslistv_data = new b4a.example3.customlistview();
 //BA.debugLineNum = 21;BA.debugLine="Private lbl_show_allTime As Label";
mostCurrent._lbl_show_alltime = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private xui As XUI";
mostCurrent._xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 24;BA.debugLine="Dim p As B4XView";
mostCurrent._p = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private lbl_date_item As Label";
mostCurrent._lbl_date_item = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private lbl_vorod_item As Label";
mostCurrent._lbl_vorod_item = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private lbl_khoroj_item As Label";
mostCurrent._lbl_khoroj_item = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private lbl_tozih_item As Label";
mostCurrent._lbl_tozih_item = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private lbl_edit_item As Label";
mostCurrent._lbl_edit_item = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private lbl_show_time As Label";
mostCurrent._lbl_show_time = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private pan_all_edit As Panel";
mostCurrent._pan_all_edit = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private et_date_edit As EditText";
mostCurrent._et_date_edit = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private et_tozih_edit As EditText";
mostCurrent._et_tozih_edit = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private lbl_khoroj_time_edit As Label";
mostCurrent._lbl_khoroj_time_edit = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private lbl_vorod_time_edit As Label";
mostCurrent._lbl_vorod_time_edit = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private lbl_show_time_edit As Label";
mostCurrent._lbl_show_time_edit = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Dim current_id As Int=0";
_current_id = (int) (0);
 //BA.debugLineNum = 43;BA.debugLine="Dim dialog_tim As TimeDialog";
mostCurrent._dialog_tim = new anywheresoftware.b4a.agraham.dialogs.InputDialog.TimeDialog();
 //BA.debugLineNum = 46;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_back_click() throws Exception{
 //BA.debugLineNum = 109;BA.debugLine="Private Sub lbl_back_Click";
 //BA.debugLineNum = 110;BA.debugLine="StartActivity(home_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._home_activity.getObject()));
 //BA.debugLineNum = 111;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_delete_edit_click() throws Exception{
 //BA.debugLineNum = 198;BA.debugLine="Private Sub lbl_delete_edit_Click";
 //BA.debugLineNum = 200;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_edit_item_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _lb = null;
anywheresoftware.b4a.objects.collections.List _list_rec = null;
 //BA.debugLineNum = 158;BA.debugLine="Private Sub lbl_edit_item_Click";
 //BA.debugLineNum = 159;BA.debugLine="Dim lb As Label=Sender";
_lb = new anywheresoftware.b4a.objects.LabelWrapper();
_lb = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 161;BA.debugLine="current_id=lb.Tag";
_current_id = (int)(BA.ObjectToNumber(_lb.getTag()));
 //BA.debugLineNum = 167;BA.debugLine="Dim list_rec As List=myFunc.get_by_id(current_id)";
_list_rec = new anywheresoftware.b4a.objects.collections.List();
_list_rec = mostCurrent._myfunc._get_by_id /*anywheresoftware.b4a.objects.collections.List*/ (mostCurrent.activityBA,_current_id);
 //BA.debugLineNum = 172;BA.debugLine="et_date_edit.Text=list_rec.Get(1)";
mostCurrent._et_date_edit.setText(BA.ObjectToCharSequence(_list_rec.Get((int) (1))));
 //BA.debugLineNum = 173;BA.debugLine="lbl_vorod_time_edit.Text=list_rec.Get(2)";
mostCurrent._lbl_vorod_time_edit.setText(BA.ObjectToCharSequence(_list_rec.Get((int) (2))));
 //BA.debugLineNum = 174;BA.debugLine="lbl_khoroj_time_edit.Text=list_rec.Get(3)";
mostCurrent._lbl_khoroj_time_edit.setText(BA.ObjectToCharSequence(_list_rec.Get((int) (3))));
 //BA.debugLineNum = 180;BA.debugLine="et_tozih_edit.Text=list_rec.Get(6)";
mostCurrent._et_tozih_edit.setText(BA.ObjectToCharSequence(_list_rec.Get((int) (6))));
 //BA.debugLineNum = 182;BA.debugLine="show_tim_lbl2";
_show_tim_lbl2();
 //BA.debugLineNum = 184;BA.debugLine="pan_all_edit.Visible=True";
mostCurrent._pan_all_edit.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 186;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_khoroj_tap_click() throws Exception{
String _tim_str = "";
 //BA.debugLineNum = 136;BA.debugLine="Private Sub lbl_khoroj_tap_Click";
 //BA.debugLineNum = 137;BA.debugLine="Dim tim_str As String=\"\"";
_tim_str = "";
 //BA.debugLineNum = 138;BA.debugLine="tim_str=DateTime.Time(DateTime.Now).SubString2(0,";
_tim_str = anywheresoftware.b4a.keywords.Common.DateTime.Time(anywheresoftware.b4a.keywords.Common.DateTime.getNow()).substring((int) (0),(int) (5));
 //BA.debugLineNum = 139;BA.debugLine="lbl_khoroj_time.Text=tim_str";
mostCurrent._lbl_khoroj_time.setText(BA.ObjectToCharSequence(_tim_str));
 //BA.debugLineNum = 141;BA.debugLine="show_tim_lbl1";
_show_tim_lbl1();
 //BA.debugLineNum = 143;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_khoroj_time_click() throws Exception{
String[] _tt = null;
int _tt_h = 0;
int _tt_m = 0;
int _j = 0;
 //BA.debugLineNum = 254;BA.debugLine="Private Sub lbl_khoroj_time_Click";
 //BA.debugLineNum = 256;BA.debugLine="Dim tt() As String= Regex.Split(\":\",lbl_khoroj_ti";
_tt = anywheresoftware.b4a.keywords.Common.Regex.Split(":",mostCurrent._lbl_khoroj_time.getText());
 //BA.debugLineNum = 257;BA.debugLine="Dim tt_h As Int=tt(0)";
_tt_h = (int)(Double.parseDouble(_tt[(int) (0)]));
 //BA.debugLineNum = 258;BA.debugLine="Dim tt_m As Int=tt(1)";
_tt_m = (int)(Double.parseDouble(_tt[(int) (1)]));
 //BA.debugLineNum = 260;BA.debugLine="dialog_tim.SetTime(tt_h,tt_m,True)";
mostCurrent._dialog_tim.SetTime(_tt_h,_tt_m,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 262;BA.debugLine="Dim j As Int= dialog_tim.Show(\"زمان خروج\",\"انتخاب";
_j = mostCurrent._dialog_tim.Show("زمان خروج","انتخاب","باشه","","نه",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 263;BA.debugLine="If j=DialogResponse.POSITIVE Then";
if (_j==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 264;BA.debugLine="lbl_khoroj_time.Text=dialog_tim.Hour&\":\"&dialog_";
mostCurrent._lbl_khoroj_time.setText(BA.ObjectToCharSequence(BA.NumberToString(mostCurrent._dialog_tim.getHour())+":"+BA.NumberToString(mostCurrent._dialog_tim.getMinute())));
 //BA.debugLineNum = 266;BA.debugLine="show_tim_lbl1";
_show_tim_lbl1();
 };
 //BA.debugLineNum = 270;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_khoroj_time_edit_click() throws Exception{
String[] _tt = null;
int _tt_h = 0;
int _tt_m = 0;
int _j = 0;
 //BA.debugLineNum = 221;BA.debugLine="Private Sub lbl_khoroj_time_edit_Click";
 //BA.debugLineNum = 223;BA.debugLine="Dim tt() As String= Regex.Split(\":\",lbl_khoroj_ti";
_tt = anywheresoftware.b4a.keywords.Common.Regex.Split(":",mostCurrent._lbl_khoroj_time_edit.getText());
 //BA.debugLineNum = 224;BA.debugLine="Dim tt_h As Int=tt(0)";
_tt_h = (int)(Double.parseDouble(_tt[(int) (0)]));
 //BA.debugLineNum = 225;BA.debugLine="Dim tt_m As Int=tt(1)";
_tt_m = (int)(Double.parseDouble(_tt[(int) (1)]));
 //BA.debugLineNum = 227;BA.debugLine="dialog_tim.SetTime(tt_h,tt_m,True)";
mostCurrent._dialog_tim.SetTime(_tt_h,_tt_m,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 229;BA.debugLine="Dim j As Int= dialog_tim.Show(\"زمان ورود\",\"انتخاب";
_j = mostCurrent._dialog_tim.Show("زمان ورود","انتخاب","باشه","","نه",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 230;BA.debugLine="If j=DialogResponse.POSITIVE Then";
if (_j==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 231;BA.debugLine="lbl_khoroj_time_edit.Text=dialog_tim.Hour&\":\"&di";
mostCurrent._lbl_khoroj_time_edit.setText(BA.ObjectToCharSequence(BA.NumberToString(mostCurrent._dialog_tim.getHour())+":"+BA.NumberToString(mostCurrent._dialog_tim.getMinute())));
 //BA.debugLineNum = 233;BA.debugLine="show_tim_lbl2";
_show_tim_lbl2();
 };
 //BA.debugLineNum = 235;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_save_click() throws Exception{
anywheresoftware.b4a.objects.collections.List _res_h_m = null;
 //BA.debugLineNum = 114;BA.debugLine="Private Sub lbl_save_Click";
 //BA.debugLineNum = 116;BA.debugLine="Dim res_H_M As List =myFunc.time_show2(et_date.Te";
_res_h_m = new anywheresoftware.b4a.objects.collections.List();
_res_h_m = mostCurrent._myfunc._time_show2 /*anywheresoftware.b4a.objects.collections.List*/ (mostCurrent.activityBA,mostCurrent._et_date.getText(),mostCurrent._lbl_vorod_time.getText(),mostCurrent._lbl_khoroj_time.getText());
 //BA.debugLineNum = 119;BA.debugLine="myFunc.add_taradod(et_date.Text,lbl_vorod_time.Te";
mostCurrent._myfunc._add_taradod /*boolean*/ (mostCurrent.activityBA,mostCurrent._et_date.getText(),mostCurrent._lbl_vorod_time.getText(),mostCurrent._lbl_khoroj_time.getText(),(int)(BA.ObjectToNumber(_res_h_m.Get((int) (0)))),(int)(BA.ObjectToNumber(_res_h_m.Get((int) (1)))),mostCurrent._et_tozih.getText(),(int) (0));
 //BA.debugLineNum = 120;BA.debugLine="fill_list(\"1401\",\"03\")";
_fill_list("1401","03");
 //BA.debugLineNum = 121;BA.debugLine="et_tozih.Text=\"\"";
mostCurrent._et_tozih.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 122;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_save_edit_click() throws Exception{
 //BA.debugLineNum = 194;BA.debugLine="Private Sub lbl_save_edit_Click";
 //BA.debugLineNum = 196;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_vorod_tap_click() throws Exception{
String _tim_str1 = "";
 //BA.debugLineNum = 145;BA.debugLine="Private Sub lbl_vorod_tap_Click";
 //BA.debugLineNum = 146;BA.debugLine="Dim tim_str1 As String=\"\"";
_tim_str1 = "";
 //BA.debugLineNum = 147;BA.debugLine="tim_str1=DateTime.Time(DateTime.Now).SubString2(0";
_tim_str1 = anywheresoftware.b4a.keywords.Common.DateTime.Time(anywheresoftware.b4a.keywords.Common.DateTime.getNow()).substring((int) (0),(int) (5));
 //BA.debugLineNum = 148;BA.debugLine="lbl_vorod_time.Text=tim_str1";
mostCurrent._lbl_vorod_time.setText(BA.ObjectToCharSequence(_tim_str1));
 //BA.debugLineNum = 151;BA.debugLine="show_tim_lbl1";
_show_tim_lbl1();
 //BA.debugLineNum = 154;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_vorod_time_click() throws Exception{
String[] _tt = null;
int _tt_h = 0;
int _tt_m = 0;
int _j = 0;
 //BA.debugLineNum = 237;BA.debugLine="Private Sub lbl_vorod_time_Click";
 //BA.debugLineNum = 240;BA.debugLine="Dim tt() As String= Regex.Split(\":\",lbl_vorod_tim";
_tt = anywheresoftware.b4a.keywords.Common.Regex.Split(":",mostCurrent._lbl_vorod_time.getText());
 //BA.debugLineNum = 241;BA.debugLine="Dim tt_h As Int=tt(0)";
_tt_h = (int)(Double.parseDouble(_tt[(int) (0)]));
 //BA.debugLineNum = 242;BA.debugLine="Dim tt_m As Int=tt(1)";
_tt_m = (int)(Double.parseDouble(_tt[(int) (1)]));
 //BA.debugLineNum = 244;BA.debugLine="dialog_tim.SetTime(tt_h,tt_m,True)";
mostCurrent._dialog_tim.SetTime(_tt_h,_tt_m,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 246;BA.debugLine="Dim j As Int= dialog_tim.Show(\"زمان ورود\",\"انتخاب";
_j = mostCurrent._dialog_tim.Show("زمان ورود","انتخاب","باشه","","نه",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 247;BA.debugLine="If j=DialogResponse.POSITIVE Then";
if (_j==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 248;BA.debugLine="lbl_vorod_time.Text=dialog_tim.Hour&\":\"&dialog_t";
mostCurrent._lbl_vorod_time.setText(BA.ObjectToCharSequence(BA.NumberToString(mostCurrent._dialog_tim.getHour())+":"+BA.NumberToString(mostCurrent._dialog_tim.getMinute())));
 //BA.debugLineNum = 250;BA.debugLine="show_tim_lbl1";
_show_tim_lbl1();
 };
 //BA.debugLineNum = 252;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_vorod_time_edit_click() throws Exception{
String[] _tt = null;
int _tt_h = 0;
int _tt_m = 0;
int _j = 0;
 //BA.debugLineNum = 204;BA.debugLine="Private Sub lbl_vorod_time_edit_Click";
 //BA.debugLineNum = 206;BA.debugLine="Dim tt() As String= Regex.Split(\":\",lbl_vorod_tim";
_tt = anywheresoftware.b4a.keywords.Common.Regex.Split(":",mostCurrent._lbl_vorod_time_edit.getText());
 //BA.debugLineNum = 207;BA.debugLine="Dim tt_h As Int=tt(0)";
_tt_h = (int)(Double.parseDouble(_tt[(int) (0)]));
 //BA.debugLineNum = 208;BA.debugLine="Dim tt_m As Int=tt(1)";
_tt_m = (int)(Double.parseDouble(_tt[(int) (1)]));
 //BA.debugLineNum = 210;BA.debugLine="dialog_tim.SetTime(tt_h,tt_m,True)";
mostCurrent._dialog_tim.SetTime(_tt_h,_tt_m,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 212;BA.debugLine="Dim j As Int= dialog_tim.Show(\"زمان ورود\",\"انتخاب";
_j = mostCurrent._dialog_tim.Show("زمان ورود","انتخاب","باشه","","نه",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 213;BA.debugLine="If j=DialogResponse.POSITIVE Then";
if (_j==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 214;BA.debugLine="lbl_vorod_time_edit.Text=dialog_tim.Hour&\":\"&dia";
mostCurrent._lbl_vorod_time_edit.setText(BA.ObjectToCharSequence(BA.NumberToString(mostCurrent._dialog_tim.getHour())+":"+BA.NumberToString(mostCurrent._dialog_tim.getMinute())));
 //BA.debugLineNum = 216;BA.debugLine="show_tim_lbl2";
_show_tim_lbl2();
 };
 //BA.debugLineNum = 219;BA.debugLine="End Sub";
return "";
}
public static String  _pan_all_edit_click() throws Exception{
 //BA.debugLineNum = 190;BA.debugLine="Private Sub pan_all_edit_Click";
 //BA.debugLineNum = 191;BA.debugLine="pan_all_edit.Visible=False";
mostCurrent._pan_all_edit.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 192;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _show_tim_lbl1() throws Exception{
anywheresoftware.b4a.objects.collections.List _res_h_m = null;
 //BA.debugLineNum = 124;BA.debugLine="Sub show_tim_lbl1";
 //BA.debugLineNum = 125;BA.debugLine="Dim res_H_M As List =myFunc.time_show2(et_date.Te";
_res_h_m = new anywheresoftware.b4a.objects.collections.List();
_res_h_m = mostCurrent._myfunc._time_show2 /*anywheresoftware.b4a.objects.collections.List*/ (mostCurrent.activityBA,mostCurrent._et_date.getText(),mostCurrent._lbl_vorod_time.getText(),mostCurrent._lbl_khoroj_time.getText());
 //BA.debugLineNum = 126;BA.debugLine="lbl_show_time.Text=\"\"&res_H_M.Get(0)&\" و \"&res_H_";
mostCurrent._lbl_show_time.setText(BA.ObjectToCharSequence(""+BA.ObjectToString(_res_h_m.Get((int) (0)))+" و "+BA.ObjectToString(_res_h_m.Get((int) (1)))+"دقیقه "));
 //BA.debugLineNum = 127;BA.debugLine="End Sub";
return "";
}
public static String  _show_tim_lbl2() throws Exception{
anywheresoftware.b4a.objects.collections.List _res_h_m = null;
 //BA.debugLineNum = 130;BA.debugLine="Sub show_tim_lbl2";
 //BA.debugLineNum = 131;BA.debugLine="Dim res_H_M As List =myFunc.time_show2(et_date_edi";
_res_h_m = new anywheresoftware.b4a.objects.collections.List();
_res_h_m = mostCurrent._myfunc._time_show2 /*anywheresoftware.b4a.objects.collections.List*/ (mostCurrent.activityBA,mostCurrent._et_date_edit.getText(),mostCurrent._lbl_vorod_time_edit.getText(),mostCurrent._lbl_khoroj_time_edit.getText());
 //BA.debugLineNum = 132;BA.debugLine="lbl_show_time_edit.Text=\"\"&res_H_M.Get(0)&\" و \"&r";
mostCurrent._lbl_show_time_edit.setText(BA.ObjectToCharSequence(""+BA.ObjectToString(_res_h_m.Get((int) (0)))+" و "+BA.ObjectToString(_res_h_m.Get((int) (1)))+"دقیقه "));
 //BA.debugLineNum = 133;BA.debugLine="End Sub";
return "";
}
}

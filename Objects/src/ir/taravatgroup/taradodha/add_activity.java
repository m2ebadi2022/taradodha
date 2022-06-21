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
public anywheresoftware.b4a.objects.LabelWrapper _lbl_day_name = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _sp_mah = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_sall = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_del_item = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _sp_date_day = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _sp_date_year = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _sp_date_moon = null;
public static String _date_str = "";
public b4a.example.dateutils _dateutils = null;
public ir.taravatgroup.taradodha.main _main = null;
public ir.taravatgroup.taradodha.starter _starter = null;
public ir.taravatgroup.taradodha.myfunc _myfunc = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 57;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 59;BA.debugLine="Activity.LoadLayout(\"add_layout\")";
mostCurrent._activity.LoadLayout("add_layout",mostCurrent.activityBA);
 //BA.debugLineNum = 62;BA.debugLine="sp_date_year.Padding=Array As Int(4dip,4dip,25dip";
mostCurrent._sp_date_year.setPadding(new int[]{anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (4)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (4)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (4))});
 //BA.debugLineNum = 63;BA.debugLine="sp_date_year.Add(\"1400\")";
mostCurrent._sp_date_year.Add("1400");
 //BA.debugLineNum = 64;BA.debugLine="sp_date_year.Add(\"1401\")";
mostCurrent._sp_date_year.Add("1401");
 //BA.debugLineNum = 65;BA.debugLine="sp_date_year.Add(\"1402\")";
mostCurrent._sp_date_year.Add("1402");
 //BA.debugLineNum = 66;BA.debugLine="sp_date_year.Add(\"1403\")";
mostCurrent._sp_date_year.Add("1403");
 //BA.debugLineNum = 67;BA.debugLine="sp_date_year.Add(\"1404\")";
mostCurrent._sp_date_year.Add("1404");
 //BA.debugLineNum = 68;BA.debugLine="sp_date_year.Add(\"1405\")";
mostCurrent._sp_date_year.Add("1405");
 //BA.debugLineNum = 69;BA.debugLine="sp_date_year.Add(\"1406\")";
mostCurrent._sp_date_year.Add("1406");
 //BA.debugLineNum = 70;BA.debugLine="sp_date_year.Add(\"1407\")";
mostCurrent._sp_date_year.Add("1407");
 //BA.debugLineNum = 71;BA.debugLine="sp_date_year.Add(\"1408\")";
mostCurrent._sp_date_year.Add("1408");
 //BA.debugLineNum = 72;BA.debugLine="sp_date_year.Add(\"1409\")";
mostCurrent._sp_date_year.Add("1409");
 //BA.debugLineNum = 73;BA.debugLine="sp_date_year.Add(\"1410\")";
mostCurrent._sp_date_year.Add("1410");
 //BA.debugLineNum = 76;BA.debugLine="sp_date_moon.Padding=Array As Int(4dip,4dip,25dip";
mostCurrent._sp_date_moon.setPadding(new int[]{anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (4)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (4)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (4))});
 //BA.debugLineNum = 77;BA.debugLine="sp_date_moon.Add(\"فروردین\")";
mostCurrent._sp_date_moon.Add("فروردین");
 //BA.debugLineNum = 78;BA.debugLine="sp_date_moon.Add(\"اردیبهشت\")";
mostCurrent._sp_date_moon.Add("اردیبهشت");
 //BA.debugLineNum = 79;BA.debugLine="sp_date_moon.Add(\"خرداد\")";
mostCurrent._sp_date_moon.Add("خرداد");
 //BA.debugLineNum = 80;BA.debugLine="sp_date_moon.Add(\"تیر\")";
mostCurrent._sp_date_moon.Add("تیر");
 //BA.debugLineNum = 81;BA.debugLine="sp_date_moon.Add(\"مرداد\")";
mostCurrent._sp_date_moon.Add("مرداد");
 //BA.debugLineNum = 82;BA.debugLine="sp_date_moon.Add(\"شهریور\")";
mostCurrent._sp_date_moon.Add("شهریور");
 //BA.debugLineNum = 83;BA.debugLine="sp_date_moon.Add(\"مهر\")";
mostCurrent._sp_date_moon.Add("مهر");
 //BA.debugLineNum = 84;BA.debugLine="sp_date_moon.Add(\"آبان\")";
mostCurrent._sp_date_moon.Add("آبان");
 //BA.debugLineNum = 85;BA.debugLine="sp_date_moon.Add(\"آذر\")";
mostCurrent._sp_date_moon.Add("آذر");
 //BA.debugLineNum = 86;BA.debugLine="sp_date_moon.Add(\"دی\")";
mostCurrent._sp_date_moon.Add("دی");
 //BA.debugLineNum = 87;BA.debugLine="sp_date_moon.Add(\"بهمن\")";
mostCurrent._sp_date_moon.Add("بهمن");
 //BA.debugLineNum = 88;BA.debugLine="sp_date_moon.Add(\"اسفند\")";
mostCurrent._sp_date_moon.Add("اسفند");
 //BA.debugLineNum = 90;BA.debugLine="sp_date_day.Padding=Array As Int(4dip,4dip,25dip,";
mostCurrent._sp_date_day.setPadding(new int[]{anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (4)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (4)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (4))});
 //BA.debugLineNum = 91;BA.debugLine="sp_date_day.AddAll(Array As String(\"01\",\"02\",\"03\"";
mostCurrent._sp_date_day.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"}));
 //BA.debugLineNum = 97;BA.debugLine="sp_date_year.SelectedIndex=1";
mostCurrent._sp_date_year.setSelectedIndex((int) (1));
 //BA.debugLineNum = 98;BA.debugLine="sp_date_moon.SelectedIndex=Main.prsianDate.Persia";
mostCurrent._sp_date_moon.setSelectedIndex((int) (mostCurrent._main._prsiandate /*com.b4a.manamsoftware.PersianDate.ManamPersianDate*/ .getPersianMonth()-1));
 //BA.debugLineNum = 99;BA.debugLine="sp_date_day.SelectedIndex=Main.prsianDate.Persian";
mostCurrent._sp_date_day.setSelectedIndex((int) (mostCurrent._main._prsiandate /*com.b4a.manamsoftware.PersianDate.ManamPersianDate*/ .getPersianDay()-1));
 //BA.debugLineNum = 100;BA.debugLine="date_str=Main.prsianDate.PersianShortDate";
mostCurrent._date_str = mostCurrent._main._prsiandate /*com.b4a.manamsoftware.PersianDate.ManamPersianDate*/ .getPersianShortDate();
 //BA.debugLineNum = 106;BA.debugLine="sp_mah.Padding=Array As Int(4dip,4dip,25dip,4dip)";
mostCurrent._sp_mah.setPadding(new int[]{anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (4)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (4)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (4))});
 //BA.debugLineNum = 107;BA.debugLine="sp_mah.Add(\"فروردین\")";
mostCurrent._sp_mah.Add("فروردین");
 //BA.debugLineNum = 108;BA.debugLine="sp_mah.Add(\"اردیبهشت\")";
mostCurrent._sp_mah.Add("اردیبهشت");
 //BA.debugLineNum = 109;BA.debugLine="sp_mah.Add(\"خرداد\")";
mostCurrent._sp_mah.Add("خرداد");
 //BA.debugLineNum = 110;BA.debugLine="sp_mah.Add(\"تیر\")";
mostCurrent._sp_mah.Add("تیر");
 //BA.debugLineNum = 111;BA.debugLine="sp_mah.Add(\"مرداد\")";
mostCurrent._sp_mah.Add("مرداد");
 //BA.debugLineNum = 112;BA.debugLine="sp_mah.Add(\"شهریور\")";
mostCurrent._sp_mah.Add("شهریور");
 //BA.debugLineNum = 113;BA.debugLine="sp_mah.Add(\"مهر\")";
mostCurrent._sp_mah.Add("مهر");
 //BA.debugLineNum = 114;BA.debugLine="sp_mah.Add(\"آبان\")";
mostCurrent._sp_mah.Add("آبان");
 //BA.debugLineNum = 115;BA.debugLine="sp_mah.Add(\"آذر\")";
mostCurrent._sp_mah.Add("آذر");
 //BA.debugLineNum = 116;BA.debugLine="sp_mah.Add(\"دی\")";
mostCurrent._sp_mah.Add("دی");
 //BA.debugLineNum = 117;BA.debugLine="sp_mah.Add(\"بهمن\")";
mostCurrent._sp_mah.Add("بهمن");
 //BA.debugLineNum = 118;BA.debugLine="sp_mah.Add(\"اسفند\")";
mostCurrent._sp_mah.Add("اسفند");
 //BA.debugLineNum = 122;BA.debugLine="et_sall.Text=Main.prsianDate.PersianYear";
mostCurrent._et_sall.setText(BA.ObjectToCharSequence(mostCurrent._main._prsiandate /*com.b4a.manamsoftware.PersianDate.ManamPersianDate*/ .getPersianYear()));
 //BA.debugLineNum = 123;BA.debugLine="sp_mah.SelectedIndex=Main.prsianDate.PersianMonth";
mostCurrent._sp_mah.setSelectedIndex((int) (mostCurrent._main._prsiandate /*com.b4a.manamsoftware.PersianDate.ManamPersianDate*/ .getPersianMonth()-1));
 //BA.debugLineNum = 126;BA.debugLine="fill_list";
_fill_list();
 //BA.debugLineNum = 129;BA.debugLine="dialog_tim.Is24Hours=True";
mostCurrent._dialog_tim.setIs24Hours(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 429;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 430;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 431;BA.debugLine="If(pan_all_edit.Visible=True)Then";
if ((mostCurrent._pan_all_edit.getVisible()==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 432;BA.debugLine="pan_all_edit.Visible=False";
mostCurrent._pan_all_edit.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 434;BA.debugLine="ExitApplication                        ' Hardw";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 };
 //BA.debugLineNum = 436;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 438;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 440;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 222;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 224;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 218;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 220;BA.debugLine="End Sub";
return "";
}
public static String  _convert_add(int _adad) throws Exception{
String _res = "";
 //BA.debugLineNum = 136;BA.debugLine="Sub convert_add (adad As Int) As String";
 //BA.debugLineNum = 138;BA.debugLine="Dim res As String=adad";
_res = BA.NumberToString(_adad);
 //BA.debugLineNum = 139;BA.debugLine="Select adad";
switch (_adad) {
case 0: {
 //BA.debugLineNum = 141;BA.debugLine="res=\"00\"";
_res = "00";
 break; }
case 1: {
 //BA.debugLineNum = 143;BA.debugLine="res=\"01\"";
_res = "01";
 break; }
case 2: {
 //BA.debugLineNum = 145;BA.debugLine="res=\"02\"";
_res = "02";
 break; }
case 3: {
 //BA.debugLineNum = 147;BA.debugLine="res=\"03\"";
_res = "03";
 break; }
case 4: {
 //BA.debugLineNum = 149;BA.debugLine="res=\"04\"";
_res = "04";
 break; }
case 5: {
 //BA.debugLineNum = 151;BA.debugLine="res=\"05\"";
_res = "05";
 break; }
case 6: {
 //BA.debugLineNum = 153;BA.debugLine="res=\"06\"";
_res = "06";
 break; }
case 7: {
 //BA.debugLineNum = 155;BA.debugLine="res=\"07\"";
_res = "07";
 break; }
case 8: {
 //BA.debugLineNum = 157;BA.debugLine="res=\"08\"";
_res = "08";
 break; }
case 9: {
 //BA.debugLineNum = 159;BA.debugLine="res=\"09\"";
_res = "09";
 break; }
}
;
 //BA.debugLineNum = 163;BA.debugLine="Return res";
if (true) return _res;
 //BA.debugLineNum = 164;BA.debugLine="End Sub";
return "";
}
public static String  _date_generat() throws Exception{
 //BA.debugLineNum = 132;BA.debugLine="Sub date_generat";
 //BA.debugLineNum = 133;BA.debugLine="date_str=sp_date_year.SelectedItem&\"/\"&convert_ad";
mostCurrent._date_str = mostCurrent._sp_date_year.getSelectedItem()+"/"+_convert_add((int) (mostCurrent._sp_date_moon.getSelectedIndex()+1))+"/"+mostCurrent._sp_date_day.getSelectedItem();
 //BA.debugLineNum = 134;BA.debugLine="End Sub";
return "";
}
public static String  _fill_list() throws Exception{
String _sal = "";
String _mah = "";
anywheresoftware.b4a.objects.collections.List _list1 = null;
int _all_tim_h = 0;
int _all_tim_m = 0;
int _i = 0;
String[] _list2 = null;
 //BA.debugLineNum = 166;BA.debugLine="Sub fill_list";
 //BA.debugLineNum = 168;BA.debugLine="Dim sal As String=et_sall.Text";
_sal = mostCurrent._et_sall.getText();
 //BA.debugLineNum = 169;BA.debugLine="Dim mah As String=\"03\"";
_mah = "03";
 //BA.debugLineNum = 173;BA.debugLine="mah=convert_add(sp_mah.SelectedIndex+1)";
_mah = _convert_add((int) (mostCurrent._sp_mah.getSelectedIndex()+1));
 //BA.debugLineNum = 176;BA.debugLine="Dim list1 As List";
_list1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 177;BA.debugLine="list1.Initialize";
_list1.Initialize();
 //BA.debugLineNum = 178;BA.debugLine="list1=myFunc.get_data(sal,mah)";
_list1 = mostCurrent._myfunc._get_data /*anywheresoftware.b4a.objects.collections.List*/ (mostCurrent.activityBA,_sal,_mah);
 //BA.debugLineNum = 182;BA.debugLine="cusListV_data.Clear";
mostCurrent._cuslistv_data._clear();
 //BA.debugLineNum = 184;BA.debugLine="Dim all_tim_h As Int=0";
_all_tim_h = (int) (0);
 //BA.debugLineNum = 185;BA.debugLine="Dim all_tim_m As Int=0";
_all_tim_m = (int) (0);
 //BA.debugLineNum = 189;BA.debugLine="For i=0 To list1.Size-1";
{
final int step10 = 1;
final int limit10 = (int) (_list1.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit10 ;_i = _i + step10 ) {
 //BA.debugLineNum = 190;BA.debugLine="p = xui.CreatePanel(\"p\")";
mostCurrent._p = mostCurrent._xui.CreatePanel(processBA,"p");
 //BA.debugLineNum = 191;BA.debugLine="p.SetLayoutAnimated(0, 0, 0, 95%x, 110dip)";
mostCurrent._p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (110)));
 //BA.debugLineNum = 192;BA.debugLine="p.LoadLayout(\"irem_data\")";
mostCurrent._p.LoadLayout("irem_data",mostCurrent.activityBA);
 //BA.debugLineNum = 193;BA.debugLine="Dim list2() As String=list1.Get(i)";
_list2 = (String[])(_list1.Get(_i));
 //BA.debugLineNum = 195;BA.debugLine="cusListV_data.Add(p,list2(0))";
mostCurrent._cuslistv_data._add(mostCurrent._p,(Object)(_list2[(int) (0)]));
 //BA.debugLineNum = 196;BA.debugLine="lbl_edit_item.Tag=list2(0)";
mostCurrent._lbl_edit_item.setTag((Object)(_list2[(int) (0)]));
 //BA.debugLineNum = 197;BA.debugLine="lbl_del_item.Tag=list2(0)";
mostCurrent._lbl_del_item.setTag((Object)(_list2[(int) (0)]));
 //BA.debugLineNum = 199;BA.debugLine="lbl_date_item.Text=list2(1)";
mostCurrent._lbl_date_item.setText(BA.ObjectToCharSequence(_list2[(int) (1)]));
 //BA.debugLineNum = 200;BA.debugLine="lbl_vorod_item.Text=list2(2)";
mostCurrent._lbl_vorod_item.setText(BA.ObjectToCharSequence(_list2[(int) (2)]));
 //BA.debugLineNum = 201;BA.debugLine="lbl_khoroj_item.Text=list2(3)";
mostCurrent._lbl_khoroj_item.setText(BA.ObjectToCharSequence(_list2[(int) (3)]));
 //BA.debugLineNum = 202;BA.debugLine="lbl_tozih_item.Text=list2(6)";
mostCurrent._lbl_tozih_item.setText(BA.ObjectToCharSequence(_list2[(int) (6)]));
 //BA.debugLineNum = 204;BA.debugLine="lbl_day_name.Text=myFunc.get_day_name(list2(1))";
mostCurrent._lbl_day_name.setText(BA.ObjectToCharSequence(mostCurrent._myfunc._get_day_name /*String*/ (mostCurrent.activityBA,_list2[(int) (1)])));
 //BA.debugLineNum = 206;BA.debugLine="all_tim_h=all_tim_h+list2(4)";
_all_tim_h = (int) (_all_tim_h+(double)(Double.parseDouble(_list2[(int) (4)])));
 //BA.debugLineNum = 207;BA.debugLine="all_tim_m=all_tim_m+list2(5)";
_all_tim_m = (int) (_all_tim_m+(double)(Double.parseDouble(_list2[(int) (5)])));
 }
};
 //BA.debugLineNum = 211;BA.debugLine="If(all_tim_m>59)Then";
if ((_all_tim_m>59)) { 
 //BA.debugLineNum = 212;BA.debugLine="all_tim_h=all_tim_h+(all_tim_m / 60)";
_all_tim_h = (int) (_all_tim_h+(_all_tim_m/(double)60));
 //BA.debugLineNum = 213;BA.debugLine="all_tim_m=all_tim_m Mod 60";
_all_tim_m = (int) (_all_tim_m%60);
 };
 //BA.debugLineNum = 216;BA.debugLine="lbl_show_allTime.Text=all_tim_h &\" ساعت و \"&all_t";
mostCurrent._lbl_show_alltime.setText(BA.ObjectToCharSequence(BA.NumberToString(_all_tim_h)+" ساعت و "+BA.NumberToString(_all_tim_m)+" دقیقه "));
 //BA.debugLineNum = 217;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private et_tozih As EditText";
mostCurrent._et_tozih = new anywheresoftware.b4a.objects.EditTextWrapper();
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
 //BA.debugLineNum = 46;BA.debugLine="Private lbl_day_name As Label";
mostCurrent._lbl_day_name = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private sp_mah As Spinner";
mostCurrent._sp_mah = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private et_sall As EditText";
mostCurrent._et_sall = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private lbl_del_item As Label";
mostCurrent._lbl_del_item = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private sp_date_day As Spinner";
mostCurrent._sp_date_day = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private sp_date_year As Spinner";
mostCurrent._sp_date_year = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private sp_date_moon As Spinner";
mostCurrent._sp_date_moon = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Dim date_str As String=\"\"";
mostCurrent._date_str = "";
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_cancel_edit_click() throws Exception{
 //BA.debugLineNum = 424;BA.debugLine="Private Sub lbl_cancel_edit_Click";
 //BA.debugLineNum = 425;BA.debugLine="pan_all_edit.Visible=False";
mostCurrent._pan_all_edit.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 426;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_del_item_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _lb = null;
int _result = 0;
 //BA.debugLineNum = 409;BA.debugLine="Private Sub lbl_del_item_Click";
 //BA.debugLineNum = 410;BA.debugLine="Dim lb As Label=Sender";
_lb = new anywheresoftware.b4a.objects.LabelWrapper();
_lb = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 412;BA.debugLine="current_id=lb.Tag";
_current_id = (int)(BA.ObjectToNumber(_lb.getTag()));
 //BA.debugLineNum = 414;BA.debugLine="Dim result As Int";
_result = 0;
 //BA.debugLineNum = 415;BA.debugLine="result = Msgbox2(\"آیا این مورد حذف شود؟\", \"حذف\",";
_result = anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("آیا این مورد حذف شود؟"),BA.ObjectToCharSequence("حذف"),"بله","","خیر",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 //BA.debugLineNum = 416;BA.debugLine="If result = DialogResponse.Positive Then";
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 417;BA.debugLine="myFunc.delete_taradod(current_id)";
mostCurrent._myfunc._delete_taradod /*boolean*/ (mostCurrent.activityBA,_current_id);
 //BA.debugLineNum = 418;BA.debugLine="pan_all_edit.Visible=False";
mostCurrent._pan_all_edit.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 419;BA.debugLine="fill_list";
_fill_list();
 };
 //BA.debugLineNum = 422;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_edit_item_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _lb = null;
anywheresoftware.b4a.objects.collections.List _list_rec = null;
 //BA.debugLineNum = 278;BA.debugLine="Private Sub lbl_edit_item_Click";
 //BA.debugLineNum = 279;BA.debugLine="Dim lb As Label=Sender";
_lb = new anywheresoftware.b4a.objects.LabelWrapper();
_lb = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 281;BA.debugLine="current_id=lb.Tag";
_current_id = (int)(BA.ObjectToNumber(_lb.getTag()));
 //BA.debugLineNum = 287;BA.debugLine="Dim list_rec As List=myFunc.get_by_id(current_id)";
_list_rec = new anywheresoftware.b4a.objects.collections.List();
_list_rec = mostCurrent._myfunc._get_by_id /*anywheresoftware.b4a.objects.collections.List*/ (mostCurrent.activityBA,_current_id);
 //BA.debugLineNum = 292;BA.debugLine="et_date_edit.Text=list_rec.Get(1)";
mostCurrent._et_date_edit.setText(BA.ObjectToCharSequence(_list_rec.Get((int) (1))));
 //BA.debugLineNum = 293;BA.debugLine="lbl_vorod_time_edit.Text=list_rec.Get(2)";
mostCurrent._lbl_vorod_time_edit.setText(BA.ObjectToCharSequence(_list_rec.Get((int) (2))));
 //BA.debugLineNum = 294;BA.debugLine="lbl_khoroj_time_edit.Text=list_rec.Get(3)";
mostCurrent._lbl_khoroj_time_edit.setText(BA.ObjectToCharSequence(_list_rec.Get((int) (3))));
 //BA.debugLineNum = 300;BA.debugLine="et_tozih_edit.Text=list_rec.Get(6)";
mostCurrent._et_tozih_edit.setText(BA.ObjectToCharSequence(_list_rec.Get((int) (6))));
 //BA.debugLineNum = 302;BA.debugLine="show_tim_lbl2";
_show_tim_lbl2();
 //BA.debugLineNum = 304;BA.debugLine="pan_all_edit.Visible=True";
mostCurrent._pan_all_edit.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 306;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_khoroj_tap_click() throws Exception{
String _tim_str = "";
 //BA.debugLineNum = 256;BA.debugLine="Private Sub lbl_khoroj_tap_Click";
 //BA.debugLineNum = 257;BA.debugLine="Dim tim_str As String=\"\"";
_tim_str = "";
 //BA.debugLineNum = 258;BA.debugLine="tim_str=DateTime.Time(DateTime.Now).SubString2(0,";
_tim_str = anywheresoftware.b4a.keywords.Common.DateTime.Time(anywheresoftware.b4a.keywords.Common.DateTime.getNow()).substring((int) (0),(int) (5));
 //BA.debugLineNum = 259;BA.debugLine="lbl_khoroj_time.Text=tim_str";
mostCurrent._lbl_khoroj_time.setText(BA.ObjectToCharSequence(_tim_str));
 //BA.debugLineNum = 261;BA.debugLine="show_tim_lbl1";
_show_tim_lbl1();
 //BA.debugLineNum = 263;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_khoroj_time_click() throws Exception{
String[] _tt = null;
int _tt_h = 0;
int _tt_m = 0;
int _j = 0;
 //BA.debugLineNum = 383;BA.debugLine="Private Sub lbl_khoroj_time_Click";
 //BA.debugLineNum = 385;BA.debugLine="Dim tt() As String= Regex.Split(\":\",lbl_khoroj_ti";
_tt = anywheresoftware.b4a.keywords.Common.Regex.Split(":",mostCurrent._lbl_khoroj_time.getText());
 //BA.debugLineNum = 386;BA.debugLine="Dim tt_h As Int=tt(0)";
_tt_h = (int)(Double.parseDouble(_tt[(int) (0)]));
 //BA.debugLineNum = 387;BA.debugLine="Dim tt_m As Int=tt(1)";
_tt_m = (int)(Double.parseDouble(_tt[(int) (1)]));
 //BA.debugLineNum = 389;BA.debugLine="dialog_tim.SetTime(tt_h,tt_m,True)";
mostCurrent._dialog_tim.SetTime(_tt_h,_tt_m,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 391;BA.debugLine="Dim j As Int= dialog_tim.Show(\"\",\"زمان خروج\",\"باش";
_j = mostCurrent._dialog_tim.Show("","زمان خروج","باشه","","نه",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 392;BA.debugLine="If j=DialogResponse.POSITIVE Then";
if (_j==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 393;BA.debugLine="lbl_khoroj_time.Text=dialog_tim.Hour&\":\"&dialog_";
mostCurrent._lbl_khoroj_time.setText(BA.ObjectToCharSequence(BA.NumberToString(mostCurrent._dialog_tim.getHour())+":"+BA.NumberToString(mostCurrent._dialog_tim.getMinute())));
 //BA.debugLineNum = 395;BA.debugLine="show_tim_lbl1";
_show_tim_lbl1();
 };
 //BA.debugLineNum = 399;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_khoroj_time_edit_click() throws Exception{
String[] _tt = null;
int _tt_h = 0;
int _tt_m = 0;
int _j = 0;
 //BA.debugLineNum = 350;BA.debugLine="Private Sub lbl_khoroj_time_edit_Click";
 //BA.debugLineNum = 352;BA.debugLine="Dim tt() As String= Regex.Split(\":\",lbl_khoroj_ti";
_tt = anywheresoftware.b4a.keywords.Common.Regex.Split(":",mostCurrent._lbl_khoroj_time_edit.getText());
 //BA.debugLineNum = 353;BA.debugLine="Dim tt_h As Int=tt(0)";
_tt_h = (int)(Double.parseDouble(_tt[(int) (0)]));
 //BA.debugLineNum = 354;BA.debugLine="Dim tt_m As Int=tt(1)";
_tt_m = (int)(Double.parseDouble(_tt[(int) (1)]));
 //BA.debugLineNum = 356;BA.debugLine="dialog_tim.SetTime(tt_h,tt_m,True)";
mostCurrent._dialog_tim.SetTime(_tt_h,_tt_m,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 358;BA.debugLine="Dim j As Int= dialog_tim.Show(\"\",\"زمان ورود\",\"باش";
_j = mostCurrent._dialog_tim.Show("","زمان ورود","باشه","","نه",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 359;BA.debugLine="If j=DialogResponse.POSITIVE Then";
if (_j==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 360;BA.debugLine="lbl_khoroj_time_edit.Text=dialog_tim.Hour&\":\"&di";
mostCurrent._lbl_khoroj_time_edit.setText(BA.ObjectToCharSequence(BA.NumberToString(mostCurrent._dialog_tim.getHour())+":"+BA.NumberToString(mostCurrent._dialog_tim.getMinute())));
 //BA.debugLineNum = 362;BA.debugLine="show_tim_lbl2";
_show_tim_lbl2();
 };
 //BA.debugLineNum = 364;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_save_click() throws Exception{
anywheresoftware.b4a.objects.collections.List _res_h_m = null;
 //BA.debugLineNum = 227;BA.debugLine="Private Sub lbl_save_Click";
 //BA.debugLineNum = 228;BA.debugLine="Try";
try { //BA.debugLineNum = 229;BA.debugLine="date_generat";
_date_generat();
 //BA.debugLineNum = 230;BA.debugLine="Dim res_H_M As List =myFunc.time_show2(date_str,";
_res_h_m = new anywheresoftware.b4a.objects.collections.List();
_res_h_m = mostCurrent._myfunc._time_show2 /*anywheresoftware.b4a.objects.collections.List*/ (mostCurrent.activityBA,mostCurrent._date_str,mostCurrent._lbl_vorod_time.getText(),mostCurrent._lbl_khoroj_time.getText());
 //BA.debugLineNum = 233;BA.debugLine="myFunc.add_taradod(date_str,lbl_vorod_time.Text,";
mostCurrent._myfunc._add_taradod /*boolean*/ (mostCurrent.activityBA,mostCurrent._date_str,mostCurrent._lbl_vorod_time.getText(),mostCurrent._lbl_khoroj_time.getText(),(int)(BA.ObjectToNumber(_res_h_m.Get((int) (0)))),(int)(BA.ObjectToNumber(_res_h_m.Get((int) (1)))),mostCurrent._et_tozih.getText(),(int) (0));
 //BA.debugLineNum = 234;BA.debugLine="fill_list";
_fill_list();
 //BA.debugLineNum = 235;BA.debugLine="et_tozih.Text=\"\"";
mostCurrent._et_tozih.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 236;BA.debugLine="ToastMessageShow(\"تردد ذخیره شد\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("تردد ذخیره شد"),anywheresoftware.b4a.keywords.Common.False);
 } 
       catch (Exception e9) {
			processBA.setLastException(e9); //BA.debugLineNum = 238;BA.debugLine="ToastMessageShow(\"خطا در ثبت\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("خطا در ثبت"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 239;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("01572876",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 242;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_save_edit_click() throws Exception{
anywheresoftware.b4a.objects.collections.List _res_h_m = null;
 //BA.debugLineNum = 314;BA.debugLine="Private Sub lbl_save_edit_Click";
 //BA.debugLineNum = 315;BA.debugLine="Try";
try { //BA.debugLineNum = 316;BA.debugLine="Dim res_H_M As List =myFunc.time_show2	(et_date_";
_res_h_m = new anywheresoftware.b4a.objects.collections.List();
_res_h_m = mostCurrent._myfunc._time_show2 /*anywheresoftware.b4a.objects.collections.List*/ (mostCurrent.activityBA,mostCurrent._et_date_edit.getText(),mostCurrent._lbl_vorod_time_edit.getText(),mostCurrent._lbl_khoroj_time_edit.getText());
 //BA.debugLineNum = 319;BA.debugLine="myFunc.edit_taradod(current_id,et_date_edit.Text";
mostCurrent._myfunc._edit_taradod /*boolean*/ (mostCurrent.activityBA,_current_id,mostCurrent._et_date_edit.getText(),mostCurrent._lbl_vorod_time_edit.getText(),mostCurrent._lbl_khoroj_time_edit.getText(),(int)(BA.ObjectToNumber(_res_h_m.Get((int) (0)))),(int)(BA.ObjectToNumber(_res_h_m.Get((int) (1)))),mostCurrent._et_tozih_edit.getText(),(int) (1));
 //BA.debugLineNum = 321;BA.debugLine="pan_all_edit.Visible=False";
mostCurrent._pan_all_edit.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 322;BA.debugLine="fill_list";
_fill_list();
 //BA.debugLineNum = 323;BA.debugLine="ToastMessageShow(\"تردد ویرایش شد\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("تردد ویرایش شد"),anywheresoftware.b4a.keywords.Common.False);
 } 
       catch (Exception e8) {
			processBA.setLastException(e8); //BA.debugLineNum = 325;BA.debugLine="ToastMessageShow(\"خطا در ویرایش \",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("خطا در ویرایش "),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 326;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("02031628",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 329;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_show_list_click() throws Exception{
 //BA.debugLineNum = 405;BA.debugLine="Private Sub lbl_show_list_Click";
 //BA.debugLineNum = 406;BA.debugLine="fill_list";
_fill_list();
 //BA.debugLineNum = 407;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_vorod_tap_click() throws Exception{
String _tim_str1 = "";
 //BA.debugLineNum = 265;BA.debugLine="Private Sub lbl_vorod_tap_Click";
 //BA.debugLineNum = 266;BA.debugLine="Dim tim_str1 As String=\"\"";
_tim_str1 = "";
 //BA.debugLineNum = 267;BA.debugLine="tim_str1=DateTime.Time(DateTime.Now).SubString2(0";
_tim_str1 = anywheresoftware.b4a.keywords.Common.DateTime.Time(anywheresoftware.b4a.keywords.Common.DateTime.getNow()).substring((int) (0),(int) (5));
 //BA.debugLineNum = 268;BA.debugLine="lbl_vorod_time.Text=tim_str1";
mostCurrent._lbl_vorod_time.setText(BA.ObjectToCharSequence(_tim_str1));
 //BA.debugLineNum = 271;BA.debugLine="show_tim_lbl1";
_show_tim_lbl1();
 //BA.debugLineNum = 274;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_vorod_time_click() throws Exception{
String[] _tt = null;
int _tt_h = 0;
int _tt_m = 0;
int _j = 0;
 //BA.debugLineNum = 366;BA.debugLine="Private Sub lbl_vorod_time_Click";
 //BA.debugLineNum = 369;BA.debugLine="Dim tt() As String= Regex.Split(\":\",lbl_vorod_tim";
_tt = anywheresoftware.b4a.keywords.Common.Regex.Split(":",mostCurrent._lbl_vorod_time.getText());
 //BA.debugLineNum = 370;BA.debugLine="Dim tt_h As Int=tt(0)";
_tt_h = (int)(Double.parseDouble(_tt[(int) (0)]));
 //BA.debugLineNum = 371;BA.debugLine="Dim tt_m As Int=tt(1)";
_tt_m = (int)(Double.parseDouble(_tt[(int) (1)]));
 //BA.debugLineNum = 373;BA.debugLine="dialog_tim.SetTime(tt_h,tt_m,True)";
mostCurrent._dialog_tim.SetTime(_tt_h,_tt_m,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 375;BA.debugLine="Dim j As Int= dialog_tim.Show(\"\",\"زمان ورود\",\"باش";
_j = mostCurrent._dialog_tim.Show("","زمان ورود","باشه","","نه",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 376;BA.debugLine="If j=DialogResponse.POSITIVE Then";
if (_j==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 377;BA.debugLine="lbl_vorod_time.Text=dialog_tim.Hour&\":\"&dialog_t";
mostCurrent._lbl_vorod_time.setText(BA.ObjectToCharSequence(BA.NumberToString(mostCurrent._dialog_tim.getHour())+":"+BA.NumberToString(mostCurrent._dialog_tim.getMinute())));
 //BA.debugLineNum = 379;BA.debugLine="show_tim_lbl1";
_show_tim_lbl1();
 };
 //BA.debugLineNum = 381;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_vorod_time_edit_click() throws Exception{
String[] _tt = null;
int _tt_h = 0;
int _tt_m = 0;
int _j = 0;
 //BA.debugLineNum = 333;BA.debugLine="Private Sub lbl_vorod_time_edit_Click";
 //BA.debugLineNum = 335;BA.debugLine="Dim tt() As String= Regex.Split(\":\",lbl_vorod_tim";
_tt = anywheresoftware.b4a.keywords.Common.Regex.Split(":",mostCurrent._lbl_vorod_time_edit.getText());
 //BA.debugLineNum = 336;BA.debugLine="Dim tt_h As Int=tt(0)";
_tt_h = (int)(Double.parseDouble(_tt[(int) (0)]));
 //BA.debugLineNum = 337;BA.debugLine="Dim tt_m As Int=tt(1)";
_tt_m = (int)(Double.parseDouble(_tt[(int) (1)]));
 //BA.debugLineNum = 339;BA.debugLine="dialog_tim.SetTime(tt_h,tt_m,True)";
mostCurrent._dialog_tim.SetTime(_tt_h,_tt_m,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 341;BA.debugLine="Dim j As Int= dialog_tim.Show(\"\",\"زمان ورود\",\"باش";
_j = mostCurrent._dialog_tim.Show("","زمان ورود","باشه","","نه",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 342;BA.debugLine="If j=DialogResponse.POSITIVE Then";
if (_j==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 343;BA.debugLine="lbl_vorod_time_edit.Text=dialog_tim.Hour&\":\"&dia";
mostCurrent._lbl_vorod_time_edit.setText(BA.ObjectToCharSequence(BA.NumberToString(mostCurrent._dialog_tim.getHour())+":"+BA.NumberToString(mostCurrent._dialog_tim.getMinute())));
 //BA.debugLineNum = 345;BA.debugLine="show_tim_lbl2";
_show_tim_lbl2();
 };
 //BA.debugLineNum = 348;BA.debugLine="End Sub";
return "";
}
public static String  _pan_all_edit_click() throws Exception{
 //BA.debugLineNum = 310;BA.debugLine="Private Sub pan_all_edit_Click";
 //BA.debugLineNum = 311;BA.debugLine="pan_all_edit.Visible=False";
mostCurrent._pan_all_edit.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 312;BA.debugLine="End Sub";
return "";
}
public static String  _panel6_click() throws Exception{
 //BA.debugLineNum = 401;BA.debugLine="Private Sub Panel6_Click";
 //BA.debugLineNum = 403;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _show_tim_lbl1() throws Exception{
anywheresoftware.b4a.objects.collections.List _res_h_m = null;
 //BA.debugLineNum = 244;BA.debugLine="Sub show_tim_lbl1";
 //BA.debugLineNum = 245;BA.debugLine="Dim res_H_M As List =myFunc.time_show2(date_str,l";
_res_h_m = new anywheresoftware.b4a.objects.collections.List();
_res_h_m = mostCurrent._myfunc._time_show2 /*anywheresoftware.b4a.objects.collections.List*/ (mostCurrent.activityBA,mostCurrent._date_str,mostCurrent._lbl_vorod_time.getText(),mostCurrent._lbl_khoroj_time.getText());
 //BA.debugLineNum = 246;BA.debugLine="lbl_show_time.Text=\" ساعت \"&res_H_M.Get(0)&\" و \"&";
mostCurrent._lbl_show_time.setText(BA.ObjectToCharSequence(" ساعت "+BA.ObjectToString(_res_h_m.Get((int) (0)))+" و "+BA.ObjectToString(_res_h_m.Get((int) (1)))+" دقیقه "));
 //BA.debugLineNum = 247;BA.debugLine="End Sub";
return "";
}
public static String  _show_tim_lbl2() throws Exception{
anywheresoftware.b4a.objects.collections.List _res_h_m = null;
 //BA.debugLineNum = 250;BA.debugLine="Sub show_tim_lbl2";
 //BA.debugLineNum = 251;BA.debugLine="Dim res_H_M As List =myFunc.time_show2(et_date_edi";
_res_h_m = new anywheresoftware.b4a.objects.collections.List();
_res_h_m = mostCurrent._myfunc._time_show2 /*anywheresoftware.b4a.objects.collections.List*/ (mostCurrent.activityBA,mostCurrent._et_date_edit.getText(),mostCurrent._lbl_vorod_time_edit.getText(),mostCurrent._lbl_khoroj_time_edit.getText());
 //BA.debugLineNum = 252;BA.debugLine="lbl_show_time_edit.Text=\"ساعت \"&res_H_M.Get(0)&\"";
mostCurrent._lbl_show_time_edit.setText(BA.ObjectToCharSequence("ساعت "+BA.ObjectToString(_res_h_m.Get((int) (0)))+" و "+BA.ObjectToString(_res_h_m.Get((int) (1)))+"دقیقه "));
 //BA.debugLineNum = 253;BA.debugLine="End Sub";
return "";
}
}

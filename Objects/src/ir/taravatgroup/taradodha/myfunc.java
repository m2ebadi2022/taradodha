package ir.taravatgroup.taradodha;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class myfunc {
private static myfunc mostCurrent = new myfunc();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public b4a.example.dateutils _dateutils = null;
public ir.taravatgroup.taradodha.main _main = null;
public ir.taravatgroup.taradodha.starter _starter = null;
public ir.taravatgroup.taradodha.home_activity _home_activity = null;
public ir.taravatgroup.taradodha.add_activity _add_activity = null;
public ir.taravatgroup.taradodha.list_activity _list_activity = null;
public static boolean  _add_taradod(anywheresoftware.b4a.BA _ba,String _date,String _vorod,String _khoroj,int _h,int _m,String _tozih,int _state) throws Exception{
 //BA.debugLineNum = 65;BA.debugLine="Sub add_taradod(date As String,vorod As String,kho";
 //BA.debugLineNum = 66;BA.debugLine="connection_sql";
_connection_sql(_ba);
 //BA.debugLineNum = 67;BA.debugLine="Main.sql.ExecNonQuery2(\"INSERT INTO taradodha (da";
mostCurrent._main._sql /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2("INSERT INTO taradodha (date , vorod, khoroj , tim_h, tim_m, tozih , state) VALUES (?,?,?,?,?,?,?)",anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(_date),(Object)(_vorod),(Object)(_khoroj),(Object)(_h),(Object)(_m),(Object)(_tozih),(Object)(_state)}));
 //BA.debugLineNum = 68;BA.debugLine="Main.sql.Close";
mostCurrent._main._sql /*anywheresoftware.b4a.sql.SQL*/ .Close();
 //BA.debugLineNum = 69;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 70;BA.debugLine="End Sub";
return false;
}
public static String  _connection_sql(anywheresoftware.b4a.BA _ba) throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub connection_sql";
 //BA.debugLineNum = 11;BA.debugLine="If(File.Exists(File.DirInternal,\"data.db\")=False)";
if ((anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"data.db")==anywheresoftware.b4a.keywords.Common.False)) { 
 //BA.debugLineNum = 12;BA.debugLine="File.Copy(File.DirAssets,\"data.db\",File.DirInter";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"data.db",anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"data.db");
 };
 //BA.debugLineNum = 14;BA.debugLine="If (Main.sql.IsInitialized=False)Then";
if ((mostCurrent._main._sql /*anywheresoftware.b4a.sql.SQL*/ .IsInitialized()==anywheresoftware.b4a.keywords.Common.False)) { 
 //BA.debugLineNum = 15;BA.debugLine="Main.sql.Initialize(File.DirInternal,\"data.db\",T";
mostCurrent._main._sql /*anywheresoftware.b4a.sql.SQL*/ .Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"data.db",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return "";
}
public static String  _fa2en(anywheresoftware.b4a.BA _ba,String _a) throws Exception{
String _fa = "";
int _la = 0;
 //BA.debugLineNum = 56;BA.debugLine="Sub fa2en(a As String) As String";
 //BA.debugLineNum = 57;BA.debugLine="Dim fa As String=\"۰۱۲۳۴۵۶۷۸۹\"";
_fa = "۰۱۲۳۴۵۶۷۸۹";
 //BA.debugLineNum = 58;BA.debugLine="For la=0 To 9";
{
final int step2 = 1;
final int limit2 = (int) (9);
_la = (int) (0) ;
for (;_la <= limit2 ;_la = _la + step2 ) {
 //BA.debugLineNum = 59;BA.debugLine="a=a.Replace(fa.SubString2(la,la+1),la)";
_a = _a.replace(_fa.substring(_la,(int) (_la+1)),BA.NumberToString(_la));
 }
};
 //BA.debugLineNum = 61;BA.debugLine="Return a";
if (true) return _a;
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.collections.List  _get_by_id(anywheresoftware.b4a.BA _ba,int _id) throws Exception{
anywheresoftware.b4a.objects.collections.List _list_result = null;
 //BA.debugLineNum = 19;BA.debugLine="Sub get_by_id(id As Int) As List";
 //BA.debugLineNum = 21;BA.debugLine="connection_sql";
_connection_sql(_ba);
 //BA.debugLineNum = 22;BA.debugLine="Main.res=Main.sql.ExecQuery(\"SELECT * FROM tarado";
mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/  = (anywheresoftware.b4a.sql.SQL.ResultSetWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.ResultSetWrapper(), (android.database.Cursor)(mostCurrent._main._sql /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery("SELECT * FROM taradodha WHERE id="+BA.NumberToString(_id)+" ;")));
 //BA.debugLineNum = 23;BA.debugLine="Main.res.Position=0";
mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .setPosition((int) (0));
 //BA.debugLineNum = 25;BA.debugLine="Dim list_result As List";
_list_result = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 26;BA.debugLine="list_result.Initialize";
_list_result.Initialize();
 //BA.debugLineNum = 28;BA.debugLine="list_result.AddAll( Array As String(Main.res.GetI";
_list_result.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{BA.NumberToString(mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetInt("id")),mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetString("date"),mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetString("vorod"),mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetString("khoroj"),BA.NumberToString(mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetInt("tim_h")),BA.NumberToString(mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetInt("tim_m")),mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetString("tozih")}));
 //BA.debugLineNum = 30;BA.debugLine="Main.sql.Close";
mostCurrent._main._sql /*anywheresoftware.b4a.sql.SQL*/ .Close();
 //BA.debugLineNum = 31;BA.debugLine="Main.res.Close";
mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .Close();
 //BA.debugLineNum = 33;BA.debugLine="Return list_result";
if (true) return _list_result;
 //BA.debugLineNum = 35;BA.debugLine="End Sub";
return null;
}
public static anywheresoftware.b4a.objects.collections.List  _get_data(anywheresoftware.b4a.BA _ba,String _sal,String _mah) throws Exception{
anywheresoftware.b4a.objects.collections.List _list_result = null;
 //BA.debugLineNum = 38;BA.debugLine="Sub get_data(sal As String, mah As String) As List";
 //BA.debugLineNum = 39;BA.debugLine="connection_sql";
_connection_sql(_ba);
 //BA.debugLineNum = 40;BA.debugLine="Main.res=Main.sql.ExecQuery(\"SELECT * FROM tarado";
mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/  = (anywheresoftware.b4a.sql.SQL.ResultSetWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.ResultSetWrapper(), (android.database.Cursor)(mostCurrent._main._sql /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery("SELECT * FROM taradodha WHERE date like '%"+_sal+"/"+_mah+"%' ORDER BY  id DESC;")));
 //BA.debugLineNum = 42;BA.debugLine="Dim list_result As List";
_list_result = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 43;BA.debugLine="list_result.Initialize";
_list_result.Initialize();
 //BA.debugLineNum = 45;BA.debugLine="Do While Main.res.NextRow";
while (mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .NextRow()) {
 //BA.debugLineNum = 46;BA.debugLine="list_result.Add(Array As String(Main.res.GetInt(";
_list_result.Add((Object)(new String[]{BA.NumberToString(mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetInt("id")),mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetString("date"),mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetString("vorod"),mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetString("khoroj"),BA.NumberToString(mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetInt("tim_h")),BA.NumberToString(mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetInt("tim_m")),mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetString("tozih")}));
 }
;
 //BA.debugLineNum = 50;BA.debugLine="Main.sql.Close";
mostCurrent._main._sql /*anywheresoftware.b4a.sql.SQL*/ .Close();
 //BA.debugLineNum = 51;BA.debugLine="Main.res.Close";
mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .Close();
 //BA.debugLineNum = 53;BA.debugLine="Return list_result";
if (true) return _list_result;
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return null;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.collections.List  _time_show2(anywheresoftware.b4a.BA _ba,String _dat1,String _tim1,String _tim2) throws Exception{
anywheresoftware.b4a.objects.collections.List _lis_result = null;
anywheresoftware.b4a.objects.collections.List _list_date_per1 = null;
anywheresoftware.b4a.objects.collections.List _list_date_per2 = null;
anywheresoftware.b4a.objects.collections.List _list_date_miladi1 = null;
anywheresoftware.b4a.objects.collections.List _list_date_miladi2 = null;
String _dat_mil_2 = "";
String _dat_mil_1 = "";
String _date_end1 = "";
String _date_end2 = "";
String _time_end1 = "";
String _time_end2 = "";
long _tim1_long = 0L;
long _tim2_long = 0L;
b4a.example.dateutils._period _period_between = null;
anywheresoftware.b4a.keywords.StringBuilderWrapper _str_show = null;
 //BA.debugLineNum = 74;BA.debugLine="Sub time_show2 (dat1 As String, tim1 As String,tim";
 //BA.debugLineNum = 76;BA.debugLine="Try";
try { //BA.debugLineNum = 77;BA.debugLine="Dim lis_result As List";
_lis_result = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 78;BA.debugLine="lis_result.Initialize";
_lis_result.Initialize();
 //BA.debugLineNum = 81;BA.debugLine="Dim list_date_per1 , list_date_per2 As List";
_list_date_per1 = new anywheresoftware.b4a.objects.collections.List();
_list_date_per2 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 82;BA.debugLine="Dim list_date_miladi1 ,list_date_miladi2 As List";
_list_date_miladi1 = new anywheresoftware.b4a.objects.collections.List();
_list_date_miladi2 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 83;BA.debugLine="Dim dat_mil_2 As String";
_dat_mil_2 = "";
 //BA.debugLineNum = 84;BA.debugLine="Dim dat_mil_1 As String";
_dat_mil_1 = "";
 //BA.debugLineNum = 86;BA.debugLine="list_date_per1.Initialize";
_list_date_per1.Initialize();
 //BA.debugLineNum = 87;BA.debugLine="list_date_per2.Initialize";
_list_date_per2.Initialize();
 //BA.debugLineNum = 88;BA.debugLine="list_date_miladi1.Initialize";
_list_date_miladi1.Initialize();
 //BA.debugLineNum = 89;BA.debugLine="list_date_miladi1.Initialize";
_list_date_miladi1.Initialize();
 //BA.debugLineNum = 96;BA.debugLine="list_date_per1=Main.strfun.Split(dat1,\"/\")";
_list_date_per1 = mostCurrent._main._strfun /*adr.stringfunctions.stringfunctions*/ ._vvvvvv5(_dat1,"/");
 //BA.debugLineNum = 97;BA.debugLine="list_date_per2=Main.strfun.Split(dat1,\"/\")";
_list_date_per2 = mostCurrent._main._strfun /*adr.stringfunctions.stringfunctions*/ ._vvvvvv5(_dat1,"/");
 //BA.debugLineNum = 101;BA.debugLine="dat_mil_2=Main.prsianDate.PersianToGregorian(lis";
_dat_mil_2 = mostCurrent._main._prsiandate /*com.b4a.manamsoftware.PersianDate.ManamPersianDate*/ .PersianToGregorian((int)(BA.ObjectToNumber(_list_date_per2.Get((int) (0)))),(int)(BA.ObjectToNumber(_list_date_per2.Get((int) (1)))),(int)(BA.ObjectToNumber(_list_date_per2.Get((int) (2)))));
 //BA.debugLineNum = 102;BA.debugLine="dat_mil_1=Main.prsianDate.PersianToGregorian(lis";
_dat_mil_1 = mostCurrent._main._prsiandate /*com.b4a.manamsoftware.PersianDate.ManamPersianDate*/ .PersianToGregorian((int)(BA.ObjectToNumber(_list_date_per1.Get((int) (0)))),(int)(BA.ObjectToNumber(_list_date_per1.Get((int) (1)))),(int)(BA.ObjectToNumber(_list_date_per1.Get((int) (2)))));
 //BA.debugLineNum = 105;BA.debugLine="list_date_miladi1=Main.strfun.Split(dat_mil_1,\"/";
_list_date_miladi1 = mostCurrent._main._strfun /*adr.stringfunctions.stringfunctions*/ ._vvvvvv5(_dat_mil_1,"/");
 //BA.debugLineNum = 106;BA.debugLine="list_date_miladi2=Main.strfun.Split(dat_mil_2,\"/";
_list_date_miladi2 = mostCurrent._main._strfun /*adr.stringfunctions.stringfunctions*/ ._vvvvvv5(_dat_mil_2,"/");
 //BA.debugLineNum = 109;BA.debugLine="Dim date_end1 ,date_end2 As String";
_date_end1 = "";
_date_end2 = "";
 //BA.debugLineNum = 110;BA.debugLine="Dim time_end1 ,time_end2 As String";
_time_end1 = "";
_time_end2 = "";
 //BA.debugLineNum = 112;BA.debugLine="date_end2=list_date_miladi2.Get(1)&\"/\"&list_date";
_date_end2 = BA.ObjectToString(_list_date_miladi2.Get((int) (1)))+"/"+BA.ObjectToString(_list_date_miladi2.Get((int) (2)))+"/"+BA.ObjectToString(_list_date_miladi2.Get((int) (0)));
 //BA.debugLineNum = 113;BA.debugLine="date_end1=list_date_miladi1.Get(1)&\"/\"&list_date";
_date_end1 = BA.ObjectToString(_list_date_miladi1.Get((int) (1)))+"/"+BA.ObjectToString(_list_date_miladi1.Get((int) (2)))+"/"+BA.ObjectToString(_list_date_miladi1.Get((int) (0)));
 //BA.debugLineNum = 115;BA.debugLine="time_end2=tim2&\":00\"";
_time_end2 = _tim2+":00";
 //BA.debugLineNum = 116;BA.debugLine="time_end1=tim1&\":00\"";
_time_end1 = _tim1+":00";
 //BA.debugLineNum = 118;BA.debugLine="Dim tim1_long As Long";
_tim1_long = 0L;
 //BA.debugLineNum = 119;BA.debugLine="Dim tim2_long As Long";
_tim2_long = 0L;
 //BA.debugLineNum = 120;BA.debugLine="tim1_long=DateTime.DateTimeParse(fa2en(date_end1";
_tim1_long = anywheresoftware.b4a.keywords.Common.DateTime.DateTimeParse(_fa2en(_ba,_date_end1),_fa2en(_ba,_time_end1));
 //BA.debugLineNum = 121;BA.debugLine="tim2_long=DateTime.DateTimeParse(fa2en(date_end2";
_tim2_long = anywheresoftware.b4a.keywords.Common.DateTime.DateTimeParse(_fa2en(_ba,_date_end2),_fa2en(_ba,_time_end2));
 //BA.debugLineNum = 125;BA.debugLine="Dim period_between As Period";
_period_between = new b4a.example.dateutils._period();
 //BA.debugLineNum = 126;BA.debugLine="period_between=DateUtils.PeriodBetween(fa2en(tim";
_period_between = mostCurrent._dateutils._periodbetween(_ba,(long)(Double.parseDouble(_fa2en(_ba,BA.NumberToString(_tim1_long)))),(long)(Double.parseDouble(_fa2en(_ba,BA.NumberToString(_tim2_long)))));
 //BA.debugLineNum = 129;BA.debugLine="Dim str_show As StringBuilder";
_str_show = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 130;BA.debugLine="str_show.Initialize";
_str_show.Initialize();
 //BA.debugLineNum = 132;BA.debugLine="If (period_between.Years<>0)Then";
if ((_period_between.Years!=0)) { 
 //BA.debugLineNum = 133;BA.debugLine="str_show.Append(period_between.Years&\" سال \").A";
_str_show.Append(BA.NumberToString(_period_between.Years)+" سال ").Append(" و ");
 };
 //BA.debugLineNum = 135;BA.debugLine="If (period_between.Months<>0)Then";
if ((_period_between.Months!=0)) { 
 //BA.debugLineNum = 136;BA.debugLine="str_show.Append(period_between.Months&\" ماه \").";
_str_show.Append(BA.NumberToString(_period_between.Months)+" ماه ").Append(" و ");
 };
 //BA.debugLineNum = 138;BA.debugLine="If (period_between.Days<>0)Then";
if ((_period_between.Days!=0)) { 
 //BA.debugLineNum = 139;BA.debugLine="str_show.Append(period_between.Days&\" روز \").Ap";
_str_show.Append(BA.NumberToString(_period_between.Days)+" روز ").Append(" و ");
 };
 //BA.debugLineNum = 142;BA.debugLine="str_show.Append(period_between.Hours&\" ساعت \").A";
_str_show.Append(BA.NumberToString(_period_between.Hours)+" ساعت ").Append(" و ");
 //BA.debugLineNum = 143;BA.debugLine="str_show.Append(period_between.Minutes&\" دقیقه \"";
_str_show.Append(BA.NumberToString(_period_between.Minutes)+" دقیقه ");
 //BA.debugLineNum = 156;BA.debugLine="lis_result.Add(period_between.Hours)";
_lis_result.Add((Object)(_period_between.Hours));
 //BA.debugLineNum = 157;BA.debugLine="lis_result.Add(period_between.Minutes)";
_lis_result.Add((Object)(_period_between.Minutes));
 } 
       catch (Exception e46) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e46); //BA.debugLineNum = 160;BA.debugLine="ToastMessageShow(\"خطا\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("خطا"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 163;BA.debugLine="Return lis_result";
if (true) return _lis_result;
 //BA.debugLineNum = 164;BA.debugLine="End Sub";
return null;
}
}

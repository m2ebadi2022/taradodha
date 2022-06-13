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
 //BA.debugLineNum = 45;BA.debugLine="Sub add_taradod(date As String,vorod As String,kho";
 //BA.debugLineNum = 46;BA.debugLine="connection_sql";
_connection_sql(_ba);
 //BA.debugLineNum = 47;BA.debugLine="Main.sql.ExecNonQuery2(\"INSERT INTO taradodha (da";
mostCurrent._main._sql /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2("INSERT INTO taradodha (date , vorod, khoroj , tim_h, tim_m, tozih , state) VALUES (?,?,?,?,?,?,?)",anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(_date),(Object)(_vorod),(Object)(_khoroj),(Object)(_h),(Object)(_m),(Object)(_tozih),(Object)(_state)}));
 //BA.debugLineNum = 48;BA.debugLine="Main.sql.Close";
mostCurrent._main._sql /*anywheresoftware.b4a.sql.SQL*/ .Close();
 //BA.debugLineNum = 49;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 36;BA.debugLine="Sub fa2en(a As String) As String";
 //BA.debugLineNum = 37;BA.debugLine="Dim fa As String=\"۰۱۲۳۴۵۶۷۸۹\"";
_fa = "۰۱۲۳۴۵۶۷۸۹";
 //BA.debugLineNum = 38;BA.debugLine="For la=0 To 9";
{
final int step2 = 1;
final int limit2 = (int) (9);
_la = (int) (0) ;
for (;_la <= limit2 ;_la = _la + step2 ) {
 //BA.debugLineNum = 39;BA.debugLine="a=a.Replace(fa.SubString2(la,la+1),la)";
_a = _a.replace(_fa.substring(_la,(int) (_la+1)),BA.NumberToString(_la));
 }
};
 //BA.debugLineNum = 41;BA.debugLine="Return a";
if (true) return _a;
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.collections.List  _get_data(anywheresoftware.b4a.BA _ba,String _sal,String _mah) throws Exception{
anywheresoftware.b4a.objects.collections.List _list_result = null;
 //BA.debugLineNum = 21;BA.debugLine="Sub get_data(sal As String, mah As String) As List";
 //BA.debugLineNum = 22;BA.debugLine="connection_sql";
_connection_sql(_ba);
 //BA.debugLineNum = 23;BA.debugLine="Main.res=Main.sql.ExecQuery(\"SELECT * FROM tarado";
mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/  = (anywheresoftware.b4a.sql.SQL.ResultSetWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.ResultSetWrapper(), (android.database.Cursor)(mostCurrent._main._sql /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery("SELECT * FROM taradodha WHERE date like '%"+_sal+"/"+_mah+"%' ORDER BY  id DESC;")));
 //BA.debugLineNum = 25;BA.debugLine="Dim list_result As List";
_list_result = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 26;BA.debugLine="list_result.Initialize";
_list_result.Initialize();
 //BA.debugLineNum = 28;BA.debugLine="Do While Main.res.NextRow";
while (mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .NextRow()) {
 //BA.debugLineNum = 29;BA.debugLine="list_result.Add(Array As String(Main.res.GetInt(";
_list_result.Add((Object)(new String[]{BA.NumberToString(mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetInt("id")),mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetString("date"),mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetString("vorod"),mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetString("khoroj"),BA.NumberToString(mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetInt("tim_h")),BA.NumberToString(mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetInt("tim_m")),mostCurrent._main._res /*anywheresoftware.b4a.sql.SQL.ResultSetWrapper*/ .GetString("tozih")}));
 }
;
 //BA.debugLineNum = 33;BA.debugLine="Return list_result";
if (true) return _list_result;
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return null;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
}

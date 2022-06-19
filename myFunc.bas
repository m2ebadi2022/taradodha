B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=StaticCode
Version=11.5
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	
End Sub


Sub connection_sql
	If(File.Exists(File.DirInternal,"data.db")=False)Then
		File.Copy(File.DirAssets,"data.db",File.DirInternal,"data.db")
	End If
	If (Main.sql.IsInitialized=False)Then
		Main.sql.Initialize(File.DirInternal,"data.db",True)
	End If
End Sub

Sub get_by_id(id As Int) As List
	
	connection_sql
	Main.res=Main.sql.ExecQuery("SELECT * FROM taradodha WHERE id="&id&" ;")
	Main.res.Position=0
	
	Dim list_result As List
	list_result.Initialize
	
	list_result.AddAll( Array As String(Main.res.GetInt("id"),Main.res.GetString("date"),Main.res.GetString("vorod"),Main.res.GetString("khoroj"),Main.res.GetInt("tim_h"),Main.res.GetInt("tim_m"),Main.res.GetString("tozih")))
	
	Main.sql.Close
	Main.res.Close
	
	Return list_result
	
End Sub


Sub get_data(sal As String, mah As String) As List
	connection_sql
	Main.res=Main.sql.ExecQuery("SELECT * FROM taradodha WHERE date like '%"&sal&"/"&mah&"%' ORDER BY  id DESC;")
	
	Dim list_result As List
	list_result.Initialize
	
	Do While Main.res.NextRow
		list_result.Add(Array As String(Main.res.GetInt("id"),Main.res.GetString("date"),Main.res.GetString("vorod"),Main.res.GetString("khoroj"),Main.res.GetInt("tim_h"),Main.res.GetInt("tim_m"),Main.res.GetString("tozih")))
		
	Loop
	
	Main.sql.Close
	Main.res.Close
	
	Return list_result
End Sub

Sub fa2en(a As String) As String
	Dim fa As String="۰۱۲۳۴۵۶۷۸۹"
	For la=0 To 9
		a=a.Replace(fa.SubString2(la,la+1),la)
	Next
	Return a
End Sub


Sub add_taradod(date As String,vorod As String,khoroj As String,h As Int,m As Int, tozih As String, state As Int) As Boolean
	connection_sql
	Main.sql.ExecNonQuery2("INSERT INTO taradodha (date , vorod, khoroj , tim_h, tim_m, tozih , state) VALUES (?,?,?,?,?,?,?)", Array As Object(date,vorod, khoroj, h,m,tozih,state))
	Main.sql.Close
	Return True
End Sub
Sub edit_taradod(id As Int,date As String,vorod As String,khoroj As String,h As Int,m As Int, tozih As String, state As Int) As Boolean
	
	connection_sql
	Main.sql.ExecNonQuery2("UPDATE taradodha SET date=? , vorod=?, khoroj=?, tim_h=?, tim_m=?, tozih=?, state=? WHERE id=?;", Array As Object(date,vorod, khoroj, h,m,tozih,state,id))
	Main.sql.Close
	Return True
End Sub

Sub delete_taradod(id As Int) As Boolean
	connection_sql
	Main.sql.ExecNonQuery("DELETE FROM taradodha WHERE id="&id)
	Main.sql.Close
	Return True


End Sub


Sub time_show2 (dat1 As String, tim1 As String,tim2 As String) As List
	
	Try
		Dim lis_result As List
		lis_result.Initialize
	
	
		Dim list_date_per1 , list_date_per2 As List
		Dim list_date_miladi1 ,list_date_miladi2 As List
		Dim dat_mil_2 As String
		Dim dat_mil_1 As String
	
		list_date_per1.Initialize
		list_date_per2.Initialize
		list_date_miladi1.Initialize
		list_date_miladi1.Initialize
	
		
		
		
	
		
		list_date_per1=Main.strfun.Split(dat1,"/")
		list_date_per2=Main.strfun.Split(dat1,"/")
	
	
	
		dat_mil_2=Main.prsianDate.PersianToGregorian(list_date_per2.Get(0),list_date_per2.Get(1),list_date_per2.Get(2))
		dat_mil_1=Main.prsianDate.PersianToGregorian(list_date_per1.Get(0),list_date_per1.Get(1),list_date_per1.Get(2))
	
	
		list_date_miladi1=Main.strfun.Split(dat_mil_1,"/")
		list_date_miladi2=Main.strfun.Split(dat_mil_2,"/")
	
	
		Dim date_end1 ,date_end2 As String
		Dim time_end1 ,time_end2 As String
	
		date_end2=list_date_miladi2.Get(1)&"/"&list_date_miladi2.Get(2)&"/"&list_date_miladi2.Get(0)
		date_end1=list_date_miladi1.Get(1)&"/"&list_date_miladi1.Get(2)&"/"&list_date_miladi1.Get(0)
	
		time_end2=tim2&":00"
		time_end1=tim1&":00"
	
		Dim tim1_long As Long
		Dim tim2_long As Long
		tim1_long=DateTime.DateTimeParse(fa2en(date_end1),fa2en(time_end1))
		tim2_long=DateTime.DateTimeParse(fa2en(date_end2),fa2en(time_end2))
	
	
	
		Dim period_between As Period
		period_between=DateUtils.PeriodBetween(fa2en(tim1_long),fa2en(tim2_long))
	
	
		Dim str_show As StringBuilder
		str_show.Initialize
	
		If (period_between.Years<>0)Then
			str_show.Append(period_between.Years&" سال ").Append(" و ")
		End If
		If (period_between.Months<>0)Then
			str_show.Append(period_between.Months&" ماه ").Append(" و ")
		End If
		If (period_between.Days<>0)Then
			str_show.Append(period_between.Days&" روز ").Append(" و ")
		End If
		
		str_show.Append(period_between.Hours&" ساعت ").Append(" و ")
		str_show.Append(period_between.Minutes&" دقیقه ")
		
		
'		year_bt=period_between.Years
'		moon_bt=period_between.Months
'		day_bt=period_between.Days
'		h_bt=period_between.Hours
'		m_bt=period_between.Minutes

		
'		lbl_show_time.Text=""&h_bt&" و "&m_bt&"دقیقه "
'		lbl_show_time_edit.Text=""&h_bt&" و "&m_bt&"دقیقه "

		lis_result.Add(period_between.Hours)
		lis_result.Add(period_between.Minutes)
		
	Catch
		ToastMessageShow("خطا",False)
	End Try
	
	Return lis_result
End Sub




Sub get_day_name (date_per As String) As String
	
	Dim date_l() As String
	date_l=Regex.Split("/",date_per)
	Dim day_index_name As String=""
		
	Dim date_miladi As String=Main.prsianDate.PersianToGregorian(date_l(0),date_l(1),date_l(2))
	Dim date_l2() As String
	date_l2=Regex.Split("/",fa2en( date_miladi))
		
	Dim day_index As Int=DateTime.GetDayOfWeek(DateTime.DateParse(date_l2(1)&"/"&date_l2(2)&"/"&date_l2(0)))
		
	Select day_index
		Case 7
			day_index_name="شنبه"
		Case 1
			day_index_name="یکشنبه"
		Case 2
			day_index_name="دوشنبه"
		Case 3
			day_index_name="سه شنبه"
		Case 4
			day_index_name="چهارشنبه"
		Case 5
			day_index_name="پنجشنبه"
		Case 6
			day_index_name="جمعه"
				
	End Select

	Return day_index_name
End Sub


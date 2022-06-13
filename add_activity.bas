B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=11.5
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: false
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
Dim prsianDate As ManamPersianDate
	Dim strfun As StringFunctions
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private et_tozih As EditText
	Private et_date As EditText
	Private lbl_khoroj_time As Label
	Private lbl_vorod_time As Label
	Private cusListV_data As CustomListView
	Private lbl_show_allTime As Label
	
	Private xui As XUI
	Dim p As B4XView
	Private lbl_date_item As Label
	Private lbl_vorod_item As Label
	Private lbl_khoroj_item As Label
	Private lbl_tozih_item As Label
	
	
	Dim h_bt As Int=0
	Dim m_bt As Int=0
	Private lbl_edit_item As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("add_layout")
	et_date.Text=prsianDate.PersianShortDate
	
	fill_list("1401","03")
	
End Sub

Sub fill_list(sal As String , mah As String)
	Dim list1 As List
	list1.Initialize
	list1=myFunc.get_data(sal,mah)
	
	
	
	
	
	
	cusListV_data.Clear
	
	Dim all_tim_h As Int=0
	Dim all_tim_m As Int=0
	
	
	
	For i=0 To list1.Size-1
		p = xui.CreatePanel("p")
		p.SetLayoutAnimated(0, 0, 0, 95%x, 120dip)
		p.LoadLayout("irem_data")
		Dim list2() As String=list1.Get(i)
		
		cusListV_data.Add(p,list2(0))
		lbl_edit_item.Tag=list2(0)
		
		lbl_date_item.Text=list2(1)
		lbl_vorod_item.Text=list2(2)
		lbl_khoroj_item.Text=list2(3)
		lbl_tozih_item.Text=list2(6)
		
		all_tim_h=all_tim_h+list2(4)
		all_tim_m=all_tim_m+list2(5)
		
	Next
	
	If(all_tim_m>59)Then
		all_tim_h=all_tim_h+(all_tim_m / 60)
		all_tim_m=all_tim_m Mod 60
	End If
	
	lbl_show_allTime.Text=all_tim_h &"ساعت و "&all_tim_m &"دقیقه "
End Sub
Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Private Sub lbl_back_Click
	StartActivity(home_activity)
	Activity.Finish
End Sub

Private Sub lbl_save_Click
	
	
	time_show2(et_date.Text,lbl_vorod_time.Text,lbl_khoroj_time.Text)
	
	myFunc.add_taradod(et_date.Text,lbl_vorod_time.Text,lbl_khoroj_time.Text,h_bt,m_bt,et_tozih.Text,0)
	fill_list("1401","03")
	et_tozih.Text=""
End Sub

Private Sub lbl_khoroj_tap_Click
	lbl_khoroj_time.Text=DateTime.Time(DateTime.Now)
End Sub

Private Sub lbl_vorod_tap_Click
	lbl_vorod_time.Text=DateTime.Time(DateTime.Now)
End Sub



Private Sub lbl_edit_item_Click
	Dim lb As Label=Sender
	
	
	
	Log(lb.Tag)
End Sub



Sub time_show2 (dat1 As String, tim1 As String,tim2 As String) 
	
	Try
	
	
		Dim list_date_per1 , list_date_per2 As List
		Dim list_date_miladi1 ,list_date_miladi2 As List
		Dim dat_mil_2 As String
		Dim dat_mil_1 As String
	
		list_date_per1.Initialize
		list_date_per2.Initialize
		list_date_miladi1.Initialize
		list_date_miladi1.Initialize
	
		
		
		
	
		
		list_date_per1=strfun.Split(dat1,"/")
		list_date_per2=strfun.Split(dat1,"/")
	
	
	
		dat_mil_2=prsianDate.PersianToGregorian(list_date_per2.Get(0),list_date_per2.Get(1),list_date_per2.Get(2))
		dat_mil_1=prsianDate.PersianToGregorian(list_date_per1.Get(0),list_date_per1.Get(1),list_date_per1.Get(2))
	
	
		list_date_miladi1=strfun.Split(dat_mil_1,"/")
		list_date_miladi2=strfun.Split(dat_mil_2,"/")
	
	
		Dim date_end1 ,date_end2 As String
		Dim time_end1 ,time_end2 As String
	
		date_end2=list_date_miladi2.Get(1)&"/"&list_date_miladi2.Get(2)&"/"&list_date_miladi2.Get(0)
		date_end1=list_date_miladi1.Get(1)&"/"&list_date_miladi1.Get(2)&"/"&list_date_miladi1.Get(0)
	
		time_end2=tim2&":00"
		time_end1=tim1&":00"
	
		Dim tim1_long As Long
		Dim tim2_long As Long
		tim1_long=DateTime.DateTimeParse(myFunc.fa2en(date_end1),myFunc.fa2en(time_end1))
		tim2_long=DateTime.DateTimeParse(myFunc.fa2en(date_end2),myFunc.fa2en(time_end2))
	
	
	
		Dim period_between As Period
		period_between=DateUtils.PeriodBetween(myFunc.fa2en(tim1_long),myFunc.fa2en(tim2_long))
	
	
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
		
		
		'year_bt=period_between.Years
		'moon_bt=period_between.Months
		'day_bt=period_between.Days
		
		h_bt=period_between.Hours
		m_bt=period_between.Minutes
		
		
		
		
		
	Catch
		ToastMessageShow("خطا",False)
	End Try
	
	
End Sub


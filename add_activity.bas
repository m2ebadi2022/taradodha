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
	
	
	
	Private lbl_edit_item As Label
	Private lbl_show_time As Label
	Private pan_all_edit As Panel
	Private et_date_edit As EditText
	Private et_tozih_edit As EditText
	Private lbl_khoroj_time_edit As Label
	Private lbl_vorod_time_edit As Label
	Private lbl_show_time_edit As Label
	
	Dim current_id As Int=0
	
	Dim dialog_tim As TimeDialog
	
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("add_layout")
	et_date.Text=Main.prsianDate.PersianShortDate
	
	fill_list("1401","03")
	dialog_tim.Is24Hours=True
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
	
	Dim res_H_M As List =myFunc.time_show2(et_date.Text,lbl_vorod_time.Text,lbl_khoroj_time.Text)
	
	
	myFunc.add_taradod(et_date.Text,lbl_vorod_time.Text,lbl_khoroj_time.Text,res_H_M.Get(0),res_H_M.Get(1),et_tozih.Text,0)
	fill_list("1401","03")
	et_tozih.Text=""
End Sub

Sub show_tim_lbl1
	Dim res_H_M As List =myFunc.time_show2(et_date.Text,lbl_vorod_time.Text,lbl_khoroj_time.Text)
	lbl_show_time.Text=""&res_H_M.Get(0)&" و "&res_H_M.Get(1)&"دقیقه "
End Sub


Sub show_tim_lbl2
Dim res_H_M As List =myFunc.time_show2(et_date_edit.Text,lbl_vorod_time_edit.Text,lbl_khoroj_time_edit.Text)
	lbl_show_time_edit.Text=""&res_H_M.Get(0)&" و "&res_H_M.Get(1)&"دقیقه "
End Sub


Private Sub lbl_khoroj_tap_Click
	Dim tim_str As String=""
	tim_str=DateTime.Time(DateTime.Now).SubString2(0,5)
	lbl_khoroj_time.Text=tim_str
	
	show_tim_lbl1
	
End Sub

Private Sub lbl_vorod_tap_Click
	Dim tim_str1 As String=""
	tim_str1=DateTime.Time(DateTime.Now).SubString2(0,5)
	lbl_vorod_time.Text=tim_str1
	
	
	show_tim_lbl1
	
	
End Sub



Private Sub lbl_edit_item_Click
	Dim lb As Label=Sender
	
	current_id=lb.Tag
	
	
	
	
	
	Dim list_rec As List=myFunc.get_by_id(current_id)
	
	
	
	'list_rec.Get(0)
	et_date_edit.Text=list_rec.Get(1)
	lbl_vorod_time_edit.Text=list_rec.Get(2)
	lbl_khoroj_time_edit.Text=list_rec.Get(3)
	
	
	'list_rec.Get(4)
	'list_rec.Get(5)
	
	et_tozih_edit.Text=list_rec.Get(6)
	
	show_tim_lbl2

pan_all_edit.Visible=True
	
End Sub



Private Sub pan_all_edit_Click
	pan_all_edit.Visible=False
End Sub

Private Sub lbl_save_edit_Click
	
End Sub

Private Sub lbl_delete_edit_Click
	
End Sub



Private Sub lbl_vorod_time_edit_Click
	
	Dim tt() As String= Regex.Split(":",lbl_vorod_time_edit.Text)
	Dim tt_h As Int=tt(0)
	Dim tt_m As Int=tt(1)
	
	dialog_tim.SetTime(tt_h,tt_m,True)
	
	Dim j As Int= dialog_tim.Show("زمان ورود","انتخاب","باشه","","نه",Null)
	If j=DialogResponse.POSITIVE Then
		lbl_vorod_time_edit.Text=dialog_tim.Hour&":"&dialog_tim.Minute
		
		show_tim_lbl2
	End If
	
End Sub

Private Sub lbl_khoroj_time_edit_Click
	
	Dim tt() As String= Regex.Split(":",lbl_khoroj_time_edit.Text)
	Dim tt_h As Int=tt(0)
	Dim tt_m As Int=tt(1)
	
	dialog_tim.SetTime(tt_h,tt_m,True)
	
	Dim j As Int= dialog_tim.Show("زمان ورود","انتخاب","باشه","","نه",Null)
	If j=DialogResponse.POSITIVE Then
		lbl_khoroj_time_edit.Text=dialog_tim.Hour&":"&dialog_tim.Minute
		
		show_tim_lbl2
	End If
End Sub

Private Sub lbl_vorod_time_Click
	
	
	Dim tt() As String= Regex.Split(":",lbl_vorod_time.Text)
	Dim tt_h As Int=tt(0)
	Dim tt_m As Int=tt(1)
	
	dialog_tim.SetTime(tt_h,tt_m,True)
	
	Dim j As Int= dialog_tim.Show("زمان ورود","انتخاب","باشه","","نه",Null)
	If j=DialogResponse.POSITIVE Then
		lbl_vorod_time.Text=dialog_tim.Hour&":"&dialog_tim.Minute
		
		show_tim_lbl1
	End If
End Sub

Private Sub lbl_khoroj_time_Click
	
	Dim tt() As String= Regex.Split(":",lbl_khoroj_time.Text)
	Dim tt_h As Int=tt(0)
	Dim tt_m As Int=tt(1)
	
	dialog_tim.SetTime(tt_h,tt_m,True)
	
	Dim j As Int= dialog_tim.Show("زمان خروج","انتخاب","باشه","","نه",Null)
	If j=DialogResponse.POSITIVE Then
		lbl_khoroj_time.Text=dialog_tim.Hour&":"&dialog_tim.Minute
		
		show_tim_lbl1
	End If
	
	
End Sub